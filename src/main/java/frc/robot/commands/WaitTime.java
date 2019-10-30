package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

public class WaitTime extends Command {

  public WaitTime(double time) {
    setTimeout(time);
  }

  @Override
  protected void initialize() {
  }

  @Override
  protected void execute() {
  }

  @Override
  protected boolean isFinished() {
    return isTimedOut();
  }

  @Override
  protected void end() {
  }

  @Override
  protected void interrupted() {
  }
}
