package com.jump.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

/**
 * Created by jc on 17.06.16.
 */
public class GameVars {

    private static GameVars instance;

    private GameVars() {
    }

    public static GameVars getGameVars() {
        if (instance == null) {
            instance = new GameVars();
        }
        return instance;
    }

    public int getRecord() {
        try {
            return Gdx.app.getPreferences("Jumper").getInteger("record");
        } catch (NullPointerException e) {
            return 0;
        }
    }

    public void setRecord(int record) {

        Preferences prefs = Gdx.app.getPreferences("Jumper");

        try {
            if (record > prefs.getInteger("record")) {
                prefs.putInteger("record", record);
                Gdx.app.error("record", "try score > record");
            } else
                Gdx.app.error("record", "try score <= record");
        } catch (NullPointerException e) {
            prefs.putInteger("record", record);
            Gdx.app.error("record", "catch nptr");
        } finally {
            prefs.flush();
            Gdx.app.error("record", "finally flushed");
        }
    }

    public int getCoins() {
        try {
            return Gdx.app.getPreferences("Jumper").getInteger("coins");
        } catch (NullPointerException e) {
            return 0;
        }
    }

    public void setCoins(int coins) {

        Preferences prefs = Gdx.app.getPreferences("Jumper");

        prefs.putInteger("coins", coins);
        prefs.flush();
        Gdx.app.error("coins", "flushed");
    }
}
