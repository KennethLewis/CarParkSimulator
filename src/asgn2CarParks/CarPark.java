/**
 * 
 * This file is part of the CarParkSimulator Project, written as 
 * part of the assessment for INB370, semester 1, 2014. 
 *
 * CarParkSimulator
 * asgn2CarParks 
 * 21/04/2014
 * 
 */
package asgn2CarParks;

import java.util.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

import asgn2Exceptions.SimulationException;
import asgn2Exceptions.VehicleException;
import asgn2Simulators.Constants;
import asgn2Simulators.Simulator;
import asgn2Vehicles.Car;
import asgn2Vehicles.MotorCycle;
import asgn2Vehicles.Vehicle;

/**
 * The CarPark class provides a range of facilities for working with a car park in support 
 * of the simulator. In particular, it maintains a collection of currently parked vehicles, 
 * a queue of vehicles wishing to enter the car park, and an historical list of vehicles which 
 * have left or were never able to gain entry. 
 * 
 * The class maintains a wide variety of constraints on small cars, normal cars and motorcycles 
 * and their access to the car park. See the method javadoc for details. 
 * 
 * The class relies heavily on the asgn2.Vehicle hierarchy, and provides a series of reports 
 * used by the logger. 
 * 
 * @author hogan
 *
 */
public class CarPark {

	private List<Vehicle> spaces;
	private int maxCarSpaces;
	private int maxSmallCarSpaces;
	private int maxMotorCycleSpaces;
	private int maxQueueSize;
	
	//Stores variables
	private List<Vehicle> queue;
	private List<Vehicle> carParkEntries;
	private List<Vehicle> past;
	private int count;
	private int numCars;
	private int numSmallCars;
	private int numMotorCycles;
	private int numDissatisfied;
	private String status;
	/* * CarPark constructor sets the basic size parameters. 
	 * Uses default parameters
	 */
	public CarPark() {
		this(Constants.DEFAULT_MAX_CAR_SPACES,Constants.DEFAULT_MAX_SMALL_CAR_SPACES,
				Constants.DEFAULT_MAX_MOTORCYCLE_SPACES,Constants.DEFAULT_MAX_QUEUE_SIZE);
		
		spaces = new ArrayList<Vehicle>(maxCarSpaces);
		
		this.queue = new ArrayList<Vehicle>();
		this.past = new ArrayList<Vehicle>();
		this.carParkEntries = new ArrayList<Vehicle>();
		this.count = 0;
		this.numCars = 0;
		this.numSmallCars = 0;
		this.numMotorCycles = 0;
		this.numDissatisfied = 0;
	}
	
	/**
	 * CarPark constructor sets the basic size parameters. 
	 * @param maxCarSpaces maximum number of spaces allocated to cars in the car park 
	 * @param maxSmallCarSpaces maximum number of spaces (a component of maxCarSpaces) 
	 * 						 restricted to small cars
	 * @param maxMotorCycleSpaces maximum number of spaces allocated to MotorCycles
	 * @param maxQueueSize maximum number of vehicles allowed to queue
	 */
	public CarPark(int maxCarSpaces,int maxSmallCarSpaces, int maxMotorCycleSpaces, int maxQueueSize) {
		spaces = new ArrayList<Vehicle>(maxCarSpaces);
		
		this.maxCarSpaces = maxCarSpaces;
		this.maxSmallCarSpaces = maxSmallCarSpaces;
		this.maxMotorCycleSpaces = maxMotorCycleSpaces;
		this.maxQueueSize = maxQueueSize;
		
		this.queue = new ArrayList<Vehicle>();
		this.past = new ArrayList<Vehicle>();
		this.count = 0;
		this.numCars = 0;
		this.numSmallCars = 0;
		this.numMotorCycles = 0;
		this.numDissatisfied = 0;
	}

	/**
	 * Archives vehicles exiting the car park after a successful stay. Includes transition via 
	 * Vehicle.exitParkedState(). 
	 * @param time int holding time at which vehicle leaves
	 * @param force boolean forcing departure to clear car park 
	 * @throws VehicleException if vehicle to be archived is not in the correct state 
	 * @throws SimulationException if one or more departing vehicles are not in the car park when operation applied
	 * @author Ken Lewis
	 */
	public void archiveDepartingVehicles(int time,boolean force) throws VehicleException, SimulationException {
		
		for (int i = 0; i < spaces.size(); i++){
			//Check to make sure vehicle is parked, throw exception if not
			if(spaces.get(i).isParked() == false)
				throw new SimulationException("One or more departing Vehicles are" +
						" currently not in the car park. Archive Departing Vehicles failure.\n");
			else if (time == spaces.get(i).getDepartureTime()) {
				spaces.get(i).exitParkedState(time);
				outgoingVehicleMonitor(spaces.get(i));//changing carpark numbers making sure
				  									 //it ends up empty
				past.add(spaces.get(i));
				spaces.remove(i);
				status += setVehicleMsg(spaces.get(i), "P", "A");
				
			}
			else if (force == true){ //Forces cars to leave at the end of the day. Removes
									//all cars from the CarPark and archives them.
				spaces.get(i).exitParkedState(time);
				outgoingVehicleMonitor(spaces.get(i));
				past.add(spaces.get(i));
				spaces.remove(i);
			}
		}
	}
		
