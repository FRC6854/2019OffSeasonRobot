package frc.robot.commands.drivetrain;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Constants;
import frc.robot.subsystems.KitDrivetrain;
import viking.led.LEDController;
import viking.led.LEDController.LEDMode;

public class DriveAngle extends CommandBase implements Constants {
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

    addRequirements(drivetrain);

    this.angle = angle;

    if (Math.abs(angle) - drivetrain.getGyroAngle() >= 135) {
      drivetrain.changeGyroGains(pGyro1, iGyro1, dGyro1);
      this.speed = 0.5;
      withTimeout(2);
    }
    else {
      drivetrain.changeGyroGains(pGyro0, iGyro0, dGyro0);
      this.speed = 0.6;
      withTimeout(1.5);
    }
  }

  @Override
  public void execute() {
    leds.setMode(LEDMode.AUTO);
    drivetrain.turnDrive(angle, speed, toleranceDegrees);
    withinTolerance = (drivetrain.getGyroAngle() < (angle + toleranceDegrees) && drivetrain.getGyroAngle() > (angle - toleranceDegrees));

    if (withinTolerance) {
      timer++;
    }
  }

  @Override
  public boolean isFinished() {
    if (withinTolerance && timer > waitForTime) {
      return true;
    }
    return false;
  }

  @Override
  public void end(boolean interrupted) {
    if (interrupted == true) {
      System.out.println("Timed Out");
      leds.setMode(LEDMode.ERROR);
    } 
    else {
      System.out.println("Within Tolerance");
    }

    drivetrain.arcadeDrive(0, 0);
  }
}
