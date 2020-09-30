package edu.neu.csye6200.ui;

public interface UIConstants {
    int LANE1_Y_COORDINATE = 120;
    int LANE2_Y_COORDINATE = 230;
    int LANE3_Y_COORDINATE = 360;
    int CAR_WIDTH = 45;
    int CAR_HEIGHT = 45;

    String SIMPLE = "Simple";
    String COMPLEX_LEFT_PASSING = "Complex - Left Passing";
    String COMPLEX_LEFT_AND_RIGHT_PASSING = "Complex - Left and Right Passing";

   //scenarios
    String SCENARIO_RANDOM = "Random";
    String SCENARIO_LANE_CHANGE_LEFT_TWO_STOPPED_VEHICLES = "Lane Change Left - Two Stopped Vehicles (Run in Fullscreen)" ;
    String SCENARIO_LANE_CHANGE_LEFT_LANE = "Lane Change Left Lane (Run in Fullscreen)";
    String SCENARIO_LANE_CHANGE_LEFT_LANE_ADJ_VEHICLE = "Lane Change LeftLane - AdjVehicle (Run in Fullscreen)";
    String SCENARIO_LANE_CHANGE_LEFT_LANE_STOPPED_VEHICLE = "Lane Change Left Lane - Stopped Vehicle (Run in Fullscreen)";
}
