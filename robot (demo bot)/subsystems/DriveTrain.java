package org.usfirst.frc.team1555.robot.subsystems;

import org.usfirst.frc.team1555.robot.RobotMap;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class DriveTrain extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

	//Declaring motors
	//Due to a lack of signal wire splitters, we currently have two ports for the right drive motors
	public static Victor L;
    public static Victor R;
    public static Victor R2;
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    //Called during init to create the drive train
    public static void buildDrive() {
    	L = new Victor(RobotMap.Lmotor);
    	R = new Victor(RobotMap.Rmotor);
    	R2 =  new Victor(RobotMap.Rmotor2);
    }
    
    //Stops the drive train
    public static void stop() {
    	L.stopMotor();
        R.stopMotor();
    	R2.stopMotor();
    }
    
    //Drives the robot with two separate powers for the wheels
    public static void driveTank(double Lspeed, double Rspeed) {
    	L.set(-Lspeed);
    	R.set(Rspeed);
    	R2.set(Rspeed);
    }
    
    //Drives straight
    public static void driveStraight(double speed) {
    	L.set(-speed);
    	R.set(speed);
    	R2.set(speed);
    }
    
    //Function pending
    //Will allow you to drive with separate powers for the wheels for a set amount of time
    public static void driveTank(double Lspeed, double Rspeed, long time) {
    	driveTank(Lspeed, Rspeed);
    	//insert wait command here
    	stop();
    }
    
    //Function pending
    //Will allow you to drive straight for a set amount of time
    public static void driveStraight(double speed, double time) {
    	driveStraight(speed);
    	//insert wait command here
    	stop();
    }
}

