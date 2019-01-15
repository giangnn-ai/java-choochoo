package bk.zodi.android.games.choochoo;

import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import bk.zodi.android.games.choochoo.World.WorldListener;

import com.badlogic.androidgames.framework.Game;
import com.badlogic.androidgames.framework.Input.TouchEvent;
import com.badlogic.androidgames.framework.gl.Camera2D;
import com.badlogic.androidgames.framework.gl.SpriteBatcher;
import com.badlogic.androidgames.framework.impl.GLScreen;
import com.badlogic.androidgames.framework.math.OverlapTester;
import com.badlogic.androidgames.framework.math.Rectangle;
import com.badlogic.androidgames.framework.math.Vector2;


public class GameScreen extends GLScreen {
	static final int GAME_READY = 0;
	static final int GAME_RUNNING = 1;
	static final int GAME_PAUSED = 2;
	static final int GAME_LEVEL_END = 3;
	static final int GAME_OVER = 4;

	int state;
	Camera2D guiCam;
	Vector2 touchPoint;
	SpriteBatcher batcher;
	World world;
	WorldListener worldListener;
	WorldRenderer renderer;
	Rectangle pauseBounds;
	Rectangle resumeBounds;
	Rectangle quitBounds;
	Button[] buttons;

	public GameScreen(Game game, int level) {
		super(game);
		state = GAME_READY;
		guiCam = new Camera2D(glGraphics, 800, 480);
		touchPoint = new Vector2();
		batcher = new SpriteBatcher(glGraphics, 1000);
		if (Settings.soundEnabled)
			Assets.music.stop();
		worldListener = new WorldListener() {

			@Override
			public void crash() {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void choochoo() {
				// TODO Auto-generated method stub
				//Assets.playSound(Assets.choo);
			}

			@Override
			public void wheelshreek() {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void win() {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void autoRotate() {
				// TODO Auto-generated method stub
				
			}

		};
		world = new World(worldListener, level);
		renderer = new WorldRenderer(glGraphics, batcher, world);
		buttons = new Button[4];
		for (int i = 0; i < 4; ++i) {
			buttons[i] = new Button(130 + i * 160, 450, i);
		}
	}

	@Override
	public void update(float deltaTime) {
		switch (state) {
		case GAME_READY:
			updateReady();
			break;
		case GAME_RUNNING:
			updateRunning(deltaTime);
			break;
		case GAME_PAUSED:
			updatePaused();
			break;
		case GAME_LEVEL_END:
			updateLevelEnd();
			break;
		case GAME_OVER:
			updateGameOver();
			break;
		}
	}

	private void updateReady() {
		if (game.getInput().getTouchEvents().size() > 0) {
			state = GAME_RUNNING;
		}
	}

	private void updateRunning(float deltaTime) {
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			if (event.type != TouchEvent.TOUCH_UP)
				continue;

			touchPoint.set(event.x, event.y);
			guiCam.touchToWorld(touchPoint);
			for (int j = 0; j < 4; ++j) {
				if (OverlapTester.pointInRectangle(buttons[j].bounds,
						touchPoint)) {
					if (buttons[j].type == Button.BUTTON_CHOOCHOO)
						world.state = World.WORLD_STATE_RUNNING;
					else if (buttons[j].type == Button.BUTTON_CHANGESPEED)
						world.changeSpeed();
					else if (buttons[j].type == Button.BUTTON_RESTART) {
						world.restart();
					} else if (buttons[j].type == Button.BUTTON_MENU) {
						game.setScreen(new ListLevelsScreen(game));
					}
					System.out.println("touch " + j);
					break;
				}
			}
			world.touchWorld(touchPoint);
			if (world.state == World.WORLD_STATE_RAILING)
				world.checkCoolisionsRails(touchPoint);
		}

		world.update(deltaTime);
	}

	private void updatePaused() {

	}

	private void updateLevelEnd() {

	}

	private void updateGameOver() {

	}

	public void renderButtons() {
		Button button;
		for (int i = 0; i < 4; ++i) {
			button = buttons[i];
			batcher.drawSprite(button.position.x, button.position.y, 120, 40,
					Assets.buttons[button.type]);
		}
	}

	@Override
	public void present(float deltaTime) {
		GL10 gl = glGraphics.getGL();
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		gl.glEnable(GL10.GL_TEXTURE_2D);

		renderer.render();

		guiCam.setViewportAndMatrices();
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		batcher.beginBatch(Assets.items);
		switch (state) {
		case GAME_READY:
			presentReady();
			break;
		case GAME_RUNNING:
			presentRunning();
			break;
		case GAME_PAUSED:
			presentPaused();
			break;
		case GAME_LEVEL_END:
			presentLevelEnd();
			break;
		case GAME_OVER:
			presentGameOver();
			break;
		}
		renderButtons();
		batcher.endBatch();
		gl.glDisable(GL10.GL_BLEND);
	}

	private void presentReady() {
	}

	private void presentRunning() {

	}

	private void presentPaused() {

	}

	private void presentLevelEnd() {
		String topText = "the princess is ...";
		String bottomText = "in another castle!";
		float topWidth = Assets.font.glyphWidth * topText.length();
		float bottomWidth = Assets.font.glyphWidth * bottomText.length();
		Assets.font.drawText(batcher, topText, 160 - topWidth / 2, 480 - 40);
		Assets.font.drawText(batcher, bottomText, 160 - bottomWidth / 2, 40);
	}

	private void presentGameOver() {

	}

	@Override
	public void pause() {
		if (state == GAME_RUNNING)
			state = GAME_PAUSED;
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
	}
}
