package frc.robot.commands.arm;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class ZeroArm extends Command {
  public ZeroArm() {
    super();
    requires(Robot.arm);
  }

  @Override
  protected void initialize() {
  }

  @Override
  protected void execute() {
    Robot.arm.driveManual(-0.25);
  }

  @Override
  protected boolean isFinished() {
    if (Robot.arm.getReverseLimitSwitch() == true){
      Robot.arm.zeroSensor();
      return true;
    } else {
      return false;
    }
  }

  @Override
  protected void end() {
  }

  @Override
  protected void interrupted() {
  }
}
