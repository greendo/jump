package com.jump.game;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.jump.game.sprites.Player;

/**
 * Created by jc on 30.05.16.
 */
public class ActionController implements InputProcessor {

    private Vector2 positionStart, positionFin;
    private Player player;

    public ActionController(Player player) {
        this.player = player;
        positionStart = new Vector2();
        positionFin = new Vector2();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        positionStart.x = screenX;
        positionStart.y = -1 * screenY;
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        positionFin.set(screenX, -1 * screenY);

        positionFin.sub(positionStart);
        if(positionFin.x < 0)
            positionFin.x = 0;

        player.jump(positionFin);
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
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
