package frc.robot.commands.drivetrain;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

import frc.robot.subsystems.Arm;
import frc.robot.subsystems.KitDrivetrain;
import frc.team6854.LEDController;
import frc.team6854.LEDController.LEDMode;

public class DriveProfileWithArm extends Command {

  private KitDrivetrain drivetrain = null;
  private Arm arm = null;
  private LEDController leds = null;

  String path;
  Timer timer;

  boolean movedArm = false;
  double driveAtTime = 0.0;

  public DriveProfileWithArm(String path, double time) {
    drivetrain = KitDrivetrain.getInstance();
    arm = Arm.getInstance();
    leds = LEDController.getInstance();
    timer = new Timer();

    this.path = path;
    this.driveAtTime = time;

    requires(drivetrain);
    requires(arm);

    setTimeout(15.0);
  }

  @Override
  protected void initialize() {
    leds.setMode(LEDMode.AUTO);
    drivetrain.zeroSensors();
    drivetrain.resetMotionProfile();
    drivetrain.loadMotionProfiles(path);

    drivetrain.motionProfile();

    timer.start();
  }

  @Override
  protected void execute() {
    if(timer.get() >= driveAtTime && movedArm == false) {
      arm.setStage(1);
      movedArm = true;
    }
  }

  @Override
  protected boolean isFinished() {
    return (drivetrain.getLeftMaster().isMotionProfileFinished() && drivetrain.getRightMaster().isMotionProfileFinished()) || isTimedOut();
  }

  @Override
  protected void end() {
    timer.stop();
    drivetrain.resetMotionProfile();
  }

  @Override
  protected void interrupted() {
      end();
  }
}
