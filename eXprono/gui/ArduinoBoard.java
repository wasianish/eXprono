package eXprono.gui;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
	
	public SerialPort serial;
	
	private static final int compStartByte = 36;
	private static final int arduinoStartByte = 64;
	
	public final File imageFile;
	public BufferedImage image;
	public BufferedImage pinImage;
	public final String dataFile;
	
	private Pin[] digitalPins;
	private Pin[] analogPins;
	
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
		
		analogPins = new Pin[board.analogPins];
		digitalPins = new Pin[board.digitalPins];
				
		for(int i = 0; i < board.digitalPins; i++) {
			boolean pwm = false;
			for(int j = 0; j < board.pwmPins.length; j++) {
				if(board.pwmPins[j] == i) {
					digitalPins[i] = new Pin(true, i, true, this);
					pwm = true;
					break;
				}
			}
			if(!pwm) {
				digitalPins[i] = new Pin(true, i, false, this);
			}
		}
		
		for(int i = 0; i < board.analogPins; i++) {
			analogPins[i] = new Pin(false, i, false, this);
		}
		
		imageFile = new File(this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath() + "boards/" + board.prefix + ".png"); // Also get the folder
		try {
			image = ImageIO.read(imageFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		dataFile = this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath() + "boards/" + board.prefix + ".txt"; // Also get the folder
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(dataFile));
			String temp = "";
			int pinStyle = 0;
			int dimension = 0;
			while((temp = br.readLine()) != null) {
				String[] stuff = temp.split("\t");
				if(stuff[0].equals("PinStyle")) {
					if(stuff[1].equals("Square")) {
						pinStyle = 1;
					} else {
						pinStyle = 2;
					}
				} else if(stuff[0].equals("Dimension")) {
					dimension = Integer.parseInt(stuff[1]);
				} else {
					int num = Integer.parseInt(stuff[0].substring(1));
					if(stuff[0].charAt(0) == 'D') {
						for(Pin pin: digitalPins) {
							if(pin.number == num) {
								pin.addBounds(pinStyle, dimension, Integer.parseInt(stuff[1]), Integer.parseInt(stuff[2]));
							}
						}
					} else {
						for(Pin pin: analogPins) {
							if(pin.number == num) {
								pin.addBounds(pinStyle, dimension, Integer.parseInt(stuff[1]), Integer.parseInt(stuff[2]));
							}
						}
					}
				}
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void digitalWrite(int pin, int value) {
		byte[] toSend = {compStartByte, (byte)(128 + pin), 1, 0};
		if(value == 1) {
			toSend[3] = 1;
		}
		try {
			serial.writeBytes(toSend);
		} catch(SerialPortException e) {
			e.printStackTrace();
		}
		digitalPins[pin].updateValue(value);
		
	}
	
	public int digitalRead(int pin) {
		byte[] toSend = {compStartByte, (byte)(128 + pin), 0, 0};
		try {
			serial.writeBytes(toSend);
		} catch(SerialPortException e) {
			e.printStackTrace();
		}
		byte[] received = nextByte();
		if(received.length == 0) {
			return -1;
		}
		digitalPins[pin].updateValue(received[1]);
		return received[1];
	}
	
	public void analogWrite(int pin, int value) {
		byte[] toSend = {compStartByte, (byte)pin, (byte)value, 0};
		try {
			serial.writeBytes(toSend);
		} catch(SerialPortException e) {
			e.printStackTrace();
		}
		digitalPins[pin].updateValue(value);
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
			return analogPins[pin].getValue();
		}
		return (received[1] & 127) - (received[1] & -128) + (received[2] << 8);
	}
	
	public void pinMode(int pin, boolean input) {
		byte[] toSend = {compStartByte, (byte) (128 + pin), 4, 0};
		digitalPins[pin].setMode(Pin.Modes.INPUT);
		if(input) {
			toSend[2] = 3;
			digitalPins[pin].setMode(Pin.Modes.OUTPUT);;
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
			out = new byte[0];
		}
		return out;
	}
	
	public Pin getPinAtCoords(int x, int y) {
		for(int i = 0; i < digitalPins.length; i++) {
			if(digitalPins[i].containsPixel(x, y)) {
				return digitalPins[i];
			}
		}
		for(int i = 0; i < analogPins.length; i++) {
			if(analogPins[i].containsPixel(x, y)) {
				return analogPins[i];
			}
		}
		return null;
	}
	
	public Pin getFirstPin() {
		return digitalPins[0];
	}
	
	
}
