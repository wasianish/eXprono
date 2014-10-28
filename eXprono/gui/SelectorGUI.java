package eXprono.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;

import java.awt.GridLayout;

import javax.swing.JLabel;

import java.awt.Window.Type;

import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import javax.swing.JPasswordField;

import java.awt.Font;

import jssc.SerialPortList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Toolkit;

public class SelectorGUI {

	private JFrame frameSelector;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SelectorGUI window = new SelectorGUI();
					window.frameSelector.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public SelectorGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		GUIListener listener = new GUIListener();
		
		frameSelector = new JFrame();
		frameSelector.setIconImage(Toolkit.getDefaultToolkit().getImage(SelectorGUI.class.getResource("/javax/swing/plaf/metal/icons/Error.gif")));
		frameSelector.setResizable(false);
		frameSelector.setTitle(" eXprono ");
		frameSelector.setBounds(100, 100, 248, 306);
		frameSelector.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frameSelector.getContentPane().setLayout(null);
		
		String[] portStrings = SerialPortList.getPortNames();
		JComboBox comboPort = new JComboBox(portStrings);
		comboPort.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		comboPort.setBounds(36, 121, 155, 20);
		comboPort.addActionListener(listener);
		frameSelector.getContentPane().add(comboPort);
		
		JLabel textPort = new JLabel("Select a COM Port");
		textPort.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		textPort.setBounds(36, 96, 186, 14);
		frameSelector.getContentPane().add(textPort);
		
		JLabel textBoard = new JLabel("Select an Arduino Board");
		textBoard.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		textBoard.setBounds(36, 152, 155, 14);
		frameSelector.getContentPane().add(textBoard);
		
		String[] boardStrings = {"Uno", "Leonardo", "Due", "Yun", "Tre", 
				"Zero","Micro","Esplora", "Mega ADK","Ethernet","Mega2560", 
				"Robot", "Nano", "Fio"};
		JComboBox comboBoard = new JComboBox(boardStrings);
		comboBoard.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		comboBoard.setBounds(36, 177, 155, 20);
		comboBoard.addActionListener(listener);
		frameSelector.getContentPane().add(comboBoard);
		
		JLabel iconLabel = new JLabel("");
		iconLabel.setIcon(new ImageIcon(SelectorGUI.class.getResource("/sun/print/resources/duplex.png")));
		iconLabel.setBounds(36, 11, 161, 74);
		frameSelector.getContentPane().add(iconLabel);
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.setBounds(36, 226, 155, 30);
		btnSubmit.addAncestorListener(listener);
		frameSelector.getContentPane().add(btnSubmit);
	}
}
