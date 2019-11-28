package frc.robot.commands.arm;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import frc.robot.Robot;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Constants;
import frc.team6854.OI;

public class OperateArm extends Command implements Constants {
  private Arm arm = null;
  private Scheduler scheduler = null;
  private OI oi = null;

  private static boolean manualControl;

  public OperateArm() {
    arm = Arm.getInstance();
    scheduler = Scheduler.getInstance();
    oi = OI.getInstance();
    
    requires(arm);
  }

  @Override
  protected void initialize() {
    manualControl = true;
  }

  @Override
  protected void execute() {
    double manualOutput = Robot.driver.getDriverRTrigger() - Robot.driver.getDriverLTrigger();

    // If the triggers are pressed (> 0) then set the arm to manual mode
    if (manualOutput > 0 || manualOutput < 0) {
      manualControl = true;
    }

    if(Robot.driver.getDriverBButtonPressed()) {
      manualControl = true;
      scheduler.add(new DropHatch());
    }

    // If in manual mode and the stage switch buttons are pressed switch to stage mode
    if (manualControl == true && (Robot.driver.getDriverRBumperPressed() || Robot.driver.getDriverLBumperPressed())) {
      manualControl = false;
    }

    // Select the stage using the bumpers... + for increase - for decrease
    if (Robot.driver.getDriverRBumperPressed() == true) {
      if (arm.selectedStage < arm.numStages) {
        arm.selectedStage ++;
      }
    }
    
    if (Robot.driver.getDriverLBumperPressed() == true) {
      if (arm.selectedStage > 0) {
        arm.selectedStage --;
      }
    }

    if(manualControl == false) {
      arm.setStage(arm.selectedStage);
    }

    // If in manual mode drive the arm manually
    if (manualControl) {
      arm.driveManual(manualOutput);
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
