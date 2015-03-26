package org.usfirst.frc.team5485.robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Joystick.AxisType;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.Timer;

/**
 * This is a demo program showing the use of the RobotDrive class. The
 * SampleRobot class is the base of a robot application that will automatically
 * call your Autonomous and OperatorControl methods at the right time as
 * controlled by the switches on the driver station or the field controls.
 *
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the SampleRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 *
 * WARNING: While it may look like a good choice to use for your code if you're
 * inexperienced, don't. Unless you know what you are doing, complex code will
 * be much more difficult under this system. Use IterativeRobot or Command-Based
 * instead if you're new.
 */
public class Robot extends SampleRobot {
	RobotDrive myRobot;
	Joystick stick;

	CANTalon driveRight = new CANTalon(1);
	CANTalon driveLeft = new CANTalon(3);

	CANTalon liftCan = new CANTalon(2);

	DigitalInput switchDown = new DigitalInput(0);
	DigitalInput switchUp = new DigitalInput(1);

	public Robot() {
		myRobot = new RobotDrive(driveLeft, driveRight);
		myRobot.setExpiration(0.1);
		stick = new Joystick(0);
	}

	/**
	 * Drive left & right motors for 2 seconds then stop
	 */
	public void autonomous() {
		myRobot.setSafetyEnabled(false);
		myRobot.drive(0.75, 0.0); // drive forwards 3/4 speed
		Timer.delay(5.0); // for 5 seconds
		myRobot.drive(0.0, 0.0); // stop robot
	}

	/**
	 * Runs the motors with arcade steering.
	 */
	public void operatorControl() {
		myRobot.setSafetyEnabled(true);
		boolean up = false;
		boolean down = false;
		while (isOperatorControl() && isEnabled()) {
			// myRobot.tank(stick); // drive with arcade style (use right stick)
			myRobot.tankDrive(-1*stick.getRawAxis(1), -1*stick.getRawAxis(5)); // drive
																			// with
																			// arcade
																			// style
																			// (use
																			// right
																			// stick)
			Timer.delay(0.005); // wait for a motor update time

			// UP
			if (stick.getRawButton(4)) {
//				if (switchUp.get()) {
//					System.err.println("Up done");
//					liftCan.set(0);
//					up = false;
//				} else {
					System.err.println("Up signaled");
					liftCan.set(1.0);
					up = true;
//				}
			} else if (up) {
				liftCan.set(0);
				up = false;
			}

			// DOWN
			if (stick.getRawButton(1)) {
//				if (switchDown.get()) {
//					System.err.println("Down done");
//					liftCan.set(0);
//					down = false;
//				} else {
					System.err.println("Down signaled");
					liftCan.set(-1.0);
					down = true;
//				}
			} else {
				if (down) {
					liftCan.set(0);
					down = false;
				}
			}

		}
	}

	/**
	 * Runs during test mode
	 */
	public void test() {
	}
}
