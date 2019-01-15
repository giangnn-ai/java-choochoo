package bk.zodi.android.games.choochoo;

import com.badlogic.androidgames.gamedev2d.RotativeGameObject;

public class Carriage extends RotativeGameObject {
	public static final float CARRIAGE_WIDTH = 1f;
	public static final float CARRIAGE_HEIGHT = 0.5f;

	public int direction;
	public Rail rail;
	public boolean isDisappear = false;
	public int type;

	public Carriage(float x, float y, int type, float angle) {
		super(x, y, CARRIAGE_WIDTH, CARRIAGE_HEIGHT);
		// TODO Auto-generated constructor stub
		this.angle = angle;
		this.direction = updateDirection();
		this.type = type;
	}

	public int updateDirection() {
		if ((angle % 360) == 0)
			return GameConstants.DIRECTION_RIGHT;
		else if ((angle % 360) == 90)
			return GameConstants.DIRECTION_UP;
		else if ((angle % 360) == 180)
			return GameConstants.DIRECTION_LEFT;
		else
			return GameConstants.DIRECTION_DOWN;
	}

	public void move(int inRailPosition) throws NullPointerException {
		Guide guide = rail.getGuide(direction, inRailPosition);
		if (guide != null) {
			position.x = guide.x;
			position.y = guide.y;
			angle = guide.angle;
		} else {
			throw new NullPointerException();
		}
	}
}
