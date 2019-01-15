package bk.zodi.android.games.choochoo;

import com.badlogic.androidgames.gamedev2d.GameObject;

public class Button extends GameObject {
	public static final float BUTTON_WIDTH = 120;
	public static final float BUTTON_HEIGHT = 40;
	
	public static final int BUTTON_CHOOCHOO = 0;
	public static final int BUTTON_CHANGESPEED = 1;
	public static final int BUTTON_RESTART = 2;
	public static final int BUTTON_MENU = 3;
	
	public int type;
	
	public Button(float x, float y, int type) {
		super(x, y, BUTTON_WIDTH, BUTTON_HEIGHT);
		// TODO Auto-generated constructor stub
		this.type = type;
	}
}
