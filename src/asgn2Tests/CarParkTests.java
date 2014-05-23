/**
 * 
 * This file is part of the CarParkSimulator Project, written as 
 * part of the assessment for INB370, semester 1, 2014. 
 *
 * CarParkSimulator
 * asgn2Tests 
 * 29/04/2014
 * 
 */
package asgn2Tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import asgn2Exceptions.SimulationException;
import asgn2Exceptions.VehicleException;
import asgn2Simulators.Constants;
import asgn2Vehicles.Car;
import asgn2Vehicles.MotorCycle;
import asgn2CarParks.CarPark;


/**
 * @author hogan
 *
 */
public class CarParkTests {


	private static final int EXAMPLE_SPACES = Constants.DEFAULT_MAX_CAR_SPACES;
	private static final int EXAMPLE_SPACES_TINY = 1;
	
	private static final int EXAMPLE_SMALL_SPACES = Constants.DEFAULT_MAX_SMALL_CAR_SPACES;
	private static final int EXAMPLE_SMALL_SPACES_TINY = 1;
	private static final int EXAMPLE_CYCLE_SPACES = Constants.DEFAULT_MAX_MOTORCYCLE_SPACES;
	private static final int EXAMPLE_CYCLE_SPACES_TINY = 1;
	private static final int EXAMPLE_QUEUE_SIZE = Constants.DEFAULT_MAX_QUEUE_SIZE;
	
	private static final String EXAMPLE_PLATE = "1234Test";	
	private static final int EXAMPLE_ARRIVAL_TIME = 5;
	private static final int EXAMPLE_DEPARTURE_TIME = 7;
	private static final int EXAMPLE_INTENDED_DURATION = Constants.MINIMUM_STAY;
	
	private static final int EXAMPLE_LOOP = 10;
	private CarPark testCarPark;
	
	@Before
	/**
	 * Creates a new car park before every test
	 * @author Thomas McCarthy
	 */
	public void testCreateTestCarPark() throws VehicleException {
		testCarPark = new CarPark();
	}

	
	/**
	 * Testing the creation of a normal Vehicle Object
	 * @throws VehicleException
	 * @author Thomas McCarthy
	 */
	@Test
	public void testNewCarParkCreation() throws VehicleException{
		assertTrue(testCarPark != null);
	}

	
	
	
	/**
	 * Tests if the car park is correctly returning
	 * true when it is empty
	 * @throws VehicleException
	 * @author Thomas McCarthy
	 */
	@Test
	public void testEmptyCarPark() throws VehicleException{
		assertTrue(testCarPark.carParkEmpty());
	}
	
	
	
	/**
	 * Tests if the car park is correctly returning
	 * false when it is not empty
	 * @throws VehicleException
	 * @author Thomas McCarthy
	 * @throws SimulationException 
	 */
	@Test
	public void testEmptyCarPark_False() throws VehicleException, SimulationException{
		Car testCar = new Car (EXAMPLE_PLATE, EXAMPLE_ARRIVAL_TIME, false); 
		testCarPark.parkVehicle(testCar, EXAMPLE_ARRIVAL_TIME, EXAMPLE_INTENDED_DURATION);
		assertFalse(testCarPark.carParkEmpty());
	}
	
