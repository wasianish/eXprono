package eXprono.gui;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
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
	public JFrame frameSelector;
	public JComboBox<String> comboPort;
	private String[] portStrings;
	private JLabel textPort;
	private JLabel textBoard;
	private String[] boardStrings;
	public JComboBox<String> comboBoard;
	private JLabel iconLabel;
	public JButton btnSubmit;
	
	/**
	 * Create the application.
	 */
	public SelectorGUI() {
		
		boardStrings = new String[ArduinoBoard.Boards.values().length];
		for(int i = 0; i < ArduinoBoard.Boards.values().length; i++) {
			boardStrings[i] = ArduinoBoard.Boards.values()[i].name;
		}
		
		this.setIconImage(Toolkit.getDefaultToolkit().getImage(SelectorGUI.class.getResource("/javax/swing/plaf/metal/icons/Error.gif")));
		this.setResizable(false);
		this.setTitle("eXprono");
		this.setBounds(100, 100, 248, 306);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.getContentPane().setLayout(null);
		
		portStrings = SerialPortList.getPortNames();
		List<String> ports = new ArrayList<String>();
		ports.add("---");
		for(int i = 0; i < portStrings.length; i++) {
			boolean found = false;
			for(ArduinoGUI board: Main.arduinoGUI) {
				if(board.board.serial.getPortName().equals(portStrings[i])) {
					found = true;
				}
			}
			if(!found) {
				ports.add(portStrings[i]);
			}
		}
		
		comboPort = new JComboBox<String>(ports.toArray(new String[0]));
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
		try {
			BufferedImage bi = ImageIO.read(new File(this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath() + "boards/logo.png"));
			BufferedImage newbr = new BufferedImage(161,74, BufferedImage.TRANSLUCENT);
			Graphics2D g = newbr.createGraphics();
			g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
			g.drawImage(bi, 0, 0, 161, 74, null);
			iconLabel.setIcon(new ImageIcon(newbr));
		} catch (IOException e) {
			e.printStackTrace();
		}
		iconLabel.setBounds(36, 11, 161, 74);
		this.getContentPane().add(iconLabel);
		
		btnSubmit = new JButton("Start");
		btnSubmit.setBounds(36, 226, 155, 30);
		btnSubmit.addActionListener(Main.listener);
		this.getContentPane().add(btnSubmit);
		
		this.setVisible(true);
	}
}
