package edu.neu.csye6200.av;

import edu.neu.csye6200.ui.Images;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;


public class Vehicle {

    private int cell;
    private int lane;
    private double fuelCapacity;
    private double fuelEfficiency;
    private int stopDistance;
    private int speed;
    private int id;
    private AVRule avRule;
    private BufferedImage image;

    //todo do this factory method with auto incrementing the id
    public Vehicle(AVRule avRule, int id,  int lane, int cell, int speed) {
        this.id = id;
        this.speed = speed;
        this.lane = lane;
        this.cell = cell;
        this.avRule = avRule;
        this.image = Images.getInstance().getImage();
    }

    public void setRuleStrategy(AVRule ruleStrategy) {
        this.avRule = ruleStrategy;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    /**
     * calls the drive method of the implementation of avRule passed to this vehicle instance
     */
    public void drive() {
        avRule.drive(this);
    }

    public int getId() {
        return id;
    }

    public int getCell() {
        return cell;
    }

    public int getLane() {
        return lane;
    }

    public void setCell(int cell) {
        this.cell = cell;
    }

    public void setLane(int lane) {
        this.lane = lane;
    }

    public int getSpeed() {
        return speed;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "id=" + id + "," +
                "speed=" + speed + "," +
                "location=" + currentLocation() +
                '}';
    }

    /**
     * @return the road position
     */
    public RoadPosition currentLocation() {
        return new RoadPosition(lane, cell);
    }

    public void setLocation(RoadPosition roadPosition){
        if(roadPosition!=null) {
            this.lane = roadPosition.getLane();
            this.cell = roadPosition.getSpot();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vehicle vehicle = (Vehicle) o;
        return id == vehicle.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /**
     * @return the image associated with this vehicle instance
     */
    public BufferedImage getImage() {
        return image;
    }
}
