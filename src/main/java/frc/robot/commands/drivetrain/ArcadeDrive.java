package frc.robot.commands.drivetrain;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.KitDrivetrain;
import frc.team6854.LEDController;
import frc.team6854.Limelight;
import frc.team6854.OI;
import frc.team6854.LEDController.LEDMode;
import frc.team6854.Limelight.LightMode;

public class ArcadeDrive extends Command {
  private KitDrivetrain drivetrain = null;
  private Limelight limelight = null;
  private OI oi = null;
  private LEDController leds = null;

  double tX = 0;
  double kP = 0.04;
  double maxCommand = 0.5;

  public ArcadeDrive() {
    drivetrain = KitDrivetrain.getInstance();
    limelight = Limelight.getInstance();
    oi = OI.getInstance();
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
    if (oi.getDriverAButton() == true) {
      leds.setMode(LEDMode.VISION);

      limelight.setDriverMode(false);
      limelight.setLEDMode(LightMode.ON);

      double steeringAdjust = kP * tX;
      if(steeringAdjust >= maxCommand) {
        steeringAdjust = maxCommand;
      }
      double operatorThrottle = oi.getDriverLeftStickY();

      drivetrain.arcadeDrive(operatorThrottle, steeringAdjust);
    } 

    else {
      leds.setMode(LEDMode.TELEOP);

      limelight.setLEDMode(LightMode.OFF);
      limelight.setDriverMode(true);

      drivetrain.arcadeDrive(oi.getDriverLeftStickY(), oi.getDriverRightStickX());
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
