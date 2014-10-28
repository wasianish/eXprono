package eXprono.boards;

import eXprono.gui.ArduinoBoard;


public class Mega2560 extends ArduinoBoard {	
	
	private static final int digitalPins = 54;
	private static final int analogPins = 16;
	private static final int serials = 4;
	private static final int[] pwmPins = {2,3,4,5,6,7,8,9,10,11,12,13};
	
	public Mega2560(String comPort) {
		super(comPort, digitalPins, analogPins, serials, pwmPins);
	}

}
