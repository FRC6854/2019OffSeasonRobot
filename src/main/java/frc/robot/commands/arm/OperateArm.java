package frc.robot.commands.arm;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.Robot;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Constants;

public class OperateArm extends CommandBase implements Constants {
  private Arm arm = null;
  private CommandScheduler scheduler = null;

  private static boolean manualControl;

  public OperateArm() {
    arm = Arm.getInstance();
    scheduler = CommandScheduler.getInstance();
    
    addRequirements(arm);
  }

  @Override
  public void initialize() {
    manualControl = true;
  }

  @Override
  public void execute() {
    double manualOutput = Robot.driver.getDriverRTrigger() - Robot.driver.getDriverLTrigger();

    // If the triggers are pressed (> 0) then set the arm to manual mode
    if (manualOutput > 0 || manualOutput < 0) {
      manualControl = true;
    }

    if(Robot.driver.getDriverBButtonPressed()) {
      manualControl = true;
      scheduler.schedule(new DropHatch().withTimeout(1.0));
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
}
