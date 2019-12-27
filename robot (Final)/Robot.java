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
	public static Victor driveL;
	public static Victor driveR;
	public static Talon intake;
	public static Talon lift;
	public static Talon hatch;

	DigitalInput limit;
	DigitalInput hLimit;
	// public static Counter encLMag;
	// public static Counter encLIndex;
	// public static Counter encHMag;
	// public static Counter encHIndex;

	Encoder encH;
	Encoder encL;


	public static final ExampleSubsystem kExampleSubsystem
    = new ExampleSubsystem();
    public static final DriveTrain Drive
    = new DriveTrain();
    public static final limelight kLimelight
    = new limelight();
    public static final pneumatics kPneumatics
    = new pneumatics();
    public static final Intake kIntake
    = new Intake();
    public static final Lift kLift
    = new Lift();
    public static final HatchSlapper hatchy
	= new HatchSlapper();
	public static final encoder encode 
	= new encoder();
    public static final Timer tim
	= new Timer();
	
	//Doubles for the motor target positions
	public static double liftTargetPosition;
	public static double hatchTargetPosition;
	public static final int liftHomePosition = 0;
	public static final double liftScorePosition = 10000;
	public static final double liftIntakePosition = 46000;
	public static final double hatchScorePosition = 21000;
	public static final double hatchHomePosition = 0;
	public static final double hatchFloorPosition = 70000;

    //Declaring commands
	Command m_autonomousCommand;

	//Doubles to hold the margin of error for distance and angle
	double xError;
	double zError;
		
	//Doubles to hold the power adjustment to give the motors
	double speedAdjust;
	double steeringAdjust;
	    
	//Double to hold the +- value of xError (Holds 1 or -1)
	double pole;
	    
	//Doubles to hold the final amount of power given to the motors
	double lSpeed;
	double rSpeed;
	    
	//Max value of steeringAdjust
    public final double steeringCap = 0.3;
	    
    //If the absolute value of xError is ever lower than this it sets xError to 0
	public final double xToleranceThreshold = 10;
	
	//The higher this value the less the robot will correct for zError
	double distanceConstant;
	
	//The value added to lSpeed and rSpeed by default
	public final double defaultSpeed = 0.2;
		
	//The speed the robot will turn at when searching for the target
	public final double turnSpeed = 0.25;
	
	//Used for cycling through the lead patterns
	boolean cyclingLead;

	//Target distance for the limelight
	public static final double targetArea = 100;
	
	//Used for the camera controls
	public static boolean primaryCamActive;
	
	//Used to keep track of if we have a ball
	boolean haveBall;
	
	//I really don't know what this thing is it was here when I made the program and I haven't bothered to figure out what it does yet
	SendableChooser<Command> m_chooser = new SendableChooser<>();

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */

