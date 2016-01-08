package coms.whodunnit;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import coms.whodunnit.CreateHost.ServerAsyncTask;



import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class EnterName extends Activity { 

	private Handler mHandler = new Handler();
	
	static int received=0;
	private final int SERVER_PORT = 8080; //Define the server port
	CountDownTimer t;
	private EditText name;
	private TextView textview2,textviewnew,textview3;
	String message;
	private boolean mContentLoaded;
	private View mContentView;
	private View mLoadingView;
	private int mShortAnimationDuration;
	public String what;
	NameIpArray nameip,nameip1;
	EditText edit;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Intent intent=getIntent();
		what=intent.getStringExtra("from");
		
		
		setContentView(R.layout.activity_enter_name);
		name=(EditText) findViewById(R.id.editText1);
		textview2 = (TextView) findViewById(R.id.textView2);
		textview3 = (TextView) findViewById(R.id.textView3);
		
		textviewnew = (TextView) findViewById(R.id.textViewNew);
		
		edit=(EditText) findViewById(R.id.editText1);
		
		TextView textview1=(TextView)findViewById(R.id.textView1);
		Button b1=(Button)findViewById(R.id.button1);
	
		
		Typeface typeFace=Typeface.createFromAsset(this.getAssets(),"fonts/yorkwhiteletter.ttf");
		
		textview1.setTypeface(typeFace);
		textview2.setTypeface(typeFace);
		textview3.setTypeface(typeFace);
		
		textviewnew.setTypeface(typeFace);
		
		
		nameip1=new NameIpArray();
		
		mContentView = findViewById(R.id.content);
        mLoadingView = findViewById(R.id.loading_spinner); 
                
     // Initially hide the content view.
        mLoadingView.setVisibility(View.GONE);
        textview2.setVisibility(View.GONE);
		
        // Retrieve and cache the system's default "short" animation time.
        mShortAnimationDuration = getResources().getInteger(android.R.integer.config_shortAnimTime);

      if(what.compareTo("join")==0)
      {  
    	  
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
            
              
              	    break;
                  }
                  catch (Exception e) {
                      e.printStackTrace();
                  }

              }           
              
          }

    	  
    	  
        new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					//Create a server socket object and bind it to a port
					ServerSocket socServer = new ServerSocket(8081);
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
		}

	/*
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.enter_name, menu);
		return true;
	}
*/
	public void connect(View view)
	{
		
		if(edit.getText().length()==0)
		Toast.makeText(this,"name can't be left empty", Toast.LENGTH_SHORT).show();
		else
		{
	
			
        if(what.compareTo("host")==0)
        {
      
            mContentView.animate()
            .alpha(0f)
            .setDuration(mShortAnimationDuration)
            .setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mContentView.setVisibility(View.GONE);
                }
            });

            mLoadingView.animate()
            .alpha(1f)
            .setDuration(mShortAnimationDuration)
            .setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoadingView.setVisibility(View.VISIBLE);
                    Intent intent1= new Intent(EnterName.this,CreateHost.class);
                	intent1.putExtra("name", name.getText().toString().replaceAll("\\s",""));
                	startActivity(intent1);
                	finish();
                	
                }
            });
 
        	
        }
        
        else
        {
		showContentOrLoadingIndicator(mContentLoaded);
        
		try {
			//Loop through all the network interface devices
			for (Enumeration<NetworkInterface> enumeration = NetworkInterface
					.getNetworkInterfaces(); enumeration.hasMoreElements();) {
				NetworkInterface networkInterface = enumeration.nextElement();
				//Loop through all the ip addresses of the network interface devices
				for (Enumeration<InetAddress> enumerationIpAddr = networkInterface.getInetAddresses(); enumerationIpAddr.hasMoreElements();) {
					InetAddress inetAddress = enumerationIpAddr.nextElement();
					//Filter out loopback address and other irrelevant ip addresses 
					if (!inetAddress.isLoopbackAddress() && inetAddress.getAddress().length == 4) {
						//Print the device ip address in to the text view 
						message=inetAddress.getHostAddress();
					}
				}
			}
		} catch (SocketException e) {
			Log.e("ERROR:", e.toString());
		}
		
		//Create an instance of AsyncTask
		ClientAsyncTask clientAST = new ClientAsyncTask();
		//Pass the server ip, port and client message to the AsyncTask
		clientAST.execute(new String[] { "192.168.43.1", "8080",name.getText().toString().replaceAll("\\s",""),message});
		mContentLoaded = !mContentLoaded;
		
		
		 }
        
		}
	}
	
	class ClientAsyncTask extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... params) {
			String result = null;
			System.out.println(params[0]+" "+params[1]+" "+params[2]+" "+params[3]);
			nameip1.name=new ArrayList<String>();
			nameip1.ip=new ArrayList<String>();
			
			try {
				
				Socket socket = new Socket(params[0],
						Integer.parseInt(params[1]));
				nameip1.name.add(params[2]);
				nameip1.ip.add(params[3]);
				
				InputStream is = socket.getInputStream();
				ObjectOutputStream out=new ObjectOutputStream(socket.getOutputStream());
				out.writeObject(nameip1);
				
				BufferedReader br = new BufferedReader(
						new InputStreamReader(is));
				result = br.readLine();
				
				//Close the client socket
				socket.close();
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return result;
		}
		@Override
		protected void onPostExecute(String s) {
		
			if(s!=null)
			{	
			String[] ss=s.split(":");
			textviewnew.setText(ss[0]);
			textview2.setText(ss[1]);
			textview3.setText("à"+name.getText().toString().replaceAll("\\s","")+"á");
			received=1;
			}
			
		}
	}
	
	
	class ServerAsyncTask extends AsyncTask<Socket, Void, String> {
		//Background task which serve for the client
		@Override
		protected String doInBackground(Socket... params) {
			String result = null;
			//Get the accepted socket object 
			Socket mySocket = params[0];
			try {
				ObjectInputStream in = new ObjectInputStream(mySocket.getInputStream());
				nameip=(NameIpArray)in.readObject();	
				
				PrintWriter out = new PrintWriter(
						mySocket.getOutputStream(), true);
				out.println("received");
				Intent intent=new Intent (EnterName.this,GamePlay.class);
				intent.putExtra("nameip", nameip);
				intent.putExtra("ishost","0");
				startActivity(intent);
				
				//Close the client connection
				mySocket.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return result;
		}

		@Override
		protected void onPostExecute(String s) {
		
			finish();
			
		}
	}
	
	@SuppressLint("NewApi")
	private void showContentOrLoadingIndicator(boolean contentLoaded) {
        // Decide which view to hide and which to show.
        final View showView = contentLoaded ? mContentView : mLoadingView;
        final View hideView = contentLoaded ? mLoadingView : mContentView;
        
        // Set the "show" view to 0% opacity but visible, so that it is visible
        // (but fully transparent) during the animation.
        showView.setAlpha(0f);
        showView.setVisibility(View.VISIBLE);
        textview2.setAlpha(0f);
        textview2.setVisibility(View.VISIBLE);
        textview3.setAlpha(0f);
        textview3.setVisibility(View.VISIBLE);
        textviewnew.setAlpha(0f);
        textviewnew.setVisibility(View.VISIBLE);
        
        
        // Animate the "show" view to 100% opacity, and clear any animation listener set on
        // the view. Remember that listeners are not limited to the specific animation
        // describes in the chained method calls. Listeners are set on the
        // ViewPropertyAnimator object for the view, which persists across several
        // animations.
        showView.animate()
                .alpha(1f)
                .setDuration(mShortAnimationDuration)
                .setListener(null);

        textview2.animate()
        .alpha(1f)
        .setDuration(mShortAnimationDuration)
        .setListener(null);

        textview3.animate()
        .alpha(1f)
        .setDuration(mShortAnimationDuration)
        .setListener(null);

        
        textviewnew.animate()
        .alpha(1f)
        .setDuration(mShortAnimationDuration)
        .setListener(null);

        
        // Animate the "hide" view to 0% opacity. After the animation ends, set its visibility
        // to GONE as an optimization step (it won't participate in layout passes, etc.)
        hideView.animate()
                .alpha(0f)
                .setDuration(mShortAnimationDuration)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        hideView.setVisibility(View.GONE);
                    }
                });
       
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
	public void onBackPressed()
	{
		if(received==1)
		{
			ClientAsyncTask clientAST = new ClientAsyncTask();
			clientAST.execute(new String[] { "192.168.43.1", "8080",name.getText().toString().replaceAll("\\s",""),message});
			
		}	
		super.onBackPressed();
	}
	
	@Override
	public void onDestroy() {
	    super.onDestroy();
	    unbindDrawables(findViewById(R.id.entername));
	    System.gc();
	}
	
}
