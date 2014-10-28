package eXprono.gui;


public class Main {
	
	public static SelectorGUI selectGUI;
	public static ArduinoGUI arduinoGUI;
	
	public static GUIListener listener;
	
	public static void main(String[] args) {
		listener = new GUIListener();
		selectGUI = new SelectorGUI();
	}
	
	/*
	 * Called when the start button is clicked in the Selector GUI
	 */
	
	public static void selected() {
		selectGUI.setVisible(false);
		
		arduinoGUI = new ArduinoGUI(new ArduinoBoard((String)selectGUI.comboPort.getSelectedItem(),
				ArduinoBoard.Boards.getBoardFromName((String)selectGUI.comboBoard.getSelectedItem())));
	}

}
