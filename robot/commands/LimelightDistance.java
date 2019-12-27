package org.usfirst.frc.team1555.robot.commands;

import org.usfirst.frc.team1555.robot.Robot;
import org.usfirst.frc.team1555.robot.subsystems.limelight;

import edu.wpi.first.wpilibj.command.Command;

public class LimelightDistance extends Command {
   
	public LimelightDistance() {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.kLimelight);
	}
	double error;
    double drivingAdjust;
    double currentDistance;
    double areaConstant;
    double targetDistance;
    double defaultSpeed;
	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
	areaConstant = 1;
	//currentDistance = Robot.kLimelight.distanceByArea(areaConstant);
	targetDistance = 12;
	error = currentDistance - targetDistance;
	drivingAdjust = (error*0.2);

	}
	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
//		currentDistance = Robot.kLimelight.distanceByArea(areaConstant);
//		targetDistance = 12;
//		error = currentDistance - targetDistance;
//		drivingAdjust = (error*0.01);
//		if (drivingAdjust > 0.4) {
//			drivingAdjust = 0.4;
//		}
//		if ((-0.1 < drivingAdjust) && (drivingAdjust < 0.1)) {
//			if (drivingAdjust<0) {
//				drivingAdjust = -0.1;
//			}
//			else if (drivingAdjust>0) {
//				drivingAdjust = 0.1;
//			}
//		}
//		Robot.Drive.driveTank(drivingAdjust, -drivingAdjust);
		
		if (Robot.kLimelight.getLimeA() < 45) {
			Robot.Drive.driveStraight(0.3);
		}
		else if (Robot.kLimelight.getLimeA() <= 75) {
			Robot.Drive.driveStraight(0.2);
		}
		else if (Robot.kLimelight.getLimeA() >= 90){
			Robot.Drive.driveStraight(-0.2);
		}
		else {
			Robot.Drive.stop();
		}
		
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		if (targetDistance >= currentDistance) {
			Robot.Drive.driveTank(0, 0);
			return true;
		}
		else {
			return false;
		}
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
