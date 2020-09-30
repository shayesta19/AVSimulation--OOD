package edu.neu.csye6200.av;

public class ChangeLanesToPassLeftAVRule extends AbstractAVRule {

    protected void move(Vehicle vehicle, RoadPosition roadPosition) {
        Road road = Road.getInstance();
        RoadPosition nextPosition = nextPosition(vehicle, roadPosition);
        road.move(vehicle, nextPosition);
    }

    @Override
    protected RoadPosition nextPosition(Vehicle vehicle, RoadPosition targetRoadPosition) {
        RoadPosition nextPosition = super.nextPosition(vehicle, targetRoadPosition);
        Road road = Road.getInstance();
        if (nextPosition != null) {
            Vehicle sameLaneVehicle = road.vehicleAt(nextPosition.getLane(), nextPosition.getSpot() + 1);
            if (sameLaneVehicle != null) {
                RoadPosition leftLanePosition = leftLanePosition(vehicle, sameLaneVehicle, targetRoadPosition);
                nextPosition = leftLanePosition == null ? nextPosition : leftLanePosition;
            }
        }
        return nextPosition;
    }

    protected RoadPosition leftLanePosition(Vehicle vehicle, Vehicle vehicleInFrontCurrentLane, RoadPosition targetRoadPosition) {
        RoadPosition nextPosition = null;
        Road road = Road.getInstance();
        if (targetRoadPosition.getLane() - 1 >= 0) {// if there is a left lane
            RoadPosition nextLaneTargetPosition = new RoadPosition(targetRoadPosition.getLane() - 1, targetRoadPosition.getSpot());
            nextPosition = super.nextPosition(vehicle, nextLaneTargetPosition);
            if (nextPosition != null) {//can move to left lane
                if (nextPosition.isBehind(vehicle.currentLocation())) {
                    nextPosition = new RoadPosition(vehicleInFrontCurrentLane.currentLocation().getLane(), vehicleInFrontCurrentLane.currentLocation().getSpot() - 1);
                    vehicle.setSpeed(vehicle.getSpeed() - (targetRoadPosition.getSpot() - nextPosition.getSpot()));
                } else {//move to next lane
                    Vehicle nextLaneVehicle = road.vehicleAt(nextPosition.getLane(), nextPosition.getSpot() + 1);
                    if (nextLaneVehicle != null) {
                        if ((nextLaneVehicle.getSpeed() < vehicleInFrontCurrentLane.getSpeed())) {
                            //slow down in current lane if next lane vehicle is going slower
                            RoadPosition currentLocation = vehicleInFrontCurrentLane.currentLocation();
                            nextPosition = new RoadPosition(currentLocation.getLane(), (int) Math.floor(vehicleInFrontCurrentLane.currentLocation().getSpot() - 2));
                            vehicle.setSpeed(vehicle.getSpeed() / 2);
                        } else {
                            //speed up in left lane
                            vehicle.setSpeed(road.laneMaxSpeed(nextPosition.getLane()));// speed up
                        }
                    }
                }
            }
        }
        return nextPosition;
    }
}
