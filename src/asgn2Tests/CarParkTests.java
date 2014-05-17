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
import asgn2Vehicles.Car;
import asgn2Vehicles.MotorCycle;
import asgn2CarParks.CarPark;


/**
 * @author hogan
 *
 */
public class CarParkTests {


	private static final int EXAMPLE_SPACES = 20;
	private static final int EXAMPLE_SPACES_TINY = 1;
	
	private static final int EXAMPLE_SMALL_SPACES = 5;
	private static final int EXAMPLE_CYCLE_SPACES = 3;
	private static final int EXAMPLE_QUEUE_SIZE = 5;
	
	private static final String EXAMPLE_PLATE = "1234Test";	
	private static final int EXAMPLE_ARRIVAL_TIME = 5;
	private static final int EXAMPLE_DEPARTURE_TIME = 5;
	private static final int EXAMPLE_INTENDED_DURATION = 10;
	
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
		assertTrue(testCarPark.carParkEmpty() == true);
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
		testCarPark = new CarPark(EXAMPLE_SPACES_TINY, EXAMPLE_SMALL_SPACES,
								  EXAMPLE_CYCLE_SPACES, EXAMPLE_QUEUE_SIZE);
		testCarPark.parkVehicle(testCar, testCar.getArrivalTime(), EXAMPLE_INTENDED_DURATION);
		assertTrue(testCarPark.carParkFull() == true);
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
	 * TODO
	 * Tests if an exception is throw when we try
	 * adding a car to a carpark with invalid time constraints
	 * @throws VehicleException
	 * @author Thomas McCarthy
	 * @throws SimulationException 
	 */
	@Test(expected = SimulationException.class)
	public void testParkVehicle_InvalidTimeConstraints() throws VehicleException, SimulationException {
	}
	
	
	/**
	 * TODO
	 * Tests if an exception is throw when we try
	 * adding a car with an invalid state to a carpark
	 * @throws VehicleException
	 * @author Thomas McCarthy
	 * @throws SimulationException 
	 */
	@Test(expected = SimulationException.class)
	public void testParkVehicle_InvalidState() throws VehicleException, SimulationException {
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
		Car testCar = new Car(EXAMPLE_PLATE, EXAMPLE_ARRIVAL_TIME, false);
		for(int i = 0; i < EXAMPLE_LOOP; i++)  {
		testCarPark.parkVehicle(testCar, testCar.getArrivalTime(), EXAMPLE_INTENDED_DURATION);
		}
		assertTrue(testCarPark.getNumCars() == EXAMPLE_LOOP);
	}
	
	
	
	/**
	 * Tests if we can correctly get the number of small cars
	 * in the carpark
	 * @throws VehicleException
	 * @author Thomas McCarthy
	 * @throws SimulationException 
	 */
	@Test
	public void testNumSmallCars() throws VehicleException, SimulationException{
		Car testSmallCar = new Car(EXAMPLE_PLATE, EXAMPLE_ARRIVAL_TIME, true);
		Car testCar = new Car(EXAMPLE_PLATE, EXAMPLE_ARRIVAL_TIME, false);
		
		// adding a non small car to make sure it's excluded in the final count
		testCarPark.parkVehicle(testCar, testCar.getArrivalTime(), EXAMPLE_INTENDED_DURATION);
		
		for(int i = 0; i < EXAMPLE_LOOP; i++)  {
		testCarPark.parkVehicle(testSmallCar, testSmallCar.getArrivalTime(), EXAMPLE_INTENDED_DURATION);
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
		MotorCycle testBike= new MotorCycle(EXAMPLE_PLATE, EXAMPLE_ARRIVAL_TIME);
		Car testCar = new Car(EXAMPLE_PLATE, EXAMPLE_ARRIVAL_TIME, false);
		
		// adding something that isnt a motorcycle to make sure it's excluded in the final count
		testCarPark.parkVehicle(testCar, testCar.getArrivalTime(), EXAMPLE_INTENDED_DURATION);
		
		for(int i = 0; i < EXAMPLE_LOOP; i++)  {
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
	public void testVehiclesInQueue() throws VehicleException, SimulationException{
		Car testCar = new Car(EXAMPLE_PLATE, EXAMPLE_ARRIVAL_TIME, false);
		testCarPark.enterQueue(testCar);
		assertTrue(testCarPark.numVehiclesInQueue() == 1);
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
		assertTrue(testCarPark.carParkEmpty() == true);
	}
	
	
	/**
	 * Tests if an exception is thrown when we try to 
	 * remove a car from a carpark when it isn't in there
	 * @throws VehicleException
	 * @author Thomas McCarthy
	 * @throws SimulationException 
	 */
	@Test(expected = VehicleException.class)
	public void testRemoveFromCarpark_WithoutBeingParked() throws VehicleException, SimulationException{
		Car testCar = new Car(EXAMPLE_PLATE, EXAMPLE_ARRIVAL_TIME, false);
		testCarPark.unparkVehicle(testCar, EXAMPLE_DEPARTURE_TIME);
	}
}
