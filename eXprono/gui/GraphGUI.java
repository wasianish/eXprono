package eXprono.gui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GraphGUI extends JFrame {

	private List<Pin> pins = new ArrayList<Pin>();
	
	private List<HashMap<Pin,Double>> data = new ArrayList<HashMap<Pin,Double>>();
	
	private int pixelsPerTimeStamp = 5;
	private int pixelsPerFiveVolt = 50;
	private long graphMillis = 100;
	
	private JPanel panel;
	
	public boolean active = false;
	
	private Runnable update = new Runnable() {

		@Override
		public void run() {
			while(true) {
				if(active) {
					graph();
					try {
						Thread.sleep(graphMillis);
					} catch(Exception e) {
						
					}
				}
			}
		}
	};
	
	public GraphGUI() {
		this.setResizable(false);
		this.setSize(500, 500);
		this.setVisible(true);
		
		data.add(new HashMap<Pin,Double>());
		
		Thread run = new Thread(update);
		run.start();
	}
	
	public void addPin(Pin pin) {
		pins.add(pin);
		data.get(data.size() - 1).put(pin, pin.getNormalizedValue());
		active = true;
	}
	
	public void graph() {
		HashMap<Pin,Double> temp = new HashMap<Pin,Double>();
		for(Pin pin: pins) {
			pin.update();
			temp.put(pin, pin.getNormalizedValue());
		}
		data.add(temp);
		BufferedImage nImage = new BufferedImage(500,500,BufferedImage.TYPE_4BYTE_ABGR);
		Graphics g = nImage.createGraphics();
		for(int i = data.size() - 1; i > 0; i--) {
			if((data.size() - 1 - i) * pixelsPerTimeStamp < 0) {
				return;
			}
			for(Pin pin: data.get(i).keySet()) {
				g.drawLine(this.getWidth() - (data.size() - 1 - i) * pixelsPerTimeStamp, 
						(int)(this.getHeight() - data.get(i - 1).get(pin) * pixelsPerFiveVolt), 
						this.getWidth() - (data.size() - 2 - i) * pixelsPerTimeStamp, 
						(int)(this.getHeight() - data.get(i).get(pin) * pixelsPerFiveVolt));
			}
		}
		this.getGraphics().clearRect(0, 0, 500, 500);
		this.getGraphics().drawImage(nImage, 0, 0, null);
	}
	
	public void stop() {
		active = false;
		data.clear();
		pins.clear();
		dispose();
	}
}
