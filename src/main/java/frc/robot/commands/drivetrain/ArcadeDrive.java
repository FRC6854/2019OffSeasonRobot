package frc.robot.commands.drivetrain;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.subsystems.KitDrivetrain;
import viking.led.LEDController;
import viking.Limelight;
import viking.led.LEDController.LEDMode;
import viking.Limelight.LightMode;

public class ArcadeDrive extends CommandBase {
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

    addRequirements(drivetrain);
  }
  
  @Override
  public void execute() {
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
}
