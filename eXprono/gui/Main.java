package eXprono.gui;

import java.util.ArrayList;
import java.util.List;


public class Main {
	
	public static SelectorGUI selectGUI;
	public static List<ArduinoGUI> arduinoGUI;
	
	public static GUIListener listener;
	
	public static void main(String[] args) {
		arduinoGUI = new ArrayList<ArduinoGUI>();
		listener = new GUIListener();
		selectGUI = new SelectorGUI();
	}
	
	/*
	 * Called when the start button is clicked in the Selector GUI
	 */
	
	public static void selected() {
		selectGUI.setVisible(false);
		
		arduinoGUI.add(new ArduinoGUI(new ArduinoBoard((String)selectGUI.comboPort.getSelectedItem(),
				ArduinoBoard.Boards.getBoardFromName((String)selectGUI.comboBoard.getSelectedItem()))));
	}

}
