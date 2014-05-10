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
import asgn2Simulators.Constants;
import asgn2Vehicles.Vehicle;
import asgn2Vehicles.Car;

/**
 * @author hogan
 *
 */
public class CarTests {
	
	private Vehicle testVehicle;
	
	/**
	 * Testing the creation of a normal Vehicle Object
	 * @throws VehicleException
	 * @author Ken Lewis
	 */
	@Test
	public void testNewCarCreation() throws VehicleException{
		
		testVehicle = new Car ("1234Test", 10, false);
	}
	
	/**
	 * Testing the creation of a small car object
	 * @throws VehicleException
	 * @author Ken Lewis
	 */	
	@Test
	public void testNewSmallCarCreation() throws VehicleException {
		
		testVehicle = new Car ("1234Test", 10, true);
	}
	
	/**
	 * Testing the creation of a small car object with a low
	 * arrival time which should throw an error as the time
	 * cannot be <= 0
	 * @throws VehicleException
	 * @author Ken Lewis
	 */	
	@Test (expected = VehicleException.class)
	public void testLowArrivalTime() throws VehicleException {
		
		testVehicle = new Car ("1234Test", 0, true);
	}
	
	/**
	 * Testing the creation of a small car object with a negative
	 * arrival time which should throw an error as the time
	 * cannot be <= 0
	 * @throws VehicleException
	 * @author Ken Lewis
	 */	
	@Test (expected = VehicleException.class)
	public void testNegativeArrivalTime() throws VehicleException {
		
		testVehicle = new Car ("1234Test", -8, true);
	}
	
	/**
	 * Testing the creation of a small car object with a negative
	 * arrival time which should throw an error as the time
	 * cannot be <= 0
	 * @throws VehicleException
	 * @author Ken Lewis
	 */	
	@Test (expected = VehicleException.class)
	public void testLargeNegativeArrivalTime() throws VehicleException {
		
		testVehicle = new Car ("1234Test", -100000, true);
	}
	
	/**
	 * Testing the creation of a large car object with a negative
	 * arrival time which should throw an error as the time
	 * cannot be <= 0
	 * @throws VehicleException
	 * @author Ken Lewis
	 */	
	@Test (expected = VehicleException.class)
	public void testLargeCarNegativeArrivalTime() throws VehicleException {
		
		testVehicle = new Car ("1234Test", -8, false);
	}
	
	/**
	 * Testing the method in vehicle which changes the state of
	 * the vehicle once it arrives to the CarPark. Should change
	 * state to parked and should be able to register that the 
	 * car is parked.
	 * @throws VehicleException
	 * @author Ken Lewis
	 */	
	@Test
	public void parkVehicle() throws VehicleException {
		
		testVehicle = new Car ("1234Test", 10, false);
		testVehicle.enterParkedState(10, 20);
	}
	
	/**
	 * Testing the method in vehicle which changes the state of
	 * the vehicle once it arrives to the CarPark. Should throw
	 * an exception due to the same car already being parked.
	 * @throws VehicleException
	 * @author Ken Lewis
	 */	
	@Test (expected = VehicleException.class)
	public void parkVehicleAlreadyParked() throws VehicleException {
		
		testVehicle = new Car ("1234Test", 10, false);
		testVehicle.enterParkedState(10, 20);
		testVehicle.enterParkedState(10,20);
	}
	
	/**
	 * Testing the method in vehicle which changes the state of
	 * the vehicle once it arrives to the CarPark. Should throw
	 * an exception due to the same car already being parked.
	 * @throws VehicleException
	 * @author Ken Lewis
	 */	
	@Test (expected = VehicleException.class)
	public void parkVehicleWhichIsInQue() throws VehicleException {
		
		testVehicle = new Car ("1234Test", 10, false);
		testVehicle.enterParkedState(10, 20);
		testVehicle.enterParkedState(10,20);
	}
	
	

	
/*public void enterParkedState(int parkingTime, int intendedDuration) throws VehicleException {
		
		if(vehicleState.contains("P") || vehicleState.contains("Q"))
			throw new VehicleException ("Vehicle is currently either already parked"
					+ " or in the que to enter the CarPark.\n");
		else if (parkingTime < 0)
			throw new VehicleException ("Vehicle parking time cannot be less than 0\n");
		else if (intendedDuration < Constants.MINIMUM_STAY)
			throw new VehicleException ("Intended duration cannot be less than the" +
					" Minimum Duration\n");
		else {
			this.parkingTime = parkingTime;
			this.intendedDuration = intendedDuration;
		}
	}*/
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link asgn2Vehicles.Car#toString()}.
	 */
	@Test
	public void testToString() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link asgn2Vehicles.Car#Car(java.lang.String, int, boolean)}.
	 */
	@Test
	public void testCar() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link asgn2Vehicles.Car#isSmall()}.
	 */
	@Test
	public void testIsSmall() {
		fail("Not yet implemented"); // TODO
	}

}