	/**
	 * Method to archive new vehicles that don't get parked or queued and are turned 
	 * away
	 * @param v Vehicle to be archived
	 * @throws SimulationException if vehicle is currently queued or parked
	 */
	public void archiveNewVehicle(Vehicle v) throws SimulationException {
		
		if (v.isParked() == true || v.isQueued() == true) {
			throw new SimulationException("Vehicle is currently queued or parked.\n" +
					"Vehicle is expected to be turned away. ArchiveNewVehicle failure\n");
		}
		past.add(v); //adds vehicle to the past list
		numDissatisfied++;
			
	}
	
	/**
	 * Archive vehicles which have stayed in the queue too long 
	 * @param time int holding current simulation time 
	 * @author Thomas McCarthy
	 * @author Ken Lewis
	 * @throws VehicleException if one or more vehicles not in the correct state or if timing constraints are violated
	 */
	public void archiveQueueFailures(int time) throws VehicleException {
		
		for (int i = 0; i < queue.size(); i++){
			
			int timeInQueue = time - queue.get(i).getArrivalTime();
			
			if (timeInQueue >= Constants.MAXIMUM_QUEUE_TIME){
				Vehicle vehicleToRemove = queue.get(i);
				
				past.add(vehicleToRemove);
				queue.remove(i);
				vehicleToRemove.exitQueuedState(time);
				numDissatisfied++;
			}
				
		}
	}
	
	/**
	 * Simple status showing whether carPark is empty
	 * @return true if car park empty, false otherwise
	 * @author Ken Lewis
	 */
	public boolean carParkEmpty() {
		
		if (spaces.size() == 0)
			return true;
		else
			return false;
		
	}
	
	/**
	 * Simple status showing whether carPark is full
	 * @return true if car park full, false otherwise
	 * @author Ken Lewis
	 */
	public boolean carParkFull() {
		
		int totalSpaces = this.maxCarSpaces + this.maxMotorCycleSpaces +
				this.maxSmallCarSpaces;
		if (totalSpaces <= spaces.size())
			return true;
		else
			return false;
	}
	
	/**
	 * Method to add vehicle successfully to the queue
	 * Precondition is a test that spaces are available
	 * Includes transition through Vehicle.enterQueuedState 
	 * @param v Vehicle to be added 
	 * @throws SimulationException if queue is full  
	 * @throws VehicleException if vehicle not in the correct state 
	 * @author Ken Lewis
	 */
	public void enterQueue(Vehicle v) throws SimulationException, VehicleException {
		
		if(queue.size() != maxQueueSize){
			v.enterQueuedState();
			queue.add(v);
			status += setVehicleMsg(v, "N", "Q");
		}
		else {
			//Time passed through to archiveQueue method should be the current time
			//as the car arrival time will be the current time.
			archiveQueueFailures(v.getArrivalTime());
			throw new SimulationException ("Car cannot enter queue as the queue" +
					" is currently full.\nCar turned away and Arvhived.\n");
		}			
	}
	
	
	/**
	 * Method to remove vehicle from the queue after which it will be parked or 
	 * removed altogether. Includes transition through Vehicle.exitQueuedState.  
	 * @param v Vehicle to be removed from the queue 
	 * @param exitTime int time at which vehicle exits queue
	 * @throws SimulationException if vehicle is not in queue 
	 * @throws VehicleException if the vehicle is in an incorrect state or timing 
	 * constraints are violated
	 */
	public void exitQueue(Vehicle v,int exitTime) throws SimulationException, VehicleException {
		
		int queuedTime =  exitTime - v.getArrivalTime();
		
		if(v.isQueued() == false)
			throw new SimulationException("Vehicle is currently not in the CarPark Queue!\n" + v.toString());
		else if (queuedTime > Constants.MAXIMUM_QUEUE_TIME){
			//If the vehicle has been in queue for too long.
			past.add(v);
			queue.remove(v);
			v.exitQueuedState(exitTime);
			numDissatisfied++;
		}
		else {
			v.exitQueuedState(exitTime);
			carParkEntries.add(v);
			incomingVehicleMonitor(v);

		}
			
					
			
	}
	
