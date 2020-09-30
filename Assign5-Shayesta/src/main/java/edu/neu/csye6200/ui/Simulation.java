package edu.neu.csye6200.ui;

import edu.neu.csye6200.av.*;

import java.util.*;
import java.util.concurrent.TimeoutException;

public class Simulation extends Observable implements Runnable {

    private Thread thread = null; // the thread that runs my simulation
    private boolean paused = false;
    private boolean done = false; // set true to end the simulation loop
    private int ctr = 0;
    private boolean running = false; // set true if the simulation is running
    private final Object lock = new Object();
    private SimulationControls simulationControls;

    public List<Vehicle> vehicles;
    private Map<Vehicle, RoadPosition> initialPositions;
    private AVRule avRule;
    private Random random = new Random();

    public SimulationControls getSimulationControls() {
        return simulationControls;
    }

    public void setSimulationControls(SimulationControls simulationControls) {
        this.simulationControls = simulationControls;
    }



    public Simulation(SimulationControls simulationControls) {
        avRule = new SimpleMoveStraightInLaneAVRule();
        this.setSimulationControls(simulationControls);
        initVehicles();
        setChanged();
        notifyObservers(this);
    }

    private void initVehicles() {
        Road road = Road.getInstance();
        road.reset();
        vehicles = Scenarios.pickScenario(simulationControls.getScenario(), avRule);
        if (vehicles == null) {
            initialPositions = new HashMap<>();
            vehicles = new ArrayList<>();
            int idCounter = 1;
            for (int i = 0; i < simulationControls.getNumberOfCars(); i++) {
                int lane = (int) (Math.random() * 3);
                int cell = (int) (Math.random() * road.getRoadLength() / 3);
                Vehicle vehicle = new Vehicle(avRule, ++idCounter, lane, cell, random.nextInt(4 - 1) + 1);
                vehicles.add(vehicle);
                initialPositions.put(vehicle, vehicle.currentLocation());
            }
        }
        for (Vehicle vehicle : vehicles) {
            road.move(vehicle, vehicle.currentLocation());
        }

    }

    public void reInit() {
        if (simulationControls.isChanged()) {
            simulationControls.setChanged(false);
            initVehicles();
            setChanged();
            notifyObservers(this);
        }
    }


    public void reset() {
        Road road = Road.getInstance();
        road.reset();
        if (simulationControls.getScenario().equals(UIConstants.SCENARIO_RANDOM))
            for (Vehicle vehicle : initialPositions.keySet()) {
                RoadPosition roadPosition = initialPositions.get(vehicle);
                road.move(vehicle, roadPosition);
            }
        else {
            initVehicles();
        }
        setChanged();
        notifyObservers(this);
    }

    public void startSim() {
        System.out.println("Starting the simulation");
        if (thread != null) return; // A thread is already running
        reInit();
        thread = new Thread(this); // Create a worker thread
        paused = false;
        done = false; // reset the done flag.
        ctr = 0; // reset the loop counter
        thread.start();
    }

    public void pauseSim() {
        synchronized (lock) {
            if (paused)
                lock.notifyAll();
        }
        paused = !paused;
        System.out.println("Pause the simulation: " + paused);
    }

    public boolean isPaused() {
        return paused;
    }

    private void allowPause() {
        synchronized (lock) {
            if (isPaused()) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    // nothing
                }
            }
        }
    }

    public boolean isPausable() {
        if (!running) return false;
        if (done) return false;
        return true;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public void stopSim() {
        System.out.println("Stop the simulation");
        reset();
        if (thread == null) return; // defensive coding in case the thread is null
        done = true;
    }

    @Override
    public void run() {
        runSimLoop();
        thread = null; // flag that the simulation thread is finished
    }

    private void runSimLoop() {
        running = true;
        Road road = Road.getInstance();
        while (!done) {
            allowPause();
            road.printRoad();
            for (int i = 0; i < road.getLaneCount(); i++) {
                for (int j = road.lastSpotOnRoad(); j >= 0; j--) {
                    Vehicle vehicle = road.vehicleAt(i, j);
                    if (vehicle != null) {
                        vehicle.setRuleStrategy(avRule);
                        vehicle.drive();
                    }
                }
            }

            if (!paused)
                updateSim();
            sleep(simulationControls.getDelay()); // A half second sleep
        }


        running = false;
    }

    /*
     * Make the current thread sleep a little
     */
    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void updateSim() {
        System.out.println("Updating the simualtion " + ctr++);
        setChanged();
        notifyObservers(this); // Send a copy of the simulation
    }

    public Object getLock() {
        return lock;
    }

    public void setAvRule(AVRule avRule) {
        this.avRule = avRule;
    }
}
