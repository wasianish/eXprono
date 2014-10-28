package eXprono.serial;

import jssc.SerialPort;

public class SerialConnection {
	
	private SerialPort serialPort;
	
	public SerialConnection(String comPort) {
		try {
			serialPort = new SerialPort(comPort);
			serialPort.openPort();
			serialPort.setParams(SerialPort.BAUDRATE_19200,
					SerialPort.DATABITS_8,
					SerialPort.STOPBITS_1,
					SerialPort.PARITY_NONE);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}
