package frc.robot.commands.drivetrain;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class DriveDistance extends Command {
  int rotations;
  public DriveDistance(int rotations) {
    super();
    requires(Robot.drivetrain);
    this.rotations = rotations;
  }

  @Override
  protected void initialize() {
    System.out.println("Running Distance");
    Robot.drivetrain.zeroSensor();
    System.out.println(rotations);
    Robot.drivetrain.driveRotations(rotations);
  }

  @Override
  protected boolean isFinished() {
    if(Robot.drivetrain.getLeftTicks() == Robot.drivetrain.rotationsToTicks(rotations)){
      return true;
    }
    return false;
  }

  @Override
  protected void end() {
    Robot.scheduler.add(new ArcadeDrive());
  }

  @Override
  protected void interrupted() {
    end();
  }
}
