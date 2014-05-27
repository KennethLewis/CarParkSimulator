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

import java.lang.reflect.Array;

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
	private static final int EXAMPLE_SPACES_TINY = 2;
	
	private static final int EXAMPLE_SMALL_SPACES = Constants.DEFAULT_MAX_SMALL_CAR_SPACES;
	private static final int EXAMPLE_SMALL_SPACES_TINY = 1;
	private static final int EXAMPLE_CYCLE_SPACES = Constants.DEFAULT_MAX_MOTORCYCLE_SPACES;
	private static final int EXAMPLE_CYCLE_SPACES_TINY = 1;
	private static final int EXAMPLE_QUEUE_SIZE = Constants.DEFAULT_MAX_QUEUE_SIZE;
	
	
	private static final String EXAMPLE_PLATE = "1234Test";	
	private static final int EXAMPLE_ARRIVAL_TIME = 5;

	private static final int EXAMPLE_INTENDED_DURATION = Constants.MINIMUM_STAY;
	private static final int EXAMPLE_DEPARTURE_TIME = EXAMPLE_ARRIVAL_TIME + EXAMPLE_INTENDED_DURATION;
	
	private static final int EXAMPLE_LOOP = 10;
	
	
	private CarPark testCarPark;
	private CarPark testTinyPark;
	private Car testCar;
	private Car testSmallCar;
	private MotorCycle testBike;
	
	@Before
	/**
	 * Creates a new car park before every test
	 * @author Thomas McCarthy
	 */
	public void testCreateTestCarPark() throws VehicleException {
		testCarPark = new CarPark();
		testTinyPark = new CarPark(EXAMPLE_SPACES_TINY, EXAMPLE_SMALL_SPACES_TINY,
				 EXAMPLE_CYCLE_SPACES_TINY,EXAMPLE_QUEUE_SIZE);
		testCar = new Car (EXAMPLE_PLATE, EXAMPLE_ARRIVAL_TIME, false); 
		testSmallCar = new Car (EXAMPLE_PLATE, EXAMPLE_ARRIVAL_TIME, true);
		testBike = new MotorCycle (EXAMPLE_PLATE, EXAMPLE_ARRIVAL_TIME); 
	}

	@Test
	public void NoExtraPublicMethods() {
		//Extends Object, extras less toString() 
		final int ExtraMethods = 21; 
		final int NumObjectClassMethods = Array.getLength(Object.class.getMethods());
		final int NumCarParkClassMethods = Array.getLength(CarPark.class.getMethods());
		assertTrue("obj:"+NumObjectClassMethods+":cp:"+NumCarParkClassMethods,(NumObjectClassMethods+ExtraMethods)==NumCarParkClassMethods);
	}
	
	@Test 
	public void NoExtraPublicFields() {
		//Same as Vehicle 
		final int NumObjectClassFields = Array.getLength(Object.class.getFields());
		final int NumCarParkClassFields = Array.getLength(CarPark.class.getFields());
		assertTrue("obj:"+NumObjectClassFields+":cp:"+NumCarParkClassFields,(NumObjectClassFields)==NumCarParkClassFields);
	}
	
	@Test 
	public void NoExtraPublicConstructors() {
		//One extra cons used. 
		final int NumObjectClassConstructors = Array.getLength(Object.class.getConstructors());
		final int NumCarParkClassConstructors = Array.getLength(CarPark.class.getConstructors());
		assertTrue(":obj:"+NumObjectClassConstructors+":cp:"+NumCarParkClassConstructors,(NumObjectClassConstructors+1)==NumCarParkClassConstructors);
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
		
		testTinyPark.parkVehicle(testCar, testCar.getArrivalTime(), EXAMPLE_INTENDED_DURATION);
		testTinyPark.parkVehicle(testSmallCar, testSmallCar.getArrivalTime(), EXAMPLE_INTENDED_DURATION);
		testTinyPark.parkVehicle(testBike, testBike.getArrivalTime(),
				EXAMPLE_INTENDED_DURATION);
		assertTrue(testTinyPark.carParkFull());
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
		
		testTinyPark.parkVehicle(testCar, testCar.getArrivalTime(), EXAMPLE_INTENDED_DURATION);
		testTinyPark.parkVehicle(testSmallCar, testSmallCar.getArrivalTime(),
				EXAMPLE_INTENDED_DURATION);

		// We don't add a motorbike so the park shouldn't be full
		assertFalse(testTinyPark.carParkFull());
	}
	
	
	/**
	 * Tests if an exception is throw when we try
	 * adding a car to a full carpark
	 * @throws VehicleException
	 * @author Thomas McCarthy
	 * @throws SimulationException 
	 */
	@Test(expected = VehicleException.class)
	public void testParkVehicle_Duplicate() throws VehicleException, SimulationException {
		testCarPark.parkVehicle(testCar, testCar.getArrivalTime(), EXAMPLE_INTENDED_DURATION);
		testCarPark.parkVehicle(testCar, testCar.getArrivalTime(), EXAMPLE_INTENDED_DURATION);
	}
	
	
	/**
	 * Tests if an exception is throw when we try
	 * adding a car to a full carpark
	 * @throws VehicleException
	 * @author Thomas McCarthy
	 * @throws SimulationException 
	 */
	@Test(expected = SimulationException.class)
	public void testParkVehicle_NoSpaces() throws VehicleException, SimulationException {
		testTinyPark.parkVehicle(testCar, testCar.getArrivalTime(), EXAMPLE_INTENDED_DURATION);
		
		testCar = new Car(EXAMPLE_PLATE, EXAMPLE_ARRIVAL_TIME, false);
		testTinyPark.parkVehicle(testCar, testCar.getArrivalTime(), EXAMPLE_INTENDED_DURATION);
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
	
		for(int i = 0; i < EXAMPLE_LOOP; i++)  {
			testCar = new Car(EXAMPLE_PLATE, EXAMPLE_ARRIVAL_TIME, true);
			testCarPark.parkVehicle(testCar, testCar.getArrivalTime(), EXAMPLE_INTENDED_DURATION);
		}
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
		
		testCarPark.parkVehicle(testCar, testCar.getArrivalTime(), EXAMPLE_INTENDED_DURATION);
		
		for(int i = 0; i < EXAMPLE_LOOP; i++)  {
			testCarPark.parkVehicle(testBike, testBike.getArrivalTime(), EXAMPLE_INTENDED_DURATION);
			testBike = new MotorCycle (EXAMPLE_PLATE, EXAMPLE_ARRIVAL_TIME); 
		}
		assertTrue(testCarPark.getNumMotorCycles() == EXAMPLE_LOOP);
	}
	
	
	
	/**
	 * Tests if we can add a car to the queue and correctly get number of vehicles
	 * @throws VehicleException
	 * @author Thomas McCarthy
	 * @throws SimulationException 
	 */
	@Test
	public void testQueueNumVehicles() throws VehicleException, SimulationException{
		testCarPark.enterQueue(testCar);
		assertTrue(testCarPark.numVehiclesInQueue() == 1);
	}
	
	/**
	 * Tests if numVehiclesInQueue returns 0 when the queue is empty
	 * @throws VehicleException
	 * @author Thomas McCarthy
	 * @throws SimulationException 
	 */
	@Test
	public void testQueueNumVehicles_Empty() throws VehicleException, SimulationException{
		assertEquals(testCarPark.numVehiclesInQueue(), 0);
	}
	
	
	
	

	/**
	 * Tests if a queue is correctly reported as being full
	 * @throws VehicleException
	 * @author Thomas McCarthy
	 * @throws SimulationException 
	 */
	@Test
	public void testQueueFull() throws VehicleException, SimulationException{
			
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
		testCarPark.enterQueue(testCar);
		assertFalse(testCarPark.queueFull());
	}
	
	
	
	/**
	 * Tests if the queue is correctly reported as empty
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
	@Test
	public void testRemoveFromCarpark_WithoutBeingParked() throws VehicleException, SimulationException{
		testCarPark.parkVehicle(testCar, testCar.getArrivalTime(), EXAMPLE_INTENDED_DURATION);
		testCarPark.unparkVehicle(testCar, EXAMPLE_DEPARTURE_TIME);
		assertTrue(testCarPark.carParkEmpty());
		
	}
	

	
	
	
	
	/**
	 * Tests if it is calculated that there is no space for a normal car
	 * when all spaces are filled
	 * @throws VehicleException
	 * @author Thomas McCarthy
	 * @throws SimulationException 
	 */
	@Test
	public void testSpacesAvailable_Full_Car() throws VehicleException, SimulationException{
		
		testTinyPark.parkVehicle(testCar, testCar.getArrivalTime(), EXAMPLE_INTENDED_DURATION);
		testTinyPark.parkVehicle(testSmallCar, testSmallCar.getArrivalTime(), EXAMPLE_INTENDED_DURATION);
		testTinyPark.parkVehicle(testBike, testBike.getArrivalTime(),
				EXAMPLE_INTENDED_DURATION);
		assertFalse(testTinyPark.spacesAvailable(testCar));
	
	}
	
	

	/**
	 * Tests if it is calculated that there is no space for a small car
	 * when all spaces are filled
	 * @throws VehicleException
	 * @author Thomas McCarthy
	 * @throws SimulationException 
	 */
	@Test
	public void testSpacesAvailable_Full_SmallCar() throws VehicleException, SimulationException{
		
		testTinyPark.parkVehicle(testCar, testCar.getArrivalTime(), EXAMPLE_INTENDED_DURATION);
		testTinyPark.parkVehicle(testSmallCar, testSmallCar.getArrivalTime(), EXAMPLE_INTENDED_DURATION);
		testTinyPark.parkVehicle(testBike, testBike.getArrivalTime(),
				EXAMPLE_INTENDED_DURATION);
		assertFalse(testTinyPark.spacesAvailable(testSmallCar));
	
	}
	
	

	/**
	 * Tests if it is calculated that there is no space for a bike
	 * when all spaces are filled
	 * @throws VehicleException
	 * @author Thomas McCarthy
	 * @throws SimulationException 
	 */
	@Test
	public void testSpacesAvailable_Full_Bike() throws VehicleException, SimulationException{
		
		testTinyPark.parkVehicle(testCar, testCar.getArrivalTime(), EXAMPLE_INTENDED_DURATION);
		testTinyPark.parkVehicle(testSmallCar, testSmallCar.getArrivalTime(), EXAMPLE_INTENDED_DURATION);
		testTinyPark.parkVehicle(testBike, testBike.getArrivalTime(),
				EXAMPLE_INTENDED_DURATION);
		assertFalse(testTinyPark.spacesAvailable(testBike));
	
	}
	
	

	/**
	 * Tests if it is calculated that is space for a bike
	 * when there is in fact space
	 * @throws VehicleException
	 * @author Thomas McCarthy
	 * @throws SimulationException 
	 */
	@Test
	public void testSpacesAvailable_Bike() throws VehicleException, SimulationException{
		
		testTinyPark.parkVehicle(testCar, testCar.getArrivalTime(), EXAMPLE_INTENDED_DURATION);
		testTinyPark.parkVehicle(testSmallCar, testSmallCar.getArrivalTime(), EXAMPLE_INTENDED_DURATION);
		assertTrue(testTinyPark.spacesAvailable(testBike));
	
	}
	
	
	/**
	 * Tests if it is calculated that is space for a car
	 * when there is in fact space
	 * @throws VehicleException
	 * @author Thomas McCarthy
	 * @throws SimulationException 
	 */
	@Test
	public void testSpacesAvailable_Car() throws VehicleException, SimulationException{
		
		testTinyPark.parkVehicle(testSmallCar, testSmallCar.getArrivalTime(), EXAMPLE_INTENDED_DURATION);
		testTinyPark.parkVehicle(testBike, testBike.getArrivalTime(),
				EXAMPLE_INTENDED_DURATION);
		assertTrue(testTinyPark.spacesAvailable(testCar));
	
	}
	
	
	
	/**
	 * Tests if it is calculated that is space for a small car
	 * when there is in fact space
	 * @throws VehicleException
	 * @author Thomas McCarthy
	 * @throws SimulationException 
	 */
	@Test
	public void testSpacesAvailable_SmallCar() throws VehicleException, SimulationException{
		
		testTinyPark.parkVehicle(testCar, testCar.getArrivalTime(), EXAMPLE_INTENDED_DURATION);
		testTinyPark.parkVehicle(testBike, testBike.getArrivalTime(),
				EXAMPLE_INTENDED_DURATION);
		assertTrue(testTinyPark.spacesAvailable(testSmallCar));
	
	}
	

	/**
	 * Tests if it is calculated that is space for a small car
	 * when there is in fact space (a normal space that is)
	 * @throws VehicleException
	 * @author Thomas McCarthy
	 * @throws SimulationException 
	 */
	@Test
	public void testSpacesAvailable_SmallCar_InBigSpace() throws VehicleException, SimulationException{
		
		testTinyPark.parkVehicle(testSmallCar, testSmallCar.getArrivalTime(), EXAMPLE_INTENDED_DURATION);
		testTinyPark.parkVehicle(testBike, testBike.getArrivalTime(),
				EXAMPLE_INTENDED_DURATION);
		assertTrue(testTinyPark.spacesAvailable(testSmallCar));
	
	}
	
	
	/**
	 * Tests if it is calculated that is space for a bike
	 * when there is in fact space (a small car space that is)
	 * @throws VehicleException
	 * @author Thomas McCarthy
	 * @throws SimulationException 
	 */
	@Test
	public void testSpacesAvailable_Bike_InBigSpace() throws VehicleException, SimulationException{
		
		testTinyPark.parkVehicle(testCar, testCar.getArrivalTime(), EXAMPLE_INTENDED_DURATION);
		testTinyPark.parkVehicle(testBike, testBike.getArrivalTime(),
				EXAMPLE_INTENDED_DURATION);
		assertTrue(testTinyPark.spacesAvailable(testBike));
	
	}
	
	
	/**
	 * Test if small cars are overflowing into normal
	 * spaces and properly blocking normal cars
	 * @throws VehicleException
	 * @author Thomas McCarthy
	 * @throws SimulationException 
	 */
	@Test
	public void testSpacesAvailable_Car_CantFromOverflow() throws VehicleException, SimulationException{
		
		testTinyPark.parkVehicle(testSmallCar, testSmallCar.getArrivalTime(), EXAMPLE_INTENDED_DURATION);
		testSmallCar = new Car (EXAMPLE_PLATE, EXAMPLE_ARRIVAL_TIME, true);
		testTinyPark.parkVehicle(testSmallCar, testSmallCar.getArrivalTime(), EXAMPLE_INTENDED_DURATION);
		testTinyPark.parkVehicle(testBike, testBike.getArrivalTime(),
				EXAMPLE_INTENDED_DURATION);
		assertFalse(testTinyPark.spacesAvailable(testCar));
	
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
	@Test
	public void testArchiveNewVehicle_NeverQueued() throws SimulationException, VehicleException{
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
		testCarPark.archiveNewVehicle(testCar);
		assertTrue(testCar.wasParked() == false);
	}
	
	
	/**
	 * Tests if parked departing vehicles are correctly archived
	 * @throws VehicleException
	 * @author Thomas McCarthy
	 * @throws SimulationException 
	 */
	@Test
	public void testArchiveDepartingVehicles() throws SimulationException, VehicleException{
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
	@Test
	public void testArchiveDepartingVehicles_ForceClear() throws SimulationException, VehicleException{
		testCarPark.parkVehicle(testCar, EXAMPLE_ARRIVAL_TIME, EXAMPLE_INTENDED_DURATION);
		testCarPark.archiveDepartingVehicles(EXAMPLE_DEPARTURE_TIME, true);
		assertTrue(testCarPark.getStatus(testCar.getArrivalTime()).contains("P:0"));
	}
	
	
	/**
	 * Tests if we don't allow intended durations that are too short
	 * @throws VehicleException
	 * @author Thomas McCarthy
	 * @throws SimulationException 
	 */
	@Test(expected = VehicleException.class)
	public void testArchiveDepartingVehicles_Shorted() throws SimulationException, VehicleException{
		testCarPark.parkVehicle(testCar, EXAMPLE_ARRIVAL_TIME, EXAMPLE_ARRIVAL_TIME);
		testCarPark.archiveDepartingVehicles(EXAMPLE_DEPARTURE_TIME, true);
	}
	

		
	
	/**
	 * Tests if this method actually archives the vehicle
	 * @throws VehicleException
	 * @author Thomas McCarthy
	 * @throws SimulationException 
 */
	@Test
	public void testArchiveQueueFailures() throws SimulationException, VehicleException{
		int finishTime = EXAMPLE_ARRIVAL_TIME + Constants.MAXIMUM_QUEUE_TIME  + 1;
		
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
	@Test
	public void testArchiveQueueFailures_NotLongEnough() throws SimulationException, VehicleException{
		
		// Just below maximum queue time
		int finishTime = EXAMPLE_ARRIVAL_TIME + Constants.MAXIMUM_QUEUE_TIME;
		
		testCarPark.enterQueue(testCar);
		testCarPark.archiveQueueFailures(finishTime);
		assertFalse(testCarPark.getStatus(finishTime).contains("C:Q>A"));
	}
	
	/**
	 * Tests if vehicles are added to the queue
	 * @throws VehicleException
	 * @author Thomas McCarthy
	 * @throws SimulationException 
 */
	@Test
	public void testEnterQueue() throws SimulationException, VehicleException{
		
		testCarPark.enterQueue(testCar);
		assertTrue(testCarPark.getStatus(EXAMPLE_ARRIVAL_TIME).contains("Q:1"));
	}
	
	/**
	 * Tests if exception is thrown when vehicle is added to a queue with
	 * a max size of 0
	 * @throws VehicleException
	 * @author Thomas McCarthy
	 * @throws SimulationException 
 */
	@Test(expected = SimulationException.class)
	public void testEnterQueue_Full_ZeroQueue() throws SimulationException, VehicleException{
		testCarPark = new CarPark(EXAMPLE_SPACES, EXAMPLE_SMALL_SPACES, EXAMPLE_CYCLE_SPACES, 0);
		testCarPark.enterQueue(testCar);
	}
	
	/**
	 * Tests if exception is thrown when vehicle is added to a full queue
	 * @throws VehicleException
	 * @author Thomas McCarthy
	 * @throws SimulationException 
 */
	@Test(expected = SimulationException.class)
	public void testEnterQueue_Full() throws SimulationException, VehicleException{
		for (int i = 0; i < EXAMPLE_QUEUE_SIZE; i++) {
			Car testCar = new Car (EXAMPLE_PLATE, EXAMPLE_ARRIVAL_TIME, false); 
			testCarPark.enterQueue(testCar);
		}
		
		Car testCar = new Car (EXAMPLE_PLATE, EXAMPLE_ARRIVAL_TIME, true);
		testCarPark.enterQueue(testCar);
	
	}
	
	/**
	 * Tests if vehicles are properly removed from the queue
	 * @throws VehicleException
	 * @author Thomas McCarthy
	 * @throws SimulationException 
 */
	@Test
	public void testExitQueue() throws SimulationException, VehicleException{
		
		testCarPark.enterQueue(testCar);
		testCarPark.exitQueue(testCar, EXAMPLE_DEPARTURE_TIME);
		
		assertFalse(testCar.isQueued());
	}
	
	
	/**
	 * Tests if exception is thrown when we attempt to remove
	 * a car from the queue that doesn't exist in the queue
	 * @throws VehicleException
	 * @author Thomas McCarthy
	 * @throws SimulationException 
 */
	@Test(expected = SimulationException.class)
	public void testExitQueue_NotInQueue() throws SimulationException, VehicleException{
		testCarPark.exitQueue(testCar, EXAMPLE_DEPARTURE_TIME);
	}
	
}
