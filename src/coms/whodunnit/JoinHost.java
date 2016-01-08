package coms.whodunnit;


import java.util.List;



import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

public class JoinHost extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	
    	
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_host);
        

    	Context context=this;
    	WifiManager mWifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
    	mWifiManager.setWifiEnabled(true);
    	
        
        WifiConfiguration conf = new WifiConfiguration();
        conf.SSID = "\"\"" + "CLUE" + "\"\"";
        
        conf.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
               
        WifiManager wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE); 
        wifiManager.addNetwork(conf);

        List<WifiConfiguration> list = wifiManager.getConfiguredNetworks();
        for( WifiConfiguration i : list ) {
        	 System.out.println(i.SSID);  
            if(i.SSID != null && i.SSID.equals("\"\"" + "CLUE" + "\"\"") ) 
            {
                try {
                    wifiManager.disconnect();
                    wifiManager.enableNetwork(i.networkId, true);
                    System.out.print("i.networkId " + i.networkId + "\n");
                    wifiManager.reconnect();
          
        Intent intent = new Intent(JoinHost.this, EnterName.class);
        intent.putExtra("from", "join");
            	    startActivity(intent);
            
            	    break;
                }
                catch (Exception e) {
                    e.printStackTrace();
                }

            }           
            
        }
        	
        
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.join_host, menu);
        return true;
    }
    
}
