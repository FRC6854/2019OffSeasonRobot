package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class SwerveDrive extends Command {
  public SwerveDrive() {
    super();
    requires(Robot.drivetrain);
  }

  @Override
  protected void initialize() {
  }

  @Override
  protected void execute() {
    Robot.drivetrain.swerveDrive(Robot.oi.getDriverLeftStickY(), Robot.oi.getDriverLeftStickX(), Robot.oi.getDriverRightStickX(), Robot.oi.getDriverRightStickY());
  }

  @Override
  protected boolean isFinished() {
    return false;
  }

  @Override
  protected void end() {
    Robot.drivetrain.fullStop();
  }

  @Override
  protected void interrupted() {
  }
}