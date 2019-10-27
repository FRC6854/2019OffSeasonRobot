package frc.team6854;

import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class LEDController extends Subsystem implements RobotMap {
    private static LEDController instance;
    private static OI oi;

    public static enum LEDMode {
        TELEOP,
        AUTO,
        VISION,
        DEFAULT,
        ERROR
    };

    public LEDController () {
        oi = OI.getInstance();
    }

    public void setMode(LEDMode mode) {
        switch (mode) {
            case TELEOP:
                setTeleop();
                break;
            case AUTO:
                setAuto();
                break;
            case VISION:
                setVision();
                break;
            case DEFAULT:
                setDefault();
                break;
            case ERROR:
            default:
                setDefault();
                break;
        }
    }

    public void setTeleop() {
        if(oi.getAlliance() == Alliance.Blue) {
            oi.ledDataSerialPort(BLUE_TELEOP);
        }
        else if(oi.getAlliance() == Alliance.Red) {
            oi.ledDataSerialPort(RED_TELEOP);
        }
        else {
            oi.ledDataSerialPort(BLUE_TELEOP);
        }
    }

    public void setAuto() {
        if(oi.getAlliance() == Alliance.Blue) {
            oi.ledDataSerialPort(BLUE_AUTO);
        }
        else if(oi.getAlliance() == Alliance.Red) {
            oi.ledDataSerialPort(RED_AUTO);
        }
        else {
            oi.ledDataSerialPort(BLUE_AUTO);
        }
    }

    public void setVision() {
        if(oi.getAlliance() == Alliance.Blue) {
            oi.ledDataSerialPort(BLUE_VISION);
        }
        else if(oi.getAlliance() == Alliance.Red) {
            oi.ledDataSerialPort(RED_VISION);
        }
        else {
            oi.ledDataSerialPort(BLUE_VISION);
        }
    }

    public void setDefault() {
        if(oi.getAlliance() == Alliance.Blue) {
            oi.ledDataSerialPort(BLUE_DEFAULT);
        }
        else if(oi.getAlliance() == Alliance.Red) {
            oi.ledDataSerialPort(RED_DEFAULT);
        }
        else {
            oi.ledDataSerialPort(BLUE_DEFAULT);
        }
    }

    public void setError() {
        oi.ledDataSerialPort(ERROR);
    }

    public static LEDController getInstance () {
        if (instance == null)
          instance = new LEDController();
        return instance;
    }

    @Override
    protected void initDefaultCommand() {
        
    }
}