	/**
	 * Tests if the car park is correctly returning
	 * true when it is full
	 * @throws VehicleException
	 * @author Thomas McCarthy
	 * @throws SimulationException 
	 */
	@Test
	public void testFullCarPark() throws VehicleException, SimulationException {
		
		Car testCar = new Car(EXAMPLE_PLATE, EXAMPLE_ARRIVAL_TIME, false);
		Car testSmallCar = new Car (EXAMPLE_PLATE, EXAMPLE_ARRIVAL_TIME, true);
		MotorCycle motorBike = new MotorCycle (EXAMPLE_PLATE, EXAMPLE_ARRIVAL_TIME); 
		testCarPark = new CarPark(EXAMPLE_SPACES_TINY, EXAMPLE_SMALL_SPACES_TINY,
				 EXAMPLE_CYCLE_SPACES_TINY,EXAMPLE_QUEUE_SIZE);
		testCarPark.parkVehicle(testCar, testCar.getArrivalTime(), EXAMPLE_INTENDED_DURATION);
		testCarPark.parkVehicle(testSmallCar, testSmallCar.getArrivalTime(),
				EXAMPLE_INTENDED_DURATION);
		testCarPark.parkVehicle(motorBike, motorBike.getArrivalTime(),+
				EXAMPLE_INTENDED_DURATION);
		assertTrue(testCarPark.carParkFull());
	}
	
	
	/**
	 * Tests if the car park is correctly returning
	 * false when it is not full
	 * @throws VehicleException
	 * @author Thomas McCarthy
	 * @throws SimulationException 
	 */
	@Test
	public void testFullCarPark_False() throws VehicleException, SimulationException {
		
		Car testCar = new Car(EXAMPLE_PLATE, EXAMPLE_ARRIVAL_TIME, false);
		Car testSmallCar = new Car (EXAMPLE_PLATE, EXAMPLE_ARRIVAL_TIME, true);
		MotorCycle motorBike = new MotorCycle (EXAMPLE_PLATE, EXAMPLE_ARRIVAL_TIME); 
		testCarPark = new CarPark(EXAMPLE_SPACES_TINY, EXAMPLE_SMALL_SPACES_TINY,
				 EXAMPLE_CYCLE_SPACES_TINY,EXAMPLE_QUEUE_SIZE);
		testCarPark.parkVehicle(testCar, testCar.getArrivalTime(), EXAMPLE_INTENDED_DURATION);
		testCarPark.parkVehicle(testSmallCar, testSmallCar.getArrivalTime(),
				EXAMPLE_INTENDED_DURATION);

		// We don't add a motorbike so the park shouldn't be full
		assertFalse(testCarPark.carParkFull());
	}
	
	
	/**
	 * Tests if an exception is throw when we try
	 * adding a car to a full carpark
	 * @throws VehicleException
	 * @author Thomas McCarthy
	 * @throws SimulationException 
	 */
	@Test(expected = VehicleException.class)
	public void testParkVehicle_FullCarPark() throws VehicleException, SimulationException {
		Car testCar = new Car(EXAMPLE_PLATE, EXAMPLE_ARRIVAL_TIME, false);
		testCarPark = new CarPark(EXAMPLE_SPACES_TINY, EXAMPLE_SMALL_SPACES,
								  EXAMPLE_CYCLE_SPACES, EXAMPLE_QUEUE_SIZE);
		testCarPark.parkVehicle(testCar, testCar.getArrivalTime(), EXAMPLE_INTENDED_DURATION);
		testCarPark.parkVehicle(testCar, testCar.getArrivalTime(), EXAMPLE_INTENDED_DURATION);
	}
	
	
	/**
	 * Tests if we can correctly get the number of cars
	 * in the carpark
	 * @throws VehicleException
	 * @author Thomas McCarthy
	 * @throws SimulationException 
	 */
	@Test
	public void testNumCars() throws VehicleException, SimulationException{
		
		Car testCar;
		for(int i = 0; i < EXAMPLE_LOOP; i++)  {
			testCar = new Car(EXAMPLE_PLATE, EXAMPLE_ARRIVAL_TIME, false);
			testCarPark.parkVehicle(testCar, testCar.getArrivalTime(), EXAMPLE_INTENDED_DURATION);
		}
		assertTrue(testCarPark.getNumCars() == EXAMPLE_LOOP);
	}
	
	
	
