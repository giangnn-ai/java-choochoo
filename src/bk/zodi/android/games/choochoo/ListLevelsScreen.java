package bk.zodi.android.games.choochoo;

import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import com.badlogic.androidgames.framework.Game;
import com.badlogic.androidgames.framework.Input.TouchEvent;
import com.badlogic.androidgames.framework.gl.Camera2D;
import com.badlogic.androidgames.framework.gl.SpriteBatcher;
import com.badlogic.androidgames.framework.impl.GLScreen;
import com.badlogic.androidgames.framework.math.OverlapTester;
import com.badlogic.androidgames.framework.math.Rectangle;
import com.badlogic.androidgames.framework.math.Vector2;

public class ListLevelsScreen extends GLScreen {
	Camera2D guiCam;
	SpriteBatcher batcher;
	Rectangle[] level;
	Vector2 touchPoint;

	int startX = 60;
	int startY = 380;

	public ListLevelsScreen(Game game) {
		super(game);
		guiCam = new Camera2D(glGraphics, 800, 480);
		batcher = new SpriteBatcher(glGraphics, 100);
		level = new Rectangle[21];
		for (int i = 0; i < 21; ++i) {
			level[i] = new Rectangle(startX + (i % 7) * 100, startY - (i / 7)
					* 100 - 80, 80, 80);
		}
		touchPoint = new Vector2();

	}

	@Override
	public void update(float deltaTime) {
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		game.getInput().getKeyEvents();

		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			if (event.type == TouchEvent.TOUCH_UP) {
				touchPoint.set(event.x, event.y);
				guiCam.touchToWorld(touchPoint);
				for (int j = 0; j < 21; ++j) {
					if (OverlapTester.pointInRectangle(level[j], touchPoint)) {
						System.out.println(j);
						game.setScreen(new GameScreen(game, j));
					}
				}
			}
		}
	}

	@Override
	public void present(float deltaTime) {
		GL10 gl = glGraphics.getGL();
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		guiCam.setViewportAndMatrices();

		gl.glEnable(GL10.GL_TEXTURE_2D);

		batcher.beginBatch(Assets.background);
		batcher.drawSprite(400, 240, 800, 480, Assets.backgroundRegion);
		batcher.endBatch();

		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

		batcher.beginBatch(Assets.items);
		for (int i = 0; i < 21; ++i) {
			float x = startX + (i % 7) * 100 + 40;
			float y = startY - (i / 7) * 100 - 40;
			batcher.drawSprite(x, y, 80, 80, Assets.level);
			Assets.font.drawText(batcher, Integer.toString(i + 1), x, y);
			if (Settings.levels[i] == 0)
				batcher.drawSprite(x, y, 40, 40, Assets.lock);
		}

		batcher.endBatch();

		gl.glDisable(GL10.GL_BLEND);
	}

	@Override
	public void pause() {
		Settings.save(game.getFileIO());
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
	}
}
