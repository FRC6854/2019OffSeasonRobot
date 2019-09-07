package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.Faults;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;

public class Arm extends Subsystem implements Constants {
  TalonSRX arm;
  Faults faults;

  public int selectedStage = 0; 
  public int numStages = 3;

  public static int STAGE_BOTTOM = 30;
	public static int STAGE_MIDDLE = 60;
	public static int STAGE_TOP = 90;

  public Arm(int ID_ARM) {
    arm = new TalonSRX(ID_ARM);
    faults = new Faults();
    init();
  }

  private void init() {
    arm.configFactoryDefault();

    arm.setInverted(false);
    arm.setSensorPhase(true);

    /* Configure Sensor Source for Pirmary PID */
    arm.configSelectedFeedbackSensor(arm_kFeedbackDevice, 0, 0);
    arm.setSelectedSensorPosition(0, 0, 0);
    
    /* Set the peak and nominal outputs */
    arm.configNominalOutputForward(0, arm_kTimeoutMs);
		arm.configNominalOutputReverse(0, arm_kTimeoutMs);
		arm.configPeakOutputForward(1, arm_kTimeoutMs);
    arm.configPeakOutputReverse(-1, arm_kTimeoutMs);
    
    /* Set Motion Magic gains in slot0 - see documentation */
    arm.selectProfileSlot(arm_kSlotIdx, arm_kPIDLoopIdx);
		arm.config_kF(arm_kSlotIdx, arm_kF, arm_kTimeoutMs);
		arm.config_kP(arm_kSlotIdx, arm_kP, arm_kTimeoutMs);
		arm.config_kI(arm_kSlotIdx, arm_kI, arm_kTimeoutMs);
    arm.config_kD(arm_kSlotIdx, arm_kD, arm_kTimeoutMs);
    
    /* Set acceleration and vcruise velocity - see documentation */
		arm.configMotionCruiseVelocity(1000, arm_kTimeoutMs);
		arm.configMotionAcceleration(500, arm_kTimeoutMs);

    /* Zero the sensor */
    arm.setSelectedSensorPosition(0, arm_kPIDLoopIdx, arm_kTimeoutMs);
  }

  public void driveManual(double output) {
    arm.set(ControlMode.PercentOutput, output);
  }

  public void driveTicks(int ticks) {
    arm.set(ControlMode.MotionMagic, ticks);
  }

  public void driveAngle(int angle) {
    arm.set(ControlMode.MotionMagic, angleToTicks(angle));
  }

  public void setStage(int stage) {
    selectedStage = stage;
    if (selectedStage == 1) {
      driveAngle(STAGE_BOTTOM);
    } else if (selectedStage == 2) {
      driveAngle(STAGE_MIDDLE);
    } else if (selectedStage == 3) {
      driveAngle(STAGE_TOP);
    }
  }

  public void teachStage(int stage, int newAngle) {
    if (stage == 1) {
      STAGE_BOTTOM = newAngle;
    } else if (stage == 2) {
      STAGE_MIDDLE = newAngle;
    } else if (stage == 3) {
      STAGE_TOP = newAngle;
    }
  }
  
  private int angleToTicks(int angle) {
    return arm_encoderTicksPerRev * angle / 360 ; //Returns the angle in ticks
  }

  private int ticksToAngle(int ticks) {
    return 360 * ticks / arm_encoderTicksPerRev;
  }

  public void zeroSensor() {
    arm.setSelectedSensorPosition(angleToTicks(-5));
  }

  public void updateFaults() {
    arm.getFaults(faults);
  }

  public int getAngle() {
    return ticksToAngle(arm.getSelectedSensorPosition());
  }

  public boolean getReverseLimitSwitch() {
    return faults.ReverseLimitSwitch;
  }

  public boolean getForwardLimitSwitch() {
    return faults.ForwardLimitSwitch;
  }

  public ControlMode getControlMode() {
    return arm.getControlMode();
  }

  public double getArmVelocity() {
    return arm.getSelectedSensorVelocity();
  }

  @Override
  public void initDefaultCommand() {
  }
}
