package frc.robot.commands.drivetrain;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.team6854.LEDController.LEDMode;

public class DriveDistance extends Command {
  int rotations;
  public DriveDistance(int rotations) {
    super();
    requires(Robot.drivetrain);
    this.rotations = rotations;
  }

  @Override
  protected void initialize() {
    Robot.leds.currentMode = LEDMode.AUTO;
    Robot.drivetrain.zeroSensor();
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
