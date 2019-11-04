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

  private final double tolerance = 1;

  private double tX = 0;
  private final double kP = 0.04;
  private final double maxCommand = 0.5;

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

    drivetrain.arcadeDrive(0.3, steeringAdjust);
  }

  @Override
  protected boolean isFinished() {
    return drivetrain.getFrontSensor() || isTimedOut();
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
