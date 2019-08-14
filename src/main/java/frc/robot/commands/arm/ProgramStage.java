package frc.robot.commands.arm;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class ProgramStage extends Command {
  public ProgramStage() {
    requires(Robot.arm);
  }

  @Override
  protected void initialize() {
  }

  @Override
  protected void execute() {
    double manualOutput = Robot.oi.getDriverRTrigger() - Robot.oi.getDriverLTrigger();
    Robot.arm.driveManual(manualOutput); 
  }

  @Override
  protected boolean isFinished() {
    if (Robot.oi.getDriverStartButtonPressed()) {
      Robot.arm.teachStage(Robot.arm.selectedStage, Robot.arm.getAngle());
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
  }
}
