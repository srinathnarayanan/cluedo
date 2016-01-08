package coms.whodunnit;

import java.util.Locale;

import coms.myfirstapp.cardsui.CardUI;
import coms.whodunnit.FlowTextView;
import coms.whodunnit.R;




import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.text.Spanned;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class StartActivity extends FragmentActivity {
	
	
	static CardUI mCardView;
	static int halfscreenwidth;
    ViewPagerParallax pager;
    static MyImageCard androidViewsCard,androidViewsCard1,androidViewsCard2,androidViewsCard3,androidViewsCard4,androidViewsCard5,androidViewsCard6,androidViewsCard7,androidViewsCard8,androidViewsCard9,androidViewsCard10,androidViewsCard11;
	SectionsPagerAdapter mSectionsPagerAdapter;

	//ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);

		DisplayMetrics metrics = this.getResources().getDisplayMetrics();
		halfscreenwidth = (int)(metrics.widthPixels/2);

		
		Intent intent=getIntent();
		if(intent!=null)
		{
			String result=intent.getStringExtra("EXIT");
			if(result!=null)
				{
				finish();
				System.exit(0);
				}
		}
		
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());

		pager = (ViewPagerParallax) findViewById(R.id.pager);
	
 	//  mViewPager = (ViewPager) findViewById(R.id.pager);
	//	mViewPager.setAdapter(mSectionsPagerAdapter);
		
		pager.set_max_pages(3);
	    pager.setBackgroundAsset(R.drawable.clueback22);
	    pager.setAdapter(mSectionsPagerAdapter);
	    pager.setCurrentItem(1);


	}

	/*
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.start, menu);
		return true;
	}
*/
	
	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {

			switch(position)
			{
			case 0:
				return new BoardInstructionsFragment();
			case 1:
					return new PlayFragment();
			case 2:
				return new AppInstructionsFragment();
			
			}
			return null;
		
		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			return 3;
		}

		@Override
		public String getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return "The Game";
			case 1:
				return "Are you ready?";
			case 2:
				return "The App";
			}
			return null;
		}
	}



	public static class BoardInstructionsFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		
		public BoardInstructionsFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.start_board,
					container, false);
			
			mCardView = (CardUI) rootView.findViewById(R.id.cardsview);
			  mCardView.setSwipeable(false);
			  
			  androidViewsCard = new MyImageCard(1,0,0,0,halfscreenwidth,(int)(halfscreenwidth*0.62),"THE CASE", R.drawable.mansion,"<b>A murder has taken place at Tudor Manor.</b><br/><br/> Dr.Black is dead, and not by mere coincidence did he have 10 guests over with him the previous night. The investigationn now lies in the hands of the detectives, entrusted with figuring out who killed Dr.Black, with what weapon and in which room...before it is too late.","");
			  androidViewsCard1 = new MyImageCard(1,0,0,0,halfscreenwidth,(int)(halfscreenwidth*0.89),"CARDS", R.drawable.pouch,"All of the guests - <i><b>Miss Scarlet, Mrs.Peacock, Mr.Brown, Professor Plum, Mrs. Rose, Madam Peach,Sargeant Grey, Colonel Mustard, Mrs.White and Reverend Green </b></i>- are now suspects.<br/><br/>Initial investigation had the detectives shortlist objects ranging from the innocuous to the fatal - <i><b> Candlestick, Rope, Wrench, Lead Pipe, Revolver, Knife, Axe, Bomb, Poison and Syringe </b></i>- as possible murder weapons.<br/><br/>Tudor Manor has 9 rooms -<i><b> Conservatory, Ballroom, Billiards room, Library, Dinning Room, Kitchen, Lounge, Study and Hall</b></i> - of which any place is as likely as the other, to have been the scene of the crime.","");
			  androidViewsCard2 = new MyImageCard(2,R.drawable.list,halfscreenwidth/2,(int)(halfscreenwidth*(2.178/2)),halfscreenwidth,(int)(halfscreenwidth),"GAME BEGINS", R.drawable.cluedobigedit2,"Each of the players is a detective trying to solve the murder and needs to find the answers to the question :<b> WHO? WHERE ? WHAT WEAPON?</b> <br/><br/>At the beginning of the game, the cards are divided into 3 different stacks -<i><b> WEAPONS, CHARACTERS AND ROOMS </b></i>- and are shuffled and kept face down. One card from each stack is picked such that none of the players can see it. <i>These cards constitute the murderer, murder weapon and the place where the murder took place.</i> They are set aside for the time being. <br/><br/>The character cards are distributed in such a manner that each detective receives atleast one. The remaining cards are shuffled together and distributed evenly. The detectives have to make sure that they don't reveal their cards to anyone. <br/><br/>Detectives also receive a <i>'notes sheet'</i> that has the list of cards and player numbers, to help take down clues that would help them solve the crime.","");
			  androidViewsCard3 = new MyImageCard(1,0,0,0,halfscreenwidth,(int)(halfscreenwidth/2),"ROCK 'N ROLL", R.drawable.die,"<b>Each detective chooses to start with any one of the character cards he/she has.</b> His/Her starting position on the board is based on the same. <br/><br/><i>A detective has to roll a die to know the number of blocks he/she can move.</i> Any detective can roll first and start. In order to enter a room, a player needs to roll a number greater than or equal to the number of blocks from his current position to any door of the room.<br/><br/> All detectives have to enter or exit a room only via the doors to that room. <i><b>Corner rooms also have a secret passage to rooms on the opposite corner, to which players can move directly.</b></i> A player can move horizontally, vertically, forwards or backwards but not diagonally.","");
			  androidViewsCard4 = new MyImageCard(1,0,0,0,halfscreenwidth,(int)(halfscreenwidth*0.81),"WHO DUNNIT?",R.drawable.whodunnit,"<b>Detectives need to ask questions to start gathering evidence.</b><br/><br/><i> Remember! you can only ask a question if you are in a room.</i> By asking questions throughout the game, try to work out -  by a process of elimination - which three cards constitute the murderer, murder weapon and the murder room.<br/><br/> Once you reach a room, you can ask a question ONLY to the detective who is to play the next turn. Questions are of the form <i>'Did X kill in Y with Z'.</i> <br/><br/><b>Example :</b> Let's say that you are the detective incharge of Miss Scarlet. You have just moved into the Dining Room. Your question could be <br/><br/><i><b>'Did Reverend Green kill in the Dining room with the wrench?'.</b></i><br/><br/> YOU CAN ASK A QUESTION ABOUT A ROOM ONLY WHEN YOU ARE IN THE ROOM. The detective who is to play next would reply <b>'MAYBE'</b> if he has none of the cards from your question, meaning that any of the three cards - Reverend Green, The Dinning Room or the wrench - could be part of the murder trio.<br/><br/> If the detective has even one of the cards, or any 2 of the cards  or all 3, he should reply <b>'NO'</b>, implying that any one,two or all of the cards that were part of your question are in his posession and consequently can't be a part of the murder trio. The detective who is answering should not reveal which card is in his posession. <br/><br/>You have complete freedom in choosing the murderer and the weapon for your question. You may even include the character you are playing with as a suspect in your question. <br/><br/>Once the detective next to you has answered, <i>The turn to roll, move and ask a question goes to him/her.</i> This will go on until one detective makes a correct declaration.","");
			  androidViewsCard5 = new MyImageCard(1,0,0,0,halfscreenwidth,(int)(halfscreenwidth*0.89),"I DECLARE!",R.drawable.intro,"Once a detective is sure about the cards making up the murder trio, he or she needs to head to the correct room and make a <b>'DECLARATION'.</b><br/><br/> You cannot ask a question and declare in the same turn. A declaration is of the form <i>'I accuse X of killing in the Y with a Z'</i>. After declaring, the detective looks at the 3 cards which were initially kept aside in such a manner that none of the other players can see, and checks if his declaration was right.<br/><br/><b><i> If the declaration was correct, the game comes to an end.</i></b> if the declaration was wrong, the detective who declared wrongly has to return the murder trio to its original place (face down), reveal all his cards and quit the game. The game goes on till one of the detectives makes a correct declaration.","");
					  
			  mCardView.addCard(androidViewsCard);
			  mCardView.addCard(androidViewsCard1);
			  mCardView.addCard(androidViewsCard2);
			  mCardView.addCard(androidViewsCard3);
			  mCardView.addCard(androidViewsCard4);
			  mCardView.addCard(androidViewsCard5);
				  
	            
	    		// draw cards
	    		mCardView.refresh();
	    
			
			return rootView;
		}
	}

	
	public static class PlayFragment extends Fragment {
		
		public PlayFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.activity_main,
					container, false);
			
			TextView textView1=(TextView)rootView.findViewById(R.id.textView1);
			Button b1=(Button)rootView.findViewById(R.id.button1);
			Button b2=(Button)rootView.findViewById(R.id.button2);
				
			Typeface typeFace=Typeface.createFromAsset(getActivity().getAssets(),"fonts/yorkwhiteletter.ttf");
			textView1.setTypeface(typeFace);
			
			
			return rootView;
		}
	}

	
	public static class AppInstructionsFragment extends Fragment {
		
		public AppInstructionsFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.start_app,
					container, false);
		
			mCardView = (CardUI) rootView.findViewById(R.id.cardsview);
			  mCardView.setSwipeable(false);
			
			  
			  androidViewsCard6 = new MyImageCard(3,R.drawable.wifi,0,0,halfscreenwidth,(int)(halfscreenwidth),"GET CONNECTED", R.drawable.firstpic,"<b>Choose one of the players to be the host.</b><br/><br/> Clicking on 'create host' ensures that the host's hotspot is turned on. Make sure that the <i>number of players has been chosen by the host</i> and that the other players are <i>connected to the host's hotspot before entering their names.","");
			  androidViewsCard7 = new MyImageCard(1,0,0,0,halfscreenwidth,(int)(halfscreenwidth),"GET STARTED", R.drawable.choosecharacter,"The murder trio will be chosen and the cards evenly distributed amongst the players.<b> Choose one of your characters to get started with</b> and wait for the others to choose theirs as well.<br/><br/> The host always gets to roll first, followed by the order in which the other players connected to the host.","");
			  androidViewsCard8 = new MyImageCard(3,R.drawable.thirdslide,0,0,halfscreenwidth,(int)(halfscreenwidth),"READY TO ROLL?", R.drawable.swipe,"To facilitate gameplay,<b> the cards, the zoomable board and the clue notes sections</b> are just a swipe away from each other. <br/><br/>","A pair of dice will show up near your board when it is your turn to roll. You can roll only once and can only ask a question if you move to a room. Once you are inside the room,<i> swipe to the cards section to ask a question</i> after having deliberated over your cards.<br/><br/> Standard clue rules appl- You can only enter or exit a room through its door or through the secret passage, you can only move one block at a time- horizontally or vertically - and you can ask a question only about the room you are in");
			  androidViewsCard9 = new MyImageCard(3,R.drawable.logsexample2,0,0,halfscreenwidth,(int)(halfscreenwidth),"LOGS", R.drawable.logsexample1,"Each question asked along with its answer, <b>is saved up in the logs</b> (part of the cards section) so that you can deliberate over them later.<br/><br/>","<i> When a wrong declaration is made</i>, The cards of the player who declared will be revealed in the logs as well.");
			  androidViewsCard10 = new MyImageCard(1,0,0,0,halfscreenwidth,(int)(halfscreenwidth),"NOTES",R.drawable.notesbox,"The Notes section has a comprehensive layout for keeping<b> notes on all the players and all 29 cards.</b><br/><br/> Use it to work out your calculations. Press on a cell to place a symbol in it. Symbols can mean mean whatever you want. Clicking on the blank square removes all symbols. <br/><br/>By default, a cross is placed on cells corresponding to your name and the cards that you have.","");
			  androidViewsCard11 = new MyImageCard(3,R.drawable.longpics,0,0,halfscreenwidth,(int)(halfscreenwidth),"THE END",R.drawable.declarebox,"<b>You can only declare if you are in a room.</b><br/><br/> You can't ask a question and declare in the same turn. A <i>correct declaration ends the game</i> for all players."," A wrong declaration reveals the players cards and the other players carry on until a correct declaration takes place.");
			
			  /*
			  androidViewsCard6 = new MyImageCard(3,R.drawable.roseicon,0,0,halfscreenwidth,(int)(halfscreenwidth),"GET CONNECTED", R.drawable.roseicon,"<b>Choose one of the players to be the host.</b><br/><br/> Clicking on 'create host' ensures that the host's hotspot is turned on. Make sure that the <i>number of players has been chosen by the host</i> and that the other players are <i>connected to the host's hotspot before entering their names.","");
			  androidViewsCard7 = new MyImageCard(1,0,0,0,halfscreenwidth,(int)(halfscreenwidth),"GET STARTED", R.drawable.roseicon,"The murder trio will be chosen and the cards evenly distributed amongst the players.<b> Choose one of your characters to get started with</b> and wait for the others to choose theirs as well.<br/><br/> The host always gets to roll first, followed by the order in which the other players connected to the host.","");
			  androidViewsCard8 = new MyImageCard(3,R.drawable.roseicon,0,0,halfscreenwidth,(int)(halfscreenwidth),"READY TO ROLL?", R.drawable.roseicon,"To facilitate gameplay,<b> the cards, the zoomable board and the clue notes sections</b> are just a swipe away from each other. <br/><br/>","A pair of dice will show up near your board when it is your turn to roll. You can roll only once and can only ask a question if you move to a room. Once you are inside the room,<i> swipe to the cards section to ask a question</i> after having deliberated over your cards.<br/><br/> Standard clue rules appl- You can only enter or exit a room through its door or through the secret passage, you can only move one block at a time- horizontally or vertically - and you can ask a question only about the room you are in");
			  androidViewsCard9 = new MyImageCard(3,R.drawable.logsexample2,0,0,halfscreenwidth,(int)(halfscreenwidth),"LOGS", R.drawable.logsexample1,"Each question asked along with its answer, <b>is saved up in the logs</b> (part of the cards section) so that you can deliberate over them later.<br/><br/>","<i> When a wrong declaration is made</i>, The cards of the player who declared will be revealed in the logs as well.");
			  androidViewsCard10 = new MyImageCard(1,0,0,0,halfscreenwidth,(int)(halfscreenwidth),"NOTES",R.drawable.notesbox,"The Notes section has a comprehensive layout for keeping<b> notes on all the players and all 29 cards.</b><br/><br/> Use it to work out your calculations. Press on a cell to place a symbol in it. Symbols can mean mean whatever you want. Clicking on the blank square removes all symbols. <br/><br/>By default, a cross is placed on cells corresponding to your name and the cards that you have.","");
			  androidViewsCard11 = new MyImageCard(3,R.drawable.longpics,0,0,halfscreenwidth,(int)(halfscreenwidth),"THE END",R.drawable.declarebox,"<b>You can only declare if you are in a room.</b><br/><br/> You can't ask a question and declare in the same turn. A <i>correct declaration ends the game</i> for all players."," A wrong declaration reveals the players cards and the other players carry on until a correct declaration takes place.");
				*/
			  
			  mCardView.addCard(androidViewsCard6);
			  mCardView.addCard(androidViewsCard7);
			  mCardView.addCard(androidViewsCard8);
			  mCardView.addCard(androidViewsCard9);
			  mCardView.addCard(androidViewsCard10);
			  mCardView.addCard(androidViewsCard11);
				  
	            
	    		// draw cards
	    		mCardView.refresh();
	    
		
			
			
			return rootView;
		}
	}

	
	
	public void CreateHost(View view) 
	{
	    Intent intent = new Intent(StartActivity.this, EnterName.class);
	    intent.putExtra("from", "host");
	    startActivity(intent);
	    androidViewsCard.remove();
	    androidViewsCard1.remove();
	    androidViewsCard2.remove();
	    androidViewsCard3.remove();
	    androidViewsCard4.remove();
	    androidViewsCard5.remove();
	    androidViewsCard6.remove();
	    androidViewsCard7.remove();
	    androidViewsCard8.remove();
	    androidViewsCard9.remove();
	    androidViewsCard10.remove();
	    androidViewsCard11.remove();
	    
	    finish();
	    
	}
	public void JoinHost(View view) 
	{
		WifiManager wifiManager = (WifiManager) this.getSystemService(Context.WIFI_SERVICE);    
           if(wifiManager.isWifiEnabled()==false)
           {
        	   Toast.makeText(this,"please ensure that your wifi is turned on. Connect to the hotspot of the host", Toast.LENGTH_SHORT).show();
           }
        
           else

        {
        Toast.makeText(this,"Ensure that you are Connected to the hotspot of the host", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(StartActivity.this, EnterName.class);
	    intent.putExtra("from", "join");
	    startActivity(intent);
	    finish();
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
	public void onDestroy() {
	    super.onDestroy();
	    
	    unbindDrawables(findViewById(R.id.pager));
	    System.gc();
	    
	}
}
