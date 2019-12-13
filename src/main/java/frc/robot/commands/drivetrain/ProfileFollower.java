package frc.robot.commands.drivetrain;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.KitDrivetrain;
import viking.led.LEDController;
import viking.led.LEDController.LEDMode;

public class ProfileFollower extends Command {

  private KitDrivetrain drivetrain = null;
  private LEDController leds = null;
  String path = null;

  public ProfileFollower(String path) {
    drivetrain = KitDrivetrain.getInstance();
    leds = LEDController.getInstance();
    requires(drivetrain);

    this.path = path;

    setTimeout(15.0);
  }

  @Override
  protected void initialize() {
    leds.setMode(LEDMode.AUTO);
    drivetrain.zeroSensors();
    drivetrain.resetMotionProfile();
    System.out.println("Filling Talons...");
    drivetrain.loadMotionProfiles(path);

    System.out.println("Executing the Profile");

    drivetrain.motionProfile();
  }

  @Override
  protected void execute() {

  }

  @Override
  protected boolean isFinished() {
    return (drivetrain.getLeftMaster().isMotionProfileFinished() && drivetrain.getRightMaster().isMotionProfileFinished()) || isTimedOut();
  }

  @Override
  protected void end() {
    System.out.println("Done MP driving!");
    drivetrain.resetMotionProfile();
  }

  @Override
  protected void interrupted() {
    end();
  }
}
