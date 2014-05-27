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

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.*;

import org.jfree.ui.RefineryUtilities;

import asgn2CarParks.CarPark;
import asgn2Exceptions.SimulationException;
import asgn2Exceptions.VehicleException;
	
/**
 * @author hogan
 * @author Ken Lewis
 * @author Thomas McCarthy
 */
@SuppressWarnings("serial")
public class GUISimulator extends JFrame implements Runnable {
	

	//Objects to help the GUI run the Simulation
	private CarPark carPark;
	private Simulator sim;
	private Log log;
	private SimulationRunner sr;
	
	private ArrayList<String> chartData = new ArrayList<String>();
	
	private int maxCarSpaces,maxSmallSpaces, maxBikeSpaces, maxQueue, seed;
	private double carProb,smallCarProb,bikeProb,stayMean, staySD;
	
	
	//4 Panels which are placed on the GUIPANEL (this.)	
	private JPanel allComponents = new JPanel(new GridLayout());
	private JPanel userOptionsPanel;
	private JPanel bottomButtons;
	private JPanel topHeadings;
	
	//Text Area's
	private JTextArea carParkLogData, carParkSummary;
	private JScrollPane logScroll,summaryScroll;
	//Charts
	
	
	//Buttons
	private JButton run, timeChart, barChart, exit;
	
	//Titles and blanks (blanks were used to help balance layout)
	private JLabel userOptions, variables, probabilities,carParkLog,summary;
	private JLabel blank2 = new JLabel ("");
	private JLabel blank3 = new JLabel ("");
	
	//All Fields and Labels for the User Options area
	private JTextField default_max_car_spaces, default_max_small_car_spaces,
						default_max_motorcycle_spaces, default_max_queue_size;
	private JLabel max_car_spaces, max_small_car_spaces, max_motorcycle_spaces,
					max_queue_size;

	private JTextField default_seed, default_car_prob, default_small_car_prob,
						default_motorcycle_prob, default_intended_stay_mean,
						default_intended_stay_sd;
	private JLabel probabilitySeed, car_prob, small_car_prob, motorcycle_prob, intended_stay_mean,
						intended_stay_sd;
	
	
	
	//Window Size
	protected static final int WIDTH = 1080;
	protected static final int HEIGHT = 480;
	protected static final Dimension PREFSIZE = new Dimension(WIDTH, HEIGHT);
	private JPanel theGUI = new JPanel();
	
	
	public GUISimulator (){
		// Initialize the Frame and add the GamePanel
		this.setTitle("Car Park Application");
		setSize(PREFSIZE);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		//Set CarPark spaces to constant variables for initial display if
		//no args are entered
		this.maxCarSpaces = Constants.DEFAULT_MAX_CAR_SPACES;
		this.maxSmallSpaces = Constants.DEFAULT_MAX_SMALL_CAR_SPACES;
		this.maxBikeSpaces = Constants.DEFAULT_MAX_MOTORCYCLE_SPACES;
		this.maxQueue = Constants.DEFAULT_MAX_QUEUE_SIZE;
		
		//Set Probability variables to constant variables for initial display
		//if no args are entered.
		this.seed = Constants.DEFAULT_SEED;
		this.carProb = Constants.DEFAULT_CAR_PROB;
		this.smallCarProb = Constants.DEFAULT_SMALL_CAR_PROB;
		this.bikeProb = Constants.DEFAULT_MOTORCYCLE_PROB;
		this.stayMean = Constants.DEFAULT_INTENDED_STAY_MEAN;
		this.staySD = Constants.DEFAULT_INTENDED_STAY_SD;
		theGUI.setLayout(new BorderLayout());
		initilizeComponents();
		repaint();

	}
	
	
	public GUISimulator (int maxCarSpaces, int maxSmallSpaces, int maxBikeSpaces, int maxQueue, int seed,
			double carProb, double smallCarProb, double bikeProb, double stayMean, double staySD){
		this.setTitle("Car Park Application");
		setSize(PREFSIZE);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);;
		
		this.maxCarSpaces = maxCarSpaces;
		this.maxSmallSpaces = maxSmallSpaces;
		this.maxBikeSpaces = maxBikeSpaces;
		this.maxQueue = maxQueue;
		this.seed = seed;
		this.carProb = carProb;
		this.smallCarProb = smallCarProb;
		this.bikeProb = bikeProb;
		this.stayMean = stayMean;
		this.staySD = staySD;
		
		theGUI.setLayout(new BorderLayout());
		initilizeComponents();
		repaint();

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
	
