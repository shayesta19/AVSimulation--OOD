/**
 *
 */
package edu.neu.csye6200.ui;

import edu.neu.csye6200.av.AVRule;
import edu.neu.csye6200.av.ChangeLanesToPassLeftAVRule;
import edu.neu.csye6200.av.ChangeLanesToPassLeftAndRightAVRule;
import edu.neu.csye6200.av.SimpleMoveStraightInLaneAVRule;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Logger;

import javax.swing.*;

import static edu.neu.csye6200.ui.UIConstants.*;

/**
 * @author mgmunson
 *
 */
public class MyAppUI extends AVApp implements ActionListener {


    private Logger log = Logger.getLogger(MyAppUI.class.getName());
    private final Object lock = new Object();

    private AVPanel myPanel;
    protected JPanel mainPanel;
    protected JPanel northPanel;
    protected ControlsPanel controlsPanel;
    protected JPanel containerPanel;
    private JButton startBtn;
    private JButton pauseBtn;
    private JButton stopBtn;
    private JScrollPane scrollPane;
    private JTextArea aboutArea;

    private JComboBox<String> ruleComboBox;

    private Simulation sim = null;

    /**
     * Constructor
     */
    @SuppressWarnings("deprecation")
    public MyAppUI() {

        frame.setSize(800, 600);
        frame.setTitle("MyAppUI");

        menuMgr.createDefaultActions(); // Set up default menu items

        initSim(); // Initialize the sim

        showUI(); // Cause the Swing Dispatch thread to display the JFrame
        // make the subscription
        aboutArea=new JTextArea(1000,1000);
        aboutArea.setLineWrap(true);
        scrollPane=new JScrollPane(aboutArea);
        try {
            aboutArea.read(new InputStreamReader(getClass().getResourceAsStream("/AboutPage.txt")),null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        sim.addObserver(myPanel); // Allow the panel to hear about simulation events

    }

    /*
     * Initialize the simulation
     */
    private void initSim() {
        sim = new Simulation(new SimulationControls());
    }

    /**
     * Get the North panel - this includes buttons and a combo box
     *
     * @return the JPanel which contains the north panel
     */
    public JPanel getNorthPanel() {
        northPanel = new JPanel();
        controlsPanel = new ControlsPanel();
        containerPanel = new JPanel();
        northPanel.setLayout(new FlowLayout());
        containerPanel.setLayout(new BorderLayout());
        containerPanel.add(controlsPanel, BorderLayout.SOUTH);
        containerPanel.add(northPanel, BorderLayout.NORTH);

        // northPanel.setBackground(Color.BLUE); // Set the background blue

        JLabel ruleLbl = new JLabel("Rule:");
        ruleComboBox = new JComboBox<String>();
        ruleComboBox.addItem(SIMPLE);// Rule 1
        ruleComboBox.addItem(COMPLEX_LEFT_PASSING);
        ruleComboBox.addItem(COMPLEX_LEFT_AND_RIGHT_PASSING);
        ruleComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String rule = ruleComboBox.getSelectedItem().toString();
                AVRule avRule = pickRule(rule);
                sim.setAvRule(avRule);
            }
        });

        startBtn = new JButton("Start");
        // startBtn.addActionListener(this); // subscribe to button events

        startBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Start was pressed");
                sim.setSimulationControls(controlsPanel.getSimulationControls());
                sim.startSim();
                sim.setRunning(true); // force this on early, because we're about to reset the buttons
                resetButtons();
            }

        });

        pauseBtn = new JButton("Pause");
        pauseBtn.setEnabled(false); // Disable until 'start' has been pressed
        pauseBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Pause was pressed");
                sim.pauseSim();
                pauseBtn.setText(sim.isPaused() ? "Resume" : "Pause");
                resetButtons();
            }

        });

        stopBtn = new JButton("Stop");
        stopBtn.setEnabled(false); // Disable until 'start' has been pressed
        stopBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Stop was pressed");
                sim.stopSim();
                sim.setRunning(false); // Force this off early, because we're about to reset the buttons
                resetButtons();
            }

        });

        // Add everthing to the north panel
        northPanel.add(ruleLbl);
        northPanel.add(ruleComboBox);

        northPanel.add(startBtn);
        northPanel.add(pauseBtn);
        northPanel.add(stopBtn);

        return containerPanel;
    }

    private AVRule pickRule(String rule) {
        switch (rule) {
            case COMPLEX_LEFT_PASSING:
                return new ChangeLanesToPassLeftAVRule();
            case COMPLEX_LEFT_AND_RIGHT_PASSING:
                return new ChangeLanesToPassLeftAndRightAVRule();
            default:
                return new SimpleMoveStraightInLaneAVRule();
        }
    }

    private void resetButtons() {
        if (sim == null)
            return;

        ruleComboBox.setEnabled(!sim.isRunning());

        startBtn.setEnabled(!sim.isRunning());
        pauseBtn.setEnabled(sim.isPausable());
        stopBtn.setEnabled(sim.isRunning() && !sim.isPaused());
        for (Component c : controlsPanel.getComponents()) {
            if (c instanceof JComboBox) {
                c.setEnabled(!sim.isRunning());
            }
            if (c instanceof JTextField) {
                c.setEnabled(!sim.isRunning());
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        log.info("We received an ActionEvent " + e);
        if (e.getSource() == stopBtn) {
            System.out.println("Stop was pressed");
        }
    }

    @Override
    public void showHelp() {
        JOptionPane.showMessageDialog(null,
                scrollPane,
                "About",
                JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        MyAppUI myApp = new MyAppUI();
        System.out.println("MyAppUI is exiting !!!!!!!!!!");
    }

    @Override
    public void windowOpened(WindowEvent e) {
    }

    @Override
    public void windowClosing(WindowEvent e) {
    }

    @Override
    public void windowClosed(WindowEvent e) {
    }

    @Override
    public void windowIconified(WindowEvent e) {
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
    }

    @Override
    public void windowActivated(WindowEvent e) {
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
    }

    @Override
    public JPanel getMainPanel() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(BorderLayout.NORTH, getNorthPanel());

        myPanel = new AVPanel();
        mainPanel.add(BorderLayout.CENTER, myPanel);
        return mainPanel;
    }

}
