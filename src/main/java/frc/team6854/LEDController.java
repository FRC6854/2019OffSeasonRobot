package frc.team6854;

import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class LEDController extends Subsystem {
    public enum LEDMode {
        TELEOP,
        AUTO,
        VISION,
        DEFAULT
    };

    public LEDMode currentMode = LEDMode.TELEOP;
    LEDMode lastMode = null;

    public void resetLastMode() {
        lastMode = null;
    }

    public void setMode() {
        if(currentMode != lastMode) {
            switch(currentMode) {
                case TELEOP:
                    setTeleop();
                    lastMode = LEDMode.TELEOP;
                    break;
                case AUTO:
                    setAuto();
                    lastMode = LEDMode.AUTO;
                    break;
                case VISION:
                    setVision();
                    lastMode = LEDMode.VISION;
                    break;
                case DEFAULT:
                    setDefault();
                    lastMode = LEDMode.DEFAULT;
                    break;
                default:
                    setDefault();
                    lastMode = LEDMode.DEFAULT;
                    break;
            }
        }
    }

    public void setTeleop() {
        if(Robot.oi.getAlliance() == Alliance.Blue) {
            Robot.oi.ledDataSerialPort(RobotMap.BLUE_TELEOP);
        }
        else if(Robot.oi.getAlliance() == Alliance.Red) {
            Robot.oi.ledDataSerialPort(RobotMap.RED_TELEOP);
        }
        else {
            Robot.oi.ledDataSerialPort(RobotMap.BLUE_TELEOP);
        }
    }

    public void setAuto() {
        if(Robot.oi.getAlliance() == Alliance.Blue) {
            Robot.oi.ledDataSerialPort(RobotMap.BLUE_AUTO);
        }
        else if(Robot.oi.getAlliance() == Alliance.Red) {
            Robot.oi.ledDataSerialPort(RobotMap.RED_AUTO);
        }
        else {
            Robot.oi.ledDataSerialPort(RobotMap.BLUE_AUTO);
        }
    }

    public void setVision() {
        if(Robot.oi.getAlliance() == Alliance.Blue) {
            Robot.oi.ledDataSerialPort(RobotMap.BLUE_VISION);
        }
        else if(Robot.oi.getAlliance() == Alliance.Red) {
            Robot.oi.ledDataSerialPort(RobotMap.RED_VISION);
        }
        else {
            Robot.oi.ledDataSerialPort(RobotMap.BLUE_VISION);
        }
    }

    public void setDefault() {
        if(Robot.oi.getAlliance() == Alliance.Blue) {
            Robot.oi.ledDataSerialPort(RobotMap.BLUE_DEFAULT);
        }
        else if(Robot.oi.getAlliance() == Alliance.Red) {
            Robot.oi.ledDataSerialPort(RobotMap.RED_DEFAULT);
        }
        else {
            Robot.oi.ledDataSerialPort(RobotMap.BLUE_DEFAULT);
        }
    }

    @Override
    protected void initDefaultCommand() {
        
    }
}
