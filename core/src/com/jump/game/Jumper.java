package com.jump.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jump.game.states.StateManager;

public class Jumper extends ApplicationAdapter {

	/** we are drawing on it */
	private SpriteBatch batch;
	/** words to show on batch */
	private BitmapFont font;
	/** control screens */
	private StateManager sm;

	/** screen */
	public static int WIDTH, HEIGHT;

	@Override
	public void create() {
		batch = new SpriteBatch();
		font = new BitmapFont();
		WIDTH = Gdx.graphics.getWidth();
		HEIGHT = Gdx.graphics.getHeight();

		Gdx.gl.glClearColor(1, 0, 0, 1);

		sm = new StateManager();
	}

	@Override
	public void render() {
		//Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		sm.update(Gdx.graphics.getDeltaTime());
		sm.render(batch, font);
	}

	@Override
	public void dispose() {
		super.dispose();
	}
}
