package eXprono.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class GUIListener implements ActionListener, ComponentListener {

	@Override
	public void actionPerformed(ActionEvent event) {
		if((JButton)event.getSource() == Main.selectGUI.btnSubmit) {
			Main.selected();
		}
	}

	@Override
	public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentMoved(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentResized(ComponentEvent e) {
		if(((JFrame)e.getSource()) == Main.arduinoGUI && Main.arduinoGUI.doneResizing) {
			Main.arduinoGUI.resized();
		}
	}

	@Override
	public void componentShown(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

}
