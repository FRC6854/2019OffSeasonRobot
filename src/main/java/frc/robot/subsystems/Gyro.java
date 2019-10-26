package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SPI.Port;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Gyro extends Subsystem {

  public static AHRS gyro = new AHRS(Port.kMXP);

  public double getAngle() {
    return gyro.getAngle();
  }

  public void reset() {
    gyro.reset();
  }

  @Override
  public void initDefaultCommand() {
    
  }
}
