package frc.robot.commands.drivetrain;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.KitDrivetrain;
import viking.led.LEDController;
import viking.led.LEDController.LEDMode;

public class DriveDistance extends CommandBase {
  private KitDrivetrain drivetrain = null;
  private LEDController leds = null;
  
  double meters;

  int timer = 0;

  final int waitForTime = 15;

  public DriveDistance(double meters) {
    drivetrain = KitDrivetrain.getInstance();
    leds = LEDController.getInstance();

    addRequirements(drivetrain);

    this.meters = meters;
  }

  @Override
  public void initialize() {
    drivetrain.zeroSensors();
    drivetrain.driveMeters(meters);
  }

  @Override
  public void execute() {
    leds.setMode(LEDMode.AUTO);
    timer++;
  }

  @Override
  public boolean isFinished() {
    return (timer > waitForTime && (drivetrain.getLeftVelocity() == 0 && drivetrain.getRightVelocity() == 0));
  }

  @Override
  public void end(boolean interrupted) {
    if (interrupted == true) {
      System.out.println("Interrupted Driving " + meters + "m");
    }
    else {
      System.out.println("Finished Driving " + meters + "m");
      drivetrain.arcadeDrive(0, 0);
    }
  }
}