	/**
	 * State dump intended for use in logging the final state of the carpark
	 * All spaces and queue positions should be empty and so we dump the archive
	 * @return String containing dump of final carpark state 
	 */
	public String finalState() {
		String str = "Vehicles Processed: count:" + 
				this.count + ", logged: " + this.past.size() 
				+ "\nVehicle Record: \n";
		for (Vehicle v : this.past) {
			str += v.toString() + "\n\n";
		}
		return str + "\n";
	}
	
	/**
	 * Simple getter for number of cars in the car park 
	 * @return number of cars in car park, including small cars
	 * @author Ken Lewis
	 */
	public int getNumCars() {
		
		int carCounter = 0;
		
		for (int i =0; i < spaces.size(); i++)
			if (spaces.get(i) instanceof Car)
				carCounter++;
		
		return carCounter;
	}
	
	/**
	 * Simple getter for number of motorcycles in the car park 
	 * @return number of MotorCycles in car park, including those occupying 
	 * 			a small car space
	 * @author Ken Lewis
	 */
	public int getNumMotorCycles() {
		
		int bikeCounter = 0;
		
		for(int i =0; i < spaces.size();i++)
			if(spaces.get(i) instanceof MotorCycle)
				bikeCounter++;
		
		return bikeCounter;
				
	}
	
	/**
	 * Simple getter for number of small cars in the car park 
	 * @return number of small cars in car park, including those 
	 * 		   not occupying a small car space. 
	 * @author Ken Lewis
	 */
	public int getNumSmallCars() {
		
		int smallCounter = 0;
		
		for(int i =0; i < spaces.size(); i++ ){
			if(spaces.get(i) instanceof Car){
				Car newCar = (Car)spaces.get(i);
				if (newCar.isSmall() == true)
					smallCounter ++;
			}
		}
		return smallCounter;
	}
	
	/**
	 * Method used to provide the current status of the car park. 
	 * Uses private status String set whenever a transition occurs. 
	 * Example follows (using high probability for car creation). At time 262, 
	 * we have 276 vehicles existing, 91 in car park (P), 84 cars in car park (C), 
	 * of which 14 are small (S), 7 MotorCycles in car park (M), 48 dissatisfied (D),
	 * 176 archived (A), queue of size 9 (CCCCCCCCC), and on this iteration we have 
	 * seen: car C go from Parked (P) to Archived (A), C go from queued (Q) to Parked (P),
	 * and small car S arrive (new N) and go straight into the car park<br>
	 * 262::276::P:91::C:84::S:14::M:7::D:48::A:176::Q:9CCCCCCCCC|C:P>A||C:Q>P||S:N>P|
	 * @return String containing current state 
	 */
	public String getStatus(int time) {
		String str = time +"::"
		+ this.count + "::" 
		+ "P:" + this.spaces.size() + "::"
		+ "C:" + this.numCars + "::S:" + this.numSmallCars 
		+ "::M:" + this.numMotorCycles 
		+ "::D:" + this.numDissatisfied 
		+ "::A:" + this.past.size()  
		+ "::Q:" + this.queue.size(); 
		for (Vehicle v : this.queue) {
			if (v instanceof Car) {
				if (((Car)v).isSmall()) {
					str += "S";
				} else {
					str += "C";
				}
			} else {
				str += "M";
			}
		}
		str += this.status;
		this.status="";
		return str+"\n";
	}
	

	/**
	 * State dump intended for use in logging the initial state of the carpark.
	 * Mainly concerned with parameters. 
	 * @return String containing dump of initial carpark state 
	 */
	public String initialState() {
		return "CarPark [maxCarSpaces: " + this.maxCarSpaces
				+ " maxSmallCarSpaces: " + this.maxSmallCarSpaces 
				+ " maxMotorCycleSpaces: " + this.maxMotorCycleSpaces 
				+ " maxQueueSize: " + this.maxQueueSize + "]";
	}

	/**
	 * Simple status showing number of vehicles in the queue 
	 * @return number of vehicles in the queue
	 * @author Ken Lewis
	 */
	public int numVehiclesInQueue() {
		
		return queue.size();
	}
	
