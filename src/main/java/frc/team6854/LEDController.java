package frc.team6854;

import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class LEDController extends Subsystem {
    public static DigitalOutput bit1 = new DigitalOutput(Robot.DIO[0]);
    public static DigitalOutput bit2 = new DigitalOutput(Robot.DIO[1]);
    public static DigitalOutput bit4 = new DigitalOutput(Robot.DIO[2]);
    public static DigitalOutput bit8 = new DigitalOutput(Robot.DIO[3]);
    public static DigitalOutput bit16 = new DigitalOutput(Robot.DIO[4]);
    public static DigitalOutput bit32 = new DigitalOutput(Robot.DIO[5]);
    public static DigitalOutput bit64 = new DigitalOutput(Robot.DIO[6]);
    public static DigitalOutput bit128 = new DigitalOutput(Robot.DIO[7]);
    
    public static DigitalOutput[] binaryOutput = {
      bit1,
      bit2,
      bit4,
      bit8,
      bit16,
      bit32,
      bit64,
      bit128
    };

    public void setTeleop() {
        if(Robot.oi.getAlliance() == Alliance.Blue) {
            Robot.oi.ledDataI2C(RobotMap.BLUE_TELEOP);
        }
        else if(Robot.oi.getAlliance() == Alliance.Red) {
            Robot.oi.ledDataI2C(RobotMap.RED_TELEOP);
        }
        else {
            Robot.oi.ledDataI2C(RobotMap.BLUE_TELEOP);
        }
    }

    public void setAuto() {
        if(Robot.oi.getAlliance() == Alliance.Blue) {
            Robot.oi.ledDataI2C(RobotMap.BLUE_AUTO);
        }
        else if(Robot.oi.getAlliance() == Alliance.Red) {
            Robot.oi.ledDataI2C(RobotMap.RED_AUTO);
        }
        else {
            Robot.oi.ledDataI2C(RobotMap.BLUE_AUTO);
        }
    }

    public void setVision() {
        if(Robot.oi.getAlliance() == Alliance.Blue) {
            Robot.oi.ledDataI2C(RobotMap.BLUE_VISION);
        }
        else if(Robot.oi.getAlliance() == Alliance.Red) {
            Robot.oi.ledDataI2C(RobotMap.RED_VISION);
        }
        else {
            Robot.oi.ledDataI2C(RobotMap.BLUE_VISION);
        }
    }

    public void setDefault() {
        if(Robot.oi.getAlliance() == Alliance.Blue) {
            Robot.oi.ledDataI2C(RobotMap.BLUE_DEFAULT);
        }
        else if(Robot.oi.getAlliance() == Alliance.Red) {
            Robot.oi.ledDataI2C(RobotMap.RED_DEFAULT);
        }
        else {
            Robot.oi.ledDataI2C(RobotMap.BLUE_DEFAULT);
        }
    }

    @Override
    protected void initDefaultCommand() {
        
    }
}
