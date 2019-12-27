/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team1555.robot;

import edu.wpi.first.wpilibj.Talon;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Spark;

import org.usfirst.frc.team1555.robot.subsystems.DriveTrain;
import org.usfirst.frc.team1555.robot.subsystems.ExampleSubsystem;
import org.usfirst.frc.team1555.robot.subsystems.pneumatics;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends TimedRobot {
	public static Victor driveL;
	public static Victor driveR;
	public static Spark led;

	public static final ExampleSubsystem kExampleSubsystem
    = new ExampleSubsystem();
    public static final DriveTrain Drive
    = new DriveTrain();
    public static final pneumatics kPneumatics
    = new pneumatics();
    public static final Timer tim
	= new Timer();

    //Declaring commands
	Command m_autonomousCommand;

	//Used for holding the x and y values of the stick when using arcade drive
	double xValue;
	double yValue;

	double speed = 0.5;

	double ledStyle = 0.01;

	boolean buttonPressed = true;

	//Declaring OI
	public static OI m_oi;

	//I really don't know what this thing is it was here when I made the program and I haven't bothered to figure out what it does yet
	SendableChooser<Command> m_chooser = new SendableChooser<>();

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */

	public void robotInit() {
		driveL = new Victor(0);
		driveR = new Victor(1);
		led = new Spark(2);
		
		//Creating OI
	    m_oi = new OI();
	    
	    //Autonomous stuff I haven't figured out yet
		//m_chooser.addDefault("Default Auto", new ExampleCommand());
		// chooser.addObject("My Auto", new MyAutoCommand());
		SmartDashboard.putData("Auto mode", m_chooser);
		
		//Prepares the pneumatics
		kPneumatics.clearStickyFault();
		kPneumatics.solenoidOff();
		kPneumatics.compressorOn();
			
	}
	
	@Override
	public void robotPeriodic() {
		// TODO Auto-generated method stub
		super.robotPeriodic();
	}
	
	@Override
	public void disabledInit() {	
		//Stops the motors
		driveL.stopMotor();
		driveR.stopMotor();
		
		super.disabledInit();
	}
	
	@Override
	public void disabledPeriodic() {
		// TODO Auto-generated method stub
		super.disabledPeriodic();
		ledControls();
		
	}
	
	@Override
	public void autonomousInit() {
		m_autonomousCommand = m_chooser.getSelected();

		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector",
		 * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
		 * = new MyAutoCommand(); break; case "Default Auto": default:
		 * autonomousCommand = new ExampleCommand(); break; }
		 */

		// schedule the autonomous command (example)
		if (m_autonomousCommand != null) {
			m_autonomousCommand.start();
		}
		
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
		//Runs the teleop controls
		//We run these because there is no autonomous program to run
		teleopControl();
	}
	
	@Override
	public void teleopInit() {
		//Sets the motors to 0
		driveL.set(0);
		driveR.set(0);
	}
	
	@Override
	public void teleopPeriodic() {
		//Runs the teleop controls
		teleopControl();
	}

	//A method that runs the teleop controls
	public void teleopControl() {
		//xValue = m_oi.GetRightX();
		//yValue = m_oi.GetRightY();

		//Drives the robot
		//arcadeDrive(xValue, yValue);
		driveR.set(-m_oi.GetLeftY() * speed);
		driveL.set(m_oi.GetRightY() * speed);

		//Fires the piston when one of the triggers is pressed
		if (m_oi.leftButtons[1].get() || m_oi.rightButtons[1].get()) {
			kPneumatics.extend();
		}
		//Retracts the piston if no triggers are pressed
		else {
			kPneumatics.retract();
		}
		
		ledControls();

		//Press buttons 6 and 7 on the left stick while the Z axis is turned all the way up to enable full speed
		//Press buttons 11 and 12 on the right stick or turn the Z axis on the left stick down to disable full speed
		if (m_oi.leftButtons[6].get() && m_oi.leftButtons[7].get() && (m_oi.GetLeftZ() == -1)) {
			speed = 1;
		}
		else if ((m_oi.rightButtons[10].get() && m_oi.rightButtons[11].get()) || m_oi.GetLeftZ() != -1) {
			speed = 0.5;
		}

	}

	public void ledControls() {
		
		if (m_oi.rightButtons[2].get() && !buttonPressed) {
			ledStyle += 0.02;
			buttonPressed = true;
		}
		else if (m_oi.leftButtons[2].get() && !buttonPressed) {
			ledStyle -= 0.02;
			buttonPressed = true;
		}
		else if (!(m_oi.leftButtons[2].get() || m_oi.rightButtons[2].get())) {
			buttonPressed = false;
		}

		led.set(ledStyle);
	}

	//A method for driving arcade style (one stick)
	public void arcadeDrive(double x, double y) {
		driveL.set(x - y);
		driveR.set(x + y);
	}

	//A method that returns true if any button on the right stick is pressed
	public boolean anyButtonPressed() {
		boolean buttonPressed = false;
		for (int i = 1; i <= 11; i++) {
			if (m_oi.rightButtons[i].get()) {
				buttonPressed = true;
				i = 11;
			}
		}
		return buttonPressed;
	}

}
