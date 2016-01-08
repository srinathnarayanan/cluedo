/*
 * TouchImageView.java
 * By: Michael Ortiz
 * Updated By: Patrick Lackemacher
 * Updated By: Babay88
 * Updated By: @ipsilondev
 * Updated By: hank-cp
 * Updated By: singpolyma
 * -------------------
 * Extends Android ImageView to include pinch zooming, panning, fling and double tap zoom.
 */

package coms.whodunnit;



import java.util.ArrayList;

import coms.whodunnit.MyImageView.RectArea;


import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.Camera.Area;
import android.net.Uri;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.OverScroller;
import android.widget.Scroller;
import android.widget.Toast;

public class TouchImageView extends ImageView {
	
	public class RectArea extends Area {
		float _left;
		float _top;
		float _right;
		float _bottom;
		public String n;
		public int idd;

		RectArea(int id, String name, float left, float top, float right, float bottom) {
			super(null, id);
			_left = left;
			_top = top;
			_right = right;
			_bottom = bottom;
			n=name;
			idd=id;
		}

		public boolean isInArea(float x, float y) {
			boolean ret = false;
			if ((x > _left) && (x < _right)) {
				if ((y > _top) && (y < _bottom)) {
					ret = true;
				}
			}
			return ret;
		}

		public float getOriginX() {
			return _left;
		}

		public float getOriginY() {
			return _top;
		}

	}
	
	static public int characterarea,prevarea=0;
	public static ArrayList<RectArea> mAreaList = new ArrayList<RectArea>(200);	
	public static ArrayList<ArrayList<RectArea>> neighbours=new ArrayList<ArrayList<RectArea>>(200);
	public static int movesleft=0;
	public static int[] roomcounter=new int[9];
	
	private static final String DEBUG = "DEBUG";
	
	//
	// SuperMin and SuperMax multipliers. Determine how much the image can be
	// zoomed below or above the zoom boundaries, before animating back to the
	// min/max zoom boundary.
	//
	private static final float SUPER_MIN_MULTIPLIER = .75f;
	private static final float SUPER_MAX_MULTIPLIER = 1.25f;

    //
    // Scale of image ranges from minScale to maxScale, where minScale == 1
    // when the image is stretched to fit view.
    //
    private float normalizedScale;
    
    //
    // Matrix applied to image. MSCALE_X and MSCALE_Y should always be equal.
    // MTRANS_X and MTRANS_Y are the other values used. prevMatrix is the matrix
    // saved prior to the screen rotating.
    //
	private Matrix matrix, prevMatrix;

    private static enum State { NONE, DRAG, ZOOM, FLING, ANIMATE_ZOOM };
    private State state;

    private float minScale;
    private float maxScale;
    private float superMinScale;
    private float superMaxScale;
    private float[] m;
    
    private Context context;
    private Fling fling;
    
    private ScaleType mScaleType;
    
    private boolean imageRenderedAtLeastOnce;
    private boolean onDrawReady;
    
    private ZoomVariables delayedZoomVariables;

    //
    // Size of view and previous view size (ie before rotation)
    //
    private int viewWidth, viewHeight, prevViewWidth, prevViewHeight;
    
    //
    // Size of image when it is stretched to fit view. Before and After rotation.
    //
    private float matchViewWidth, matchViewHeight, prevMatchViewWidth, prevMatchViewHeight;
    
    private ScaleGestureDetector mScaleDetector;
    private GestureDetector mGestureDetector;
    private GestureDetector.OnDoubleTapListener doubleTapListener = null;
    private OnTouchListener userTouchListener = null;
    private OnTouchImageViewListener touchImageViewListener = null;

    public TouchImageView(Context context) {
        super(context);
        sharedConstructing(context);
    }

