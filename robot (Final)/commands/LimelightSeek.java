/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team1555.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team1555.robot.Robot;

/**
 * An example command.  You can replace me with your own command.
 */
public class LimelightSeek extends Command {
	public LimelightSeek() {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.kLimelight);
	}
	double error;
    double steeringAdjust;

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
	error = 0;
	steeringAdjust = 0;
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
        //Checks for a target
		if (Robot.kLimelight.targetVisible()) {
			
			//Checks the error of the angle
			error = Robot.kLimelight.getLimeX();
			//Determines steering adjustment
			if (error <= 10) {
				steeringAdjust = error * 0.1;
			}
			else {
				steeringAdjust = error * 0.15;
			}
			
			//Drives toward the target
			if (Robot.kLimelight.getLimeA() < 10) {
				//Drives forward
			 	Robot.Drive.driveTank(0.3 + (0.1*steeringAdjust), 0.3 - (0.1*steeringAdjust));
			}
			else if (Robot.kLimelight.getLimeA() <= 25) {
				//Drives forward slowly
				Robot.Drive.driveTank(0.2 + (0.1*steeringAdjust), 0.2 - (0.1*steeringAdjust));
			}
			else if (Robot.kLimelight.getLimeA() >= 75) {
				//Drives backwards
				Robot.Drive.driveTank(-0.2 + (0.1*steeringAdjust), -0.2 - (0.1*steeringAdjust));
			}
			else {
				Robot.Drive.stop();
			}
		  
		}
		
		else {
			if (error > 0) {
				Robot.Drive.driveTank(0.25, -0.25);
			}
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
