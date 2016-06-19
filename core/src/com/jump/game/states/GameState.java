package com.jump.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jump.game.ActionController;
import com.jump.game.Jumper;
import com.jump.game.sprites.Player;
import com.jump.game.sprites.World;

import java.util.Random;

public class GameState extends State {

    private boolean debug = true;

    private World world;
    private Player player;
    private int worldIndex;
    private int playerIndex;

    public GameState(StateManager sm, int worldIndex, int playerIndex) {
        super(sm);

        this.worldIndex = worldIndex;
        this.playerIndex = playerIndex;

        camera.setToOrtho(false, Jumper.WIDTH, Jumper.HEIGHT);

        player = new Player(worldIndex, playerIndex, 50, Jumper.HEIGHT / 3 + 50);
        world = new World("world" + worldIndex, debug, playerIndex);

        /** for swipes */
        Gdx.input.setInputProcessor(new ActionController(player));
    }

    private void pause() {sManager.init(new PauseState(sManager, this));}

    @Override
    protected void handleInput() {
        if(Gdx.input.isKeyPressed(Input.Keys.R))
            sManager.init(new ChoosePlayerState(sManager));
//        if(Gdx.input.justTouched() || Gdx.input.isKeyPressed(Input.Keys.ANY_KEY))
//            player.jump();
    }

    @Override
    public void update(float delta) {
        handleInput();

        if(world.update(delta, player, camera) || player.getPosition().y >= Jumper.HEIGHT) {
            Jumper.gameVars.setRecord(world.getScore());
            Jumper.gameVars.setCoins(world.getCoins());
            sManager.init(new DeathState(sManager));
        }

        camera.update();
    }

    @Override
    public void render(SpriteBatch sb, BitmapFont font) {
        sb.setProjectionMatrix(camera.combined);

        sb.begin();

        world.render(sb, font, camera, player);
        sb.draw(player.getCurrentTexture(), player.getPosition().x, player.getPosition().y,
                player.getCurrentTexture().getRegionWidth() / 2, player.getCurrentTexture().getRegionHeight() / 2);

        sb.end();
    }

    @Override
    public void dispose() {
        player.getTexture().dispose();
        world.dispose();
    }
}
