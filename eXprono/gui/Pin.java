package eXprono.gui;

public class Pin {
	
	public static enum Modes {
		INPUT, OUTPUT, UNDECLARED;
	}
	
	public final boolean digital;
	public final int number;
	public final boolean pwm;
	private int value;
	private Modes mode = Modes.UNDECLARED;
	private ArduinoBoard board;
	
	private int pinImageType; // 1 is square else is circle
	private int dimension;
	private int x;
	private int y;
	
	public Pin(boolean digital, int number, boolean pwm, ArduinoBoard board) {
		this.digital = digital;
		this.number = number;
		this.pwm = pwm;
		this.board = board;
		if(!digital) {
			mode = Modes.OUTPUT;
		}
	}
	
	public void updateValue(int val) {
		value = val;
	}
	
	public int getValue() {
		return value;
	}
	
	public double getNormalizedValue() {
		double out;
		if(digital) {
			out =  value * 5;
		} else {
			out = ((double)value) * 5 / 1023;
		}
		out = (double)((int)out * 1000)/1000;
		return out;
	}
	
	public void setMode(Modes mod) {
		mode = mod;
	}
	
	public Modes getMode() {
		return mode;
	}
	
	public void update() {
		if(digital) {
			value = board.digitalRead(number);
		} else {
			value = board.analogRead(number);
			System.out.println(value);
		}
	}
	
	public boolean containsPixel(int x, int y) {
		if(pinImageType == 1) {
			if(x > this.x && x < this.x + dimension && y > this.y && y < this.y + dimension) {
				return true;
			}
			return false;
		} else {
			return false;
		}
	}
	
	public void addBounds(int type, int dimension, int x, int y) {
		this.pinImageType = type;
		this.dimension = dimension;
		this.x = x;
		this.y = y;
	}
	
	public void togglePinMode() {
		if(digital) {
			if(mode == Modes.OUTPUT) {
				board.pinMode(number, true);
				mode = Modes.INPUT;
			} else {
				board.pinMode(number, false);
				mode = Modes.OUTPUT;
			}
		}
	}
	
	public void togglePinValue() {
		if(digital && mode == Modes.OUTPUT) {
			if(value == 1) {
				board.digitalWrite(number, 0);
				value = 0;
			} else {
				board.digitalWrite(number, 1);
				value = 1;
			}
		}
	}
	
}
