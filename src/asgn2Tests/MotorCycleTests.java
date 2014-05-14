/**
 * 
 * This file is part of the CarParkSimulator Project, written as 
 * part of the assessment for INB370, semester 1, 2014. 
 *
 * CarParkSimulator
 * asgn2Tests 
 * 22/04/2014
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
import asgn2Vehicles.Vehicle;


public class MotorCycleTests {
	private static final String EXAMPLE_PLATE = "1234Test";
	private static final int EXAMPLE_TIME = 10;
	private Vehicle testBike;

	@Before
	/**
	 * Creates a new bike before every test
	 * @author Thomas McCarthy
	 */
	public void testCreateTestBike() throws VehicleException {
		testBike = new MotorCycle (EXAMPLE_PLATE, EXAMPLE_TIME);
	}

	
	/**
	 * Testing the creation of a normal motorcycle Object
	 * @throws VehicleException
	 * @author Thomas McCarthy
	 */
	@Test
	public void testNewMotorcycleCreation() throws VehicleException{
		assertTrue(testBike != null);
	}
	
	/**
	 * Tests if the constructor throws an exception if provided arrival
	 * time <= 0
	 * @author Thomas McCarthy
	 * @throws VehicleException
	 */
	@Test (expected = VehicleException.class)
	public void testLowArrivalTime() throws VehicleException {
		
		testBike = new MotorCycle(EXAMPLE_PLATE, 0);
	}
	
}