    public TouchImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        sharedConstructing(context);
    }
    
    public TouchImageView(Context context, AttributeSet attrs, int defStyle) {
    	super(context, attrs, defStyle);
    	sharedConstructing(context);
    }
    
    private void sharedConstructing(Context context) {
    	
    	RectArea one=new RectArea(1,"1",644,105,719,178);
    	RectArea two=new RectArea(2,"2",1310,106,1383,177);
    	RectArea three=new RectArea(3,"3",644,179,720,254);
    	RectArea four=new RectArea(4,"4",719,181,789,251);
    	RectArea five=new RectArea(5,"5",1236,182,1310,250);
    	RectArea six=new RectArea(6,"6",1312,177,1383,246);
    	RectArea seven=new RectArea(7,"7",646,257,719,331);
    	RectArea eight=new RectArea(8,"8",720,255,789,328);
    	RectArea nine=new RectArea(9,"9",1238,254,1311,324);
    	RectArea ten=new RectArea(10,"10",1310,252,1384,323);
    	
    	RectArea eleven=new RectArea(11,"11",646,330,718,402);
    	RectArea twelve=new RectArea(12,"12",720,330,789,404);
    	RectArea thirteen=new RectArea(13,"13",1239,327,1311,401);
    	RectArea fourteen=new RectArea(14,"14",1311,325,1386,399);
    	RectArea fifteen=new RectArea(15,"15",197,408,268,480);
    	RectArea sixteen=new RectArea(16,"16",271,406,345,480);
    	RectArea seventeen=new RectArea(17,"17",346,407,418,478);
    	RectArea eighteen=new RectArea(18,"18",419,404,491,478);
    	RectArea nineteen=new RectArea(19,"19",491,406,571,475);
    	
    	RectArea twenty=new RectArea(20,"20",572,406,644,480);
    	RectArea twentyone=new RectArea(21,"21",645,405,719,480);
    	RectArea twentytwo=new RectArea(22,"22",718,404,791,476);
    	RectArea twentythree=new RectArea(23,"23",1239,402,1310,475);
    	RectArea twentyfour= new RectArea(24,"24",1311,402,1385,472);
    	RectArea twentyfive= new RectArea(25,"25",126,483,196,546);
    	RectArea twentysix= new RectArea(26,"26",198,483,272,546);
    	RectArea twentyseven= new RectArea(27,"27",270,482,343,549);
    	RectArea twentyeight= new RectArea(28,"28",346,480,421,548);
    	RectArea twentynine= new RectArea(29,"29",419,480,490,547);
    	
    	RectArea thirty= new RectArea(30,"30",495,479,570,548);
    	RectArea thirtyone= new RectArea(31,"31",572,480,646,551);
    	RectArea thirtytwo= new RectArea(32,"32",649,479,719,551);
    	RectArea thirtythree= new RectArea(33,"33",721,481,792,552);
    	RectArea thirtyfour= new RectArea(34,"34",1238,478,1311,550);
    	RectArea thirtyfive=new RectArea(35,"35",1313,477,1385,548);
    	RectArea thirtysix=new RectArea(36,"36",569,552,645,626);
    	RectArea thirtyseven=new RectArea(37,"37",647,553,718,625);
    	RectArea thirtyeight=new RectArea(38,"38",721,552,793,626);
    	RectArea thirtynine=new RectArea(39,"39",1238,555,1313,623);
    	
    	RectArea fourty=new RectArea(40,"40",1313,551,1387,622);
    	RectArea fourtyone=new RectArea(41,"41",1389,552,1462,623);
    	RectArea fourtytwo=new RectArea(42,"42",1464,551,1541,618);
    	RectArea fourtythree=new RectArea(43,"43",1542,550,1614,619);
    	RectArea fourtyfour=new RectArea(44,"44",1616,549,1693,617);
    	RectArea fourtyfive=new RectArea(45,"45",1694,549,1769,617);
    	RectArea fourtysix=new RectArea(46,"46",1768,548,1846,619);
    	RectArea fourtyseven=new RectArea(47,"47",647,627,719,703);
    	RectArea fourtyeight=new RectArea(48,"48",722,628,795,704);
    	RectArea fourtynine=new RectArea(49,"49",796,631,867,699);
    	
    	RectArea fifty=new RectArea(50,"50",867,630,943,703);
    	RectArea fiftyone=new RectArea(51,"51",944,631,1014,701);
    	RectArea fiftytwo=new RectArea(52,"52",1013,631,1085,700);
    	RectArea fiftythree=new RectArea(53,"53",1086,632,1162,700);
    	RectArea fiftyfour=new RectArea(54,"54",1163,631,1235,699);
    	RectArea fiftyfive=new RectArea(55,"55",1237,631,1313,698);
    	RectArea fiftysix=new RectArea(56,"56",1312,629,1386,699);
    	RectArea fiftyseven=new RectArea(57,"57",1387,627,1464,696);
    	RectArea fiftyeight=new RectArea(58,"58",1464,624,1541,694);
    	RectArea fiftynine=new RectArea(59,"59",1543,620,1614,693);
    	
    	RectArea sixty=new RectArea(60,"60",1618,622,1690,695);
    	RectArea sixtyone=new RectArea(61,"61",1690,620,1769,694);
    	RectArea sixtytwo=new RectArea(62,"62",1769,619,1848,693);
    	RectArea sixtythree=new RectArea(63,"63",1850,620,1921,688);
    	RectArea sixtyfour=new RectArea(64,"64",646,703,720,776);
    	RectArea sixtyfive=new RectArea(65,"65",723,704,793,774);
    	RectArea sixtysix=new RectArea(66,"66",1163,704,1236,771);
    	RectArea sixtyseven=new RectArea(67,"67",1239,706,1311,771);
    	RectArea sixtyeight=new RectArea(68,"68",1313,701,1389,770);
    	RectArea sixtynine=new RectArea(69,"69",1389,705,1459,768);
    	
    	RectArea seventy=new RectArea(70,"70",1464,700,1541,769);
    	RectArea seventyone=new RectArea(71,"71",1544,700,1618,765);
    	RectArea seventytwo=new RectArea(72,"72",1617,696,1691,764);
    	RectArea seventythree=new RectArea(73,"73",1693,696,1771,766);
    	RectArea seventyfour=new RectArea(74,"74",1771,696,1850,767);
    	RectArea seventyfive=new RectArea(75,"75",645,781,718,851);
    	RectArea seventysix=new RectArea(76,"76",721,779,794,850);
    	RectArea seventyseven=new RectArea(77,"77",1163,774,1238,851);
    	RectArea seventyeight=new RectArea(78,"78",1240,775,1310,849);
    	RectArea seventynine=new RectArea(79,"79",572,854,646,924);
    	
    	RectArea eighty=new RectArea(80,"80",647,856,721,927);
    	RectArea eightyone=new RectArea(81,"81",720,854,793,923);
    	RectArea eightytwo=new RectArea(82,"82",1165,851,1239,922);
    	RectArea eightythree=new RectArea(83,"83",1240,853,1310,924);
    	RectArea eightyfour=new RectArea(84,"84",199,929,270,998);
    	RectArea eightyfive=new RectArea(85,"85",273,929,344,997);
    	RectArea eightysix=new RectArea(86,"86",345,930,422,998);
    	RectArea eightyseven=new RectArea(87,"87",423,932,497,995);
    	RectArea eightyeight=new RectArea(88,"88",498,933,573,995);
    	RectArea eightynine=new RectArea(89,"89",572,926,644,999);
    	
    	RectArea ninety=new RectArea(90,"90",647,926,717,999);
    	RectArea ninetyone=new RectArea(91,"91",717,926,795,997);
    	RectArea ninetytwo=new RectArea(92,"92",1165,924,1238,996);
    	RectArea ninetythree=new RectArea(93,"93",1238,926,1313,997);
    	RectArea ninetyfour=new RectArea(94,"94",573,1001,646,1075);
    	RectArea ninetyfive=new RectArea(95,"95",649,1002,720,1073);
    	RectArea ninetysix=new RectArea(96,"96",720,1001,793,1071);
    	RectArea ninetyseven=new RectArea(97,"97",1165,998,1238,1071);
    	RectArea ninetyeight=new RectArea(98,"98",1239,999,1314,1071);
    	RectArea ninetynine=new RectArea(99,"99",572,1077,646,1148);
    	RectArea hundred=new RectArea(100,"100",647,1078,721,1149);
    	
    	RectArea onezeroone=new RectArea(101,"101",721,1075,796,1147);
    	RectArea onezerotwo=new RectArea(102,"102",1165,1073,1239,1145);
    	RectArea onezerothree=new RectArea(103,"103",1241,1073,1314,1145);
    	RectArea onezerofour=new RectArea(104,"104",572,1151,646,1223);
    	RectArea onezerofive=new RectArea(105,"105",647,1151,722,1225);
    	RectArea onezerosix=new RectArea(106,"106",719,1150,797,1224);
    	RectArea onezeroseven=new RectArea(107,"107",1170,1147,1239,1220);
    	RectArea onezeroeight=new RectArea(108,"108",1242,1148,1317,1224);
    	RectArea onezeronine=new RectArea(109,"109",573,1225,644,1295);
    	RectArea oneten=new RectArea(110,"110",645,1225,722,1295);
    	
    	RectArea oneeleven=new RectArea(111,"111",724,1227,797,1296);
    	RectArea onetwelve=new RectArea(112,"112",801,1229,869,1296);
    	RectArea onethirteen=new RectArea(113,"113",871,1229,943,1297);
    	RectArea onefourteen=new RectArea(114,"114",945,1230,1014,1297);
    	RectArea onefifteen=new RectArea(115,"115",1015,1230,1090,1298);
    	RectArea onesixteen=new RectArea(116,"116",1093,1230,1169,1295);
    	RectArea oneseventeen=new RectArea(117,"117",1169,1224,1240,1293);
    	RectArea oneeighteen=new RectArea(118,"118",1244,1225,1316,1292);
    	RectArea onenineteen=new RectArea(119,"119",1317,1227,1391,1295);
    	
    	RectArea onetwenty=new RectArea(120,"120",1393,1227,1467,1299);
    	RectArea onetwentyone=new RectArea(121,"121",1470,1227,1548,1298);
    	RectArea onetwentytwo=new RectArea(122,"122",570,1301,643,1371);
    	RectArea onetwentythree=new RectArea(123,"123",644,1302,721,1370);
    	RectArea onetwentyfour= new RectArea(124,"124",722,1299,798,1372);
    	RectArea onetwentyfive= new RectArea(125,"125",799,1299,872,1370);
    	RectArea onetwentysix= new RectArea(126,"126",874,1301,947,1368);
    	RectArea onetwentyseven= new RectArea(127,"127",948,1299,1016,1368);
    	RectArea onetwentyeight= new RectArea(128,"128",1019,1300,1089,1367);
    	RectArea onetwentynine= new RectArea(129,"129",1091,1299,1168,1369);
    	
    	RectArea onethirty= new RectArea(130,"130",1167,1298,1241,1368);
    	RectArea onethirtyone= new RectArea(131,"131",1242,1296,1318,1370);
    	RectArea onethirtytwo= new RectArea(132,"132",1319,1302,1397,1370);
    	RectArea onethirtythree= new RectArea(133,"133",1393,1299,1469,1372);
    	RectArea onethirtyfour= new RectArea(134,"134",1470,1299,1549,1374);
    	RectArea onethirtyfive=new RectArea(135,"135",1551,1300,1623,1373);
    	RectArea onethirtysix=new RectArea(136,"136",1624,1300,1704,1374);
    	RectArea onethirtyseven=new RectArea(137,"137",1704,1302,1782,1374);
    	RectArea onethirtyeight=new RectArea(138,"138",1781,1303,1854,1376);
    	RectArea onethirtynine=new RectArea(139,"139",197,1378,266,1452);
    	
    	RectArea onefourty=new RectArea(140,"140",269,1377,346,1452);
    	RectArea onefourtyone=new RectArea(141,"141",346,1378,418,1449);
    	RectArea onefourtytwo=new RectArea(142,"142",418,1378,494,1449);
    	RectArea onefourtythree=new RectArea(143,"143",497,1378,569,1452);
    	RectArea onefourtyfour=new RectArea(144,"144",570,1378,644,1448);
    	RectArea onefourtyfive=new RectArea(145,"145",646,1376,722,1447);
    	RectArea onefourtysix=new RectArea(146,"146",1321,1375,1395,1447);
    	RectArea onefourtyseven=new RectArea(147,"147",1397,1376,1471,1449);
    	RectArea onefourtyeight=new RectArea(148,"148",1472,1375,1548,1442);
    	RectArea onefourtynine=new RectArea(149,"149",1551,1375,1624,1445);
    	
    	RectArea onefifty=new RectArea(150,"150",1626,1379,1705,1449);
    	RectArea onefiftyone=new RectArea(151,"151",1706,1380,1784,1446);
    	RectArea onefiftytwo=new RectArea(152,"152",1785,1378,1858,1448);
    	RectArea onefiftythree=new RectArea(153,"153",1859,1379,1934,1447);
    	RectArea onefiftyfour=new RectArea(154,"154",121,1454,191,1522);
    	RectArea onefiftyfive=new RectArea(155,"155",192,1455,269,1521);
    	RectArea onefiftysix=new RectArea(156,"156",271,1455,344,1523);
    	RectArea onefiftyseven=new RectArea(157,"157",346,1456,420,1522);
    	RectArea onefiftyeight=new RectArea(158,"158",420,1455,496,1523);
    	RectArea onefiftynine=new RectArea(159,"159",500,1455,570,1524);
    	
    	RectArea onesixty=new RectArea(160,"160",570,1454,645,1524);
    	RectArea onesixtyone=new RectArea(161,"161",647,1453,719,1526);
    	RectArea onesixtytwo=new RectArea(162,"162",1322,1450,1398,1527);
    	RectArea onesixtythree=new RectArea(163,"163",1399,1449,1473,1527);
    	RectArea onesixtyfour=new RectArea(164,"164",499,1526,571,1599);
    	RectArea onesixtyfive=new RectArea(165,"165",573,1529,647,1602);
    	RectArea onesixtysix=new RectArea(166,"166",648,1528,722,1603);
    	RectArea onesixtyseven=new RectArea(167,"167",1323,1528,1398,1600);
    	RectArea onesixtyeight=new RectArea(168,"168",1398,1530,1474,1603);
    	RectArea onesixtynine=new RectArea(169,"169",572,1607,645,1676);
    	
    	RectArea oneseventy=new RectArea(170,"170",649,1609,720,1674);
    	RectArea oneseventyone=new RectArea(171,"171",1325,1608,1396,1679);
    	RectArea oneseventytwo=new RectArea(172,"172",1400,1611,1474,1677);
    	RectArea oneseventythree=new RectArea(173,"173",574,1686,642,1755);
    	RectArea oneseventyfour=new RectArea(174,"174",648,1688,722,1753);
    	RectArea oneseventyfive=new RectArea(175,"175",1325,1685,1400,1758);
    	RectArea oneseventysix=new RectArea(176,"176",1402,1684,1478,1757);
    	RectArea oneseventyseven=new RectArea(177,"177",574,1760,641,1830);
    	RectArea oneseventyeight=new RectArea(178,"178",644,1763,718,1831);
    	RectArea oneseventynine=new RectArea(179,"179",1327,1762,1400,1828);
    	
    	RectArea oneeighty=new RectArea(180,"180",1404,1764,1477,1829);
    	RectArea oneeightyone=new RectArea(181,"181",648,1834,721,1908);
    	RectArea oneeightytwo=new RectArea(182,"182",724,1837,794,1908);
    	RectArea oneeightythree=new RectArea(183,"183",797,1840,877,1911);
    	RectArea oneeightyfour=new RectArea(184,"184",1177,1837,1247,1909);
    	RectArea oneeightyfive=new RectArea(185,"185",1247,1840,1328,1905);
    	RectArea oneeightysix=new RectArea(186,"186",1330,1841,1404,1909);
    	RectArea oneeightyseven=new RectArea(187,"187",803,1913,873,1987);
    	RectArea oneeightyeight=new RectArea(188,"188",1179,1913,1247,1982);
    	
    	
    	RectArea study=new RectArea(189,"study",111,98,628,396);
    	RectArea hall= new RectArea(190,"hall",809,144,1222,609);
    	RectArea lounge =new RectArea(191,"lounge",1399,71,1953,542);
    	RectArea dinning= new RectArea(192,"dinning",1312,772,1957,1219);
    	RectArea kitchen= new RectArea(193,"kitchen",1489,1466,1960,1921);
    	RectArea ballroom= new RectArea(194,"ballroom",738,1388,1307,1814);
    	RectArea conservatory = new RectArea(195,"conservatory",104,1618,555,1923);
    	RectArea billiards= new RectArea(196,"billiards",110,1016,554,1357);
    	RectArea library=new RectArea(197,"library",182,551,570,925);
    	
    	
    	mAreaList.add(new RectArea(0,"filler",0,0,0,0));
    	
    	mAreaList.add(one);
    	mAreaList.add(two);
    	mAreaList.add(three);
    	mAreaList.add(four);
    	mAreaList.add(five);
    	mAreaList.add(six);
    	mAreaList.add(seven);
    	mAreaList.add(eight);
    	mAreaList.add(nine);
    	mAreaList.add(ten);
        
    	mAreaList.add(eleven);
    	mAreaList.add(twelve);
    	mAreaList.add(thirteen);
    	mAreaList.add(fourteen);
    	mAreaList.add(fifteen);
    	mAreaList.add(sixteen);
    	mAreaList.add(seventeen);
    	mAreaList.add(eighteen);
    	mAreaList.add(nineteen);
    	mAreaList.add(twenty);
        

    	mAreaList.add(twentyone);
    	mAreaList.add(twentytwo);
    	mAreaList.add(twentythree);
    	mAreaList.add(twentyfour);
    	mAreaList.add(twentyfive);
    	mAreaList.add(twentysix);
    	mAreaList.add(twentyseven);
    	mAreaList.add(twentyeight);
    	mAreaList.add(twentynine);
    	mAreaList.add(thirty);
        
    	mAreaList.add(thirtyone);
    	mAreaList.add(thirtytwo);
    	mAreaList.add(thirtythree);
    	mAreaList.add(thirtyfour);
    	mAreaList.add(thirtyfive);
    	mAreaList.add(thirtysix);
    	mAreaList.add(thirtyseven);
    	mAreaList.add(thirtyeight);
    	mAreaList.add(thirtynine);
    	mAreaList.add(fourty);
        
    	mAreaList.add(fourtyone);
    	mAreaList.add(fourtytwo);
    	mAreaList.add(fourtythree);
    	mAreaList.add(fourtyfour);
    	mAreaList.add(fourtyfive);
    	mAreaList.add(fourtysix);
    	mAreaList.add(fourtyseven);
    	mAreaList.add(fourtyeight);
    	mAreaList.add(fourtynine);
    	mAreaList.add(fifty);
        
    	mAreaList.add(fiftyone);
    	mAreaList.add(fiftytwo);
    	mAreaList.add(fiftythree);
    	mAreaList.add(fiftyfour);
    	mAreaList.add(fiftyfive);
    	mAreaList.add(fiftysix);
    	mAreaList.add(fiftyseven);
    	mAreaList.add(fiftyeight);
    	mAreaList.add(fiftynine);
    	mAreaList.add(sixty);
        
    	mAreaList.add(sixtyone);
    	mAreaList.add(sixtytwo);
    	mAreaList.add(sixtythree);
    	mAreaList.add(sixtyfour);
    	mAreaList.add(sixtyfive);
    	mAreaList.add(sixtysix);
    	mAreaList.add(sixtyseven);
    	mAreaList.add(sixtyeight);
    	mAreaList.add(sixtynine);
    	mAreaList.add(seventy);
        
    	mAreaList.add(seventyone);
    	mAreaList.add(seventytwo);
    	mAreaList.add(seventythree);
    	mAreaList.add(seventyfour);
    	mAreaList.add(seventyfive);
    	mAreaList.add(seventysix);
    	mAreaList.add(seventyseven);
    	mAreaList.add(seventyeight);
    	mAreaList.add(seventynine);
    	mAreaList.add(eighty);
        
    	mAreaList.add(eightyone);
    	mAreaList.add(eightytwo);
    	mAreaList.add(eightythree);
    	mAreaList.add(eightyfour);
    	mAreaList.add(eightyfive);
    	mAreaList.add(eightysix);
    	mAreaList.add(eightyseven);
    	mAreaList.add(eightyeight);
    	mAreaList.add(eightynine);
    	mAreaList.add(ninety);
        
    	mAreaList.add(ninetyone);
    	mAreaList.add(ninetytwo);
    	mAreaList.add(ninetythree);
    	mAreaList.add(ninetyfour);
    	mAreaList.add(ninetyfive);
    	mAreaList.add(ninetysix);
    	mAreaList.add(ninetyseven);
    	mAreaList.add(ninetyeight);
    	mAreaList.add(ninetynine);
    	mAreaList.add(hundred);
        
    	mAreaList.add(onezeroone);
    	mAreaList.add(onezerotwo);
    	mAreaList.add(onezerothree);
    	mAreaList.add(onezerofour);
    	mAreaList.add(onezerofive);
    	mAreaList.add(onezerosix);
    	mAreaList.add(onezeroseven);
    	mAreaList.add(onezeroeight);
    	mAreaList.add(onezeronine);
    	mAreaList.add(oneten);
        
    	mAreaList.add(oneeleven);
    	mAreaList.add(onetwelve);
    	mAreaList.add(onethirteen);
    	mAreaList.add(onefourteen);
    	mAreaList.add(onefifteen);
    	mAreaList.add(onesixteen);
    	mAreaList.add(oneseventeen);
    	mAreaList.add(oneeighteen);
    	mAreaList.add(onenineteen);
    	mAreaList.add(onetwenty);
        

    	mAreaList.add(onetwentyone);
    	mAreaList.add(onetwentytwo);
    	mAreaList.add(onetwentythree);
    	mAreaList.add(onetwentyfour);
    	mAreaList.add(onetwentyfive);
    	mAreaList.add(onetwentysix);
    	mAreaList.add(onetwentyseven);
    	mAreaList.add(onetwentyeight);
    	mAreaList.add(onetwentynine);
    	mAreaList.add(onethirty);
        
    	mAreaList.add(onethirtyone);
    	mAreaList.add(onethirtytwo);
    	mAreaList.add(onethirtythree);
    	mAreaList.add(onethirtyfour);
    	mAreaList.add(onethirtyfive);
    	mAreaList.add(onethirtysix);
    	mAreaList.add(onethirtyseven);
    	mAreaList.add(onethirtyeight);
    	mAreaList.add(onethirtynine);
    	mAreaList.add(onefourty);
        
    	mAreaList.add(onefourtyone);
    	mAreaList.add(onefourtytwo);
    	mAreaList.add(onefourtythree);
    	mAreaList.add(onefourtyfour);
    	mAreaList.add(onefourtyfive);
    	mAreaList.add(onefourtysix);
    	mAreaList.add(onefourtyseven);
    	mAreaList.add(onefourtyeight);
    	mAreaList.add(onefourtynine);
    	mAreaList.add(onefifty);
        
    	mAreaList.add(onefiftyone);
    	mAreaList.add(onefiftytwo);
    	mAreaList.add(onefiftythree);
    	mAreaList.add(onefiftyfour);
    	mAreaList.add(onefiftyfive);
    	mAreaList.add(onefiftysix);
    	mAreaList.add(onefiftyseven);
    	mAreaList.add(onefiftyeight);
    	mAreaList.add(onefiftynine);
    	mAreaList.add(onesixty);
        
    	mAreaList.add(onesixtyone);
    	mAreaList.add(onesixtytwo);
    	mAreaList.add(onesixtythree);
    	mAreaList.add(onesixtyfour);
    	mAreaList.add(onesixtyfive);
    	mAreaList.add(onesixtysix);
    	mAreaList.add(onesixtyseven);
    	mAreaList.add(onesixtyeight);
    	mAreaList.add(onesixtynine);
    	mAreaList.add(oneseventy);
        
    	mAreaList.add(oneseventyone);
    	mAreaList.add(oneseventytwo);
    	mAreaList.add(oneseventythree);
    	mAreaList.add(oneseventyfour);
    	mAreaList.add(oneseventyfive);
    	mAreaList.add(oneseventysix);
    	mAreaList.add(oneseventyseven);
    	mAreaList.add(oneseventyeight);
    	mAreaList.add(oneseventynine);
    	mAreaList.add(oneeighty);
        
    	mAreaList.add(oneeightyone);
    	mAreaList.add(oneeightytwo);
    	mAreaList.add(oneeightythree);
    	mAreaList.add(oneeightyfour);
    	mAreaList.add(oneeightyfive);
    	mAreaList.add(oneeightysix);
    	mAreaList.add(oneeightyseven);
    	mAreaList.add(oneeightyeight);
    	
    	mAreaList.add(study);
    	mAreaList.add(hall);
    	mAreaList.add(lounge);
    	mAreaList.add(dinning);
    	mAreaList.add(kitchen);
    	mAreaList.add(ballroom);
    	mAreaList.add(conservatory);
    	mAreaList.add(billiards);
    	mAreaList.add(library);
    	
    	
    	ArrayList<RectArea> neighboursof1=new ArrayList<RectArea>();
    	neighboursof1.add(three);
    	
    	ArrayList<RectArea> neighboursof2=new ArrayList<RectArea>();
    	neighboursof2.add(six);
    	
    	ArrayList<RectArea> neighboursof3=new ArrayList<RectArea>();
    	neighboursof3.add(four);
    	neighboursof3.add(seven);
    	neighboursof3.add(one);
    	
    	ArrayList<RectArea> neighboursof4=new ArrayList<RectArea>();
    	neighboursof4.add(three);
    	neighboursof4.add(eight);
    	
    	ArrayList<RectArea> neighboursof5=new ArrayList<RectArea>();
    	neighboursof5.add(six);
    	neighboursof5.add(nine);
    	
    	ArrayList<RectArea> neighboursof6=new ArrayList<RectArea>();
    	neighboursof6.add(five);
    	neighboursof6.add(ten);
    	neighboursof6.add(two);
    	
    	ArrayList<RectArea> neighboursof7=new ArrayList<RectArea>();
    	neighboursof7.add(eight);
    	neighboursof7.add(eleven);
    	neighboursof7.add(three);
    	
    	ArrayList<RectArea> neighboursof8=new ArrayList<RectArea>();
    	neighboursof8.add(seven);
    	neighboursof8.add(twelve);
    	neighboursof8.add(four);
    	
    	ArrayList<RectArea> neighboursof9=new ArrayList<RectArea>();
    	neighboursof9.add(ten);
    	neighboursof9.add(thirteen);
    	neighboursof9.add(five);
    	
    	ArrayList<RectArea> neighboursof10=new ArrayList<RectArea>();
    	neighboursof10.add(fourteen);
    	neighboursof10.add(six);
    	neighboursof10.add(nine);
    	
    	ArrayList<RectArea> neighboursof11=new ArrayList<RectArea>();
    	neighboursof11.add(seven);
    	neighboursof11.add(twelve);
    	neighboursof11.add(twentyone);
    	
    	ArrayList<RectArea> neighboursof12=new ArrayList<RectArea>();
    	neighboursof12.add(eight);
    	neighboursof12.add(eleven);
    	neighboursof12.add(twentytwo);
    	
    	ArrayList<RectArea> neighboursof13=new ArrayList<RectArea>();
    	neighboursof13.add(nine);
    	neighboursof13.add(fourteen);
    	neighboursof13.add(twentythree);
    	
    	ArrayList<RectArea> neighboursof14=new ArrayList<RectArea>();
    	neighboursof14.add(ten);
    	neighboursof14.add(thirteen);
    	neighboursof14.add(twentyfour);
    	
    	ArrayList<RectArea> neighboursof15=new ArrayList<RectArea>();
    	neighboursof15.add(sixteen);
    	neighboursof15.add(twentysix);
    	
    	ArrayList<RectArea> neighboursof16=new ArrayList<RectArea>();
    	neighboursof16.add(fifteen);
    	neighboursof16.add(twentyseven);
    	neighboursof16.add(seventeen);
    	
    	ArrayList<RectArea> neighboursof17=new ArrayList<RectArea>();
    	neighboursof17.add(sixteen);
    	neighboursof17.add(twentyeight);
    	neighboursof17.add(eighteen);
    	
    	ArrayList<RectArea> neighboursof18=new ArrayList<RectArea>();
    	neighboursof18.add(seventeen);
    	neighboursof18.add(twentynine);
    	neighboursof18.add(nineteen);
    	
    	ArrayList<RectArea> neighboursof19=new ArrayList<RectArea>();
    	neighboursof19.add(eighteen);
    	neighboursof19.add(thirty);
    	neighboursof19.add(twenty);
    	
    	ArrayList<RectArea> neighboursof20=new ArrayList<RectArea>();
    	neighboursof20.add(nineteen);
    	neighboursof20.add(thirtyone);
    	neighboursof20.add(twentyone);
    	neighboursof20.add(study);
    	
    	ArrayList<RectArea> neighboursof21=new ArrayList<RectArea>();
    	neighboursof21.add(twenty);
    	neighboursof21.add(thirtytwo);
    	neighboursof21.add(twentytwo);
    	neighboursof21.add(eleven);
    	
    	
    	ArrayList<RectArea> neighboursof22=new ArrayList<RectArea>();
    	neighboursof22.add(twelve);
    	neighboursof22.add(twentyone);
    	neighboursof22.add(thirtythree);
    	neighboursof22.add(hall);
    	
    	ArrayList<RectArea> neighboursof23=new ArrayList<RectArea>();
    	neighboursof23.add(thirteen);
    	neighboursof23.add(twentyfour);
    	neighboursof23.add(thirtyfour);
    	
    	ArrayList<RectArea> neighboursof24=new ArrayList<RectArea>();
    	neighboursof24.add(fourteen);
    	neighboursof24.add(twentythree);
    	neighboursof24.add(thirtyfive);
    	
    	ArrayList<RectArea> neighboursof25=new ArrayList<RectArea>();
    	neighboursof25.add(twentysix);
    	
    	ArrayList<RectArea> neighboursof26=new ArrayList<RectArea>();
    	neighboursof26.add(fifteen);
    	neighboursof26.add(twentyfive);
    	neighboursof26.add(twentyseven);
    	
    	ArrayList<RectArea> neighboursof27=new ArrayList<RectArea>();
    	neighboursof27.add(sixteen);
    	neighboursof27.add(twentysix);
    	neighboursof27.add(twentyeight);
    	
    	ArrayList<RectArea> neighboursof28=new ArrayList<RectArea>();
    	neighboursof28.add(seventeen);
    	neighboursof28.add(twentyseven);
    	neighboursof28.add(twentynine);
    	
    	ArrayList<RectArea> neighboursof29=new ArrayList<RectArea>();
    	neighboursof29.add(eighteen);
    	neighboursof29.add(twentyeight);
    	neighboursof29.add(thirty);
    	
    	ArrayList<RectArea> neighboursof30=new ArrayList<RectArea>();
    	neighboursof30.add(nineteen);
    	neighboursof30.add(twentynine);
    	neighboursof30.add(thirtyone);
    	
    	ArrayList<RectArea> neighboursof31=new ArrayList<RectArea>();
    	neighboursof31.add(twenty);
    	neighboursof31.add(thirty);
    	neighboursof31.add(thirtytwo);
    	neighboursof31.add(thirtysix);
    	
    	ArrayList<RectArea> neighboursof32=new ArrayList<RectArea>();
    	neighboursof32.add(twentyone);
    	neighboursof32.add(thirtyone);
    	neighboursof32.add(thirtythree);
    	neighboursof32.add(thirtyseven);
    	
    	ArrayList<RectArea> neighboursof33=new ArrayList<RectArea>();
    	neighboursof33.add(twentytwo);
    	neighboursof33.add(thirtytwo);
    	neighboursof33.add(thirtyeight);
    	
    	ArrayList<RectArea> neighboursof34=new ArrayList<RectArea>();
    	neighboursof34.add(twentythree);
    	neighboursof34.add(thirtyfive);
    	neighboursof34.add(thirtynine);
    	
    	
    	ArrayList<RectArea> neighboursof35=new ArrayList<RectArea>();
    	neighboursof35.add(twentyfour);
    	neighboursof35.add(thirtyfour);
    	neighboursof35.add(fourty);
    	
    	ArrayList<RectArea> neighboursof36=new ArrayList<RectArea>();
    	neighboursof36.add(thirtyone);
    	neighboursof36.add(thirtyseven);
    	
    	ArrayList<RectArea> neighboursof37=new ArrayList<RectArea>();
    	neighboursof37.add(thirtytwo);
    	neighboursof37.add(thirtysix);
    	neighboursof37.add(thirtyeight);
    	neighboursof37.add(fourtyseven);   	
    	
    	ArrayList<RectArea> neighboursof38=new ArrayList<RectArea>();
    	neighboursof38.add(thirtythree);
    	neighboursof38.add(thirtyseven);
    	neighboursof38.add(fourtyeight);
    	
    	ArrayList<RectArea> neighboursof39=new ArrayList<RectArea>();
    	neighboursof39.add(thirtyfour);
    	neighboursof39.add(fourty);
    	neighboursof39.add(fiftyfive);
    	
    	ArrayList<RectArea> neighboursof40=new ArrayList<RectArea>();
    	neighboursof40.add(thirtyfive);
    	neighboursof40.add(fiftysix);
    	neighboursof40.add(thirtynine);
    	neighboursof40.add(fourtyone);
    	
    	
    	ArrayList<RectArea> neighboursof41=new ArrayList<RectArea>();
    	neighboursof41.add(fourty);
    	neighboursof41.add(fourtytwo);
    	neighboursof41.add(fiftyseven);
    	neighboursof41.add(lounge);
    	
    	ArrayList<RectArea> neighboursof42=new ArrayList<RectArea>();
    	neighboursof42.add(fiftyeight);
    	neighboursof42.add(fourtyone);
    	neighboursof42.add(fourtythree);
    	
    	ArrayList<RectArea> neighboursof43=new ArrayList<RectArea>();
    	neighboursof43.add(fiftynine);
    	neighboursof43.add(fourtytwo);
    	neighboursof43.add(fourtyfour);
    	
    	ArrayList<RectArea> neighboursof44=new ArrayList<RectArea>();
    	neighboursof44.add(sixty);
    	neighboursof44.add(fourtythree);
    	neighboursof44.add(fourtyfive);
    	
    	ArrayList<RectArea> neighboursof45=new ArrayList<RectArea>();
    	neighboursof45.add(sixtyone);
    	neighboursof45.add(fourtyfour);
    	neighboursof45.add(fourtysix);
    
    	ArrayList<RectArea> neighboursof46=new ArrayList<RectArea>();
    	neighboursof46.add(fourtyfive);
    	neighboursof46.add(sixtytwo);
    	
    	ArrayList<RectArea> neighboursof47=new ArrayList<RectArea>();
    	neighboursof47.add(thirtyseven);
    	neighboursof47.add(fourtyeight);
    	neighboursof47.add(sixtyfour);
    	
    	ArrayList<RectArea> neighboursof48=new ArrayList<RectArea>();
    	neighboursof48.add(thirtyeight);
    	neighboursof48.add(sixtyfive);
    	neighboursof48.add(fourtyseven);
    	neighboursof48.add(fourtynine);
    	
    	
    	ArrayList<RectArea> neighboursof49=new ArrayList<RectArea>();
    	neighboursof49.add(fourtyeight);
    	neighboursof49.add(fifty);
    	
    	ArrayList<RectArea> neighboursof50=new ArrayList<RectArea>();
    	neighboursof50.add(fourtynine);
    	neighboursof50.add(fiftyone);
    	
    	ArrayList<RectArea> neighboursof51=new ArrayList<RectArea>();
    	neighboursof51.add(fifty);
    	neighboursof51.add(fiftytwo);
    	neighboursof51.add(hall);
    	
    	
    	ArrayList<RectArea> neighboursof52=new ArrayList<RectArea>();
    	neighboursof52.add(fiftyone);
    	neighboursof52.add(fiftythree);
    	neighboursof52.add(hall);
    	
    	ArrayList<RectArea> neighboursof53=new ArrayList<RectArea>();
    	neighboursof53.add(fiftytwo);
    	neighboursof53.add(fiftyfour);
    	
    	ArrayList<RectArea> neighboursof54=new ArrayList<RectArea>();
    	neighboursof54.add(fiftythree);
    	neighboursof54.add(fiftyfive);
    	neighboursof54.add(sixtysix);
    	
    	ArrayList<RectArea> neighboursof55=new ArrayList<RectArea>();
    	neighboursof55.add(fiftyfour);
    	neighboursof55.add(fiftysix);
    	neighboursof55.add(thirtynine);
       	neighboursof55.add(sixtyseven);
           	
    	ArrayList<RectArea> neighboursof56=new ArrayList<RectArea>();
    	neighboursof56.add(fourty);
    	neighboursof56.add(sixtyeight);
    	neighboursof56.add(fiftyfive);
       	neighboursof56.add(fiftyseven);
        
    	ArrayList<RectArea> neighboursof57=new ArrayList<RectArea>();
    	neighboursof57.add(fourtyone);
    	neighboursof57.add(sixtynine);
    	neighboursof57.add(fiftysix);
       	neighboursof57.add(fiftyeight);
        
    	ArrayList<RectArea> neighboursof58=new ArrayList<RectArea>();
    	neighboursof58.add(fourtytwo);
    	neighboursof58.add(seventy);
    	neighboursof58.add(fiftyseven);
    	neighboursof58.add(fiftynine);
    	
    	ArrayList<RectArea> neighboursof59=new ArrayList<RectArea>();
    	neighboursof59.add(fourtythree);
    	neighboursof59.add(seventyone);
    	neighboursof59.add(fiftyeight);
    	neighboursof59.add(sixty);
    	
    	ArrayList<RectArea> neighboursof60=new ArrayList<RectArea>();
    	neighboursof60.add(fourtyfour);
    	neighboursof60.add(seventytwo);
    	neighboursof60.add(fiftynine);
    	neighboursof60.add(sixtyone);
    
    	ArrayList<RectArea> neighboursof61=new ArrayList<RectArea>();
    	neighboursof61.add(fourtyfive);
    	neighboursof61.add(seventythree);
    	neighboursof61.add(sixty);
    	neighboursof61.add(sixtytwo);
    	
    	ArrayList<RectArea> neighboursof62=new ArrayList<RectArea>();
    	neighboursof62.add(fourtysix);
    	neighboursof62.add(seventyfour);
    	neighboursof62.add(sixtyone);
    	neighboursof62.add(sixtythree);
    	
    	ArrayList<RectArea> neighboursof63=new ArrayList<RectArea>();
    	neighboursof63.add(sixtytwo);
    	
    	ArrayList<RectArea> neighboursof64=new ArrayList<RectArea>();
    	neighboursof64.add(fourtyseven);
    	neighboursof64.add(seventyfive);
    	neighboursof64.add(sixtyfive);
    	neighboursof64.add(library);
    	
    	ArrayList<RectArea> neighboursof65=new ArrayList<RectArea>();
    	neighboursof65.add(fourtyeight);
    	neighboursof65.add(sixtyfour);
    	neighboursof65.add(seventysix);
    	
    	ArrayList<RectArea> neighboursof66=new ArrayList<RectArea>();
    	neighboursof66.add(fiftyfour);
    	neighboursof66.add(seventyseven);
    	neighboursof66.add(sixtyseven);
    	
    	ArrayList<RectArea> neighboursof67=new ArrayList<RectArea>();
    	neighboursof67.add(fiftyfive);
    	neighboursof67.add(seventyeight);
    	neighboursof67.add(sixtysix);
    	neighboursof67.add(sixtyeight);
    	
    	ArrayList<RectArea> neighboursof68=new ArrayList<RectArea>();
    	neighboursof68.add(fiftysix);
    	neighboursof68.add(sixtyseven);
    	neighboursof68.add(sixtynine);
    	
    	ArrayList<RectArea> neighboursof69=new ArrayList<RectArea>();
    	neighboursof69.add(fiftyseven);
    	neighboursof69.add(sixtyeight);
    	neighboursof69.add(seventy);
    	neighboursof69.add(dinning);
    	
    	ArrayList<RectArea> neighboursof70=new ArrayList<RectArea>();
    	neighboursof70.add(fiftyeight);
    	neighboursof70.add(sixtynine);
    	neighboursof70.add(seventyone);
    	
    	ArrayList<RectArea> neighboursof71=new ArrayList<RectArea>();
    	neighboursof71.add(fiftynine);
    	neighboursof71.add(seventy);
    	neighboursof71.add(seventytwo);
    	
    	ArrayList<RectArea> neighboursof72=new ArrayList<RectArea>();
    	neighboursof72.add(sixty);
    	neighboursof72.add(seventyone);
    	neighboursof72.add(seventythree);
    	
    	ArrayList<RectArea> neighboursof73=new ArrayList<RectArea>();
    	neighboursof73.add(sixtyone);
    	neighboursof73.add(seventytwo);
    	neighboursof73.add(seventyfour);
    	
    	ArrayList<RectArea> neighboursof74=new ArrayList<RectArea>();
    	neighboursof74.add(sixtytwo);
    	neighboursof74.add(seventythree);
    	
    	ArrayList<RectArea> neighboursof75=new ArrayList<RectArea>();
    	neighboursof75.add(sixtyfour);
    	neighboursof75.add(eighty);
    	neighboursof75.add(seventysix);
    	
    	ArrayList<RectArea> neighboursof76=new ArrayList<RectArea>();
    	neighboursof76.add(sixtyfive);
    	neighboursof76.add(eightyone);
    	neighboursof76.add(seventyfive);
    	
    	ArrayList<RectArea> neighboursof77=new ArrayList<RectArea>();
    	neighboursof77.add(sixtysix);
    	neighboursof77.add(eightytwo);
    	neighboursof77.add(seventyeight);
    	
    	ArrayList<RectArea> neighboursof78=new ArrayList<RectArea>();
    	neighboursof78.add(sixtyseven);
    	neighboursof78.add(eightythree);
    	neighboursof78.add(seventyseven);
    	
    	ArrayList<RectArea> neighboursof79=new ArrayList<RectArea>();
    	neighboursof79.add(eighty);
    	neighboursof79.add(eightynine);
    	
    	ArrayList<RectArea> neighboursof80=new ArrayList<RectArea>();
    	neighboursof80.add(seventyfive);
    	neighboursof80.add(seventynine);
    	neighboursof80.add(ninety);
    	neighboursof80.add(eightyone);
    	    	
    	ArrayList<RectArea> neighboursof81=new ArrayList<RectArea>();
    	neighboursof81.add(seventysix);
    	neighboursof81.add(ninetyone);
    	neighboursof81.add(eighty);
    	
    	ArrayList<RectArea> neighboursof82=new ArrayList<RectArea>();
    	neighboursof82.add(seventyseven);
    	neighboursof82.add(ninetytwo);
    	neighboursof82.add(eightythree);
    	
    	ArrayList<RectArea> neighboursof83=new ArrayList<RectArea>();
    	neighboursof83.add(seventyeight);
    	neighboursof83.add(ninetythree);
    	neighboursof83.add(eightytwo);
    	
    	ArrayList<RectArea> neighboursof84=new ArrayList<RectArea>();
    	neighboursof84.add(eightyfive);
    	neighboursof84.add(billiards);
    	
    	ArrayList<RectArea> neighboursof85=new ArrayList<RectArea>();
    	neighboursof85.add(eightyfour);
    	neighboursof85.add(eightysix);
    	
    	ArrayList<RectArea> neighboursof86=new ArrayList<RectArea>();
    	neighboursof86.add(eightyfive);
    	neighboursof86.add(eightyseven);
    	neighboursof86.add(library);
    	
    	ArrayList<RectArea> neighboursof87=new ArrayList<RectArea>();
    	neighboursof87.add(eightysix);
    	neighboursof87.add(eightyeight);
    	
    	ArrayList<RectArea> neighboursof88=new ArrayList<RectArea>();
    	neighboursof88.add(eightyseven);
    	neighboursof88.add(eightynine);
    	
    	ArrayList<RectArea> neighboursof89=new ArrayList<RectArea>();
    	neighboursof89.add(seventynine);
    	neighboursof89.add(ninetyfour);
    	neighboursof89.add(eightyeight);
    	neighboursof89.add(ninety);
    	
    	ArrayList<RectArea> neighboursof90=new ArrayList<RectArea>();
    	neighboursof90.add(eighty);
    	neighboursof90.add(ninetyfive);
    	neighboursof90.add(eightynine);
    	neighboursof90.add(ninetyone);
    	
    	ArrayList<RectArea> neighboursof91=new ArrayList<RectArea>();
    	neighboursof91.add(eightyone);
    	neighboursof91.add(ninetysix);
    	neighboursof91.add(ninety);
    	
    	ArrayList<RectArea> neighboursof92=new ArrayList<RectArea>();
    	neighboursof92.add(eightytwo);
    	neighboursof92.add(ninetyseven);
    	neighboursof92.add(ninetythree);
    	
    	ArrayList<RectArea> neighboursof93=new ArrayList<RectArea>();
    	neighboursof93.add(eightythree);
    	neighboursof93.add(ninetyeight);
    	neighboursof93.add(ninetytwo);
    	
    	ArrayList<RectArea> neighboursof94=new ArrayList<RectArea>();
    	neighboursof94.add(eightynine);
    	neighboursof94.add(ninetynine);
    	neighboursof94.add(ninetyfive);
    	
    	ArrayList<RectArea> neighboursof95=new ArrayList<RectArea>();
    	neighboursof95.add(ninety);
    	neighboursof95.add(hundred);
    	neighboursof95.add(ninetyfour);
    	neighboursof95.add(ninetysix);
    	
    	ArrayList<RectArea> neighboursof96=new ArrayList<RectArea>();
    	neighboursof96.add(ninetyone);
    	neighboursof96.add(onezeroone);
    	neighboursof96.add(ninetyfive);
    	
    	ArrayList<RectArea> neighboursof97=new ArrayList<RectArea>();
    	neighboursof97.add(ninetytwo);
    	neighboursof97.add(onezerotwo);
    	neighboursof97.add(ninetyeight);
    	
    	ArrayList<RectArea> neighboursof98=new ArrayList<RectArea>();
    	neighboursof98.add(ninetythree);
    	neighboursof98.add(onezerothree);
    	neighboursof98.add(ninetyseven);
    	neighboursof98.add(dinning);
    	
    	ArrayList<RectArea> neighboursof99=new ArrayList<RectArea>();
    	neighboursof99.add(ninetyfour);
    	neighboursof99.add(onezerofour);
    	neighboursof99.add(hundred);
    	
    	ArrayList<RectArea> neighboursof100=new ArrayList<RectArea>();
    	neighboursof100.add(ninetyfive);
    	neighboursof100.add(onezerofive);
    	neighboursof100.add(ninetynine);
    	neighboursof100.add(onezeroone);
    	
    	ArrayList<RectArea> neighboursof101=new ArrayList<RectArea>();
    	neighboursof101.add(ninetysix);
    	neighboursof101.add(onezerosix);
    	neighboursof101.add(hundred);
    	
    	ArrayList<RectArea> neighboursof102=new ArrayList<RectArea>();
    	neighboursof102.add(ninetyseven);
    	neighboursof102.add(onezeroseven);
    	neighboursof102.add(onezerothree);
    	
    	ArrayList<RectArea> neighboursof103=new ArrayList<RectArea>();
    	neighboursof103.add(ninetyeight);
    	neighboursof103.add(onezeroeight);
    	neighboursof103.add(onezerotwo);
    	
    	ArrayList<RectArea> neighboursof104=new ArrayList<RectArea>();
    	neighboursof104.add(ninetynine);
    	neighboursof104.add(onezeronine);
    	neighboursof104.add(onezerofive);
    	
    	ArrayList<RectArea> neighboursof105=new ArrayList<RectArea>();
    	neighboursof105.add(hundred);
    	neighboursof105.add(oneten);
    	neighboursof105.add(onezerofour);
    	neighboursof105.add(onezerosix);
    	
    	ArrayList<RectArea> neighboursof106=new ArrayList<RectArea>();
    	neighboursof106.add(onezeroone);
    	neighboursof106.add(oneeleven);
    	neighboursof106.add(onezerofive);
    	
    	ArrayList<RectArea> neighboursof107=new ArrayList<RectArea>();
    	neighboursof107.add(onezerotwo);
    	neighboursof107.add(onezeroeight);
    	neighboursof107.add(oneseventeen);
    	
    	ArrayList<RectArea> neighboursof108=new ArrayList<RectArea>();
    	neighboursof108.add(onezerothree);
    	neighboursof108.add(onezeroseven);
    	neighboursof108.add(oneeighteen);
    	
    	ArrayList<RectArea> neighboursof109=new ArrayList<RectArea>();
    	neighboursof109.add(onezerofour);
    	neighboursof109.add(onetwentytwo);
    	neighboursof109.add(oneten);
    	neighboursof109.add(billiards);
    	
    	ArrayList<RectArea> neighboursof110=new ArrayList<RectArea>();
    	neighboursof110.add(onezerofive);
    	neighboursof110.add(onetwentythree);
    	neighboursof110.add(onezeronine);
    	neighboursof110.add(oneeleven);
    	
    	ArrayList<RectArea> neighboursof111=new ArrayList<RectArea>();
    	neighboursof111.add(onezerosix);
    	neighboursof111.add(onetwentyfour);
    	neighboursof111.add(oneten);
    	neighboursof111.add(onetwelve);
    	
    	ArrayList<RectArea> neighboursof112=new ArrayList<RectArea>();
    	neighboursof112.add(onetwentyfive);
    	neighboursof112.add(oneeleven);
    	neighboursof112.add(onethirteen);
    	
    	ArrayList<RectArea> neighboursof113=new ArrayList<RectArea>();
    	neighboursof113.add(onetwentysix);
    	neighboursof113.add(onetwelve);
    	neighboursof113.add(onefourteen);
    	
    	ArrayList<RectArea> neighboursof114=new ArrayList<RectArea>();
    	neighboursof114.add(onetwentyseven);
    	neighboursof114.add(onethirteen);
    	neighboursof114.add(onefifteen);
    	
    	ArrayList<RectArea> neighboursof115=new ArrayList<RectArea>();
    	neighboursof115.add(onetwentyeight);
    	neighboursof115.add(onefourteen);
    	neighboursof115.add(onesixteen);
    	
    	ArrayList<RectArea> neighboursof116=new ArrayList<RectArea>();
    	neighboursof116.add(onetwentynine);
    	neighboursof116.add(onefifteen);
    	neighboursof116.add(oneseventeen);
    	
    	ArrayList<RectArea> neighboursof117=new ArrayList<RectArea>();
    	neighboursof117.add(onethirty);
    	neighboursof117.add(onezeroseven);
    	neighboursof117.add(onesixteen);
    	neighboursof117.add(onenineteen);
    	
    	ArrayList<RectArea> neighboursof118=new ArrayList<RectArea>();
    	neighboursof118.add(onezeroeight);
    	neighboursof118.add(onethirtyone);
    	neighboursof118.add(onenineteen);
    	neighboursof118.add(oneseventeen);
    	
    	ArrayList<RectArea> neighboursof119=new ArrayList<RectArea>();
    	neighboursof119.add(onethirtytwo);
    	neighboursof119.add(oneeighteen);
    	neighboursof119.add(onetwenty);
    	
    	ArrayList<RectArea> neighboursof120=new ArrayList<RectArea>();
    	neighboursof120.add(onethirtythree);
    	neighboursof120.add(onenineteen);
    	neighboursof120.add(onetwentyone);
    	
    	ArrayList<RectArea> neighboursof121=new ArrayList<RectArea>();
    	neighboursof121.add(onetwenty);
    	neighboursof121.add(onethirtyfour);
    	
    	ArrayList<RectArea> neighboursof122=new ArrayList<RectArea>();
    	neighboursof122.add(onezeronine);
    	neighboursof122.add(onefourtyfour);
    	neighboursof122.add(onetwentythree);
    	
    	ArrayList<RectArea> neighboursof123=new ArrayList<RectArea>();
    	neighboursof123.add(oneten);
    	neighboursof123.add(onefourtyfive);
    	neighboursof123.add(onetwentytwo);
    	neighboursof123.add(onetwentyfour);
    	
    	ArrayList<RectArea> neighboursof124=new ArrayList<RectArea>();
    	neighboursof124.add(oneeleven);
    	neighboursof124.add(onetwentythree);
    	neighboursof124.add(onetwentyfive);
    	
    	ArrayList<RectArea> neighboursof125=new ArrayList<RectArea>();
    	neighboursof125.add(onetwelve);
    	neighboursof125.add(onetwentyfour);
    	neighboursof125.add(onetwentysix);
    	neighboursof125.add(ballroom);
    	
    	ArrayList<RectArea> neighboursof126=new ArrayList<RectArea>();
    	neighboursof126.add(onethirteen);
    	neighboursof126.add(onetwentyfive);
    	neighboursof126.add(onetwentyseven);
    	
    	ArrayList<RectArea> neighboursof127=new ArrayList<RectArea>();
    	neighboursof127.add(onefourteen);
    	neighboursof127.add(onetwentysix);
    	neighboursof127.add(onetwentyeight);
    	
    	ArrayList<RectArea> neighboursof128=new ArrayList<RectArea>();
    	neighboursof128.add(onefifteen);
    	neighboursof128.add(onetwentyseven);
    	neighboursof128.add(onetwentynine);
    	
    	ArrayList<RectArea> neighboursof129=new ArrayList<RectArea>();
    	neighboursof129.add(onesixteen);
    	neighboursof129.add(onetwentyeight);
    	neighboursof129.add(onethirty);
    	
    	ArrayList<RectArea> neighboursof130=new ArrayList<RectArea>();
    	neighboursof130.add(oneseventeen);
    	neighboursof130.add(onetwentynine);
    	neighboursof130.add(onethirtyone);
    	neighboursof130.add(ballroom);
    	
    	ArrayList<RectArea> neighboursof131=new ArrayList<RectArea>();
    	neighboursof131.add(oneeighteen);
    	neighboursof131.add(onethirty);
    	neighboursof131.add(onethirtytwo);
    	
    	ArrayList<RectArea> neighboursof132=new ArrayList<RectArea>();
    	neighboursof132.add(onenineteen);
    	neighboursof132.add(onefourtysix);
    	neighboursof132.add(onethirtythree);
    	neighboursof132.add(onethirtyone);
    	
    	ArrayList<RectArea> neighboursof133=new ArrayList<RectArea>();
    	neighboursof133.add(onetwenty);
    	neighboursof133.add(onefourtyseven);
    	neighboursof133.add(onethirtytwo);
    	neighboursof133.add(onethirtyfour);
    	
    	
    	ArrayList<RectArea> neighboursof134=new ArrayList<RectArea>();
    	neighboursof134.add(onetwentyone);
    	neighboursof134.add(onefourtyeight);
    	neighboursof134.add(onethirtythree);
    	neighboursof134.add(onethirtyfive);
    	
    	
    	ArrayList<RectArea> neighboursof135=new ArrayList<RectArea>();
    	neighboursof135.add(onefourtynine);
    	neighboursof135.add(onethirtyfour);
    	neighboursof135.add(onethirtysix);
    	
    	ArrayList<RectArea> neighboursof136=new ArrayList<RectArea>();
    	neighboursof136.add(onefifty);
    	neighboursof136.add(onethirtyseven);
    	neighboursof136.add(onethirtyfive);
    	
    	ArrayList<RectArea> neighboursof137=new ArrayList<RectArea>();
    	neighboursof137.add(onefiftyone);
    	neighboursof137.add(onethirtysix);
    	neighboursof137.add(onethirtyeight);
    	
    	ArrayList<RectArea> neighboursof138=new ArrayList<RectArea>();
    	neighboursof138.add(onefiftytwo);
    	neighboursof138.add(onethirtyseven);
    	
    	ArrayList<RectArea> neighboursof139=new ArrayList<RectArea>();
    	neighboursof139.add(onefourty);
    	neighboursof139.add(onefiftyfive);
    	
    	ArrayList<RectArea> neighboursof140=new ArrayList<RectArea>();
    	neighboursof140.add(onefiftysix);
    	neighboursof140.add(onethirtynine);
    	neighboursof140.add(onefourtyone);
    	
    	
    	ArrayList<RectArea> neighboursof141=new ArrayList<RectArea>();
    	neighboursof141.add(onefourty);
    	neighboursof141.add(onefourtytwo);
    	neighboursof141.add(onefiftyseven);
    	
    	ArrayList<RectArea> neighboursof142=new ArrayList<RectArea>();
    	neighboursof142.add(onefiftyeight);
    	neighboursof142.add(onefourtyone);
    	neighboursof142.add(onefourtythree);
    	
    	ArrayList<RectArea> neighboursof143=new ArrayList<RectArea>();
    	neighboursof143.add(onefiftynine);
    	neighboursof143.add(onefourtytwo);
    	neighboursof143.add(onefourtyfour);
    	
    	ArrayList<RectArea> neighboursof144=new ArrayList<RectArea>();
    	neighboursof144.add(onesixty);
    	neighboursof144.add(onefourtythree);
    	neighboursof144.add(onefourtyfive);
    	neighboursof144.add(onetwentytwo);
    	
    	ArrayList<RectArea> neighboursof145=new ArrayList<RectArea>();
    	neighboursof145.add(onesixtyone);
    	neighboursof145.add(onefourtyfour);
    	neighboursof145.add(onetwentythree);
    
    	ArrayList<RectArea> neighboursof146=new ArrayList<RectArea>();
    	neighboursof146.add(onefourtyseven);
    	neighboursof146.add(onesixtytwo);
    	neighboursof146.add(onethirtytwo);
    	
    	
    	ArrayList<RectArea> neighboursof147=new ArrayList<RectArea>();
    	neighboursof147.add(onethirtythree);
    	neighboursof147.add(onefourtyeight);
    	neighboursof147.add(onefourtysix);
    	neighboursof147.add(onesixtythree);
    	
    	ArrayList<RectArea> neighboursof148=new ArrayList<RectArea>();
    	neighboursof148.add(onethirtyfour);
    	neighboursof148.add(onefourtyseven);
    	neighboursof148.add(onefourtynine);
    	
    	
    	ArrayList<RectArea> neighboursof149=new ArrayList<RectArea>();
    	neighboursof149.add(onefourtyeight);
    	neighboursof149.add(onethirtyfive);
    	neighboursof149.add(onefifty);
    	neighboursof149.add(kitchen);
    	
    	ArrayList<RectArea> neighboursof150=new ArrayList<RectArea>();
    	neighboursof150.add(onefourtynine);
    	neighboursof150.add(onefiftyone);
    	neighboursof150.add(onethirtysix);
    	
    	ArrayList<RectArea> neighboursof151=new ArrayList<RectArea>();
    	neighboursof151.add(onefifty);
    	neighboursof151.add(onefiftytwo);
    	neighboursof151.add(onethirtyseven);
    	
    	ArrayList<RectArea> neighboursof152=new ArrayList<RectArea>();
    	neighboursof152.add(onefiftyone);
    	neighboursof152.add(onefiftythree);
    	neighboursof152.add(onethirtyeight);
    	
    	ArrayList<RectArea> neighboursof153=new ArrayList<RectArea>();
    	neighboursof153.add(onefiftytwo);
    	
    	ArrayList<RectArea> neighboursof154=new ArrayList<RectArea>();
    	neighboursof154.add(onefiftyfive);
    	
    	ArrayList<RectArea> neighboursof155=new ArrayList<RectArea>();
    	neighboursof155.add(onefiftysix);
    	neighboursof155.add(onefiftyfour);
    	neighboursof155.add(onethirtynine);
    	
    	ArrayList<RectArea> neighboursof156=new ArrayList<RectArea>();
    	neighboursof156.add(onefourty);
    	neighboursof156.add(onefiftyfive);
    	neighboursof156.add(onefiftyseven);
    	
    	ArrayList<RectArea> neighboursof157=new ArrayList<RectArea>();
    	neighboursof157.add(onefourtyone);
    	neighboursof157.add(onefiftysix);
    	neighboursof157.add(onefiftyeight);
    	
    	ArrayList<RectArea> neighboursof158=new ArrayList<RectArea>();
    	neighboursof158.add(onefourtytwo);
    	neighboursof158.add(onefiftyseven);
    	neighboursof158.add(onefiftynine);
    	
    	ArrayList<RectArea> neighboursof159=new ArrayList<RectArea>();
    	neighboursof159.add(onefourtythree);
    	neighboursof159.add(onesixtyfour);
    	neighboursof159.add(onefiftyeight);
    	neighboursof159.add(onesixty);
    	
    	ArrayList<RectArea> neighboursof160=new ArrayList<RectArea>();
    	neighboursof160.add(onefourtyfour);
    	neighboursof160.add(onesixtyfive);
    	neighboursof160.add(onefiftynine);
    	neighboursof160.add(onesixtyone);
    	
    	ArrayList<RectArea> neighboursof161=new ArrayList<RectArea>();
    	neighboursof161.add(onefourtyfive);
    	neighboursof161.add(onesixty);
    	neighboursof161.add(onesixtysix);
    	
    	ArrayList<RectArea> neighboursof162=new ArrayList<RectArea>();
    	neighboursof162.add(onesixtythree);
    	neighboursof162.add(onefourtysix);
    	neighboursof162.add(onesixtyseven);
    	
    	ArrayList<RectArea> neighboursof163=new ArrayList<RectArea>();
    	neighboursof163.add(onefourtyseven);
    	neighboursof163.add(onesixtytwo);
    	neighboursof163.add(onesixtyeight);
    	
    	ArrayList<RectArea> neighboursof164=new ArrayList<RectArea>();
    	neighboursof164.add(onefiftynine);
    	neighboursof164.add(onesixtyfive);
    	neighboursof164.add(conservatory);
    
    	ArrayList<RectArea> neighboursof165=new ArrayList<RectArea>();
    	neighboursof165.add(onesixty);
    	neighboursof165.add(onesixtynine);
    	neighboursof165.add(onesixtyfour);
    	neighboursof165.add(onesixtysix);
    	
    	ArrayList<RectArea> neighboursof166=new ArrayList<RectArea>();
    	neighboursof166.add(onesixtyone);
    	neighboursof166.add(oneseventy);
    	neighboursof166.add(onesixtyfive);
    	neighboursof166.add(ballroom);
    	
    	ArrayList<RectArea> neighboursof167=new ArrayList<RectArea>();
    	neighboursof167.add(onesixtytwo);
    	neighboursof167.add(oneseventyone);
    	neighboursof167.add(onesixtyeight);
    	neighboursof167.add(ballroom);
    	
    	ArrayList<RectArea> neighboursof168=new ArrayList<RectArea>();
    	neighboursof168.add(onesixtythree);
    	neighboursof168.add(oneseventytwo);
    	neighboursof168.add(onesixtyseven);
    	
    	ArrayList<RectArea> neighboursof169=new ArrayList<RectArea>();
    	neighboursof169.add(onesixtyfive);
    	neighboursof169.add(oneseventythree);
    	neighboursof169.add(oneseventy);
    	
    	ArrayList<RectArea> neighboursof170=new ArrayList<RectArea>();
    	neighboursof170.add(oneseventyfour);
    	neighboursof170.add(onesixtynine);
    	neighboursof170.add(onesixtysix);
    	
    	ArrayList<RectArea> neighboursof171=new ArrayList<RectArea>();
    	neighboursof171.add(onesixtyseven);
    	neighboursof171.add(oneseventyfive);
    	neighboursof171.add(oneseventytwo);
    	
    	ArrayList<RectArea> neighboursof172=new ArrayList<RectArea>();
    	neighboursof172.add(oneseventyone);
    	neighboursof172.add(onesixtyeight);
    	neighboursof172.add(oneseventysix);
    	
    	ArrayList<RectArea> neighboursof173=new ArrayList<RectArea>();
    	neighboursof173.add(onesixtynine);
    	neighboursof173.add(oneseventyseven);
    	neighboursof173.add(oneseventyfour);
    	
    	ArrayList<RectArea> neighboursof174=new ArrayList<RectArea>();
    	neighboursof174.add(oneseventythree);
    	neighboursof174.add(oneseventy);
    	neighboursof174.add(oneseventyeight);
    	
    	ArrayList<RectArea> neighboursof175=new ArrayList<RectArea>();
    	neighboursof175.add(oneseventyone);
    	neighboursof175.add(oneseventynine);
    	neighboursof175.add(oneseventysix);
    	
    	ArrayList<RectArea> neighboursof176=new ArrayList<RectArea>();
    	neighboursof176.add(oneseventyfive);
    	neighboursof176.add(oneseventytwo);
    	neighboursof176.add(oneeighty);
    	
    	ArrayList<RectArea> neighboursof177=new ArrayList<RectArea>();
    	neighboursof177.add(oneseventythree);
    	neighboursof177.add(oneseventyeight);
    	
    	ArrayList<RectArea> neighboursof178=new ArrayList<RectArea>();
    	neighboursof178.add(oneseventyfour);
    	neighboursof178.add(oneeightyone);
    	neighboursof178.add(oneseventyseven);
    	
    	ArrayList<RectArea> neighboursof179=new ArrayList<RectArea>();
    	neighboursof179.add(oneseventyfive);
    	neighboursof179.add(oneeightysix);
    	neighboursof179.add(oneeighty);
    	
    	ArrayList<RectArea> neighboursof180=new ArrayList<RectArea>();
    	neighboursof180.add(oneseventynine);
    	neighboursof180.add(oneseventysix);
    	
    	ArrayList<RectArea> neighboursof181=new ArrayList<RectArea>();
    	neighboursof181.add(oneseventyeight);
    	neighboursof181.add(oneeightytwo);
    	
    	ArrayList<RectArea> neighboursof182=new ArrayList<RectArea>();
    	neighboursof182.add(oneeightyone);
    	neighboursof182.add(oneeightythree);
    	
    	ArrayList<RectArea> neighboursof183=new ArrayList<RectArea>();
    	neighboursof183.add(oneeightytwo);
    	neighboursof183.add(oneeightyseven);
    	
    	ArrayList<RectArea> neighboursof184=new ArrayList<RectArea>();
    	neighboursof184.add(oneeightyfive);
    	neighboursof184.add(oneeightyeight);
    	
    	ArrayList<RectArea> neighboursof185=new ArrayList<RectArea>();
    	neighboursof185.add(oneeightyfour);
    	neighboursof185.add(oneeightysix);
    	
    	ArrayList<RectArea> neighboursof186=new ArrayList<RectArea>();
    	neighboursof186.add(oneseventynine);
    	neighboursof186.add(oneeightyfive);
    	
    	ArrayList<RectArea> neighboursof187=new ArrayList<RectArea>();
    	neighboursof187.add(oneeightythree);
    	
    	ArrayList<RectArea> neighboursof188=new ArrayList<RectArea>();
    	neighboursof188.add(oneeightyfour);
    	
    	ArrayList<RectArea> neighboursof189=new ArrayList<RectArea>();
    	neighboursof189.add(twenty);
    	neighboursof189.add(kitchen);
    	
    	ArrayList<RectArea> neighboursof190=new ArrayList<RectArea>();
    	neighboursof190.add(twentytwo);
    	neighboursof190.add(fiftyone);
    	neighboursof190.add(fiftytwo);
    	
    	ArrayList<RectArea> neighboursof191=new ArrayList<RectArea>();
    	neighboursof191.add(fourtyone);
    	neighboursof191.add(conservatory);
    	
    	ArrayList<RectArea> neighboursof192=new ArrayList<RectArea>();
    	neighboursof192.add(sixtynine);
    	neighboursof192.add(ninetyeight);
    	
    	ArrayList<RectArea> neighboursof193=new ArrayList<RectArea>();
    	neighboursof193.add(onefourtynine);
    	neighboursof193.add(study);
    	
    	ArrayList<RectArea> neighboursof194=new ArrayList<RectArea>();
    	neighboursof194.add(onesixtysix);
    	neighboursof194.add(onesixtyseven);
    	neighboursof194.add(onethirty);
    	neighboursof194.add(onetwentyfive);
    	
    	
    	ArrayList<RectArea> neighboursof195=new ArrayList<RectArea>();
    	neighboursof195.add(onesixtyfour);
    	neighboursof195.add(lounge);
    	
    	ArrayList<RectArea> neighboursof196=new ArrayList<RectArea>();
    	neighboursof196.add(onezeronine);
    	neighboursof196.add(eightyfour);
    	
    	ArrayList<RectArea> neighboursof197=new ArrayList<RectArea>();
    	neighboursof197.add(eightysix);
    	neighboursof197.add(sixtyfour);    	
    	
    	neighbours.add(new ArrayList<RectArea>());
    	neighbours.add(neighboursof1);
    	neighbours.add(neighboursof2);
    	neighbours.add(neighboursof3);
    	neighbours.add(neighboursof4);
    	neighbours.add(neighboursof5);
    	neighbours.add(neighboursof6);
    	neighbours.add(neighboursof7);
    	neighbours.add(neighboursof8);
    	neighbours.add(neighboursof9);
    	neighbours.add(neighboursof10);
    	
    	neighbours.add(neighboursof11);
    	neighbours.add(neighboursof12);
    	neighbours.add(neighboursof13);
    	neighbours.add(neighboursof14);
    	neighbours.add(neighboursof15);
    	neighbours.add(neighboursof16);
    	neighbours.add(neighboursof17);
    	neighbours.add(neighboursof18);
    	neighbours.add(neighboursof19);
    	neighbours.add(neighboursof20);
    	
    	neighbours.add(neighboursof21);
    	neighbours.add(neighboursof22);
    	neighbours.add(neighboursof23);
    	neighbours.add(neighboursof24);
    	neighbours.add(neighboursof25);
    	neighbours.add(neighboursof26);
    	neighbours.add(neighboursof27);
    	neighbours.add(neighboursof28);
    	neighbours.add(neighboursof29);
    	neighbours.add(neighboursof30);
    	
    	neighbours.add(neighboursof31);
    	neighbours.add(neighboursof32);
    	neighbours.add(neighboursof33);
    	neighbours.add(neighboursof34);
    	neighbours.add(neighboursof35);
    	neighbours.add(neighboursof36);
    	neighbours.add(neighboursof37);
    	neighbours.add(neighboursof38);
    	neighbours.add(neighboursof39);
    	neighbours.add(neighboursof40);
    	
    	neighbours.add(neighboursof41);
    	neighbours.add(neighboursof42);
    	neighbours.add(neighboursof43);
    	neighbours.add(neighboursof44);
    	neighbours.add(neighboursof45);
    	neighbours.add(neighboursof46);
    	neighbours.add(neighboursof47);
    	neighbours.add(neighboursof48);
    	neighbours.add(neighboursof49);
    	neighbours.add(neighboursof50);
    	
    	neighbours.add(neighboursof51);
    	neighbours.add(neighboursof52);
    	neighbours.add(neighboursof53);
    	neighbours.add(neighboursof54);
    	neighbours.add(neighboursof55);
    	neighbours.add(neighboursof56);
    	neighbours.add(neighboursof57);
    	neighbours.add(neighboursof58);
    	neighbours.add(neighboursof59);
    	neighbours.add(neighboursof60);
    	
    	neighbours.add(neighboursof61);
    	neighbours.add(neighboursof62);
    	neighbours.add(neighboursof63);
    	neighbours.add(neighboursof64);
    	neighbours.add(neighboursof65);
    	neighbours.add(neighboursof66);
    	neighbours.add(neighboursof67);
    	neighbours.add(neighboursof68);
    	neighbours.add(neighboursof69);
    	neighbours.add(neighboursof70);
    	
    	neighbours.add(neighboursof71);
    	neighbours.add(neighboursof72);
    	neighbours.add(neighboursof73);
    	neighbours.add(neighboursof74);
    	neighbours.add(neighboursof75);
    	neighbours.add(neighboursof76);
    	neighbours.add(neighboursof77);
    	neighbours.add(neighboursof78);
    	neighbours.add(neighboursof79);
    	neighbours.add(neighboursof80);
    	
    	neighbours.add(neighboursof81);
    	neighbours.add(neighboursof82);
    	neighbours.add(neighboursof83);
    	neighbours.add(neighboursof84);
    	neighbours.add(neighboursof85);
    	neighbours.add(neighboursof86);
    	neighbours.add(neighboursof87);
    	neighbours.add(neighboursof88);
    	neighbours.add(neighboursof89);
    	neighbours.add(neighboursof90);
    	
    	neighbours.add(neighboursof91);
    	neighbours.add(neighboursof92);
    	neighbours.add(neighboursof93);
    	neighbours.add(neighboursof94);
    	neighbours.add(neighboursof95);
    	neighbours.add(neighboursof96);
    	neighbours.add(neighboursof97);
    	neighbours.add(neighboursof98);
    	neighbours.add(neighboursof99);
    	neighbours.add(neighboursof100);
    	
    	neighbours.add(neighboursof101);
    	neighbours.add(neighboursof102);
    	neighbours.add(neighboursof103);
    	neighbours.add(neighboursof104);
    	neighbours.add(neighboursof105);
    	neighbours.add(neighboursof106);
    	neighbours.add(neighboursof107);
    	neighbours.add(neighboursof108);
    	neighbours.add(neighboursof109);
    	neighbours.add(neighboursof110);
    	
    	neighbours.add(neighboursof111);
    	neighbours.add(neighboursof112);
    	neighbours.add(neighboursof113);
    	neighbours.add(neighboursof114);
    	neighbours.add(neighboursof115);
    	neighbours.add(neighboursof116);
    	neighbours.add(neighboursof117);
    	neighbours.add(neighboursof118);
    	neighbours.add(neighboursof119);
    	neighbours.add(neighboursof120);
    	
    	neighbours.add(neighboursof121);
    	neighbours.add(neighboursof122);
    	neighbours.add(neighboursof123);
    	neighbours.add(neighboursof124);
    	neighbours.add(neighboursof125);
    	neighbours.add(neighboursof126);
    	neighbours.add(neighboursof127);
    	neighbours.add(neighboursof128);
    	neighbours.add(neighboursof129);
    	neighbours.add(neighboursof130);
    	
    	neighbours.add(neighboursof131);
    	neighbours.add(neighboursof132);
    	neighbours.add(neighboursof133);
    	neighbours.add(neighboursof134);
    	neighbours.add(neighboursof135);
    	neighbours.add(neighboursof136);
    	neighbours.add(neighboursof137);
    	neighbours.add(neighboursof138);
    	neighbours.add(neighboursof139);
    	neighbours.add(neighboursof140);
    	
    	neighbours.add(neighboursof141);
    	neighbours.add(neighboursof142);
    	neighbours.add(neighboursof143);
    	neighbours.add(neighboursof144);
    	neighbours.add(neighboursof145);
    	neighbours.add(neighboursof146);
    	neighbours.add(neighboursof147);
    	neighbours.add(neighboursof148);
    	neighbours.add(neighboursof149);
    	neighbours.add(neighboursof150);
    	
    	neighbours.add(neighboursof151);
    	neighbours.add(neighboursof152);
    	neighbours.add(neighboursof153);
    	neighbours.add(neighboursof154);
    	neighbours.add(neighboursof155);
    	neighbours.add(neighboursof156);
    	neighbours.add(neighboursof157);
    	neighbours.add(neighboursof158);
    	neighbours.add(neighboursof159);
    	neighbours.add(neighboursof160);
    	
    	neighbours.add(neighboursof161);
    	neighbours.add(neighboursof162);
    	neighbours.add(neighboursof163);
    	neighbours.add(neighboursof164);
    	neighbours.add(neighboursof165);
    	neighbours.add(neighboursof166);
    	neighbours.add(neighboursof167);
    	neighbours.add(neighboursof168);
    	neighbours.add(neighboursof169);
    	neighbours.add(neighboursof170);
    	
    	neighbours.add(neighboursof171);
    	neighbours.add(neighboursof172);
    	neighbours.add(neighboursof173);
    	neighbours.add(neighboursof174);
    	neighbours.add(neighboursof175);
    	neighbours.add(neighboursof176);
    	neighbours.add(neighboursof177);
    	neighbours.add(neighboursof178);
    	neighbours.add(neighboursof179);
    	neighbours.add(neighboursof180);
    	
    	neighbours.add(neighboursof181);
    	neighbours.add(neighboursof182);
    	neighbours.add(neighboursof183);
    	neighbours.add(neighboursof184);
    	neighbours.add(neighboursof185);
    	neighbours.add(neighboursof186);
    	neighbours.add(neighboursof187);
    	neighbours.add(neighboursof188);
    	neighbours.add(neighboursof189);
    	neighbours.add(neighboursof190);
    	
    	neighbours.add(neighboursof191);
    	neighbours.add(neighboursof192);
    	neighbours.add(neighboursof193);
    	neighbours.add(neighboursof194);
    	neighbours.add(neighboursof195);
    	neighbours.add(neighboursof196);
    	neighbours.add(neighboursof197);
    	
    	
    	
        super.setClickable(true);
        this.context = context;
        mScaleDetector = new ScaleGestureDetector(context, new ScaleListener());
        mGestureDetector = new GestureDetector(context, new GestureListener());
        matrix = new Matrix();
        prevMatrix = new Matrix();
        m = new float[9];
        normalizedScale = 1;
        if (mScaleType == null) {
        	mScaleType = ScaleType.FIT_CENTER;
        }
        minScale = 1;
        maxScale = 3;
        superMinScale = SUPER_MIN_MULTIPLIER * minScale;
        superMaxScale = SUPER_MAX_MULTIPLIER * maxScale;
        setImageMatrix(matrix);
        setScaleType(ScaleType.MATRIX);
        setState(State.NONE);
        onDrawReady = false;
        super.setOnTouchListener(new PrivateOnTouchListener());
        
        for(int i=0;i<9;i++)
        	roomcounter[i]=0;
    }

    @Override
    public void setOnTouchListener(View.OnTouchListener l) {
        userTouchListener = l;
    }
    
    public void setOnTouchImageViewListener(OnTouchImageViewListener l) {
    	touchImageViewListener = l;
    }

    public void setOnDoubleTapListener(GestureDetector.OnDoubleTapListener l) {
        doubleTapListener = l;
    }

    @Override
    public void setImageResource(int resId) {
    	super.setImageResource(resId);
    	savePreviousImageValues();
    	fitImageToView();
    }
    
    @Override
    public void setImageBitmap(Bitmap bm) {
    	super.setImageBitmap(bm);
    	savePreviousImageValues();
    	fitImageToView();
    }
    
    @Override
    public void setImageDrawable(Drawable drawable) {
    	super.setImageDrawable(drawable);
    	savePreviousImageValues();
    	fitImageToView();
    }
    
    @Override
    public void setImageURI(Uri uri) {
    	super.setImageURI(uri);
    	savePreviousImageValues();
    	fitImageToView();
    }
    
    @Override
    public void setScaleType(ScaleType type) {
    	if (type == ScaleType.FIT_START || type == ScaleType.FIT_END) {
    		throw new UnsupportedOperationException("TouchImageView does not support FIT_START or FIT_END");
    	}
    	if (type == ScaleType.MATRIX) {
    		super.setScaleType(ScaleType.MATRIX);
    		
    	} else {
    		mScaleType = type;
    		if (onDrawReady) {
    			//
    			// If the image is already rendered, scaleType has been called programmatically
    			// and the TouchImageView should be updated with the new scaleType.
    			//
    			setZoom(this);
    		}
    	}
    }
    
    @Override
    public ScaleType getScaleType() {
    	return mScaleType;
    }
    
    /**
     * Returns false if image is in initial, unzoomed state. False, otherwise.
     * @return true if image is zoomed
     */
    public boolean isZoomed() {
    	return normalizedScale != 1;
    }
    
    /**
     * Return a Rect representing the zoomed image.
     * @return rect representing zoomed image
     */
    public RectF getZoomedRect() {
    	if (mScaleType == ScaleType.FIT_XY) {
    		throw new UnsupportedOperationException("getZoomedRect() not supported with FIT_XY");
    	}
    	PointF topLeft = transformCoordTouchToBitmap(0, 0, true);
    	PointF bottomRight = transformCoordTouchToBitmap(viewWidth, viewHeight, true);
    	
    	float w = getDrawable().getIntrinsicWidth();
    	float h = getDrawable().getIntrinsicHeight();
    	return new RectF(topLeft.x / w, topLeft.y / h, bottomRight.x / w, bottomRight.y / h);
    }
    
    /**
     * Save the current matrix and view dimensions
     * in the prevMatrix and prevView variables.
     */
    private void savePreviousImageValues() {
    	if (matrix != null && viewHeight != 0 && viewWidth != 0) {
	    	matrix.getValues(m);
	    	prevMatrix.setValues(m);
	    	prevMatchViewHeight = matchViewHeight;
	        prevMatchViewWidth = matchViewWidth;
	        prevViewHeight = viewHeight;
	        prevViewWidth = viewWidth;
    	}
    }
    
    @Override
    public Parcelable onSaveInstanceState() {
    	Bundle bundle = new Bundle();
    	bundle.putParcelable("instanceState", super.onSaveInstanceState());
    	bundle.putFloat("saveScale", normalizedScale);
    	bundle.putFloat("matchViewHeight", matchViewHeight);
    	bundle.putFloat("matchViewWidth", matchViewWidth);
    	bundle.putInt("viewWidth", viewWidth);
    	bundle.putInt("viewHeight", viewHeight);
    	matrix.getValues(m);
    	bundle.putFloatArray("matrix", m);
    	bundle.putBoolean("imageRendered", imageRenderedAtLeastOnce);
    	return bundle;
    }
    
    @Override
    public void onRestoreInstanceState(Parcelable state) {
      	if (state instanceof Bundle) {
	        Bundle bundle = (Bundle) state;
	        normalizedScale = bundle.getFloat("saveScale");
	        m = bundle.getFloatArray("matrix");
	        prevMatrix.setValues(m);
	        prevMatchViewHeight = bundle.getFloat("matchViewHeight");
	        prevMatchViewWidth = bundle.getFloat("matchViewWidth");
	        prevViewHeight = bundle.getInt("viewHeight");
	        prevViewWidth = bundle.getInt("viewWidth");
	        imageRenderedAtLeastOnce = bundle.getBoolean("imageRendered");
	        super.onRestoreInstanceState(bundle.getParcelable("instanceState"));
	        return;
      	}

      	super.onRestoreInstanceState(state);
    }
    
    @Override
    protected void onDraw(Canvas canvas) {
    	onDrawReady = true;
    	imageRenderedAtLeastOnce = true;
    	if (delayedZoomVariables != null) {
    		setZoom(delayedZoomVariables.scale, delayedZoomVariables.focusX, delayedZoomVariables.focusY, delayedZoomVariables.scaleType);
    		delayedZoomVariables = null;
    	}
    	super.onDraw(canvas);
    }
    
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
    	super.onConfigurationChanged(newConfig);
    	savePreviousImageValues();
    }
    
    /**
     * Get the max zoom multiplier.
     * @return max zoom multiplier.
     */
    public float getMaxZoom() {
    	return maxScale;
    }

    /**
     * Set the max zoom multiplier. Default value: 3.
     * @param max max zoom multiplier.
     */
    public void setMaxZoom(float max) {
        maxScale = max;
        superMaxScale = SUPER_MAX_MULTIPLIER * maxScale;
    }
    
    /**
     * Get the min zoom multiplier.
     * @return min zoom multiplier.
     */
    public float getMinZoom() {
    	return minScale;
    }
    
    /**
     * Get the current zoom. This is the zoom relative to the initial
     * scale, not the original resource.
     * @return current zoom multiplier.
     */
    public float getCurrentZoom() {
    	return normalizedScale;
    }
    
    /**
     * Set the min zoom multiplier. Default value: 1.
     * @param min min zoom multiplier.
     */
    public void setMinZoom(float min) {
    	minScale = min;
    	superMinScale = SUPER_MIN_MULTIPLIER * minScale;
    }
    
    /**
     * Reset zoom and translation to initial state.
     */
    public void resetZoom() {
    	normalizedScale = 1;
    	fitImageToView();
    }
    
    /**
     * Set zoom to the specified scale. Image will be centered by default.
     * @param scale
     */
    public void setZoom(float scale) {
    	setZoom(scale, 0.5f, 0.5f);
    }
    
    /**
     * Set zoom to the specified scale. Image will be centered around the point
     * (focusX, focusY). These floats range from 0 to 1 and denote the focus point
     * as a fraction from the left and top of the view. For example, the top left 
     * corner of the image would be (0, 0). And the bottom right corner would be (1, 1).
     * @param scale
     * @param focusX
     * @param focusY
     */
    public void setZoom(float scale, float focusX, float focusY) {
    	setZoom(scale, focusX, focusY, mScaleType);
    }
    
    /**
     * Set zoom to the specified scale. Image will be centered around the point
     * (focusX, focusY). These floats range from 0 to 1 and denote the focus point
     * as a fraction from the left and top of the view. For example, the top left 
     * corner of the image would be (0, 0). And the bottom right corner would be (1, 1).
     * @param scale
     * @param focusX
     * @param focusY
     * @param scaleType
     */
    public void setZoom(float scale, float focusX, float focusY, ScaleType scaleType) {
    	//
    	// setZoom can be called before the image is on the screen, but at this point, 
    	// image and view sizes have not yet been calculated in onMeasure. Thus, we should
    	// delay calling setZoom until the view has been measured.
    	//
    	if (!onDrawReady) {
    		delayedZoomVariables = new ZoomVariables(scale, focusX, focusY, scaleType);
    		return;
    	}
    	
    	if (scaleType != mScaleType) {
    		setScaleType(scaleType);
    	}
    	resetZoom();
    	scaleImage(scale, viewWidth / 2, viewHeight / 2, true);
    	matrix.getValues(m);
    	m[Matrix.MTRANS_X] = -((focusX * getImageWidth()) - (viewWidth * 0.5f));
    	m[Matrix.MTRANS_Y] = -((focusY * getImageHeight()) - (viewHeight * 0.5f));
    	matrix.setValues(m);
    	fixTrans();
    	setImageMatrix(matrix);
    }
    
    /**
     * Set zoom parameters equal to another TouchImageView. Including scale, position,
     * and ScaleType.
     * @param TouchImageView
     */
    public void setZoom(TouchImageView img) {
    	PointF center = img.getScrollPosition();
    	setZoom(img.getCurrentZoom(), center.x, center.y, img.getScaleType());
    }
    
    /**
     * Return the point at the center of the zoomed image. The PointF coordinates range
     * in value between 0 and 1 and the focus point is denoted as a fraction from the left 
     * and top of the view. For example, the top left corner of the image would be (0, 0). 
     * And the bottom right corner would be (1, 1).
     * @return PointF representing the scroll position of the zoomed image.
     */
    public PointF getScrollPosition() {
    	Drawable drawable = getDrawable();
    	if (drawable == null) {
    		return null;
    	}
    	int drawableWidth = drawable.getIntrinsicWidth();
        int drawableHeight = drawable.getIntrinsicHeight();
        
        PointF point = transformCoordTouchToBitmap(viewWidth / 2, viewHeight / 2, true);
        point.x /= drawableWidth;
        point.y /= drawableHeight;
        return point;
    }
    
    /**
     * Set the focus point of the zoomed image. The focus points are denoted as a fraction from the
     * left and top of the view. The focus points can range in value between 0 and 1. 
     * @param focusX
     * @param focusY
     */
    public void setScrollPosition(float focusX, float focusY) {
    	setZoom(normalizedScale, focusX, focusY);
    }
    
    /**
     * Performs boundary checking and fixes the image matrix if it 
     * is out of bounds.
     */
    private void fixTrans() {
        matrix.getValues(m);
        float transX = m[Matrix.MTRANS_X];
        float transY = m[Matrix.MTRANS_Y];
        
        float fixTransX = getFixTrans(transX, viewWidth, getImageWidth());
        float fixTransY = getFixTrans(transY, viewHeight, getImageHeight());
        
        if (fixTransX != 0 || fixTransY != 0) {
            matrix.postTranslate(fixTransX, fixTransY);
        }
    }
    
    /**
     * When transitioning from zooming from focus to zoom from center (or vice versa)
     * the image can become unaligned within the view. This is apparent when zooming
     * quickly. When the content size is less than the view size, the content will often
     * be centered incorrectly within the view. fixScaleTrans first calls fixTrans() and 
     * then makes sure the image is centered correctly within the view.
     */
    private void fixScaleTrans() {
    	fixTrans();
    	matrix.getValues(m);
    	if (getImageWidth() < viewWidth) {
    		m[Matrix.MTRANS_X] = (viewWidth - getImageWidth()) / 2;
    	}
    	
    	if (getImageHeight() < viewHeight) {
    		m[Matrix.MTRANS_Y] = (viewHeight - getImageHeight()) / 2;
    	}
    	matrix.setValues(m);
    }

    private float getFixTrans(float trans, float viewSize, float contentSize) {
        float minTrans, maxTrans;

        if (contentSize <= viewSize) {
            minTrans = 0;
            maxTrans = viewSize - contentSize;
            
        } else {
            minTrans = viewSize - contentSize;
            maxTrans = 0;
        }

        if (trans < minTrans)
            return -trans + minTrans;
        if (trans > maxTrans)
            return -trans + maxTrans;
        return 0;
    }
    
    private float getFixDragTrans(float delta, float viewSize, float contentSize) {
        if (contentSize <= viewSize) {
            return 0;
        }
        return delta;
    }
    
    private float getImageWidth() {
    	return matchViewWidth * normalizedScale;
    }
    
    private float getImageHeight() {
    	return matchViewHeight * normalizedScale;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Drawable drawable = getDrawable();
        if (drawable == null || drawable.getIntrinsicWidth() == 0 || drawable.getIntrinsicHeight() == 0) {
        	setMeasuredDimension(0, 0);
        	return;
        }
        
        int drawableWidth = drawable.getIntrinsicWidth();
        int drawableHeight = drawable.getIntrinsicHeight();
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        viewWidth = setViewSize(widthMode, widthSize, drawableWidth);
        viewHeight = setViewSize(heightMode, heightSize, drawableHeight);
        
        //
        // Set view dimensions
        //
        setMeasuredDimension(viewWidth, viewHeight);
        
        //
        // Fit content within view
        //
        fitImageToView();
    }
    
    /**
     * If the normalizedScale is equal to 1, then the image is made to fit the screen. Otherwise,
     * it is made to fit the screen according to the dimensions of the previous image matrix. This
     * allows the image to maintain its zoom after rotation.
     */
    private void fitImageToView() {
    	Drawable drawable = getDrawable();
        if (drawable == null || drawable.getIntrinsicWidth() == 0 || drawable.getIntrinsicHeight() == 0) {
        	return;
        }
        if (matrix == null || prevMatrix == null) {
        	return;
        }
        
        int drawableWidth = drawable.getIntrinsicWidth();
        int drawableHeight = drawable.getIntrinsicHeight();
    	
    	//
    	// Scale image for view
    	//
        float scaleX = (float) viewWidth / drawableWidth;
        float scaleY = (float) viewHeight / drawableHeight;
        
        switch (mScaleType) {
        case CENTER:
        	scaleX = scaleY = 1;
        	break;
        	
        case CENTER_CROP:
        	scaleX = scaleY = Math.max(scaleX, scaleY);
        	break;
        	
        case CENTER_INSIDE:
        	scaleX = scaleY = Math.min(1, Math.min(scaleX, scaleY));
        	
        case FIT_CENTER:
        	scaleX = scaleY = Math.min(scaleX, scaleY);
        	break;
        	
        case FIT_XY:
        	break;
        	
    	default:
    		//
    		// FIT_START and FIT_END not supported
    		//
    		throw new UnsupportedOperationException("TouchImageView does not support FIT_START or FIT_END");
        	
        }

        //
        // Center the image
        //
        float redundantXSpace = viewWidth - (scaleX * drawableWidth);
        float redundantYSpace = viewHeight - (scaleY * drawableHeight);
        matchViewWidth = viewWidth - redundantXSpace;
        matchViewHeight = viewHeight - redundantYSpace;
        if (!isZoomed() && !imageRenderedAtLeastOnce) {
        	//
        	// Stretch and center image to fit view
        	//
        	matrix.setScale(scaleX, scaleY);
        	matrix.postTranslate(redundantXSpace / 2, redundantYSpace / 2);
        	normalizedScale = 1;
        	
        } else {
        	//
        	// These values should never be 0 or we will set viewWidth and viewHeight
        	// to NaN in translateMatrixAfterRotate. To avoid this, call savePreviousImageValues
        	// to set them equal to the current values.
        	//
        	if (prevMatchViewWidth == 0 || prevMatchViewHeight == 0) {
        		savePreviousImageValues();
        	}
        	
        	prevMatrix.getValues(m);
        	
        	//
        	// Rescale Matrix after rotation
        	//
        	m[Matrix.MSCALE_X] = matchViewWidth / drawableWidth * normalizedScale;
        	m[Matrix.MSCALE_Y] = matchViewHeight / drawableHeight * normalizedScale;
        	
        	//
        	// TransX and TransY from previous matrix
        	//
            float transX = m[Matrix.MTRANS_X];
            float transY = m[Matrix.MTRANS_Y];
            
            //
            // Width
            //
            float prevActualWidth = prevMatchViewWidth * normalizedScale;
            float actualWidth = getImageWidth();
            translateMatrixAfterRotate(Matrix.MTRANS_X, transX, prevActualWidth, actualWidth, prevViewWidth, viewWidth, drawableWidth);
            
            //
            // Height
            //
            float prevActualHeight = prevMatchViewHeight * normalizedScale;
            float actualHeight = getImageHeight();
            translateMatrixAfterRotate(Matrix.MTRANS_Y, transY, prevActualHeight, actualHeight, prevViewHeight, viewHeight, drawableHeight);
            
            //
            // Set the matrix to the adjusted scale and translate values.
            //
            matrix.setValues(m);
        }
        fixTrans();
        setImageMatrix(matrix);
    }
    
    /**
     * Set view dimensions based on layout params
     * 
     * @param mode 
     * @param size
     * @param drawableWidth
     * @return
     */
    private int setViewSize(int mode, int size, int drawableWidth) {
    	int viewSize;
    	switch (mode) {
		case MeasureSpec.EXACTLY:
			viewSize = size;
			break;
			
		case MeasureSpec.AT_MOST:
			viewSize = Math.min(drawableWidth, size);
			break;
			
		case MeasureSpec.UNSPECIFIED:
			viewSize = drawableWidth;
			break;
			
		default:
			viewSize = size;
		 	break;
		}
    	return viewSize;
    }
    
    /**
     * After rotating, the matrix needs to be translated. This function finds the area of image 
     * which was previously centered and adjusts translations so that is again the center, post-rotation.
     * 
     * @param axis Matrix.MTRANS_X or Matrix.MTRANS_Y
     * @param trans the value of trans in that axis before the rotation
     * @param prevImageSize the width/height of the image before the rotation
     * @param imageSize width/height of the image after rotation
     * @param prevViewSize width/height of view before rotation
     * @param viewSize width/height of view after rotation
     * @param drawableSize width/height of drawable
     */
    private void translateMatrixAfterRotate(int axis, float trans, float prevImageSize, float imageSize, int prevViewSize, int viewSize, int drawableSize) {
    	if (imageSize < viewSize) {
        	//
        	// The width/height of image is less than the view's width/height. Center it.
        	//
        	m[axis] = (viewSize - (drawableSize * m[Matrix.MSCALE_X])) * 0.5f;
        	
        } else if (trans > 0) {
        	//
        	// The image is larger than the view, but was not before rotation. Center it.
        	//
        	m[axis] = -((imageSize - viewSize) * 0.5f);
        	
        } else {
        	//
        	// Find the area of the image which was previously centered in the view. Determine its distance
        	// from the left/top side of the view as a fraction of the entire image's width/height. Use that percentage
        	// to calculate the trans in the new view width/height.
        	//
        	float percentage = (Math.abs(trans) + (0.5f * prevViewSize)) / prevImageSize;
        	m[axis] = -((percentage * imageSize) - (viewSize * 0.5f));
        }
    }
    
    private void setState(State state) {
    	this.state = state;
    }
    
    public boolean canScrollHorizontallyFroyo(int direction) {
        return canScrollHorizontally(direction);
    }
    
    @Override
    public boolean canScrollHorizontally(int direction) {
    	matrix.getValues(m);
    	float x = m[Matrix.MTRANS_X];
    	
    	if (getImageWidth() < viewWidth) {
    		return false;
    		
    	} else if (x >= -1 && direction < 0) {
    		return false;
    		
    	} else if (Math.abs(x) + viewWidth + 1 >= getImageWidth() && direction > 0) {
    		return false;
    	}
    	
    	return true;
    }
    
    /**
     * Gesture Listener detects a single click or long click and passes that on
     * to the view's listener.
     * @author Ortiz
     *
     */
    private class GestureListener extends GestureDetector.SimpleOnGestureListener {
    	
        @Override
        public boolean onSingleTapConfirmed(MotionEvent e)
        {
            if(doubleTapListener != null) {
            	return doubleTapListener.onSingleTapConfirmed(e);
            }
        	return performClick();
        }
        
        @Override
        public void onLongPress(MotionEvent e)
        {
        	performLongClick();
        	
            
        }
        
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY)
        {
        	if (fling != null) {
        		//
        		// If a previous fling is still active, it should be cancelled so that two flings
        		// are not run simultaenously.
        		//
        		fling.cancelFling();
        	}
        	fling = new Fling((int) velocityX, (int) velocityY);
        	compatPostOnAnimation(fling);
        	return super.onFling(e1, e2, velocityX, velocityY);
        }
        
        @Override
        public boolean onDoubleTap(MotionEvent e) {
        	boolean consumed = false;
            if(doubleTapListener != null) {
            	consumed = doubleTapListener.onDoubleTap(e);
            }
        	if (state == State.NONE) {
	        	float targetZoom = (normalizedScale == minScale) ? maxScale : minScale;
	        	DoubleTapZoom doubleTap = new DoubleTapZoom(targetZoom, e.getX(), e.getY(), false);
	        	compatPostOnAnimation(doubleTap);
	        	consumed = true;
        	}
        	return consumed;
        }

        @Override
        public boolean onDoubleTapEvent(MotionEvent e) {
            if(doubleTapListener != null) {
            	return doubleTapListener.onDoubleTapEvent(e);
            }
            return false;
        }
    }
    
    public interface OnTouchImageViewListener {
    	public void onMove();
    }
    
    /**
     * Responsible for all touch events. Handles the heavy lifting of drag and also sends
     * touch events to Scale Detector and Gesture Detector.
     * @author Ortiz
     *
     */
    private class PrivateOnTouchListener implements OnTouchListener {
    	
    	//
        // Remember last point position for dragging
        //
        private PointF last = new PointF();
    	
    	@Override
        public boolean onTouch(View v, MotionEvent event) {
            mScaleDetector.onTouchEvent(event);
            mGestureDetector.onTouchEvent(event);
            PointF curr = new PointF(event.getX(), event.getY());
        	
            
            if (state == State.NONE || state == State.DRAG || state == State.FLING) {
                
	            switch (event.getAction()) {
	                case MotionEvent.ACTION_DOWN:
	                	last.set(curr);
	                    if (fling != null)
	                    	fling.cancelFling();
	                    setState(State.DRAG);
	                    break;
	                    
	                case MotionEvent.ACTION_MOVE:
	                    if (state == State.DRAG) {
	                        float deltaX = curr.x - last.x;
	                        float deltaY = curr.y - last.y;
	                        float fixTransX = getFixDragTrans(deltaX, viewWidth, getImageWidth());
	                        float fixTransY = getFixDragTrans(deltaY, viewHeight, getImageHeight());
	                        matrix.postTranslate(fixTransX, fixTransY);
	                        fixTrans();
	                        last.set(curr.x, curr.y);
	                    }
	                    break;
	
	                case MotionEvent.ACTION_UP:
	                case MotionEvent.ACTION_POINTER_UP:
	                {
	                
	                 	if(GamePlay.movecount!=0)
	                	{
	                	
	                 		System.out.println("movesleft="+GamePlay.movecount);
	                	float[] values = new float[9];
	                	matrix.getValues(values);

	                	// values[2] and values[5] are the x,y coordinates of the top left corner of the drawable image, regardless of the zoom factor.
	                	// values[0] and values[4] are the zoom factors for the image's width and height respectively. If you zoom at the same factor, these should both be the same value.

	                	// event is the touch event for MotionEvent.ACTION_UP
	                	float relativeX = (event.getX() - values[2]) / values[0];
	                	float relativeY = (event.getY() - values[5]) / values[4];
	                	
	                	
	                	for(RectArea a : mAreaList)
	                	{
	                		if(a.isInArea(relativeX, relativeY))
	                			{
	                			
	                			if(neighbours.get(characterarea).contains(a))
	                			{
	
	                				characterarea=a.idd;
	                				GamePlay.movecount--;
	                				
                					int counter=0;
                					for(int l=0;l<GamePlay.players;l++)
                					{
                						if(GamePlay.isin.get(l)==characterarea)
                							counter++;
                					}

	                				if(characterarea>188)
	                				{	
	                					GamePlay.movecount=0;
	                					GamePlay.setisin(GamePlay.yournumber, a.idd,(int) a._left+150+(counter*80),(int) a._top+150);
	                				}
	                			
	                				else	
	                				{
	                					GamePlay.setisin(GamePlay.yournumber, a.idd,(int) a._left+(counter*30),(int) a._top);
	                					
	                				}
	                				
	                				GamePlay.sendmessagetype=3;
	                				if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
	                				       GamePlay.serve.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
	                				    else
	                				       GamePlay.serve.execute();
	                				
	                				
	                				if(GamePlay.movecount==0 || characterarea>188)
	                				{
	                					GamePlay.dice_picture1.setVisibility(View.GONE);
	                					GamePlay.dice_picture2.setVisibility(View.GONE);
	                					
	                				}
	                				
                					
	                			}
	                			}
	                			
	                	}
	                	
	                	
	              	}
	                	else if(GamePlay.dice_picture1.getVisibility()==View.VISIBLE)
	                		Toast.makeText(getContext(), "roll the dice to move", Toast.LENGTH_SHORT).show();
        				
	                	else
	                		Toast.makeText(getContext(), "you can not move now", Toast.LENGTH_SHORT).show();
            				

	                	setState(State.NONE);
	                }
	                
	                    break;
	            }
            }
            
            setImageMatrix(matrix);
            
            //
    		// User-defined OnTouchListener
    		//
    		if(userTouchListener != null) {
    			userTouchListener.onTouch(v, event);
    		}
            
    		//
    		// OnTouchImageViewListener is set: TouchImageView dragged by user.
    		//
    		if (touchImageViewListener != null) {
    			touchImageViewListener.onMove();
    		}
    		
            //
            // indicate event was handled
            //
            return true;
        }
    }

    /**
     * ScaleListener detects user two finger scaling and scales image.
     * @author Ortiz
     *
     */
    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            setState(State.ZOOM);
            return true;
        }

        @Override
        public boolean onScale(ScaleGestureDetector detector) {
        	scaleImage(detector.getScaleFactor(), detector.getFocusX(), detector.getFocusY(), true);
        	
        	//
        	// OnTouchImageViewListener is set: TouchImageView pinch zoomed by user.
        	//
        	if (touchImageViewListener != null) {
        		touchImageViewListener.onMove();
        	}
            return true;
        }
        
        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {
        	super.onScaleEnd(detector);
        	setState(State.NONE);
        	boolean animateToZoomBoundary = false;
        	float targetZoom = normalizedScale;
        	if (normalizedScale > maxScale) {
        		targetZoom = maxScale;
        		animateToZoomBoundary = true;
        		
        	} else if (normalizedScale < minScale) {
        		targetZoom = minScale;
        		animateToZoomBoundary = true;
        	}
        	
        	if (animateToZoomBoundary) {
	        	DoubleTapZoom doubleTap = new DoubleTapZoom(targetZoom, viewWidth / 2, viewHeight / 2, true);
	        	compatPostOnAnimation(doubleTap);
        	}
        }
    }
    
    private void scaleImage(double deltaScale, float focusX, float focusY, boolean stretchImageToSuper) {
    	
    	float lowerScale, upperScale;
    	if (stretchImageToSuper) {
    		lowerScale = superMinScale;
    		upperScale = superMaxScale;
    		
    	} else {
    		lowerScale = minScale;
    		upperScale = maxScale;
    	}
    	
    	float origScale = normalizedScale;
        normalizedScale *= deltaScale;
        if (normalizedScale > upperScale) {
            normalizedScale = upperScale;
            deltaScale = upperScale / origScale;
        } else if (normalizedScale < lowerScale) {
            normalizedScale = lowerScale;
            deltaScale = lowerScale / origScale;
        }
        
        matrix.postScale((float) deltaScale, (float) deltaScale, focusX, focusY);
        fixScaleTrans();
    }
    
    /**
     * DoubleTapZoom calls a series of runnables which apply
     * an animated zoom in/out graphic to the image.
     * @author Ortiz
     *
     */
    private class DoubleTapZoom implements Runnable {
    	
    	private long startTime;
    	private static final float ZOOM_TIME = 500;
    	private float startZoom, targetZoom;
    	private float bitmapX, bitmapY;
    	private boolean stretchImageToSuper;
    	private AccelerateDecelerateInterpolator interpolator = new AccelerateDecelerateInterpolator();
    	private PointF startTouch;
    	private PointF endTouch;

    	DoubleTapZoom(float targetZoom, float focusX, float focusY, boolean stretchImageToSuper) {
    		setState(State.ANIMATE_ZOOM);
    		startTime = System.currentTimeMillis();
    		this.startZoom = normalizedScale;
    		this.targetZoom = targetZoom;
    		this.stretchImageToSuper = stretchImageToSuper;
    		PointF bitmapPoint = transformCoordTouchToBitmap(focusX, focusY, false);
    		this.bitmapX = bitmapPoint.x;
    		this.bitmapY = bitmapPoint.y;
    		
    		//
    		// Used for translating image during scaling
    		//
    		startTouch = transformCoordBitmapToTouch(bitmapX, bitmapY);
    		endTouch = new PointF(viewWidth / 2, viewHeight / 2);
    	}

		@Override
		public void run() {
			float t = interpolate();
			double deltaScale = calculateDeltaScale(t);
			scaleImage(deltaScale, bitmapX, bitmapY, stretchImageToSuper);
			translateImageToCenterTouchPosition(t);
			fixScaleTrans();
			setImageMatrix(matrix);
			
			//
			// OnTouchImageViewListener is set: double tap runnable updates listener
			// with every frame.
			//
			if (touchImageViewListener != null) {
				touchImageViewListener.onMove();
			}
			
			if (t < 1f) {
				//
				// We haven't finished zooming
				//
				compatPostOnAnimation(this);
				
			} else {
				//
				// Finished zooming
				//
				setState(State.NONE);
			}
		}
		
		/**
		 * Interpolate between where the image should start and end in order to translate
		 * the image so that the point that is touched is what ends up centered at the end
		 * of the zoom.
		 * @param t
		 */
		private void translateImageToCenterTouchPosition(float t) {
			float targetX = startTouch.x + t * (endTouch.x - startTouch.x);
			float targetY = startTouch.y + t * (endTouch.y - startTouch.y);
			PointF curr = transformCoordBitmapToTouch(bitmapX, bitmapY);
			matrix.postTranslate(targetX - curr.x, targetY - curr.y);
		}
		
		/**
		 * Use interpolator to get t
		 * @return
		 */
		private float interpolate() {
			long currTime = System.currentTimeMillis();
			float elapsed = (currTime - startTime) / ZOOM_TIME;
			elapsed = Math.min(1f, elapsed);
			return interpolator.getInterpolation(elapsed);
		}
		
		/**
		 * Interpolate the current targeted zoom and get the delta
		 * from the current zoom.
		 * @param t
		 * @return
		 */
		private double calculateDeltaScale(float t) {
			double zoom = startZoom + t * (targetZoom - startZoom);
			return zoom / normalizedScale;
		}
    }
    
    /**
     * This function will transform the coordinates in the touch event to the coordinate 
     * system of the drawable that the imageview contain
     * @param x x-coordinate of touch event
     * @param y y-coordinate of touch event
     * @param clipToBitmap Touch event may occur within view, but outside image content. True, to clip return value
     * 			to the bounds of the bitmap size.
     * @return Coordinates of the point touched, in the coordinate system of the original drawable.
     */
    private PointF transformCoordTouchToBitmap(float x, float y, boolean clipToBitmap) {
         matrix.getValues(m);
         float origW = getDrawable().getIntrinsicWidth();
         float origH = getDrawable().getIntrinsicHeight();
         float transX = m[Matrix.MTRANS_X];
         float transY = m[Matrix.MTRANS_Y];
         float finalX = ((x - transX) * origW) / getImageWidth();
         float finalY = ((y - transY) * origH) / getImageHeight();
         
         if (clipToBitmap) {
        	 finalX = Math.min(Math.max(finalX, 0), origW);
        	 finalY = Math.min(Math.max(finalY, 0), origH);
         }
         
         return new PointF(finalX , finalY);
    }
    
    /**
     * Inverse of transformCoordTouchToBitmap. This function will transform the coordinates in the
     * drawable's coordinate system to the view's coordinate system.
     * @param bx x-coordinate in original bitmap coordinate system
     * @param by y-coordinate in original bitmap coordinate system
     * @return Coordinates of the point in the view's coordinate system.
     */
    private PointF transformCoordBitmapToTouch(float bx, float by) {
        matrix.getValues(m);        
        float origW = getDrawable().getIntrinsicWidth();
        float origH = getDrawable().getIntrinsicHeight();
        float px = bx / origW;
        float py = by / origH;
        float finalX = m[Matrix.MTRANS_X] + getImageWidth() * px;
        float finalY = m[Matrix.MTRANS_Y] + getImageHeight() * py;
        return new PointF(finalX , finalY);
    }
    
    /**
     * Fling launches sequential runnables which apply
     * the fling graphic to the image. The values for the translation
     * are interpolated by the Scroller.
     * @author Ortiz
     *
     */
    private class Fling implements Runnable {
    	
        CompatScroller scroller;
    	int currX, currY;
    	
    	Fling(int velocityX, int velocityY) {
    		setState(State.FLING);
    		scroller = new CompatScroller(context);
    		matrix.getValues(m);
    		
    		int startX = (int) m[Matrix.MTRANS_X];
    		int startY = (int) m[Matrix.MTRANS_Y];
    		int minX, maxX, minY, maxY;
    		
    		if (getImageWidth() > viewWidth) {
    			minX = viewWidth - (int) getImageWidth();
    			maxX = 0;
    			
    		} else {
    			minX = maxX = startX;
    		}
    		
    		if (getImageHeight() > viewHeight) {
    			minY = viewHeight - (int) getImageHeight();
    			maxY = 0;
    			
    		} else {
    			minY = maxY = startY;
    		}
    		
    		scroller.fling(startX, startY, (int) velocityX, (int) velocityY, minX,
                    maxX, minY, maxY);
    		currX = startX;
    		currY = startY;
    	}
    	
    	public void cancelFling() {
    		if (scroller != null) {
    			setState(State.NONE);
    			scroller.forceFinished(true);
    		}
    	}
    	
		@Override
		public void run() {
			
			//
			// OnTouchImageViewListener is set: TouchImageView listener has been flung by user.
			// Listener runnable updated with each frame of fling animation.
			//
			if (touchImageViewListener != null) {
				touchImageViewListener.onMove();
			}
			
			if (scroller.isFinished()) {
        		scroller = null;
        		return;
        	}
			
			if (scroller.computeScrollOffset()) {
	        	int newX = scroller.getCurrX();
	            int newY = scroller.getCurrY();
	            int transX = newX - currX;
	            int transY = newY - currY;
	            currX = newX;
	            currY = newY;
	            matrix.postTranslate(transX, transY);
	            fixTrans();
	            setImageMatrix(matrix);
	            compatPostOnAnimation(this);
        	}
		}
    }
    
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
	private class CompatScroller {
    	Scroller scroller;
    	OverScroller overScroller;
    	boolean isPreGingerbread;
    	
    	public CompatScroller(Context context) {
    		if (VERSION.SDK_INT < VERSION_CODES.GINGERBREAD) {
    			isPreGingerbread = true;
    			scroller = new Scroller(context);
    			
    		} else {
    			isPreGingerbread = false;
    			overScroller = new OverScroller(context);
    		}
    	}
    	
    	public void fling(int startX, int startY, int velocityX, int velocityY, int minX, int maxX, int minY, int maxY) {
    		if (isPreGingerbread) {
    			scroller.fling(startX, startY, velocityX, velocityY, minX, maxX, minY, maxY);
    		} else {
    			overScroller.fling(startX, startY, velocityX, velocityY, minX, maxX, minY, maxY);
    		}
    	}
    	
    	public void forceFinished(boolean finished) {
    		if (isPreGingerbread) {
    			scroller.forceFinished(finished);
    		} else {
    			overScroller.forceFinished(finished);
    		}
    	}
    	
    	public boolean isFinished() {
    		if (isPreGingerbread) {
    			return scroller.isFinished();
    		} else {
    			return overScroller.isFinished();
    		}
    	}
    	
    	public boolean computeScrollOffset() {
    		if (isPreGingerbread) {
    			return scroller.computeScrollOffset();
    		} else {
    			overScroller.computeScrollOffset();
    			return overScroller.computeScrollOffset();
    		}
    	}
    	
    	public int getCurrX() {
    		if (isPreGingerbread) {
    			return scroller.getCurrX();
    		} else {
    			return overScroller.getCurrX();
    		}
    	}
    	
    	public int getCurrY() {
    		if (isPreGingerbread) {
    			return scroller.getCurrY();
    		} else {
    			return overScroller.getCurrY();
    		}
    	}
    }
    
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	private void compatPostOnAnimation(Runnable runnable) {
    	if (VERSION.SDK_INT >= VERSION_CODES.JELLY_BEAN) {
            postOnAnimation(runnable);
            
        } else {
            postDelayed(runnable, 1000/60);
        }
    }
    
    private class ZoomVariables {
    	public float scale;
    	public float focusX;
    	public float focusY;
    	public ScaleType scaleType;
    	
    	public ZoomVariables(float scale, float focusX, float focusY, ScaleType scaleType) {
    		this.scale = scale;
    		this.focusX = focusX;
    		this.focusY = focusY;
    		this.scaleType = scaleType;
    	}
    }
    
    private void printMatrixInfo() {
    	float[] n = new float[9];
    	matrix.getValues(n);
    	Log.d(DEBUG, "Scale: " + n[Matrix.MSCALE_X] + " TransX: " + n[Matrix.MTRANS_X] + " TransY: " + n[Matrix.MTRANS_Y]);
    }
}