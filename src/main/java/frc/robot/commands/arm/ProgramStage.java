package frc.robot.commands.arm;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import frc.robot.subsystems.Arm;
import frc.team6854.OI;

public class ProgramStage extends Command {
  private Arm arm = null;
  private Scheduler scheduler = null;
  private OI oi = null;

  public ProgramStage() {
    arm = Arm.getInstance();
    scheduler = Scheduler.getInstance();
    oi = OI.getInstance();

    requires(arm);
  }

  @Override
  protected void initialize() {
  }

  @Override
  protected void execute() {
    double manualOutput = oi.getDriverRTrigger() - oi.getDriverLTrigger();
    arm.driveManual(manualOutput); 
  }

  @Override
  protected boolean isFinished() {
    if (oi.getDriverStartButtonPressed()) {
      arm.teachStage(arm.selectedStage, arm.getAngle());
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
  }
}
