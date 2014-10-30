package eXprono.gui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JFrame;

public class GraphGUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<Pin> pins = new ArrayList<Pin>();
	
	private List<HashMap<Pin,Double>> data = new ArrayList<HashMap<Pin,Double>>();
	
	private int pixelsPerTimeStamp = 3;
	private int pixelsPerFiveVolt = 50;
	public boolean active = false;
	
	public GraphGUI() {
		this.setResizable(false);
		this.setSize(500, 500);
		this.setVisible(true);
		
		data.add(new HashMap<Pin,Double>());
	}
	
	public void addPin(Pin pin) {
		pins.add(pin);
		data.get(data.size() - 1).put(pin, pin.getNormalizedValue());
		active = true;
	}
	
	public void graph() {
		HashMap<Pin,Double> temp = new HashMap<Pin,Double>();
		for(Pin pin: pins) {
			if(pin.isGraphed) {
				pin.update();
				temp.put(pin, pin.getNormalizedValue());
			}
			
		}
		data.add(temp);
		BufferedImage nImage = new BufferedImage(500,500,BufferedImage.TYPE_4BYTE_ABGR);
		Graphics g = nImage.createGraphics();
		for(int i = data.size() - 1; i > 0; i--) {
			if((data.size() - 1 - i) * pixelsPerTimeStamp < 0) {
				data.remove(0);
				return;
			}
			for(Pin pin: data.get(i).keySet()) {
				try {
					g.setColor(pin.color);
					g.drawLine(this.getWidth() - (data.size() - 1 - i) * pixelsPerTimeStamp, 
						(int)(this.getHeight() - data.get(i - 1).get(pin) * pixelsPerFiveVolt), 
						this.getWidth() - (data.size() - 2 - i) * pixelsPerTimeStamp, 
						(int)(this.getHeight() - data.get(i).get(pin) * pixelsPerFiveVolt));
				} catch(Exception e) {
					
				}
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
