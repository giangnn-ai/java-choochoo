package bk.zodi.android.games.choochoo;

import com.badlogic.androidgames.gamedev2d.GameObject;

public abstract class Rail extends GameObject {
	public static final float RAIL_WIDTH = 1f;
	public static final float RAIL_HEIGHT = 1f;
	public static final float STEP = 0.05f;
	public static final int NUM_GUIDE = 21;
	
	protected int type;
	public int direction;
	public boolean isAutoRotate = false;
	public float nextRail = 1f;

	public Rail(float x, float y, int direction) {
		super(x, y, RAIL_WIDTH, RAIL_HEIGHT);
		// TODO Auto-generated constructor stub
		this.direction = direction;
	}
	
	public int getType() {
		return type;
	}
	
	public abstract void turn();
	
	public abstract Guide getGuide(int enterDirect, int i);
	
	public abstract float getAngle();
}
