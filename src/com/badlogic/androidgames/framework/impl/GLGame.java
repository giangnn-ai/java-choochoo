package com.badlogic.androidgames.framework.impl;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.app.Activity;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.Renderer;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.view.Window;
import android.view.WindowManager;

import com.badlogic.androidgames.framework.Audio;
import com.badlogic.androidgames.framework.FileIO;
import com.badlogic.androidgames.framework.Game;
import com.badlogic.androidgames.framework.Graphics;
import com.badlogic.androidgames.framework.Input;
import com.badlogic.androidgames.framework.Screen;

public class GLGame extends Activity implements Game, Renderer {
	enum GLGameState {
		Initialized,
		Running,
		Paused,
		Finished,
		Idle
	}
	
	GLSurfaceView glView;
	GLGraphics glGraphics;
	Audio audio;
	Input input;
	FileIO fileIO;
	Screen screen;
	GLGameState state = GLGameState.Initialized;
	Object stateChanged = new Object();
	long startTime = System.nanoTime();
	WakeLock wakeLock;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		glView = new GLSurfaceView(this);
		glView.setRenderer(this);
		setContentView(glView);
		
		glGraphics = new GLGraphics(glView);
		fileIO = new AndroidFileIO(this);
		audio = new AndroidAudio(this);
		input = new AndroidInput(this, glView, 1, 1);
		PowerManager powerManage = (PowerManager) getSystemService(Context.POWER_SERVICE);
		wakeLock = powerManage.newWakeLock(PowerManager.FULL_WAKE_LOCK, "GLGame");	
	}
	
	@Override
	public void onResume() {
		super.onResume();
		glView.onResume();
		wakeLock.acquire();
	}
	
	@Override
	public void onPause() {
		synchronized (stateChanged) {
			if (isFinishing())
				state = GLGameState.Finished;
			else
				state = GLGameState.Paused;
			while (true) {
				try {
					stateChanged.wait();
					break;
				} catch (InterruptedException e) {
				}
			}
		}
		wakeLock.release();
		glView.onPause();
		super.onPause();
	}
	
	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		// TODO Auto-generated method stub
		glGraphics.setGL(gl);
		
		synchronized (stateChanged) {
			if (state == GLGameState.Initialized)
				screen = getStartScreen();
			state = GLGameState.Running;
			screen.resume();
			startTime = System.nanoTime();
		}
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDrawFrame(GL10 gl) {
		// TODO Auto-generated method stub
		GLGameState state = null;
		
		synchronized (stateChanged) {
			state = this.state;
		}
		
		if (state == GLGameState.Running) {
			float deltaTime = (System.nanoTime() - startTime) / 1000000000.0f;
			startTime = System.nanoTime();
			
			screen.update(deltaTime);
			screen.present(deltaTime);
		}
		
		if (state == GLGameState.Paused) {
			screen.pause();
			synchronized (stateChanged) {
				this.state = GLGameState.Idle;
				stateChanged.notifyAll();
			}
		}
		
		if (state == GLGameState.Finished) {
			screen.pause();
			screen.dispose();
			synchronized (stateChanged) {
				this.state = GLGameState.Idle;
				stateChanged.notifyAll();
			}
		}
	}
	
	public GLGraphics getGLGraphics() {
		return glGraphics;
	}

	@Override
	public Input getInput() {
		// TODO Auto-generated method stub
		return input;
	}

	@Override
	public FileIO getFileIO() {
		// TODO Auto-generated method stub
		return fileIO;
	}

	@Override
	public Graphics getGraphics() {
		// TODO Auto-generated method stub
		throw new IllegalStateException("We are using OpenGL");
	}

	@Override
	public Audio getAudio() {
		// TODO Auto-generated method stub
		return audio;
	}

	@Override
	public void setScreen(Screen screen) {
		// TODO Auto-generated method stub
		if (screen == null)
			throw new IllegalArgumentException("Screen must not be null");
		
		this.screen.pause();
		this.screen.dispose();
		screen.resume();
		screen.update(0);
		this.screen = screen;
	}

	@Override
	public Screen getCurrentScreen() {
		// TODO Auto-generated method stub
		return screen;
	}

	@Override
	public Screen getStartScreen() {
		// TODO Auto-generated method stub
		return null;
	}

}
