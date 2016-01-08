package coms.whodunnit;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class MyLinearLayout extends LinearLayout{

	private int mLayoutHeight;

	public MyLinearLayout(Context context, AttributeSet attrs) {
	    super(context, attrs);
	}

	public void setLayoutHeight(int height) {
	    mLayoutHeight = height;
	    ViewGroup.LayoutParams lp = getLayoutParams();
	    lp.height = height;
	    setLayoutParams(lp);
	}

	public int getLayoutHeight() {
	    return mLayoutHeight;
	}
}
