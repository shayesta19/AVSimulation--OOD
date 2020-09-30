package edu.neu.csye6200.ui;

import javax.swing.*;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class ControlsPanel extends JPanel {

    JComboBox<String> scenariosComboBox;
    JSlider numberOfCarsSlider;
    private JLabel scenarioLbl;
    private JLabel numberOfCarsLabel;

    private SimulationControls simulationControls;

    public ControlsPanel() {
        this.setLayout(new GridBagLayout());
        initializeComponents();
        initLayout();
        this.setVisible(true);
        simulationControls = new SimulationControls();
    }

    private void initializeComponents() {

        scenariosComboBox = new JComboBox<>();
        numberOfCarsSlider = new JSlider(SwingConstants.HORIZONTAL, 10, 3);
        numberOfCarsSlider.addChangeListener(e -> {
			simulationControls.setNumberOfCars(numberOfCarsSlider.getValue());
			simulationControls.setChanged(true);
		});
        numberOfCarsSlider.setMajorTickSpacing(10);
        numberOfCarsSlider.setMinorTickSpacing(1);
        numberOfCarsSlider.setPaintTicks(true);
        numberOfCarsSlider.setPaintLabels(true);
        scenarioLbl = new JLabel("Choose scenario: ");
        numberOfCarsLabel = new JLabel("Number Of Cars: ");
        scenariosComboBox.addItem(UIConstants.SCENARIO_RANDOM);
        scenariosComboBox.addItem(UIConstants.SCENARIO_LANE_CHANGE_LEFT_LANE);
        scenariosComboBox.addItem(UIConstants.SCENARIO_LANE_CHANGE_LEFT_LANE_ADJ_VEHICLE);
        scenariosComboBox.addItem(UIConstants.SCENARIO_LANE_CHANGE_LEFT_LANE_STOPPED_VEHICLE);
        scenariosComboBox.addItem(UIConstants.SCENARIO_LANE_CHANGE_LEFT_TWO_STOPPED_VEHICLES);
        scenariosComboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				simulationControls.setScenario(Objects.requireNonNull(scenariosComboBox.getSelectedItem()).toString());
				simulationControls.setChanged(true);
			}
		});

    }

    private void initLayout() {

        GridBagConstraints gc = new GridBagConstraints();
        ///////////first column////////////////////
        gc.gridx = 0;
        gc.gridy = 0;
        gc.fill = GridBagConstraints.NONE;
        gc.insets = new Insets(0, 0, 5, 0);
        gc.anchor = GridBagConstraints.LINE_START;
        add(scenarioLbl, gc);

        gc.gridx = 0;
        gc.gridy = 1;
        gc.fill = GridBagConstraints.NONE;
        gc.anchor = GridBagConstraints.LINE_START;
        add(scenariosComboBox, gc);

        ////////////////second column///////////////
        gc.gridx = 1;
        gc.gridy = 0;
        gc.fill = GridBagConstraints.NONE;
        gc.insets = new Insets(0, 0, 5, 0);
        gc.anchor = GridBagConstraints.LINE_START;
        add(numberOfCarsLabel, gc);

        gc.gridx = 1;
        gc.gridy = 1;
        gc.fill = GridBagConstraints.NONE;
        gc.anchor = GridBagConstraints.LINE_START;
        add(numberOfCarsSlider, gc);
    }


    public SimulationControls getSimulationControls() {
        return simulationControls;
    }

}


