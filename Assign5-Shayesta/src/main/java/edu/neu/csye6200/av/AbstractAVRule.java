package edu.neu.csye6200.av;

public abstract class AbstractAVRule implements AVRule {

    @Override
    public void drive(Vehicle vehicle) {
        if (vehicle != null && vehicle.getSpeed() != 0) {
            move(vehicle, new RoadPosition(vehicle.getLane(), vehicle.getCell() + vehicle.getSpeed()));
        }
    }

    abstract protected void move(Vehicle vehicle, RoadPosition roadPosition);

    protected void stopIfVehicleInFrontIsStopped(Vehicle vehicle, RoadPosition nextPosition) {
        if (nextPosition != null) {
            int spot = (nextPosition.getSpot()) + 1;
            spot = Math.min(spot, Road.getInstance().lastSpotOnRoad());
            Vehicle nextVehicle = Road.getInstance().vehicleAt(new RoadPosition(nextPosition.getLane(), spot));
            if (nextVehicle != null && nextVehicle.getSpeed() == 0)
                vehicle.setSpeed(0);
        }
    }

    protected RoadPosition nextPosition(Vehicle vehicle, RoadPosition targetRoadPosition) {
        int targetCell = targetRoadPosition.getSpot();
        int targetLane = targetRoadPosition.getLane();
        Road road = Road.getInstance();
        int positionOfVehicleInFront = lookForVehicleInFront(vehicle, targetLane, vehicle.getCell(), road.lastSpotOnRoad());
        if (road.isBeyondRoad(targetCell)) {
            return positionOfVehicleInFront == -1  ? null : new RoadPosition(targetRoadPosition.getLane(),positionOfVehicleInFront - 1);
        } else {
            int nextSpot = targetCell;
            nextSpot = positionOfVehicleInFront == -1 || positionOfVehicleInFront > nextSpot ? nextSpot : positionOfVehicleInFront - 1;
            return new RoadPosition(targetRoadPosition.getLane(), nextSpot);
        }
    }

    private synchronized int lookForVehicleInFront(Vehicle vehicle, int lane, int startCell, int limit) {
        Road road = Road.getInstance();
        int start = vehicle.getLane() == lane ? startCell + 1 : startCell;
        for (int i = start; i <= limit; i++) {
            if (road.vehicleAt(lane, i) != null) {
                return i;
            }
        }
        return -1;
    }
}
