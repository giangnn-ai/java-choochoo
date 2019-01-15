package com.badlogic.androidgames.gamedev2d;

public class RotativeGameObject extends GameObject {
	public float angle;

	public RotativeGameObject(float x, float y, float width, float height) {
		super(x, y, width, height);
		angle = 0;
	}
}
