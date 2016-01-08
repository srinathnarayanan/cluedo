package coms.whodunnit;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import coms.myfirstapp.cardsui.CardStack;
import coms.myfirstapp.cardsui.CardUI;
import coms.whodunnit.DFragment;
import coms.whodunnit.CreateHost.ServerAsyncTask;
import coms.whodunnit.EnterName.ClientAsyncTask;
import coms.whodunnit.MyImageView.RectArea;




import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.Typeface;
import android.graphics.Bitmap.Config;
import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;



import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.Camera.Area;
import android.media.AudioManager;
import android.media.SoundPool;
import android.net.DhcpInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.Handler.Callback;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

@SuppressWarnings("deprecation")
public class GamePlay extends FragmentActivity {

	
	static String[] noting={"people","plum","mustard","white","peach","rose","scarlet","peacock","grey","brown","green","weapons","poison","candlestick","revolver","knife","rope","syringe","leadpipe","wrench","axe","bomb","rooms","billiardsroom","ballroom","library","study","conservatory","kitchen","dinning","hall","lounge"}; 
	
	static int[][] ids={
			{R.id.textView21,R.id.textView22,R.id.textView23,R.id.textView24,R.id.textView25,R.id.textView26,R.id.textView27,R.id.textView28,R.id.textView29 },
			{R.id.textView31,R.id.layout32,R.id.layout33,R.id.layout34,R.id.layout35,R.id.layout36,R.id.layout37,R.id.layout38,R.id.layout39},
			{R.id.textView41,R.id.layout42,R.id.layout43,R.id.layout44,R.id.layout45,R.id.layout46,R.id.layout47,R.id.layout48,R.id.layout49},
			{R.id.textView51,R.id.layout52,R.id.layout53,R.id.layout54,R.id.layout55,R.id.layout56,R.id.layout57,R.id.layout58,R.id.layout59},
			{R.id.textView61,R.id.layout62,R.id.layout63,R.id.layout64,R.id.layout65,R.id.layout66,R.id.layout67,R.id.layout68,R.id.layout69},
			{R.id.textView71,R.id.layout72,R.id.layout73,R.id.layout74,R.id.layout75,R.id.layout76,R.id.layout77,R.id.layout78,R.id.layout79},
			{R.id.textView81,R.id.layout82,R.id.layout83,R.id.layout84,R.id.layout85,R.id.layout86,R.id.layout87,R.id.layout88,R.id.layout89},
			{R.id.textView91,R.id.layout92,R.id.layout93,R.id.layout94,R.id.layout95,R.id.layout96,R.id.layout97,R.id.layout98,R.id.layout99},
			{R.id.textView101,R.id.layout102,R.id.layout103,R.id.layout104,R.id.layout105,R.id.layout106,R.id.layout107,R.id.layout108,R.id.layout109},
			{R.id.textView111,R.id.layout112,R.id.layout113,R.id.layout114,R.id.layout115,R.id.layout116,R.id.layout117,R.id.layout118,R.id.layout119},
			{R.id.textView121,R.id.layout122,R.id.layout123,R.id.layout124,R.id.layout125,R.id.layout126,R.id.layout127,R.id.layout128,R.id.layout129},
			{R.id.textView131,R.id.textView132,R.id.textView133,R.id.textView134,R.id.textView135,R.id.textView136,R.id.textView137,R.id.textView138,R.id.textView139 },
			{R.id.textView141,R.id.layout142,R.id.layout143,R.id.layout144,R.id.layout145,R.id.layout146,R.id.layout147,R.id.layout148,R.id.layout149},
			{R.id.textView151,R.id.layout152,R.id.layout153,R.id.layout154,R.id.layout155,R.id.layout156,R.id.layout157,R.id.layout158,R.id.layout159},
			{R.id.textView161,R.id.layout162,R.id.layout163,R.id.layout164,R.id.layout165,R.id.layout166,R.id.layout167,R.id.layout168,R.id.layout169},
			{R.id.textView171,R.id.layout172,R.id.layout173,R.id.layout174,R.id.layout175,R.id.layout176,R.id.layout177,R.id.layout178,R.id.layout179},
			{R.id.textView181,R.id.layout182,R.id.layout183,R.id.layout184,R.id.layout185,R.id.layout186,R.id.layout187,R.id.layout188,R.id.layout189},
			{R.id.textView191,R.id.layout192,R.id.layout193,R.id.layout194,R.id.layout195,R.id.layout196,R.id.layout197,R.id.layout198,R.id.layout199},
			{R.id.textView201,R.id.layout202,R.id.layout203,R.id.layout204,R.id.layout205,R.id.layout206,R.id.layout207,R.id.layout208,R.id.layout209},
			{R.id.textView211,R.id.layout212,R.id.layout213,R.id.layout214,R.id.layout215,R.id.layout216,R.id.layout217,R.id.layout218,R.id.layout219},
			{R.id.textView221,R.id.layout222,R.id.layout223,R.id.layout224,R.id.layout225,R.id.layout226,R.id.layout227,R.id.layout228,R.id.layout229},
			{R.id.textView231,R.id.layout232,R.id.layout233,R.id.layout234,R.id.layout235,R.id.layout236,R.id.layout237,R.id.layout238,R.id.layout239},
			{R.id.textView241,R.id.textView242,R.id.textView243,R.id.textView244,R.id.textView245,R.id.textView246,R.id.textView247,R.id.textView248,R.id.textView249 },
			{R.id.textView251,R.id.layout252,R.id.layout253,R.id.layout254,R.id.layout255,R.id.layout256,R.id.layout257,R.id.layout258,R.id.layout259},
			{R.id.textView261,R.id.layout262,R.id.layout263,R.id.layout264,R.id.layout265,R.id.layout266,R.id.layout267,R.id.layout268,R.id.layout269},
			{R.id.textView271,R.id.layout272,R.id.layout273,R.id.layout274,R.id.layout275,R.id.layout276,R.id.layout277,R.id.layout278,R.id.layout279},
			{R.id.textView281,R.id.layout282,R.id.layout283,R.id.layout284,R.id.layout285,R.id.layout286,R.id.layout287,R.id.layout288,R.id.layout289},
			{R.id.textView291,R.id.layout292,R.id.layout293,R.id.layout294,R.id.layout295,R.id.layout296,R.id.layout297,R.id.layout298,R.id.layout299},
			{R.id.textView301,R.id.layout302,R.id.layout303,R.id.layout304,R.id.layout305,R.id.layout306,R.id.layout307,R.id.layout308,R.id.layout309},
			{R.id.textView311,R.id.layout312,R.id.layout313,R.id.layout314,R.id.layout315,R.id.layout316,R.id.layout317,R.id.layout318,R.id.layout319},
			{R.id.textView321,R.id.layout322,R.id.layout323,R.id.layout324,R.id.layout325,R.id.layout326,R.id.layout327,R.id.layout328,R.id.layout329},
			{R.id.textView331,R.id.layout332,R.id.layout333,R.id.layout334,R.id.layout335,R.id.layout336,R.id.layout337,R.id.layout338,R.id.layout339}};
	
	
	static Button declare;
	static int mShortAnimationDuration;

	String hostname;
	static Integer[] images1 = {R.drawable.plumicon, R.drawable.peacockicon, R.drawable.brownicon,R.drawable.whiteicon, R.drawable.greenicon,R.drawable.mustardicon, R.drawable.scarleticon,R.drawable.peachicon, R.drawable.roseicon,R.drawable.greyicon };
	static Integer[] images2 = {R.drawable.leadpiperound, R.drawable.kniferound, R.drawable.revolverround,R.drawable.poisonround, R.drawable.candlestickround,R.drawable.syringeround, R.drawable.wrenchround,R.drawable.roperound, R.drawable.axeround,R.drawable.bombround};
	static Integer[] images3=new Integer[10];
	
	static ArrayList<Integer> yourimages=new ArrayList<Integer>();
	static Spinner dropdown4,dropdown5;
	
	
    static ImageView dice_picture1,dice_picture2;		//reference to dice picture 
	static Random rng=new Random();	//generate random numbers
	static SoundPool dice_sound = new SoundPool(1,AudioManager.STREAM_MUSIC,0);
	static int sound_id;		//Used to control sound stream return by SoundPool
	static Handler handler;	//Post message to start roll
	static Timer timer=new Timer();	//Used to implement feedback to user
	static boolean rolling=false;		//Is die rolling?
	static int movecount=0;
	public static TouchImageView tiv;	
	public static Bitmap bitmap;
	 public static Bitmap bmOverlay; 
	 static BitmapFactory.Options opts = new BitmapFactory.Options();
	 static int imageid;
	 static int[][] removable= new int [33][10];
	public static int iforsend,jforsend,xforsend,yforsend;
	public static int dieclick=1;

	static CardUI mCardView;
	static int[] brunettepos={571,1757};
	static int[] plumpos={126,483};
	static int[] peachpos={1401,1756};
	static int[] greenpos={799,1908};
	static int[] scarletpos={1311,104};
	static int[] peacockpos={119,1453};
	static int[] mustardpos={1845,616};
	static int[] rosepos={644,107};
	static int[] whitepos={1179,1908};
	static int[] graypos={1858,1375};
	static ArrayList<Bitmap> icon=new ArrayList<Bitmap>();
	static ArrayList<Integer> isin=new ArrayList<Integer>();
	
	static LinearLayout[][] linears=new LinearLayout[33][10];
	static ArrayList<String> playingplayers=new ArrayList<String>();
	int ppsize=0;
	int playertoplay=-1;
	Context context=this;
	static LinearLayout myGallery1;
	static TextView logs;
	static String chosencharacter=new String("");
	static ArrayList<String> yournames=new ArrayList<String>();
	static ArrayList<String> yourrooms=new ArrayList<String>();
	static ArrayList<String> yourweapons=new ArrayList<String>();
	static int currentplayer=-1;
	static TextView t1;
	static DatagramSocket socket;
	UDPClientAsyncTask u=new UDPClientAsyncTask();
	static boolean sentt=false;
	static String qname,finalname;
	static String qweapon,finalweapon;
	FragmentManager f= this.getSupportFragmentManager();
	static String sent ;
	byte[] received = new byte[30];
	
