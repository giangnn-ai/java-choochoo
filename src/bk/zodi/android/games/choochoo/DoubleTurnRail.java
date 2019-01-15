package bk.zodi.android.games.choochoo;

public class DoubleTurnRail extends Rail {
	public static final int DOUBLE_TURN_RAIL_QUATER_24 = 0;
	public static final int DOUBLE_TURN_RAIL_QUATER_13 = 1;
	
	
	public float[] gtx = {-10f, -9f, -8f, -7f, -6f, -5f, -4.5f, -4f, -3.5f, -3f, -2.5f, -2f, -1.5f, -1f, 0.5f, 0f, 0f, 0f, 0f, 0f, 0f};
	public float[] gty = {-0f, -0f, 0f, 0f, 0f, 0f, 0.5f, 1f, 1.5f, 2f, 2.5f, 3f, 3.5f, 4f, 4.5f, 5f, 6f, 7f, 8f, 9f, 10f};
	public float[] gta = {0f, 0f, 0f, 0f, 0f, 0f, 9f, 18f, 27f, 36f, 45f, 54f, 63f, 72f, 81f, 90f, 90f, 90f, 90f, 90f, 90f};
	
	public DoubleTurnRail(float x, float y, int direction) {
		super(x, y, direction);
		// TODO Auto-generated constructor stub
		this.type = GameConstants.RAIL_DOUBLE_TURN;
		for (int i = 0; i < NUM_GUIDE; ++i) {
			gtx[i] *= STEP;
			gty[i] *= STEP;
		}
	}
	@Override
	public void turn() {
		// TODO Auto-generated method stub
		if (++direction > 1) direction = 0;
	}

	@Override
	public Guide getGuide(int enterDirect, int i) {
		// TODO Auto-generated method stub
		if (direction == DOUBLE_TURN_RAIL_QUATER_24) {
			if (enterDirect == GameConstants.DIRECTION_DOWN) {
				return new Guide(position.x + gtx[NUM_GUIDE - i - 1], position.y + gty[NUM_GUIDE - i - 1], 270 - gta[i]);
			} else if (enterDirect == GameConstants.DIRECTION_LEFT) {
				return new Guide(position.x + -gtx[i], position.y + -gty[i], 180 + gta[i]);
			} else if (enterDirect == GameConstants.DIRECTION_UP) {
				return new Guide(position.x + -gtx[NUM_GUIDE - i - 1], position.y + -gty[NUM_GUIDE - i - 1], 90 - gta[i]);
			} else {
				return new Guide(position.x + gtx[i], position.y + gty[i], gta[i]);
			}
		} else {
			if (enterDirect == GameConstants.DIRECTION_DOWN) {
				return new Guide(position.x + -gtx[NUM_GUIDE - i - 1], position.y + gty[NUM_GUIDE - i - 1], 270 + gta[i]);
			} else if (enterDirect == GameConstants.DIRECTION_LEFT) {
				return new Guide(position.x + -gtx[i], position.y + gty[i], 180 - gta[i]);
			} else if (enterDirect == GameConstants.DIRECTION_UP) {
				return new Guide(position.x + gtx[NUM_GUIDE - i - 1], position.y + -gty[NUM_GUIDE - i - 1], 90 + gta[i]);
			} else {
				return new Guide(position.x + gtx[i], position.y + -gty[i], 360 - gta[i]);
			}
		}
	}

	@Override
	public float getAngle() {
		// TODO Auto-generated method stub
		if (direction == DOUBLE_TURN_RAIL_QUATER_24)
			return 0;
		else 
			return 90;
	}

}
