package frc.robot.commands.arm;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import frc.robot.Robot;
import frc.robot.subsystems.Arm;

public class ZeroArm extends Command {
  private Arm arm = null;
  private Scheduler scheduler = null;

  public ZeroArm() {
    arm = Arm.getInstance();
    scheduler = Scheduler.getInstance();
    
    requires(arm);
  }

  @Override
  protected void initialize() {
  }

  @Override
  protected void execute() {
    arm.driveManual(-0.1);
  }

  @Override
  protected boolean isFinished() {
    if (arm.getReverseLimitSwitch() == true){
      arm.zeroSensor();
      return true;
    } else {
      return false;
    }
  }

  @Override
  protected void end() {
    scheduler.add(new OperateArm());
  }

  @Override
  protected void interrupted() {
  }
}