	/**
	 * Tests if we can correctly get the number of small cars
	 * in the carpark
	 * @throws VehicleException
	 * @author Thomas McCarthy
	 * @author Ken Lewis
	 * @throws SimulationException 
	 */
	@Test
	public void testNumSmallCars() throws VehicleException, SimulationException{
		
		Car testCar;
		
		// adding a non small car to make sure it's excluded in the final count
		//testCarPark.parkVehicle(testCar, testCar.getArrivalTime(), EXAMPLE_INTENDED_DURATION);
		
		for(int i = 0; i < EXAMPLE_LOOP; i++)  {
			testCar = new Car(EXAMPLE_PLATE, EXAMPLE_ARRIVAL_TIME, true);
			testCarPark.parkVehicle(testCar, testCar.getArrivalTime(), EXAMPLE_INTENDED_DURATION);
		}
		System.out.printf("%d",testCarPark.getNumSmallCars());
		assertTrue(testCarPark.getNumSmallCars() == EXAMPLE_LOOP);
	}
	
	
	/**
	 * Tests if we can correctly get the number of motorcycles
	 * in the carpark
	 * @throws VehicleException
	 * @author Thomas McCarthy
	 * @throws SimulationException 
	 */
	@Test
	public void testNumMotorCycles() throws VehicleException, SimulationException{
		
		MotorCycle testBike;
		Car testCar = new Car(EXAMPLE_PLATE, EXAMPLE_ARRIVAL_TIME, false);
		
		// adding something that isnt a motorcycle to make sure it's excluded in the final count
		testCarPark.parkVehicle(testCar, testCar.getArrivalTime(), EXAMPLE_INTENDED_DURATION);
		
		for(int i = 0; i < EXAMPLE_LOOP; i++)  {
			testBike = new MotorCycle(EXAMPLE_PLATE, EXAMPLE_ARRIVAL_TIME);
			testCarPark.parkVehicle(testBike, testBike.getArrivalTime(), EXAMPLE_INTENDED_DURATION);
		}
		assertTrue(testCarPark.getNumMotorCycles() == EXAMPLE_LOOP);
	}
	
	
	
	/**
	 * Tests if we can correctly get the number of cars
	 * in the queue
	 * @throws VehicleException
	 * @author Thomas McCarthy
	 * @throws SimulationException 
	 */
	@Test
	public void testQueueNumVehicles() throws VehicleException, SimulationException{
		Car testCar = new Car(EXAMPLE_PLATE, EXAMPLE_ARRIVAL_TIME, false);
		testCarPark.enterQueue(testCar);
		assertTrue(testCarPark.numVehiclesInQueue() == 1);
	}
	
	
	
	
	
	/**
	 * Tests if we can correctly get the number of cars
	 * in the queue
	 * @throws VehicleException
	 * @author Thomas McCarthy
	 * @throws SimulationException 
	 */
	@Test(expected = SimulationException.class)
	public void testQueueEnter_Full() throws VehicleException, SimulationException{
		
		testCarPark = new CarPark(EXAMPLE_SPACES_TINY, EXAMPLE_SMALL_SPACES,
				  EXAMPLE_CYCLE_SPACES, EXAMPLE_QUEUE_SIZE);
		Car testCar; 
		
		// Loop an extra time on purpose
		for (int i = 0; i < EXAMPLE_QUEUE_SIZE + 1; i++) {
			testCar = new Car(EXAMPLE_PLATE, EXAMPLE_ARRIVAL_TIME, false);
			testCarPark.enterQueue(testCar);
		}
	}


	/**
	 * Tests if a queue is correctly reported as being full
	 * @throws VehicleException
	 * @author Thomas McCarthy
	 * @throws SimulationException 
	 */
	@Test
	public void testQueueFull() throws VehicleException, SimulationException{
		
		testCarPark = new CarPark(EXAMPLE_SPACES_TINY, EXAMPLE_SMALL_SPACES,
				  EXAMPLE_CYCLE_SPACES, EXAMPLE_QUEUE_SIZE);
		Car testCar; 
		
		for (int i = 0; i < EXAMPLE_QUEUE_SIZE; i++) {
			
			testCar = new Car(EXAMPLE_PLATE, EXAMPLE_ARRIVAL_TIME, false);
			testCarPark.enterQueue(testCar);
		}
		assertTrue(testCarPark.queueFull());
	}
	
	
	/**
	 * Tests if a queue is correctly reported as being not full
	 * @throws VehicleException
	 * @author Thomas McCarthy
	 * @throws SimulationException 
	 */
	@Test
	public void testQueueFull_False() throws VehicleException, SimulationException{
		testCarPark = new CarPark(EXAMPLE_SPACES_TINY, EXAMPLE_SMALL_SPACES,
				  EXAMPLE_CYCLE_SPACES, EXAMPLE_QUEUE_SIZE);
		Car testCar = new Car(EXAMPLE_PLATE, EXAMPLE_ARRIVAL_TIME, false);
		testCarPark.enterQueue(testCar);
		assertFalse(testCarPark.queueFull());
	}
	
	
	
