package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motion.BufferedTrajectoryPointStream;
import com.ctre.phoenix.motion.MotionProfileStatus;
import com.ctre.phoenix.motion.SetValueMotionProfile;
import com.ctre.phoenix.motion.TrajectoryPoint;

import com.kauailabs.navx.frc.AHRS;

import frc.team6854.PIDController;
import frc.team6854.CSVFileManager;
import frc.team6854.VikingSPXSlave;
import frc.team6854.VikingSRX;
import jaci.pathfinder.Trajectory;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.SPI.Port;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.commands.drivetrain.ArcadeDrive;
import frc.robot.RobotMap;

import java.util.ArrayList;
import java.util.List;

public class KitDrivetrain extends Subsystem implements Constants, RobotMap {
  private static KitDrivetrain instance = null;

  private VikingSRX leftMaster;
  private VikingSPXSlave leftSlave;

  private VikingSRX rightMaster;
  private VikingSPXSlave rightSlave;

  private AHRS gyro;

  private PIDController gyroPID;
  private PIDController driveTargetPID;

  private DigitalInput frontSensor;

  private AnalogInput distanceSensor;
  
  private double leftOutput = 0;
  private double rightOutput = 0;

  private List<List<String>> table = new ArrayList<List<String>>();

  public KitDrivetrain() {
    leftMaster = new VikingSRX(CAN_LEFT_FRONT, false, true, dt_kFeedbackDevice, dt_kF, dt_kP, dt_kI, dt_kD, 1250, 1250);
    leftSlave = new VikingSPXSlave(CAN_LEFT_BACK, leftMaster, false);
    rightMaster = new VikingSRX(CAN_RIGHT_FRONT, true, true, dt_kFeedbackDevice, dt_kF, dt_kP, dt_kI, dt_kD, 1250, 1250);
    rightSlave = new VikingSPXSlave(CAN_RIGHT_BACK, rightMaster, true);

    gyro = new AHRS(Port.kMXP);

    frontSensor = new DigitalInput(DIGITAL_DISTANCE);

    distanceSensor = new AnalogInput(ANALOG_ULTRASONIC);

    gyroPID = new PIDController(pGyro, iGyro, dGyro);
    driveTargetPID = new PIDController(pDriveTarget, iDriveTarget, dDriveTarget);
  }

  public void writeTable() {
    CSVFileManager.writeCSVLog(table);
  }

  public void updateTable() {
    List<String> row = new ArrayList<String>();

    row.add("Drivetrain");
    row.add(leftOutput + " " + rightOutput);  

    table.add(row);
  }

  public void clearTable() {
    table.clear();
  }

  // Copied from the WPILib Differential Drive Class with some minor alterations
  // for compatibility
  public void arcadeDrive(double xSpeed, double zRotation) {
    zRotation = limit(zRotation);
    zRotation = applyDeadband(zRotation, dt_kDefaultDeadband);

    xSpeed = limit(xSpeed);
    xSpeed = applyDeadband(xSpeed, dt_kDefaultDeadband);

    // Square the inputs (while preserving the sign) to increase fine control
    // while permitting full power.
    zRotation = Math.copySign(zRotation * zRotation, zRotation);
    xSpeed = Math.copySign(xSpeed * xSpeed, xSpeed);

    double leftMotorOutput;
    double rightMotorOutput;

    double maxInput = Math.copySign(Math.max(Math.abs(zRotation), Math.abs(xSpeed)), zRotation);

    if (zRotation >= 0.0) {
      // First quadrant, else second quadrant
      if (xSpeed >= 0.0) {
        leftMotorOutput = maxInput;
        rightMotorOutput = zRotation - xSpeed;
      } else {
        leftMotorOutput = zRotation + xSpeed;
        rightMotorOutput = maxInput;
      }
    } else {
      // Third quadrant, else fourth quadrant
      if (xSpeed >= 0.0) {
        leftMotorOutput = zRotation + xSpeed;
        rightMotorOutput = maxInput;
      } else {
        leftMotorOutput = maxInput;
        rightMotorOutput = zRotation - xSpeed;
      }
    }

    driveLeft(limit(leftMotorOutput) * dt_kDefaultMaxOutput);
    driveRight(limit(rightMotorOutput) * dt_kDefaultMaxOutput * dt_rightSideInvertMultiplier);
  }

  public VikingSRX getLeftMaster() {
    return leftMaster;
  }

