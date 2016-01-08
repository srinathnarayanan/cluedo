package coms.whodunnit;

import java.io.IOException;
import java.io.InputStream;



import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class GameOver extends Activity {

	static LinearLayout myGallery,myGallery1,myGallery2,myGallery3,myGallery4,myGallery5,myGallery6,myGallery7,myGallery8,myGallery9;
	String result;
	String winner;
	NameIpArray nameip;
	TextView t,t1,t2,t3,t4,t5,t6,t7,t8,t9;
	ImageView i1,i2,i3;
	static int width;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_over);
		
		DisplayMetrics metrics = this.getResources().getDisplayMetrics();
		width = metrics.widthPixels;
		
		
		Typeface typeFace=Typeface.createFromAsset(this.getAssets(),"fonts/yorkwhiteletter.ttf");
		
		Button b=(Button) findViewById(R.id.button1);
		
		myGallery = (LinearLayout) findViewById(R.id.mygallery);
		myGallery1 = (LinearLayout) findViewById(R.id.mygallery1);
		myGallery2 = (LinearLayout) findViewById(R.id.mygallery2);
		myGallery3 = (LinearLayout) findViewById(R.id.mygallery3);
		myGallery4 = (LinearLayout) findViewById(R.id.mygallery4);
		myGallery5 = (LinearLayout) findViewById(R.id.mygallery5);
		myGallery6 = (LinearLayout) findViewById(R.id.mygallery6);
		myGallery7 = (LinearLayout) findViewById(R.id.mygallery7);
		myGallery8 = (LinearLayout) findViewById(R.id.mygallery8);
		myGallery9 = (LinearLayout) findViewById(R.id.mygallery9);
		
		t=(TextView) findViewById(R.id.textView);
		t1=(TextView) findViewById(R.id.textView1);
		t2=(TextView) findViewById(R.id.textView2);
		t3=(TextView) findViewById(R.id.textView3);
		t4=(TextView) findViewById(R.id.textView4);
		t5=(TextView) findViewById(R.id.textView5);
		t6=(TextView) findViewById(R.id.textView6);
		t7=(TextView) findViewById(R.id.textView7);
		t8=(TextView) findViewById(R.id.textView8);
		t9=(TextView) findViewById(R.id.textView9);
		
		
		t.setTypeface(typeFace);
		t1.setTypeface(typeFace);
		t2.setTypeface(typeFace);
		t3.setTypeface(typeFace);
		t4.setTypeface(typeFace);
		t5.setTypeface(typeFace);
		t6.setTypeface(typeFace);
		t7.setTypeface(typeFace);
		t8.setTypeface(typeFace);
		t9.setTypeface(typeFace);
		
		Intent intent=getIntent();
		result=intent.getStringExtra("result");
		winner=intent.getStringExtra("winner");
		nameip=(NameIpArray)intent.getSerializableExtra("nameip");
		
	
		
		if(result.compareTo("yes")==0)
			t.setText("ÌCONGRATS\\YOU!!WONÍ\nôThe!hidden!cards!wereõ");
		else
		{
			if(winner.compareTo("none")==0)
				t.setText(":WRONG\\DECLARATION;\n:GAME!OVER;\nôthe!hidden!cards!wereõ");
			else
				t.setText(":CORRECT\\DECLARATION\\BY\\"+winner+";\n:GAME!OVER;\nôthe!hidden!cards!wereõ");
		}

		   myGallery.addView(insertPhoto(nameip.selectedperson));
		   myGallery.addView(insertPhoto(nameip.selectedroom));
		   myGallery.addView(insertPhoto(nameip.selectedweapon));
				
		int i;
		   switch (nameip.players)
		   {
		   case 9:
		   {
			   for(i=0;i<nameip.alloted.get(8).size();i++)
				  myGallery9.addView(insertPhoto(nameip.alloted.get(8).get(i)));
			   
			   t9.setText("^"+nameip.name.get(8)+"\"s!cards*");
			   t9.setVisibility(View.VISIBLE);
		   }
		   
		   case 8:
		   {
			   for(i=0;i<nameip.alloted.get(7).size();i++)
				  myGallery8.addView(insertPhoto(nameip.alloted.get(7).get(i)));
			   
			   t8.setText("^"+nameip.name.get(7)+"\"s!cards*");
			   t8.setVisibility(View.VISIBLE);
		   }
		   
		   case 7:
		   {
			   for(i=0;i<nameip.alloted.get(6).size();i++)
				  myGallery7.addView(insertPhoto(nameip.alloted.get(6).get(i)));
		   
			   t7.setText("^"+nameip.name.get(6)+"*\"s!cards");
			   t7.setVisibility(View.VISIBLE);
		   }
		   
		   case 6:
		   {
			   for(i=0;i<nameip.alloted.get(5).size();i++)
				  myGallery6.addView(insertPhoto(nameip.alloted.get(5).get(i)));
		   
			   t6.setText("^"+nameip.name.get(5)+"\"s!cards*");
			   t6.setVisibility(View.VISIBLE);
		   }
		   
		   case 5:
		   {
			   for(i=0;i<nameip.alloted.get(4).size();i++)
				  myGallery5.addView(insertPhoto(nameip.alloted.get(4).get(i)));
			   
			   t5.setText("^"+nameip.name.get(4)+"\"s!cards*");
			   t5.setVisibility(View.VISIBLE);
		   }
		   
		   case 4:
		   {
			   for(i=0;i<nameip.alloted.get(3).size();i++)
				  myGallery4.addView(insertPhoto(nameip.alloted.get(3).get(i)));
		   
		   
			   t4.setText("^"+nameip.name.get(3)+"\"s!cards*");
			   t4.setVisibility(View.VISIBLE);
		   }
		   
		   case 3:
		   {
			   for(i=0;i<nameip.alloted.get(2).size();i++)
				  myGallery3.addView(insertPhoto(nameip.alloted.get(2).get(i)));
		   
			   t3.setText("^"+nameip.name.get(2)+"\"s!cards*");
			   t3.setVisibility(View.VISIBLE);
		   }
		   
		   case 2:
		   {
			   for(i=0;i<nameip.alloted.get(1).size();i++)
				  myGallery2.addView(insertPhoto(nameip.alloted.get(1).get(i)));
		   
			   t2.setText("^"+nameip.name.get(1)+"\"s!cards*");
			   t2.setVisibility(View.VISIBLE);
		   }
		   
		   case 1:
		   {
			   for(i=0;i<nameip.alloted.get(0).size();i++)		   
				  myGallery1.addView(insertPhoto(nameip.alloted.get(0).get(i)));
		
			   t1.setText("^"+nameip.name.get(0)+"\"s!cards*");
			   t1.setVisibility(View.VISIBLE);
		   }
		   
		   
		   
		   }
		   
	}

	/*
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game_over, menu);
		return true;
		
		
	}
*/
	
	LinearLayout insertPhoto(String str){
		
		int lw=(int)(width/3.25);
		int lh=(int)(lw*1.29);

		int dp = (int) (this.getResources().getDimension(R.dimen.gallery) / this.getResources().getDisplayMetrics().density);
		
		Bitmap bm = decodeSampledBitmapFromUri("images/"+str+".jpg",lw-20,lh-20);

	     LinearLayout layout = new LinearLayout(this.getApplicationContext());
	     layout.setLayoutParams(new LayoutParams(lw, lh));
	     layout.setGravity(Gravity.CENTER);
	     
	     ImageView imageView = new ImageView(this.getApplicationContext());
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
	     
	     AssetManager assetManager = this.getApplicationContext().getAssets();
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
	        AssetManager assetManager = this.getApplicationContext().getAssets();
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

public void exit(View v)
{

	Intent intent = new Intent(getApplicationContext(), GamePlay.class);
	intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	intent.putExtra("EXIT", "true");
	startActivity(intent);
}
	
@Override
public void onBackPressed() {
}
}
