package bk.zodi.android.games.choochoo;

import javax.microedition.khronos.opengles.GL10;

import com.badlogic.androidgames.framework.gl.Animation;
import com.badlogic.androidgames.framework.gl.Camera2D;
import com.badlogic.androidgames.framework.gl.SpriteBatcher;
import com.badlogic.androidgames.framework.gl.TextureRegion;
import com.badlogic.androidgames.framework.impl.GLGraphics;

public class WorldRenderer {
	static final float FRUSTUM_WIDTH = 20;
	static final float FRUSTUM_HEIGHT = 12;
	GLGraphics glGraphics;
	World world;
	Camera2D cam;
	SpriteBatcher batcher;

	public WorldRenderer(GLGraphics glGraphics, SpriteBatcher batcher,
			World world) {
		this.glGraphics = glGraphics;
		this.world = world;
		this.cam = new Camera2D(glGraphics, FRUSTUM_WIDTH, FRUSTUM_HEIGHT);
		this.batcher = batcher;
	}

	public void render() {
		cam.setViewportAndMatrices();
		renderBackground();
		renderObjects();
	}

	public void renderBackground() {
		batcher.beginBatch(Assets.background);
		batcher.drawSprite(cam.position.x, cam.position.y, FRUSTUM_WIDTH,
				FRUSTUM_HEIGHT, Assets.backgroundRegion);
		batcher.endBatch();
	}

	public void renderObjects() {
		GL10 gl = glGraphics.getGL();
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		renderRails();
		renderStations();
		renderTrains();
	}

	public void renderRails() {
		batcher.beginBatch(Assets.items);
		int len = world.rails.size();
		for (int i = 0; i < len; i++) {
			Rail rail = world.rails.get(i);
			if (rail.isAutoRotate)
				batcher.drawSprite(rail.position.x, rail.position.y, 1, 1,
						Assets.autoRotate);
			float angle = rail.getAngle();
			batcher.drawSprite(rail.position.x, rail.position.y, 1, 1, angle,
					Assets.rails[rail.type]);
		}
		batcher.endBatch();
	}

	public void renderTrains() {
		if (world.state != World.WORLD_STATE_LEVEL_COMPLETE) {
			batcher.beginBatch(Assets.items);
			int len = world.trains.size();
			for (int i = 0; i < len; i++) {
				Train train = world.trains.get(i);
				Carriage carriage = train.carriages.get(0);
				if (!carriage.isDisappear)
					batcher.drawSprite(carriage.position.x,
							carriage.position.y, 1, 0.5f, carriage.angle,
							Assets.heads[train.getType()]);
				int numCarriage = train.carriages.size();
				for (int j = 1; j < numCarriage; ++j) {
					carriage = train.carriages.get(j);
					if (!carriage.isDisappear)
						batcher.drawSprite(carriage.position.x,
								carriage.position.y, 1, 0.5f, carriage.angle,
								Assets.carriages[train.getType()]);
				}
			}
			batcher.endBatch();
		}
	}

	public void renderStations() {
		batcher.beginBatch(Assets.items);
		int len = world.stations.size();
		for (int i = 0; i < len; i++) {
			Station station = world.stations.get(i);
			batcher.drawSprite(station.position.x, station.position.y, 1, 1,
					station.angle, Assets.stations[station.getType()]);
		}
		batcher.endBatch();
	}
}
