package com.gordonrawe.funnymenu;

import android.graphics.Point;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.OvershootInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;

public class AnimationHelper {
	public static void enableView(View view){
		view.setVisibility(0);
		view.setFocusable(true);
		view.setClickable(true);
		view.setAlpha(1);
	}

	public static void disableView(View view){
//		view.setVisibility(4);//这里如果改为8的话会有问题，它在空间中不会占有尺寸
		view.setFocusable(false);
		view.setClickable(false);
	}
	public static void startExpandInContainer(ViewGroup group,int duration){
		for(int i = 0; i < group.getChildCount();i++){
			ImageButton img_button = (ImageButton)group.getChildAt(i);
			AnimationHelper.startTranslateAnimationExpand(img_button, MeFragment.center, MeFragment.points[i],10*i, duration);
			enableView(img_button);
		}
	}

	public static void startShrinkInContainer(ViewGroup group,int duration){
		for(int i = 0; i < group.getChildCount();i++){
			ImageButton img_button = (ImageButton)group.getChildAt(i);
			AnimationHelper.startTranslateAnimationShrink(img_button, MeFragment.points[i], MeFragment.center,10*i, duration);
			disableView(img_button);
		}
	}

	public static Animation getRotateAnimation(float from,float to,int duration){
		RotateAnimation animation = new RotateAnimation(from, to, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		animation.setDuration(duration);
		animation.setFillAfter(true);
		return animation;
	}

	public static void startTranslateAnimationExpand(final View view,Point start,final Point end,int offset,int duration){
		Animation animation = new TranslateAnimation(start.x - end.x, 0, start.y - end.y, 0);
		Animation s_aniAnimation = new ScaleAnimation(0, 1, 0, 1, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		Animation alpha = new AlphaAnimation(0, 1);
		AnimationSet as = new AnimationSet(true);
		as.addAnimation(s_aniAnimation);
		as.addAnimation(animation);
		as.addAnimation(alpha);
		as.setDuration(duration);
		as.setFillAfter(true);
		as.setStartOffset(offset);
		as.setInterpolator(new OvershootInterpolator(2F));
		view.startAnimation(as);
		PositionHelper.moveViewCenterTo(view, end.x, end.y);
	}
	
	public static void slowMove(final View view,Point start,final Point end,int offset,int duration){
		Animation animation = new TranslateAnimation(start.x - end.x, 0, start.y - end.y, 0);
		animation.setDuration(duration);
		animation.setFillAfter(true);
		animation.setStartOffset(offset);
		animation.setInterpolator(new OvershootInterpolator(2F));
		view.startAnimation(animation);
		PositionHelper.moveViewCenterTo(view, end.x, end.y);
	}
	
	public static void startTranslateAnimationShrink(final View view,Point start,final Point end,int offset,int duration){
		Animation animation = new TranslateAnimation(start.x - end.x, 0, start.y - end.y, 0);
		Animation s_aniAnimation = new ScaleAnimation(1, 0, 1, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		Animation alpha = new AlphaAnimation(1, 0);
		AnimationSet as = new AnimationSet(true);
		as.addAnimation(s_aniAnimation);
		as.addAnimation(animation);
		as.addAnimation(alpha);
		as.setDuration(duration);
		as.setFillAfter(true);
		as.setStartOffset(offset);
		as.setInterpolator(new OvershootInterpolator(1F));
		view.startAnimation(as);
		PositionHelper.moveViewCenterTo(view, end.x, end.y);
	}


	public static Animation getSmallerAnimation(int duration){
		Animation animation = new ScaleAnimation(1.0f, 0f, 1.0f, 0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		animation.setDuration(duration);
		animation.setFillAfter(true);
		return animation;
	}

	public static Animation getBiggerAndDisappear(int duration){
		AnimationSet as = new AnimationSet(true);
		Animation scale_animation = new ScaleAnimation(1.0f, 4.0f, 1.0f, 4.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		Animation alpha_animation = new AlphaAnimation(1, 0);		
		as.addAnimation(scale_animation);
		as.addAnimation(alpha_animation);
		as.setDuration(duration);
		as.setFillAfter(true);
		return as;
	}
}