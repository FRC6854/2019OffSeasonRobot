package frc.robot.commands.drivetrain;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.KitDrivetrain;
import frc.team6854.LEDController;
import frc.team6854.LEDController.LEDMode;

public class DriveAngle extends Command {
  private KitDrivetrain drivetrain = null;
  private LEDController leds = null;

  double angle = 0;
  double speed = 0.6;

  final double toleranceDegrees = 1;
  
  boolean withinTolerance = false;

  int timer = 0;

  final int waitForTime = 15;

  public DriveAngle(double angle, double speed) {
    drivetrain = KitDrivetrain.getInstance();
    leds = LEDController.getInstance();

    requires(drivetrain);
    setTimeout(3);

    this.angle = angle;
    this.speed = speed;
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
