/**
 * 
 * This file is part of the CarParkSimulator Project, written as 
 * part of the assessment for INB370, semester 1, 2014. 
 *
 * CarParkSimulator
 * asgn2Vehicles 
 * 19/04/2014
 * 
 */

package asgn2Vehicles;

import asgn2Exceptions.VehicleException;
import asgn2Simulators.Constants;
import java.util.*;


/**
 * Vehicle is an abstract class specifying the basic state of a vehicle and the methods used to 
 * set and access that state. A vehicle is created upon arrival, at which point it must either 
 * enter the car park to take a vacant space or become part of the queue. If the queue is full, then 
 * the vehicle must leave and never enters the car park. The vehicle cannot be both parked and queued 
 * at once and both the constructor and the parking and queuing state transition methods must 
 * respect this constraint. 
 * 
 * Vehicles are created in a neutral state. If the vehicle is unable to park or queue, then no changes 
 * are needed if the vehicle leaves the carpark immediately.
 * Vehicles that remain and can't park enter a queued state via {@link #enterQueuedState() enterQueuedState} 
 * and leave the queued state via {@link #exitQueuedState(int) exitQueuedState}. 
 * Note that an exception is thrown if an attempt is made to join a queue when the vehicle is already 
 * in the queued state, or to leave a queue when it is not. 
 * 
 * Vehicles are parked using the {@link #enterParkedState(int, int) enterParkedState} method and depart using 
 * {@link #exitParkedState(int) exitParkedState}
 * 
 * Note again that exceptions are thrown if the state is inappropriate: vehicles cannot be parked or exit 
 * the car park from a queued state. 
 * 
 * The method javadoc below indicates the constraints on the time and other parameters. Other time parameters may 
 * vary from simulation to simulation and so are not constrained here.  
 * 
 * @author hogan
 *
 */
public abstract class Vehicle {
		
	private String vehID;
	private Integer arrivalTime;
	private Integer parkingTime;
	private Integer departureTime;
	private Integer exitTime; //Time the vehicle leaves the que
	private Integer intendedDuration;
	private List<String> vehicleState;
	
	/**
	 * Vehicle Constructor 
	 * @param vehID String identification number or plate of the vehicle
	 * @param arrivalTime int time (minutes) at which the vehicle arrives and is 
	 *        either queued, given entry to the car park or forced to leave
	 * @throws VehicleException if arrivalTime is <= 0 
	 * @author Ken Lewis
	 */
	public Vehicle(String vehID,int arrivalTime) throws VehicleException  {
		
		this.vehID = vehID;
		
		if(arrivalTime <= 0)
			throw new VehicleException("Arrival Time must be greater that" +
					"0\n");
		else
			this.arrivalTime = arrivalTime;
		
		//Setting other initial vehicle variables
		this.parkingTime = 0;
		this.departureTime = 0;
		this.exitTime = 0;
		this.intendedDuration = 0;
		this.vehicleState = new ArrayList<String>();
		this.vehicleState.add("N");
	}

	/**
	 * Transition vehicle to parked state (mutator)
	 * Parking starts on arrival or on exit from the queue, but time is set here
	 * @param parkingTime int time (minutes) at which the vehicle was able to park
	 * @param intendedDuration int time (minutes) for which the vehicle is intended to remain in the car park.
	 *  	  Note that the parkingTime + intendedDuration yields the departureTime
	 * @throws VehicleException if the vehicle is already in a parked or queued state, if parkingTime < 0, 
	 *         or if intendedDuration is less than the minimum prescribed in asgnSimulators.Constants
	 * @author Ken Lewis
	 */
	public void enterParkedState(int parkingTime, int intendedDuration) throws VehicleException {

		
		///for(int i =0; i < vehicleState.size(); i++){
		///	System.out.printf("VEHICLE STATE IS: %s\n", vehicleState.get(i));
		///}
		

		if(vehicleState.contains("P") || vehicleState.get(vehicleState.size() - 1) == "Q")
			throw new VehicleException ("Vehicle is currently either already parked"
					+ " or in the queue to enter the CarPark.\n");
		else if (parkingTime < 0)
			throw new VehicleException ("Vehicle parking time cannot be less than 0\n");
		else if (intendedDuration < Constants.MINIMUM_STAY)
			throw new VehicleException ("Intended duration cannot be less than the" +
					" Minimum Duration\n");
		else {
			this.parkingTime = parkingTime;
			this.intendedDuration = intendedDuration;
			this.vehicleState.add("P");//change state of Vehicle to parked once checked
		}
	}
	
