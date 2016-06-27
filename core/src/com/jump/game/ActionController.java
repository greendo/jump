package com.jump.game;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.jump.game.sprites.Player;
import com.jump.game.states.GameState;
import com.jump.game.states.PauseState;

/**
 * Created by jc on 30.05.16.
 */
public class ActionController implements InputProcessor {

    private Vector2 positionStart, positionFin;
    private Player player;
    public static boolean touched = false;
    private GameState gs;

    public ActionController(Player player, GameState gs) {
        this.player = player;
        this.gs = gs;
        positionStart = new Vector2();
        positionFin = new Vector2();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        positionStart.x = screenX;
        positionStart.y = -1 * screenY;
        player.setCurrentTexture("down");
        touched = true;
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        positionFin.set(screenX, -1 * screenY);

        positionFin.sub(positionStart);
        if(positionFin.x < 0)
            positionFin.x = 0;

        player.jump(positionFin);
        touched = false;
        return true;
    }

    @Override
    public boolean keyDown(int keycode) {
//        if(keycode == Input.Keys.BACK || keycode == Input.Keys.HOME) {
//            gs.getsManager().init(new PauseState(gs.getsManager(), gs));
//        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if(keycode == Input.Keys.BACK || keycode == Input.Keys.HOME) {
            gs.getsManager().init(new PauseState(gs.getsManager(), gs));
        }
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }


    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
