/**
 * 
 * This file is part of the CarParkSimulator Project, written as 
 * part of the assessment for INB370, semester 1, 2014. 
 *
 * CarParkSimulator
 * asgn2Simulators 
 * 23/04/2014
 * 
 */
package asgn2Simulators;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;

import asgn2CarParks.CarPark;
import asgn2Exceptions.SimulationException;
import asgn2Exceptions.VehicleException;

/**
 * Class to operate the simulation, taking parameters and utility methods from
 * the Simulator to control the CarPark, and using Log to provide a record of
 * operation.
 * 
 * @author hogan
 * 
 */
public class SimulationRunner {
	private CarPark carPark;
	private Simulator sim;

	private static ArrayList<String> chartData = new ArrayList<String>();

	private Log log;

	/**
	 * Constructor just does initialisation
	 * 
	 * @param carPark
	 *            CarPark currently used
	 * @param sim
	 *            Simulator containing simulation parameters
	 * @param log
	 *            Log to provide logging services
	 */
	public SimulationRunner(CarPark carPark, Simulator sim, Log log) {
		this.carPark = carPark;
		this.sim = sim;
		this.log = log;

	}

	/**
	 * Method to run the simulation from start to finish. Exceptions are
	 * propagated upwards from Vehicle, Simulation and Log objects as necessary
	 * 
	 * @throws VehicleException
	 *             if Vehicle creation or operation constraints violated
	 * @throws SimulationException
	 *             if Simulation constraints are violated
	 * @throws IOException
	 *             on logging failures
	 */
	public void runSimulation() throws VehicleException, SimulationException,
			IOException {

		this.log.initialEntry(this.carPark, this.sim);
		for (int time = 0; time <= Constants.CLOSING_TIME; time++) {
			// queue elements exceed max waiting time
			if (!this.carPark.queueEmpty()) {
				this.carPark.archiveQueueFailures(time);
			}
			// vehicles whose time has expired
			if (!this.carPark.carParkEmpty()) {
				// force exit at closing time, otherwise normal
				boolean force = (time == Constants.CLOSING_TIME);
				this.carPark.archiveDepartingVehicles(time, force);
			}
			// attempt to clear the queue
			if (!this.carPark.carParkFull()) {
				this.carPark.processQueue(time, this.sim);
			}
			// new vehicles from minute 1 until the last hour
			if (newVehiclesAllowed(time)) {
				this.carPark.tryProcessNewVehicles(time, this.sim);
			}
			// Log progress
			this.log.logEntry(time, this.carPark);

			chartData.add(carPark.getStatus(time));
			// System.out.printf("%s\n",carPark.getStatus(time));
		}
		this.log.finalise(this.carPark);

	}

	/**
	 * Main program for the simulation
	 * 
	 * @param args
	 *            Arguments to the simulation
	 */
	public static void main(String[] args) {

		GUISimulator frame;

		// Arguments are all or nothing
		if (args.length == 10) {

			try {
				int maxCarSpaces = Integer.parseInt(args[0]);
				int maxSmallSpaces = Integer.parseInt(args[1]);
				int maxBikeSpaces = Integer.parseInt(args[2]);
				int maxQueue = Integer.parseInt(args[3]);
				int seed = Integer.parseInt(args[4]);
				double carProb = Double.parseDouble(args[5]);
				double smallCarProb = Double.parseDouble(args[6]);
				double bikeProb = Double.parseDouble(args[7]);
				double stayMean = Double.parseDouble(args[8]);
				double staySD = Double.parseDouble(args[9]);

				frame = new GUISimulator(maxCarSpaces, maxSmallSpaces,
						maxBikeSpaces, maxQueue, seed, carProb, smallCarProb,
						bikeProb, stayMean, staySD);
				frame.gatherChartData(chartData);

				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);

			} catch (Exception e) {
				System.out.printf(
						"There were one or more incorrect entries entered on"
								+ " the command line.\n%s\n", e);
			}
		} else {
			frame = new GUISimulator();
			frame.gatherChartData(chartData);

			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setVisible(true);
		}

	}

	/**
	 * Helper method to determine if new vehicles are permitted
	 * 
	 * @param time
	 *            int holding current simulation time
	 * @return true if new vehicles permitted, false if not allowed due to
	 *         simulation constraints.
	 */
	private boolean newVehiclesAllowed(int time) {
		boolean allowed = (time >= 1);
		return allowed && (time <= (Constants.CLOSING_TIME - 60));
	}

}
