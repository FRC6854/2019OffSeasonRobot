package frc.robot.commands.drivetrain;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.KitDrivetrain;
import viking.led.LEDController;
import viking.Limelight;
import viking.led.LEDController.LEDMode;
import viking.Limelight.LightMode;

public class DriveVisionTarget extends Command {

  private KitDrivetrain drivetrain = null;
  private LEDController leds = null;
  private Limelight limelight = null;

  private double tX = 0;
  private final double kP = 0.04;
  private final double maxCommand = 0.5;

  public DriveVisionTarget() {
    drivetrain = KitDrivetrain.getInstance();
    leds = LEDController.getInstance();
    limelight = Limelight.getInstance();

    setTimeout(5.0);

    requires(drivetrain);
  }

  @Override
  protected void initialize() {
    drivetrain.arcadeDrive(0, 0);
  }

  @Override
  protected void execute() {
    tX = limelight.targetX();

    leds.setMode(LEDMode.VISION);

    limelight.setDriverMode(false);
    limelight.setLEDMode(LightMode.ON);

    double steeringAdjust = kP * tX;

    if(steeringAdjust >= maxCommand) {
      steeringAdjust = maxCommand;
    }

    drivetrain.arcadeDrive(0.5, steeringAdjust);
  }

  @Override
  protected boolean isFinished() {
    if (drivetrain.getFrontSensor() == true) {
      System.out.println("Front Sensor End");
      return true;
    }
    if (isTimedOut() == true) {
      System.out.println("Timed Out");
      return true;
    }

    return false;
  }
  
  @Override
  protected void end() {
    drivetrain.arcadeDrive(0, 0);
    limelight.setDriverMode(true);
    limelight.setLEDMode(LightMode.OFF);
  }

  @Override
  protected void interrupted() {
  }
}