	CardsSectionFragment frag1;
	ClueBoardSectionFragment frag2;
	NotesSectionFragment frag3;
	static UDPServerAsyncTask serve;
	
	static int sendmessagetype=0;
	static int players;
	int ishost;
	static int result=0;
	static int yournumber;
	public static ArrayList<String> name=new ArrayList<String>();
	public static ArrayList<String> ip=new ArrayList<String>();
	public static String[] characters={"plum","peacock","brown","white","green","mustard","scarlet","peach","rose","grey"};
	public static String[] rooms={"conservatory","ballroom","billiardsroom","library","kitchen","dinning","lounge","study","hall"};
	public static String[] weapons={"leadpipe","knife","revolver","poison","candlestick","syringe","wrench","rope","axe","bomb"};
	public static ArrayList<ArrayList<String>> alloted = new ArrayList<ArrayList<String>>();
	public static String selectedweapon=new String();
	public static String selectedroom=new String();
	public static String selectedperson=new String();
	String yourip;
	static String prev;
	static String s;
	static NameIpArray nameip,nameiptosend;
	static InetAddress broadcastAdd;
	NewThread thread1;
	static String room=new String();
	
	static int height;
	static int width;

	SectionsPagerAdapter mSectionsPagerAdapter;
	static ViewPagerParallax mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_play);
		 
		
		Intent intent2=getIntent();
		if(intent2!=null)
		{
			String result=intent2.getStringExtra("EXIT");
			if(result!=null)
				{
				finish();
				System.exit(0);
				}
		}
		
		mShortAnimationDuration= getResources().getInteger(android.R.integer.config_shortAnimTime);
	
		DisplayMetrics metrics = context.getResources().getDisplayMetrics();
		width = metrics.widthPixels;
		
		opts.inScaled = false;	
		serve=new UDPServerAsyncTask();
		try
		{
			
			socket = new DatagramSocket(5000);
			socket.setBroadcast(true);
		
			WifiManager wifi = (WifiManager) this.getSystemService(Context.WIFI_SERVICE);
		
	    DhcpInfo dhcp = wifi.getDhcpInfo();
	    // handle null somehow

	    int broadcast = (dhcp.ipAddress & dhcp.netmask) | ~dhcp.netmask;
	    byte[] quads = new byte[4];
	    for (int k = 0; k < 4; k++)
	      quads[k] = (byte) ((broadcast >> k * 8) & 0xFF);
	     broadcastAdd= InetAddress.getByAddress(quads);
		}
		catch(Exception e)
		{
			
		}
		
	thread1=new NewThread();
	thread1.start();
	

		/*
		Thread t2=new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					while (true) {
						UDPServerAsyncTask UDPserverAsyncTask = new UDPServerAsyncTask();
						if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
							UDPserverAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
						    else
						    UDPserverAsyncTask.execute();
									
						
					}
					
					}
				 catch (Exception e) {
					e.printStackTrace();
				}
			}
		});	
		*/
			
			
		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPagerParallax) findViewById(R.id.pager);
		mViewPager.setOffscreenPageLimit(2);
		mViewPager.set_max_pages(3);
	    mViewPager.setBackgroundAsset(R.drawable.backgroun);
	    mViewPager.setAdapter(mSectionsPagerAdapter);
		
		
		
		
		
		Intent intent = getIntent();
		ishost=Integer.parseInt(intent.getStringExtra("ishost"));
		
		if(ishost==1)
		{
		
		Intent intent1=getIntent();	
		hostname=intent1.getStringExtra("name");
		
		players=Integer.parseInt(intent.getStringExtra("players"));
		System.out.println("p="+players);
		name.add(hostname);
		ip.add("192.168.43.1");
		
		for(int i=0;i<players;i++)
		{
			name.add(intent.getStringExtra("name"+Integer.toString(i)));
			ip.add(intent.getStringExtra("ip"+Integer.toString(i)));
			System.out.println(name.get(i)+" "+ip.get(i));
		}
		
		players++;
	randomallot();	
	yourip="192.168.43.1";
	nameip=new NameIpArray(players,selectedperson,selectedroom,selectedweapon,name,ip,alloted);	
	
	ClientAsyncTask clientAST = new ClientAsyncTask();
	//Pass the server ip, port and client message to the AsyncTask
	clientAST.execute(nameip);
		
	}
	
	
	else
	{
		
	nameip =(NameIpArray) intent.getSerializableExtra("nameip");	
	players= nameip.players;
	name.addAll(nameip.name);
	ip.addAll(nameip.ip);
	alloted.addAll(nameip.alloted);	
	selectedperson = nameip.selectedperson;
	selectedweapon=nameip.selectedweapon;
	selectedroom=nameip.selectedroom;
	
	
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
						yourip=inetAddress.getHostAddress();
					}
				}
			}
		} catch (SocketException e) {
			Log.e("ERROR:", e.toString());
		}
	
	}	
	
		for(int i=0;i<ip.size();i++)
		{
			if(ip.get(i).compareTo(yourip)==0)
			{
				yournumber=i;
				break;
			}
		}
		
		for(int i=0;i<players;i++)
		{
			playingplayers.add(new String());
			
			isin.add(0);
		}
		
		
	}

	
	/*
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game_play, menu);
		
		return true;
	}

	
	*/
	
	
	
	public int randInt(int min, int max) {

	    Random rand = new Random();
	    int randomNum = rand.nextInt((max - min) + 1) + min;

	    return randomNum;
	}
	
	
	
	
	public void randomallot()
	{
		int i,j,k;

		ArrayList<String> c=new ArrayList<String>();
		ArrayList<String> r=new ArrayList<String>();
		ArrayList<String> w=new ArrayList<String>();
		ArrayList<String> combined=new ArrayList<String>();
		
		for(i=0;i<characters.length;i++)
		c.add(characters[i]);	
		for(i=0;i<rooms.length;i++)
		r.add(rooms[i]);	
		for(i=0;i<weapons.length;i++)
		w.add(weapons[i]);	
				
		
		int choice=randInt(0,9);
		selectedperson=c.get(choice);
		c.remove(choice);

		choice=randInt(0,8);
		selectedroom=r.get(choice);
		r.remove(choice);
		
		choice=randInt(0,9);
		selectedweapon=w.get(choice);
		w.remove(choice);

		ArrayList<String> one=new ArrayList<String>();
		ArrayList<String> two=new ArrayList<String>();
		ArrayList<String> three=new ArrayList<String>();
		ArrayList<String> four=new ArrayList<String>();
		ArrayList<String> five=new ArrayList<String>();
		ArrayList<String> six=new ArrayList<String>();
		ArrayList<String> seven=new ArrayList<String>();
		ArrayList<String> eight=new ArrayList<String>();
		ArrayList<String> nine=new ArrayList<String>();
		
		alloted.add(one);
		alloted.add(two);
		alloted.add(three);
		alloted.add(four);
		alloted.add(five);
		alloted.add(six);
		alloted.add(seven);
		alloted.add(eight);
		alloted.add(nine);
		
		for(i=0;i<players;i++)
		{
		k=randInt(0,c.size()-1);
		alloted.get(i).add(c.get(k));
		c.remove(k);			
		}
		
		for(i=0;i<w.size();i++)
		combined.add(w.get(i));

		for(i=0;i<c.size();i++)
		combined.add(c.get(i));

		for(i=0;i<r.size();i++)
		combined.add(r.get(i));


	while(true)
	{
	int flag=0;
	for(i=0;i<players;i++)
		{
		k=randInt(0,combined.size()-1);
		alloted.get(i).add(combined.get(k));
		combined.remove(k);			
		if(combined.size()==0)
		{
			flag=1;
			break;
		}
		}
	if(flag==1)
	break;

	}
		
	}
	
	
	class ClientAsyncTask extends AsyncTask<NameIpArray, Void, String> {
		@Override
		protected String doInBackground(NameIpArray... params) {
			String result = null;
			
			try {
				//Create a client socket and define internet address and the port of the server
				
				for(int i=1;i<params[0].ip.size();i++)
				{
				Socket socket = new Socket(params[0].ip.get(i),8081);
				InputStream is = socket.getInputStream();
				ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
				out.writeObject(params[0]);	
				BufferedReader br = new BufferedReader(new InputStreamReader(is));
				result = br.readLine();
				
				//Close the client socket
				socket.close();
				}
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
			//Write server message to the text view
		//	textview2.setText(s);
			
				
			
		}
	}
	
	
	
	
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		FragmentManager fragm;
		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
			fragm=fm;
			
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a DummySectionFragment (defined as a static inner class
			// below) with the page number as its lone argument.
			
			switch(position) {
	        case 0:
	        	
	            return new CardsSectionFragment();
	        case 1:
	        	
	        	return new ClueBoardSectionFragment();
	        case 2:
	        	return new NotesSectionFragment();
	        default:
	            return null;
	    }
			
		}

	
		
		
		@Override
		public int getCount() {
			// Show 3 total pages.
			return 3;
		}

		
		
		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.title_section1).toUpperCase(l);
			case 1:
				return getString(R.string.title_section2).toUpperCase(l);
			case 2:
				return getString(R.string.title_section3).toUpperCase(l);
			}
			return null;
		}
	}
	
	
	
	
	

	public static class ClueBoardSectionFragment extends Fragment{
		
		TextView t;
		View rootView;
	
		
	 
	public ClueBoardSectionFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			
			rootView = inflater.inflate(R.layout.fragment_game_play,
					container, false);

			rootView.setBackgroundResource(R.drawable.backgroun);

			t = (TextView) rootView.findViewById(R.id.textView1);

		    declare=(Button) rootView.findViewById(R.id.declare);

		    Typeface typeFace=Typeface.createFromAsset(getActivity().getAssets(),"fonts/yorkwhiteletter.ttf");
		   
		    t.setTypeface(typeFace);
			
		    
		    
		    declare.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View arg0) {
					
					AlertDialog.Builder alertadd = new AlertDialog.Builder(getActivity());
					LayoutInflater factory = LayoutInflater.from(getActivity());
					final View view = factory.inflate(R.layout.declaration, null);
					
					TextView t1=(TextView) view.findViewById(R.id.textView1);
					TextView t3=(TextView) view.findViewById(R.id.textView3);
					TextView t4=(TextView) view.findViewById(R.id.textView4);
					
					Typeface typeFace=Typeface.createFromAsset(getActivity().getAssets(),"fonts/yorkwhiteletter.ttf");
					
					t1.setTypeface(typeFace);
					t3.setTypeface(typeFace);
					t4.setTypeface(typeFace);
					
					
					dropdown4=(Spinner) view.findViewById(R.id.spinnername);
				    dropdown5=(Spinner) view.findViewById(R.id.spinnerweapon);
					
				    dropdown4.setAdapter(new MyAdapter(getActivity(), R.layout.custom,characters,1));
				    dropdown5.setAdapter(new MyAdapter(getActivity(), R.layout.custom,weapons,2));
					  
				    
				    
				    dropdown4.setOnItemSelectedListener(new OnItemSelectedListener(){

						@Override
						public void onItemSelected(AdapterView<?> arg0, View arg1,
								int pos, long id) {
							finalname = dropdown4.getItemAtPosition(pos).toString();
							
						}

						@Override
						public void onNothingSelected(AdapterView<?> arg0) {
							// TODO Auto-generated method stub
							
						}
				    	
				    });

				    
				    dropdown5.setOnItemSelectedListener(new OnItemSelectedListener(){

						@Override
						public void onItemSelected(AdapterView<?> arg0, View arg1,
								int pos, long id) {
							finalweapon = dropdown5.getItemAtPosition(pos).toString();
							
						}

						@Override
						public void onNothingSelected(AdapterView<?> arg0) {
							// TODO Auto-generated method stub
							
						}
				    	
				    });

					
					
					
					alertadd.setView(view);
					alertadd.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
					                public void onClick(DialogInterface dlg, int sumthin) {

					                	dlg.dismiss();
					                	
					                }
					            });
					
					alertadd.setPositiveButton("OK", new DialogInterface.OnClickListener() {
		                public void onClick(DialogInterface dlg, int sumthin) {

		                	if(selectedroom.equals(room) && finalname.equals(selectedperson) && selectedweapon.equals(finalweapon))
		                	{
		                		sendmessagetype=4;	
		                		Intent intent=new Intent(getActivity(),GameOver.class);
		                		intent.putExtra("result", "yes");
		                		intent.putExtra("winner", name.get(yournumber));
		                		intent.putExtra("nameip", nameip);
		                		startActivity(intent);
		                		getActivity().finish();
		                	}
		                	else
		                	{
		                		sendmessagetype=5;
		                		Intent intent=new Intent(getActivity(),GameOver.class);
		                		intent.putExtra("result", "no");
		                		intent.putExtra("winner", "none");
		                		intent.putExtra("nameip", nameip);
		                		startActivity(intent);
		                		getActivity().finish();
		                	}
		                	
		                		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
		    					       serve.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
		    					    else
		    					       serve.execute();
		                		dlg.dismiss();
		                }
		            });
					
					if(isin.get(yournumber)>188)
					alertadd.show();
					
					else
						Toast.makeText(getActivity(), "you have to get to a room to declare", Toast.LENGTH_SHORT).show();
					
				}
		    	
		    	
		    });
			sound_id=dice_sound.load(getActivity(),R.raw.shake_dice,1);
			//get reference to image widget

			dice_picture1 = (ImageView) rootView.findViewById(R.id.die1);
	        dice_picture2 = (ImageView) rootView.findViewById(R.id.die2);
	        
	        dice_picture1.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View arg0) {
					
					if(dieclick==1)
					HandleClick(arg0);
				}
	        	
	        });
	        

	        dice_picture2.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View arg0) {
					
					if(dieclick==1)
					HandleClick(arg0);
				}
	        	
	        });
	        
	        

	        //link handler to callback
	        handler=new Handler(callback);
			
			
			bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.cluedobigedit,opts);
			bmOverlay = Bitmap.createBitmap(bitmap.getWidth(),
		            bitmap.getHeight(), Config.RGB_565);

			
			final int mShortAnimationDuration = getResources().getInteger(android.R.integer.config_shortAnimTime);

			
			tiv=(TouchImageView) rootView.findViewById(R.id.imageView1);			
			 
			if(chosencharacter.length()<2)
			{
				Toast.makeText(getActivity(), "please choose your character to proceed", Toast.LENGTH_LONG).show();
			}
			
			// Any implementation of ImageView can be used!
		
		   
		   
			return rootView;
		}

		public void update() {
	 t.setText("«welcomeå"+name.get(yournumber)+"»");
	 ImageView i=(ImageView)rootView.findViewById(R.id.top);	 
		 i.setImageResource(getResources().getIdentifier("coms.whodunnit:drawable/"+chosencharacter+"icon", null, null));
		 i.setVisibility(View.VISIBLE);
			
		}

	public void updateposition()
	{

		 int j=0;
		 Canvas canvas = new Canvas(bmOverlay);
	     canvas.drawARGB(0x00, 0, 0, 0);
	     canvas.drawBitmap(bitmap, 0, 0, null);
	     
		 /* set other image top of the first icon */
	     for(int i=0;i<players;i++)
	     {
		if(playingplayers.get(i).compareTo("brown")==0)
		{
	     icon.add( BitmapFactory.decodeResource(this
	             .getResources(), R.drawable.brownicon,opts));
	     isin.add(177);
	     
	     if(yournumber==i)
	     TouchImageView.characterarea=177;
	     
	     canvas.drawBitmap(Bitmap.createScaledBitmap(icon.get(i),70 ,70 , false), brunettepos[0],brunettepos[1], null);
		}
		
		else if(playingplayers.get(i).compareTo("peach")==0)
		{
	     icon.add(BitmapFactory.decodeResource(this
	             .getResources(), R.drawable.peachicon,opts));
	     isin.add(180);
	     
	     if(yournumber==i)   
	     TouchImageView.characterarea=180;
	     
	     canvas.drawBitmap(Bitmap.createScaledBitmap(icon.get(i),70 ,70 , false), peachpos[0],peachpos[1], null);
		}
		

		else if(playingplayers.get(i).compareTo("grey")==0)
		{
	     icon.add(BitmapFactory.decodeResource(this
	             .getResources(), R.drawable.greyicon,opts));
	     isin.add(153);
	     if(yournumber==i)
		     TouchImageView.characterarea=153;
	     
	     canvas.drawBitmap(Bitmap.createScaledBitmap(icon.get(i),70 ,70 , false), graypos[0],graypos[1], null);
		}
		
		else if(playingplayers.get(i).compareTo("rose")==0)
		{
	     icon.add(BitmapFactory.decodeResource(this
	             .getResources(), R.drawable.roseicon,opts));
	     isin.add(1);
	     
	     if(yournumber==i)
		     TouchImageView.characterarea=1;
	     
	     canvas.drawBitmap(Bitmap.createScaledBitmap(icon.get(i),70 ,70 , false), rosepos[0],rosepos[1], null);
		}
		
		else if(playingplayers.get(i).compareTo("white")==0)
		{
	     icon.add(BitmapFactory.decodeResource(this
	             .getResources(), R.drawable.whiteicon,opts));
	     isin.add(188);
	     
	     if(yournumber==i)
		     TouchImageView.characterarea=188;
	     
	     canvas.drawBitmap(Bitmap.createScaledBitmap(icon.get(i),70 ,70 , false), whitepos[0],whitepos[1], null);
		}
		
		else if(playingplayers.get(i).compareTo("green")==0)
		{
	     icon.add(BitmapFactory.decodeResource(this
	             .getResources(), R.drawable.greenicon,opts));
	     isin.add(187);
	     
	     if(yournumber==i)
		     TouchImageView.characterarea=187;
	     
	     canvas.drawBitmap(Bitmap.createScaledBitmap(icon.get(i),70 ,70 , false), greenpos[0],greenpos[1], null);
		}
		
		else if(playingplayers.get(i).compareTo("mustard")==0)
		{
	     icon.add(BitmapFactory.decodeResource(this
	             .getResources(), R.drawable.mustardicon,opts));
	     isin.add(63);
	     
	     if(yournumber==i)
		     TouchImageView.characterarea=63;
	     
	     canvas.drawBitmap(Bitmap.createScaledBitmap(icon.get(i),70 ,70 , false), mustardpos[0],mustardpos[1], null);
		}
		
		else if(playingplayers.get(i).compareTo("peacock")==0)
		{
	     icon.add(BitmapFactory.decodeResource(this
	             .getResources(), R.drawable.peacockicon,opts));
	     
	     isin.add(154);
	     
	     if(yournumber==i)
		     TouchImageView.characterarea=154;
	     
	     canvas.drawBitmap(Bitmap.createScaledBitmap(icon.get(i),70 ,70 , false), peacockpos[0],peacockpos[1], null);
		}
		
		else if(playingplayers.get(i).compareTo("scarlet")==0)
		{
	     icon.add( BitmapFactory.decodeResource(this
	             .getResources(), R.drawable.scarleticon,opts));
	     isin.add(2);
	     
	     if(yournumber==i)   
	     TouchImageView.characterarea=2;
	     
	     canvas.drawBitmap(Bitmap.createScaledBitmap(icon.get(i),70 ,70 , false), scarletpos[0],scarletpos[1], null);
		}
		
		else if(playingplayers.get(i).compareTo("plum")==0)
		{
	     icon.add(BitmapFactory.decodeResource(this
	             .getResources(), R.drawable.plumicon,opts));
	     isin.add(25);
	     
	     if(yournumber==i)    
	     TouchImageView.characterarea=25;
	     
	     canvas.drawBitmap(Bitmap.createScaledBitmap(icon.get(i),70 ,70 , false), plumpos[0],plumpos[1], null);
		}
		
	     
	    tiv.setImageBitmap(bmOverlay);

	    
	     }

				
	}

	public void show() {
		
		dieclick=1;
		
		dice_picture1.animate()
        .alpha(1f)
        .setDuration(mShortAnimationDuration)
        .setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                dice_picture1.setVisibility(View.VISIBLE);
            }
        });

		dice_picture2.animate()
        .alpha(1f)
        .setDuration(mShortAnimationDuration)
        .setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                dice_picture2.setVisibility(View.VISIBLE);
            }
        });
		if(isin.get(yournumber)>188)
		{


			declare.animate()
		        .alpha(1f)
		        .setDuration(mShortAnimationDuration)
		        .setListener(new AnimatorListenerAdapter() {
		            @Override
		            public void onAnimationEnd(Animator animation) {
		                declare.setVisibility(View.VISIBLE);
		            }
		        });

				

			CardsSectionFragment cb = (CardsSectionFragment)
	                getActivity().getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.pager + ":" + 0);   
		    
			if(cb!=null)
				cb.show();
		}
	}
	

	//User pressed dice, lets start
	public void HandleClick(View arg0) {
		if(!rolling) {
			rolling=true;
			//Show rolling image
			dice_picture1.setImageResource(R.drawable.dice3droll);
			dice_picture2.setImageResource(R.drawable.dice3droll);
			
			//Start rolling sound
			dice_sound.play(sound_id,1.0f,1.0f,0,0,1.0f);
			//Pause to allow image to update
			timer.schedule(new Roll(), 400);
			dieclick=0;
			
		}
	}

	//When pause completed message sent to callback
	class Roll extends TimerTask {
	    public void run() {
		    handler.sendEmptyMessage(0);
	    }
	}

	//Receives message from timer to start dice roll
	Callback callback = new Callback() {
	    public boolean handleMessage(Message msg) {
	    	//Get roll result
	    	//Remember nextInt returns 0 to 5 for argument of 6
	    	//hence + 1
	    	int rand=rng.nextInt(6)+1;
	    	movecount+=rand;
			switch(rand) {
	    	case 1:
	    		dice_picture1.setImageResource(R.drawable.one);
	    		break;
	    	case 2:
	    		dice_picture1.setImageResource(R.drawable.two);
	    		break;
	    	case 3:
	    		dice_picture1.setImageResource(R.drawable.three);
	    		break;
	    	case 4:
	    		dice_picture1.setImageResource(R.drawable.four);
	    		break;
	    	case 5:
	    		dice_picture1.setImageResource(R.drawable.five);
	    		break;
	    	case 6:
	    		dice_picture1.setImageResource(R.drawable.six);
	    		break;
	    	default:
			}
			rand=rng.nextInt(6)+1;
			movecount+=rand;
			switch(rand) {
	    	case 1:
	    		dice_picture2.setImageResource(R.drawable.one);
	    		break;
	    	case 2:
	    		dice_picture2.setImageResource(R.drawable.two);
	    		break;
	    	case 3:
	    		dice_picture2.setImageResource(R.drawable.three);
	    		break;
	    	case 4:
	    		dice_picture2.setImageResource(R.drawable.four);
	    		break;
	    	case 5:
	    		dice_picture2.setImageResource(R.drawable.five);
	    		break;
	    	case 6:
	    		dice_picture2.setImageResource(R.drawable.six);
	    		break;
	    	default:
			}
    	    rolling=false;	//user can press again
    	    Toast.makeText(getActivity(), movecount+" moves left", Toast.LENGTH_SHORT).show();
			TouchImageView.movesleft=movecount;
    	    return true;
	    }
	    
	};


	public class MyAdapter extends ArrayAdapter {

		int n;
		
		public MyAdapter(Context context, int textViewResourceId,String[] objects,int num) {
		super(context, textViewResourceId, objects);
		n=num;
		}

		public View getCustomView(int position, View convertView,
		ViewGroup parent) {

		// Inflating the layout for the custom Spinner
		LayoutInflater inflater = getActivity().getLayoutInflater();
		View layout = inflater.inflate(R.layout.custom, parent, false);

		// Declaring and Typecasting the textview in the inflated layout
		TextView tvLanguage = (TextView) layout.findViewById(R.id.tvLanguage);

		
		// Setting the color of the text
		tvLanguage.setTextColor(Color.rgb(255, 255, 255));

		// Declaring and Typecasting the imageView in the inflated layout
		ImageView img = (ImageView) layout.findViewById(R.id.imgLanguage);

		// Setting an image using the id's in the array
		if(n==1)
		{
			img.setImageResource(images1[position]);
			tvLanguage.setText(characters[position]);
			
		}
		else if(n==2)
		{	
			img.setImageResource(images2[position]);
			tvLanguage.setText(weapons[position]);
			
		}
		
		return layout;
		}

		// It gets a View that displays in the drop down popup the data at the specified position
		@Override
		public View getDropDownView(int position, View convertView,
		ViewGroup parent) {
		return getCustomView(position, convertView, parent);
		}

		// It gets a View that displays the data at the specified position
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
		return getCustomView(position, convertView, parent);
		}
	}

	
	
		
	}
	
	
	
	
	public static class CardsSectionFragment extends Fragment{
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		
		Context con=getActivity();
		View rootView;
		TextView textview,textview3,textview4;
		Spinner spinner1;
		Button button,buttonchoice;
		Spinner dropdown,dropdown2,dropdown3 ;
		   ArrayAdapter<String> adapter ;
		   ScrollView scroller;
			int mShortAnimationDuration ;
		public static final String ARG_SECTION_NUMBER = "section_number";

		public CardsSectionFragment() {
		
		}
		
		
		
		
		@SuppressWarnings("unchecked")
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			mShortAnimationDuration = getResources().getInteger(android.R.integer.config_shortAnimTime);

				rootView = inflater.inflate(R.layout.fragment_game_start,container, false);
		
				rootView.setBackgroundResource(R.drawable.backgroun);
			    
				mCardView = (CardUI) rootView.findViewById(R.id.cardsview);
			      mCardView.setSwipeable(false);
				
				
				
				
				textview=(TextView) rootView.findViewById(R.id.textView2);
				textview3=(TextView) rootView.findViewById(R.id.textView3);
				textview4=(TextView) rootView.findViewById(R.id.textView4);
			
				Typeface typeFace=Typeface.createFromAsset(getActivity().getAssets(),"fonts/yorkwhiteletter.ttf");
				
				textview.setTypeface(typeFace);
				textview3.setTypeface(typeFace);
				textview4.setTypeface(typeFace);
				
				
		    spinner1=(Spinner) rootView.findViewById(R.id.spinner1);
		    dropdown2=(Spinner) rootView.findViewById(R.id.spinnername);
		    dropdown3=(Spinner) rootView.findViewById(R.id.spinnerweapon);
			

			   dropdown2.setAdapter(new MyAdapter(getActivity(), R.layout.custom,characters,1));

			   dropdown3.setAdapter(new MyAdapter(getActivity(), R.layout.custom,weapons,2));
			   
		    
		    
		    dropdown2.setOnItemSelectedListener(new OnItemSelectedListener(){

				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,
						int pos, long id) {
					qname = dropdown2.getItemAtPosition(pos).toString();
					
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub
					
				}
		    	
		    });

		    
		    dropdown3.setOnItemSelectedListener(new OnItemSelectedListener(){

				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,
						int pos, long id) {
					qweapon = dropdown3.getItemAtPosition(pos).toString();
					
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub
					
				}
		    	
		    });

		    buttonchoice=(Button) rootView.findViewById(R.id.buttonchoice);
		    
		    buttonchoice.setOnClickListener(new OnClickListener(){

		    	
				@Override
				public void onClick(View arg0) {
					
					
					dice_picture1.animate()
			        .alpha(0f)
			        .setDuration(mShortAnimationDuration)
			        .setListener(new AnimatorListenerAdapter() {
			            @Override
			            public void onAnimationEnd(Animator animation) {
			                dice_picture1.setVisibility(View.INVISIBLE);
			            }
			        });

					dice_picture2.animate()
			        .alpha(0f)
			        .setDuration(mShortAnimationDuration)
			        .setListener(new AnimatorListenerAdapter() {
			            @Override
			            public void onAnimationEnd(Animator animation) {
			                dice_picture2.setVisibility(View.INVISIBLE);
			            }
			        });


					declare.animate()
			        .alpha(0f)
			        .setDuration(mShortAnimationDuration)
			        .setListener(new AnimatorListenerAdapter() {
			            @Override
			            public void onAnimationEnd(Animator animation) {
			                dice_picture1.setVisibility(View.INVISIBLE);
			            }
			        });


					
					sendmessagetype=2;
					if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
					       serve.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
					    else
					       serve.execute();
					
					
					final RelativeLayout rl=(RelativeLayout)rootView.findViewById(R.id.lay1);

					 rl.animate()
		             .alpha(0f)
		             .setDuration(mShortAnimationDuration)
		             .setListener(new AnimatorListenerAdapter() {
		                 @Override
		                 public void onAnimationEnd(Animator animation) {
		                     rl.setVisibility(View.GONE);
		                 }
		             });
					
					//textview3.setVisibility(View.GONE);
					//textview4.setVisibility(View.GONE);
					//dropdown2.setVisibility(View.GONE);
					//dropdown3.setVisibility(View.GONE);
					//buttonchoice.setVisibility(View.GONE);
				
				}
		    	
		    	
		    });
		    
		    button=(Button) rootView.findViewById(R.id.button1);
		    
					button.setOnClickListener(new OnClickListener(){

						@Override
						public void onClick(View v) {
					
							ClueBoardSectionFragment cb = (ClueBoardSectionFragment)
					                getActivity().getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.pager + ":" + 1);   
						    
								
							chosencharacter = spinner1.getSelectedItem().toString();
					playingplayers.set(yournumber,chosencharacter);
							if(cb!=null)
								cb.update();
						
							sendmessagetype=1;
							if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
							       serve.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
							    else
							       serve.execute();
							
							Toast.makeText(getActivity(), "you have chosen "+chosencharacter, Toast.LENGTH_LONG).show();
						    button.animate()
						    .alpha(0f)
						    .setDuration(mShortAnimationDuration)
						    .setListener(new AnimatorListenerAdapter() {
						        @Override
						        public void onAnimationEnd(Animator animation) {
						            button.setVisibility(View.GONE);
						        }
						    });
						    
						    textview.animate()
						    .alpha(0f)
						    .setDuration(mShortAnimationDuration)
						    .setListener(new AnimatorListenerAdapter() {
						        @Override
						        public void onAnimationEnd(Animator animation) {
						            textview.setVisibility(View.GONE);
						        }
						    });
						    spinner1.animate()
						    .alpha(0f)
						    .setDuration(mShortAnimationDuration)
						    .setListener(new AnimatorListenerAdapter() {
						        @Override
						        public void onAnimationEnd(Animator animation) {
						            spinner1.setVisibility(View.GONE);
						        }
						    });

							
						}
						
						
					});
					
				    		
			myGallery1 = (LinearLayout)rootView.findViewById(R.id.mygallery1);
			int lw=(int)(width/3.25);
			int lh=(int)(lw*1.29);

			  
		   for(int i=0;i<alloted.get(yournumber).size();i++)
		   {
			   if(Arrays.asList(characters).contains(alloted.get(yournumber).get(i)))
			   {
				   yournames.add(alloted.get(yournumber).get(i));
				   yourimages.add(getResources().getIdentifier("coms.whodunnit:drawable/"+alloted.get(yournumber).get(i)+"icon", null, null));
			   }
			   else if(Arrays.asList(rooms).contains(alloted.get(yournumber).get(i)))
			   {
				   yourrooms.add(alloted.get(yournumber).get(i));
			   }
			   else
				   yourweapons.add(alloted.get(yournumber).get(i));
			    
			   
				 myGallery1.addView(insertPhoto(alloted.get(yournumber).get(i)));
			     
		   } 
		   
		   dropdown = (Spinner)rootView.findViewById(R.id.spinner1);
		   
		   
		   images3=yourimages.toArray(new Integer[yourimages.size()]);
		   dropdown.setAdapter(new MyAdapter(getActivity(), R.layout.custom,yournames.toArray(new String[yournames.size()]),3));
		   
		 //  adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, yournames.toArray(new String[yournames.size()]));
		 //  dropdown.setAdapter(adapter);
		   
		   return rootView;
		}
		
		
			
			LinearLayout insertPhoto(String str){
		     s =str; 
		     
			int lw=(int)(width/3.25);
			int lh=(int)(lw*1.29);

			//int dp = (int) (getActivity().getResources().getDimension(R.dimen.gallery) / getActivity().getResources().getDisplayMetrics().density);
			
			Bitmap bm = decodeSampledBitmapFromUri("images/"+str+".jpg",lw-20,lh-20);

		     LinearLayout layout = new LinearLayout(getActivity().getApplicationContext());
		     layout.setLayoutParams(new LayoutParams(lw, lh));
		     layout.setGravity(Gravity.CENTER);
		     
		     ImageView imageView = new ImageView(getActivity().getApplicationContext());
		     imageView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		     imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
		     imageView.setImageBitmap(bm);
		     layout.addView(imageView);
		     
		     
		     return layout;
		    }
		    
				 
		   public Bitmap decodeSampledBitmapFromUri(String str,int reqWidth, int reqHeight) {
		  
		     Bitmap bm = null;
		     
		     // First decode with inJustDecodeBounds=true to check dimensions
		     final BitmapFactory.Options options = new BitmapFactory.Options();
		     options.inJustDecodeBounds = true;
		     
		     AssetManager assetManager = getActivity().getApplicationContext().getAssets();
		        InputStream istr = null;
		        try {
		            istr = assetManager.open(str);
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
		        Bitmap bitmap = BitmapFactory.decodeStream(istr, null, options);
		     
		     // Calculate inSampleSize
		     options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
		     
		     // Decode bitmap with inSampleSize set
		     options.inJustDecodeBounds = false;
		     bm = BitmapFactory.decodeStream(istr, null, options); 
		     
		     return bm;  
		    }
		   
		 Bitmap getBitmapFromAsset(String strName)
		    {
		        AssetManager assetManager = getActivity().getApplicationContext().getAssets();
		        InputStream istr = null;
		        try {
		            istr = assetManager.open(strName);
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
		        Bitmap bitmap = BitmapFactory.decodeStream(istr);
		        return bitmap;
		    }
		 
		 
		 public int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
			     // Raw height and width of image
			     final int height = options.outHeight;
			     final int width = options.outWidth;
			     int inSampleSize = 1;
			     
			     
			     if (height > reqHeight || width > reqWidth) {
			      if (width > height) {
			       inSampleSize = Math.round((float)height / (float)reqHeight);   
			      } else {
			       inSampleSize = Math.round((float)width / (float)reqWidth);   
			      }   
			     }
			     
			     			          
			     return inSampleSize;   
			    }




		public void show() {
			
			
			RelativeLayout rl=(RelativeLayout)rootView.findViewById(R.id.lay1);
			rl.animate()
            .alpha(1f)
            .setDuration(mShortAnimationDuration)
            .setListener(null);
			rl.setVisibility(View.VISIBLE);
			
			//textview3.setVisibility(View.VISIBLE);
			//textview4.setVisibility(View.VISIBLE);
			//dropdown2.setVisibility(View.VISIBLE);
			//dropdown3.setVisibility(View.VISIBLE);
			//buttonchoice.setVisibility(View.VISIBLE);
		}




		public void updatelog(String message) {
			mCardView.setVisibility(View.VISIBLE);
			String[] ss=message.split("\\r?\\n");
			MyPlayCard androidViewsCard = new MyPlayCard(ss[0],
    				ss[1], "#669900",
    				"#669900", false, false);	
		mCardView.addCard(androidViewsCard);
            
    		// draw cards
    		mCardView.refresh();
    
		}




		public void updatelog2(char charAt, int k) {
			
			int a=Integer.parseInt(Character.toString(charAt));
			mCardView.setVisibility(View.VISIBLE);
			String message;
			if(k==0)
			message="WRONG DECLARATION BY "+name.get(a);
			else
			message=name.get(a)+" QUIT THE GAME";
				
			String message2= name.get(a)+" had the following cards:\n";
		
			
			MyPlayCard androidViewsCard = new MyPlayCard(message,
    				message2, "#D80000",
    				"#D80000", false, false);	
		mCardView.addCard(androidViewsCard);
            
    		// draw cards
    		mCardView.refresh();
    
	
			for(int i=0;i<alloted.get(a).size();i++)
				{
				androidViewsCard = new MyPlayCard(alloted.get(a).get(i).toUpperCase(),
	    				"", "#F80000",
	    				"#F80000", true, false);	
			mCardView.addCard(androidViewsCard);
	            
	    		// draw cards
	    		mCardView.refresh();
				}
		
			
			
		}




		public void hide() {

			
			final RelativeLayout rl=(RelativeLayout)rootView.findViewById(R.id.lay1);
			 rl.animate()
             .alpha(0f)
             .setDuration(mShortAnimationDuration)
             .setListener(new AnimatorListenerAdapter() {
                 @Override
                 public void onAnimationEnd(Animator animation) {
                     rl.setVisibility(View.GONE);
                 }
             });
		
			
		}
		
		
		public class MyAdapter extends ArrayAdapter {

			int n;
			
			public MyAdapter(Context context, int textViewResourceId,String[] objects,int num) {
			super(context, textViewResourceId, objects);
			n=num;
			}

			public View getCustomView(int position, View convertView,
			ViewGroup parent) {

			// Inflating the layout for the custom Spinner
			LayoutInflater inflater = getActivity().getLayoutInflater();
			View layout = inflater.inflate(R.layout.custom, parent, false);

			// Declaring and Typecasting the textview in the inflated layout
			TextView tvLanguage = (TextView) layout.findViewById(R.id.tvLanguage);

			
			// Setting the color of the text
			tvLanguage.setTextColor(Color.rgb(255, 255, 255));

			// Declaring and Typecasting the imageView in the inflated layout
			ImageView img = (ImageView) layout.findViewById(R.id.imgLanguage);

			// Setting an image using the id's in the array
			if(n==1)
			{
				img.setImageResource(images1[position]);
				tvLanguage.setText(characters[position]);
				
			}
			else if(n==2)
			{	
				img.setImageResource(images2[position]);
				tvLanguage.setText(weapons[position]);
				
			}
			else if(n==3)
			{
				img.setImageResource(images3[position]);
				tvLanguage.setText(yournames.get(position));
				
			}
		    
			
			return layout;
			}

			// It gets a View that displays in the drop down popup the data at the specified position
			@Override
			public View getDropDownView(int position, View convertView,
			ViewGroup parent) {
			return getCustomView(position, convertView, parent);
			}

			// It gets a View that displays the data at the specified position
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
			return getCustomView(position, convertView, parent);
			}
		}
			
		
	}
	
	
	
		
	
	
	public static class NotesSectionFragment extends Fragment {
			
		
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		
		public static final String ARG_SECTION_NUMBER = "section_number";
		LinearLayout l;
		public NotesSectionFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
		
			System.out.println(selectedroom +" "+selectedweapon+" "+selectedperson);
			
			ArrayList<Integer> addimage = new ArrayList<Integer>();
			int nownumber=yournumber+1;	
			for(int i=0;i<alloted.get(yournumber).size();i++)
			{
				int j=0;
				for(j=0;j<32;j++)
				{
					if(noting[j].compareTo(alloted.get(yournumber).get(i))==0 )
						break;
				}
				addimage.add(ids[j][nownumber]);
			}
			
			
			View rootView = inflater.inflate(R.layout.fragment_game_notes,
					container, false);
			rootView.setBackgroundResource(R.drawable.backgroun);
			Typeface typeFace=Typeface.createFromAsset(getActivity().getAssets(),"fonts/yorkwhiteletter.ttf");
			Typeface typeFace2=Typeface.createFromAsset(getActivity().getAssets(),"fonts/Helvetica Neue Regular.ttf");
			
			TextView title=(TextView) rootView.findViewById(R.id.textView10);
			title.setTypeface(typeFace);
			
			
			for(int i=0;i<32;i++)
			{
				int j;
				for(j=0;j<=players;j++)
				{
				if(i!=0 && i!=11 && i!=22 && j!=0)
				{
				
					l=(LinearLayout) rootView.findViewById(ids[i][j]);
					l.setBackground(getResources().getDrawable( R.drawable.celda_cuerpo3 ));
					l.setGravity(Gravity.CENTER);
					
					if(addimage.contains(ids[i][j]))
					{
					ImageView newimage1=new ImageView(getActivity());
                	newimage1.setImageResource(R.drawable.cross);
                	newimage1.setPadding(5, 5, 5, 5);
                	l.addView(newimage1);
					}
					
					l.setOnClickListener(new OnClickListener(){
					
						@Override
						public void onClick(View v) {
							
						final View f=v;	
						f.setAlpha(0.25f);
							AlertDialog.Builder alertadd = new AlertDialog.Builder(getActivity());
					LayoutInflater factory = LayoutInflater.from(getActivity());
					final View view = factory.inflate(R.layout.notesicon, null);
					
					TextView t1=(TextView) view.findViewById(R.id.textView1);
					
					Typeface typeFace=Typeface.createFromAsset(getActivity().getAssets(),"fonts/yorkwhiteletter.ttf");
					
					t1.setTypeface(typeFace);
					
					final ImageView[] im=new ImageView[12];
					im[0]=(ImageView)view.findViewById(R.id.imageView1);
					im[1]=(ImageView)view.findViewById(R.id.imageView2);
					im[2]=(ImageView)view.findViewById(R.id.imageView3);
					im[3]=(ImageView)view.findViewById(R.id.imageView4);
					im[4]=(ImageView)view.findViewById(R.id.imageView5);
					im[5]=(ImageView)view.findViewById(R.id.imageView6);
					im[6]=(ImageView)view.findViewById(R.id.imageView7);
					im[7]=(ImageView)view.findViewById(R.id.imageView8);
					im[8]=(ImageView)view.findViewById(R.id.imageView9);
					im[9]=(ImageView)view.findViewById(R.id.imageView10);
					im[10]=(ImageView)view.findViewById(R.id.imageView11);
					im[11]=(ImageView)view.findViewById(R.id.imageView12);
					

					int k;
					OnClickListener h=new OnClickListener(){

						
						@Override
						public void onClick(View v) {
							
							
							for(int k=0;k<12;k++)
							{
							if(im[k].equals(v))
								{
								im[k].setAlpha(0.25f);
								
								switch(k)
								{
								case 0:
									imageid=R.drawable.correct;
									break;
								case 1:
									imageid=R.drawable.cross;
									break;
								case 2:
									imageid=R.drawable.star;
									break;
								case 3:
									imageid=R.drawable.flag;
									break;
								case 4:
									imageid=R.drawable.time;
									break;
								case 5:
									imageid=R.drawable.magnifying;
									break;
								case 6:
									imageid=R.drawable.exclamation;
									break;
								case 7:
									imageid=R.drawable.question;
									break;
								case 8:
									imageid=R.drawable.speech;
									break;
								case 9:
									imageid=R.drawable.hourglass;
									break;
								case 10:
									imageid=R.drawable.astrix;
									break;
								case 11:
									imageid=R.drawable.blanksmall;
									break;
									
								}
								
								}
							else
								im[k].setAlpha(1f);
							
							}
							
							
						}
						
					};
					
					for(k=0;k<12;k++)
					im[k].setOnClickListener(h);
					
					alertadd.setView(view);
					alertadd.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
					                public void onClick(DialogInterface dlg, int sumthin) {

					                	dlg.dismiss();
					                	f.setAlpha(1f);
					                }
					            });

					alertadd.setPositiveButton("OK", new DialogInterface.OnClickListener() {
		                public void onClick(DialogInterface dlg, int sumthin) {

		                	LinearLayout linear=(LinearLayout)f;
		                	
		                	if(linear.getChildCount()>0)
		                		linear.removeAllViews();
		                	
		                	ImageView newimage=new ImageView(getActivity());
		                	newimage.setImageResource(imageid);
		                	newimage.setPadding(5, 5, 5, 5);
		                	linear.addView(newimage);
		                	
		                	dlg.dismiss();
		                	f.setAlpha(1f);
		                }
		            });
					alertadd.setCancelable(false);
					alertadd.show();
							
						}
						
					});
					
				}
				else if(i!=0 && i!=11 && i!=22 && j==0)
				{
					TextView t=(TextView) rootView.findViewById(ids[i][j]);
					t.setTypeface(typeFace2);
					
				}
				else
				{
					TextView t=(TextView) rootView.findViewById(ids[i][j]);
					t.setTypeface(typeFace);
					
					if(j>0)
					t.setText("^"+name.get(j-1)+"*");
				}
				}
				for(j=players+1;j<9;j++)
				{
					View temp=(View) rootView.findViewById(ids[i][j]);
					temp.setVisibility(View.GONE);
				}
			}
			
			
			return rootView;
		}
	}


	class UDPServerAsyncTask extends AsyncTask<Void, Void, String> {
		boolean run=true;
		DatagramPacket packet;
		@Override
		protected String doInBackground(Void... params) {
			String result=null;
			try {
				
				
				if(sendmessagetype==1)
				{
					sent=new String(yournumber+ ":"+chosencharacter+":"+"hello");
					sentt=true;
				}
				else if(sendmessagetype==2)
				sent=new String(qname+":"+qweapon+":"+yournumber+":"+room);
				
				else if(sendmessagetype==3)
				{
					sent=new String(Integer.toString(iforsend)+":"+Integer.toString(jforsend)+":"+Integer.toString(xforsend)+":"+Integer.toString(yforsend)+":"+movecount+":");
					
				}
				else if(sendmessagetype==4)
					sent=new String(Integer.toString(4)+Integer.toString(yournumber));
				else if(sendmessagetype==5)
					sent=new String(Integer.toString(5)+Integer.toString(yournumber));
				else
					sent=new String(Integer.toString(6)+Integer.toString(yournumber));
				
				System.out.println("sender="+sent);
				
				for(int i=0;i<players;i++)
				{
					if(i!=yournumber)
					{
				packet = new DatagramPacket(sent.getBytes(), sent.length(),InetAddress.getByName(ip.get(i)), 5000);
				socket.send(packet);
					}
				}
				
				if(sendmessagetype==5)
				{
					name.remove(yournumber);
					ip.remove(yournumber);
					alloted.remove(yournumber);
					icon.remove(yournumber);
					isin.remove(yournumber);
					playingplayers.remove(yournumber);
					players--;
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			return result;
		}

		@Override
		protected void onPostExecute(String s) {

			//Toast.makeText(getBaseContext(),sent, Toast.LENGTH_SHORT).show();
			serve=new UDPServerAsyncTask();
			
			if(ppsize==players-1 && sentt==true && sendmessagetype==1)
			{
				ClueBoardSectionFragment cb= (ClueBoardSectionFragment) f.findFragmentByTag("android:switcher:" + R.id.pager + ":" + 1);
				if(cb!=null)
				{
						cb.updateposition();
					
				}
				
				
				if(yournumber-1==playertoplay )
				{
					runOnUiThread(new Runnable() {
					    public void run() {
					    	
					    
					    	mViewPager.setCurrentItem(1, true);
					    	
					        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
					        View layout = inflater.inflate(R.layout.alerts, (ViewGroup) findViewById(R.layout.activity_game_play), false);
					        AlertDialog.Builder builder = new AlertDialog.Builder(context).setView(layout);
					        
					        builder.setIcon(android.R.drawable.ic_dialog_alert);
						       
							final AlertDialog alertDialog = builder.create();
							alertDialog.setCancelable(false);
					        alertDialog.show();
					        
					        final TextView t2= (TextView) layout.findViewById(R.id.textView1);
					        Button b= (Button) layout.findViewById(R.id.button1);
					        Typeface typeFace=Typeface.createFromAsset(context.getAssets(),"fonts/yorkwhiteletter.ttf");
					        
					        t2.setTypeface(typeFace);
					        t2.setText("{YOUR!TURN}");
					        
					        b.setOnClickListener(new OnClickListener(){

								@Override
								public void onClick(View arg0) {
									
									alertDialog.dismiss();
									
								}
					        	
					        });
					    	
							ClueBoardSectionFragment cbs = (ClueBoardSectionFragment) f.findFragmentByTag("android:switcher:" + R.id.pager + ":" + 1);
							if(cbs!=null)
							cbs.show();
									
							if(isin.get(yournumber)>188)
							{
								declare.animate()
						        .alpha(1f)
						        .setDuration(mShortAnimationDuration)
						        .setListener(new AnimatorListenerAdapter() {
						            @Override
						            public void onAnimationEnd(Animator animation) {
						                declare.setVisibility(View.VISIBLE);
						            }
						        });

						CardsSectionFragment cb = (CardsSectionFragment) f.findFragmentByTag("android:switcher:" + R.id.pager + ":" + 0);   
						   if(cb!=null)
							cb.show();
							}

					    }
					});

					
				}

				
			}
			
			if(sendmessagetype==2)
			{
			
				String answer=new String();	
				final String ans;
				 final String[] parameters=sent.split(":");
				 final int asker=Character.getNumericValue(parameters[2].charAt(0));
				int answerer= asker+1;
				if(answerer==players)
					answerer=0;
				final int answererer=answerer;
				
			    if(alloted.get(answerer).contains(parameters[0]) || alloted.get(answerer).contains(parameters[1]))
			    answer="NO";
			    else
			    	answer="MAYBE";
			    	
			    ans=answer;
			    String message1="{YOU!ASKED}\n{DID!"+parameters[0].toUpperCase()+"!KILL}\n{WITH!A!"+parameters[1].toUpperCase()+"}\n{IN!THE!"+parameters[3].toUpperCase()+"}";
			    String message2= "["+name.get(answererer).toUpperCase()+"!REPLIED!"+ans+"]";
			    String messagelogs="YOU asked : Did "+parameters[0].toUpperCase()+" kill with a "+parameters[1].toUpperCase()+" in the "+parameters[3].toUpperCase()+"?\n"+name.get(answererer).toUpperCase()+" replied "+ans;
			    
			   CardsSectionFragment cb = (CardsSectionFragment) f.findFragmentByTag("android:switcher:" + R.id.pager + ":" + 0);   
				   if(cb!=null)
					cb.updatelog(messagelogs);
						
	
				   LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			        View layout = inflater.inflate(R.layout.alerts2, (ViewGroup) findViewById(R.layout.activity_game_play), false);
			        AlertDialog.Builder builder = new AlertDialog.Builder(context).setView(layout);
			        
			        builder.setIcon(android.R.drawable.ic_dialog_alert);
				   
			        
			        final AlertDialog alertDialog = builder.create();
			        alertDialog.setCancelable(false);
			        alertDialog.show();
			        final TextView t2= (TextView) layout.findViewById(R.id.textView2);
			        final TextView t1= (TextView) layout.findViewById(R.id.textView1);
			        final TextView t3= (TextView) layout.findViewById(R.id.textView3);
				        
			        Button b= (Button) layout.findViewById(R.id.button1);
			        Typeface typeFace=Typeface.createFromAsset(context.getAssets(),"fonts/yorkwhiteletter.ttf");
			        
			        t1.setTypeface(typeFace);    
			        t2.setTypeface(typeFace);
			        t3.setTypeface(typeFace);
			        
			        t2.setText(message1);
			        t3.setText(message2);
			        b.setOnClickListener(new OnClickListener(){

						@Override
						public void onClick(View arg0) {
							
							alertDialog.dismiss();
							
						}
			        	
			        });
			    	

				   
				   
				   /*   
				   new AlertDialog.Builder(context)
					    .setTitle("QUESTION")
					    .setMessage(message)
					    .setNegativeButton("dismiss", new DialogInterface.OnClickListener() {
					        public void onClick(DialogInterface dialog, int which) { 
					    
					        	dialog.dismiss();
					        }
					     })
					    .setIcon(android.R.drawable.ic_dialog_alert)
					     .show();
*/
				    				
			}
			else
			{
				
				if(isin.get(yournumber)>188)
				{
			CardsSectionFragment cb = (CardsSectionFragment) f.findFragmentByTag("android:switcher:" + R.id.pager + ":" + 0);   
			   if(cb!=null)
				cb.show();
			   
			   declare.animate()
		        .alpha(1f)
		        .setDuration(mShortAnimationDuration)
		        .setListener(new AnimatorListenerAdapter() {
		            @Override
		            public void onAnimationEnd(Animator animation) {
		                declare.setVisibility(View.VISIBLE);
		            }
		        });

			   
			   switch(isin.get(yournumber))
			   {
			   case 189:
				   room="study";
				   break;
			   case 190:
				   room="hall";
				   break;
			   case 191:
				   room="lounge";
				   break;
			   case 192:
				   room="dinning";
				   break;
			   case 193:
				   room="kitchen";
				   break;
			   case 194:
				   room="ballroom";
				   break;
			   case 195:
				   room="conservatory";
				   break;
			   case 196:
				   room="billiardsroom";
				   break;
			   case 197:
				   room="library";
				   break;
			   default :
				   break;
				   
			   }
			   
			   
				}
				else
				{
					CardsSectionFragment cb = (CardsSectionFragment) f.findFragmentByTag("android:switcher:" + R.id.pager + ":" + 0);   
					   if(cb!=null)
						cb.hide();
					   
					   	declare.animate()
				        .alpha(0f)
				        .setDuration(mShortAnimationDuration)
				        .setListener(new AnimatorListenerAdapter() {
				            @Override
				            public void onAnimationEnd(Animator animation) {
				                declare.setVisibility(View.INVISIBLE);
				            }
				        });


					
				}

			}
		}
		
		}
	
	class UDPClientAsyncTask extends AsyncTask<Void, Void, String> {
		boolean good=true;
		DatagramPacket packet;
		@Override
		protected String doInBackground(Void... params) {
			String result = null;
			
			try {
				
				
				while(true)
				{
				packet = new DatagramPacket(received, received.length);
				socket.receive(packet);
				
				final String s=new String(packet.getData(),0, packet.getLength(), "US-ASCII");
				System.out.println("received="+s+"\n");
				
				int counter = 0;
				for( int ii=0; ii<s.length(); ii++ ) {
				    if( s.charAt(ii) == ':' ) {
				        counter++;
				    } 
				}
				
				
				
				//type1 message
				if(s.charAt(1)==':' && counter!=5)
				{
					final String[] ss=s.split(":");
					playingplayers.set(Integer.parseInt(ss[0]),ss[1]);
					ppsize++;
					
					runOnUiThread(new Runnable() {
					    public void run() {
					    	Toast.makeText(getBaseContext(), name.get(Integer.parseInt(ss[0]))+" is ready", Toast.LENGTH_SHORT).show();
			
					    }
					});
					
					if(ppsize==players-1 && sentt==true && sendmessagetype==1)
					{

							
					
						runOnUiThread(new Runnable() {
						    public void run() {
						
						    	ClueBoardSectionFragment cb= (ClueBoardSectionFragment) f.findFragmentByTag("android:switcher:" + R.id.pager + ":" + 1);
								if(cb!=null)
								{
										cb.updateposition();
									
								}
							
						    	Toast.makeText(getBaseContext(), "let the games begin!", Toast.LENGTH_SHORT).show();		
						    }
						});
							
						
							letsplay(playertoplay);
						
					}
				}
				
				
				
				//type2 message
				else
				{
				if(counter!=5 && s.charAt(1)!=':' && s.charAt(0)!='4' && s.charAt(0)!='5')
				{
				//Toast.makeText(getBaseContext(), s, Toast.LENGTH_SHORT).show();
				 String answer=new String();	
				final String ans;
				 final String[] parameters=s.split(":");
				 final int asker=Character.getNumericValue(parameters[2].charAt(0));
				int answerer= asker+1;
				if(answerer==players)
					answerer=0;
				final int answererer=answerer;
				
			    if(alloted.get(answerer).contains(parameters[0]) || alloted.get(answerer).contains(parameters[1]) || alloted.get(answerer).contains(parameters[3]))
			    answer="NO";
			    else
			    	answer="MAYBE";
			    	
			    ans=answer;
			    
			    String answerername;
			    if(yournumber==answerer)
			    	answerername="you";
			    else
			    	answerername=name.get(answerer);
			    
			    final String answername=answerername;
			    
				runOnUiThread(new Runnable() {
				    public void run() {
				    
				String messagelogs=name.get(asker).toUpperCase()+" asked : Did "+parameters[0].toUpperCase()+" kill with a "+parameters[1].toUpperCase()+" in the "+parameters[3].toUpperCase()+"? \n"+answername.toUpperCase()+" replied "+ans;
				String message1="{"+name.get(asker).toUpperCase()+"!ASKED}\n{DID!"+parameters[0].toUpperCase()+"!KILL}\n{WITH!A!"+parameters[1].toUpperCase()+"}\n{IN!THE!"+parameters[3].toUpperCase()+"}";
				String message2="["+answername.toUpperCase()+"!REPLIED!"+ans+"]";
				    	
				    	CardsSectionFragment cb = (CardsSectionFragment) f.findFragmentByTag("android:switcher:" + R.id.pager + ":" + 0);   
						   if(cb!=null)
							cb.updatelog(messagelogs);
							
							   LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
						        View layout = inflater.inflate(R.layout.alerts2, (ViewGroup) findViewById(R.layout.activity_game_play), false);
						        AlertDialog.Builder builder = new AlertDialog.Builder(context).setView(layout);
						        
						        builder.setIcon(android.R.drawable.ic_dialog_alert);
							   
						        final AlertDialog alertDialog = builder.create();
						        alertDialog.setCancelable(false);
						        alertDialog.show();
						        final TextView t2= (TextView) layout.findViewById(R.id.textView2);
						        final TextView t1= (TextView) layout.findViewById(R.id.textView1);
						        final TextView t3= (TextView) layout.findViewById(R.id.textView3);
							        
						        Button b= (Button) layout.findViewById(R.id.button1);
						        Typeface typeFace=Typeface.createFromAsset(context.getAssets(),"fonts/yorkwhiteletter.ttf");
						        
						        t1.setTypeface(typeFace);    
						        t2.setTypeface(typeFace);
						        t3.setTypeface(typeFace);
						        
						        t2.setText(message1);
						        t3.setText(message2);
						        b.setOnClickListener(new OnClickListener(){

									@Override
									public void onClick(View arg0) {
									
										letsplay(asker);
								           
										alertDialog.dismiss();
										
									}
						        	
						        });
						    	

						   
						   
				    	/*
					    new AlertDialog.Builder(context)
					    .setTitle("QUESTION")
					    .setMessage(message)
					    .setNegativeButton("dismiss", new DialogInterface.OnClickListener() {
					        public void onClick(DialogInterface dialog, int which) { 
					    
					        	   	letsplay(asker);
					           
					        	dialog.dismiss();
					        }
					     })
					    .setIcon(android.R.drawable.ic_dialog_alert)
					    .setCancelable(false)
					     .show();
						*/
						   
						   
						   
				    }
				});
			    	
			
			    
					
				}
				//type3 message
				else if(counter==5)
				{
					final String[] change=s.split(":");
					runOnUiThread(new Runnable() {
					    public void run() {
					    	GamePlay.setisin(Integer.parseInt(change[0]), Integer.parseInt(change[1]), Integer.parseInt(change[2]), Integer.parseInt(change[3]));
					    	
					    	int asker=Integer.parseInt(change[0]);
					    	int x;
					    	if(asker==players-1)
					    		x=-1;
					    	else
					    		x=asker;
					    	if(Integer.parseInt(change[4])==0 && x==yournumber-1 && Integer.parseInt(change[1])<188)
					    	{
					    		
					    		mViewPager.setCurrentItem(1, true);
					    		
					    		  LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
							        View layout = inflater.inflate(R.layout.alerts, (ViewGroup) findViewById(R.layout.activity_game_play), false);
							        AlertDialog.Builder builder = new AlertDialog.Builder(context).setView(layout);
							        
							        builder.setIcon(android.R.drawable.ic_dialog_alert);
								   
							        
							        final AlertDialog alertDialog = builder.create();
									alertDialog.setCancelable(false);
							        alertDialog.show();
							        final TextView t2= (TextView) layout.findViewById(R.id.textView1);
							        Button b= (Button) layout.findViewById(R.id.button1);
							        Typeface typeFace=Typeface.createFromAsset(context.getAssets(),"fonts/yorkwhiteletter.ttf");
							        
							        t2.setTypeface(typeFace);
							        t2.setText("{YOUR!TURN}");
							        
							        b.setOnClickListener(new OnClickListener(){

										@Override
										public void onClick(View arg0) {
											
											alertDialog.dismiss();
											
										}
							        	
							        });
							    	
					    		
					    		ClueBoardSectionFragment cb = (ClueBoardSectionFragment)
						                f.findFragmentByTag("android:switcher:" + R.id.pager + ":" + 1);   
							    
								if(cb!=null)
									cb.show();
					    		
					    	}
					    		
					    		
					    }
					});
					
				}
				
				//type4
				else if(s.charAt(0)=='4')
				{
					Intent intent =new Intent(context,GameOver.class);
					intent.putExtra("result", "no");
					intent.putExtra("winner", name.get(Integer.parseInt(new String(Character.toString(s.charAt(1)))) ) );
					intent.putExtra("nameip", nameip);
					startActivity(intent);
					finish();
				}
				
				//type5
				else if(s.charAt(0)=='5' || s.charAt(0)=='6')
				{
					runOnUiThread(new Runnable() {
					    public void run() {
					
					    	if(s.charAt(0)=='5')
					    		Toast.makeText(context, "wrong declaration took place! check the logs for more...", Toast.LENGTH_SHORT).show();
					    	else
					    		Toast.makeText(context, "A player quit the game! check the logs for more...", Toast.LENGTH_SHORT).show();
							    		
					    	
					CardsSectionFragment cb = (CardsSectionFragment) f.findFragmentByTag("android:switcher:" + R.id.pager + ":" + 0);   
					   if(cb!=null)
						cb.updatelog2(s.charAt(1),Integer.parseInt(Character.toString(s.charAt(0))));
					   
					   int thisnumber=Integer.parseInt(Character.toString(s.charAt(1)));
					   
					   int nextnumber;
					   if(thisnumber == players-1)
						   nextnumber=0;
					   else
						   nextnumber=thisnumber+1;
						   
					   if(yournumber==nextnumber)
					   {
						   ClueBoardSectionFragment csb = (ClueBoardSectionFragment) f.findFragmentByTag("android:switcher:" + R.id.pager + ":" + 1);   
						   if(csb!=null)
							csb.show();
						
					   }	   
						   
						name.remove(thisnumber);
						ip.remove(thisnumber);
						alloted.remove(thisnumber);
						icon.remove(thisnumber);
						isin.remove(thisnumber);
						playingplayers.remove(thisnumber);
						players--;
				    
						if(yournumber>thisnumber)
							   yournumber--;
						   
						
					    if(players==1)
					   {
						   Intent intent = new Intent(context,GameOver.class);
						   intent.putExtra("result", "yes");
						   intent.putExtra("winner", name.get(yournumber));
						   intent.putExtra("nameip", nameip);
						   startActivity(intent);
						   finish();
					   }
					   
					    }
					});
				}
				}
			}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
						
			
		
			return result;
		}

		void letsplay(int playertoplay)
		  
		{
			int x;
			if(playertoplay==players-1)
				x=-1;
			else
				x=playertoplay;
			
		if(yournumber-1==x )
		{
			runOnUiThread(new Runnable() {
			    public void run() {
					
			    	mViewPager.setCurrentItem(1, true);
			    	
			    	  LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				        View layout = inflater.inflate(R.layout.alerts, (ViewGroup) findViewById(R.layout.activity_game_play), false);
				        AlertDialog.Builder builder = new AlertDialog.Builder(context).setView(layout);
				        
				        builder.setIcon(android.R.drawable.ic_dialog_alert);
					       
				        final AlertDialog alertDialog = builder.create();
						alertDialog.setCancelable(false);
				        alertDialog.show();
				        final TextView t2= (TextView) layout.findViewById(R.id.textView1);
				        Button b= (Button) layout.findViewById(R.id.button1);
				        Typeface typeFace=Typeface.createFromAsset(context.getAssets(),"fonts/yorkwhiteletter.ttf");
				        
				        t2.setTypeface(typeFace);
				        t2.setText("{YOUR!TURN}");
				        
				        b.setOnClickListener(new OnClickListener(){

							@Override
							public void onClick(View arg0) {
								
								alertDialog.dismiss();
								
							}
				        	
				        });
				    	
			    	
					ClueBoardSectionFragment cbs = (ClueBoardSectionFragment) f.findFragmentByTag("android:switcher:" + R.id.pager + ":" + 1);
					if(cbs!=null)
					cbs.show();
					
					if(isin.get(yournumber)>188)
					{
					CardsSectionFragment cb = (CardsSectionFragment) f.findFragmentByTag("android:switcher:" + R.id.pager + ":" + 0);   
				   if(cb!=null)
					cb.show();
				   
				   declare.animate()
			        .alpha(1f)
			        .setDuration(mShortAnimationDuration)
			        .setListener(new AnimatorListenerAdapter() {
			            @Override
			            public void onAnimationEnd(Animator animation) {
			                declare.setVisibility(View.VISIBLE);
			            }
			        });

					}
					
			    }
			});

		/*	playertoplay++;
			if(playertoplay==players-1)
				playertoplay=-1;
			*/
			
		}

		}
		
		
		}
	
	
	public class NewThread extends Thread
	{
		public UDPClientAsyncTask u;
		
		@Override
		public void run() {
			
			
			u=new UDPClientAsyncTask();
			if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
				u.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);	
		    else
		       u.execute();			
			
	}
	}
	
	public static void setisin(int i,int j,int x,int y)
	{
		
		Canvas canvas = new Canvas(bmOverlay);
	     canvas.drawARGB(0x00, 0, 0, 0);
	     canvas.drawBitmap(bitmap, 0, 0, null);
	    
	     
	     iforsend=i;
	     jforsend=j;
	     xforsend=x;
	     yforsend=y;
		
		isin.set(i, j);
		
		
		if(playingplayers.get(i).compareTo("brown")==0)
		{
			brunettepos[0]=x;	
			brunettepos[1]=y;
		}
		
		else if(playingplayers.get(i).compareTo("peach")==0)
		{
			peachpos[0]=x;
			peachpos[1]=y;
		}
		

		else if(playingplayers.get(i).compareTo("grey")==0)
		{
			graypos[0]=x;
			graypos[1]=y;
		}
		
		else if(playingplayers.get(i).compareTo("rose")==0)
		{
	    	rosepos[0]=x;
			rosepos[1]=y;
		}
		
		else if(playingplayers.get(i).compareTo("white")==0)
		{
	    	whitepos[0]=x;
			whitepos[1]=y;
		}
		
		else if(playingplayers.get(i).compareTo("green")==0)
		{
			greenpos[0]=x;
			greenpos[1]=y;
		}
		
		else if(playingplayers.get(i).compareTo("mustard")==0)
		{
	    	mustardpos[0]=x;
			mustardpos[1]=y;
		}
		
		else if(playingplayers.get(i).compareTo("peacock")==0)
		{
			peacockpos[0]=x;
			peacockpos[1]=y;
		}
		
		else if(playingplayers.get(i).compareTo("scarlet")==0)
		{
	    	scarletpos[0]=x;
			scarletpos[1]=y;
		}
		
		else if(playingplayers.get(i).compareTo("plum")==0)
		{
			plumpos[0]=x;
			plumpos[1]=y;
		}

		
		
		for(int k=0;k<players;k++)
		{
		
			if(playingplayers.get(k).compareTo("brown")==0)
			{
		     canvas.drawBitmap(Bitmap.createScaledBitmap(icon.get(k),70 ,70 , false), brunettepos[0],brunettepos[1], null);
			}
			
			else if(playingplayers.get(k).compareTo("peach")==0)
			{
			
		     canvas.drawBitmap(Bitmap.createScaledBitmap(icon.get(k),70 ,70 , false), peachpos[0],peachpos[1], null);
			}
			

			else if(playingplayers.get(k).compareTo("grey")==0)
			{
		    
				canvas.drawBitmap(Bitmap.createScaledBitmap(icon.get(k),70 ,70 , false), graypos[0],graypos[1], null);
			}
			
			else if(playingplayers.get(k).compareTo("rose")==0)
			{
		    
				canvas.drawBitmap(Bitmap.createScaledBitmap(icon.get(k),70 ,70 , false), rosepos[0],rosepos[1], null);
			}
			
			else if(playingplayers.get(k).compareTo("white")==0)
			{
		    
				canvas.drawBitmap(Bitmap.createScaledBitmap(icon.get(k),70 ,70 , false), whitepos[0],whitepos[1], null);
			}
			
			else if(playingplayers.get(k).compareTo("green")==0)
			{
		     canvas.drawBitmap(Bitmap.createScaledBitmap(icon.get(k),70 ,70 , false), greenpos[0],greenpos[1], null);
			}
			
			else if(playingplayers.get(k).compareTo("mustard")==0)
			{
		    
				canvas.drawBitmap(Bitmap.createScaledBitmap(icon.get(k),70 ,70 , false), mustardpos[0],mustardpos[1], null);
			}
			
			else if(playingplayers.get(k).compareTo("peacock")==0)
			{
			
		     canvas.drawBitmap(Bitmap.createScaledBitmap(icon.get(k),70 ,70 , false), peacockpos[0],peacockpos[1], null);
			}
			
			else if(playingplayers.get(k).compareTo("scarlet")==0)
			{
			
		     canvas.drawBitmap(Bitmap.createScaledBitmap(icon.get(k),70 ,70 , false), scarletpos[0],scarletpos[1], null);
			}
			
			else if(playingplayers.get(k).compareTo("plum")==0)
			{
		    
				canvas.drawBitmap(Bitmap.createScaledBitmap(icon.get(k),70 ,70 , false), plumpos[0],plumpos[1], null);
			}

			
			
		}

		tiv.setImageBitmap(bmOverlay);


		
		
	}
	
	@Override
	public void onBackPressed() {
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
	public void onDestroy() {
	    super.onDestroy();
	    unbindDrawables(findViewById(R.id.entername));
	    System.gc();
	}
}
