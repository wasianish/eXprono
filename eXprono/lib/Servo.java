package eXprono.lib;

import eXprono.gui.ArduinoBoard;

public class Servo {
	
	private int pin = 0;
	private int angle = 0;
	private int pulse = 0;
	private int minPulse = 544;
	private int maxPulse = 2400;
	private ArduinoBoard board;
	
	public Servo(ArduinoBoard board) {
		this.board = board;
	}
	
	public void setMinimumPulse(int t) {
		
	}
	
	public void setMaximumPulse(int i) {
		
	}
	
	public void attach(int pin) {
		this.pin = pin;
		board.digitalWrite(pin, false);
		board.pinMode(pin, false);
	}
	
	public void write(int angle) {
		if(angle < 0) {
			angle = 0;
		} else if(angle > 180) {
			angle = 180;
		}
		this.angle = angle;
		pulse = (minPulse )
	}
}
