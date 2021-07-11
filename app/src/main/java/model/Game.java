package model;

import model.settings.Settings;

public class Game {

    //---- Constants and Definitions ----
    private static Game game = null;

    //---- Attributes ----
    private String racerName;
    private int coins;
    private boolean isCarConnected;

    //---- Construction ----
    private Game() {

    }

    public static Game getInstance() {
        if(game == null) {
            game = new Game();
        }
        return game;
    }

    //---- Methods ----

}
