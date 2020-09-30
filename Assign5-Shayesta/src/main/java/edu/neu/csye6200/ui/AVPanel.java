package edu.neu.csye6200.ui;

import edu.neu.csye6200.av.Road;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.Observable;
import java.util.Observer;


public class AVPanel extends JPanel implements Observer{


	private static final long serialVersionUID = 2105533625224637015L;
	private Simulation sim = null;
	private LightThemeRoadPanel roadPanel;

	public AVPanel() {
		roadPanel=new LightThemeRoadPanel();
		setLayout(new GridLayout(0,1));
		add(roadPanel);
		repaint();
	}

	@Override
	public void update(Observable o, Object arg) {
		System.out.println("AvPanel received an update");
		if (arg instanceof Simulation) {
			sim = (Simulation) arg;
			this.repaint(); // Notify that we need to paint the canvas
		}

	}

}
