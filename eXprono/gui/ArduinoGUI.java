package eXprono.gui;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.border.TitledBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ArduinoGUI extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/*
	 * Adding in private fields of 
	 * the components
	 */
	private JLabel boardDisplay;
	private JLabel lblPinDetails;
	private JLabel lblNumber;
	private JLabel lblMode;
	private JLabel lblValue;
	private JLabel lblType;
	private JSlider sliderPWM;
	private JButton btnIO;
	private JButton btnHighLow;
	private JLabel lblPinNumber;
	private JLabel lblPinType;
	private JLabel lblPinMode;
	private JLabel lblPinValue;
	
	private BufferedImage scaledImage;
	
	private ArduinoBoard board;
	
	private int pastX;
	private int pastY;
	
	public boolean doneResizing = true;
	public static JButton btnNewInstance;
	
	/**
	 * Create the application.
	 */
	public ArduinoGUI(ArduinoBoard board) {
		
		this.board = board;
		
		this.addComponentListener(Main.listener);
		
		this.setResizable(true);
		//this.setIconImage(Toolkit.getDefaultToolkit().getImage(ArduinoGUI.class.getResource("/javax/swing/plaf/metal/icons/Error.gif")));
		this.setTitle("eXprono");
		this.setBounds(100, 100, 544, 510);
		pastX = this.getWidth();
		pastY = this.getHeight();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setLayout(null);
		
		boardDisplay = new JLabel("");
		boardDisplay.setBounds(10, 11, 508, 268);
		this.scaleArduinoImage(board.image);
		boardDisplay.setIcon(new ImageIcon(scaledImage));
		this.getContentPane().add(boardDisplay);
		
	 	lblPinDetails = new JLabel("Pin Details");
		lblPinDetails.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblPinDetails.setBounds(20, 290, 177, 14);
		this.getContentPane().add(lblPinDetails);
		
		lblNumber = new JLabel("Number");
		lblNumber.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblNumber.setBounds(20, 315, 59, 14);
		this.getContentPane().add(lblNumber);
		
		lblMode = new JLabel("Mode");
		lblMode.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblMode.setBounds(20, 365, 59, 14);
		this.getContentPane().add(lblMode);
		
		lblValue = new JLabel("Value");
		lblValue.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblValue.setBounds(20, 390, 59, 14);
		this.getContentPane().add(lblValue);
		
		lblType = new JLabel("Type");
		lblType.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblType.setBounds(20, 340, 59, 14);
		this.getContentPane().add(lblType);
		
		sliderPWM = new JSlider();
		sliderPWM.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		sliderPWM.setToolTipText("changes the PWM frequency");
		sliderPWM.setValue(0);
		sliderPWM.setPaintLabels(true);
		sliderPWM.setMaximum(255);
		sliderPWM.setMajorTickSpacing(255);
		sliderPWM.setBounds(20, 415, 215, 50);
		this.getContentPane().add(sliderPWM);
		
		lblPinNumber = new JLabel("N/A");
		lblPinNumber.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblPinNumber.setBounds(117, 315, 80, 14);
		this.getContentPane().add(lblPinNumber);
		
		lblPinType = new JLabel("N/A");
		lblPinType.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblPinType.setBounds(117, 341, 80, 14);
		this.getContentPane().add(lblPinType);
		
		lblPinMode = new JLabel("N/A");
		lblPinMode.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblPinMode.setBounds(117, 366, 80, 14);
		this.getContentPane().add(lblPinMode);
		
		lblPinValue = new JLabel("N/A");
		lblPinValue.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblPinValue.setBounds(117, 391, 80, 14);
		this.getContentPane().add(lblPinValue);
		
		btnIO = new JButton("I/O");
		btnIO.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		btnIO.setBounds(174, 362, 61, 23);
		btnIO.addActionListener(Main.listener);
		this.getContentPane().add(btnIO);
		
		btnHighLow = new JButton("Hi/Lo");
		btnHighLow.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		btnHighLow.setBounds(174, 387, 61, 23);
		btnHighLow.addActionListener(Main.listener);
		this.getContentPane().add(btnHighLow);
		
		JButton btnNewInstance = new JButton("Open Another Arduino");
		btnNewInstance.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		btnNewInstance.setBounds(371, 439, 147, 23);
		btnNewInstance.addActionListener(Main.listener);
		getContentPane().add(btnNewInstance);
		
		this.setVisible(true);
	}
	
	private void scaleArduinoImage(BufferedImage input) {
		double heightRatio = (double)input.getHeight()/boardDisplay.getHeight();
		double widthRatio = (double)input.getWidth()/boardDisplay.getWidth();
		int newWidth = boardDisplay.getWidth();
		int newHeight = boardDisplay.getHeight();
		if(heightRatio > widthRatio) {
			newWidth = (int)(input.getWidth()/heightRatio);
		} else {
			newHeight = (int)(input.getHeight()/widthRatio);
		}
		scaledImage = new BufferedImage(newWidth, newHeight, BufferedImage.TRANSLUCENT);
		Graphics2D g = scaledImage.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
	    g.drawImage(input, 0, 0, newWidth, newHeight, null);
	    g.dispose();
	}
	
	public void resized() {
		doneResizing = false;
		int changeX = this.getWidth() - pastX;
		int changeY = this.getHeight() - pastY;
		lblMode.setLocation(lblMode.getX(), lblMode.getY() + changeY);
		lblNumber.setLocation(lblNumber.getX(), lblNumber.getY() + changeY);
		lblPinDetails.setLocation(lblPinDetails.getX(), lblPinDetails.getY() + changeY);
		lblValue.setLocation(lblValue.getX(), lblValue.getY() + changeY);
		lblType.setLocation(lblType.getX(), lblType.getY() + changeY);
		sliderPWM.setLocation(sliderPWM.getX(), sliderPWM.getY() + changeY);
		lblPinNumber.setLocation(lblPinNumber.getX(), lblPinNumber.getY() + changeY);
		lblPinType.setLocation(lblPinType.getX(), lblPinType.getY() + changeY);
		lblPinMode.setLocation(lblPinMode.getX(), lblPinMode.getY() + changeY);
		lblPinValue.setLocation(lblPinValue.getX(), lblPinValue.getY() + changeY);
		btnIO.setLocation(btnIO.getX(), btnIO.getY() + changeY);
		btnHighLow.setLocation(btnHighLow.getX(), btnHighLow.getY() + changeY);
		boardDisplay.setSize(boardDisplay.getWidth() + changeX, boardDisplay.getHeight() + changeY);
		pastX = this.getWidth();
		pastY = this.getHeight();
		scaleArduinoImage(board.image);
		boardDisplay.setIcon(new ImageIcon(this.scaledImage));
		doneResizing = true;
	}
}