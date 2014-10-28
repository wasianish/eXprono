package eXprono.gui;

import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

import jssc.SerialPortList;

public class SelectorGUI extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/*
	 * Adding in private fields of 
	 * the components
	 */
<<<<<<< HEAD
	public JFrame frameSelector;
=======
>>>>>>> cb02923a8f8ffac00cd425984afe05a5bd054173
	private JComboBox<String> comboPort;
	private String[] portStrings;
	private JLabel textPort;
	private JLabel textBoard;
	private String[] boardStrings = {"Uno", "Leonardo", "Due", "Yun", "Tre", 
		"Zero","Micro","Esplora", "Mega ADK","Ethernet","Mega2560", 
		"Robot", "Nano", "Fio"};
	private JComboBox<String> comboBoard;
	private JLabel iconLabel;
	private JButton btnSubmit;
	
	/**
	 * Create the application.
	 */
	public SelectorGUI() {
		
		GUIListener listener = new GUIListener();
		
		this.setIconImage(Toolkit.getDefaultToolkit().getImage(SelectorGUI.class.getResource("/javax/swing/plaf/metal/icons/Error.gif")));
		this.setResizable(false);
		this.setTitle("eXprono");
		this.setBounds(100, 100, 248, 306);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setLayout(null);
		
		portStrings = SerialPortList.getPortNames();
		comboPort = new JComboBox<String>(portStrings);
		comboPort.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		comboPort.setBounds(36, 121, 155, 20);
		this.getContentPane().add(comboPort);
		
		textPort = new JLabel("Select a COM Port");
		textPort.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		textPort.setBounds(36, 96, 186, 14);
		this.getContentPane().add(textPort);
		
		textBoard = new JLabel("Select an Arduino Board");
		textBoard.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		textBoard.setBounds(36, 152, 155, 14);
		this.getContentPane().add(textBoard);
		
		comboBoard = new JComboBox<String>(boardStrings);
		comboBoard.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		comboBoard.setBounds(36, 177, 155, 20);
		this.getContentPane().add(comboBoard);
		
		iconLabel = new JLabel("");
		iconLabel.setIcon(new ImageIcon(SelectorGUI.class.getResource("/sun/print/resources/duplex.png")));
		iconLabel.setBounds(36, 11, 161, 74);
		this.getContentPane().add(iconLabel);
		
		btnSubmit = new JButton("Submit");
		btnSubmit.setBounds(36, 226, 155, 30);
		btnSubmit.addActionListener(listener);
		this.getContentPane().add(btnSubmit);
		
		this.setVisible(true);
	}
}
