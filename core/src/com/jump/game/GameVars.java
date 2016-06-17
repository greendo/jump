package com.jump.game;

import com.badlogic.gdx.Gdx;

/**
 * Created by jc on 17.06.16.
 */
public class GameVars {

    private static GameVars instance;

    private GameVars() {}

    public static GameVars getGameVars() {
        if(instance == null) {
            instance = new GameVars();
        }
        return instance;
    }

    public int getRecord() {
        try {
            return Gdx.app.getPreferences("Jumper").getInteger("record");
        }
        catch(NullPointerException e) {
            return 0;
        }
    }

    public void setRecord(int record) {
        try {
            if(record > Gdx.app.getPreferences("Jumper").getInteger("record")) {
                Gdx.app.getPreferences("Jumper").putInteger("record", record);
                Gdx.app.error("record", "try score > record");
            }
            else
                Gdx.app.error("record", "try score <= record");
        }
        catch(NullPointerException e) {
            Gdx.app.getPreferences("Jumper").putInteger("record", record);
            Gdx.app.error("record", "catch nptr");
        }
        finally {
            Gdx.app.getPreferences("Jumper").flush();
            Gdx.app.error("record", "finally flushed");
        }
    }
}
