package com.gordonrawe.funnymenu;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.widget.ImageButton;
import android.widget.RelativeLayout;


public class OriginalActivity extends Activity {
	public static Point center;
	private int radius;
	public static Point[] points;

	private int lastX,lastY;
	private Point screenSize;

	private boolean isShowing = false;
	private RelativeLayout multi_buttons_wrapper;
	private RelativeLayout main_button_wrapper;
	private ImageButton btn_main;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu_fragment);
		findViews();
	}

	private void setupListeners(){
		//给住按钮设置事件
		main_button_wrapper.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				if(!isShowing){
					Animation animation = AnimationHelper.getRotateAnimation(0, 135, 500);
					btn_main.startAnimation(animation);
					AnimationHelper.startExpandInContainer(multi_buttons_wrapper, 500);
				}
				else{
					Animation animation = AnimationHelper.getRotateAnimation(135, 0, 500);
					btn_main.startAnimation(animation);
					AnimationHelper.startShrinkInContainer(multi_buttons_wrapper, 500);

				}
				isShowing = !isShowing;
			}
		});
		//给小按钮们设置事件
		for(int i = 0; i < multi_buttons_wrapper.getChildCount();i++){
			final ImageButton img_button = (ImageButton)multi_buttons_wrapper.getChildAt(i);
			final int current = i;
			img_button.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					btn_main.startAnimation(AnimationHelper.getRotateAnimation(-135, 0, 300));
					isShowing = !isShowing;
					img_button.startAnimation(AnimationHelper.getBiggerAndDisappear(400));
					for(int j = 0; j < multi_buttons_wrapper.getChildCount();j++){

						multi_buttons_wrapper.getChildAt(j).setVisibility(8);
						multi_buttons_wrapper.getChildAt(j).setFocusable(false);
						multi_buttons_wrapper.getChildAt(j).setClickable(false);

						if(j!=current){
							final ImageButton curButton = (ImageButton)multi_buttons_wrapper.getChildAt(j);
							curButton.startAnimation(AnimationHelper.getSmallerAnimation(300));
						}
					}
				}
			});}

		//设置拖拉事件
		main_button_wrapper.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				if(!isShowing){
					int dx = 0,dy= 0,top=0,left=0;
					Point log;
					switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN:  
						lastX=(int)event.getRawX();  
						lastY=(int)event.getRawY();
						break;  
					case MotionEvent.ACTION_MOVE:  
						dx=(int)event.getRawX()-lastX;  
						dy=(int)event.getRawY()-lastY;  
						top=v.getTop()+dy;  
						left=v.getLeft()+dx;  

						//检查越界
						if(top<=0)  
						{  
							top=0;
						}  
						if(top>=screenSize.y-main_button_wrapper.getHeight())
						{  
							top=screenSize.y-main_button_wrapper.getHeight();
						}  
						if(left>=screenSize.x - main_button_wrapper.getWidth())  
						{  
							left=screenSize.x - main_button_wrapper.getWidth();  
						}
						if(left<=0)  
						{  
							left=0;  
						}

						//						v.layout(left, top, left+v.getWidth(), top+v.getHeight());
						lastX=(int)event.getRawX();  
						lastY=(int)event.getRawY();
						OriginalActivity.center.x = left+v.getWidth()/2;
						OriginalActivity.center.y = top+v.getHeight()/2;
						calcParams();
						PositionHelper.moveViewCenterTo(main_button_wrapper, left+v.getWidth()/2, top+v.getHeight()/2);
						break;  
					case MotionEvent.ACTION_UP:
						//dock到边界上去
						int dockSide = PositionHelper.dockSide(main_button_wrapper, screenSize);
						switch (dockSide) {
						case 0://left side
							log = new Point(OriginalActivity.center);
							OriginalActivity.center.x = v.getWidth()/2+20;
							calcParams();
							AnimationHelper.slowMove(v, log, OriginalActivity.center, 100, 500);
							break;
						case 1:
							log = new Point(OriginalActivity.center);
							OriginalActivity.center.y = v.getHeight()/2+20;
							calcParams();
							AnimationHelper.slowMove(v, log, OriginalActivity.center, 100, 500);
							break;
						case 2:
							log = new Point(OriginalActivity.center);
							OriginalActivity.center.x = screenSize.x - v.getWidth()/2-20;
							calcParams();
							AnimationHelper.slowMove(v, log, OriginalActivity.center, 100, 500);
							break;
						case 3:
							log = new Point(OriginalActivity.center);
							OriginalActivity.center.y = screenSize.y - v.getHeight()/2-20;
							calcParams();
							AnimationHelper.slowMove(v, log, OriginalActivity.center, 100, 500);
							break;
						default:
							break;
						}
						break;  
					}
				}
				return false;
			}
		});
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		// TODO Auto-generated method stub
		super.onWindowFocusChanged(hasFocus);

		positionViews();
		setupListeners();

	}

	private void initParams(){

		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		screenSize = new Point(metrics.widthPixels,metrics.heightPixels);
		isShowing = false;
		radius = 300;
		OriginalActivity.center = new Point(500,700);
		OriginalActivity.points = new Point[5];
		calcParams();
	}

	private void calcParams(){
		Point side;
		if(center.x < screenSize.x/2 && center.y < screenSize.y/2){//第一象限
			side = PositionHelper.matrix[0];
		}else if(center.x < screenSize.x/2 && center.y > screenSize.y/2){//第二象限
			side = PositionHelper.matrix[1];
		}else if(center.x > screenSize.x/2 && center.y > screenSize.y/2){//第三象限
			side = PositionHelper.matrix[2];
		}else {//第四象限
			side = PositionHelper.matrix[3];
		}
		for(int i = 0; i < OriginalActivity.points.length;i++){
			double x = radius*Math.cos(i*(Math.PI/8))*side.x;
			double y = radius*Math.sin(i*(Math.PI/8))*side.y;
			OriginalActivity.points[i] = new Point(OriginalActivity.center.x-(int)x,OriginalActivity.center.y-(int)y);
		}
	}

	private void findViews(){
		btn_main = (ImageButton)findViewById(R.id.btn_main);
		multi_buttons_wrapper = (RelativeLayout)findViewById(R.id.multi_buttons_wrapper);
		main_button_wrapper = (RelativeLayout)findViewById(R.id.main_button_wrapper);
	}

	private void positionViews(){
		initParams();
		PositionHelper.moveViewCenterTo(main_button_wrapper, OriginalActivity.center.x, OriginalActivity.center.y);
		for(int i = 0; i < multi_buttons_wrapper.getChildCount();i++){
			ImageButton img_button = (ImageButton)multi_buttons_wrapper.getChildAt(i);
			PositionHelper.moveViewCenterTo(img_button, OriginalActivity.points[i].x, OriginalActivity.points[i].y);
		}
	}

}


/*				PositionHelper.startAnimationFromTo(arg0, new Point(100,100),new Point(500,500),500);
				//				int[] location = new int[2];
				//				btn.getLocationOnScreen(location);
				//				System.out.println("location_x"+location[0]);
				//				System.out.println("location_y"+location[1]);
				//				
				//				System.out.println("width"+btn.getWidth());
				//				System.out.println("height"+btn.getHeight());
				//				
				//				
				//				
				//				System.out.println("left:"+btn.getLeft());
				//				System.out.println("right:"+btn.getRight());
				//				System.out.println("top:"+btn.getTop());
				//				System.out.println("bottom:"+btn.getBottom());
				//				
				//				WindowManager wm = (WindowManager)getSystemService(Context.WINDOW_SERVICE);
				//				Point size = new Point();
				//				wm.getDefaultDisplay().getSize(size);
				//				System.out.println(size);
				//				
				//				System.out.println(MainActivity.getViewCenter(btn));*/