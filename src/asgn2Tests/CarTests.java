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
	private static final String EXAMPLE_PLATE = "1234Test";
	private static final int EXAMPLE_TIME = 10;
	/**
	 * Testing the creation of a normal Vehicle Object
	 * @throws VehicleException
	 * @author Ken Lewis
	 */
	@Test
	public void testNewCarCreation() throws VehicleException{
		
		testVehicle = new Car (EXAMPLE_PLATE, EXAMPLE_TIME, false);
		assertTrue(testVehicle != null);
	}
	
	/**
	 * Testing the creation of a small car object
	 * @throws VehicleException
	 * @author Ken Lewis
	 */	
	@Test
	public void testNewSmallCarCreation() throws VehicleException {
		
		testVehicle = new Car (EXAMPLE_PLATE, EXAMPLE_TIME, true);
		assertTrue (testVehicle != null);
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
		
		testVehicle = new Car (EXAMPLE_PLATE, 0, true);
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
		
		testVehicle = new Car (EXAMPLE_PLATE, -8, true);
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
		
		testVehicle = new Car (EXAMPLE_PLATE, -100000, true);
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
		
		testVehicle = new Car (EXAMPLE_PLATE, -8, false);
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
		
		testVehicle = new Car (EXAMPLE_PLATE, EXAMPLE_TIME, false);
		testVehicle.enterParkedState(10, 20);
		assertTrue(testVehicle.isParked() == true);
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
		
		testVehicle = new Car (EXAMPLE_PLATE, EXAMPLE_TIME, false);
		testVehicle.enterParkedState(10, 20);
		testVehicle.enterParkedState(10,20);
	}
	
	/**
	 * Testing the method in vehicle which changes the state of
	 * the vehicle once it arrives to the CarPark. Should throw
	 * an exception due to the same car already being in Queue.
	 * @throws VehicleException
	 * @author Ken Lewis
	 */	
	
	//SHOULDNT WE BE ABLE TO PARK A CAR IF IT IS IN QUEUE!!!???
	@Test (expected = VehicleException.class)
	public void parkVehicleWhichIsInQue() throws VehicleException {
		
		testVehicle = new Car (EXAMPLE_PLATE, EXAMPLE_TIME, false);
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
		
		testVehicle = new Car (EXAMPLE_PLATE, EXAMPLE_TIME, false);
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
		
		testVehicle = new Car (EXAMPLE_PLATE, EXAMPLE_TIME, false);
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
		
		testVehicle = new Car (EXAMPLE_PLATE, EXAMPLE_TIME, false);
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
		
		testVehicle = new Car (EXAMPLE_PLATE, EXAMPLE_TIME, false);
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
		
		testVehicle = new Car (EXAMPLE_PLATE, EXAMPLE_TIME, false);
		testVehicle.enterParkedState(10, 20);
		assertTrue(testVehicle.getParkingTime() == 10);
	}

	//MAYBE TEST INTENDEDDURATION VARIABLE AS WELL? NO GETTER IN VEHICLE CLASS
	//NOT SURE IF WE CAN ADD IT. CHECK!

	/**
	 * Testing enteredQueuedState method to ensure the car can
	 * enter into the queue if the carpark is currently full.
	 * @throws VehicleException
	 * @author Ken Lewis
	 */	
	@Test
	public void testEnteringQue() throws VehicleException {
		
		testVehicle = new Car (EXAMPLE_PLATE, EXAMPLE_TIME, false);
		testVehicle.enterQueuedState();
		assertTrue(testVehicle.wasQueued() == true);
	}
	
	/**
	 * Testing enteredQueuedState method to ensure the car can
	 * cannot enter the queue once parked.
	 * @throws VehicleException
	 * @author Ken Lewis
	 */	
	@Test (expected = VehicleException.class)
	public void testEnteringQueWhileParked() throws VehicleException {
		
		testVehicle = new Car (EXAMPLE_PLATE, EXAMPLE_TIME, false);
		testVehicle.enterParkedState(10, 20);
		testVehicle.enterQueuedState();
	}
	
	/**
	 * Testing enteredQueuedState method to ensure the car can
	 * cannot enter the queue once queued.
	 * @throws VehicleException
	 * @author Ken Lewis
	 */	
	@Test (expected = VehicleException.class)
	public void testEnteringQueWhileQueued() throws VehicleException {
		
		testVehicle = new Car (EXAMPLE_PLATE, EXAMPLE_TIME, false);
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
		
		testVehicle = new Car (EXAMPLE_PLATE, EXAMPLE_TIME, false);
		testVehicle.enterParkedState(10,20);
		testVehicle.exitParkedState(12);
		assertTrue(testVehicle.wasParked() == true);
	}
	
	/**
	 * Testing exitParkedState method to ensure the car can exit
	 * its parked state and the variable departureTime is correct.
	 * @throws VehicleException
	 * @author Ken Lewis
	 */	
	@Test
	public void testExitParkedStateDepartTime() throws VehicleException {
		
		testVehicle = new Car (EXAMPLE_PLATE, EXAMPLE_TIME, false);
		testVehicle.enterParkedState(10,20);
		testVehicle.exitParkedState(12);
		assertTrue(testVehicle.getDepartureTime() == 22);
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
		
		testVehicle = new Car (EXAMPLE_PLATE, EXAMPLE_TIME, false);
		testVehicle.exitParkedState(12);
	}
	
	/**
	 * Testing enteredQueuedState method to ensure the car cannot
	 * cannot leave parked state if it was only in queue.
	 * @throws VehicleException
	 * @author Ken Lewis
	 */	
	@Test (expected = VehicleException.class)
	public void exitingParkWhileInQue() throws VehicleException {
		
		testVehicle = new Car (EXAMPLE_PLATE, EXAMPLE_TIME, false);
		testVehicle.enterQueuedState();
		testVehicle.exitParkedState(12);
	}
	
	/**
	 * Testing enteredQueuedState method to that the departure time
	 * is not less than the parking time.
	 * @throws VehicleException
	 * @author Ken Lewis
	 */	
	@Test (expected = VehicleException.class)
	public void testingDepartTimeVsParkTime() throws VehicleException {
		
		testVehicle = new Car (EXAMPLE_PLATE, EXAMPLE_TIME, false);
		testVehicle.enterParkedState(10,20);
		testVehicle.exitParkedState(9);
	}
	
	/**
	 * Testing enteredQueuedState method to that the departure time
	 * variable if a large negative.
	 * @throws VehicleException
	 * @author Ken Lewis
	 */	
	@Test (expected = VehicleException.class)
	public void testingLargeNegDepartTimeVsParkTime() throws VehicleException {
		
		testVehicle = new Car (EXAMPLE_PLATE, EXAMPLE_TIME, false);
		testVehicle.enterParkedState(10,20);
		testVehicle.exitParkedState(-99999);
	}
	
	/**
	 * Testing exitQuedState to ensure that the vehicle leaves
	 * the queue and becomes parked.
	 * @throws VehicleException
	 * @author Ken Lewis
	 */	
	@Test
	public void testingExitQueForCorrectVars() throws VehicleException {
		
		testVehicle = new Car (EXAMPLE_PLATE, EXAMPLE_TIME, false);
		testVehicle.enterQueuedState();
		testVehicle.exitQueuedState(11);
		assertTrue(testVehicle.wasParked() == true);
	}
	
	/**
	 * Testing exitQuedState to ensure that the vehicle cannot
	 * be first parked but then able to exit the queue.
	 * @throws VehicleException
	 * @author Ken Lewis
	 */	
	@Test (expected = VehicleException.class)
	public void testingParkedExitQueue() throws VehicleException {
		
		testVehicle = new Car (EXAMPLE_PLATE, EXAMPLE_TIME, false);
		testVehicle.enterParkedState(10, 20);
		testVehicle.exitQueuedState(10);
	}
	
	/**
	 * Testing exitQuedState to ensure that the vehicle is actually
	 * in the queue in order to be able to leave it.
	 * @throws VehicleException
	 * @author Ken Lewis
	 */	
	@Test (expected = VehicleException.class)
	public void testingNotAlreadyQueued() throws VehicleException {
		
		testVehicle = new Car (EXAMPLE_PLATE, EXAMPLE_TIME, false);
		testVehicle.exitQueuedState(11);
	}
	
	/**
	 * Testing arrival time to ensure correct variable is 
	 * returned.
	 * @throws VehicleException
	 * @author Ken Lewis
	 */	
	@Test
	public void testingArrivalTime() throws VehicleException {
		
		testVehicle = new Car (EXAMPLE_PLATE, EXAMPLE_TIME, false);
		assertTrue(testVehicle.getArrivalTime() == 10);
	}
	
	/**
	 * Testing getDepartureTime to make sure the times are
	 * matching. This should return the indendedDuration of
	 * the park as the vehicle hasn't left the park
	 * yet.
	 * @throws VehicleException
	 * @author Ken Lewis
	 */	
	@Test
	public void testingDepartureTime() throws VehicleException {
		
		testVehicle = new Car (EXAMPLE_PLATE, EXAMPLE_TIME, false);
		testVehicle.enterParkedState(11, 20);
		testVehicle.exitParkedState(12);
		assertTrue(testVehicle.getDepartureTime() == 23);
	}
	
	/**
	 * Testing getDepartureTime to make sure the times are
	 * matching. This should return 0 as the car has neither
	 * parked yet or entered an intended duration ie could be
	 * queued (as tested below).
	 * @throws VehicleException
	 * @author Ken Lewis
	 */	
	@Test
	public void testingQueuedDepartureTime() throws VehicleException {
		
		testVehicle = new Car (EXAMPLE_PLATE, EXAMPLE_TIME, false);
		testVehicle.enterQueuedState();
		assertTrue(testVehicle.getDepartureTime() == 0);
	}
	
	/**
	 * Testing getDepartureTime to make sure the times are
	 * matching. This should return the parking time + the
	 * intended duration as the car has not yet left its 
	 * parked state.
	 * @throws VehicleException
	 * @author Ken Lewis
	 */	
	@Test
	public void testingParkedDepartureTime() throws VehicleException {
		
		testVehicle = new Car (EXAMPLE_PLATE, EXAMPLE_TIME, false);
		testVehicle.enterParkedState(10,20);
		assertTrue(testVehicle.getDepartureTime() == 30);
	}
	
	/**
	 * Testing getDepartureTime to make sure the times are
	 * matching. This should return the actually parking time
	 * duration if the car has parked and left the carpark
	 * completely.
	 * @throws VehicleException
	 * @author Ken Lewis
	 */	
	@Test
	public void testingFinalDepartureTime() throws VehicleException {
		
		testVehicle = new Car ("1234Test",10,false);
		testVehicle.enterParkedState(10,20);
		testVehicle.exitParkedState(50);
		assertTrue(testVehicle.getDepartureTime() == 60);
	}
	
	/**
	 * Testing getParking time to ensure the correct variable
	 * is being returned
	 * @throws VehicleException
	 * @author Ken Lewis
	 */	
	@Test
	public void testGetParkingTime() throws VehicleException {
		
		testVehicle = new Car (EXAMPLE_PLATE, EXAMPLE_TIME, false);
		testVehicle.enterParkedState(10,20);
		assertTrue(testVehicle.getParkingTime() == 10);
	}
	
	/**
	 * Testing getVehicleID to ensure the correct variable
	 * is being returned
	 * @throws VehicleException
	 * @author Ken Lewis
	 */	
	@Test
	public void testVehicleID() throws VehicleException {
		
		testVehicle = new Car (EXAMPLE_PLATE, EXAMPLE_TIME, false);
		assertTrue(testVehicle.getVehID().equals("1234Test"));
	}
	
	/**
	 * Testing isParked method to ensure that if the car is 
	 * currently parked that it will return the correct boolean
	 * of true
	 * @throws VehicleException
	 * @author Ken Lewis
	 */	
	@Test
	public void testIsParked() throws VehicleException {
		
		testVehicle = new Car (EXAMPLE_PLATE, EXAMPLE_TIME, false);
		testVehicle.enterParkedState(10,20);
		assertTrue(testVehicle.isParked() ==  true);
	}
	
	/**
	 * Testing isParked method to ensure that if the was parked
	 * but is no longer parked and has exited the carpark that
	 * the method will not return true
	 * @throws VehicleException
	 * @author Ken Lewis
	 */	
	@Test
	public void testIsParkedAfterLeaving() throws VehicleException {
		
		testVehicle = new Car (EXAMPLE_PLATE, EXAMPLE_TIME, false);
		testVehicle.enterParkedState(10,20);
		testVehicle.exitParkedState(12);
		assertTrue(testVehicle.isParked() ==  false);
	}
	
	/**
	 * Testing isParked method to ensure that if the car was
	 * in queue that it will not return that the car is parked.
	 * @throws VehicleException
	 * @author Ken Lewis
	 */	
	@Test
	public void testIsParkedWhileInQueue() throws VehicleException {
		
		testVehicle = new Car (EXAMPLE_PLATE, EXAMPLE_TIME, false);
		testVehicle.enterQueuedState();
		assertTrue(testVehicle.isParked() ==  false);
	}
	
	/**
	 * Testing isQueued method to make sure that if the car
	 * is currently in queue that it will return the correct
	 * state.
	 * @throws VehicleException
	 * @author Ken Lewis
	 */	
	@Test
	public void testIsQueued() throws VehicleException {
		
		testVehicle = new Car (EXAMPLE_PLATE, EXAMPLE_TIME, false);
		testVehicle.enterQueuedState();
		assertTrue(testVehicle.isQueued() == true);
	}
	
	/**
	 * Testing isQueued method to make sure that if the car
	 * is currently in queue that it will return the correct
	 * state.
	 * @throws VehicleException
	 * @author Ken Lewis
	 */	
	@Test
	public void testIsNotQueued() throws VehicleException {
		
		testVehicle = new Car (EXAMPLE_PLATE, EXAMPLE_TIME, false);
		testVehicle.enterParkedState(10,20);
		assertTrue(testVehicle.isQueued() == false);
	}
	
	/* TODO
	 * Still to write 
	 * IS SATISFIED
	 */
	
	/* TODO
	 *  Still to write 
	 * TOSTRING
	 */
	
	/**
	 * Testing was parked method to ensure that if the car was 
	 * in a parked state that it would be returned correctly
	 * @throws VehicleException
	 * @author Ken Lewis
	 */	
	@Test
	public void wasParked() throws VehicleException {
		
		testVehicle = new Car (EXAMPLE_PLATE, EXAMPLE_TIME, false);
		testVehicle.enterParkedState(10,20);
		assertTrue(testVehicle.wasParked() == true);
	}
	
	/**
	 * Testing was parked method to ensure that if the car was 
	 * in a parked state that it would be returned correctly
	 * @throws VehicleException
	 * @author Ken Lewis
	 */	
	@Test
	public void inQueueNotParked() throws VehicleException {
		
		testVehicle = new Car (EXAMPLE_PLATE, EXAMPLE_TIME, false);
		testVehicle.enterQueuedState();
		assertTrue(testVehicle.wasParked() == false);
	}
	

	/**
	 * Testing was parked method to ensure that if the car was 
	 * was in a parked state that it would be returned correctly
	 * @throws VehicleException
	 * @author Ken Lewis
	 */	
	@Test
	public void hasParkedAndLeft() throws VehicleException {
		
		testVehicle = new Car (EXAMPLE_PLATE, EXAMPLE_TIME, false);
		testVehicle.enterParkedState(10,20);
		testVehicle.exitParkedState(12);
		assertTrue(testVehicle.wasParked() == true);
	}

	/**
	 * Testing the was queued method to check to see if at any time
	 * the car was queued.
	 * @throws VehicleException
	 * @author Ken Lewis
	 */	
	@Test
	public void wasQueued() throws VehicleException {
		
		testVehicle = new Car (EXAMPLE_PLATE, EXAMPLE_TIME, false);
		testVehicle.enterQueuedState();
		testVehicle.exitQueuedState(11);
		testVehicle.exitParkedState(12);
		assertTrue(testVehicle.wasQueued() == true);
	}
	
	/**
	 * Testing the was queued method to check to see if the car
	 * wasn't queued that it will return the correct result.
	 * @throws VehicleException
	 * @author Ken Lewis
	 */	
	@Test
	public void wasNotQueued() throws VehicleException {
		
		testVehicle = new Car (EXAMPLE_PLATE, EXAMPLE_TIME, false);
		testVehicle.enterParkedState(10,20);
		testVehicle.exitParkedState(12);
		assertTrue(testVehicle.wasQueued() == false);
	}
	

}
