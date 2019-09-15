package frc.robot.commands.drivetrain;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class MotionProfile extends Command {
  final String folder;

  public MotionProfile(String path) {
    super();
    folder = path;
    requires(Robot.drivetrain);
  }

  @Override
  protected void initialize() {
    Robot.drivetrain.loadMotionProfiles(folder);
    Robot.drivetrain.zeroSensor();
    Robot.drivetrain.motionProfile();
    Robot.leds.setDefault();
  }

  @Override
  protected void execute() {
    System.out.println("Waiting for Motion Profile Finished");
  }

  @Override
  protected boolean isFinished() {
    if(Robot.drivetrain.isMotionProfileLeftFinished() == true && Robot.drivetrain.isMotionProfileRightFinished() == true){
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