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
public class LimeDistance2 extends Command {
	public LimeDistance2() {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.kLimelight);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		
		if (Robot.kLimelight.getLimeA() < 10 && Robot.kLimelight.targetVisible()) {
			Robot.Drive.driveStraight(0.3);
		}
		else if (Robot.kLimelight.getLimeA() <= 25) {
			Robot.Drive.driveStraight(0.2);
		}
		else if (Robot.kLimelight.getLimeA() >= 75){
			Robot.Drive.driveStraight(-0.2);
		}
		else {
			Robot.Drive.stop();
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
