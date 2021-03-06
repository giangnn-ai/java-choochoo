package com.badlogic.androidgames.framework.impl;

import java.util.List;

import com.badlogic.androidgames.framework.Input;

import android.content.Context;
import android.os.Build.VERSION;
import android.view.View;


public class AndroidInput implements Input {
	AccelerometerHandler accelHandler;
	KeyboardHandler keyHandler;
	TouchHandler touchHandler;
	
	public AndroidInput(Context context, View view, float scaleX, float scaleY) {
		//accelHandler = new AccelerometerHandler(context);
		keyHandler = new KeyboardHandler(view);
		if (Integer.parseInt(VERSION.SDK) < 5)
			touchHandler = new SingleTouchHandler(view, scaleX, scaleY);
		else
			touchHandler = new MultiTouchHandler(view, scaleX, scaleY);
	}
	
	@Override
	public boolean isKeyPressed(int keyCode) {
		// TODO Auto-generated method stub
		return keyHandler.isKeyPressed(keyCode);
	}

	@Override
	public boolean isTouchDown(int pointer) {
		// TODO Auto-generated method stub
		return touchHandler.isTouchDown(pointer) ;
	}

	@Override
	public int getTouchX(int pointer) {
		// TODO Auto-generated method stub
		return touchHandler.getTouchX(pointer);
	}

	@Override
	public int getTouchY(int pointer) {
		// TODO Auto-generated method stub
		return touchHandler.getTouchY(pointer);
	}

	@Override
	public float getAccelX() {
		// TODO Auto-generated method stub
		return accelHandler.getAccelX();
	}

	@Override
	public float getAccelY() {
		// TODO Auto-generated method stub
		return accelHandler.getAccelY();
	}

	@Override
	public float getAccelZ() {
		// TODO Auto-generated method stub
		return accelHandler.getAccelZ();
	}

	@Override
	public List<KeyEvent> getKeyEvents() {
		// TODO Auto-generated method stub
		return keyHandler.getKeyEvents();
	}

	@Override
	public List<TouchEvent> getTouchEvents() {
		// TODO Auto-generated method stub
		return touchHandler.getTouchEvents();
	}

}
