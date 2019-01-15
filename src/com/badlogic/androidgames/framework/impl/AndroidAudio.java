package com.badlogic.androidgames.framework.impl;

import java.io.IOException;

import com.badlogic.androidgames.framework.Audio;
import com.badlogic.androidgames.framework.Music;
import com.badlogic.androidgames.framework.Sound;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;


public class AndroidAudio implements Audio {
	AssetManager	assets;
	SoundPool soundPool;
	
	public AndroidAudio(Activity activity) {
		activity.setVolumeControlStream(AudioManager.STREAM_MUSIC);
		this.assets = activity.getAssets();
		this.soundPool = new SoundPool(20, AudioManager.STREAM_MUSIC, 0);
	}
	
	@Override
	public Music newMusic(String fileName) {
		// TODO Auto-generated method stub
		try {
			AssetFileDescriptor assetFileDescriptor = assets.openFd(fileName);
			return new AndroidMusic(assetFileDescriptor);
		} catch (IOException e) {
			throw new RuntimeException("Couldn't load music '" + fileName + "'");
		}
	}

	@Override
	public Sound newSound(String fileName) {
		// TODO Auto-generated method stub
		try {
			AssetFileDescriptor assetFileDescriptor = assets.openFd(fileName);
			int soundId = soundPool.load(assetFileDescriptor, 0);
			return new AndroidSound(soundPool, soundId);
		} catch (IOException e) {
			throw new RuntimeException("Couldn't load sound '" + fileName + "'");
		}
	}

}
