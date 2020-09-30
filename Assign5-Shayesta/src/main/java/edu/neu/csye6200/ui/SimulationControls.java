package edu.neu.csye6200.ui;

import java.util.Objects;

import static edu.neu.csye6200.ui.UIConstants.SCENARIO_RANDOM;

public class SimulationControls {

    private int numberOfCars = 3;
    private boolean stopCar;
    private String scenario = SCENARIO_RANDOM;
    private long delay = 1000L;

    public boolean isChanged() {
        return isChanged;
    }

    public void setChanged(boolean changed) {
        isChanged = changed;
    }

    private boolean isChanged;

    public void setNumberOfCars(int numberOfCars) {
        this.numberOfCars = numberOfCars;
    }

    public void setStopCar(boolean stopCar) {
        this.stopCar = stopCar;
    }

    public void setScenario(String scenario) {
        this.scenario = scenario;
    }

    public int getNumberOfCars() {
        return numberOfCars;
    }

    public boolean isStopCar() {
        return stopCar;
    }

    public String getScenario() {
        return scenario;
    }

    public long getDelay() {
        return delay;
    }
}
