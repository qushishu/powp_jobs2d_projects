package edu.kis.powp.jobs2d.drivers.composite;

import edu.kis.powp.jobs2d.Job2dDriver;

import java.util.LinkedList;
import java.util.List;

public class DriverComposite implements IDriverComposite {

    private final List<Job2dDriver> job2dDriverList = new LinkedList<>();

    @Override
    public void setPosition(int x, int y) {
        job2dDriverList.forEach(job2dDriver -> job2dDriver.setPosition(x, y));
    }

    @Override
    public void operateTo(int x, int y) {
        job2dDriverList.forEach(job2dDriver -> job2dDriver.operateTo(x, y));
    }

    @Override
    public void add(Job2dDriver driver) {
        job2dDriverList.add(driver);
    }

    @Override
    public void remove(Job2dDriver driver) {
        job2dDriverList.remove(driver);
    }
}