	/**
	 * Tests if the queue is correctly reported as empty
	 * TODO Make better?
	 * @throws VehicleException
	 * @author Thomas McCarthy
	 * @throws SimulationException 
	 */
	@Test
	public void testQueueEmpty() throws VehicleException, SimulationException{
		assertTrue(testCarPark.queueEmpty() == true);
	}
	
	
	/**
	 * Tests if a queue is correctly reported as being not empty
	 * @throws VehicleException
	 * @author Thomas McCarthy
	 * @throws SimulationException 
	 */
	@Test
	public void testQueueEmpty_False() throws VehicleException, SimulationException{
		testCarPark = new CarPark(EXAMPLE_SPACES_TINY, EXAMPLE_SMALL_SPACES,
				  EXAMPLE_CYCLE_SPACES, EXAMPLE_QUEUE_SIZE);
		Car testCar = new Car(EXAMPLE_PLATE, EXAMPLE_ARRIVAL_TIME, false);
		testCarPark.enterQueue(testCar);
		assertFalse(testCarPark.queueEmpty());
	}
	
	
	/**
	 * Tests if a vehicle can be removed from the carpark
	 * @throws VehicleException
	 * @author Thomas McCarthy
	 * @throws SimulationException 
	 */
	@Test
	public void testRemoveFromCarpark() throws VehicleException, SimulationException{
		Car testCar = new Car(EXAMPLE_PLATE, EXAMPLE_ARRIVAL_TIME, false);
		testCarPark.parkVehicle(testCar, testCar.getArrivalTime(), EXAMPLE_INTENDED_DURATION);
		testCarPark.unparkVehicle(testCar, EXAMPLE_DEPARTURE_TIME);
		assertTrue(testCarPark.carParkEmpty());
	}
	
	
	/**
	 * Tests if an exception is thrown when we try to 
	 * remove a car from a carpark when it isn't in there
	 * @throws VehicleException
	 * @author Thomas McCarthy
	 * @throws SimulationException 
	 */
	@Test//(expected = VehicleException.class)
	public void testRemoveFromCarpark_WithoutBeingParked() throws VehicleException, SimulationException{
		
		Car testCar = new Car(EXAMPLE_PLATE, EXAMPLE_ARRIVAL_TIME, false);
		testCarPark.parkVehicle(testCar, testCar.getArrivalTime(), EXAMPLE_INTENDED_DURATION);
		testCarPark.unparkVehicle(testCar, EXAMPLE_DEPARTURE_TIME);
		assertTrue(testCarPark.carParkEmpty());
		
	}
	
	
	/**
	 * Tests if the carpark correctly reports that there are no
	 * spaces available for a normal car when normal car spaces are empty
	 * @throws VehicleException
	 * @author Thomas McCarthy
	 * @throws SimulationException 
	 */
	@Test (expected = SimulationException.class)
	public void testSpacesAvailableCar() throws VehicleException, SimulationException{
		
		Car testCar = new Car ("TEST1234", EXAMPLE_ARRIVAL_TIME, false); 
		Car testSmallCar; 
		
		for (int i =0; i < EXAMPLE_SPACES; i++) {
			testCar = new Car(EXAMPLE_PLATE, EXAMPLE_ARRIVAL_TIME, false);
			testCarPark.parkVehicle(testCar, testCar.getArrivalTime(), EXAMPLE_INTENDED_DURATION);
		}
		
		for (int i =0; i < EXAMPLE_SMALL_SPACES; i++) {
			testSmallCar = new Car(EXAMPLE_PLATE, EXAMPLE_ARRIVAL_TIME, true);
			testCarPark.parkVehicle(testSmallCar, testSmallCar.getArrivalTime(), EXAMPLE_INTENDED_DURATION);
		}
	}
	
	
	/**
	 * Tests if the carpark correctly reports that there are no
	 * spaces available for a small car when all available car spaces are empty
	 * @throws VehicleException
	 * @author Thomas McCarthy
	 * @throws SimulationException 
	 */
	@Test (expected = SimulationException.class)
	public void testSpacesAvailableSmallCar() throws VehicleException, SimulationException{
		
		Car testCar;
		Car testSmallCar = new Car("TEST1234", EXAMPLE_ARRIVAL_TIME, true);
		
		for (int i =0; i < EXAMPLE_SPACES; i++) {
			testCar = new Car(EXAMPLE_PLATE, EXAMPLE_ARRIVAL_TIME, false);
			testCarPark.parkVehicle(testCar, testCar.getArrivalTime(), EXAMPLE_INTENDED_DURATION);
		}
		
		for (int i =0; i < EXAMPLE_SMALL_SPACES; i++) {
			testSmallCar = new Car(EXAMPLE_PLATE, EXAMPLE_ARRIVAL_TIME, true);
			testCarPark.parkVehicle(testSmallCar, testSmallCar.getArrivalTime(), EXAMPLE_INTENDED_DURATION);
		}
	}
	
	
	
