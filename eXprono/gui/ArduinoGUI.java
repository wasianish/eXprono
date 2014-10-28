package eXprono.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JSlider;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.Toolkit;

public class ArduinoGUI {

	/*
	 * Adding in private fields of 
	 * the components
	 */
	private JFrame frameArduino;
	private JLabel boardDisplay;
	private JLabel lblPinDetails;
	private JLabel lblPinNumber;
	private JLabel lblMode;
	private JLabel lblValue;
	private JLabel lblType;
	private JSlider sliderPWM;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ArduinoGUI window = new ArduinoGUI();
					window.frameArduino.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ArduinoGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frameArduino = new JFrame();
		frameArduino.setIconImage(Toolkit.getDefaultToolkit().getImage(ArduinoGUI.class.getResource("/javax/swing/plaf/metal/icons/Error.gif")));
		frameArduino.setTitle("eXprono");
		frameArduino.setResizable(false);
		frameArduino.setBounds(100, 100, 544, 510);
		frameArduino.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frameArduino.getContentPane().setLayout(null);
		
		boardDisplay = new JLabel("");
		boardDisplay.setIcon(new ImageIcon(ArduinoGUI.class.getResource("/com/sun/javafx/scene/control/skin/modena/HTMLEditor-Cut.png")));
		boardDisplay.setBounds(10, 11, 508, 268);
		frameArduino.getContentPane().add(boardDisplay);
		
	 	lblPinDetails = new JLabel("Pin Details");
		lblPinDetails.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblPinDetails.setBounds(20, 290, 177, 14);
		frameArduino.getContentPane().add(lblPinDetails);
		
		lblPinNumber = new JLabel("Number");
		lblPinNumber.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblPinNumber.setBounds(20, 315, 59, 14);
		frameArduino.getContentPane().add(lblPinNumber);
		
		lblMode = new JLabel("Mode");
		lblMode.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblMode.setBounds(20, 365, 59, 14);
		frameArduino.getContentPane().add(lblMode);
		
		lblValue = new JLabel("Value");
		lblValue.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblValue.setBounds(20, 390, 59, 14);
		frameArduino.getContentPane().add(lblValue);
		
		lblType = new JLabel("Type");
		lblType.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblType.setBounds(20, 340, 59, 14);
		frameArduino.getContentPane().add(lblType);
		
		sliderPWM = new JSlider();
		sliderPWM.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		sliderPWM.setToolTipText("changes the PWM frequency");
		sliderPWM.setValue(0);
		sliderPWM.setPaintLabels(true);
		sliderPWM.setMaximum(255);
		sliderPWM.setMajorTickSpacing(255);
		sliderPWM.setBounds(20, 415, 200, 50);
		frameArduino.getContentPane().add(sliderPWM);
	}
}
