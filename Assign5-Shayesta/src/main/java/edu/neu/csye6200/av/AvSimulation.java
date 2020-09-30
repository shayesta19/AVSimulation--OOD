package edu.neu.csye6200.av;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Observable;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import static edu.neu.csye6200.av.Scenarios.*;

public class AvSimulation extends Observable implements Runnable {
    private final static Logger logger= Logger.getLogger(AvSimulation.class.getName());
    private static FileHandler fh;
    public static void main(String[] args) throws InterruptedException {
        SimpleDateFormat format = new SimpleDateFormat("M-d_HHmmss");
        try {
            fh = new FileHandler("fileLogging" + format.format(Calendar.getInstance().getTime()) + ".log", true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        fh.setLevel(Level.ALL);
        logger.addHandler(fh);
        logger.info("starting avsimulation");
        Road road = Road.getInstance();
        road.init(3000);
        AVRule avRule = new ChangeLanesToPassLeftAndRightAVRule();
        List<Vehicle> vehicles = laneChangeLeftLaneAdjVehicleLeftLane(avRule);
        for (Vehicle vehicle : vehicles) {
            road.move(vehicle,vehicle.currentLocation());
        }
        // laneChangeLeftLaneTwoStoppedVehicles(road,avRule);
     //laneChangeLeftLaneStoppedVehiclePassingLane(road,avRule);
        while (true) {
            road.printRoad();
            for (int i = 0; i < road.getLaneCount(); i++) {
                for (int j = road.lastSpotOnRoad(); j >= 0; j--) {
                    Vehicle vehicle = road.vehicleAt(i, j);
                    if (vehicle != null) vehicle.drive();
                }
            }
            Thread.sleep(1000);
        }

    }



    @Override
    public void run() {
        // TODO Auto-generated method stub
    }

}
