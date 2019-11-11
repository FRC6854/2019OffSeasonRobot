package frc.robot.commands.drivetrain;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.KitDrivetrain;

public class ProfileFollower extends Command {

  private KitDrivetrain drivetrain = null;
  String path = null;

  public ProfileFollower(String path) {
    drivetrain = KitDrivetrain.getInstance();
    requires(drivetrain);

    this.path = path;

    setTimeout(15.0);
  }

  @Override
  protected void initialize() {
    drivetrain.zeroSensors();
    drivetrain.resetMotionProfile();
    System.out.println("Filling Talons...");
    drivetrain.loadMotionProfiles(path);

    System.out.println("Talons filled!");
    System.out.println("Executing the Profile");

    drivetrain.motionProfile();
  }

  @Override
  protected void execute() {
    drivetrain.driveMotionProfile();
  }

  @Override
  protected boolean isFinished() {
    return (drivetrain.isMotionProfileLeftFinished() || drivetrain.isMotionProfileRightFinished()) || isTimedOut();
  }

  @Override
  protected void end() {
    System.out.println("Done MP driving!");
    drivetrain.resetMotionProfile();
    drivetrain.arcadeDrive(0, 0);
  }

  @Override
  protected void interrupted() {
    end();
  }
}
