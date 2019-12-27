package org.usfirst.frc.team1555.robot.commands;

import org.usfirst.frc.team1555.robot.Robot;
import org.usfirst.frc.team1555.robot.subsystems.limelight;

import edu.wpi.first.wpilibj.command.Command;

public class LimelightTargeting extends Command {
   
	public LimelightTargeting() {
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
		
        double distance = 1;
		
		if (Robot.kLimelight.targetVisible()) {
			
			error = Robot.kLimelight.getLimeX();
			steeringAdjust = error * 0.1;

		   if (error <= 10) {
		 	Robot.Drive.driveTank(0.3*steeringAdjust, 0.3*-steeringAdjust);
		  }
		  else {
			Robot.Drive.driveTank(0.2*steeringAdjust, -0.2*steeringAdjust);

		  }
		}
		else {
			if (error < 0) {
				Robot.Drive.driveTank(-0.25, 0.25);
			}
			else {
				Robot.Drive.driveTank(0.25, -0.25);

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
