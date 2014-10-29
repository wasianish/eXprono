package eXprono.gui;

public class Pin {
	
	public static enum Modes {
		INPUT, OUTPUT, UNDECLARED;
	}
	
	public final boolean digital;
	public final int number;
	public final boolean pwm;
	private int value;
	private Modes mode;
	
	public Pin(boolean digital, int number, boolean pwm) {
		this.digital = digital;
		this.number = number;
		this.pwm = pwm;
	}
	
	public void updateValue(int val) {
		value = val;
	}
	
	public int getValue() {
		return value;
	}
	
	public void setMode(Modes mod) {
		mode = mod;
	}
	
	public Modes getMode() {
		return mode;
	}
	
}
