package frc.robot.commands.drivetrain;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.KitDrivetrain;
import viking.led.LEDController;
import viking.led.LEDController.LEDMode;

public class ProfileFollower extends CommandBase {

  private KitDrivetrain drivetrain = null;
  private LEDController leds = null;
  String path = null;

  public ProfileFollower(String path) {
    drivetrain = KitDrivetrain.getInstance();
    leds = LEDController.getInstance();

    addRequirements(drivetrain);

    this.path = path;

    withTimeout(15.0);
  }

  @Override
  public void initialize() {
    leds.setMode(LEDMode.AUTO);
    drivetrain.zeroSensors();
    drivetrain.resetMotionProfile();
    System.out.println("Filling Talons...");
    drivetrain.loadMotionProfiles(path);

    System.out.println("Executing the Profile");

    drivetrain.motionProfile();
  }

  @Override
  public boolean isFinished() {
    return (drivetrain.getLeftMaster().isMotionProfileFinished() && drivetrain.getRightMaster().isMotionProfileFinished());
  }

  @Override
  public void end(boolean interrupted) {
    System.out.println("Done MP driving!");
    drivetrain.resetMotionProfile();
  }
}
