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



import java.awt.Dialog.ModalityType;
import java.lang.annotation.Target;

 
import org.usfirst.frc.team1555.robot.commands.*;
import org.usfirst.frc.team1555.robot.subsystems.DriveTrain;
import org.usfirst.frc.team1555.robot.subsystems.ExampleSubsystem;
import org.usfirst.frc.team1555.robot.subsystems.HatchSlapper;
import org.usfirst.frc.team1555.robot.subsystems.Intake;
import org.usfirst.frc.team1555.robot.subsystems.Lift;
import org.usfirst.frc.team1555.robot.subsystems.encoder;
import org.usfirst.frc.team1555.robot.subsystems.limelight;
import org.usfirst.frc.team1555.robot.subsystems.pneumatics;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends TimedRobot {
	
	//Declaring motor controllers
	//Victors, Talons, and Sparks are the three types of motor controllers
	public static Victor driveL;
	public static Victor driveR;
	public static Talon appendage;

	//Declaring sensors
	DigitalInput limit;
	Encoder encoder;

	//Declaring subsystems
	public static final ExampleSubsystem kExampleSubsystem
	= new ExampleSubsystem();	//The only reason we keep this is because the ExampleSubsytem class will give errors without it
	public static final OI m_oi
	= new OI();		//Object Interface. This creates the controllers
    public static final DriveTrain Drive
    = new DriveTrain();		//The drive train for the robot. Includes all the methods for driving the robot
    public static final limelight kLimelight
    = new limelight();		//The vision tracking classes require this to be used
    public static final pneumatics kPneumatics
    = new pneumatics();		//Pneumatic controls
	public static final encoder encode 
	= new encoder();	//Controls all the encoders
    public static final Timer time
	= new Timer();		//Used for keeping track of time
	public static final RobotMap map
	= new RobotMap();		//Maps the robot

	//Declaring commands
	public static ExampleCommand kExampleCommand
	= new ExampleCommand();
	public static SeekVisionTarget kSeekVisionTarget
	= new SeekVisionTarget();
	
	//Doubles for the motor target positions
	public static double liftTargetPosition;
	public static double hatchTargetPosition;
	public static final int liftHomePosition = 0;
	public static final double liftScorePosition = 10000;
	public static final double liftIntakePosition = 46000;
	public static final double hatchScorePosition = 21000;
	public static final double hatchHomePosition = 0;
	public static final double hatchFloorPosition = 70000;

	double speedDrop;

	boolean cyclingLead;

    //Declaring commands
	Command m_autonomousCommand;

	
	
	//Used for the camera controls
	public static boolean primaryCamActive;
	
	//I really don't know what this thing is it was here when I made the program and I haven't bothered to figure out what it does yet
	SendableChooser<Command> m_chooser = new SendableChooser<>();

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */

//Declaring OI

	public void robotInit() {
		//Mapping all the hardware
		map.mapAll();
		driveL = map.leftMotor;
		driveR = map.rightMotor;
		appendage = map.intakeMotor;

		limit = new DigitalInput(0);
		encoder = new Encoder(9, 8);
	    
	  //Autonomous stuff I haven't figured out yet
	  		//m_chooser.addDefault("Default Auto", new ExampleCommand());
	  		// chooser.addObject("My Auto", new MyAutoCommand());
	  		SmartDashboard.putData("Auto mode", m_chooser);
	  		
	  		//Prepares the pneumatics
	  		kPneumatics.clearStickyFault();
	  		kPneumatics.solenoidOff();
	  		kPneumatics.compressorOn();
	  		
	  		cyclingLead = false;
			primaryCamActive = true;			

	}
	
	// TODO: Figure out what this is for
	@Override
	public void robotPeriodic() {
		// TODO Auto-generated method stub
		super.robotPeriodic();
	}
	
	@Override
	public void disabledInit() {
		driveL.stopMotor();
		driveR.stopMotor();
		appendage.stopMotor();
		
		super.disabledInit();
		System.out.println("disabled");
		encoder.reset();
		kLimelight.setPipe(1);
	}
	
	@Override
	public void disabledPeriodic() {
		// TODO Auto-generated method stub
		super.disabledPeriodic();
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
		kLimelight.setStreamPrimary();
		//kLimelight.setStreamSecondary();
		kLimelight.setPipe(1);
		hatchTargetPosition = 0;
		liftTargetPosition = 0;
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
		tedFerdenan();
	}
	
	@Override
	public void teleopInit() {
		System.out.println("*************************************************************************************");
		System.out.println("*************************************************************************************");
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
//		if (m_autonomousCommand != null) {
//			m_autonomousCommand.cancel();
//		}

		driveL.set(0);
		driveR.set(0);
		appendage.set(0);

		//Used to regulate the speed of the drive train
		speedDrop = 1.0;
	}
	
	@Override
	public void teleopPeriodic() {
		tedFerdenan();
	}

	public void tedFerdenan() {
		//Prints the encoder reading
		//System.out.println("Encoder: " + encoder.getDistance());
		Scheduler.getInstance().run();
		//Drive controls
		//System.out.println(m_oi.leftButtons[1].get());
		
		//Checks to see if left button one is pressed
		if (m_oi.leftButtons[1].get()) {
			//Runs vision seeking controls
			m_oi.leftButtons[1].whileHeld(kSeekVisionTarget);
		}
		else {
			//Runs manual controls
			if (primaryCamActive) {
				//driveL.set(m_oi.GetRightY());
				driveL.set(m_oi.GetLeftY());
				driveR.set(m_oi.GetLeftY());

			}
			else {
				driveL.set(m_oi.GetLeftY());
				//driveR.set(m_oi.GetRightY());
				driveR.set(m_oi.GetLeftY());
			}
		}
		
		
		//Hatch controls

		//Sets the target position of the hatch
		if (m_oi.hatchScore.get()) {
			hatchTargetPosition = hatchScorePosition;
		}
		else if (m_oi.hatchTravel.get()) {
			hatchTargetPosition = hatchHomePosition;
		}
		//Moves the hatch
		if (m_oi.hatchUp.get() && !limit.get()) {
			appendage.set(0.3);
		}
		//ManipY -1 = button 16
		else if (m_oi.GetManipY() == -1.0) {
			appendage.set(-0.7);
		} 

		//Resets the hatch encoder
		//ManipY +1 = button 15
		if ((m_oi.GetManipY() == 1) || limit.get())  {
			encoder.reset();
		}
		
		//Pneumatics controls
		if (m_oi.shootHatch.get()) {
			kPneumatics.extend();
		}
		else {
			kPneumatics.retract();
		}
		
		//Camera controls
		if (m_oi.leftButtons[2].get()) {
			kLimelight.setStreamPrimary();
			primaryCamActive = true;
			kLimelight.setPipe(1);
		}
		if (m_oi.rightButtons[2].get()) {
			kLimelight.setStreamSecondary();
			primaryCamActive = false;
		}

	}

}
