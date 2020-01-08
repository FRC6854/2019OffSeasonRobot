package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.Faults;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;
import frc.robot.commands.arm.OperateArm;
import viking.controllers.VikingSRX;

public class Arm extends SubsystemBase implements Constants, RobotMap {
  private static Arm instance = null;

  private VikingSRX arm;
  private Faults faults;

  public int selectedStage = 1; 
  public int numStages = 3;

  public static int STAGE_BOTTOM_DROP = 500;
  public static int STAGE_MIDDLE_DROP = 3000;
  public static int STAGE_TOP_DROP = 5200;

  public static int STAGE_BOTTOM = 1100;
	public static int STAGE_MIDDLE = 3500;
	public static int STAGE_TOP = 5800;

  public Arm() {
    arm = new VikingSRX(CAN_ARM, false, true, arm_kFeedbackDevice, arm_kF, arm_kP, arm_kI, arm_kD, 2000, 1000, 0);
    faults = new Faults();
  }

  public void driveManual(double output) {
    arm.percentOutput(output);
  }

  public void driveTicks(int ticks) {
    arm.motionMagic(ticks);
  }

  public void driveAngle(int angle) {
    arm.motionMagic(angleToTicks(angle));
  }

  public void dropStage() {
    switch (selectedStage) {
      case 1:
        driveTicks(STAGE_BOTTOM_DROP);
        break;
      case 2:
        driveTicks(STAGE_MIDDLE_DROP);
        break;
      case 3:
        driveTicks(STAGE_TOP_DROP);
        break;
    }
    
  }

  public void setStage(int stage) {
    selectedStage = stage;
    switch (stage) {
      case 1:
        driveTicks(STAGE_BOTTOM);
        break;
      case 2:
        driveTicks(STAGE_MIDDLE);
        break;
      case 3:
        driveTicks(STAGE_TOP);
        break;
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
    arm.zeroSensor();
  }

  public void updateFaults() {
    arm.getTalonSRX().getFaults(faults);
  }

  public int getAngle() {
    return ticksToAngle(arm.getTicks());
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
    return arm.getVelocity();
  }

  public boolean getErrorMargin (int goal, int tickError) {
    if(arm.getTicks() > goal - tickError && arm.getTicks() < goal + tickError) {
      return true;
    }

    return false;
  }

  public static Arm getInstance() {
    if (instance == null) {
      instance = new Arm();
      instance.setDefaultCommand(new OperateArm());
    }

    return instance;
  }
}
