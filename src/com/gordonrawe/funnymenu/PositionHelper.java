package com.gordonrawe.funnymenu;

import android.graphics.Point;
import android.view.View;
import android.view.ViewGroup.MarginLayoutParams;


public class PositionHelper {
	public static Point[] matrix= {new Point(-1,-1),new Point(-1,1),new Point(1,1),new Point(1,-1)};
	
	public static Point getViewCenter(View view){
		Point ret = new Point(view.getTop()+view.getHeight()/2,view.getLeft()+view.getWidth()/2);
		return ret;
	}
	
	
	public static void moveViewCenterTo(View view,int x,int y){//以左上角作为坐标系，在xml中声明left和top的margin为起点
		int width = view.getWidth();int height = view.getHeight();
		MarginLayoutParams params = (MarginLayoutParams)view.getLayoutParams();
		params.leftMargin =x - width/2;params.topMargin = y - height/2;
		view.setLayoutParams(params);
	}
	
	public static int dockSide(View view,Point screenSize){
		int left = view.getLeft();
		int top = view.getTop();
		int right = screenSize.x - view.getRight();
		int bottom = screenSize.y-view.getBottom();
		if(left <=top && left <= right && left <= bottom) return 0;
		if(top<=left&&top<=right&&top<=bottom) return 1;
		if(right<=left&&right<=top&&right<=bottom) return 2;
		if(bottom<=left&&bottom<=top&&bottom<=right) return 3;
		return 0;
	}
	
}