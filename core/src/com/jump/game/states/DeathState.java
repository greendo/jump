package com.jump.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jump.game.Jumper;

/**
 * Created by jc on 28.05.16.
 */
public class DeathState extends State {

    private String tmp;

    public DeathState(StateManager sm) {
        super(sm);
        tmp = "Tap to return to menu";
        camera.setToOrtho(false, Jumper.WIDTH, Jumper.HEIGHT);
    }

    @Override
    protected void handleInput() {
        if(Gdx.input.justTouched() || Gdx.input.isKeyPressed(Input.Keys.ANY_KEY))
            sManager.init(new MenuState(sManager));
    }

    @Override
    public void update(float delta) {
        camera.update();
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb, BitmapFont font) {
        sb.setProjectionMatrix(camera.combined);
        sb.begin();
        font.draw(sb, tmp, Jumper.WIDTH / 2 - 15, Jumper.HEIGHT / 2);
        sb.end();

//        Texture texture = new Texture(Gdx.files.internal("image.png"));
//        TextureRegion region = new TextureRegion(texture, 20, 20, 50, 50);
//        Sprite sprite = new Sprite(texture, 20, 20, 50, 50);
//        sprite.setPosition(100, 10);
//        sprite.setColor(0, 0, 1, 1);
//
//        SpriteBatch batch = sb;
//
//        batch.begin();
//        batch.setColor(0, 1, 0, 1);
//        batch.draw(region, 50, 10);
//        batch.setColor(255, 0, 0, 100);
//        batch.draw(texture, 10, 10);
//        sprite.draw(batch);
//        batch.end();
    }

    @Override
    public void dispose() {}

    @Override
    public void continueGame() {}
}
