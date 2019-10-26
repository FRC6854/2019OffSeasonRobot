package frc.robot.commands.arm;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class ReleaseHatch extends Command {
  public ReleaseHatch() {
    super();
    requires(Robot.arm);
  }

  @Override
  protected void initialize() {
    switch(Robot.arm.selectedStage) {
      case 1:
        Robot.arm.driveTicks(500);
        break;
      case 2:
        Robot.arm.driveTicks(3000);
        break;
      case 3:
        Robot.arm.driveTicks(5200);
        break;
    }
  }

  @Override
  protected void execute() {
  }

  @Override
  protected boolean isFinished() {
    switch(Robot.arm.selectedStage) {
      case 1:
        if(Robot.arm.getErrorMargin(500, 50))
          return true;
      case 2:
        if(Robot.arm.getErrorMargin(3000, 50))
          return true;
      case 3:
        if(Robot.arm.getErrorMargin(5200, 50))
          return true;
    }
    return false;
  }

  @Override
  protected void end() {
    Robot.scheduler.add(new OperateArm());
  }

  @Override
  protected void interrupted() {
    end();
  }
}
