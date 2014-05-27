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

import java.lang.reflect.Array;

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
	private static final int EXAMPLE_DURATION = 20;
	private static final int LOW_DURATION = 0;
	private static final int LOW_ARRIVAL_TIME = 0;
	private static final int NEG_ARRIVAL_TIME = -8;
	private static final int NEG_PARKING_TIME = -8;
	private static final int UNDER_DEFAULT_STAY = 19;
	private static final int NEG_DEFAULT_STAY = -19;
	private static final int NORM_LEAVE_TIME = 12;
	private static final int LOW_LEAVE_TIME = 9;
	private static final int NEG_LEAVE_TIME = -9999;
	private static final int NORM_QUEUE_LEAVE_TIME = 11;

	/*
	 * Confirm that the API spec has not been violated through the addition of
	 * public fields, constructors or methods that were not requested
	 */
	@Test
	public void NoExtraPublicMethods() {
		// Car Class implements Vehicle, adds isSmall()
		final int NumVehicleClassMethods = Array.getLength(Vehicle.class
				.getMethods());
		final int NumCarClassMethods = Array.getLength(Car.class.getMethods());
		assertTrue("veh:" + NumVehicleClassMethods + ":car:"
				+ NumCarClassMethods,
				(NumVehicleClassMethods + 1) == NumCarClassMethods);
	}

	@Test
	public void NoExtraPublicFields() {
		// Same as Vehicle
		final int NumVehicleClassFields = Array.getLength(Vehicle.class
				.getFields());
		final int NumCarClassFields = Array.getLength(Car.class.getFields());
		assertTrue(
				"veh:" + NumVehicleClassFields + ":car:" + NumCarClassFields,
				(NumVehicleClassFields) == NumCarClassFields);
	}

	@Test
	public void NoExtraPublicConstructors() {
		// Same as Vehicle
		final int NumVehicleClassConstructors = Array.getLength(Vehicle.class
				.getConstructors());
		final int NumCarClassConstructors = Array.getLength(Car.class
				.getConstructors());
		assertTrue(":veh:" + NumVehicleClassConstructors + ":car:"
				+ NumCarClassConstructors,
				(NumVehicleClassConstructors) == NumCarClassConstructors);
	}

	/**
	 * Testing the creation of a normal Vehicle Object
	 * 
	 * @throws VehicleException
	 * @author Ken Lewis
	 */
	@Test
	public void testNewCarCreation() throws VehicleException {

		testVehicle = new Car(EXAMPLE_PLATE, EXAMPLE_TIME, false);
		assertTrue(testVehicle != null);
	}

	/**
	 * Testing the creation of a small car object
	 * 
	 * @throws VehicleException
	 * @author Ken Lewis
	 */
	@Test
	public void testNewSmallCarCreation() throws VehicleException {

		testVehicle = new Car(EXAMPLE_PLATE, EXAMPLE_TIME, true);
		assertTrue(testVehicle != null);
	}

	/**
	 * Testing the creation of a small car object with a low arrival time which
	 * should throw an error as the time cannot be <= 0
	 * 
	 * @throws VehicleException
	 * @author Ken Lewis
	 */
	@Test(expected = VehicleException.class)
	public void testLowArrivalTime() throws VehicleException {

		testVehicle = new Car(EXAMPLE_PLATE, LOW_ARRIVAL_TIME, true);
	}

	/**
	 * Testing the creation of a small car object with a negative arrival time
	 * which should throw an error as the time cannot be <= 0
	 * 
	 * @throws VehicleException
	 * @author Ken Lewis
	 */
	@Test(expected = VehicleException.class)
	public void testNegativeArrivalTime() throws VehicleException {

		testVehicle = new Car(EXAMPLE_PLATE, NEG_ARRIVAL_TIME, true);
	}

	/**
	 * Testing the method in vehicle which changes the state of the vehicle once
	 * it arrives to the CarPark. Should change state to parked and should be
	 * able to register that the car is parked.
	 * 
	 * @throws VehicleException
	 * @author Ken Lewis
	 */
	@Test
	public void parkVehicle() throws VehicleException {

		testVehicle = new Car(EXAMPLE_PLATE, EXAMPLE_TIME, false);
		testVehicle.enterParkedState(EXAMPLE_TIME, EXAMPLE_DURATION);
		assertTrue(testVehicle.isParked() == true);
	}

	/**
	 * Testing the method in vehicle which changes the state of the vehicle once
	 * it arrives to the CarPark. Should throw an exception due to the same car
	 * already being parked.
	 * 
	 * @throws VehicleException
	 * @author Ken Lewis
	 */
	@Test(expected = VehicleException.class)
	public void parkVehicleAlreadyParked() throws VehicleException {

		testVehicle = new Car(EXAMPLE_PLATE, EXAMPLE_TIME, false);
		testVehicle.enterParkedState(EXAMPLE_TIME, EXAMPLE_DURATION);
		testVehicle.enterParkedState(EXAMPLE_TIME, EXAMPLE_DURATION);
	}

	/**
	 * Testing the method in vehicle which changes the state of the vehicle once
	 * it arrives to the CarPark. Should throw an exception due to the same car
	 * already being in Queue.
	 * 
	 * @throws VehicleException
	 * @author Ken Lewis
	 */

	// SHOULDNT WE BE ABLE TO PARK A CAR IF IT IS IN QUEUE!!!???
	@Test(expected = VehicleException.class)
	public void parkVehicleWhichIsInQue() throws VehicleException {

		testVehicle = new Car(EXAMPLE_PLATE, EXAMPLE_TIME, false);
		testVehicle.enterParkedState(EXAMPLE_TIME, EXAMPLE_DURATION);
		testVehicle.enterQueuedState();
		testVehicle.enterParkedState(EXAMPLE_TIME, EXAMPLE_DURATION);
	}

	/**
	 * Testing the method in vehicle which changes the state of the vehicle once
	 * it arrives to the CarPark. Should throw an exception due to the parking
	 * time being < 0.
	 * 
	 * @throws VehicleException
	 * @author Ken Lewis
	 */
	@Test(expected = VehicleException.class)
	public void parkVehicleWithNegParkingTime() throws VehicleException {

		testVehicle = new Car(EXAMPLE_PLATE, EXAMPLE_TIME, false);
		testVehicle.enterParkedState(NEG_PARKING_TIME, EXAMPLE_DURATION);
	}

	/**
	 * Testing the method in vehicle which changes the state of the vehicle once
	 * it arrives to the CarPark. Should throw an exception due to the
	 * intendedDuration being less then the min stay requirements.
	 * 
	 * @throws VehicleException
	 * @author Ken Lewis
	 */
	@Test(expected = VehicleException.class)
	public void parkVehicleWithLowIntendedDur() throws VehicleException {

		testVehicle = new Car(EXAMPLE_PLATE, EXAMPLE_TIME, false);
		testVehicle.enterParkedState(EXAMPLE_TIME, NEG_DEFAULT_STAY);
	}

	/**
	 * Testing the method in vehicle which changes the state of the vehicle once
	 * it arrives to the CarPark. Should throw an exception due to the parking
	 * time and intendedDuration being less then the minimum requirements.
	 * 
	 * @throws VehicleException
	 * @author Ken Lewis
	 */
	@Test(expected = VehicleException.class)
	public void parkVehicleWithBothBadVariables() throws VehicleException {

		testVehicle = new Car(EXAMPLE_PLATE, EXAMPLE_TIME, false);
		testVehicle.enterParkedState(NEG_PARKING_TIME, UNDER_DEFAULT_STAY);
	}

	/**
	 * Testing the method in vehicle which changes the state of the vehicle once
	 * it arrives to the CarPark. Should throw an exception due to the
	 * intendedDuration being far less then the minimum requirements.
	 * 
	 * @throws VehicleException
	 * @author Ken Lewis
	 */
	@Test(expected = VehicleException.class)
	public void parkVehicleWithVeryLowIntendedDur() throws VehicleException {

		testVehicle = new Car(EXAMPLE_PLATE, EXAMPLE_TIME, false);
		testVehicle.enterParkedState(EXAMPLE_TIME, LOW_DURATION);
	}

	/**
	 * Testing to check that both the parkingTime is correctly registered and
	 * stored in the Vehicle/Car object
	 * 
	 * @throws VehicleException
	 * @author Ken Lewis
	 */
	@Test
	public void testParkTimeVar() throws VehicleException {

		testVehicle = new Car(EXAMPLE_PLATE, EXAMPLE_TIME, false);
		testVehicle.enterParkedState(EXAMPLE_TIME, EXAMPLE_DURATION);
		assertTrue(testVehicle.getParkingTime() == 10);
	}

	// MAYBE TEST INTENDEDDURATION VARIABLE AS WELL? NO GETTER IN VEHICLE CLASS
	// NOT SURE IF WE CAN ADD IT. CHECK!

	/**
	 * Testing enteredQueuedState method to ensure the car can enter into the
	 * queue if the carpark is currently full.
	 * 
	 * @throws VehicleException
	 * @author Ken Lewis
	 */
	@Test
	public void testEnteringQueue() throws VehicleException {

		testVehicle = new Car(EXAMPLE_PLATE, EXAMPLE_TIME, false);
		testVehicle.enterQueuedState();
		assertTrue(testVehicle.wasQueued() == true);
	}

	/**
	 * Testing enteredQueuedState method to ensure the car can cannot enter the
	 * queue once parked.
	 * 
	 * @throws VehicleException
	 * @author Ken Lewis
	 */
	@Test(expected = VehicleException.class)
	public void testEnteringQueueWhileParked() throws VehicleException {

		testVehicle = new Car(EXAMPLE_PLATE, EXAMPLE_TIME, false);
		testVehicle.enterParkedState(EXAMPLE_TIME, EXAMPLE_DURATION);
		testVehicle.enterQueuedState();
	}

	/**
	 * Testing enteredQueuedState method to ensure the car can cannot enter the
	 * queue once queued.
	 * 
	 * @throws VehicleException
	 * @author Ken Lewis
	 */
	@Test(expected = VehicleException.class)
	public void testEnteringQueueWhileQueued() throws VehicleException {

		testVehicle = new Car(EXAMPLE_PLATE, EXAMPLE_TIME, false);
		testVehicle.enterQueuedState();
		testVehicle.enterQueuedState();
	}

	/**
	 * Testing exitParkedState method to ensure the car can exit its parked
	 * state.
	 * 
	 * @throws VehicleException
	 * @author Ken Lewis
	 */
	@Test
	public void testExitParkedState() throws VehicleException {

		testVehicle = new Car(EXAMPLE_PLATE, EXAMPLE_TIME, false);
		testVehicle.enterParkedState(EXAMPLE_TIME, EXAMPLE_DURATION);
		testVehicle.exitParkedState(NORM_LEAVE_TIME);
		assertTrue(testVehicle.wasParked() == true);
	}

	/**
	 * Testing exitParkedState method to ensure the car can exit its parked
	 * state and the variable departureTime is correct.
	 * 
	 * @throws VehicleException
	 * @author Ken Lewis
	 */
	@Test
	public void testExitParkedStateDepartTime() throws VehicleException {

		testVehicle = new Car(EXAMPLE_PLATE, EXAMPLE_TIME, false);
		testVehicle.enterParkedState(EXAMPLE_TIME, EXAMPLE_DURATION);
		testVehicle.exitParkedState(NORM_LEAVE_TIME);
		assertEquals(testVehicle.getDepartureTime(), 12);
	}

	/**
	 * Testing enteredQueuedState method to ensure the car cannot cannot leave
	 * parked state if it was never parked in the first place
	 * 
	 * @throws VehicleException
	 * @author Ken Lewis
	 */
	@Test(expected = VehicleException.class)
	public void exitingParkWithoutParking() throws VehicleException {

		testVehicle = new Car(EXAMPLE_PLATE, EXAMPLE_TIME, false);
		testVehicle.exitParkedState(NORM_LEAVE_TIME);
	}

	/**
	 * Testing enteredQueuedState method to ensure the car cannot cannot leave
	 * parked state if it was only in queue.
	 * 
	 * @throws VehicleException
	 * @author Ken Lewis
	 */
	@Test(expected = VehicleException.class)
	public void exitingParkWhileInQueue() throws VehicleException {

		testVehicle = new Car(EXAMPLE_PLATE, EXAMPLE_TIME, false);
		testVehicle.enterQueuedState();
		testVehicle.exitParkedState(NORM_LEAVE_TIME);
	}

	/**
	 * Testing enteredQueuedState method to that the departure time is not less
	 * than the parking time.
	 * 
	 * @throws VehicleException
	 * @author Ken Lewis
	 */
	@Test(expected = VehicleException.class)
	public void testingDepartTimeVsParkTime() throws VehicleException {

		testVehicle = new Car(EXAMPLE_PLATE, EXAMPLE_TIME, false);
		testVehicle.enterParkedState(EXAMPLE_TIME, EXAMPLE_DURATION);
		testVehicle.exitParkedState(LOW_LEAVE_TIME);
	}

	/**
	 * Testing enteredQueuedState method to that the departure time variable if
	 * a large negative.
	 * 
	 * @throws VehicleException
	 * @author Ken Lewis
	 */
	@Test(expected = VehicleException.class)
	public void testingLargeNegDepartTimeVsParkTime() throws VehicleException {

		testVehicle = new Car(EXAMPLE_PLATE, EXAMPLE_TIME, false);
		testVehicle.enterParkedState(EXAMPLE_TIME, EXAMPLE_DURATION);
		testVehicle.exitParkedState(NEG_LEAVE_TIME);
	}

	/**
	 * Testing exitQuedState to ensure that the vehicle leaves the queue and
	 * becomes parked.
	 * 
	 * @throws VehicleException
	 * @author Ken Lewis
	 */
	@Test
	public void testExitQueueForCorrectVars() throws VehicleException {

		testVehicle = new Car(EXAMPLE_PLATE, EXAMPLE_TIME, false);
		testVehicle.enterQueuedState();
		testVehicle.exitQueuedState(NORM_QUEUE_LEAVE_TIME);
		testVehicle.enterParkedState(EXAMPLE_TIME, EXAMPLE_DURATION);
		assertTrue(testVehicle.wasParked() == true);
	}

	/**
	 * Testing exitQuedState to ensure that the vehicle cannot be first parked
	 * but then able to exit the queue.
	 * 
	 * @throws VehicleException
	 * @author Ken Lewis
	 */
	@Test(expected = VehicleException.class)
	public void testingParkedExitQueue() throws VehicleException {

		testVehicle = new Car(EXAMPLE_PLATE, EXAMPLE_TIME, false);
		testVehicle.enterParkedState(EXAMPLE_TIME, EXAMPLE_DURATION);
		testVehicle.exitQueuedState(NORM_QUEUE_LEAVE_TIME);
	}

	/**
	 * Testing exitQuedState to ensure that the vehicle is actually in the queue
	 * in order to be able to leave it.
	 * 
	 * @throws VehicleException
	 * @author Ken Lewis
	 */
	@Test(expected = VehicleException.class)
	public void testingNotAlreadyQueued() throws VehicleException {

		testVehicle = new Car(EXAMPLE_PLATE, EXAMPLE_TIME, false);
		testVehicle.exitQueuedState(NORM_QUEUE_LEAVE_TIME);
	}

	/**
	 * Testing arrival time to ensure correct variable is returned.
	 * 
	 * @throws VehicleException
	 * @author Ken Lewis
	 */
	@Test
	public void testingArrivalTime() throws VehicleException {

		testVehicle = new Car(EXAMPLE_PLATE, EXAMPLE_TIME, false);
		assertTrue(testVehicle.getArrivalTime() == 10);
	}

	/**
	 * Testing getDepartureTime to make sure the times are matching. This should
	 * return the indendedDuration of the park as the vehicle hasn't left the
	 * park yet.
	 * 
	 * @throws VehicleException
	 * @author Ken Lewis
	 */
	@Test
	public void testingDepartureTime() throws VehicleException {

		testVehicle = new Car(EXAMPLE_PLATE, EXAMPLE_TIME, false);
		testVehicle.enterParkedState(EXAMPLE_TIME, EXAMPLE_DURATION);
		testVehicle.exitParkedState(12);
		assertTrue(testVehicle.getDepartureTime() == 12);
	}

	/**
	 * Testing getDepartureTime to make sure the times are matching. This should
	 * return 0 as the car has neither parked yet or entered an intended
	 * duration ie could be queued (as tested below).
	 * 
	 * @throws VehicleException
	 * @author Ken Lewis
	 */
	@Test
	public void testingQueuedDepartureTime() throws VehicleException {

		testVehicle = new Car(EXAMPLE_PLATE, EXAMPLE_TIME, false);
		testVehicle.enterQueuedState();
		assertTrue(testVehicle.getDepartureTime() == 0);
	}

	/**
	 * Testing getDepartureTime to make sure the times are matching. This should
	 * return the parking time + the intended duration as the car has not yet
	 * left its parked state.
	 * 
	 * @throws VehicleException
	 * @author Ken Lewis
	 */
	@Test
	public void testingParkedDepartureTime() throws VehicleException {

		testVehicle = new Car(EXAMPLE_PLATE, EXAMPLE_TIME, false);
		testVehicle.enterParkedState(EXAMPLE_TIME, EXAMPLE_DURATION);
		assertTrue(testVehicle.getDepartureTime() == 30);
	}

	/**
	 * Testing getDepartureTime to make sure the times are matching. This should
	 * return the actually parking time duration if the car has parked and left
	 * the carpark completely.
	 * 
	 * @throws VehicleException
	 * @author Ken Lewis
	 */
	@Test
	public void testingFinalDepartureTime() throws VehicleException {

		testVehicle = new Car("1234Test", 10, false);
		testVehicle.enterParkedState(EXAMPLE_TIME, EXAMPLE_DURATION);
		testVehicle.exitParkedState(50);
		System.out.printf("%s\n", testVehicle.getDepartureTime());
		assertTrue(testVehicle.getDepartureTime() == 50);
	}

	/**
	 * Testing getParking time to ensure the correct variable is being returned
	 * 
	 * @throws VehicleException
	 * @author Ken Lewis
	 */
	@Test
	public void testGetParkingTime() throws VehicleException {

		testVehicle = new Car(EXAMPLE_PLATE, EXAMPLE_TIME, false);
		testVehicle.enterParkedState(EXAMPLE_TIME, EXAMPLE_DURATION);
		assertTrue(testVehicle.getParkingTime() == 10);
	}

	/**
	 * Testing getVehicleID to ensure the correct variable is being returned
	 * 
	 * @throws VehicleException
	 * @author Ken Lewis
	 */
	@Test
	public void testVehicleID() throws VehicleException {

		testVehicle = new Car(EXAMPLE_PLATE, EXAMPLE_TIME, false);
		assertTrue(testVehicle.getVehID().equals("1234Test"));
	}

	/**
	 * Testing isParked method to ensure that if the car is currently parked
	 * that it will return the correct boolean of true
	 * 
	 * @throws VehicleException
	 * @author Ken Lewis
	 */
	@Test
	public void testIsParked() throws VehicleException {

		testVehicle = new Car(EXAMPLE_PLATE, EXAMPLE_TIME, false);
		testVehicle.enterParkedState(EXAMPLE_TIME, EXAMPLE_DURATION);
		assertTrue(testVehicle.isParked() == true);
	}

	/**
	 * Testing isParked method to ensure that if the was parked but is no longer
	 * parked and has exited the carpark that the method will not return true
	 * 
	 * @throws VehicleException
	 * @author Ken Lewis
	 */
	@Test
	public void testIsParkedAfterLeaving() throws VehicleException {

		testVehicle = new Car(EXAMPLE_PLATE, EXAMPLE_TIME, false);
		testVehicle.enterParkedState(EXAMPLE_TIME, EXAMPLE_DURATION);
		testVehicle.exitParkedState(NORM_LEAVE_TIME);
		assertTrue(testVehicle.isParked() == false);
	}

	/**
	 * Testing isParked method to ensure that if the car was in queue that it
	 * will not return that the car is parked.
	 * 
	 * @throws VehicleException
	 * @author Ken Lewis
	 */
	@Test
	public void testIsParkedWhileInQueue() throws VehicleException {

		testVehicle = new Car(EXAMPLE_PLATE, EXAMPLE_TIME, false);
		testVehicle.enterQueuedState();
		assertTrue(testVehicle.isParked() == false);
	}

	/**
	 * Testing isQueued method to make sure that if the car is currently in
	 * queue that it will return the correct state.
	 * 
	 * @throws VehicleException
	 * @author Ken Lewis
	 */
	@Test
	public void testIsQueued() throws VehicleException {

		testVehicle = new Car(EXAMPLE_PLATE, EXAMPLE_TIME, false);
		testVehicle.enterQueuedState();
		assertTrue(testVehicle.isQueued() == true);
	}

	/**
	 * Testing isQueued method to make sure that if the car is currently in
	 * queue that it will return the correct state.
	 * 
	 * @throws VehicleException
	 * @author Ken Lewis
	 */
	@Test
	public void testIsNotQueued() throws VehicleException {

		testVehicle = new Car(EXAMPLE_PLATE, EXAMPLE_TIME, false);
		testVehicle.enterParkedState(EXAMPLE_TIME, EXAMPLE_DURATION);
		assertTrue(testVehicle.isQueued() == false);
	}

	/**
	 * Testing isSatisfied to check that if they car parks that the method will
	 * return the correct variable.
	 * 
	 * @throws VehicleException
	 * @author Ken Lewis
	 */
	@Test
	public void isSatisfied() throws VehicleException {

		testVehicle = new Car(EXAMPLE_PLATE, EXAMPLE_TIME, false);
		testVehicle.enterParkedState(EXAMPLE_TIME, EXAMPLE_DURATION);
		testVehicle.exitParkedState(NORM_LEAVE_TIME);
		assertTrue(testVehicle.isSatisfied() == true);
	}

	/**
	 * Testing was parked method to ensure that if the car was in a parked state
	 * that it would be returned correctly
	 * 
	 * @throws VehicleException
	 * @author Ken Lewis
	 */
	@Test
	public void wasParked() throws VehicleException {

		testVehicle = new Car(EXAMPLE_PLATE, EXAMPLE_TIME, false);
		testVehicle.enterParkedState(EXAMPLE_TIME, EXAMPLE_DURATION);
		assertTrue(testVehicle.wasParked() == true);
	}

	/**
	 * Testing was parked method to ensure that if the car was in a parked state
	 * that it would be returned correctly
	 * 
	 * @throws VehicleException
	 * @author Ken Lewis
	 */
	@Test
	public void inQueueNotParked() throws VehicleException {

		testVehicle = new Car(EXAMPLE_PLATE, EXAMPLE_TIME, false);
		testVehicle.enterQueuedState();
		assertTrue(testVehicle.wasParked() == false);
	}

	/**
	 * Testing was parked method to ensure that if the car was was in a parked
	 * state that it would be returned correctly
	 * 
	 * @throws VehicleException
	 * @author Ken Lewis
	 */
	@Test
	public void hasParkedAndLeft() throws VehicleException {

		testVehicle = new Car(EXAMPLE_PLATE, EXAMPLE_TIME, false);
		testVehicle.enterParkedState(EXAMPLE_TIME, EXAMPLE_DURATION);
		testVehicle.exitParkedState(NORM_LEAVE_TIME);
		assertTrue(testVehicle.wasParked() == true);
	}

	/**
	 * Testing the was queued method to check to see if at any time the car was
	 * queued.
	 * 
	 * @throws VehicleException
	 * @author Ken Lewis
	 */
	@Test
	public void wasQueued() throws VehicleException {

		testVehicle = new Car(EXAMPLE_PLATE, EXAMPLE_TIME, false);
		testVehicle.enterQueuedState();
		testVehicle.exitQueuedState(NORM_LEAVE_TIME);
		assertTrue(testVehicle.wasQueued() == true);
	}

	/**
	 * Testing the was queued method to check to see if the car wasn't queued
	 * that it will return the correct result.
	 * 
	 * @throws VehicleException
	 * @author Ken Lewis
	 */
	@Test
	public void wasNotQueued() throws VehicleException {

		testVehicle = new Car(EXAMPLE_PLATE, EXAMPLE_TIME, false);
		testVehicle.enterParkedState(EXAMPLE_TIME, EXAMPLE_DURATION);
		testVehicle.exitParkedState(NORM_LEAVE_TIME);
		assertTrue(testVehicle.wasQueued() == false);
	}

	/**
	 * Testing the is satisfied method to make sure that it is returning the
	 * correct variable. Should return true because the car was satisfied.
	 * 
	 * @throws VehicleException
	 * @author Ken Lewis
	 */
	@Test
	public void testIsSatisfied() throws VehicleException {

		testVehicle = new Car(EXAMPLE_PLATE, EXAMPLE_TIME, false);
		testVehicle.enterParkedState(EXAMPLE_TIME, EXAMPLE_DURATION);
		testVehicle.exitParkedState(NORM_LEAVE_TIME);
		assertTrue(testVehicle.isSatisfied() == true);
	}

	/**
	 * Testing the is satisfied method to make sure that it is returning the
	 * correct variable. Should return true because the car was satisfied.
	 * 
	 * @throws VehicleException
	 * @author Ken Lewis
	 */
	@Test
	public void testIsNotSatisfied() throws VehicleException {

		testVehicle = new Car(EXAMPLE_PLATE, EXAMPLE_TIME, false);
		testVehicle.enterQueuedState();
		testVehicle.exitQueuedState(Constants.MAXIMUM_QUEUE_TIME + 1);
		assertTrue(testVehicle.isSatisfied() == false);
	}

}
