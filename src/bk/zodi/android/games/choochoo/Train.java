package bk.zodi.android.games.choochoo;

import java.util.ArrayList;
import java.util.List;

public class Train {
	int type;
	public List<Carriage> carriages;
	public int inRailPosition = 10;
	public boolean isCrash = false;
	
	public Train(float x, float y, int direction, int type, int numCarriage, int inRailPosition) {
		carriages = new ArrayList<Carriage>();
		this.type = type;
		for (int i = 0; i < numCarriage; ++i) {
			float cx = 0, cy = 0, angle = 0;
			if (direction == GameConstants.DIRECTION_RIGHT) {
				cx = x - i * Carriage.CARRIAGE_WIDTH;
				cy = y;
				angle = 0;
			} else if (direction == GameConstants.DIRECTION_UP) {
				cx = x;
				cy = y + i * Carriage.CARRIAGE_HEIGHT;
				angle = 90;
			} else if (direction == GameConstants.DIRECTION_LEFT) {
				cx = x + i * Carriage.CARRIAGE_WIDTH;
				cy = y;
				angle = 180;
			} else {
				cx = x;
				cy = y - i * Carriage.CARRIAGE_HEIGHT;
				angle = 270;
			}
			Carriage carriage = new Carriage(cx, cy, type, angle);
			carriages.add(carriage);
		}
		this.inRailPosition = inRailPosition;
	}
	
	public int getType() {
		return type;
	}
}
