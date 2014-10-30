package eXprono.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;

public class GUIListener implements ActionListener, ComponentListener, MouseListener, MouseMotionListener {

	@Override
	public void actionPerformed(ActionEvent event) {
		if(event.getSource().getClass().toString().equals("class javax.swing.JButton")) {
			if((JButton)event.getSource() == Main.selectGUI.btnSubmit) {
				Main.selected();
				return;
			} else if(((JButton)(event.getSource())).getText().equals("Open Another Arduino")) {
				Main.selectGUI = new SelectorGUI();
				return;
			} else if(((JButton)(event.getSource())).getText().equals("I/O")) {
				for(ArduinoGUI gui: Main.arduinoGUI) {
					if(((JButton)(event.getSource())).getRootPane().equals(gui.getRootPane())) {
						gui.toggleCurrentPinMode();
						return;
					}
				}
			} else if(((JButton)(event.getSource())).getText().equals("Hi/Lo")) {
				for(ArduinoGUI gui: Main.arduinoGUI) {
					if(((JButton)(event.getSource())).getRootPane().equals(gui.getRootPane())) {
						gui.toggleCurrentPinValue();;
						return;
					}
				}
			}
		} else if(event.getSource().getClass().toString().equals("class javax.swing.JCheckBox")) {
			if(((JCheckBox)event.getSource()).getText().equals("Graph")) {
				for(ArduinoGUI gui: Main.arduinoGUI) {
					if(((JCheckBox)event.getSource()).getRootPane().equals(gui.getRootPane())) {
						if(!((JCheckBox)event.getSource()).isSelected()) {
							gui.currentPin.isGraphed = false;
							return;
						}
						if(gui.graphGui == null) {
							gui.graphGui = new GraphGUI();
						}
						gui.graphGui.addPin(gui.currentPin);
						gui.currentPin.isGraphed = true;
					}
				}
			}
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
/*		for(ArduinoGUI a:Main.arduinoGUI) {
			if(((JFrame)e.getSource()) == a && a.doneResizing()) {
				a.resized();
			}
		}*/
	}

	@Override
	public void componentShown(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent event) {
		((ArduinoGUI)(event.getSource())).clicked(event.getX(), event.getY());
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent event) {

	}

}
