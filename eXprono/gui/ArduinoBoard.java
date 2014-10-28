package eXprono.gui;

import java.util.ArrayList;
import java.util.List;

import jssc.SerialPort;


public class ArduinoBoard {
	
	public enum Boards{
		Mega2560(54, 16, 4, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13);
		
		public int digitalPins;
		public int analogPins;
		public int serials;
		public int[] pwmPins;
		
		private Boards(int digitalPins, int analogPins, int serials, int... pwmPins) {
			this.digitalPins = digitalPins;
			this.analogPins = analogPins;
			this.serials = serials;
			this.pwmPins = pwmPins;
		}
	}
	
	private SerialPort serial;
	
	
	
	private int[] digitalStates;
	private int[] analogStates;
	private int[] pwmPins;
	private List<Integer> pwmStates;
	private boolean[] toSerialForward;
	
	public ArduinoBoard(String comPort, Boards board) {
		try {
			serial = new SerialPort(comPort);
			serial.openPort();
			serial.setParams(SerialPort.BAUDRATE_19200,
					SerialPort.DATABITS_8,
					SerialPort.STOPBITS_1,
					SerialPort.PARITY_NONE);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		digitalStates = new int[board.digitalPins];
		analogStates = new int[board.analogPins];
		toSerialForward = new boolean[board.serials];
		pwmPins = board.pwmPins;
		pwmStates = new ArrayList<Integer>();
		for(int i: pwmPins) {
			pwmStates.set(i, 0);
		}
	}
	
	public void digitalWrite(int pin, boolean value) {
		byte[] toSend = new byte[4];
	}
	
	public boolean digitalRead(int pin) {
		return false;
	}
	
	public void analogWrite(int pin, int value) {
		
	}
	
	public int analogRead(int pin) {
		return false;
	}
	
	
	
}
