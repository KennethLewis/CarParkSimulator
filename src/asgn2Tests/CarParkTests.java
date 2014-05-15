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
	private static final int EXAMPLE_SMALL_SPACES = 5;
	private static final int EXAMPLE_CYCLE_SPACES = 3;
	private static final int EXAMPLE_QUEUE_SIZE = 5;
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
	 * TODO  Tests if the car park is correctly returning
	 * true when it is empty
	 * @throws VehicleException
	 * @author Thomas McCarthy
	 */
	@Test
	public void testFullCarPark() throws VehicleException{
		testCarPark = new CarPark(EXAMPLE_SPACES, EXAMPLE_SMALL_SPACES,
								  EXAMPLE_CYCLE_SPACES, EXAMPLE_QUEUE_SIZE);
		testCarPark.parkVehicle(testVehicle, time, intendedDuration);
		
	}
}
