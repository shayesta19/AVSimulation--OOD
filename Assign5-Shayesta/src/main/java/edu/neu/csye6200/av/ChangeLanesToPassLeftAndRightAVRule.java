package edu.neu.csye6200.av;

public class ChangeLanesToPassLeftAndRightAVRule extends ChangeLanesToPassLeftAVRule {

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
                RoadPosition rightLanePosition = rightLanePosition(vehicle, sameLaneVehicle, targetRoadPosition);
                nextPosition = rightLanePosition == null ? nextPosition : rightLanePosition;
            }
        }
        return nextPosition;
    }

    protected RoadPosition rightLanePosition(Vehicle vehicle, Vehicle vehicleInFrontCurrentLane, RoadPosition targetRoadPosition) {
        RoadPosition nextPosition = null;
        Road road = Road.getInstance();
        if (targetRoadPosition.getLane() + 1 < road.getLaneCount() - 1) {
            RoadPosition nextLaneTargetPosition = new RoadPosition(targetRoadPosition.getLane() + 1, targetRoadPosition.getSpot());
            nextPosition = super.nextPosition(vehicle, nextLaneTargetPosition);
            if (nextPosition != null) {//can move to right lane
                if (nextPosition.isBehind(vehicle.currentLocation())) {
                    nextPosition = new RoadPosition(vehicleInFrontCurrentLane.currentLocation().getLane(), vehicleInFrontCurrentLane.currentLocation().getSpot() - 1);
                    vehicle.setSpeed(vehicle.getSpeed() - (targetRoadPosition.getSpot() - nextPosition.getSpot()));
                } else {//move to right lane
                    Vehicle rightLaneVehicle = road.vehicleAt(nextPosition.getLane(), nextPosition.getSpot() + 1);
                    if (rightLaneVehicle != null) {
                        if ((rightLaneVehicle.getSpeed() < vehicleInFrontCurrentLane.getSpeed())) {
                            //slow down in current lane if next lane vehicle is going slower
                            RoadPosition currentLocation = vehicleInFrontCurrentLane.currentLocation();
                            nextPosition = new RoadPosition(currentLocation.getLane(), (int) Math.floor(vehicleInFrontCurrentLane.currentLocation().getSpot() - 2));
                            vehicle.setSpeed(vehicle.getSpeed() / 2);
                        } else {
                            vehicle.setSpeed(vehicle.getSpeed() - 1);// slow down in right lane
                        }
                    }
                }
            }
        }
        return nextPosition;
    }
}
