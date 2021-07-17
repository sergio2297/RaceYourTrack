package model;

public class Game {

    //---- Constants and Definitions ----
    private static Game game = null;

    //---- Attributes ----
    private String racerName;
    private int coins;
    private boolean isCarConnected;
    private Car selectedCar;

    //---- Construction ----
    private Game() {
        selectedCar = Car.MUSCLE_CAR;
    }

    public static Game getInstance() {
        if(game == null) {
            game = new Game();
        }
        return game;
    }

    //---- Methods ----
    public Car getSelectedCar() {
        return selectedCar;
    }
}
