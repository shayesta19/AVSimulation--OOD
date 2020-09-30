package edu.neu.csye6200.av;

import java.util.Objects;

public class RoadPosition {
    private int lane;
    private int spot;

    public RoadPosition(int lane, int spot) {
        this.lane = lane;
        this.spot = spot;
    }

    public int getLane() {
        return lane;
    }

    public RoadPosition setLane(int lane) {
        this.lane = lane;
        return this;
    }

    public int getSpot() {
        return spot;
    }

    public RoadPosition setSpot(int spot) {
        this.spot = spot;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoadPosition that = (RoadPosition) o;
        return lane == that.lane &&
                spot == that.spot;
    }

    @Override
    public int hashCode() {
        return Objects.hash(lane, spot);
    }

    @Override
    public String toString() {
        return "{" + "lane=" + lane + ", spot=" + spot + '}';
    }

    /**
     * checks if this vehicle is behind another vehicle
     * @param currentLocation the position of the vehicle infront
     * @return if a vehicle is behind
     * */
    public boolean isBehind(RoadPosition currentLocation) {
        return spot<currentLocation.spot;
    }
}