	/**
	 * Transition vehicle to queued state (mutator) 
	 * Queuing formally starts on arrival and ceases with a call to {@link #exitQueuedState(int) exitQueuedState}
	 * @throws VehicleException if the vehicle is already in a queued or parked state
	 * @author Ken Lewis
	 */
	public void enterQueuedState() throws VehicleException {
		
		if(vehicleState.contains("P") || vehicleState.contains("Q"))
			throw new VehicleException ("Vehicle is currently either already parked"
					+ " or in the que to enter the CarPark.\n");
		else
			this.vehicleState.add("Q");
	}
	
	/**
	 * Transition vehicle from parked state (mutator) 
	 * @param departureTime int holding the actual departure time 
	 * @throws VehicleException if the vehicle is not in a parked state, is in a queued 
	 * 		  state or if the revised departureTime < parkingTime
	 * @author Ken Lewis
	 */
	public void exitParkedState(int departureTime) throws VehicleException {
		
		int lastState = vehicleState.size() -1 ;//-1 to get the last pos
		if(vehicleState.contains("P") == false || 
				vehicleState.get(lastState)	== ("Q"))
			throw new VehicleException ("Vehicle  cannot exit parked state, as it is" +
					" currently either not parked or in the que to enter the CarPark.\n");
		else if (departureTime < this.parkingTime)
			throw new VehicleException ("Revised departure time cannot be less than" +
					" the parking time.\n");
		//Method enterParkedState mentions departure time is parkingTime + 
		//intendedDuration yields the departureTime
		else {
			this.departureTime = departureTime;
			vehicleState.add("A");
		}
			
	}

	/**
	 * Transition vehicle from queued state (mutator) 
	 * Queuing formally starts on arrival with a call to {@link #enterQueuedState() enterQueuedState}
	 * Here we exit and set the time at which the vehicle left the queue
	 * @param exitTime int holding the time at which the vehicle left the queue 
	 * @throws VehicleException if the vehicle is in a parked state or not in a queued state, or if 
	 *  exitTime is not later than arrivalTime for this vehicle
	 *  @author Ken Lewis
	 */
	public void exitQueuedState(int exitTime) throws VehicleException {
	
		int timeInQueue = exitTime - this.getArrivalTime();
		int lastState = vehicleState.size() -1;
		
		if(vehicleState.contains("P") || vehicleState.contains("Q") == false)
			throw new VehicleException ("Vehicle  cannot exit Queued state, as it is" +
					" currently either parked or not in the que to enter the CarPark.\n");
		else if (exitTime < arrivalTime)
			throw new VehicleException ("Exit time cannot be greater than arrival time\n");
		else if (timeInQueue >= Constants.MAXIMUM_QUEUE_TIME){
			this.departureTime = exitTime;
			this.vehicleState.add("A");
		}
		else if (timeInQueue < Constants.MAXIMUM_QUEUE_TIME && 
				vehicleState.get(lastState).equals("Q"))				
			this.vehicleState.add("N");
			
		
	}
	
	/**
	 * Simple getter for the arrival time 
	 * @return the arrivalTime
	 * @author Ken Lewis
	 */
	public int getArrivalTime() {
		
		return this.arrivalTime;
	}
	
	/**
	 * Simple getter for the departure time from the car park
	 * Note: result may be 0 before parking, show intended departure 
	 * time while parked; and actual when archived
	 * @return the departureTime
	 * @author Ken Lewis
	 */
	public int getDepartureTime() {
		
		int lastState = vehicleState.size() -1; //-1 to get correct array position
		/**Presuming that the last vehicleState is the one which is
		* required. ie if it was parked that should be its last state,
		* if it was archived it should be its last state.
		*/
		if(this.vehicleState.get(lastState).equals("P"))
				return this.parkingTime + this.intendedDuration;
		else if (this.vehicleState.get(lastState).equals("A"))
			//Presume this is where the EnterParkedState method mentions
			//returning proper departure time
				return this.departureTime;
		else if (this.vehicleState.get(lastState)== "N" || this.vehicleState.get(lastState) == "Q")
			//Car may be in queue or just arrived.
			return 0;
		else
			return this.departureTime;
	}
	
	/**
	 * Simple getter for the parking time
	 * Note: result may be 0 before parking
	 * @return the parkingTime
	 * @author Ken Lewis
	 */
	public int getParkingTime() {
		
		return this.parkingTime;
	}

