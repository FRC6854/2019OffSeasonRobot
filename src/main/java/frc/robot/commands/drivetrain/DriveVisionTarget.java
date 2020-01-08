package frc.robot.commands.drivetrain;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.KitDrivetrain;
import viking.led.LEDController;
import viking.Limelight;
import viking.led.LEDController.LEDMode;
import viking.Limelight.LightMode;

public class DriveVisionTarget extends CommandBase {

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

    addRequirements(drivetrain);
  }

  @Override
  public void initialize() {
    drivetrain.arcadeDrive(0, 0);
  }

  @Override
  public void execute() {
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
  public boolean isFinished() {
    if (drivetrain.getFrontSensor() == true) {
      System.out.println("Front Sensor End");
      return true;
    }

    return false;
  }

  @Override
  public void end(boolean interrupted) {
    if (interrupted == true) {
      System.out.println("Timed Out");
    }

    drivetrain.arcadeDrive(0, 0);
    limelight.setDriverMode(true);
    limelight.setLEDMode(LightMode.OFF);
  }
}
