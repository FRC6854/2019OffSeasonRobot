package frc.robot.commands.drivetrain;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.KitDrivetrain;
import viking.led.LEDController;
import viking.led.LEDController.LEDMode;

public class DriveProfileWithArm extends CommandBase {

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

    addRequirements(drivetrain, arm);
  }

  @Override
  public void initialize() {
    leds.setMode(LEDMode.AUTO);
    drivetrain.zeroSensors();
    drivetrain.resetMotionProfile();
    drivetrain.loadMotionProfiles(path);

    drivetrain.motionProfile();

    timer.start();
  }

  @Override
  public void execute() {
    for (int i = 0; i < driveAtTimes.length; i++) {
      if(timer.get() >= driveAtTimes[i]) {
        arm.setStage(stages[i]);
      }
    }
  }

  @Override
  public boolean isFinished() {
    return (drivetrain.getLeftMaster().isMotionProfileFinished() && drivetrain.getRightMaster().isMotionProfileFinished());
  }

  @Override
  public void end(boolean interrupted) {
    timer.stop();
    drivetrain.resetMotionProfile();
  }
}
