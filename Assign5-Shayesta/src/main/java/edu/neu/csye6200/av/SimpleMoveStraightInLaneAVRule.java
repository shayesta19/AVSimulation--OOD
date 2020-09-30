package edu.neu.csye6200.av;

public class SimpleMoveStraightInLaneAVRule extends AbstractAVRule {

    /**
     * moves a vehicle on the road and the vehicle stops if the vehicle in front is stopped
     *
     * @param vehicle      the vehicle to be moved
     * @param roadPosition the current road position of the vehicle
     */
    protected void move(Vehicle vehicle, RoadPosition roadPosition) {
        Road road = Road.getInstance();
        RoadPosition nextPosition = nextPosition(vehicle, roadPosition);
        stopIfVehicleInFrontIsStopped(vehicle, nextPosition);
        road.move(vehicle, nextPosition);
    }
}
