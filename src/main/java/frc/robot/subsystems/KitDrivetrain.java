package frc.robot.subsystems;

import com.ctre.phoenix.motion.BufferedTrajectoryPointStream;
import com.ctre.phoenix.motion.TrajectoryPoint;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.Filesystem;

import frc.robot.Robot;

public class KitDrivetrain extends Subsystem implements Constants {
  TalonSRX leftMaster;
  VictorSPX leftSlave;

  TalonSRX rightMaster;
  VictorSPX rightSlave;

  public static final double kDefaultQuickStopThreshold = 0.2;
  public static final double kDefaultQuickStopAlpha = 0.1;

  BufferedTrajectoryPointStream _bufferedStreamLeft = new BufferedTrajectoryPointStream();
  BufferedTrajectoryPointStream _bufferedStreamRight = new BufferedTrajectoryPointStream();

  public KitDrivetrain(int ID_LEFT_FRONT, int ID_LEFT_BACK, int ID_RIGHT_FRONT, int ID_RIGHT_BACK) {
    leftMaster = new TalonSRX(ID_LEFT_BACK);
    leftSlave = new VictorSPX(ID_LEFT_FRONT);
    rightMaster = new TalonSRX(ID_RIGHT_BACK);
    rightSlave = new VictorSPX(ID_RIGHT_FRONT);

    init();
  }

  private void init() {
    loadMotionProfiles("testing");

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

    leftMaster.configMotionAcceleration(500, dt_kTimeoutMs);
    rightMaster.configMotionAcceleration(500, dt_kTimeoutMs);

    /* Zero the sensor */
    leftMaster.setSelectedSensorPosition(0, dt_kPIDLoopIdx, dt_kTimeoutMs);
    rightMaster.setSelectedSensorPosition(0, dt_kPIDLoopIdx, dt_kTimeoutMs);
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

    leftMaster.set(ControlMode.PercentOutput, limit(leftMotorOutput) * dt_kDefaultMaxOutput);
    rightMaster.set(ControlMode.PercentOutput,
        limit(rightMotorOutput) * dt_kDefaultMaxOutput * dt_rightSideInvertMultiplier);
  }

  public void loadMotionProfiles(String folderName) {
    Double[][] leftPath = Robot.reader.pathLeft(Filesystem.getDeployDirectory().getAbsolutePath() + "/" + folderName);
    Double[][] rightPath = Robot.reader.pathRight(Filesystem.getDeployDirectory().getAbsolutePath() + "/" + folderName);

    initBufferLeft(leftPath, leftPath.length);
    initBufferRight(rightPath, rightPath.length);
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

  public void driveRotations(double rotations) {
    leftMaster.set(ControlMode.MotionMagic, rotationsToTicks(rotations));
    rightMaster.set(ControlMode.MotionMagic, rotationsToTicks(rotations));
  }

  public void driveTicks(int ticks) {
    leftMaster.set(ControlMode.MotionMagic, ticks);
    rightMaster.set(ControlMode.MotionMagic, ticks);
  }

  public void tankDrive(double left, double right) {
    leftMaster.set(ControlMode.PercentOutput, left);
    rightMaster.set(ControlMode.PercentOutput, right);
  }

  public void fullStop() {
    leftMaster.set(ControlMode.Disabled, 0);
    rightMaster.set(ControlMode.Disabled, 0);
  }

  public void zeroSensor() {
    leftMaster.setSelectedSensorPosition(0);
    rightMaster.setSelectedSensorPosition(0);
  }

  public int rotationsToTicks(double rotations) {
    return (int) rotations * 4096;
  }

  public int ticksToRotations(int ticks) {
    return ticks / 4096;
  }

  // We technically shouldn't need this but it was in the differentialDrive class
  // I think this exists only to prevent people from being stupid and
  // Sending more than 100% power to a motor.
  /**
   * Limit motor values to the -1.0 to +1.0 range.
   */
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

  public int getLeftTicks() {
    return leftMaster.getSelectedSensorPosition();
  }

  public int getRightTicks() {
    return rightMaster.getSelectedSensorPosition();
  }

  public void debug() {
    // SmartDashboard.putData(leftMaster.conTrol)
  }

  private void initBufferLeft(Double[][] profile, int totalCnt) {

    boolean forward = true; // set to false to drive in opposite direction of profile (not really needed
                            // since you can use negative numbers in profile).

    TrajectoryPoint point = new TrajectoryPoint(); // temp for for loop, since unused params are initialized
                                                   // automatically, you can alloc just one

    /* clear the buffer, in case it was used elsewhere */
    _bufferedStreamLeft.Clear();

    /* Insert every point into buffer, no limit on size */
    for (int i = 0; i < totalCnt; ++i) {

      double direction = forward ? +1 : -1;
      double positionRot = profile[i][0];
      double velocityRPM = profile[i][1];
      int durationMilliseconds = 10;

      /* for each point, fill our structure and pass it to API */
      point.timeDur = durationMilliseconds;
      point.position = direction * positionRot * 4096; // Convert Revolutions to
                                                       // Units
      point.velocity = direction * velocityRPM * 4096 / 600.0; // Convert RPM to
                                                               // Units/100ms
      point.auxiliaryPos = 0;
      point.auxiliaryVel = 0;
      point.profileSlotSelect0 = 0; /* which set of gains would you like to use [0,3]? */
      point.profileSlotSelect1 = 0; /* auxiliary PID [0,1], leave zero */
      point.zeroPos = (i == 0); /* set this to true on the first point */
      point.isLastPoint = ((i + 1) == totalCnt); /* set this to true on the last point */
      point.arbFeedFwd = 0; /* you can add a constant offset to add to PID[0] output here */

      _bufferedStreamLeft.Write(point);
    }
  }

  private void initBufferRight(Double[][] profile, int totalCnt) {

    boolean forward = true; // set to false to drive in opposite direction of profile (not really needed
                            // since you can use negative numbers in profile).

    TrajectoryPoint point = new TrajectoryPoint(); // temp for for loop, since unused params are initialized
                                                   // automatically, you can alloc just one

    /* clear the buffer, in case it was used elsewhere */
    _bufferedStreamRight.Clear();

    /* Insert every point into buffer, no limit on size */
    for (int i = 0; i < totalCnt; ++i) {

      double direction = forward ? +1 : -1;
      double positionRot = profile[i][0];
      double velocityRPM = profile[i][1];
      int durationMilliseconds = 10;

      /* for each point, fill our structure and pass it to API */
      point.timeDur = durationMilliseconds;
      point.position = direction * positionRot * 4096; // Convert Revolutions to
                                                       // Units
      point.velocity = direction * velocityRPM * 4096 / 600.0; // Convert RPM to
                                                               // Units/100ms
      point.auxiliaryPos = 0;
      point.auxiliaryVel = 0;
      point.profileSlotSelect0 = 0; /* which set of gains would you like to use [0,3]? */
      point.profileSlotSelect1 = 0; /* auxiliary PID [0,1], leave zero */
      point.zeroPos = (i == 0); /* set this to true on the first point */
      point.isLastPoint = ((i + 1) == totalCnt); /* set this to true on the last point */
      point.arbFeedFwd = 0; /* you can add a constant offset to add to PID[0] output here */

      _bufferedStreamRight.Write(point);
    }
  }

  @Override
  public void initDefaultCommand() {
  }
}
