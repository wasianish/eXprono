package eXprono.gui;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.border.TitledBorder;
import javax.swing.JButton;

public class ArduinoGUI {

	/*
	 * Adding in private fields of 
	 * the components
	 */
	private JFrame frameArduino;
	private JLabel boardDisplay;
	private JLabel lblPinDetails;
	private JLabel lblNumber;
	private JLabel lblMode;
	private JLabel lblValue;
	private JLabel lblType;
	private JSlider sliderPWM;
	private JButton btnIO;
	private JButton btnHighLow;
	
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
		frameArduino.setResizable(false);
		frameArduino.setIconImage(Toolkit.getDefaultToolkit().getImage(ArduinoGUI.class.getResource("/javax/swing/plaf/metal/icons/Error.gif")));
		frameArduino.setTitle("eXprono");
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
		
		lblNumber = new JLabel("Number");
		lblNumber.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblNumber.setBounds(20, 315, 59, 14);
		frameArduino.getContentPane().add(lblNumber);
		
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
		sliderPWM.setBounds(20, 415, 215, 50);
		frameArduino.getContentPane().add(sliderPWM);
		
		JLabel lblPinNumber = new JLabel("N/A");
		lblPinNumber.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblPinNumber.setBounds(117, 315, 80, 14);
		frameArduino.getContentPane().add(lblPinNumber);
		
		JLabel lblPinType = new JLabel("N/A");
		lblPinType.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblPinType.setBounds(117, 341, 80, 14);
		frameArduino.getContentPane().add(lblPinType);
		
		JLabel lblPinMode = new JLabel("N/A");
		lblPinMode.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblPinMode.setBounds(117, 366, 80, 14);
		frameArduino.getContentPane().add(lblPinMode);
		
		JLabel lblPinValue = new JLabel("N/A");
		lblPinValue.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblPinValue.setBounds(117, 391, 80, 14);
		frameArduino.getContentPane().add(lblPinValue);
		
		btnIO = new JButton("I/O");
		btnIO.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		btnIO.setBounds(174, 362, 61, 23);
		frameArduino.getContentPane().add(btnIO);
		
		btnHighLow = new JButton("Hi/Lo");
		btnHighLow.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		btnHighLow.setBounds(174, 387, 61, 23);
		frameArduino.getContentPane().add(btnHighLow);
	}
}
