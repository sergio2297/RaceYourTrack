package model.cosmetics;

import es.sfernandez.raceyourtrack.R;

public class SteeringWheelCosmetic {

    //---- Constants and Definitions ----
    private final SteeringWheelCosmetic[] STEERING_WHEEL_COSMETICS = {
            new SteeringWheelCosmetic(1, R.drawable.png_steering_wheel, 0)
    };

    //---- Attributes ----
    private final int id;
    private final int drawableId;
    private final int prize;
    private boolean unlocked;

    //---- Constructor ----
    public SteeringWheelCosmetic(final int id, final int drawableId, final int prize) {
        this.id = id;
        this.drawableId = drawableId;
        this.prize = prize;
        unlocked = false;
    }

    //---- Methods ----
    public int getId() {
        return id;
    }

    public int getDrawableId() {
        return drawableId;
    }

    public int getPrize() {
        return prize;
    }

    public boolean isUnlocked() {
        return unlocked;
    }

    public void setUnlocked(boolean unlocked) {
        this.unlocked = unlocked;
    }
}
