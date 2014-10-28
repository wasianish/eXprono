package eXprono.gui;

import java.util.ArrayList;
import java.util.List;

import eXprono.serial.SerialConnection;


public class ArduinoBoard {
	
	private SerialConnection serial;
	
	private int[] digitalStates;
	private int[] analogStates;
	private int[] pwmPins;
	private List<Integer> pwmStates;
	private boolean[] toSerialForward;
	
	public ArduinoBoard(String comPort, int digitalPins, int analogPins, int serials, int[] pwm) {
		serial = new SerialConnection(comPort);
		digitalStates = new int[digitalPins];
		analogStates = new int[analogPins];
		toSerialForward = new boolean[serials];
		pwmPins = pwm;
		pwmStates = new ArrayList<Integer>();
		for(int i: pwmPins) {
			pwmStates.set(i, 0);
		}
	}
	
}
