package eXprono.gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.border.TitledBorder;

public class ArduinoGUI extends JFrame {

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
	private JCheckBox chkGraph;
	
	public JButton btnNewInstance;
	
	private BufferedImage scaledImage;
	public Pin currentPin;
	public ArduinoBoard board;
	
	private int pastX;
	private int pastY;
	
	private double scaleFactor;
	private boolean scaledToHeight = false;
	
	private Runnable updateScale = new Runnable() {

		@Override
		public void run() {
			while(true) {
				resized();
			}
		}
		
	};
	
	private Runnable updatePins = new Runnable() {

		@Override
		public void run() {
			while(true) {
				long start = System.currentTimeMillis();
				for(Pin pin: pinsToUpdate) {
					pin.update();
				}
				if(currentPin != null) {
					lblPinValue.setText(Double.toString(currentPin.getNormalizedValue()) + " V");
					lblPinMode.setText(currentPin.getMode().toString());
				}
				if(graphGui != null && graphGui.active) {
					graphGui.graph();
				}
				System.out.println(System.currentTimeMillis() - start);
				try {
					Thread.sleep(100);
				} catch(Exception e) {
				}
			}
		}
		
	};
	
	private List<Pin> pinsToUpdate = new ArrayList<Pin>();
	
	public GraphGUI graphGui;
	
	
	/**
	 * Create the application.
	 */
	public ArduinoGUI(ArduinoBoard board) {
		
		this.board = board;
		
		this.addMouseListener(Main.listener);
		
		this.addComponentListener(Main.listener);
		
		this.setResizable(true);
		//this.setIconImage(Toolkit.getDefaultToolkit().getImage(ArduinoGUI.class.getResource("/javax/swing/plaf/metal/icons/Error.gif")));
		this.setTitle("eXprono");
		this.setBounds(100, 100, 544, 510);
		pastX = this.getWidth();
		pastY = this.getHeight();
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
		lblPinType.setBounds(117, 341, 120, 14);
		this.getContentPane().add(lblPinType);
		
		lblPinMode = new JLabel("N/A");
		lblPinMode.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblPinMode.setBounds(117, 366, 120, 14);
		this.getContentPane().add(lblPinMode);
		
		lblPinValue = new JLabel("N/A");
		lblPinValue.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblPinValue.setBounds(117, 391, 80, 14);
		this.getContentPane().add(lblPinValue);
		
		btnIO = new JButton("I/O");
		btnIO.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		btnIO.setBounds(224, 362, 61, 23);
		btnIO.addActionListener(Main.listener);
		this.getContentPane().add(btnIO);
		
		btnHighLow = new JButton("Hi/Lo");
		btnHighLow.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		btnHighLow.setBounds(224, 387, 61, 23);
		btnHighLow.addActionListener(Main.listener);
		this.getContentPane().add(btnHighLow);
		
		btnNewInstance = new JButton("Open Another Arduino");
		btnNewInstance.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		btnNewInstance.setBounds(250, 439, 147, 23);
		btnNewInstance.addActionListener(Main.listener);
		this.getContentPane().add(btnNewInstance);
		
		chkGraph = new JCheckBox("Graph");
		chkGraph.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		chkGraph.setBounds(224, 337, 100, 23);
		chkGraph.addActionListener(Main.listener);
		this.getContentPane().add(chkGraph);
		
		this.setVisible(true);
		
		//currentPin = board.getFirstPin();
		//updateCurrentPin();
		
		Thread updateThread = new Thread(updateScale);
		Thread updatePinss = new Thread(updatePins);
		//read updateCurrentPinThread = new Thread(updateCurrentPin);
		updateThread.start();
		updatePinss.start();
		//updateCurrentPinThread.start();
	}
	
