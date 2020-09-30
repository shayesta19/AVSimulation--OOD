package edu.neu.csye6200.ui;

import edu.neu.csye6200.av.Road;
import edu.neu.csye6200.av.Vehicle;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import static edu.neu.csye6200.ui.UIConstants.*;

public class LightThemeRoadPanel extends JPanel {


    private BufferedImage grass;
    private BufferedImage animatedGravel;
    private BufferedImage blueSky;

    private int LANEHEIGHT = 120;


    LightThemeRoadPanel() {

        try {
            blueSky = ImageIO.read(getClass().getResource("/images/bluesky.png"));
            grass = ImageIO.read(getClass().getResource("/images/grass1.png"));
            animatedGravel = ImageIO.read(getClass().getResource("/images/gravelanimation.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent componentEvent) {
                Road.getInstance().init(getWidth());
                repaint();
            }
        });
        this.repaint();

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Road road = Road.getInstance();
        Dimension size = getSize();
        Graphics2D g2 = (Graphics2D) g;
        Rectangle r1 = new Rectangle(0, 114, size.width, 120);
        Rectangle r2 = new Rectangle(0, 234, size.width, 120);
        Rectangle r3 = new Rectangle(0, 334, size.width, 120);
        g2.drawImage(blueSky, 0, 0, size.width, size.height, null);
        g2.drawImage(grass, 0, 82, size.width, 30, null);
        g2.drawImage(animatedGravel, 0, 72, size.width, 30, null);
        g2.drawImage(grass, 0, 437, size.width, 30, null);
        g2.drawImage(animatedGravel, 0, 467, size.width, 30, null);
        g2.setColor(Color.black);
        g2.fill(r1);
        g2.setColor(Color.black);
        g2.fill(r2);
        g2.setColor(Color.black);
        g2.fill(r3);
        System.out.println("height: " + size.height);
        System.out.println("width: " + size.width);
        g.setColor(Color.white);
        for (int i = 200; i < 3 * (this.LANEHEIGHT); i = i + this.LANEHEIGHT) {
            if (i < 4 * this.LANEHEIGHT) {
                for (int j = 0; j < getWidth(); j = j + 40) {
                    g.fillRect(j, i, 30, 5);
                }
            }
        }

        drawRoad(g, road);

    }

    private void drawRoad(Graphics g, Road road) {
        road.printRoad();
        for (int i = 0; i < road.getLaneCount(); i++) {
            for (int j = 0; j < getWidth() / CAR_WIDTH; j++) {
                Vehicle vehicle = road.vehicleAt(i, j);
                if (vehicle != null) {
                    g.drawImage(vehicle.getImage(), (int) Math.ceil((vehicle.getCell() + 1) * CAR_WIDTH), pickLane(vehicle.getLane()), CAR_WIDTH, CAR_HEIGHT, null);
                }
            }
        }
    }

    private int pickLane(int lane) {
        switch (lane) {
            case 1:
                return LANE2_Y_COORDINATE;
            case 2:
                return LANE3_Y_COORDINATE;
            default:
                return LANE1_Y_COORDINATE;
        }
    }

}