	public void run(){
		// TODO Auto-generated method stub

	}
	/**
	 * GatherArgs is designed to be called in SimulationRunner if args are detected
	 * on the command line. If they are this method is called and the variables which
	 * have initially been set to the constants will be changed to the new variables
	 * Inputed in the command line.
	 */
	
	public void gatherArgs(int maxCarSpaces, int maxSmallSpaces, int maxBikeSpaces, int maxQueue,
			int seed, double carProb, double smallCarProb, double bikeProb, double stayMean,
			double staySD) throws SimulationException{
		
			this.maxCarSpaces = maxCarSpaces;
			this.maxSmallSpaces = maxSmallSpaces;
			this.maxBikeSpaces = maxBikeSpaces;
			this.maxQueue = maxQueue;
			this.seed = seed;
			this.carProb = carProb;
			this.smallCarProb = smallCarProb;
			this.bikeProb = bikeProb;
			this.stayMean = stayMean;
			this.staySD = staySD;
		
	}
	
	/**
	 * Method to gather the variables which the user has entered/changed
	 * from the beginning of the gui being written. Variables are taken from the 
	 * UserOptions Panel from the various positions and converted to be passed 
	 * on.
	 */
	private void gatherUserEnteredVariables() throws SimulationException{
		String errorMsg = "All fields are required!";
		
		try{
			this.maxCarSpaces = Integer.parseInt(default_max_car_spaces.getText());
			this.maxSmallSpaces = Integer.parseInt(default_max_small_car_spaces.getText());
			this.maxBikeSpaces = Integer.parseInt(default_max_motorcycle_spaces.getText());
			this.maxQueue = Integer.parseInt(default_max_queue_size.getText());
			
			this.seed = Integer.parseInt(default_seed.getText());
			this.carProb = Double.parseDouble(default_car_prob.getText());
			this.smallCarProb = Double.parseDouble(default_small_car_prob.getText());
			this.bikeProb = Double.parseDouble(default_motorcycle_prob.getText());
			this.stayMean = Double.parseDouble(default_intended_stay_mean.getText());
			this.staySD = Double.parseDouble(default_intended_stay_sd.getText());
			
			if (maxSmallSpaces > maxCarSpaces) {
				errorMsg = "Maximum number of small car spaces cannot exceed maximum total spaces.";
				throw new NumberFormatException(errorMsg);
			}
		}
		
		// We're using NumberFormatException specifically, since that is
		// the exception thrown whenever fields are blank or invalid numbers.
		// Saves us a lot of manual work.
		catch (NumberFormatException nfe){

			JOptionPane.showMessageDialog(this,
			errorMsg,"Incorrect Entry",
			JOptionPane.ERROR_MESSAGE);
			throw new SimulationException(errorMsg);
		}
		
	}
	
