package bk.zodi.android.games.choochoo;

public class JumpBridgeRail extends Rail {
	public static final int JUMP_BRIDGE_RAIL_UP = 0;
	public static final int JUMP_BRIDGE_RAIL_LEFT = 1;
	public static final int JUMP_BRIDGE_RAIL_DOWN = 2;
	public static final int JUMP_BRIDGE_RAIL_RIGHT = 3;
	
	public float[] gsx = {0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f};
	public float[] gsy = {-10f, -8f, -6f, -4f, -2f, 0f, 2f, 4f, 6f, 8f, 10f, 12f, 14f, 16f, 18f, 20f, 22f, 24f, 26f, 28f, 30f};
	public float[] gsa = {90f, 90f, 90f, 90f, 90f, 90f, 90f, 90f, 90f, 90f, 90f, 90f, 90f, 90f, 90f, 90f, 90f, 90f, 90f, 90f, 90f};
	

	public JumpBridgeRail(float x, float y, int direction) {
		super(x, y, direction);
		// TODO Auto-generated constructor stub
		this.type = GameConstants.RAIL_JUMP_BRIDGE;
		for (int i = 0; i < NUM_GUIDE; ++i) {
			gsy[i] *= STEP;
		}
		this.nextRail = 2f;
	}

	@Override
	public void turn() {
		// TODO Auto-generated method stub
		if (++direction > 3) direction = 0;
	}

	@Override
	public Guide getGuide(int enterDirect, int i) {
		// TODO Auto-generated method stub
		if (direction == JUMP_BRIDGE_RAIL_UP) {
			if (enterDirect == GameConstants.DIRECTION_UP) {
				return new Guide(position.x + gsx[i], position.y + gsy[i], gsa[i]);
			} else return null;
		} else if (direction == JUMP_BRIDGE_RAIL_LEFT) {
			if (enterDirect == GameConstants.DIRECTION_LEFT) {
				return new Guide(position.x - gsy[i], position.y + gsx[i], 90 + gsa[i]);
			} else return null;
		} else if (direction == JUMP_BRIDGE_RAIL_DOWN) {
			if (enterDirect == GameConstants.DIRECTION_DOWN) {
				return new Guide(position.x + gsx[i], position.y + gsy[i], 180 + gsa[i]);
			} else return null;
		} else {
			if (enterDirect == GameConstants.DIRECTION_RIGHT) {
				return new Guide(position.x + gsy[i], position.y + gsx[i], 270 + gsa[i]);
			} else return null;
		}
	}

	@Override
	public float getAngle() {
		// TODO Auto-generated method stub
		if (direction == JUMP_BRIDGE_RAIL_UP)
			return 0;
		else if (direction == JUMP_BRIDGE_RAIL_LEFT)
			return 90;
		else if (direction == JUMP_BRIDGE_RAIL_DOWN)
			return 180;
		else
			return 270;
	}

}
