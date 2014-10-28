package eXprono.gui;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

import jssc.SerialPortList;

public class SelectorGUI {

	/*
	 * Adding in private fields of 
	 * the components
	 */
	private JFrame frameSelector;
	private JComboBox comboPort;
	private String[] portStrings;
	private JLabel textPort;
	private JLabel textBoard;
	private String[] boardStrings;
	private JComboBox comboBoard;
	private JLabel iconLabel;
	private JButton btnSubmit;
	
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
		frameSelector.setTitle("eXprono");
		frameSelector.setBounds(100, 100, 248, 306);
		frameSelector.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frameSelector.getContentPane().setLayout(null);
		
		portStrings = SerialPortList.getPortNames();
		comboPort = new JComboBox(portStrings);
		comboPort.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		comboPort.setBounds(36, 121, 155, 20);
		frameSelector.getContentPane().add(comboPort);
		
		textPort = new JLabel("Select a COM Port");
		textPort.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		textPort.setBounds(36, 96, 186, 14);
		frameSelector.getContentPane().add(textPort);
		
		textBoard = new JLabel("Select an Arduino Board");
		textBoard.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		textBoard.setBounds(36, 152, 155, 14);
		frameSelector.getContentPane().add(textBoard);
		
		boardStrings = {"Uno", "Leonardo", "Due", "Yun", "Tre", 
				"Zero","Micro","Esplora", "Mega ADK","Ethernet","Mega2560", 
				"Robot", "Nano", "Fio"};
		comboBoard = new JComboBox(boardStrings);
		comboBoard.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		comboBoard.setBounds(36, 177, 155, 20);
		frameSelector.getContentPane().add(comboBoard);
		
		iconLabel = new JLabel("");
		iconLabel.setIcon(new ImageIcon(SelectorGUI.class.getResource("/sun/print/resources/duplex.png")));
		iconLabel.setBounds(36, 11, 161, 74);
		frameSelector.getContentPane().add(iconLabel);
		
		btnSubmit = new JButton("Submit");
		btnSubmit.setBounds(36, 226, 155, 30);
		btnSubmit.addActionListener(listener);
		frameSelector.getContentPane().add(btnSubmit);
	}
}
