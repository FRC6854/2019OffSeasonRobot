package frc.robot.commands.drivetrain;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.KitDrivetrain;
import frc.team6854.LEDController;
import frc.team6854.LEDController.LEDMode;

public class DriveDistance extends Command {
  private KitDrivetrain drivetrain = null;
  private LEDController leds = null;
  
  double meters;

  int timer = 0;

  final int waitForTime = 15;

  public DriveDistance(double meters) {
    drivetrain = KitDrivetrain.getInstance();
    leds = LEDController.getInstance();

    requires(drivetrain);

    this.meters = meters;
  }

  @Override
  protected void initialize() {
    drivetrain.zeroSensors();
    drivetrain.driveMeters(meters);
  }

  @Override
  protected void execute() {
    leds.setMode(LEDMode.AUTO);
    timer++;
  }

  @Override
  protected boolean isFinished() {
    return (timer > waitForTime && (drivetrain.getLeftVelocity() == 0 && drivetrain.getRightVelocity() == 0));
  }

  @Override
  protected void end() {
    System.out.println("Finished Driving " + meters + "m");
    drivetrain.arcadeDrive(0, 0);
  }

  @Override
  protected void interrupted() {
    System.out.println("Interrupted Driving " + meters + "m");
    end();
  }
}
