package coms.whodunnit;

import java.util.ArrayList;


import android.content.Context;
import android.hardware.Camera.Area;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class MyImageView extends ImageView {
	
	
	public class RectArea extends Area {
		float _left;
		float _top;
		float _right;
		float _bottom;


		RectArea(int id, String name, float left, float top, float right, float bottom) {
			super(null, id);
			_left = left;
			_top = top;
			_right = right;
			_bottom = bottom;
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
	
	ArrayList<RectArea> mAreaList = new ArrayList<RectArea>();
	
	
	public MyImageView(Context context) {
		super(context);
		RectArea area=new RectArea(1,"one",0,0,1000,1000);
		
		mAreaList.add(area);
	/*	this.setOnTouchListener(new OnTouchListener(){

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				
				
				for (RectArea a : mAreaList)
				{
					if (a.isInArea(50,50))
					{
						Toast.makeText(getContext(), "touched", Toast.LENGTH_SHORT).show();
					}
						
						break;
				}
			
						
				return false;
			}
			
			
			
		});
		*/
		// TODO Auto-generated constructor stub
	}
	
	public MyImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
	}

	public MyImageView(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
		
	}
	
}
