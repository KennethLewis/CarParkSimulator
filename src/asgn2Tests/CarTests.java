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
	 * an exception due to the same car already being in Que.
	 * @throws VehicleException
	 * @author Ken Lewis
	 */	
	
	//SHOULDNT WE BE ABLE TO PARK A CAR IF IT IS IN QUE!!!???
	@Test (expected = VehicleException.class)
	public void parkVehicleWhichIsInQue() throws VehicleException {
		
		testVehicle = new Car ("1234Test", 10, false);
		testVehicle.enterParkedState(10, 20);
		testVehicle.enterQueuedState();
		testVehicle.enterParkedState(10,20);
	}
	
	/**
	 * Testing the method in vehicle which changes the state of
	 * the vehicle once it arrives to the CarPark. Should throw
	 * an exception due to the parking time being < 0.
	 * @throws VehicleException
	 * @author Ken Lewis
	 */	
	@Test (expected = VehicleException.class)
	public void parkVehicleWithNegParkingTime() throws VehicleException {
		
		testVehicle = new Car ("1234Test", 10, false);
		testVehicle.enterParkedState(-5, 20);
	}
	
	/**
	 * Testing the method in vehicle which changes the state of
	 * the vehicle once it arrives to the CarPark. Should throw
	 * an exception due to the intendedDuration being less then
	 * the min stay requirements.
	 * @throws VehicleException
	 * @author Ken Lewis
	 */	
	@Test (expected = VehicleException.class)
	public void parkVehicleWithLowIntendedDur() throws VehicleException {
		
		testVehicle = new Car ("1234Test", 10, false);
		testVehicle.enterParkedState(10, 19);
	}
	
	/**
	 * Testing the method in vehicle which changes the state of
	 * the vehicle once it arrives to the CarPark. Should throw
	 * an exception due to the parking time and intendedDuration
	 * being less then the minimum requirements.
	 * @throws VehicleException
	 * @author Ken Lewis
	 */	
	@Test (expected = VehicleException.class)
	public void parkVehicleWithBothBadVariables() throws VehicleException {
		
		testVehicle = new Car ("1234Test", 10, false);
		testVehicle.enterParkedState(-8, 19);
	}
	
	/**
	 * Testing the method in vehicle which changes the state of
	 * the vehicle once it arrives to the CarPark. Should throw
	 * an exception due to the intendedDuration being far less 
	 * then the minimum requirements.
	 * @throws VehicleException
	 * @author Ken Lewis
	 */	
	@Test (expected = VehicleException.class)
	public void parkVehicleWithVeryLowIntendedDur() throws VehicleException {
		
		testVehicle = new Car ("1234Test", 10, false);
		testVehicle.enterParkedState(10, -19);
	}
	
	/**
	 * Testing to check that both the parkingTime is correctly 
	 * registered and stored in the Vehicle/Car object
	 * @throws VehicleException
	 * @author Ken Lewis
	 */	
	@Test
	public void testParkTimeVar() throws VehicleException {
		
		testVehicle = new Car ("1234Test", 10, false);
		testVehicle.enterParkedState(10, 20);
		assertTrue(testVehicle.getParkingTime() == 10);
	}

	//MAYBE TEST INTENDEDDURATION VARIABLE AS WELL? NO GETTER IN VEHICLE CLASS
	//NOT SURE IF WE CAN ADD IT. CHECK!

	/**
	 * Testing enteredQueuedState method to ensure the car can
	 * enter into the que if the carpark is currently full.
	 * @throws VehicleException
	 * @author Ken Lewis
	 */	
	@Test
	public void testEnteringQue() throws VehicleException {
		
		testVehicle = new Car ("1234Test",10,false);
		testVehicle.enterQueuedState();
		assertTrue(testVehicle.wasQueued() == true);
	}
	
	/**
	 * Testing enteredQueuedState method to ensure the car can
	 * cannot enter the que once parked.
	 * @throws VehicleException
	 * @author Ken Lewis
	 */	
	@Test (expected = VehicleException.class)
	public void testEnteringQueWhileParked() throws VehicleException {
		
		testVehicle = new Car ("1234Test",10,false);
		testVehicle.enterParkedState(10, 20);
		testVehicle.enterQueuedState();
	}
	
	/**
	 * Testing enteredQueuedState method to ensure the car can
	 * cannot enter the que once queued.
	 * @throws VehicleException
	 * @author Ken Lewis
	 */	
	@Test (expected = VehicleException.class)
	public void testEnteringQueWhileQueued() throws VehicleException {
		
		testVehicle = new Car ("1234Test",10,false);
		testVehicle.enterQueuedState();
		testVehicle.enterQueuedState();
	}
	
	/**
	 * Testing exitParkedState method to ensure the car can exit
	 * its parked state.
	 * @throws VehicleException
	 * @author Ken Lewis
	 */	
	@Test
	public void testExitParkedState() throws VehicleException {
		
		testVehicle = new Car ("1234Test",10,false);
		testVehicle.enterParkedState(10,20);
		testVehicle.exitParkedState(12);
	}
	
	
	/**
	 * Testing enteredQueuedState method to ensure the car cannot
	 * cannot leave parked state if it was never parked in the first
	 * place
	 * @throws VehicleException
	 * @author Ken Lewis
	 */	
	@Test (expected = VehicleException.class)
	public void exitingParkWithoutParking() throws VehicleException {
		
		testVehicle = new Car ("1234Test",10,false);
		testVehicle.exitParkedState(12);
	}
	
	/**
	 * Testing enteredQueuedState method to ensure the car cannot
	 * cannot leave parked state if it was only in que.
	 * @throws VehicleException
	 * @author Ken Lewis
	 */	
	@Test (expected = VehicleException.class)
	public void exitingParkWhileInQue() throws VehicleException {
		
		testVehicle = new Car ("1234Test",10,false);
		testVehicle.enterQueuedState();
		testVehicle.exitParkedState(12);
	}
	
/*public void exitParkedState(int departureTime) throws VehicleException {
		
		if(vehicleState.contains("P") == false || vehicleState.contains("Q"))
			throw new VehicleException ("Vehicle  cannot exit parked state, as it is" +
					" currently either not parked or in the que to enter the CarPark.\n");
		else if (departureTime < this.parkingTime)
			throw new VehicleException ("Revised departure time cannot be less than" +
					" the parking time.\n");
		//Method enterParkedState mentions departure time is parkingTime + 
		//intendedDuration yields the departureTime
		else
			this.departureTime = departureTime;
			
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
