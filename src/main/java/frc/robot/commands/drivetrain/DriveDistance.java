package frc.robot.commands.drivetrain;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.team6854.LEDController.LEDMode;

public class DriveDistance extends Command {
  double meters;
  public DriveDistance(double meters) {
    super();
    requires(Robot.drivetrain);
    this.meters = meters;
  }

  @Override
  protected void initialize() {
    Robot.leds.currentMode = LEDMode.AUTO;
    Robot.drivetrain.zeroSensor();
    Robot.drivetrain.driveMeters(meters);
  }

  @Override
  protected boolean isFinished() {
    if(Robot.drivetrain.getLeftTicks() == Robot.drivetrain.metersToTicks(meters) && Robot.drivetrain.getRightTicks() == Robot.drivetrain.metersToTicks(meters)){
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
