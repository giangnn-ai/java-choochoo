package bk.zodi.android.games.choochoo;

import com.badlogic.androidgames.gamedev2d.GameObject;

public class Station extends GameObject {
	public static final int STATION_RIGHT = 0;
	public static final int STATION_UP = 1;
	public static final int STATION_LEFT = 2;
	public static final int STATION_DOWN = 3;
	
	
	public static final float STATION_WIDTH = 1f;
	public static final float STATION_HEIGHT = 1f;
	
	public int type;
	public int direction;
	public float angle;
	
	public Station(float x, float y, int type, int direction) {
		super(x, y, STATION_WIDTH, STATION_HEIGHT);
		// TODO Auto-generated constructor stub
		this.type = type;
		this.direction = direction;
		if (direction == STATION_RIGHT)
			angle = 0;
		else if (direction == STATION_UP)
			angle = 90;
		else if (direction == STATION_LEFT)
			angle = 180;
		else
			angle = 270;
	}
	
	public int getType() {
		return type;
	}
	
	public int getDirection() {
		return direction;
	}
}
