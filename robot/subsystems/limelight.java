package org.usfirst.frc.team1555.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class limelight extends Subsystem {

	static NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
	static NetworkTableEntry tx = table.getEntry("tx");
	static NetworkTableEntry ty = table.getEntry("ty");
	static NetworkTableEntry ta = table.getEntry("ta");
	static NetworkTableEntry tv = table.getEntry("tv");
	static NetworkTableEntry led = table.getEntry("ledMode");
	static NetworkTableEntry cam = table.getEntry("camMode");
	static NetworkTableEntry stream = table.getEntry("stream");
	static NetworkTableEntry pipe = table.getEntry("pipeline");

	
	double x = tx.getDouble(0);
	double y = ty.getDouble(0);
	double area = ta.getDouble(0);
	public final static double areaConstant = 60;
	public final static double h1 = 0;
	public final static double h2 = 0;
	public final static double a1 = 0;
	public final static double a2 = 0;
	
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}
	
	public static double getLimeX() {
		return tx.getDouble(0);
	}
	
	public static double getLimeY() {
		return ty.getDouble(0);
	}

	public static double getLimeA() {
		return ta.getDouble(0);
	}
	
	public static boolean targetVisible() {
		if (tv.getDouble(0) == 1) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public static double distanceByTrig(double H1, double H2, double A1, double A2) {
		return 7;
	}
	
	public static double distanceByArea() {
		return areaConstant/Math.sqrt(getLimeA());
	}
	
	public static void ledDefault() {
		led.setNumber(0);
	}
	
	public static void ledOn() {
		led.setNumber(3);
	}
	
	public static void ledOff() {
		led.setNumber(1);
	}
	
	public static void ledBlink() {
		led.setNumber(2);
	}
	
	public static void camTrack() {
		cam.setNumber(0);
	}
	
	public static void camSee() {
		cam.setNumber(1);
	}
	
	public static void setStreamSingle() {
		stream.setNumber(0);
	}
	
	public static void setStreamPrimary() {
		stream.setNumber(1);
	}
	
	public static void setStreamSecondary() {
		stream.setNumber(2);
	}
	
	public static void setPipe(double pipeline) {
		pipe.setNumber(pipeline);
	}
}
