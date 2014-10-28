package eXprono.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;

import java.awt.GridLayout;

import javax.swing.JLabel;

import java.awt.Window.Type;

import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import javax.swing.JPasswordField;

import java.awt.Font;

import jssc.SerialPortList;

public class SelectorGUI {

	private JFrame selectorForm;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SelectorGUI window = new SelectorGUI();
					window.selectorForm.setVisible(true);
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
		selectorForm = new JFrame();
		selectorForm.setTitle(" eXprono ");
		selectorForm.setBounds(100, 100, 248, 180);
		selectorForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		selectorForm.getContentPane().setLayout(null);
		
		String[] portStrings = SerialPortList.getPortNames();
		JComboBox comboPort = new JComboBox(portStings);
		comboPort.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		comboPort.setBounds(36, 38, 155, 20);
		comboPort.addActionListener(new GUIListener());
		selectorForm.getContentPane().add(comboPort);
		
		JLabel textPort = new JLabel("Select a COM Port");
		textPort.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		textPort.setBounds(36, 13, 186, 14);
		selectorForm.getContentPane().add(textPort);
		
		JLabel textBoard = new JLabel("Select an Arduino Board");
		textBoard.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		textBoard.setBounds(36, 69, 155, 14);
		selectorForm.getContentPane().add(textBoard);
		
		String[] boardStrings = {"Uno", "Leonardo", "Due", "Yun", "Tre", 
				"Zero","Micro","Esplora,""Mega ADK","Ethernet","Mega 2560", 
				"Robot", "Nano", "Fio", "Pro"};
		JComboBox comboBoard = new JComboBox(boardStrings);
		comboBoard.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		comboBoard.setBounds(36, 94, 155, 20);
		comboBoard.addActionListener(new GUIListener());
		selectorForm.getContentPane().add(comboBoard);
	}
}