	/*
	 * Scale Arduino Image
	 * @params Buffered image of Arduino
	 * @run changes the Buffered Image to fit new scaling of the window
	 */
	private void scaleArduinoImage(BufferedImage input) {
		
		/*
		 * setting adjustment variables
		 */
		double heightRatio = (double)input.getHeight()/boardDisplay.getHeight();
		double widthRatio = (double)input.getWidth()/boardDisplay.getWidth();
		int newWidth = boardDisplay.getWidth();
		int newHeight = boardDisplay.getHeight();
		
		/*
		 * applying adjustment variables
		 */
		if(heightRatio > widthRatio) {
			newWidth = (int)(input.getWidth()/heightRatio);
			scaleFactor = heightRatio;
			scaledToHeight = true;
		} else {
			newHeight = (int)(input.getHeight()/widthRatio);
			scaleFactor = widthRatio;
			scaledToHeight = false;
		}
		
		/*
		 * applying image to GUI
		 */
		scaledImage = new BufferedImage(newWidth, newHeight, BufferedImage.TRANSLUCENT);
		Graphics2D g = scaledImage.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
	    g.drawImage(input, 0, 0, newWidth, newHeight, null);
	    g.dispose();
	}
	
	/*
	 * Resized
	 * @params nothing called based on Component Listener
	 * @run adjusts components and image for new window size
	 */
	public void resized() {
		
		/*
		 * setting adjustment variables
		 */
		int changeX = this.getWidth() - pastX;
		int changeY = this.getHeight() - pastY;
		if(changeX == 0 && changeY == 0) {
			return;
		}
		pastX = this.getWidth();
		pastY = this.getHeight();
		
		/*
		 * adjusts all of the GUI components with changeY int
		 */
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
		btnNewInstance.setLocation(btnNewInstance.getX(), btnNewInstance.getY() + changeY);
		chkGraph.setLocation(chkGraph.getX(), chkGraph.getY() + changeY);
		boardDisplay.setSize(boardDisplay.getWidth() + changeX, boardDisplay.getHeight() + changeY);
		
		/*
		 * adjusts image based on scaledImage from scaleArduinoImage()
		 */
		scaleArduinoImage(board.image);
		boardDisplay.setIcon(new ImageIcon(this.scaledImage));
		System.currentTimeMillis();
	}
	
	/*
	 * Clicked
	 * @params takes in int x and int y of the selected location from MouseListener
	 * @run compares location with adjusted location and updates pin when possible
	 */
	public void clicked(int x, int y) {
		
		if(currentPin != null && !currentPin.isGraphed) {
			pinsToUpdate.remove(currentPin);
		}
		Dimension d = getNormalizedScaledPoint(x, y);
		Pin pin = board.getPinAtCoords(d.width, d.height);
		//System.out.println(x + " " + y);
		if(pin != null) {
			chkGraph.setSelected(pin.isGraphed);
			pinsToUpdate.add(pin);
			currentPin = pin;
			lblPinNumber.setText(Integer.toString(currentPin.number));
			if(currentPin.digital) {
				lblPinType.setText("Digital");
			} else {
				lblPinType.setText("Analog");
			}
			lblPinMode.setText(currentPin.getMode().toString());
		}
	}
	
	/*
	 * get Normalized Scaled Point
	 * @params int y and int x of where was clicked
	 * @run adjusts window dimensions to match reference images for location identification
	 * @output point based on refernece image
	 */
	public Dimension getNormalizedScaledPoint(int x, int y) {
		Dimension out = new Dimension();
		
		/*
		 * Normalizes dimensions of Window
		 */
		int frameX = x - 8;
		int frameY = y - 30;
		int imageX = frameX - boardDisplay.getLocation().x;
		int imageY = frameY - boardDisplay.getLocation().y;
		int normalX = 0;
		int normalY = 0;
		
		/*
		 * scales X and Y with scale factor
		 */
		if(scaledToHeight) {
			normalX = (int)(imageX * scaleFactor);
			normalY = (int)(imageY * scaleFactor);
		} else {
			normalX = (int)(imageX * scaleFactor);
			normalY = (int)((imageY - ((double)boardDisplay.getSize().height - (double)scaledImage.getHeight())/2) * scaleFactor);
		}
		
		/*
		 * returns adjusted dimension for point
		 */
		out.height = normalY;
		out.width = normalX;
		return out;
	}
	
	/*
	 * Toggle Current Pin Mode
	 * @params called for changing pin mode from ActionListener
	 * @run sets Pin Mode text to selected pin mode
	 */
	public void toggleCurrentPinMode() {
		currentPin.togglePinMode();
	}
	
	/*
	 * Toggle Current Pin Value
	 * @params called for changing pin value from ActionListener
	 * @run sets Pin Mode text to selected pin value
	 */
	public void toggleCurrentPinValue() {
		currentPin.togglePinValue();
	}
}