/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team1555.robot.subsystems;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

import org.usfirst.frc.team1555.robot.RobotMap;;
/**
 * An example subsystem.  You can replace me with your own Subsystem.
 */
public class pneumatics extends Subsystem {
	// Put methods for controlling this subsystem
	// here. Call these from Commands.
    
	public RobotMap hardwareMap = new RobotMap();
	public DoubleSolenoid solenoid = new DoubleSolenoid(hardwareMap.solenoidP1, hardwareMap.solenoidP2);
	public DoubleSolenoid climber = new DoubleSolenoid(2, 3);
	public Compressor compressor = new Compressor();
	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}
	
	public void extend() {
		solenoid.set(Value.kReverse);
	}
	
	public void retract() {
		solenoid.set(Value.kForward);
	}

	public void up() {
		climber.set(Value.kReverse);
	}

	public void down() {
		climber.set(Value.kForward);
	}

	public void off() {
		climber.set(Value.kOff);
	}
	
	public void solenoidOff() {
		solenoid.set(Value.kOff);
	}
	
	public void compressorOn() {
		compressor.start();
	}
	
	public void compressorOff() {
		compressor.stop();
	}
	
	public void clearStickyFault() {
		compressor.clearAllPCMStickyFaults();
	}
	
	public void test() {
		
	}
}
