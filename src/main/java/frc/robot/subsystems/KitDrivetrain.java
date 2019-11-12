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

import frc.robot.utils.PIDController;
import frc.team6854.CSVFileManager;
import jaci.pathfinder.Trajectory;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.commands.drivetrain.ArcadeDrive;
import frc.robot.utils.MotorControllers;
import frc.robot.RobotMap;

import java.util.ArrayList;
import java.util.List;

public class KitDrivetrain extends Subsystem implements Constants, RobotMap {
  private static KitDrivetrain instance = null;

  private TalonSRX leftMaster;
  private VictorSPX leftSlave;

  private TalonSRX rightMaster;
  private VictorSPX rightSlave;

  private BufferedTrajectoryPointStream _bufferedStreamLeft = new BufferedTrajectoryPointStream();
  private BufferedTrajectoryPointStream _bufferedStreamRight = new BufferedTrajectoryPointStream();

  private AHRS gyro;

  private MotorControllers controllers;

  private PIDController gyroPID;
  private PIDController driveTargetPID;

  private DigitalInput frontSensor;

  private AnalogInput distanceSensor;
  
  private double leftOutput = 0;
  private double rightOutput = 0;

  private List<List<String>> table = new ArrayList<List<String>>();

  public KitDrivetrain() {
    controllers = MotorControllers.getInstance();

    leftMaster = controllers.getLeftMaster();
    leftSlave = controllers.getLeftSlave();
    rightMaster = controllers.getRightMaster();
    rightSlave = controllers.getRightSlave();
    gyro = controllers.getGyro();

    frontSensor = new DigitalInput(DIGITAL_DISTANCE);

    distanceSensor = new AnalogInput(ANALOG_ULTRASONIC);

    init();
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
    leftMaster.configMotionCruiseVelocity(1250, dt_kTimeoutMs);
    rightMaster.configMotionCruiseVelocity(1250, dt_kTimeoutMs);

    leftMaster.configMotionAcceleration(1500, dt_kTimeoutMs);
    rightMaster.configMotionAcceleration(1500, dt_kTimeoutMs);

    /* Set timeout for motion profile - see documentation */
    leftMaster.configMotionProfileTrajectoryPeriod(25, dt_kTimeoutMs); 
    rightMaster.configMotionProfileTrajectoryPeriod(25, dt_kTimeoutMs);

    leftMaster.changeMotionControlFramePeriod(25);
    rightMaster.changeMotionControlFramePeriod(25);

    /* Zero the sensor */
    leftMaster.setSelectedSensorPosition(0, dt_kPIDLoopIdx, dt_kTimeoutMs);
    rightMaster.setSelectedSensorPosition(0, dt_kPIDLoopIdx, dt_kTimeoutMs);

    gyroPID = new PIDController(pGyro, iGyro, dGyro);
    driveTargetPID = new PIDController(pDriveTarget, iDriveTarget, dDriveTarget);
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

  public void loadMotionProfiles(String folderName) {
    Double[][] leftPath = CSVFileManager.pathLeft("/home/lvuser/paths/" + folderName);
    Double[][] rightPath = CSVFileManager.pathRight("/home/lvuser/paths/" + folderName);

    initBufferLeft(leftPath, leftPath.length);
    initBufferRight(rightPath, rightPath.length);

    System.out.println("Finished Loading Motion Profiles");
  }

  public void motionProfile() {
    leftMaster.startMotionProfile(_bufferedStreamLeft, 10, ControlMode.MotionProfile);
    rightMaster.startMotionProfile(_bufferedStreamRight, 10, ControlMode.MotionProfile);
  }

  public boolean isMotionProfileLeftFinished() {
    return leftMaster.isMotionProfileFinished();
  }

  public boolean isMotionProfileRightFinished() {
    return rightMaster.isMotionProfileFinished();
  }

  private void initBufferLeft(Double[][] profile, int totalCnt) {
    TrajectoryPoint point = new TrajectoryPoint(); // temp for for loop, since unused params are initialized
                                                   // automatically, you can alloc just one

    /* Insert every point into buffer, no limit on size */
    for (int i = 0; i < totalCnt; ++i) {

      double positionRot = profile[i][0] * (1 / dt_MetersPerRevolution);
      double velocityRPM = profile[i][1] * (1 / dt_MetersPerRevolution);
      int durationMilliseconds = profile[i][2].intValue();

      /* for each point, fill our structure and pass it to API */
      point.timeDur = durationMilliseconds;
      point.position = positionRot * 4096; // Convert Revolutions to
                                                       // Units
      point.velocity = velocityRPM * 4096 / 600.0; // Convert RPM to
                                                               // Units/100ms
      point.profileSlotSelect0 = 0; /* which set of gains would you like to use [0,3]? */
      point.profileSlotSelect1 = 0; /* auxiliary PID [0,1], leave zero */
      point.zeroPos = (i == 0); /* set this to true on the first point */
      point.isLastPoint = ((i + 1) == totalCnt); /* set this to true on the last point */
      point.arbFeedFwd = 0; /* you can add a constant offset to add to PID[0] output here */

      _bufferedStreamLeft.Write(point);
    }

    System.out.println("Done Writing Left");
  }

  private void initBufferRight(Double[][] profile, int totalCnt) {

    TrajectoryPoint point = new TrajectoryPoint(); // temp for for loop, since unused params are initialized
                                                   // automatically, you can alloc just one

    /* Insert every point into buffer, no limit on size */
    for (int i = 0; i < totalCnt; ++i) {

      double positionRot = profile[i][0] * (1 / dt_MetersPerRevolution);
      double velocityRPM = profile[i][1] * (1 / dt_MetersPerRevolution);
      int durationMilliseconds = profile[i][2].intValue();

      /* for each point, fill our structure and pass it to API */
      point.timeDur = durationMilliseconds;
      point.position = positionRot * 4096; // Convert Revolutions to
                                                       // Units
      point.velocity = velocityRPM * 4096 / 600.0; // Convert RPM to
                                                               // Units/100ms
      point.profileSlotSelect0 = 0; /* which set of gains would you like to use [0,3]? */
      point.profileSlotSelect1 = 0; /* auxiliary PID [0,1], leave zero */
      point.zeroPos = (i == 0); /* set this to true on the first point */
      point.isLastPoint = ((i + 1) == totalCnt); /* set this to true on the last point */
      point.arbFeedFwd = 0; /* you can add a constant offset to add to PID[0] output here */

      _bufferedStreamRight.Write(point);
    }

    System.out.println("Done Writing Right");
  }

  public void resetMotionProfile() {
    _bufferedStreamLeft.Clear();
    _bufferedStreamRight.Clear();

    leftMaster.clearMotionProfileTrajectories();
    rightMaster.clearMotionProfileTrajectories();
  }

  public boolean getFrontSensor() {
    return frontSensor.get();
  }

  public double getDistanceSensor() {
    return distanceSensor.getVoltage() * analogVoltModifier;
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

	public void changeGyroGains(double pGyro, double iGyro, double dGyro) {
		gyroPID.changePIDGains(pGyro, iGyro, dGyro);
	}

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new ArcadeDrive());
  }
}
