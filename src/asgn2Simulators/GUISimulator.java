/**
 * 
 * This file is part of the CarParkSimulator Project, written as 
 * part of the assessment for INB370, semester 1, 2014. 
 *
 * CarParkSimulator
 * asgn2Simulators 
 * 20/04/2014
 * 
 */
package asgn2Simulators;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;


	
/**
 * @author hogan
 * @author Ken Lewis
 * @author Thomas McCarthy
 */
@SuppressWarnings("serial")
public class GUISimulator extends JFrame implements Runnable {

	//Window Size
	protected static final int WIDTH = 1080;
	protected static final int HEIGHT = 920;
	protected static final Dimension PREFSIZE = new Dimension(WIDTH, HEIGHT);
	
	private JPanel userOptionsPanel;
	//Buttons
	private JButton run;
	private JButton chart;
	private JButton text;
	private JButton exit;
	
	//Titles
	
	private JLabel userOptions, variables, probabilities, statusCharts;
	
	//User Options 
	private JTextField default_max_car_spaces, default_max_small_car_spaces,
						default_max_motorcycle_spaces, default_max_queue_size;
	private JLabel max_car_spaces, max_small_car_spaces, max_motorcycle_spaces,
					max_queue_size;

	private JTextField default_seed, default_car_prob, default_small_car_prob,
						default_motorcycle_prob, default_intended_stay_mean,
						default_intended_stay_sd;
	private JLabel seed, car_prob, small_car_prob, motorcycle_prob, intended_stay_mean,
						intended_stay_sd;
	
	public GUISimulator (){
		// Initialize the Frame and add the GamePanel
				setTitle("Car Park Application");
				setSize(PREFSIZE);
				initilizeComponents();
				this.getContentPane().add(userOptionsPanel);
				repaint();
	}
	
	private void initilizeComponents (){
		
		//Create User Options Panel
		userOptionsPanel = new JPanel(new GridBagLayout());
		userOptionsPanel.setBorder(BorderFactory.createEtchedBorder());
		
		//Top User Options
		userOptions = new JLabel("User Data Entry Options");
		variables = new JLabel ("Variables");
		default_max_car_spaces = new JTextField();
		max_car_spaces = new JLabel("Max Car Spaces");
		default_max_small_car_spaces = new JTextField();
		max_small_car_spaces = new JLabel ("Max Small Car Spaces");
		default_max_motorcycle_spaces = new JTextField();
		max_motorcycle_spaces = new JLabel("Max Motorcycle Spaces");
		default_max_queue_size = new JTextField();
		max_queue_size = new JLabel ("Max Queue Size");
		
		//Probability Options
		probabilities = new JLabel ("Probabilities");
		default_seed = new JTextField();
		seed = new JLabel("Seed");
		default_car_prob = new JTextField();
		car_prob = new JLabel ("Car Probibility");
		default_small_car_prob = new JTextField();
		small_car_prob = new JLabel("Small Car Probibility");
		default_motorcycle_prob = new JTextField();
		motorcycle_prob = new JLabel ("Motorcycle Probibility");
		default_intended_stay_mean = new JTextField();
		intended_stay_mean = new JLabel("Intended Stay Mean");
		default_intended_stay_sd = new JTextField();
		intended_stay_sd = new JLabel ("Intended Stay SD");
		
		//Add Everything to User Options Panel
		userOptionsPanel.add(userOptions);
		userOptionsPanel.add(variables);
		userOptionsPanel.add(max_car_spaces);
		userOptionsPanel.add(default_max_car_spaces);
		userOptionsPanel.add(max_small_car_spaces);
		userOptionsPanel.add(default_max_small_car_spaces);
		userOptionsPanel.add(max_motorcycle_spaces);
		userOptionsPanel.add(default_max_motorcycle_spaces);
		userOptionsPanel.add(max_queue_size);
		userOptionsPanel.add(default_max_queue_size);
		userOptionsPanel.add(probabilities);
		userOptionsPanel.add(seed);
		userOptionsPanel.add(default_seed);
		userOptionsPanel.add(car_prob);
		userOptionsPanel.add(default_car_prob);
		userOptionsPanel.add(small_car_prob);
		userOptionsPanel.add(default_small_car_prob);
		userOptionsPanel.add(motorcycle_prob);
		userOptionsPanel.add(default_motorcycle_prob);
		userOptionsPanel.add(intended_stay_mean);
		userOptionsPanel.add(default_intended_stay_mean);
		userOptionsPanel.add(intended_stay_sd);
		userOptionsPanel.add(default_intended_stay_sd);
		
		
	}
	/**
	 * @param arg0
	 * @throws HeadlessException
	 */
	public GUISimulator(String arg0) throws HeadlessException {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
