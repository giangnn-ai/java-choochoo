package bk.zodi.android.games.choochoo;

import com.badlogic.androidgames.framework.Music;
import com.badlogic.androidgames.framework.Sound;
import com.badlogic.androidgames.framework.gl.Font;
import com.badlogic.androidgames.framework.gl.Texture;
import com.badlogic.androidgames.framework.gl.TextureRegion;
import com.badlogic.androidgames.framework.impl.GLGame;

public class Assets {
	public static Texture background;
	public static TextureRegion backgroundRegion;

	public static Texture items;
	public static TextureRegion level;
	public static TextureRegion lock;
	public static Font font;
	
	public static TextureRegion[] buttons;
	
	public static TextureRegion autoRotate;
	public static TextureRegion[] rails;
	public static TextureRegion[] stations;
	public static TextureRegion[] heads;
	public static TextureRegion[] carriages;
	
	public static Music music;
	public static Sound choo;

	public static void load(GLGame game) {
		background = new Texture(game, "background.png");
		backgroundRegion = new TextureRegion(background, 0, 0, 800, 480);

		items = new Texture(game, "items.png");
		level = new TextureRegion(items, 0, 0, 80, 80);
		lock = new TextureRegion(items, 320, 0, 40, 40);
		
		buttons = new TextureRegion[4];
		for (int i = 0; i < 4; ++i) {
			buttons[i] = new TextureRegion(items, 360, i * 40, 120, 40);
		}
		
		autoRotate = new TextureRegion(items, 320, 40, 40, 40);
		
		rails = new TextureRegion[7];
		for (int i = 0; i < 7; ++i) {
			rails[i] = new TextureRegion(items, i * 40, 80, 40, 40);
		}
		
		stations = new TextureRegion[3];
		for (int i = 0; i < 3; ++i) {
			stations[i] = new TextureRegion(items, 80 + i * 40, 40, 40, 40);
		}
		
		heads = new TextureRegion[3];
		
		carriages = new TextureRegion[3];
		for (int i = 0; i < 3; ++i) {
			heads[i] = new TextureRegion(items,200 + i * 40, 40, 40, 20);
			carriages[i] = new TextureRegion(items, 200 + i * 40, 60, 40, 20);
		}
		
		font = new Font(items, 80, 0, 10, 24, 40);
		
		music = game.getAudio().newMusic("music.mp3");
		music.setLooping(true);
		music.setVolume(0.5f);
		choo = game.getAudio().newSound("choo.wav");
	}

	public static void reload() {
		background.reload();
		items.reload();
		if (Settings.soundEnabled)
			music.play();
	}

	public static void playSound(Sound sound) {
		if (Settings.soundEnabled)
			sound.play(1);
	}
}