	/**
	 * Simple getter for the vehicle ID
	 * @return the vehID
	 * @author Ken Lewis
	 */
	public String getVehID() {
		
		return this.vehID;
	}

	/**
	 * Boolean status indicating whether vehicle is currently parked 
	 * @return true if the vehicle is in a parked state; false otherwise
	 * @author Ken Lewis
	 */
	public boolean isParked() {
		
		int lastState = vehicleState.size() - 1; // -1 to get right pos
		/**Presuming that the last vehicleState is the one which is
		* required. ie if it was parked that should be its last state.
		*/
		if(this.vehicleState.get(lastState).equals("P"))
			return true;
		else
			return false;
	}

	/**
	 * Boolean status indicating whether vehicle is currently queued
	 * @return true if vehicle is in a queued state, false otherwise
	 * @author Ken Lewis
	 */
	public boolean isQueued() {
		
		int lastState = vehicleState.size() - 1; // -1 to get right pos
		/**Presuming that the last vehicleState is the one which is
		* required. ie if it was queued that should be its last state.
		*/
		if(this.vehicleState.get(lastState).equals("Q"))
			return true;
		else
			return false;
			
	}
	
	/**
	 * Boolean status indicating whether customer is satisfied or not
	 * Satisfied if they park; dissatisfied if turned away, or queuing for too long 
	 * Note that calls to this method may not reflect final status 
	 * @return true if satisfied, false if never in parked state or if queuing time exceeds max allowable
	 * @author Ken Lewis 
	 */
	public boolean isSatisfied() {
		
		if (vehicleState.contains("P"))
			return true;
		else if ((this.arrivalTime + this.exitTime) > Constants.MAXIMUM_QUEUE_TIME)
			return false; //false if queue time was too long
		else 
			return false; // false if turned away
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		
		String wasQueuedTxt = "";
		if (wasQueued() == true) {
			int queueOverstay;
			
			if (getParkingTime() == 0) {
				queueOverstay = getDepartureTime() - getArrivalTime() - Constants.MAXIMUM_QUEUE_TIME;
				wasQueuedTxt = "\nExit from Queue: " + getDepartureTime() +
						"\nQueuing Time: " + (getDepartureTime() - getArrivalTime()) +
				"\nExceeded maximum acceptable queuing time by: " + queueOverstay;
			} else {

				wasQueuedTxt = "\nExit from Queue: " + getParkingTime() +
							"\nQueuing Time: " + (getParkingTime() - getArrivalTime());
			}
		} else {
			wasQueuedTxt = "\nVehicle was not queued";
		}
		
		
		String wasSatisfiedTxt = "";
		if (isSatisfied() == true) {
			wasSatisfiedTxt = "satisfied";
		} else {
			wasSatisfiedTxt = "not satisfied";
		}
		
		
		String wasParkedTxt = "";
		if (wasParked()) {
			wasParkedTxt = "\nEntry to Car Park: " + this.parkingTime + 
				"\nExit from Car Park: " + (getDepartureTime()) +
				"\nParking Time: " + 
				(getDepartureTime() - getParkingTime());
		} else {
			wasParkedTxt = "\nVehicle was not parked";
		}
		return "Vehicle vehID: " + this.vehID +
				"\nArrival Time: " + this.arrivalTime +
				wasQueuedTxt + wasParkedTxt +
				"\nCustomer was " + wasSatisfiedTxt;
		//EXAMPLE toString from Assingment Specs part II		
		/*Vehicle vehID:
			C9
			Arrival Time: 9
			Vehicle was not queued
			Entry to Car Park: 9
			Exit from Car Park: 124
			Parking Time: 115
			Customer was satisfied
			Car cannot use small parking space*/
	}

	/**
	 * Boolean status indicating whether vehicle was ever parked
	 * Will return false for vehicles in queue or turned away 
	 * @return true if vehicle was or is in a parked state, false otherwise 
	 * @author Ken Lewis
	 */
	public boolean wasParked() {
		
		if (this.vehicleState.contains("P"))
			return true;
		else
			return false;
	}

	/**
	 * Boolean status indicating whether vehicle was ever queued
	 * @return true if vehicle was or is in a queued state, false otherwise 
	 * @author Ken Lewis
	 */
	public boolean wasQueued() {
		
		if (this.vehicleState.contains("Q"))
			return true;
		else
			return false;
	}
}