//Declaring OI
public static OI m_oi;
	public void robotInit() {
		driveL = new Victor(0);
		driveR = new Victor(1);
		intake = new Talon(2);
		lift = new Talon(3);
		hatch = new Talon(4);

		limit = new DigitalInput(0);
		hLimit = new DigitalInput(1);
		// encLMag = new Counter(6);
		// encLIndex = new Counter(5);
		// encHMag = new Counter(8);
		// encHIndex = new Counter(7);
		encL = new Encoder(9, 8);
		encH = new Encoder(7, 6);
		
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
	  		
	  		cyclingLead = false;
			primaryCamActive = true;
			haveBall = false;
			

	}
	
	// TODO: Figure out what this is for
	@Override
	public void robotPeriodic() {
		// TODO Auto-generated method stub
		super.robotPeriodic();
	}
	
	@Override
	public void disabledInit() {
		System.out.println("hi");
		driveL.stopMotor();
		driveR.stopMotor();
    	System.out.println("Drive is stopping");
		hatch.stopMotor();
    	System.out.println("hatch is stopping");
		lift.stopMotor();
    	System.out.println("lift is stopping");
		intake.stopMotor();
    	System.out.println("intake is stopping");
		
		super.disabledInit();
		System.out.println("disabled");
		encH.reset();
		encL.reset();
		kLimelight.setPipe(1);
	}
	
	@Override
	public void disabledPeriodic() {
		// TODO Auto-generated method stub
		super.disabledPeriodic();
		if (m_oi.leftButtons[2].get()) {
			kLimelight.setStreamPrimary();
			primaryCamActive = true;
			kLimelight.setPipe(1);
		}
		if (m_oi.rightButtons[2].get()) {
			kLimelight.setStreamSecondary();
			primaryCamActive = false;
		}

		if (m_oi.GetManipY() == 1) {
			encL.reset();
		}
		if (m_oi.GetManipX() == 1) {
			encL.reset();
		}
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
		lift.set(0);
		hatch.set(0);
		intake.set(0);

		liftTargetPosition = 0;
		hatchTargetPosition = 0;

		

		speedDrop = 1.0;
	}
	Double speedDrop;
	
	@Override
	public void teleopPeriodic() {
		tedFerdenan();
	}

	public void tedFerdenan() {
		System.out.println("Lift: " + encL.getDistance());
		System.out.println("Hatch: " + encH.getDistance());
	

		//Drive controls
		if (primaryCamActive) {
			driveL.set(-m_oi.GetRightY());
			driveR.set(m_oi.GetLeftY());
		}
		else {
			driveL.set(m_oi.GetLeftY());
			driveR.set(-m_oi.GetRightY());
		}
		
		//Hatch controls

		//Sets the target position of the hatch
		if (m_oi.hatchScore.get()) {
			hatchTargetPosition = hatchScorePosition;
		}
		/*else if (m_oi.hatchClimb.get()) {
			hatchTargetPosition = hatchFloorPosition;
		}*/
		else if (m_oi.hatchTravel.get()) {
			hatchTargetPosition = hatchHomePosition;
		}
		//Moves the hatch
		if (m_oi.hatchUp.get() && !hLimit.get()) {
			hatch.set(0.3);
			hatchTargetPosition = encH.get();
		}
		//ManipY -1 = button 16
		else if (m_oi.GetManipY() == -1.0) {
			hatch.set(-0.7);
			hatchTargetPosition = encH.get();
		} 
		else {
			setHatchPosition(hatchTargetPosition);
		}
		//Resets the hatch encoder
		//ManipY +1 = button 15
		if ((m_oi.GetManipY() == 1) || hLimit.get())  {
			encH.reset();
		}
		
		
		//Intake controls
		if (m_oi.intake.get()) {
			intake.set(0.5);
		}
		else if (m_oi.shootBall.get()) {
			intake.set(-0.8);
		}
		else {
			intake.set(0);
		}
		
		
		//Pneumatics controls
		if (m_oi.shootHatch.get()) {
			kPneumatics.extend();
		}
		else {
			kPneumatics.retract();
		}
		if (m_oi.climbPiston.get()) {
		    kPneumatics.up();
	    }
	    else {
		    kPneumatics.down();
	    }
		//Lift controls

		//Sets the target position
		if(m_oi.liftScore.get()) {
			liftTargetPosition = liftScorePosition;
			System.out.println("Setting lift to score position");
		}
		else if (m_oi.liftCollect.get()) {
			liftTargetPosition = liftIntakePosition;
			System.out.println("Setting lift to collect position");

		}
		else if (m_oi.liftTravel.get()) {
			liftTargetPosition = liftHomePosition;
			System.out.println("Setting lift to home position");

		}
		//Moves the lift
		
		if (m_oi.liftUp.get() && !limit.get()) {
			lift.set(-0.6);
			liftTargetPosition = -encL.get();
		}
		//ManipX -1 = button 13
		else if (m_oi.GetManipX() == -1) {
			lift.set(0.2);
			liftTargetPosition = -encL.get();
		}
		else {
			setLiftPosition(liftTargetPosition);
			System.out.println("Setting lift to target position");

		}
		//Resets the encoders
		//ManipX +1 = button 14
		if ((m_oi.GetManipX() == 1) || limit.get()) {
			encL.reset();
		}

        //Seeking controls
		while(m_oi.rightButtons[1].get()) {
			SeekBall();
		}
		while(m_oi.leftButtons[1].get()) {
			SeekTarget();
			//ScoreBall();
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

	public void SeekTarget() {
		//Feedback to ensure the tracking is calling
		System.out.println("Seeking");
		distanceConstant = 100;

        //Checks for a target
		if (Robot.kLimelight.targetVisible()) {
			
			//Checks the error of the angle
			xError = Robot.kLimelight.getLimeX();
			pole = xError/Math.abs(xError);
			if (Math.abs(xError) < xToleranceThreshold) {
				xError = 0;
			}
			//Checks how far away the target is
			zError = Robot.kLimelight.distanceByArea();
			speedAdjust = zError/distanceConstant;
			//Determines steering adjustment based on how far off the angle is
			steeringAdjust = xError/zError;
			if (steeringAdjust > steeringCap) {
				steeringAdjust = steeringCap;
			}
			else if (steeringAdjust < -steeringCap)
			{
				steeringAdjust = -steeringCap;
			}
						
			lSpeed = speedAdjust + steeringAdjust - defaultSpeed;
			rSpeed = speedAdjust - steeringAdjust - defaultSpeed;
			
			//Drives toward the target
			Robot.driveL.set(lSpeed);
			Robot.driveR.set(-rSpeed);
			
		  
		}
		//Runs if the limelight can't see a target
		else {
			//Turns right if the limelight last saw the target to the right of it's field of view
			if (pole > 0) {
				Robot.driveL.set(turnSpeed);
				Robot.driveR.set(turnSpeed);
			}
			//Turns left if the limelight last saw the target to the left of it's field of view			
			else {
				Robot.driveL.set(-turnSpeed);
				Robot.driveR.set(-turnSpeed);

			}

		}
	}

	public void SeekBall() {
		//Checks for a target
		
		System.out.println("Seeking");
		distanceConstant = 80;

		//Checks for a target
		if (Robot.kLimelight.targetVisible()) {
			
			//Checks the error of the angle
			xError = Robot.kLimelight.getLimeX();
			pole = xError/Math.abs(xError);
			if (Math.abs(xError) < xToleranceThreshold) {
				xError = 0;
			}
			//Checks how far away the target is
			zError = Robot.kLimelight.distanceByArea();
			speedAdjust = zError/distanceConstant;
			//Determines steering adjustment based on how far off the angle is
			steeringAdjust = xError/zError;
			if (steeringAdjust > steeringCap) {
				steeringAdjust = steeringCap;
			}
			else if (steeringAdjust < -steeringCap)
			{
				steeringAdjust = -steeringCap;
			}
						
			lSpeed = speedAdjust + steeringAdjust - defaultSpeed;
			rSpeed = speedAdjust - steeringAdjust - defaultSpeed;
			
			//Drives toward the target
			//Robot.Drive.driveTank(lSpeed, rSpeed);
			Robot.driveL.set(-lSpeed);
			Robot.driveR.set(rSpeed);
			
		  
		}
		//Runs if the limelight can't see a target
		else {
			//Turns right if the limelight last saw the target to the right of it's field of view
			if (pole > 0) {
				Robot.driveL.set(-turnSpeed);
				Robot.driveR.set(-turnSpeed);
			}
			//Turns left if the limelight last saw the target to the left of it's field of view			
			else {
				Robot.driveL.set(turnSpeed);
				Robot.driveR.set(turnSpeed);

			}
		}
	}

	public void setLiftPosition(double position) {
		double tolerance = 1000;
		//Checks to see if the ball is too high
		System.out.println("*********************************inside setliftposition");
		if (-encL.get() + tolerance <= position) {
			//Lowers the lift
			lift.set(0.2);
			System.out.println("Lowering lift " + String.valueOf((-encL.get() - tolerance)));
		}
		//Checks to see if the ball is too low
		
		else if (-encL.get() - tolerance >= position && !limit.get()) {
			//Raises the lift
			lift.set(-0.6);
			System.out.println("Raising lift "+  String.valueOf((-encL.get() + tolerance)));
		}
		else {
			//Hovers
			lift.set(0);
			System.out.println("set lift pos else code");
		}
	}

	public void ScoreBall() {
		//Drives towards the limelight target
		SeekTarget();

		//Checks to see if it's close enough to score a ball
		if((targetArea + 10 >= kLimelight.getLimeA()) && (targetArea - 10 <= kLimelight.getLimeA())) {
			//Moves the lift towards the scoring position
			setLiftPosition(liftScorePosition);
			
			//Checks to see if the lift is in the scoring position
			if ((encL.get() > liftScorePosition - 100) && (encL.get() < liftScorePosition + 100)) {
				//Shoots the ball
				intake.set(-0.8);
			}
			else {
				//Holds the ball
				intake.set(0.2);
			}
		}
		else {
			//Moves the lift to its home position
			setLiftPosition(liftHomePosition);
		}
	}
	

	public void setHatchPosition(double position) {
		double tolerance = 1000;
		if (encH.getDistance() + tolerance <= position) {
			//Moves up
			hatch.set(-0.2);

		}
		else if (encH.get() - tolerance >= position && !hLimit.get()) {
			//Moves down
			hatch.set(0.2);
		}
		else {
			hatch.set(0);
		}
	}


}
