package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.utils.MotorControllers;

public class Gyro extends Subsystem {

  private static Gyro instance = null;

  private MotorControllers controllers;
  private AHRS gyro;

  public Gyro() {
    controllers = MotorControllers.getInstance();
    gyro = controllers.getGyro();
  }

  public double getAngle() {
    return gyro.getAngle();
  }

  public void reset() {
    gyro.reset();
  }

  public static Gyro getInstance() {
    if(instance == null)
			instance = new Gyro();
		
		return instance;
  }

  @Override
  public void initDefaultCommand() {
    
  }
}
