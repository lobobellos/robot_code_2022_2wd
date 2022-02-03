/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.SpeedControllerGroup;


public class Robot extends TimedRobot {

  // DRIVE MOTORS AND CONTROLS
  private final Spark leftMotorRear = new Spark(2);
  private final Spark leftMotorFront = new Spark(3);
  private final SpeedControllerGroup leftMotors = new SpeedControllerGroup(leftMotorRear, leftMotorFront);

  private final Spark rightMotorRear = new Spark(1);
  private final Spark rightMotorFront = new Spark(0);
  private final SpeedControllerGroup rightMotors = new SpeedControllerGroup(rightMotorRear, rightMotorFront);

  private final DifferentialDrive robotDrive = new DifferentialDrive(leftMotors, rightMotors);
  private final Joystick stick = new Joystick(0);

  private final double ROTATION_SCALE = 1.0; // scale factor 0 to 1 to make turns easier to control, but not too slow

  private int direction = 1;

  @Override
  // when the robot boots up, configure the cameras and create the switches
  // TODO: can we just do all this in the class variable declarations? What's the tradeoff?
  public void robotInit() {
  }

  @Override
  // when entering teleop mode, we need to set the intake motor as running (or not)
  // leaving this false is probably the safer bet, though in competition we may want it to be true so that the
  // driver need not remember to activate the intake motor
  public void teleopInit() {
  }

  @Override
  public void teleopPeriodic() {

    drivecontrol();
  }

  private void drivecontrol() {

    // Drive with arcade drive.
    // That means that the Y axis drives forward
    // and backward, and the X turns left and right.

    // read throttle to compute speed modification
    // linearly maps value from 1 to -1 into a value that is 0.5 to 1
    Double throttle = (-stick.getThrottle() + 1) / 2;
    throttle = throttle * 0.4 + 0.6;
    // System.out.println(speed); // debug

    // apply speed modification based on throttle and direction
    double driveSpeed = stick.getY() * throttle * direction;
    double driveRotation = stick.getX() * throttle * ROTATION_SCALE;

    // instantaneous propulsion is based on the computed speed and rotation
    robotDrive.arcadeDrive(driveSpeed, driveRotation);
    
  }
  
}