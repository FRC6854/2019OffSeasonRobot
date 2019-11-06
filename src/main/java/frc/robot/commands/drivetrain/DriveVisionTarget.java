package frc.robot.commands.drivetrain;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.KitDrivetrain;
import frc.team6854.LEDController;
import frc.team6854.Limelight;
import frc.team6854.LEDController.LEDMode;
import frc.team6854.Limelight.LightMode;

public class DriveVisionTarget extends Command {

  private KitDrivetrain drivetrain = null;
  private LEDController leds = null;
  private Limelight limelight = null;

  private double desiredDistance = 0;

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

  public DriveVisionTarget(double distance) {
    drivetrain = KitDrivetrain.getInstance();
    leds = LEDController.getInstance();
    limelight = Limelight.getInstance();

    this.desiredDistance = distance;

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

    if (desiredDistance == 0) {
      drivetrain.arcadeDrive(0.4, steeringAdjust);
    }
    else {

    }
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