	/**
	 * Tests if the carpark correctly reports that there are no
	 * spaces available for a motorcycle when all available spaces are empty
	 * @throws VehicleException
	 * @author Thomas McCarthy
	 * @throws SimulationException 
	 */
	@Test
	public void testSpacesAvailableMotorCycle() throws VehicleException, SimulationException{
		
		Car testSmallCar;
		MotorCycle testBike = new MotorCycle("TEST1234", EXAMPLE_ARRIVAL_TIME); 
		
		for (int i =0; i < EXAMPLE_SMALL_SPACES; i++) {
			testSmallCar = new Car(EXAMPLE_PLATE, EXAMPLE_ARRIVAL_TIME, true);
			testCarPark.parkVehicle(testSmallCar, testSmallCar.getArrivalTime(), EXAMPLE_INTENDED_DURATION);
		}
		
		for (int i =0; i < EXAMPLE_CYCLE_SPACES; i++) {
			testBike = new MotorCycle(EXAMPLE_PLATE, EXAMPLE_ARRIVAL_TIME);
			testCarPark.parkVehicle(testBike, testBike.getArrivalTime(), EXAMPLE_INTENDED_DURATION);
		}
		assertFalse(testCarPark.spacesAvailable(testBike));
	}
	
	
	/**
	 * Tests if an exception is thrown when we try to
	 * archive a vehicle that isn't 'new', since it's parked already
	 * @throws VehicleException
	 * @author Thomas McCarthy
	 * @throws SimulationException 
	 */
	@Test(expected = SimulationException.class)
	public void testArchiveNewVehicle_Parked() throws SimulationException, VehicleException{
		Car testCar = new Car (EXAMPLE_PLATE, EXAMPLE_ARRIVAL_TIME, false); 
		testCarPark.parkVehicle(testCar, EXAMPLE_ARRIVAL_TIME, EXAMPLE_INTENDED_DURATION);
		testCarPark.archiveNewVehicle(testCar);
	}
	
	
	
	/**
	 * Tests if an exception is thrown when we try to
	 * archive a vehicle that isn't 'new', since it's queued already
	 * @throws VehicleException
	 * @author Thomas McCarthy
	 * @throws SimulationException 
	 */
	@Test(expected = SimulationException.class)
	public void testArchiveNewVehicle_Queued() throws SimulationException, VehicleException{
		Car testCar = new Car (EXAMPLE_PLATE, EXAMPLE_ARRIVAL_TIME, false); 
		testCarPark.enterQueue(testCar);
		testCarPark.archiveNewVehicle(testCar);
	}
	
	
/**
	 * Tests if this method actually archives the vehicle
	 * @throws VehicleException
	 * @author Thomas McCarthy
	 * @throws SimulationException 
 */
	@Test
	public void testArchiveNewVehicle() throws SimulationException, VehicleException{
		Car testCar = new Car (EXAMPLE_PLATE, EXAMPLE_ARRIVAL_TIME, false); 
		testCarPark.archiveNewVehicle(testCar);
		assertTrue(testCarPark.getStatus(testCar.getArrivalTime()).contains("A:1"));
	}

	
	
	
	/**
	 * Tests if new vehicles that are archived are truly 'new',
	 * by verifying that they were never queued
	 * @throws VehicleException
	 * @author Thomas McCarthy
	 * @throws SimulationException 
	 */
	@Test//(expected = SimulationException.class)
	public void testArchiveNewVehicle_NeverQueued() throws SimulationException, VehicleException{
		Car testCar = new Car (EXAMPLE_PLATE, EXAMPLE_ARRIVAL_TIME, false); 
		testCarPark.archiveNewVehicle(testCar);
		assertFalse(testCar.wasQueued());
	}
	
	
	
