package frc.robot.commands.drivetrain;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class MotionProfile extends Command {
  public MotionProfile() {
    super();
    requires(Robot.drivetrain);
  }

  @Override
  protected void initialize() {
    Robot.drivetrain.motionProfile();
  }

  @Override
  protected void execute() {
    
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
