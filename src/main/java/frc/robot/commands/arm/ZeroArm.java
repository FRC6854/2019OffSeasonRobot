package frc.robot.commands.arm;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import frc.robot.subsystems.Arm;

public class ZeroArm extends Command {

  private Arm arm = null;
  private Scheduler scheduler = null;

  public ZeroArm() {
    arm = Arm.getInstance();
    scheduler = Scheduler.getInstance();

    setTimeout(6.0);

    requires(arm);
  }

  @Override
  protected void initialize() {
  }

  @Override
  protected void execute() {
    arm.updateFaults();
    arm.driveManual(-0.1);
  }

  @Override
  protected boolean isFinished() {
    return arm.getReverseLimitSwitch() || isTimedOut();
  }

  @Override
  protected void end() {
    arm.zeroSensor();
    scheduler.add(new OperateArm());
  }

  @Override
  protected void interrupted() {
  }
}
