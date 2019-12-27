/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team1555.robot;

import javax.swing.text.StyledEditorKit.ForegroundAction;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	
	//Declaring the joysticks that will be used
	static Joystick leftStick = new Joystick(1);
	static Joystick rightStick = new Joystick(0);
	static Joystick manipulator = new Joystick(2);
	
	//Three Arrays of joystick buttons, one for each of the the sticks. The first element in each array is null so that when mapping the buttons in the code
	//the button number in the code is the same as the one on the physical joystick
	static Button[] leftButtons = {null, new JoystickButton(leftStick, 1), new JoystickButton(leftStick, 2),
			new JoystickButton(leftStick, 3), new JoystickButton(leftStick, 4), new JoystickButton(leftStick, 5), new JoystickButton(leftStick, 6), 
			new JoystickButton(leftStick, 7), new JoystickButton(leftStick, 8), new JoystickButton(leftStick, 9), new JoystickButton(leftStick, 10)};
	
	static Button[] rightButtons = {null, new JoystickButton(rightStick, 1), new JoystickButton(rightStick, 2),
			new JoystickButton(rightStick, 3), new JoystickButton(rightStick, 4), new JoystickButton(rightStick, 5), new JoystickButton(rightStick, 6),
			new JoystickButton(rightStick, 7), new JoystickButton(rightStick, 8), new JoystickButton(rightStick, 9), new JoystickButton(rightStick, 10)};
	
	static Button[] manipButtons = {null, new JoystickButton(manipulator, 1), new JoystickButton(manipulator, 2), new JoystickButton(manipulator, 3), 
			new JoystickButton(manipulator, 4), new JoystickButton(manipulator, 5), new JoystickButton(manipulator, 6), new JoystickButton(manipulator, 7),
			new JoystickButton(manipulator, 8), new JoystickButton(manipulator, 9), new JoystickButton(manipulator, 10), new JoystickButton(manipulator, 11), new JoystickButton(manipulator, 12)};
	
	//Methods for returning values of stick controls
	public static double GetLeftX() {
		return leftStick.getX();
	}
		
	public static double GetLeftY() {
		return leftStick.getY();
	}
		
	public static double GetLeftZ() {
		return leftStick.getZ();
	}
		
	public static double GetRightX() {
		return rightStick.getX();
	}
		
	public static double GetRightY() {
		return rightStick.getY();
	}
		
	public static double GetRightZ() {
		return rightStick.getZ();
	}
	
	public static double GetManipX() {
		return manipulator.getX();
	}
	
	public static double GetManipY() {
		return manipulator.getY();
	}
	
	public static double GetManipZ() {
		return manipulator.getZ();
	}
	//Map for the buttons
	static Button shootBall = manipButtons[1];
	static Button intake = manipButtons[5];

	static Button liftScore = manipButtons[2];
	static Button liftCollect = manipButtons[6];
	static Button liftTravel = manipButtons[10];
	//Use manipX +1 value for lift encoder reset
	static Button liftUp = manipButtons[9];
	//Use manipX -1 value for liftDown

	static Button hatchScore = manipButtons[3];
	static Button hatchClimb = manipButtons[7];
	static Button hatchTravel = manipButtons[11];
	//Use manipY +1 value for hatch encoder reset
	static Button hatchUp = manipButtons[12];
	//Use manipY -1 value for hatchDown

	static Button shootHatch = manipButtons[4];

	static Button climbPiston = manipButtons[8];
	
	
	
	//// CREATING BUTTONS
	// One type of button is a joystick button which is any button on a
	//// joystick.
	// You create one by telling it which joystick it's on and which button
	// number it is.
	// Joystick stick = new Joystick(port);
	// Button button = new JoystickButton(stick, buttonNumber);

	// There are a few additional built in buttons you can use. Additionally,
	// by subclassing Button you can create custom triggers and bind those to
	// commands the same as any other Button.

	//// TRIGGERING COMMANDS WITH BUTTONS
	// Once you have a button, it's trivial to bind it to a button in one of
	// three ways:

	// Start the command when the button is pressed and let it run the command
	// until it is finished as determined by it's isFinished method.
	// button.whenPressed(new ExampleCommand());

	// Run the command while the button is being held down and interrupt it once
	// the button is released.
	// button.whileHeld(new ExampleCommand());

	// Start the command when the button is released and let it run the command
	// until it is finished as determined by it's isFinished method.
	// button.whenReleased(new ExampleCommand());
	
}

  
	
  