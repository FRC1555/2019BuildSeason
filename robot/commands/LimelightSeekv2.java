/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team1555.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import java.beans.PropertyChangeListener;

import org.usfirst.frc.team1555.robot.Robot;

/**
 * An example command.  You can replace me with your own command.
 */
public class LimelightSeekv2 extends Command {
	public LimelightSeekv2() {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.kLimelight);
	}
	double xError;
	double zError;
	double speedAdjust;
	public final double steeringCap = 0.3;
	public final double distanceConstant = 100;
    double steeringAdjust;
    double pole;
    double lSpeed;
    double rSpeed;

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
	xError = 0;
	zError = 0;
	steeringAdjust = 0;
	pole = 1;
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
        //Checks for a target
		if (Robot.kLimelight.targetVisible()) {
			
			//Checks the error of the angle
			xError = Robot.kLimelight.getLimeX();
			pole = xError/Math.abs(xError);
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
						
			lSpeed = speedAdjust + steeringAdjust - 0.2;
			rSpeed = speedAdjust - steeringAdjust - 0.2;
			
			Robot.Drive.driveTank(lSpeed, rSpeed);
			
			//Drives toward the target
			/*if (Robot.kLimelight.getLimeA() < 10) {
				//Drives forward
			 	Robot.Drive.driveTank(0.3 + steeringAdjust, 0.3 - steeringAdjust);
			}
			else if (Robot.kLimelight.getLimeA() <= 25) {
				//Drives forward slowly
				Robot.Drive.driveTank(0.2 + steeringAdjust, 0.2 - steeringAdjust);
			}
			else if (Robot.kLimelight.getLimeA() >= 75) {
				//Drives backwards
				Robot.Drive.driveTank(-0.2 + steeringAdjust, -0.2 - steeringAdjust);
			}
			else {
				//Stops the robot if the target is in range
				Robot.Drive.stop();
			}*/
		  
		}
		//Runs if the limelight can't see a target
		else {
			//Turns right if the limelight last saw the target to the right of it's field of view
			if (pole > 0) {
				Robot.Drive.driveTank(0.25, -0.25);
			}
			//Turns left if the limelight last saw the target to the left of it's field of view			
			else {
				Robot.Drive.driveTank(-0.25, 0.25);

			}
			
		}
		
		
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
	}
}
