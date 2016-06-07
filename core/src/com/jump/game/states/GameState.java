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

    private World world;
    private Player player;
    private boolean debug = true;
    private Random rand = new Random();

    public GameState(StateManager sm) {
        super(sm);
        camera.setToOrtho(false, Jumper.WIDTH, Jumper.HEIGHT);

        player = new Player(50, Jumper.HEIGHT / 3 + 20);
        world = new World("test", debug);

        /** for swipes */
        Gdx.input.setInputProcessor(new ActionController(player));
    }

    private void pause() {sManager.init(new PauseState(sManager, this));}

    @Override
    protected void handleInput() {
        if(Gdx.input.isKeyPressed(Input.Keys.R))
            sManager.init(new GameState(sManager));
//        if(Gdx.input.justTouched() || Gdx.input.isKeyPressed(Input.Keys.ANY_KEY))
//            player.jump();
    }

    @Override
    public void update(float delta) {
        handleInput();

        if(world.update(delta, player, camera) || player.getPosition().y >= Jumper.HEIGHT)
            sManager.init(new DeathState(sManager));

        camera.update();
    }

    @Override
    public void render(SpriteBatch sb, BitmapFont font) {
        sb.setProjectionMatrix(camera.combined);

        sb.begin();

        world.render(sb, font, camera, player);
        sb.draw(player.getTexture(), player.getPosition().x, player.getPosition().y);

        sb.end();
    }

    @Override
    public void dispose() {
        player.getTexture().dispose();
        world.dispose();
    }
}