	/**
	 * Method to add vehicle successfully to the car park store. 
	 * Precondition is a test that spaces are available. 
	 * Includes transition via Vehicle.enterParkedState.
	 * @param v Vehicle to be added 
	 * @param time int holding current simulation time
	 * @param intendedDuration int holding intended duration of stay 
	 * @throws SimulationException if no suitable spaces are available for parking 
	 * @throws VehicleException if vehicle not in the correct state or timing constraints are violated
	 * @author Thomas McCarthy
	 * @author Ken Lewis
	 */
	public void parkVehicle(Vehicle v, int time, int intendedDuration) throws SimulationException, VehicleException {

		if(v instanceof Car && ((Car) v).isSmall() == false && spacesAvailable(v) == true) {
			v.enterParkedState(time, intendedDuration);
			spaces.add(v);
			incomingVehicleMonitor(v);
		}
		else if (v instanceof Car && ((Car) v).isSmall() == true && spacesAvailable(v) == true){
			v.enterParkedState(time, intendedDuration);
			spaces.add(v);
			incomingVehicleMonitor(v);
		}
		else if (v instanceof MotorCycle && spacesAvailable(v) == true){
			v.enterParkedState(time, intendedDuration);
			spaces.add(v);
			incomingVehicleMonitor(v);
		}
		else
			throw new SimulationException("Vehicle cannot park, as there are no available"+
					" spaces for that particular vehicle!\n");
	}

	/**
	 * Silently process elements in the queue, whether empty or not. If possible, add them to the car park. 
	 * Includes transition via exitQueuedState where appropriate
	 * Block when we reach the first element that can't be parked. 
	 * @param time int holding current simulation time 
	 * @throws SimulationException if no suitable spaces available when parking attempted
	 * @throws VehicleException if state is incorrect, or timing constraints are violated
	 * 
	 */
	public void processQueue(int time, Simulator sim) throws VehicleException, SimulationException {

		if(this.queueEmpty() == false){
			
			for (int i = 0; i < this.queue.size(); i++) {
					if (spacesAvailable(this.queue.get(i))) {

					Vehicle v = this.queue.get(i);
					this.exitQueue(this.queue.get(i), time);
					this.parkVehicle(v, time, sim.setDuration());
					incomingVehicleMonitor(v);
					status += setVehicleMsg(v, "Q", "P");
					} else {
						break;
					}
					

				}
			}
			// We're removing the queue elements now, since we can't do it mid-iteration
			this.queue.removeAll(carParkEntries);
			
			this.carParkEntries = new ArrayList<Vehicle>();

		}

	/**
	 * Simple status showing whether queue is empty
	 * @return true if queue empty, false otherwise
	 * @author Ken Lewis
	 */
	public boolean queueEmpty() {
		
		if (queue.size() == 0)
			return true;
		else
			return false;
		
	}

	/**
	 * Simple status showing whether queue is full
	 * @return true if queue full, false otherwise
	 * @author Ken Lewis
	 */
	public boolean queueFull() {
		
		if(queue.size() < Constants.DEFAULT_MAX_QUEUE_SIZE)
			return false;
		else
			return true;
	}
	
