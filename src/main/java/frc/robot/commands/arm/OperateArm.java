package frc.robot.commands.arm;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.Constants;

public class OperateArm extends Command implements Constants {
  public boolean manualControl = true;
  public int selectedStage = 1;
  
  public OperateArm() {
    super();
    requires(Robot.arm);
  }

  @Override
  protected void initialize() {
  }

  @Override
  protected void execute() {
    double manualOutput = Robot.oi.getDriverRTrigger() - Robot.oi.getDriverLTrigger();
    if (Math.abs(manualOutput) > 0.1) {
      manualControl = true; 
    } else {
      manualControl = false;
    }

    if (manualControl) {
      Robot.arm.driveManual(manualOutput);
    }

    if (Robot.oi.getDriverLBumperPressed() || Robot.oi.getDriverRBumperPressed()) {
      manualControl = false;
      if (manualControl == false) {
        if (Robot.oi.getDriverLBumperPressed()) {
          if (selectedStage <= 1) {
            selectedStage = 1;
          } else {
            selectedStage --;
          }
        } else if (Robot.oi.getDriverRBumperPressed()) {
          selectedStage ++;
        }
      }
      if (selectedStage == 1){
        Robot.arm.driveAngle(STAGE_BOTTOM);
      } else if (selectedStage == 2) {
        Robot.arm.driveAngle(STAGE_MIDDLE);
      } else if (selectedStage == 3) {
        Robot.arm.driveAngle(STAGE_TOP);
      }
    }
  }

  @Override
  protected boolean isFinished() {
    return false;
  }

  @Override
  protected void end() {
  }

  @Override
  protected void interrupted() {
  }
}
