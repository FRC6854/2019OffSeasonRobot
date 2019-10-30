package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import com.kauailabs.navx.frc.AHRS;

import frc.robot.utils.PIDController;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.commands.drivetrain.ArcadeDrive;
import frc.robot.utils.MotorControllers;

public class KitDrivetrain extends Subsystem implements Constants {
  private static KitDrivetrain instance = null;

  private TalonSRX leftMaster;
  private VictorSPX leftSlave;

  private TalonSRX rightMaster;
  private VictorSPX rightSlave;

  private AHRS gyro;

  private MotorControllers controllers;

  private PIDController drivePID;
  private PIDController gyroPID;
  
  private double leftOutput = 0;
  private double rightOutput = 0;

  public KitDrivetrain() {
    controllers = MotorControllers.getInstance();

    leftMaster = controllers.getLeftMaster();
    leftSlave = controllers.getLeftSlave();
    rightMaster = controllers.getRightMaster();
    rightSlave = controllers.getRightSlave();
    gyro = controllers.getGyro();

    init();
  }

  private void init() {
    leftSlave.follow(leftMaster);
    rightSlave.follow(rightMaster);

    leftMaster.configFactoryDefault();
    rightMaster.configFactoryDefault();

    leftMaster.setInverted(false);
    rightMaster.setInverted(true);

    leftSlave.setInverted(false);
    rightSlave.setInverted(true);

    leftMaster.setSensorPhase(true);
    rightMaster.setSensorPhase(true);

    /* Configure Sensor Source for Pirmary PID */
    leftMaster.configSelectedFeedbackSensor(dt_kFeedbackDevice, 0, 0);
    rightMaster.configSelectedFeedbackSensor(dt_kFeedbackDevice, 0, 0);

    leftMaster.setSelectedSensorPosition(0, 0, 0);
    rightMaster.setSelectedSensorPosition(0, 0, 0);

    /* Set the peak and nominal outputs */
    leftMaster.configNominalOutputForward(0, dt_kTimeoutMs);
    rightMaster.configNominalOutputForward(0, dt_kTimeoutMs);

    leftMaster.configNominalOutputReverse(0, dt_kTimeoutMs);
    rightMaster.configNominalOutputReverse(0, dt_kTimeoutMs);

    leftMaster.configPeakOutputForward(1, dt_kTimeoutMs);
    rightMaster.configPeakOutputForward(1, dt_kTimeoutMs);

    leftMaster.configPeakOutputReverse(-1, dt_kTimeoutMs);
    rightMaster.configPeakOutputReverse(-1, dt_kTimeoutMs);

    /* Set Motion Magic gains in slot0 - see documentation */
    leftMaster.selectProfileSlot(dt_kSlotIdx, dt_kPIDLoopIdx);
    rightMaster.selectProfileSlot(dt_kSlotIdx, dt_kPIDLoopIdx);

    leftMaster.config_kF(dt_kSlotIdx, dt_kF, dt_kTimeoutMs);
    rightMaster.config_kF(dt_kSlotIdx, dt_kF, dt_kTimeoutMs);

    leftMaster.config_kP(dt_kSlotIdx, dt_kP, dt_kTimeoutMs);
    rightMaster.config_kP(dt_kSlotIdx, dt_kP, dt_kTimeoutMs);

    leftMaster.config_kI(dt_kSlotIdx, dt_kI, dt_kTimeoutMs);
    rightMaster.config_kI(dt_kSlotIdx, dt_kI, dt_kTimeoutMs);

    leftMaster.config_kD(dt_kSlotIdx, dt_kD, dt_kTimeoutMs);
    rightMaster.config_kD(dt_kSlotIdx, dt_kD, dt_kTimeoutMs);

    /* Set acceleration and vcruise velocity - see documentation */
    leftMaster.configMotionCruiseVelocity(1000, dt_kTimeoutMs);
    rightMaster.configMotionCruiseVelocity(1000, dt_kTimeoutMs);

    leftMaster.configMotionAcceleration(1000, dt_kTimeoutMs);
    rightMaster.configMotionAcceleration(1000, dt_kTimeoutMs);

    /* Zero the sensor */
    leftMaster.setSelectedSensorPosition(0, dt_kPIDLoopIdx, dt_kTimeoutMs);
    rightMaster.setSelectedSensorPosition(0, dt_kPIDLoopIdx, dt_kTimeoutMs);

    drivePID = new PIDController(pDrive, iDrive, dDrive);
    gyroPID = new PIDController(pGyro, iGyro, dGyro);
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

  public void driveMeters(double meters) {
    leftMaster.set(ControlMode.MotionMagic, metersToTicks(meters));
    rightMaster.set(ControlMode.MotionMagic, metersToTicks(meters));
  }

  public void driveRotations(double rotations) {
    leftMaster.set(ControlMode.MotionMagic, rotationsToTicks(rotations));
    rightMaster.set(ControlMode.MotionMagic, rotationsToTicks(rotations));
  }

  public void driveLeft(double value) {
    leftOutput = value;
    leftMaster.set(ControlMode.PercentOutput, value);
  }

  public void driveRight(double value) {
    rightOutput = value;
    rightMaster.set(ControlMode.PercentOutput, value);
  }

  public void driveTicks(int ticks) {
    leftMaster.set(ControlMode.MotionMagic, ticks);
    rightMaster.set(ControlMode.MotionMagic, ticks);
  }

  public void tankDrive(double left, double right) {
    driveLeft(left);
    driveRight(right);
  }

  public void fullStop() {
    driveLeft(0);
    driveRight(0);
    leftMaster.setNeutralMode(NeutralMode.Brake);
    rightMaster.setNeutralMode(NeutralMode.Brake);
  }

  public void coast() {
    leftMaster.setNeutralMode(NeutralMode.Coast);
    rightMaster.setNeutralMode(NeutralMode.Coast);
  }

  public void zeroSensors() {
    leftMaster.setSelectedSensorPosition(0);
    rightMaster.setSelectedSensorPosition(0);
  }

  public int rotationsToTicks(double rotations) {
    return (int) rotations * 4096;
  }

  public int metersToTicks(double meters) {
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

  public void driveSetpoint(double setPoint, double speed, double setAngle) {
		driveSetpoint(setPoint, speed, setAngle, 1);
	}

	public void driveSetpoint(double setPoint, double speed, double setAngle, double tolerance) {
		double output = drivePID.calcPID(setPoint, getAverageDistance(), tolerance);
    double angle = gyroPID.calcPID(setAngle, getGyroAngle(), tolerance);
    
		driveLeft((output + angle) * speed);
		driveRight((-output + angle) * speed);
  }

  public void turnDrive(double setAngle, double speed) {
		turnDrive(setAngle, speed, 1);
	}

	public void turnDrive(double setAngle, double speed, double tolerance) {
    double angle = gyroPID.calcPID(setAngle, getGyroAngle(), 1);
    double min = 0.05;
		if(Math.abs(setAngle-getGyroAngle()) < tolerance){ 
			driveLeft(0); 
			driveRight(0);
		}
		else if(angle > -min && angle < 0){
			driveLeft(min);
			driveRight(-min);
		} 
		else if(angle < min && angle > 0){ 
			driveLeft(min);
			driveRight(-min); 
		} else{ 
			driveLeft(angle * speed);
			driveRight(-angle * speed); 
		}
	}
  
  public boolean drivePIDDone() {
		return drivePID.isDone() && (getAverageVelocity() == 0);
  }
  
  public boolean gyroPIDDone() {
    return gyroPID.isDone() && (getAverageVelocity() == 0);
  }

  public int getLeftVelocity() {
    return leftMaster.getSelectedSensorVelocity();
  }

  public int getRightVelocity() {
    return rightMaster.getSelectedSensorVelocity();
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

  public int getAverageVelocity() {
    return (getRightVelocity() + getLeftVelocity()) / 2;
  }

  public int getLeftTicks() {
    return leftMaster.getSelectedSensorPosition();
  }

  public int getRightTicks() {
    return rightMaster.getSelectedSensorPosition();
  }

  public double getLeftTicksDistance() {
    return getLeftTicks() * (1/350);
  }

  public double getRightTicksDistance() {
    return getRightTicks() * (1/350);
  }

  public double getAverageDistance() {
    return (getLeftTicksDistance() + getRightTicksDistance()) / 2;
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

  public void changeDriveGains(double pDrive, double iDrive, double dDrive) {
		drivePID.changePIDGains(pDrive, iDrive, dDrive);
	}

	public void changeGyroGains(double pGyro, double iGyro, double dGyro) {
		gyroPID.changePIDGains(pGyro, iGyro, dGyro);
	}

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new ArcadeDrive());
  }
}
