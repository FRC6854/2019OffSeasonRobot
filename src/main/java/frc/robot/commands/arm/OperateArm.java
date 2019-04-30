package frc.robot.commands.arm;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.Constants;

public class OperateArm extends Command implements Constants {
  public static boolean manualControl = true;
  public static int stage = 1;

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

    if (manualOutput != 0) {
      manualControl = true;
    } else {
      manualControl = false;
    }

    if (manualControl) {
      Robot.arm.driveManual(manualOutput);
    }

    // if (Robot.oi.getDriverLBumperPressed() || Robot.oi.getDriverRBumperPressed())
    // {
    if (manualControl == false) {
      if (Robot.oi.getDriverLBumperPressed()) {
        if (stage == 1) {
          stage = 1;
        } else {
          stage--;
        }
      } else if (Robot.oi.getDriverRBumperPressed()) {
        if (stage == 3) {
          stage = 3;
        } else {
          stage++;
        }
       
      }
    }

    if (manualControl == false) {
        Robot.arm.setStage(stage);
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
