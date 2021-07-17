package db.dao;

import java.util.List;

import model.Car;

// TODO:
public class DaoCars extends AbstractDao<Car> {

    //---- Constructor ----
    protected DaoCars() {super();}

    //---- Methods ----
    @Override
    public Car findById(int id) {
        return null;
    }

    @Override
    public List<Car> findAll() {
        return null;
    }

    @Override
    protected int doInsert(Car elem) {
        return 0;
    }

    @Override
    protected long doUpdate(Car elem) {
        return 0;
    }

    @Override
    protected long doRemove(Car elem) {
        return 0;
    }

}
