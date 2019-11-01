package frc.robot.commands.drivetrain;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.KitDrivetrain;
import frc.team6854.LEDController;
import frc.team6854.LEDController.LEDMode;

public class DriveAngle extends Command {
  private KitDrivetrain drivetrain = null;
  private LEDController leds = null;

  double angle = 0;
  final double speed = 0.5;
  final double toleranceDegrees = 1;

  
  boolean withinTolerance = false;

  int timer = 0;

  public DriveAngle(double angle) {
    drivetrain = KitDrivetrain.getInstance();
    leds = LEDController.getInstance();

    requires(drivetrain);
    setTimeout(3);

    this.angle = angle;
  }

  @Override
  protected void initialize() {
  }

  @Override
  protected void execute() {
    leds.setMode(LEDMode.AUTO);
    drivetrain.turnDrive(angle, speed, toleranceDegrees);
    withinTolerance = (drivetrain.getGyroAngle() < (angle + toleranceDegrees) && drivetrain.getGyroAngle() > (angle - toleranceDegrees));

    if (drivetrain.gyroPIDDone() && withinTolerance) {
      timer++;
    }
  }

  @Override
  protected boolean isFinished() {
    System.out.println(withinTolerance);
    return ((drivetrain.gyroPIDDone() && withinTolerance && timer > 25) || isTimedOut()) ;
  }

  @Override
  protected void end() {
    System.out.println("Finished setting angle to: " + angle);
    drivetrain.arcadeDrive(0, 0);
  }
  
  @Override
  protected void interrupted() {
    System.out.println("Interrupted setting angle to: " + angle);
    end();
  }
}
