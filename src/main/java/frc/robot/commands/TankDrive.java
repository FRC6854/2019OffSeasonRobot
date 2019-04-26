package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class TankDrive extends Command {
  public TankDrive() {
    requires(Robot.drivetrain);
  }

  @Override
  protected void initialize() {
  }

  @Override
  protected void execute() {
    Robot.drivetrain.tankDrive(Robot.oi.getDriverLeftStickY(), Robot.oi.getDriverRightStickY());
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