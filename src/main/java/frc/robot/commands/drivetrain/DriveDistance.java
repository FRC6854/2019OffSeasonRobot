package frc.robot.commands.drivetrain;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.Robot;

public class DriveDistance extends InstantCommand {
  int rotations;
  public DriveDistance(int rotations) {
    super();
    requires(Robot.drivetrain);
    rotations = this.rotations;
  }

  @Override
  protected void initialize() {
    Robot.drivetrain.driveRotations(rotations);
  }

  @Override
  protected void end() {
    Robot.scheduler.add(new ArcadeDrive());
  }

  @Override
  protected void interrupted() {
  }
}
