package coms.whodunnit;

import coms.myfirstapp.cardsui.RecyclableCard;
import coms.whodunnit.FlowTextView;




import android.text.Html;
import android.view.animation.Transformation;
import android.animation.ValueAnimator;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class MyImageCard extends RecyclableCard {
	
	String con,con2;
	int width,width2;
	int height,height2;
	int toggle=0;
	int image2;
	int type;
	public static ImageView iv,ivinside,bottom;
	public MyImageCard(int type,int image2,int width2,int height2,int width,int height,String title, int image,String content,String content2){
		super(title, image);
		con=content;
		con2=content2;
		this.image2=image2;
		this.width=width;
		this.height=height;
		this.width2=width2;
		this.height2=height2;
		this.type=type;
	}

	@Override
	protected int getCardLayoutId() {
		return R.layout.card_picture;
	}

	@Override
	protected void applyTo(final View convertView) {
		
		((TextView) convertView.findViewById(R.id.title)).setText(title);
		iv= (ImageView) convertView.findViewById(R.id.imageView1);
		

		final RelativeLayout rl=(RelativeLayout) convertView.findViewById(R.id.rel1);
		
		ivinside= (ImageView) convertView.findViewById(R.id.imageView2);
		bottom= (ImageView) convertView.findViewById(R.id.imageViewbottom);
		
		final LinearLayout l=(LinearLayout) convertView.findViewById(R.id.linearlayout);
		
		iv.setImageResource(image);	
		if(type==2)
		{
		
			ivinside.setVisibility(View.VISIBLE);
			ivinside.setImageResource(image2);
			RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(width2+20, height2+45);
			layoutParams2.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
			ivinside.setLayoutParams(layoutParams2);			
			
		}
		
		if(type==3)
			bottom.setImageResource(image2);
		
	
		((FlowTextView) convertView.findViewById(R.id.description)).setText(Html.fromHtml(con));
		((FlowTextView) convertView.findViewById(R.id.description2)).setText(Html.fromHtml(con2));

		
		this.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				if(toggle==0)
				{
					expand(rl);
					l.setAlpha(0.25f);
					toggle=1;
				}
				else
				{
					collapse(rl);
					l.setAlpha(1f);
					toggle=0;
				}
			}
			
			
		});
		
		
	}

	
	
	public static void expand(final View v) {
	    v.measure(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
	    final int targetHeight = v.getMeasuredHeight();

	    // Older versions of android (pre API 21) cancel animations for views with a height of 0.
	    v.getLayoutParams().height = 1;
	    v.setVisibility(View.VISIBLE);
	    Animation a = new Animation()
	    {
	        @Override
	        protected void applyTransformation(float interpolatedTime, Transformation t) {
	            v.getLayoutParams().height = interpolatedTime == 1
	                    ? LayoutParams.WRAP_CONTENT
	                    : (int)(targetHeight * interpolatedTime);
	            v.requestLayout();
	        }

	        @Override
	        public boolean willChangeBounds() {
	            return true;
	        }
	    };

	    // 1dp/ms
	    a.setDuration((int)(targetHeight / v.getContext().getResources().getDisplayMetrics().density));
	    v.startAnimation(a);
	}

	public static void collapse(final View v) {
	    final int initialHeight = v.getMeasuredHeight();

	    Animation a = new Animation()
	    {
	        @Override
	        protected void applyTransformation(float interpolatedTime, Transformation t) {
	            if(interpolatedTime == 1){
	                v.setVisibility(View.GONE);
	            }else{
	                v.getLayoutParams().height = initialHeight - (int)(initialHeight * interpolatedTime);
	                v.requestLayout();
	            }
	        }

	        @Override
	        public boolean willChangeBounds() {
	            return true;
	        }
	    };

	    // 1dp/ms
	    a.setDuration((int)(initialHeight / v.getContext().getResources().getDisplayMetrics().density));
	    v.startAnimation(a);
	}
	
	public void remove()
	{
		iv.setImageResource(0);
		ivinside.setImageResource(0);
		bottom.setImageResource(0);
	}
}