	/**
	 * Tests if new vehicles that are archived are truly 'new',
	 * by verifying that they were never parked
	 * @throws VehicleException
	 * @author Thomas McCarthy
	 * @throws SimulationException 
	 */
	@Test
	public void testArchiveNewVehicle_NeverParked() throws SimulationException, VehicleException{
		Car testCar = new Car (EXAMPLE_PLATE, EXAMPLE_ARRIVAL_TIME, false); 
		testCarPark.archiveNewVehicle(testCar);
		assertTrue(testCar.wasParked() == false);
	}
	
	
	/**
	 * Tests if parked departing vehicles are correctly archived
	 * @throws VehicleException
	 * @author Thomas McCarthy
	 * @throws SimulationException 
	 */
	@Test//(expected = SimulationException.class)
	public void testArchiveDepartingVehicles() throws SimulationException, VehicleException{
		
		Car testCar = new Car (EXAMPLE_PLATE, EXAMPLE_ARRIVAL_TIME, false); 
		testCarPark.parkVehicle(testCar, EXAMPLE_ARRIVAL_TIME, EXAMPLE_INTENDED_DURATION);
		testCarPark.archiveDepartingVehicles(EXAMPLE_DEPARTURE_TIME, false);
		assertTrue(testCarPark.getStatus(testCar.getArrivalTime()).contains("A:1"));
	}

	
	/**
	 * Tests if parked departing vehicles are correctly cleared
	 * (if set to do so) at end of the day
	 * @throws VehicleException
	 * @author Thomas McCarthy
	 * @throws SimulationException 
	 */
	@Test(expected = SimulationException.class)
	public void testArchiveDepartingVehicles_ForceClear() throws SimulationException, VehicleException{
		Car testCar = new Car (EXAMPLE_PLATE, EXAMPLE_ARRIVAL_TIME, false); 
		testCarPark.parkVehicle(testCar, EXAMPLE_ARRIVAL_TIME, EXAMPLE_INTENDED_DURATION);
		testCarPark.archiveDepartingVehicles(EXAMPLE_DEPARTURE_TIME, true);
		assertTrue(testCarPark.getStatus(testCar.getArrivalTime()).contains("P:0"));
	}
	
	
	/**
	 * TODO How do we test this?!
	 * @throws VehicleException
	 * @author Thomas McCarthy
	 * @throws SimulationException 
	 */
	@Test(expected = SimulationException.class)
	public void testArchiveDepartingVehicles_NotInCarPark() throws SimulationException, VehicleException{
		
	}

		
	
	/**
	 * Tests if this method actually archives the vehicle
	 * @throws VehicleException
	 * @author Thomas McCarthy
	 * @throws SimulationException 
 */
	@Test(expected = SimulationException.class)
	public void testArchiveQueueFailures() throws SimulationException, VehicleException{
		Car testCar = new Car (EXAMPLE_PLATE, EXAMPLE_ARRIVAL_TIME, false); 
		int finishTime = EXAMPLE_ARRIVAL_TIME + Constants.MAXIMUM_QUEUE_TIME;
		
		testCarPark.enterQueue(testCar);
		testCarPark.archiveQueueFailures(finishTime);
		assertTrue(testCarPark.getStatus(finishTime).contains("C:Q>A"));
	}
	

	/**
	 * Tests if this method doesn't archive the vehicle
	 * if the vehicle hasn't been in the queue long enough
	 * @throws VehicleException
	 * @author Thomas McCarthy
	 * @throws SimulationException 
	 */
	@Test(expected = SimulationException.class)
	public void testArchiveQueueFailures_NotLongEnough() throws SimulationException, VehicleException{
		Car testCar = new Car (EXAMPLE_PLATE, EXAMPLE_ARRIVAL_TIME, false); 
		
		// Just below maximum queue time
		int finishTime = EXAMPLE_ARRIVAL_TIME + Constants.MAXIMUM_QUEUE_TIME - 1;
		
		testCarPark.enterQueue(testCar);
		testCarPark.archiveQueueFailures(finishTime);
		assertFalse(testCarPark.getStatus(finishTime).contains("C:Q>A"));
	}
	

}