	public void gatherChartData(ArrayList<String> chartData){
		
		this.chartData = chartData;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Cute feature that allows the user to run the simulation
		// using GUISimulator as well
		SimulationRunner.main(args);
		
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
		carParkLogData = new JTextArea();
		carParkLogData.setAutoscrolls(true);
		carParkLogData.setBorder(BorderFactory.createEtchedBorder());
		logScroll = new JScrollPane(carParkLogData);
		
		
		//Right print out of final status?
		carParkSummary = new JTextArea();
		carParkSummary.setAutoscrolls(true);
		carParkSummary.setBorder(BorderFactory.createEtchedBorder());
		carParkSummary.setLineWrap(true);
		summaryScroll = new JScrollPane(carParkSummary);
		
		
		//Top Panel with headings
		topHeadings = new JPanel();
		topHeadings.setLayout(new GridLayout());
		
		carParkLog = new JLabel("Car Park Log");
		summary = new JLabel("Car Park Summary");
		
		//Top User Options VARIABLES
		userOptions = new JLabel("User Data Entry Options");
		variables = new JLabel ("Variables");
		default_max_car_spaces = new JTextField();
		default_max_car_spaces.setText(Integer.toString(maxCarSpaces));
		max_car_spaces = new JLabel("Max Car Spaces");
		default_max_small_car_spaces = new JTextField();
		default_max_small_car_spaces.setText(Integer.toString(maxSmallSpaces));
		max_small_car_spaces = new JLabel ("Max Small Car Spaces");
		default_max_motorcycle_spaces = new JTextField();
		default_max_motorcycle_spaces.setText(Integer.toString(maxBikeSpaces));
		max_motorcycle_spaces = new JLabel("Max Motorcycle Spaces");
		default_max_queue_size = new JTextField();
		default_max_queue_size.setText(Integer.toString(maxQueue));
		max_queue_size = new JLabel ("Max Queue Size");
		
		//User Options PROBABILITY
		probabilities = new JLabel ("Probabilities");
		default_seed = new JTextField();
		default_seed.setText(Integer.toString(seed));
		probabilitySeed = new JLabel("Seed");
		default_car_prob = new JTextField();
		default_car_prob.setText(Double.toString(carProb));
		car_prob = new JLabel ("Car Probibility");
		default_small_car_prob = new JTextField();
		default_small_car_prob.setText(Double.toString(smallCarProb));
		small_car_prob = new JLabel("Small Car Probibility");
		default_motorcycle_prob = new JTextField();
		default_motorcycle_prob.setText(Double.toString(bikeProb));
		motorcycle_prob = new JLabel ("Motorcycle Probibility");
		default_intended_stay_mean = new JTextField();
		default_intended_stay_mean.setText(Double.toString(stayMean));
		intended_stay_mean = new JLabel("Intended Stay Mean");
		default_intended_stay_sd = new JTextField();
		default_intended_stay_sd.setText(Double.toString(staySD));
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
		userOptionsPanel.add(probabilitySeed);
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
		topHeadings.add(carParkLog);
		topHeadings.add(summary);
		
		//Create Buttons with ActionListeners
		run = new JButton("Run");
		run.addActionListener(new ActionListener() {

	        public void actionPerformed(ActionEvent event) {

				try{
					
					gatherUserEnteredVariables();
					
					//Need to create brand new variables so SimulationRunner
					//has new data each time.
					carPark = new CarPark(maxCarSpaces,maxSmallSpaces,maxBikeSpaces,maxQueue);
					log = new Log();
					sim = new Simulator(seed,stayMean,staySD,carProb,smallCarProb,bikeProb);
					sr = new SimulationRunner (carPark,sim,log);
					chartData.clear();
					sr.runSimulation();
					
					
					timeChart.setEnabled(true);
					barChart.setEnabled(true);
					
					fillGUIText(chartData);
				}
				catch (IOException ioe){
					System.out.printf("There was a problem running the program.\n" +
							"Details are:%s\n", ioe);
				}
				catch (SimulationException se){
					System.out.printf("There was a problem running the program.\n" +
							"Details are: %s\n", se);
				}
				catch (VehicleException ve){
					System.out.printf("There was a problem running the program.\n" +
							"Details are: %s\n", ve);
				}
				
				catch (NumberFormatException nfe){
					System.out.printf("There was a problem running the program.\n" +
							"Details are: %s\n", nfe);
				}
				
	        }
		});
		timeChart = new JButton("Time Series Chart");
		timeChart.setEnabled(false);
		timeChart.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent event) {
                ChartPanel chartWindow = new ChartPanel("Time Series Chart", chartData, false);
                
                chartWindow.getChartDialog().pack();
                RefineryUtilities.centerFrameOnScreen(chartWindow);
                chartWindow.getChartDialog().repaint();
                chartWindow.getChartDialog().setVisible(true);
	        }
		});
		
		barChart = new JButton ("Bar Chart Summary");
		barChart.setEnabled(false);
		barChart.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent event) {
                ChartPanel chartWindow = new ChartPanel("Bar Chart Summary", chartData, true);
                
                chartWindow.getChartDialog().pack();
                RefineryUtilities.centerFrameOnScreen(chartWindow);
                chartWindow.getChartDialog().repaint();
                chartWindow.getChartDialog().setVisible(true);
	        }
		});
		exit = new JButton ("Exit");
		exit.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent event) {
				System.exit(0);
	        }
		});
		
		//Add Buttons to button panel
		bottomButtons.add(run);
		bottomButtons.add(timeChart);
		bottomButtons.add (barChart);
		bottomButtons.add(exit);
		
		//Adds the three main panels to a central panel to help display
		allComponents.add(userOptionsPanel);
		allComponents.add(logScroll);
		allComponents.add(summaryScroll);
		
		//Add the final 2 panels plus the central panel to the GUIPANEL
		this.add(topHeadings,BorderLayout.PAGE_START);
		this.add(allComponents, BorderLayout.CENTER);
		this.add(bottomButtons, BorderLayout.PAGE_END);
	}
	/***
	 * Helper method that populates the GUI fields
	 * a big bunch of CarPark statuses
	 * @param statusData
	 */
	private void fillGUIText(ArrayList<String> statusData) {
		String data ="";
		for(int i =0; i < statusData.size(); i++){
			data += statusData.get(i) + "";
		}
		carParkLogData.setText(data);
		carParkSummary.setText("CarPark simulation completed. Here's the final summary:\n\n" + carPark.toString());
	}

}
