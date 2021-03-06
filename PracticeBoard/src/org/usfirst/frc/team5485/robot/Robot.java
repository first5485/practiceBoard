package org.usfirst.frc.team5485.robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.Talon;
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

	Talon driveRight = new Talon(8);
	Talon driveLeft = new Talon(9);

	Jaguar liftJag = new Jaguar(2);

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
		myRobot.drive(-0.5, 0.0); // drive forwards half speed
		Timer.delay(2.0); // for 2 seconds
		myRobot.drive(0.0, 0.0); // stop robot
	}

	/**
	 * Runs the motors with arcade steering.
	 */
	public void operatorControl() {
		myRobot.setSafetyEnabled(false);
		boolean up = false;
		boolean down = false;
		while (isOperatorControl() && isEnabled()) {
			// myRobot.tank(stick); // drive with arcade style (use right stick)
			myRobot.tankDrive(stick.getRawAxis(1), stick.getRawAxis(5));
			Timer.delay(0.005); // wait for a motor update time

			// UP
			if (stick.getRawButton(4)) {
				if (switchUp.get()) {
					System.err.println("Up done");
					liftJag.stopMotor();
					up = false;
				} else {
					System.err.println("Up signaled");
					liftJag.set(1.0);
					up = true;
				}
			} else if (up) {
				liftJag.stopMotor();
				up = false;
			}

			// DOWN
			if (stick.getRawButton(1)) {
				if (switchDown.get()) {
					System.err.println("Down done");
					liftJag.stopMotor();
					down = false;
				} else {
					System.err.println("Down signaled");
					liftJag.set(-1.0);
					down = true;
				}
			} else {
				if (down) {
					liftJag.stopMotor();
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
