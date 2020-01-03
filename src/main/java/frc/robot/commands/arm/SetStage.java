package frc.robot.commands.arm;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Arm;

public class SetStage extends CommandBase {
  private Arm arm = null;

  int stage = 1;

  int timer = 0;

  // execute() runs 50 times per second or every 20ms. So the command should run a minimum of half a second
  static final int waitForTime = 25;
  static final int velocityThreshold = 2;

  public SetStage(int stage) {
    arm = Arm.getInstance();

    addRequirements(arm);

    this.stage = stage;
  }

  @Override
  public void initialize() {
    arm.setStage(stage);
  }

  @Override
  public void execute() {
    timer++;
  }

  @Override
  public boolean isFinished() {
    return (timer > waitForTime && (arm.getArmVelocity() <= velocityThreshold && arm.getArmVelocity() >= -velocityThreshold));
  }

  @Override
  public void end(boolean interrupted) {
    arm.driveManual(0);
  }
}
