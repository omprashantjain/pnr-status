package woodbug.pnr.enquiry;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class ShowStatus extends Activity {

  JSONObject resultJson;
  TextView resultView;
  Result result;
  
  @Override
  protected void onCreate(Bundle savedInstanceState) {

    super.onCreate(savedInstanceState);
    setContentView(R.layout.status);

    resultView = (TextView) findViewById(R.id.result);
    Intent i = getIntent();
    
    try {
      if(i.getStringExtra("mode").equals("sms")) {
        String resultString = i.getStringExtra("result");
        result = PNRUtil.parseSMSResult(resultString);
        resultView.setText(result.toString());
                    
      } else {
        //Log.i("got", i.getIntExtra("status", 0)+ "");
        if(i.getIntExtra("status", 0) == 200) {
          resultJson = new JSONObject(i.getStringExtra("result"));
          String stat = resultJson.getString("status");
        
          if (!stat.equals("OK"))
            throw new Exception();
        
          result = PNRUtil.parseInternetResult(resultJson);
          resultView.setText(result.toString());
          
        } else {
          resultView.setText("Unable to reach railway server, Please try"
        		             + " after some time or use SMS mode.");
        }
      }

    } catch (Exception ignore) {
      ignore.printStackTrace();
      Toast.makeText(getApplicationContext(),
        "Invalid Result. check PNR number..", Toast.LENGTH_LONG).show();
    }
  }

}