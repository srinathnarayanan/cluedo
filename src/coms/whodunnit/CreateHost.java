package coms.whodunnit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import coms.whitebyte.wifihotspotutils.ClientScanResult;
import coms.whitebyte.wifihotspotutils.FinishScanListener;
import coms.whitebyte.wifihotspotutils.WifiApManager;


import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff.Mode;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class CreateHost extends Activity {
	WifiConfiguration netConfig = new WifiConfiguration();
	TextView textView1,textView2;
	int count=0;
	int players=2;
	int clicked=0; 
	String name;
	String prev= new String("");
	String message=new String("àwaiting\\for\\other\\playersá:àwelcome\\to\\the\\gameá:");
	WifiApManager wifiApManager; 
	private final int SERVER_PORT = 8080; //Define the server port
	ArrayList<NameIpArray> nameip1=new ArrayList<NameIpArray>();
	ProgressBar p;
	int val=0;
		@Override
    protected void onCreate(Bundle savedInstanceState) {
		
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_host);
            
        
        p=(ProgressBar) findViewById(R.id.progressBar1);
        p.getProgressDrawable().setColorFilter(Color.YELLOW, Mode.SRC_IN);
        
        Intent intent1=getIntent();
        name=intent1.getStringExtra("name");
        
        Typeface typeFace=Typeface.createFromAsset(this.getAssets(),"fonts/yorkwhiteletter.ttf");
       
      
        
        /*
        final String[] number={"1","2","3","4","5","6","7","8"};
        Builder builder = new AlertDialog.Builder(this);  
        builder.setTitle("Select number of players apart from the host");
        builder.setSingleChoiceItems(number, -1,  
            new DialogInterface.OnClickListener() {  
              @Override  
              public void onClick(DialogInterface dialog, int which) { 
           players=which;
                Toast.makeText(CreateHost.this,"waiting for " + number[which] + " players to join...",  Toast.LENGTH_LONG).show();
                players++;
                dialog.dismiss();  
              }  
            });  
        builder.setCancelable(false);
        AlertDialog alert = builder.create();  
        alert.show();          
        */
        
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.seekbar, (ViewGroup) findViewById(R.layout.activity_create_host), false);
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
        .setView(layout);
        builder.setCancelable(false);
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
        SeekBar sb = (SeekBar)layout.findViewById(R.id.seekBar1);
        final TextView t = (TextView) layout.findViewById(R.id.textViewSeeker);
        final TextView t2= (TextView) layout.findViewById(R.id.textView1);
        final TextView t3= (TextView) layout.findViewById(R.id.textView2);
        
        Button b= (Button) layout.findViewById(R.id.button1);
         
        t2.setTypeface(typeFace);
		t3.setTypeface(typeFace);
        b.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				
				alertDialog.dismiss();
				Toast.makeText(getBaseContext(), "waiting for "+ Integer.toString(players-1)+" players to join", Toast.LENGTH_SHORT).show();
				new Thread(new Runnable() {

					@Override
					public void run() {
						
						try {
							//Create a server socket object and bind it to a port
							ServerSocket socServer = new ServerSocket(SERVER_PORT);
							//Create server side client socket reference
							Socket socClient = null;
							//Infinite loop will listen for client requests to connect
							while (true) {
								//Accept the client connection and hand over communication to server side client socket
								socClient = socServer.accept();
								//For each client new instance of AsyncTask will be created
								ServerAsyncTask serverAsyncTask = new ServerAsyncTask();
								//Start the AsyncTask execution 
								//Accepted client socket object will pass as the parameter
								serverAsyncTask.execute(new Socket[] {socClient});
								
							}
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}).start();
			}
        	
        });
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
                
            	players=progress+2;
            	t.setText(Integer.toString(progress+1));
            }

			@Override
			public void onStartTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onStopTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub
				
			}
        });
        
        
        TextView textView1=(TextView) findViewById(R.id.textView1);
        textView2 = (TextView) findViewById(R.id.textView2);
        
        textView1.setTypeface(typeFace);
		textView2.setTypeface(typeFace);
		
        prev+= textView2.getText();
        wifiApManager = new WifiApManager(this);
 
        Context context=this;
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);    
        if(wifiManager.isWifiEnabled())
        {
            wifiManager.setWifiEnabled(false);          
        }       

                
        Method[] wmMethods = wifiManager.getClass().getDeclaredMethods();   //Get all declared methods in WifiManager class     
        boolean methodFound=false;
        for(Method method: wmMethods){
            if(method.getName().equals("setWifiApEnabled")){
                methodFound=true;
      
                netConfig.SSID = "\""+"CLUE"+"\"";
              
                netConfig.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
                
                try {
                    boolean apstatus=(Boolean) method.invoke(wifiManager, netConfig,true);          
                    for (Method isWifiApEnabledmethod: wmMethods)
                    {
                        if(isWifiApEnabledmethod.getName().equals("isWifiApEnabled")){
                            while(!(Boolean)isWifiApEnabledmethod.invoke(wifiManager)){
                            };
                            for(Method method1: wmMethods){
                                if(method1.getName().equals("getWifiApState")){
                                    int apstate;
                                    apstate=(Integer)method1.invoke(wifiManager);
                                }
                            }
                        }
                    }
                    if(apstatus)
                    {
                        System.out.println("SUCCESS");  

                    }else
                    {
                        System.out.println("FAILED");   

                    }

                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }      
        }
        
        
//        scan();
               
        
    }
