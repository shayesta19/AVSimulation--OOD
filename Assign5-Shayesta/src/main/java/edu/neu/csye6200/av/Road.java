package edu.neu.csye6200.av;


import edu.neu.csye6200.ui.UIConstants;

import static edu.neu.csye6200.ui.UIConstants.CAR_WIDTH;

//REF: https://www.journaldev.com/171/thread-safety-in-java-singleton-classes-with-example-code for singleton implemenation
public class Road {

    private int roadLength;
    private int maxSpeed = 3;
    private int laneCount = 3;
    private Vehicle[][] lanes;
    private static volatile Road _instance;
    private static final Object mutex = new Object();

    private Road() {
    }

    /**
     * singleton that creates an instance of road
     *
     * @return road instance
     */
    public static Road getInstance() {
        Road result = _instance;
        //todo
        if (result == null) {
            synchronized (mutex) {
                result = _instance;
                if (result == null)
                    _instance = result = new Road();
            }
        }
        return result;
    }

    /**
     * @param roadLength specifies the length of the road
     */
    public synchronized void init(int roadLength) {
        Vehicle[][] newLanes = new Vehicle[laneCount][roadLength / CAR_WIDTH];
        if (lanes != null) {
            if (roadLength / CAR_WIDTH < this.roadLength)
                return; // dont reduce the road once increased
            for (int i = 0; i < lanes.length; i++) {
                if (this.roadLength >= 0) System.arraycopy(lanes[i], 0, newLanes[i], 0, this.roadLength);
            }
        } else {
            initialize(newLanes);
        }
        this.roadLength = roadLength / CAR_WIDTH;
        lanes = newLanes;
    }

    /**
     * sets the values in the lane matrix to null
     */
    public synchronized void reset() {
        initialize(lanes);
    }

    public int getRoadLength() {
        return roadLength;
    }

    public int getLaneCount() {
        return laneCount;
    }


    /**
     * sets the initial values of the lanes matrix.
     *
     * @param lanes a 2d matrix of vehicles
     */
    public void initialize(Vehicle[][] lanes) {
        for (int i = 0; i < laneCount; i++) {
            for (int j = 0; j < roadLength; j++) {
                lanes[i][j] = null;
            }
        }
    }

    /**
     * prints the vehicles present at their location in the lanes matrix
     */
    public void printRoad() {
        System.out.println();
        for (int i = 0; i < laneCount; i++) {
            for (int j = 0; j < roadLength; j++) {
                Vehicle vehicle = lanes[i][j];
                System.out.print(String.valueOf(vehicle != null ? vehicle.getId() : 0) + ' ');
            }
            System.out.println();
        }
    }

    /**
     * move the given vehicle to the specified lane and spot in the road.
     *
     * @param vehicle  the vehicle to move
     * @param lane     the lane to move vehicle to
     * @param nextCell the spot in the lane to move the vehicle to
     * @return if the vehicle was successfully moved to the specified road position
     */
    private synchronized boolean move(Vehicle vehicle, int lane, int nextCell) {
        lanes[lane][nextCell] = vehicle;
        if (vehicle != null) {
            vehicle.setCell(nextCell);
            vehicle.setLane(lane);//todo restrict to adjacent lane or current lane
        }
        return true;
    }

    /**
     * checks if a cell is within the bounds of road length
     */
    public boolean isBeyondRoad(int cell) {
        return cell > roadLength - 1;
    }

    /**
     * gets the vehicle at the specified lane and spot on the road
     *
     * @param spot the postion of vehicle on the lane
     * @param lane the lane the vehicle is on
     * @return the vehicle present on the specified lane and spot
     */
    public Vehicle vehicleAt(int lane, int spot) {
        if (spot > lastSpotOnRoad()) {
            return lanes[lane][lastSpotOnRoad()];
        } else {
            return lanes[lane][spot];
        }
    }

    /**
     * gets the size of the road
     *
     * @return the size of the road
     */
    public int lastSpotOnRoad() {
        return roadLength - 1;
    }

    /**
     * places the vehicle on the road
     *
     * @param vehicle      the vehicle to be placed
     * @param nextPosition the lane and spot to place the vehicle on
     * @return if the vehicle was successfully placed on the specified position on the road
     */
    public boolean move(Vehicle vehicle, RoadPosition nextPosition) {
        if (nextPosition == null) {
            RoadPosition currentLocation = vehicle.currentLocation();
            move(null, currentLocation);
            return true;
        } else if (vehicle != null) {
            RoadPosition currentLocation = vehicle.currentLocation();
            boolean placed = move(vehicle, nextPosition.getLane(), nextPosition.getSpot());
            if (placed && !currentLocation.equals(nextPosition))
                move(null, currentLocation);
            return placed;
        } else {
            return move(vehicle, nextPosition.getLane(), nextPosition.getSpot());
        }
    }

    /**
     * get the vehicle at the specified road position
     *
     * @param nextPosition the road positon of the vehicle
     * @return the vehicle at that road position
     */
    public Vehicle vehicleAt(RoadPosition nextPosition) {
        return vehicleAt(nextPosition.getLane(), nextPosition.getSpot());
    }

    /**
     * get the maximum speed a vehicle can have on a specified lane
     *
     * @param lane the lane that determines the maxspeed a vehicle can have
     * @return the maxspeed based on lane
     */
    public int laneMaxSpeed(int lane) {
        return (int) Math.floor(maxSpeed * (1 - (lane / 10d)));
    }
}

