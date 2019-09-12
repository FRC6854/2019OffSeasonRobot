package frc.robot.commands.arm;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.Constants;

public class OperateArm extends Command implements Constants {
  public static boolean manualControl = true;

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

    // If the triggers are pressed (> 0) then set the arm to manual mode
    if (manualOutput > 0 || manualOutput < 0) {
      manualControl = true;
    }

    if(Robot.oi.getDriverBButtonPressed()) {
      Robot.scheduler.add(new ReleaseHatch());
    }

    // If in manual mode and the stage switch buttons are pressed switch to stage mode
    if (manualControl == true && (Robot.oi.getDriverRBumperPressed() || Robot.oi.getDriverLBumperPressed())) {
      manualControl = false;
    }

    // Select the stage using the bumpers... + for increase - for decrease
    if (Robot.oi.getDriverRBumperPressed() && manualControl == false) {
      if (Robot.arm.selectedStage < Robot.arm.numStages) {
        Robot.arm.selectedStage ++;
      }
    } else if (Robot.oi.getDriverLBumperPressed() && manualControl == false) {
      if (Robot.arm.selectedStage > 0) {
        Robot.arm.selectedStage --;
      }
    }

    // If in manual mode drive the arm manually
    if (manualControl) {
      Robot.arm.driveManual(manualOutput);
    }

    // If in stage mode send the selected stage to the arm
    if (manualControl == false) {
        Robot.arm.setStage(Robot.arm.selectedStage);
    }

    // LB RB and START to teach
    if (Robot.oi.getDriverLBumper() && Robot.oi.getDriverRBumper() && Robot.oi.getDriverStartButton()) {
      Robot.scheduler.add(new ProgramStage());
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
