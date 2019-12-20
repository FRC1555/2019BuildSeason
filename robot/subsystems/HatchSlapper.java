/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team1555.robot.subsystems;

import org.usfirst.frc.team1555.robot.RobotMap;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * An example subsystem.  You can replace me with your own Subsystem.
 */
public class HatchSlapper extends Subsystem {
	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}
	
	//This is the one time I decided to have fun naming my variables
	//These Talons control the motors to move the hatch slapper
	//We plan on turning these into one entity on the program once we have more signal wire splitters
	public static Talon HatchSlappy;
	public static Talon SlatchHappy;

	//Called during init to build the hatch slapper
	public static void build() {
		HatchSlappy = new Talon(RobotMap.hatchSlapper1);
		SlatchHappy = new Talon(RobotMap.hatchSlapper2);

	}
	
	//Used to set the power of the hatch slapper
	public static void setPower(double speed) {
		HatchSlappy.set(speed);
		SlatchHappy.set(speed);

	}
	
	//Stops the hatch slapper
	public static void stop() {
		HatchSlappy.stopMotor();
		SlatchHappy.stopMotor();

	}
}
