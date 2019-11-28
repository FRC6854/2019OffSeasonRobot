package frc.robot.commands.drivetrain;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.KitDrivetrain;
import frc.team6854.led.LEDController;
import frc.team6854.Limelight;
import frc.team6854.led.LEDController.LEDMode;
import frc.team6854.Limelight.LightMode;

public class ArcadeDrive extends Command {
  private KitDrivetrain drivetrain = null;
  private Limelight limelight = null;
  private LEDController leds = null;

  double tX = 0;
  double kP = 0.04;
  double maxCommand = 0.5;

  public ArcadeDrive() {
    drivetrain = KitDrivetrain.getInstance();
    limelight = Limelight.getInstance();
    leds = LEDController.getInstance();

    requires(drivetrain);
  }

  @Override
  protected void initialize() {
  }
  
  @Override
  protected void execute() {
    tX = limelight.targetX();

    // A button to vision aim using vision
    if (Robot.driver.getDriverAButton() == true) {
      leds.setMode(LEDMode.VISION);

      limelight.setDriverMode(false);
      limelight.setLEDMode(LightMode.ON);

      double steeringAdjust = kP * tX;
      if(steeringAdjust >= maxCommand) {
        steeringAdjust = maxCommand;
      }
      double operatorThrottle = Robot.driver.getDriverLeftStickY();

      drivetrain.arcadeDrive(operatorThrottle, steeringAdjust);
    } 

    else {
      leds.setMode(LEDMode.TELEOP);

      limelight.setLEDMode(LightMode.OFF);
      limelight.setDriverMode(true);

      drivetrain.arcadeDrive(Robot.driver.getDriverLeftStickY(), Robot.driver.getDriverRightStickX());
    }
  }

  @Override
  protected boolean isFinished() {
    return false;
  }

  @Override
  protected void end() {
  }

  @Override
  protected void interrupted() {
  }
}