	/**
	 * Method determines, given a vehicle of a particular type, whether there are spaces available for that 
	 * type in the car park under the parking policy in the class header.  
	 * @param v Vehicle to be stored. 
	 * @return true if space available for v, false otherwise 
	 * @author Thomas McCarthy
	 */
	public boolean spacesAvailable(Vehicle v) {
		
		boolean motorCycleFull = false;
		boolean smallCarFull = false;
		boolean normalCarFull = false;
		// Math.max used to convert any negatives into 0s (negatives occurring when there are
		// still spaces left)
		
		//  TODO: There is a bug here. Sometimes the carpark goes over its limits slightly.
		// I think the issue is that Math.max converts to 0 if the value is negative (we need this to make overflow equations easier).
		// However, there is no way to tell between a converted 0 (i.e. negative overly, meaning some empty spaces left) and a 'true' 0 (i.e. no spaces left)
		int motorCycleOverflow =  this.getNumMotorCycles() - this.maxMotorCycleSpaces;
		int smallCarOverflow = this.getNumSmallCars() + motorCycleOverflow - this.maxSmallCarSpaces;
		
		if (motorCycleOverflow >= 0) {
			motorCycleFull = true;
		}
		
		if (smallCarOverflow >= 0) {
			smallCarFull = true;
		}
		

		motorCycleOverflow = Math.max(0, motorCycleOverflow);
		smallCarOverflow = Math.max(0, smallCarOverflow);
		
		if (this.getNumCars() >= (this.maxCarSpaces - smallCarOverflow)) {
			normalCarFull = true;
		}
		
		
		
		if (this.carParkFull()) {
			return false;
		} else if (motorCycleFull && smallCarFull && v instanceof MotorCycle) {
			return false;
		} else if (smallCarFull && normalCarFull && v instanceof Car && ((Car) v).isSmall() == true ) {
			return false;
		} else if (normalCarFull && v instanceof Car && ((Car) v).isSmall() == false) {
			return false;
		} else {
			return true;
		}
		

	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CarPark [count: " + count
				+ "\nnumCars: " + numCars
				+ "\nnumSmallCars: " + numSmallCars
				+ "\nnumMotorCycles: " + numMotorCycles
				+ "\nqueue: " + (queue.size())
				+ "\nnumDissatisfied: " + numDissatisfied
				+ "\npast: " + past.size() + "]";
	}

	/**
	 * Method to try to create new vehicles (one trial per vehicle type per time point) 
	 * and to then try to park or queue (or archive) any vehicles that are created 
	 * @param sim Simulation object controlling vehicle creation 
	 * @throws SimulationException if no suitable spaces available when operation attempted 
	 * @throws VehicleException if vehicle creation violates constraints 
	 */
	public void tryProcessNewVehicles(int time,Simulator sim) throws VehicleException, SimulationException {
		
		Vehicle newCar;
		MotorCycle newBike;
		List<Vehicle> newVehicles = new ArrayList<Vehicle>();
		
		if (sim.newCarTrial()) {
			newCar = new Car("Asdasd", time, sim.smallCarTrial());
			newVehicles.add(newCar);
			handleNewVehicle(newCar, time, sim);
			this.count++;
		}
		
		if (sim.motorCycleTrial()) {
			newBike = new MotorCycle("Asdasd", time);
			newVehicles.add(newBike);
			handleNewVehicle(newBike, time, sim);
			this.count++;
		}
	}

	
	private void handleNewVehicle(Vehicle v, int time, Simulator sim) throws SimulationException, VehicleException {
		if (this.queueFull()) {
			this.archiveNewVehicle(v);
		} else if (this.spacesAvailable(v)) {
			this.parkVehicle(v, time, sim.setDuration());
		} else {
			this.enterQueue(v);

		}
	}
	/**
	 * Method to remove vehicle from the carpark. 
	 * For symmetry with parkVehicle, include transition via Vehicle.exitParkedState.  
	 * So vehicle should be in parked state prior to entry to this method. 
	 * @param v Vehicle to be removed from the car park 
	 * @throws VehicleException if Vehicle is not parked, is in a queue, or violates timing constraints 
	 * @author Ken Lewis
	 * @author Thomas McCarthy
	 */
	public void unparkVehicle(Vehicle v,int departureTime) throws VehicleException, SimulationException {
		
		if (!spaces.contains(v)) {
			throw new SimulationException("Cannot unpark vehicle as it is not in the carpark");
		}
		v.exitParkedState(departureTime);
		spaces.remove(v);
		outgoingVehicleMonitor(v);
	}
	
	/**
	 * Helper to set vehicle message for transitions 
	 * @param v Vehicle making a transition (uses S,C,M)
	 * @param source String holding starting state of vehicle (N,Q,P,A) 
	 * @param target String holding finishing state of vehicle (Q,P,A) 
	 * @return String containing transition in the form: |(S|C|M):(N|Q|P|A)>(Q|P|A)| 
	 */
	private String setVehicleMsg(Vehicle v,String source, String target) {
		String str="";
		if (v instanceof Car) {
			if (((Car)v).isSmall()) {
				str+="S";
			} else {
				str+="C";
			}
		} else {
			str += "M";
		}
		return "|"+str+":"+source+">"+target+"|";
	}
	/**
	 * Helper to set vehicle message for transitions 
	 * @param v Vehicle helping to make sure what addition to
	 * 			the numbers is being required.
	 * @author Ken Lewis
	 */
	private void incomingVehicleMonitor(Vehicle v){
	 ///this.count++;
	 if (v instanceof MotorCycle) {
		this.numMotorCycles++;
	 } else if (v instanceof Car && ((Car) v).isSmall() == true) {
		this.numSmallCars++;
		this.numCars++;
	 } else if (v instanceof Car && ((Car) v).isSmall() == false) {
		this.numCars++;
	}

	 
	}
	
	/**
	 * Helper to set vehicle message for transitions 
	 * @param v Vehicle helping to make sure what subtraction to
	 * 			the numbers is being required.
	 * @author Ken Lewis
	 */
	private void outgoingVehicleMonitor (Vehicle v){
		
		if (v instanceof MotorCycle)
			this.numMotorCycles --;
		 else if (v instanceof Car && ((Car) v).isSmall() == true) {
			this.numSmallCars --;
			this.numCars --;
		 }
		 else if (v instanceof Car && ((Car) v).isSmall() == false) 
			this.numCars --;
		 
		 //this.count--;
		
	}
	
}
