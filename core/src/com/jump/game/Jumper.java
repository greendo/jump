package com.jump.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jump.game.states.PauseState;
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

	/** pause and resume */
	public static boolean PAUSE = false;
	/** exit hack */

	/** settings */
	public static GameVars gameVars = GameVars.getGameVars();

	@Override
	public void create() {
		/** for future menu controller? */
		//Gdx.input.setInputProcessor(new ActionController(player, this));
		//Gdx.input.setCatchBackKey(true);
		//Gdx.input.setCatchMenuKey(true);

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
		sm.getState().dispose();
		batch.dispose();
		font.dispose();
	}

	@Override
	public void pause() {
		super.pause();
		PAUSE = true;
		Gdx.app.error("pause", "was called from Jumper.java, PAUSE set to: " + PAUSE);
		sm.init(new PauseState(sm, sm.getState()));
	}

	@Override
	public void resume() {
		super.resume();
		PAUSE = false;
		Gdx.app.error("resume", "was called from Jumper.java, PAUSE set to: " + PAUSE);
		//sm.getState().continueGame();
	}
}
