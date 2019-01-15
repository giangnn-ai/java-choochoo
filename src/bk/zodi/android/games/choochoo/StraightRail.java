package bk.zodi.android.games.choochoo;

public class StraightRail extends Rail {
	public static final int STRAIGHT_RAIL_HORIZONTAL = 0;
	public static final int STRAIGHT_RAIL_VERTICAL = 1;

	public float[] gsx = {-10f, -9f, -8f, -7f, -6f, -5f, -4f, -3f, -2f, -1f, -0f, 1f, 2f, 3f, 4f, 5f, 6f, 7f, 8f, 9f, 10f};
	public float[] gsy = {0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f};
	public float[] gsa = {0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f};
	
	public StraightRail(float x, float y, int direction) {
		super(x, y, direction);
		// TODO Auto-generated constructor stub
		this.type = GameConstants.RAIL_STRAIGHT;
		for (int i = 0; i < NUM_GUIDE; ++i) {
			gsx[i] *= STEP;
		}
	}
	
	@Override
	public void turn() {
		if (++direction > 1) direction = 0;
	}

	@Override
	public Guide getGuide(int enterDirect, int i) {
		if (direction == STRAIGHT_RAIL_HORIZONTAL) {
			if (enterDirect == GameConstants.DIRECTION_RIGHT) {
				return new Guide(position.x + gsx[i], position.y + gsy[i], gsa[i]);
			} else if (enterDirect == GameConstants.DIRECTION_LEFT) {
				return new Guide(position.x + gsx[NUM_GUIDE - i - 1], position.y + gsy[i], 180 + gsa[i]);
			} else return null;
		} else {
			if (enterDirect == GameConstants.DIRECTION_DOWN) {
				return new Guide(position.x + gsy[i], position.y + gsx[NUM_GUIDE - i - 1], 270 + gsa[i]);
			} else if (enterDirect == GameConstants.DIRECTION_UP) {
				return new Guide(position.x + gsy[i], position.y + gsx[i], 90 + gsa[i]);
			} else return null;
		}
	}
	
	@Override
	public float getAngle() {
		if (direction == STRAIGHT_RAIL_HORIZONTAL)
			return 0;
		else return 90;
	}
}
