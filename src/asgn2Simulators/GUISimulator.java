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

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
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
	protected static final int WIDTH = 640;
	protected static final int HEIGHT = 480;
	protected static final Dimension PREFSIZE = new Dimension(WIDTH, HEIGHT);
	
	private JPanel allComponents;
	private JPanel userOptionsPanel;
	private JPanel bottomButtons;
	private JPanel topHeadings;
	
	//Text Area's
	private JTextArea carParkLog, carParkSummary;
	//Charts
	
	//Buttons
	private JButton run, chart, text, exit;
	
	//Titles
	
	private JLabel title = new JLabel ("Car Park");
	private JLabel userOptions, variables, probabilities, statusCharts,log,summary;
	private JLabel blank1 = new JLabel("");
	private JLabel blank2 = new JLabel ("");
	private JLabel blank3 = new JLabel ("");
	private JLabel blank4 = new JLabel ("");
	
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
				allComponents = new JPanel();
				allComponents.setLayout(new BorderLayout());
				initilizeComponents();
				this.getContentPane().add(allComponents);
				repaint();
	}
	
	private void initilizeComponents (){
		
		//Create User Options Panel
		userOptionsPanel = new JPanel();
		userOptionsPanel.setLayout(new GridLayout(15,2));
		userOptionsPanel.setBorder(BorderFactory.createEtchedBorder());
		
		//Create Bottom Buttons
		bottomButtons = new JPanel();
		bottomButtons.setLayout(new FlowLayout());
		
		//Center print out of carpark log
		carParkLog = new JTextArea();
		carParkLog.setAutoscrolls(true);
		carParkLog.setBorder(BorderFactory.createEtchedBorder());
		
		//Right print out of final status?
		carParkSummary = new JTextArea();
		carParkSummary.setAutoscrolls(true);
		carParkSummary.setBorder(BorderFactory.createEtchedBorder());
		
		//Top Panel with headings
		topHeadings = new JPanel();
		topHeadings.setLayout(new FlowLayout());
		
		log = new JLabel("Car Park Log");
		summary = new JLabel("Car Park Summary");
		
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
		
		userOptionsPanel.add(variables);
		userOptionsPanel.add(blank3);
		userOptionsPanel.add(max_car_spaces);
		userOptionsPanel.add(default_max_car_spaces);
		userOptionsPanel.add(max_small_car_spaces);
		userOptionsPanel.add(default_max_small_car_spaces);
		userOptionsPanel.add(max_motorcycle_spaces);
		userOptionsPanel.add(default_max_motorcycle_spaces);
		userOptionsPanel.add(max_queue_size);
		userOptionsPanel.add(default_max_queue_size);
		userOptionsPanel.add(probabilities);
		userOptionsPanel.add(blank2);
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
		
		//Add Headings to the top panel
		topHeadings.add(userOptions);
		topHeadings.add(log);
		topHeadings.add(summary);
		
		//Create Buttons
		run = new JButton("Run");
		//run.addActionListener(this);
		chart = new JButton("Charts");
		text = new JButton ("Text");
		exit = new JButton ("Exit");
		
		//Add Butons to button panel
		bottomButtons.add(run);
		bottomButtons.add(chart);
		bottomButtons.add (text);
		bottomButtons.add(exit);
		
		//Add user options to the main component panel
		allComponents.add(topHeadings, BorderLayout.PAGE_START);
		allComponents.add(userOptionsPanel, BorderLayout.LINE_START);
		allComponents.add(carParkLog, BorderLayout.CENTER);
		allComponents.add(carParkSummary, BorderLayout.LINE_END);
		allComponents.add(bottomButtons, BorderLayout.PAGE_END);
		
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

	/**
	 * Same method as above except this takes an aditional param of
	 * JPanel. This is so the individual grids can place items onto 
	 * a panel so that panel can then be placed onto the main panel's
	 * grid layout.
	 * @param p panel to place component
	 * @param c component to add panel
	 * @param constraints placement within the gridbaglayout
	 * @param x column placement
	 * @param y row placement
	 * @param w grid width
	 * @param h grid height
	 * @author Code due to Cay Horstmann
	 */
	private void addToPanel(JPanel p,Component c, GridBagConstraints constraints, int x,
			int y, int w, int h) {
		constraints.gridx = x;
		constraints.gridy = y;
		constraints.gridwidth = w;
		constraints.gridheight = h;
		p.add(c, constraints);
	}
}
