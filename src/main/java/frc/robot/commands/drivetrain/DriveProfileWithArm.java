package frc.robot.commands.drivetrain;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

import frc.robot.subsystems.Arm;
import frc.robot.subsystems.KitDrivetrain;
import viking.led.LEDController;
import viking.led.LEDController.LEDMode;

public class DriveProfileWithArm extends Command {

  private KitDrivetrain drivetrain = null;
  private Arm arm = null;
  private LEDController leds = null;

  String path;
  Timer timer;

  double[] driveAtTimes;
  int[] stages;

  public DriveProfileWithArm(String path, double[] time, int[] stages) {
    drivetrain = KitDrivetrain.getInstance();
    arm = Arm.getInstance();
    leds = LEDController.getInstance();
    timer = new Timer();

    this.path = path;
    this.driveAtTimes = time;
    this.stages = stages;

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
    for (int i = 0; i < driveAtTimes.length; i++) {
      if(timer.get() >= driveAtTimes[i]) {
        arm.setStage(stages[i]);
      }
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
