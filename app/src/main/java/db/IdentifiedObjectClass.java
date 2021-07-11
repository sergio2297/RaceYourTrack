package db;

import java.io.Serializable;

public abstract class IdentifiedObjectClass implements Serializable {

    //---- Attributes ----
    private int id;

    //---- Constructor ----
    public IdentifiedObjectClass(final int id) {
        this.id = id;
    }

    //---- Methods ----
    /**
     * @return the object's identifier
     */
    public int getID() {
        return id;
    }

    /**
     * @param id new object's identifier
     */
    public void setID(final int id) {
        this.id = id;
    }
}
