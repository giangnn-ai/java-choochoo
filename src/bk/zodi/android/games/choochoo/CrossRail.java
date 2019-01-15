package bk.zodi.android.games.choochoo;

public class CrossRail extends Rail {
	public float[] gsx = {-10f, -9f, -8f, -7f, -6f, -5f, -4f, -3f, -2f, -1f, -0f, 1f, 2f, 3f, 4f, 5f, 6f, 7f, 8f, 9f, 10f};
	public float[] gsy = {0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f};
	public float[] gsa = {0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f};
	
	public CrossRail(float x, float y, int direction) {
		super(x, y, direction);
		// TODO Auto-generated constructor stub
		this.type = GameConstants.RAIL_CROSS;
		for (int i = 0; i < NUM_GUIDE; ++i) {
			gsx[i] *= STEP;
		}
	}

	@Override
	public void turn() {
		// TODO Auto-generated method stub

	}

	@Override
	public Guide getGuide(int enterDirect, int i) {
		// TODO Auto-generated method stub
		if (enterDirect == GameConstants.DIRECTION_RIGHT)
			return new Guide(position.x + gsx[i], position.y + gsy[i], gsa[i]);
		else if (enterDirect == GameConstants.DIRECTION_DOWN)
			return new Guide(position.x + gsy[i], position.y + gsx[NUM_GUIDE - i - 1], 270 + gsa[i]);
		else if (enterDirect == GameConstants.DIRECTION_LEFT)
			return new Guide(position.x + gsx[NUM_GUIDE - i - 1], position.y + gsy[i], 180 + gsa[i]);
		else
			return new Guide(position.x + gsy[i], position.y + gsx[i], 90 + gsa[i]);
	}

	@Override
	public float getAngle() {
		// TODO Auto-generated method stub
		return 0;
	}

}
