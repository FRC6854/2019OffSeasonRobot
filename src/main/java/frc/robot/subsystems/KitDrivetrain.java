package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.command.Subsystem;

public class KitDrivetrain extends Subsystem implements Constants {

  TalonSRX m_leftMaster;
  VictorSPX m_leftSlave;

  TalonSRX m_rightMaster;
  VictorSPX m_rightSlave;

  public static final double kDefaultQuickStopThreshold = 0.2;
  public static final double kDefaultQuickStopAlpha = 0.1;

  public KitDrivetrain(int ID_LEFT_FRONT, int ID_LEFT_BACK, int ID_RIGHT_FRONT, int ID_RIGHT_BACK){
    m_leftMaster = new TalonSRX(ID_LEFT_BACK);
    m_leftSlave = new VictorSPX(ID_LEFT_FRONT);
    m_rightMaster = new TalonSRX(ID_RIGHT_BACK);
    m_rightSlave = new VictorSPX(ID_RIGHT_FRONT);
    
    init();
   
  }

  private void init(){
    m_leftSlave.follow(m_leftMaster);
    m_rightSlave.follow(m_rightMaster);

    m_leftMaster.configFactoryDefault();
    m_rightMaster.configFactoryDefault();

    m_leftMaster.setInverted(false);
    m_rightMaster.setInverted(false);

    m_leftMaster.setSensorPhase(false);
    m_rightMaster.setSensorPhase(false);

    /* Configure Sensor Source for Pirmary PID */
    m_leftMaster.configSelectedFeedbackSensor(dt_kFeedbackDevice, 0, 0);
    m_rightMaster.configSelectedFeedbackSensor(dt_kFeedbackDevice, 0, 0);

    m_leftMaster.setSelectedSensorPosition(0, 0, 0);
    m_rightMaster.setSelectedSensorPosition(0, 0, 0);

    /* Set the peak and nominal outputs */
    m_leftMaster.configNominalOutputForward(0, dt_kTimeoutMs);
    m_rightMaster.configNominalOutputForward(0, dt_kTimeoutMs);

    m_leftMaster.configNominalOutputReverse(0, dt_kTimeoutMs);
    m_rightMaster.configNominalOutputReverse(0, dt_kTimeoutMs);

    m_leftMaster.configPeakOutputForward(1, dt_kTimeoutMs);
    m_rightMaster.configPeakOutputForward(1, dt_kTimeoutMs);

    m_leftMaster.configPeakOutputReverse(-1, dt_kTimeoutMs);
    m_rightMaster.configPeakOutputReverse(-1, dt_kTimeoutMs);   
    
    /* Set Motion Magic gains in slot0 - see documentation */
    m_leftMaster.selectProfileSlot(dt_kSlotIdx, dt_kPIDLoopIdx);
    m_rightMaster.selectProfileSlot(dt_kSlotIdx, dt_kPIDLoopIdx);
    
    m_leftMaster.config_kF(dt_kSlotIdx, dt_kF, dt_kTimeoutMs);
    m_rightMaster.config_kF(dt_kSlotIdx, dt_kF, dt_kTimeoutMs);
    
    m_leftMaster.config_kP(dt_kSlotIdx, dt_kP, dt_kTimeoutMs);
    m_rightMaster.config_kP(dt_kSlotIdx, dt_kP, dt_kTimeoutMs);
    
    m_leftMaster.config_kI(dt_kSlotIdx, dt_kI, dt_kTimeoutMs);
    m_rightMaster.config_kI(dt_kSlotIdx, dt_kI, dt_kTimeoutMs);
    
    m_leftMaster.config_kD(dt_kSlotIdx, dt_kD, dt_kTimeoutMs);
    m_rightMaster.config_kD(dt_kSlotIdx, dt_kD, dt_kTimeoutMs);
    
    /* Set acceleration and vcruise velocity - see documentation */
    m_leftMaster.configMotionCruiseVelocity(1000, dt_kTimeoutMs);
    m_rightMaster.configMotionCruiseVelocity(1000, dt_kTimeoutMs);
    
    m_leftMaster.configMotionAcceleration(500, dt_kTimeoutMs);
    m_rightMaster.configMotionAcceleration(500, dt_kTimeoutMs);

		/* Zero the sensor */
    m_leftMaster.setSelectedSensorPosition(0, dt_kPIDLoopIdx, dt_kTimeoutMs);
    m_rightMaster.setSelectedSensorPosition(0, dt_kPIDLoopIdx, dt_kTimeoutMs);

  }


  // Copied from the WPILib Differential Drive Class with some minor alterations for compatibility
  public void arcadeDrive(double xSpeed, double zRotation ) {
    xSpeed = limit(xSpeed);
    xSpeed = applyDeadband(xSpeed, dt_kDefaultDeadband);

    zRotation = limit(zRotation);
    zRotation = applyDeadband(zRotation, dt_kDefaultDeadband);

    // Square the inputs (while preserving the sign) to increase fine control
    // while permitting full power.
    xSpeed = Math.copySign(xSpeed * xSpeed, xSpeed);
    zRotation = Math.copySign(zRotation * zRotation, zRotation);

    double leftMotorOutput;
    double rightMotorOutput;

    double maxInput = Math.copySign(Math.max(Math.abs(xSpeed), Math.abs(zRotation)), xSpeed);

    if (xSpeed >= 0.0) {
      // First quadrant, else second quadrant
      if (zRotation >= 0.0) {
        leftMotorOutput = maxInput;
        rightMotorOutput = xSpeed - zRotation;
      } else {
        leftMotorOutput = xSpeed + zRotation;
        rightMotorOutput = maxInput;
      }
    } else {
      // Third quadrant, else fourth quadrant
      if (zRotation >= 0.0) {
        leftMotorOutput = xSpeed + zRotation;
        rightMotorOutput = maxInput;
      } else {
        leftMotorOutput = maxInput;
        rightMotorOutput = xSpeed - zRotation;
      }
    }

    m_leftMaster.set(ControlMode.PercentOutput, limit(leftMotorOutput) * dt_kDefaultMaxOutput);
    m_rightMaster.set(ControlMode.PercentOutput, limit(rightMotorOutput) * dt_kDefaultMaxOutput * dt_rightSideInvertMultiplier);
  }

  public void driveRotations(double rotations){
    m_leftMaster.set(ControlMode.MotionMagic, rotationsToTicks(rotations));
    m_rightMaster.set(ControlMode.MotionMagic, rotationsToTicks(rotations));
  }

  public void driveTicks(int ticks){
    m_leftMaster.set(ControlMode.MotionMagic, ticks);
    m_rightMaster.set(ControlMode.MotionMagic, ticks);
  }

  public void tankDrive(double left, double right){
    m_leftMaster.set(ControlMode.PercentOutput, left);
    m_rightMaster.set(ControlMode.PercentOutput, right);
  }

  public void fullStop(){
    m_leftMaster.set(ControlMode.Disabled, 0);
    m_rightMaster.set(ControlMode.Disabled, 0);
  }

  private int rotationsToTicks(double rotations) {
    return (int)rotations * 4096;
  }

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

  public int getLeftTicks(){
    return m_leftMaster.getSelectedSensorPosition();
  }

  public int getRightTicks(){
    return m_rightMaster.getSelectedSensorPosition();
  }



  @Override
  public void initDefaultCommand() {
  }
}
