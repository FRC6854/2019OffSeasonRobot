package frc.robot.commands.drivetrain;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.Constants;
import frc.robot.subsystems.KitDrivetrain;
import frc.team6854.LEDController;
import frc.team6854.LEDController.LEDMode;

public class DriveAngle extends Command implements Constants {
  private KitDrivetrain drivetrain = null;
  private LEDController leds = null;

  final double toleranceDegrees = 1.5;
  final int waitForTime = 15;
  
  double angle;
  double speed;
  boolean withinTolerance = false;

  int timer = 0;

  public DriveAngle(double angle) {
    drivetrain = KitDrivetrain.getInstance();
    leds = LEDController.getInstance();

    requires(drivetrain);

    this.angle = angle;

    if (Math.abs(angle) - drivetrain.getGyroAngle() >= 135) {
      drivetrain.changeGyroGains(pGyro1, iGyro1, dGyro1);
      this.speed = 0.5;
      setTimeout(2);
    }
    else {
      drivetrain.changeGyroGains(pGyro0, iGyro0, dGyro0);
      this.speed = 0.6;
      setTimeout(1.5);
    }
  }

  @Override
  protected void initialize() {
  }

  @Override
  protected void execute() {
    leds.setMode(LEDMode.AUTO);
    drivetrain.turnDrive(angle, speed, toleranceDegrees);
    withinTolerance = (drivetrain.getGyroAngle() < (angle + toleranceDegrees) && drivetrain.getGyroAngle() > (angle - toleranceDegrees));

    if (withinTolerance) {
      timer++;
    }
  }

  @Override
  protected boolean isFinished() {
    if (withinTolerance && timer > waitForTime) {
      System.out.println("Within Tolerance");
      return true;
    }
    else if (isTimedOut()) {
      System.out.println("Timed Out");
      return true;
    }
    return false;
  }

  @Override
  protected void end() {
    drivetrain.arcadeDrive(0, 0);
  }
  
  @Override
  protected void interrupted() {
    end();
  }
}
