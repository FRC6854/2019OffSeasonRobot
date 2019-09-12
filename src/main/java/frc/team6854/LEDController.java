package frc.team6854;

import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Robot;
import frc.team6854.OI;
import frc.robot.RobotMap;

public class LEDController extends Subsystem {
    public void setTeleop() {
        if(Robot.oi.getAlliance() == Alliance.Blue) {
            Robot.oi.ledData(RobotMap.BLUE_TELEOP);
        }
        else if(Robot.oi.getAlliance() == Alliance.Red) {
            Robot.oi.ledData(RobotMap.RED_TELEOP);
        }
        else {
            Robot.oi.ledData(RobotMap.BLUE_TELEOP);
        }
    }

  @Override
  public void initDefaultCommand() {
    
  }
}
