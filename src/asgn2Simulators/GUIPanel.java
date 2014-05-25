package asgn2Simulators;

import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import asgn2CarParks.CarPark;
import asgn2Exceptions.SimulationException;
import asgn2Exceptions.VehicleException;

@SuppressWarnings("serial")
public class GUIPanel extends JPanel implements ActionListener{
	
	private CarPark carPark;
	private Simulator sim;
	private Log log;
	private SimulationRunner sr;
	
		
	private JPanel allComponents = new JPanel(new GridLayout());
	private JPanel userOptionsPanel;
	private JPanel bottomButtons;
	private JPanel topHeadings;
	
	//Text Area's
	private JTextArea carParkLogData, carParkSummary;
	private JScrollPane logScroll,summaryScroll;
	//Charts
	
	
	//Buttons
	private JButton run, chart, text, exit;
	
	//Titles
	
	private JLabel title = new JLabel ("Car Park");
	private JLabel userOptions, variables, probabilities, statusCharts,carParkLog,summary;
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
	
	public GUIPanel (){
		
		this.setLayout(new BorderLayout());
		initilizeComponents();
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
		carParkLogData = new JTextArea();
		carParkLogData.setAutoscrolls(true);
		carParkLogData.setBorder(BorderFactory.createEtchedBorder());
		logScroll = new JScrollPane(carParkLogData);
		
		
		//Right print out of final status?
		carParkSummary = new JTextArea();
		carParkSummary.setAutoscrolls(true);
		carParkSummary.setBorder(BorderFactory.createEtchedBorder());
		summaryScroll = new JScrollPane(carParkSummary);
		
		
		//Top Panel with headings
		topHeadings = new JPanel();
		topHeadings.setLayout(new GridLayout());
		
		carParkLog = new JLabel("Car Park Log");
		summary = new JLabel("Car Park Summary");
		
		//Top User Options
		userOptions = new JLabel("User Data Entry Options");
		variables = new JLabel ("Variables");
		default_max_car_spaces = new JTextField();
		default_max_car_spaces.setText(Integer.toString(Constants.DEFAULT_MAX_CAR_SPACES));
		max_car_spaces = new JLabel("Max Car Spaces");
		default_max_small_car_spaces = new JTextField();
		default_max_small_car_spaces.setText(Integer.toString(Constants.DEFAULT_MAX_SMALL_CAR_SPACES));
		max_small_car_spaces = new JLabel ("Max Small Car Spaces");
		default_max_motorcycle_spaces = new JTextField();
		default_max_motorcycle_spaces.setText(Integer.toString(Constants.DEFAULT_MAX_MOTORCYCLE_SPACES));
		max_motorcycle_spaces = new JLabel("Max Motorcycle Spaces");
		default_max_queue_size = new JTextField();
		default_max_queue_size.setText(Integer.toString(Constants.DEFAULT_MAX_QUEUE_SIZE));
		max_queue_size = new JLabel ("Max Queue Size");
		
		//Probability Options
		probabilities = new JLabel ("Probabilities");
		default_seed = new JTextField();
		default_seed.setText(Integer.toString(Constants.DEFAULT_SEED));
		seed = new JLabel("Seed");
		default_car_prob = new JTextField();
		default_car_prob.setText(Double.toString(Constants.DEFAULT_CAR_PROB));
		car_prob = new JLabel ("Car Probibility");
		default_small_car_prob = new JTextField();
		default_small_car_prob.setText(Double.toString(Constants.DEFAULT_SMALL_CAR_PROB));
		small_car_prob = new JLabel("Small Car Probibility");
		default_motorcycle_prob = new JTextField();
		default_motorcycle_prob.setText(Double.toString(Constants.DEFAULT_MOTORCYCLE_PROB));
		motorcycle_prob = new JLabel ("Motorcycle Probibility");
		default_intended_stay_mean = new JTextField();
		default_intended_stay_mean.setText(Double.toString(Constants.DEFAULT_INTENDED_STAY_MEAN));
		intended_stay_mean = new JLabel("Intended Stay Mean");
		default_intended_stay_sd = new JTextField();
		default_intended_stay_sd.setText(Double.toString(Constants.DEFAULT_INTENDED_STAY_SD));
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
		topHeadings.add(carParkLog);
		topHeadings.add(summary);
		
		//Create Buttons
		run = new JButton("Run");
		run.addActionListener(this);
		chart = new JButton("Charts");
		chart.addActionListener(this);
		text = new JButton ("Text");
		text.addActionListener(this);
		exit = new JButton ("Exit");
		exit.addActionListener(this);
		
		//Add Butons to button panel
		bottomButtons.add(run);
		bottomButtons.add(chart);
		bottomButtons.add (text);
		bottomButtons.add(exit);
		
		//Add user options to the main component panel
		//allComponents.add(topHeadings, BorderLayout.PAGE_START);
		allComponents.add(userOptionsPanel);
		allComponents.add(logScroll);
		allComponents.add(summaryScroll);
		//allComponents.add(bottomButtons, BorderLayout.PAGE_END);
		this.add(topHeadings,BorderLayout.PAGE_START);
		this.add(allComponents, BorderLayout.CENTER);
		this.add(bottomButtons, BorderLayout.PAGE_END);
	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		// TODO Auto-generated method stub
		Object src = evt.getSource();
		
		if (src == run){
			try{
				this.carPark = new CarPark();
				this.log = new Log();
				this.sim = new Simulator();
				this.sr = new SimulationRunner (carPark,sim,log);
				sr.runSimulation();
				
				carParkLogData.setText(carPark.finalState());
				carParkSummary.setText("Customers Parked: %d\n"
						+"Customers Dissatisfied: %d\n");
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
		}
		else if (src == chart){
			
		}
		else if (src == text){
			
		}
		else if (src == exit)
			System.exit(0);
	}
	

}
