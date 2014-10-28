package eXprono.gui;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import jssc.SerialPort;
import jssc.SerialPortException;
import jssc.SerialPortList;


public class ArduinoBoard {
	
	public static enum Boards{
		UNO("Uno", "Uno", 14, 6, 1, 3, 5, 6, 9, 10, 11),
		LEONARDO("Leonardo", "Leonardo", 14, 6, 1, 5, 6, 9, 10, 11, 13),
		DUE("Due", "Due", 54, 12, 4, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13),
		//DUEMILANOVE("Duemilanove", "Duemilanove", 14, 6, 1, 3, 5, 6, 9, 10, 11),
		YUN("Yun", "Yun", 20, 12, 1, 3, 5, 6, 9, 10, 11, 13),
		//ZERO("Zero", "Zero", 14, 6, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13),
		MICRO("Micro", "Micro", 20, 12, 1, 3, 5, 6, 9, 10, 11, 13),
		MEGAADK("Mega ADK", "Mega", 54, 16, 4, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13),
		ETHERNET("Ethernet", "Ethernet", 10, 6, 1, 3, 5, 6, 9, 10),
		MEGA2560("Mega 2560", "Mega", 54, 16, 4, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13),
		NANO("Nano", "Nano", 14, 8, 1, 3, 5, 6, 9, 10, 11),
		FIO("Fio", "Fio", 14, 8, 1, 3, 5, 6, 9, 10, 11);
		
		public int digitalPins;
		public int analogPins;
		public int serials;
		public int[] pwmPins;
		public String prefix;
		public String name;
		
		private Boards(String name, String filePrefix, int digitalPins, int analogPins, int serials, int... pwmPins) {
			this.name = name;
			this.digitalPins = digitalPins;
			this.analogPins = analogPins;
			this.serials = serials;
			this.pwmPins = pwmPins;
			this.prefix = filePrefix;
		}
		
		public static Boards getBoardFromName(String name) {
			for(int i = 0; i < values().length; i++) {
				if(values()[i].name.equals(name)) {
					return values()[i];
				}
			}
			return null;
		}
	}
	
	private SerialPort serial;
	
	private static final int compStartByte = 36;
	private static final int arduinoStartByte = 64;
	
	public final File imageFile;
	public BufferedImage image;
	public final String dataFile;
	
	private boolean[] digitalStates;
	private int[] pwmStates;
	private int[] digitalModes;
	private int[] analogStates;
	private boolean[] toSerialForward;
	
	public ArduinoBoard(String comPort, Boards board) {
		try {
			for(String port:SerialPortList.getPortNames()) {
				if(port.equals(comPort)) {
					serial = new SerialPort(port);
				}
			}
			serial.openPort();
			serial.setParams(SerialPort.BAUDRATE_19200,
					SerialPort.DATABITS_8,
					SerialPort.STOPBITS_1,
					SerialPort.PARITY_NONE);
		} catch(Exception e) {
			e.printStackTrace();
		}
				
		digitalModes = new int[board.digitalPins];
		digitalStates = new boolean[board.digitalPins];
		pwmStates = new int[board.digitalPins];
		analogStates = new int[board.analogPins];
		toSerialForward = new boolean[board.serials];
		imageFile = new File(this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath() + "boards/" + board.prefix + ".png"); // Also get the folder
		try {
			image = ImageIO.read(imageFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		dataFile = this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath() + "boards/" + board.prefix + ".txt"; // Also get the folder
	}
	
	public void digitalWrite(int pin, boolean value) {
		byte[] toSend = {compStartByte, (byte)(128 + pin), 1, 0};
		if(value) {
			toSend[3] = 1;
		}
		try {
			serial.writeBytes(toSend);
		} catch(SerialPortException e) {
			e.printStackTrace();
		}
		digitalStates[pin] = value;
	}
	
	public boolean digitalRead(int pin) {
		byte[] toSend = {compStartByte, (byte)(128 + pin), 0, 0};
		try {
			serial.writeBytes(toSend);
		} catch(SerialPortException e) {
			e.printStackTrace();
		}
		byte[] received = nextByte();
		if(received.length == 0) {
			return false;
		}
		if(received[1] == 1) {
			digitalStates[pin] = true;
			return true;
		}
		digitalStates[pin] = false;
		return false;
	}
	
	public void analogWrite(int pin, int value) {
		byte[] toSend = {compStartByte, (byte)pin, (byte)value, 0};
		try {
			serial.writeBytes(toSend);
		} catch(SerialPortException e) {
			e.printStackTrace();
		}
		pwmStates[pin] = value;
	}
	
	public int analogRead(int pin) {
		byte[] toSend = {compStartByte, (byte)pin, 0, 0};
		try {
			serial.writeBytes(toSend);
		} catch(SerialPortException e) {
			e.printStackTrace();
		}
		byte[] received = nextByte();
		if(received.length == 0) {
			return -1;
		}
		return received[1] & 127 - received[1] & -128 + received[2] << 8;
	}
	
	public void pinMode(int pin, boolean input) {
		byte[] toSend = {compStartByte, (byte) (128 + pin), 4, 0};
		digitalModes[pin] = 1;
		if(input) {
			toSend[2] = 3;
			digitalModes[pin] = 2;
		}
		try {
			serial.writeBytes(toSend);
		} catch(SerialPortException e) {
			e.printStackTrace();
		}
	}
	
	private byte[] nextByte() {
		byte[] out = new byte[3];
		try {
			while(true) {
				byte a = serial.readBytes(1,100)[0];
				if(a == arduinoStartByte) {
					out = serial.readBytes(3,100);
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			out = new byte[0];
		}
		return out;
	}
	
	
}