  public VikingSRX getRightMaster() {
    return rightMaster;
  }

  public void loadMotionProfiles(String folderName) {
    Double[][] leftPath = CSVFileManager.pathLeft("/home/lvuser/paths/" + folderName);
    Double[][] rightPath = CSVFileManager.pathRight("/home/lvuser/paths/" + folderName);

    leftMaster.initMotionBuffer(leftPath, leftPath.length);
    rightMaster.initMotionBuffer(rightPath, rightPath.length);
  }

  public void motionProfile() {
    leftMaster.motionProfileStart();
    rightMaster.motionProfileStart();
  }

  public void resetMotionProfile() {
    leftMaster.resetMotionProfile();
    rightMaster.resetMotionProfile();
  }

  public boolean getFrontSensor() {
    return frontSensor.get();
  }

  public double getDistanceSensor() {
    return distanceSensor.getVoltage() * analogVoltModifier;
  }

  public void driveMeters(double meters) {
    leftMaster.motionMagic(metersToTicks(meters));
    rightMaster.motionMagic(metersToTicks(meters));
  }

  public void driveRotations(double rotations) {
    leftMaster.motionMagic(rotationsToTicks(rotations));
    rightMaster.motionMagic(rotationsToTicks(rotations));
  }

  public void driveLeft(double value) {
    leftOutput = value;
    leftMaster.percentOutput(value);
  }

  public void driveRight(double value) {
    rightOutput = value;
    rightMaster.percentOutput(value);;
  }

  public void tankDrive(double left, double right) {
    driveLeft(left);
    driveRight(right);
  }

  public void zeroSensors() {
    leftMaster.zeroSensor();
    rightMaster.zeroSensor();
  }

  public int rotationsToTicks(double rotations) {
    return (int) rotations * 4096;
  }

  public double metersToTicks(double meters) {
    return rotationsToTicks(meters / (2 * Math.PI * 0.0762));
  }

  public int ticksToRotations(int ticks) {
    return ticks / 4096;
  }

  private double limit(double value) {
    if (value > 1.0) {
      return 1.0;
    }
    if (value < -1.0) {
      return -1.0;
    }
    return value;
  }

  private double applyDeadband(double value, double deadband) {
    if (Math.abs(value) > deadband) {
      if (value > 0.0) {
        return (value - deadband) / (1.0 - deadband);
      } else {
        return (value + deadband) / (1.0 - deadband);
      }
    } else {
      return 0.0;
    }
  }

  public void driveVisionTarget(double distance, double angle, double speed) {
    double forwardOutput = driveTargetPID.calcPID(distance, getDistanceSensor(), 1);

    arcadeDrive(forwardOutput * speed, angle);
  }

  public void turnDrive(double setAngle, double speed) {
		turnDrive(setAngle, speed, 1);
	}

	public void turnDrive(double setAngle, double speed, double tolerance) {
    double angle = gyroPID.calcPID(setAngle, getGyroAngle(), tolerance);

		if(Math.abs(setAngle-getGyroAngle()) < tolerance){ 
			driveLeft(0); 
			driveRight(0);
		}
		else{ 
			driveLeft(angle * speed);
			driveRight(-angle * speed); 
		}
	}
  
  public boolean gyroPIDDone() {
    return gyroPID.isDone();
  }

  public int getLeftVelocity() {
    return leftMaster.getVelocity();
  }

  public int getRightVelocity() {
    return rightMaster.getVelocity();
  }

  public double getLeftOutput() {
    return leftOutput;
  }

  public double getRightOutput() {
    return rightOutput;
  }

  public double getAverageOutput() {
    return (leftOutput + rightOutput) / 2;
  }

  public int getLeftTicks() {
    return leftMaster.getTicks();
  }

  public int getRightTicks() {
    return rightMaster.getTicks();
  }

  public static KitDrivetrain getInstance() {
    if(instance == null)
			instance = new KitDrivetrain();
		
		return instance;
  }

  public double getGyroAngle() {
    return gyro.getAngle();
  }

  public void resetGyro() {
    gyro.reset();
  }

  public void reset() {
    zeroSensors();
    resetGyro();
  }

	public void changeGyroGains(double pGyro, double iGyro, double dGyro) {
		gyroPID.changePIDGains(pGyro, iGyro, dGyro);
	}

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new ArcadeDrive());
  }
}