/*
		private void scan() {
			wifiApManager.getClientList(false, new FinishScanListener() {

				@Override
				public void onFinishScan(final ArrayList<ClientScanResult> clients) {

					textView1.setText("WifiApState: " + wifiApManager.getWifiApState() + "\n\n");
					textView1.append("Clients: \n");
					for (ClientScanResult clientScanResult : clients) {
						textView1.append("####################\n");
						textView1.append("IpAddr: " + clientScanResult.getIpAddr() + "\n");
						textView1.append("Device: " + clientScanResult.getDevice() + "\n");
						textView1.append("HWAddr: " + clientScanResult.getHWAddr() + "\n");
						textView1.append("isReachable: " + clientScanResult.isReachable() + "\n");
					
					
					}
				}
			});
		
					
		}
*/
		
		/*
		@Override
		public boolean onCreateOptionsMenu(Menu menu) {
			menu.add(0, 0, 0, "Get Clients");
			menu.add(0, 1, 0, "Open AP");
			menu.add(0, 2, 0, "Close AP");
			return super.onCreateOptionsMenu(menu);
		}

		public boolean onMenuItemSelected(int featureId, MenuItem item) {
			switch (item.getItemId()) {
			case 0:
//				scan();
				break;
			case 1:
				wifiApManager.setWifiApEnabled(netConfig, true);
				break;
			case 2:
				wifiApManager.setWifiApEnabled(netConfig, false);
				break;
			}

			return super.onMenuItemSelected(featureId, item);
		}
    
    */
    
		class ServerAsyncTask extends AsyncTask<Socket, Void, String> {
			//Background task which serve for the client
			
			@Override
			protected String doInBackground(Socket... params) {
				String result = null;
				//Get the accepted socket object 
				Socket mySocket = params[0];
				try {
					ObjectInputStream in=new ObjectInputStream(mySocket.getInputStream());
					NameIpArray temp= (NameIpArray)in.readObject();
					
					int flag=0;
					
					for(int i=0;i<nameip1.size();i++)
					{
						if(nameip1.get(i).name.get(0).compareTo(temp.name.get(0))==0)
						{
							nameip1.remove(i);
							flag=1;
							break;
						}
					}
					
					if(flag==0)
					nameip1.add(temp);
					
					
					
					PrintWriter out = new PrintWriter(
							mySocket.getOutputStream(), true);
					//Write data to the data output stream
					out.println(message);
					//Buffer the data input stream
						
					//Close the client connection
					mySocket.close();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				return result;
			}

			@Override
			protected void onPostExecute(String s) {
				//After finishing the execution of background task data will be write the text view
				
				if(nameip1.size()>0)
				{
					for(int i=0;i<nameip1.size();i++)
					prev+="\nâ"+nameip1.get(i).name.get(0)+"!joinedã";
				}
				else
					prev="";
				
				textView2.setText(prev);
				p.setProgress((int)nameip1.size()*100/(players-1));
				
				if(nameip1.size()==players-1)
				{
				Intent intent = new Intent(CreateHost.this, GamePlay.class);
									
				for(int i=0;i<nameip1.size();i++)
				{
				
				intent.putExtra("ip"+Integer.toString(i) , nameip1.get(i).ip.get(0));
				
				intent.putExtra("name"+Integer.toString(i) , nameip1.get(i).name.get(0));
				}
				intent.putExtra("players", Integer.toString(nameip1.size()));
				intent.putExtra("ishost", Integer.toString(1));
				intent.putExtra("name", name);
				startActivity(intent);
				finish();
				}
				
			}
		}
		
		
		public void unbindDrawables(View view) {//pass your parent view here
	        try {
	            if (view.getBackground() != null)
	                view.getBackground().setCallback(null);

	            if (view instanceof ImageView) {
	                ImageView imageView = (ImageView) view;
	                imageView.setImageBitmap(null);
	            } else if (view instanceof ViewGroup) {
	                ViewGroup viewGroup = (ViewGroup) view;
	                for (int i = 0; i < viewGroup.getChildCount(); i++)
	                    unbindDrawables(viewGroup.getChildAt(i));

	                if (!(view instanceof AdapterView))
	                    viewGroup.removeAllViews();
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
		
		@Override
		public void onBackPressed() {
		}
		
		@Override
		public void onDestroy() {
		    super.onDestroy();
		    unbindDrawables(findViewById(R.id.createhost));
		    System.gc();
		    
		    
		}
		
}

