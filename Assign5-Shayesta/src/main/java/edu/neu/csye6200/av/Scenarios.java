package edu.neu.csye6200.av;

import edu.neu.csye6200.ui.UIConstants;

import java.util.ArrayList;
import java.util.List;

import static edu.neu.csye6200.ui.UIConstants.SCENARIO_LANE_CHANGE_LEFT_TWO_STOPPED_VEHICLES;

public class Scenarios {


    /**
     * sets the state of the simulation with a static number of vehicles.
     * The number of vehicles depends on the size of the array list.
     * Initializes the avrule the vehicle will follow, the id speed and the road position
     *
     * @param avRule the avrule the vehicle will follow
     * @return the list of vehicles in this scenario
     */
    public static List<Vehicle> laneChangeLeftLane(AVRule avRule) {
        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(new Vehicle(avRule, 1, 2, 20, 1));
        vehicles.add(new Vehicle(avRule, 2, 1, 2, 3));
        vehicles.add(new Vehicle(avRule, 5, 1, 5, 3));
        vehicles.add(new Vehicle(avRule, 6, 1, 4, 3));
        vehicles.add(new Vehicle(avRule, 4, 1, 16, 0));
        vehicles.add(new Vehicle(avRule, 3, 0, 19, 2));
        return vehicles;
    }

    /**
     * sets the state of the simulation with a static number of vehicles and adds vehicles in the adjacent left lane.
     * The number of vehicles depends on the size of the array list.
     * Initializes the avrule the vehicle will follow, the id speed and the road position
     *
     * @param avRule the avrule the vehicle will follow
     * @return the list of vehicles in this scenario
     */
    public static List<Vehicle> laneChangeLeftLaneAdjVehicleLeftLane(AVRule avRule) {
        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(new Vehicle(avRule, 1, 2, 20, 1));
        vehicles.add(new Vehicle(avRule, 2, 1, 2, 3));
        vehicles.add(new Vehicle(avRule, 5, 1, 5, 3));
        vehicles.add(new Vehicle(avRule, 4, 1, 16, 0));
        vehicles.add(new Vehicle(avRule, 6, 0, 2, 3));
        vehicles.add(new Vehicle(avRule, 7, 0, 3, 3));
        vehicles.add(new Vehicle(avRule, 8, 0, 4, 3));
        vehicles.add(new Vehicle(avRule, 9, 0, 5, 3));
        vehicles.add(new Vehicle(avRule, 3, 0, 19, 2));
        return vehicles;

    }

    /**
     * sets the state of the simulation with a static number of vehicles and two stopped vehicles at the specified road position.
     * The number of vehicles depends on the size of the array list.
     * Initializes the avrule the vehicle will follow, the id speed and the road position
     *
     * @param avRule the avrule the vehicle will follow
     * @return the list of vehicles in this scenario
     */
    public static List<Vehicle> laneChangeLeftLaneTwoStoppedVehicles(AVRule avRule) {
        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(new Vehicle(avRule, 3, 0, 28, 0));
        vehicles.add(new Vehicle(avRule, 4, 1, 24, 0));
        vehicles.add(new Vehicle(avRule, 2, 1, 2, 3));
        vehicles.add(new Vehicle(avRule, 5, 1, 5, 3));
        vehicles.add(new Vehicle(avRule, 6, 1, 4, 3));
        vehicles.add(new Vehicle(avRule, 1, 2, 20, 1));
        return vehicles;

    }

    /**
     * sets the state of the simulation with a static number of vehicles and a vehicle stopped at the specified road position.
     * The number of vehicles depends on the size of the array list.
     * Initializes the avrule the vehicle will follow, the id speed and the road position
     *
     * @param avRule the avrule the vehicle will follow
     * @return the list of vehicles in this scenario
     */
    public static List<Vehicle> laneChangeLeftLaneStoppedVehiclePassingLane(AVRule avRule) {
        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(new Vehicle(avRule, 1, 2, 20, 1));
        vehicles.add(new Vehicle(avRule, 2, 1, 2, 3));
        vehicles.add(new Vehicle(avRule, 5, 1, 5, 3));
        vehicles.add(new Vehicle(avRule, 6, 1, 4, 3));
        vehicles.add(new Vehicle(avRule, 4, 1, 16, 1));
        vehicles.add(new Vehicle(avRule, 3, 0, 21, 0));
        return vehicles;
    }

    /**
     * initializes the state of the simulation based on the scenario.
     *
     * @param avRule   the rule that the vehicles will follow
     * @param scenario the set up of the road
     * @return the list of vehicles in this scenario
     */
    public static List<Vehicle> pickScenario(String scenario, AVRule avRule) {
        switch (scenario) {
            case UIConstants.SCENARIO_LANE_CHANGE_LEFT_LANE_ADJ_VEHICLE:
                return laneChangeLeftLaneAdjVehicleLeftLane(avRule);
            case SCENARIO_LANE_CHANGE_LEFT_TWO_STOPPED_VEHICLES:
                return laneChangeLeftLaneTwoStoppedVehicles(avRule);
            case UIConstants.SCENARIO_LANE_CHANGE_LEFT_LANE:
                return laneChangeLeftLane(avRule);
            case UIConstants.SCENARIO_LANE_CHANGE_LEFT_LANE_STOPPED_VEHICLE:
                return laneChangeLeftLaneStoppedVehiclePassingLane(avRule);
            default:
                return null;
        }
    }
}
