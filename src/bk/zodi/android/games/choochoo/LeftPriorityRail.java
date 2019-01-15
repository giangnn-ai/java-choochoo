package bk.zodi.android.games.choochoo;

public class LeftPriorityRail extends Rail {
	public static final int LEFT_PRIORITY_RAIL_UP = 0;
	public static final int LEFT_PRIORITY_RAIL_LEFT = 1;
	public static final int LEFT_PRIORITY_RAIL_DOWN = 2;
	public static final int LEFT_PRIORITY_RAIL_RIGHT = 3;
	
	public float[] gtx = {-10f, -9f, -8f, -7f, -6f, -5f, -4.5f, -4f, -3.5f, -3f, -2.5f, -2f, -1.5f, -1f, 0.5f, 0f, 0f, 0f, 0f, 0f, 0f};
	public float[] gty = {-0f, -0f, 0f, 0f, 0f, 0f, 0.5f, 1f, 1.5f, 2f, 2.5f, 3f, 3.5f, 4f, 4.5f, 5f, 6f, 7f, 8f, 9f, 10f};
	public float[] gta = {0f, 0f, 0f, 0f, 0f, 0f, 9f, 18f, 27f, 36f, 45f, 54f, 63f, 72f, 81f, 90f, 90f, 90f, 90f, 90f, 90f};
	
	public float[] gsx = {-10f, -9f, -8f, -7f, -6f, -5f, -4f, -3f, -2f, -1f, -0f, 1f, 2f, 3f, 4f, 5f, 6f, 7f, 8f, 9f, 10f};
	public float[] gsy = {0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f};
	public float[] gsa = {0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f};
	
	
	public LeftPriorityRail(float x, float y, int direction) {
		super(x, y, direction);
		// TODO Auto-generated constructor stub
		this.type = GameConstants.RAIL_LEFT_PRIORITY;
		for (int i = 0; i < NUM_GUIDE; ++i) {
			gtx[i] *= STEP;
			gty[i] *= STEP;
			gsx[i] *= STEP;
		}
	}

	@Override
	public void turn() {
		// TODO Auto-generated method stub
		if (++direction > 3) direction = 0;
	}

	@Override
	public Guide getGuide(int enterDirect, int i) {
		// TODO Auto-generated method stub
		if (direction == LEFT_PRIORITY_RAIL_UP) {
			if (enterDirect == GameConstants.DIRECTION_UP) {
				return null;
			} else if(enterDirect == GameConstants.DIRECTION_DOWN) {
				return new Guide(position.x + -gtx[NUM_GUIDE - i - 1], position.y + gty[NUM_GUIDE - i - 1], 270 + gta[i]);
			} else if (enterDirect == GameConstants.DIRECTION_RIGHT) {
				return new Guide(position.x + gsx[i], position.y + gsy[i], gsa[i]);
			} else {
				return new Guide(position.x + gsx[NUM_GUIDE - i - 1], position.y + gsy[i], 180 + gsa[i]);
			}
		} else if (direction == LEFT_PRIORITY_RAIL_LEFT) {
			if (enterDirect == GameConstants.DIRECTION_LEFT) {
				return null;
			} else if(enterDirect == GameConstants.DIRECTION_RIGHT) {
				return new Guide(position.x + gtx[i], position.y + gty[i], gta[i]);
			} else if (enterDirect == GameConstants.DIRECTION_UP) {
				return new Guide(position.x + gsy[i], position.y + gsx[i], 90 + gsa[i]);
			} else {
				return new Guide(position.x + gsy[i], position.y + gsx[NUM_GUIDE - i - 1], 270 + gsa[i]);
			}
		} else if (direction == LEFT_PRIORITY_RAIL_DOWN) {
			if (enterDirect == GameConstants.DIRECTION_DOWN) {
				return null;
			} else if(enterDirect == GameConstants.DIRECTION_UP) {
				return new Guide(position.x + gtx[NUM_GUIDE - i - 1], position.y + -gty[NUM_GUIDE - i - 1], 90 + gta[i]);
			} else if (enterDirect == GameConstants.DIRECTION_RIGHT) {
				return new Guide(position.x + gsx[i], position.y + gsy[i], gsa[i]);
			} else {
				return new Guide(position.x + gsx[NUM_GUIDE - i - 1], position.y + gsy[i], 180 + gsa[i]);
			}
		} else {
			if (enterDirect == GameConstants.DIRECTION_RIGHT) {
				return null;
			} else if(enterDirect == GameConstants.DIRECTION_LEFT) {
				return new Guide(position.x + -gtx[i], position.y + -gty[i], 180 + gta[i]);
			} else if (enterDirect == GameConstants.DIRECTION_UP) {
				return new Guide(position.x + gsy[i], position.y + gsx[i], 90 + gsa[i]);
			} else {
				return new Guide(position.x + gsy[i], position.y + gsx[NUM_GUIDE - i - 1], 270 + gsa[i]);
			}
		}
	}

	@Override
	public float getAngle() {
		// TODO Auto-generated method stub
		if (direction == LEFT_PRIORITY_RAIL_UP)
			return 0;
		else if (direction == LEFT_PRIORITY_RAIL_LEFT)
			return 90;
		else if (direction == LEFT_PRIORITY_RAIL_DOWN)
			return 180;
		else 
			return 270;
	}

}
