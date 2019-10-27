package frc.robot.commands.arm;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import frc.robot.subsystems.Arm;

public class ReleaseHatch extends Command {
  private Arm arm = null;
  private Scheduler scheduler = null;

  public ReleaseHatch() {
    arm = Arm.getInstance();
    scheduler = Scheduler.getInstance();

    requires(arm);
  }

  @Override
  protected void initialize() {
    switch(arm.selectedStage) {
      case 1:
        arm.driveTicks(500);
        break;
      case 2:
        arm.driveTicks(3000);
        break;
      case 3:
        arm.driveTicks(5200);
        break;
    }
  }

  @Override
  protected void execute() {
  }

  @Override
  protected boolean isFinished() {
    switch(arm.selectedStage) {
      case 1:
        if(arm.getErrorMargin(500, 50))
          return true;
      case 2:
        if(arm.getErrorMargin(3000, 50))
          return true;
      case 3:
        if(arm.getErrorMargin(5200, 50))
          return true;
    }
    return false;
  }

  @Override
  protected void end() {
    scheduler.add(new OperateArm());
  }

  @Override
  protected void interrupted() {
    end();
  }
}
