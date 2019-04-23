package frc.robot.commands.drivetrain;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class DriveDistance extends Command {
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
  protected void execute() {

  }  

  @Override
  protected boolean isFinished() {
    return false;
  }

  @Override
  protected void end() {
    Robot.scheduler.add(new ArcadeDrive());
  }

  @Override
  protected void interrupted() {
  }
}
