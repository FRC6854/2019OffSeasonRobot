package frc.robot.commands.arm;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.Arm;

public class SetStage extends Command {
  private Arm arm = null;

  int stage = 1;
 
  boolean finished = false;

  int timer = 0;

  // execute() runs 50 times per second or every 20ms. So the command should run a minimum of half a second
  static final int waitForTime = 25;
  static final int velocityThreshold = 2;

  public SetStage(int stage) {
    arm = Arm.getInstance();

    requires(arm);

    this.stage = stage;
  }

  @Override
  protected void initialize() {
    arm.setStage(stage);
  }

  @Override
  protected void execute() {
    timer++;

    finished = (timer > waitForTime && (arm.getArmVelocity() <= velocityThreshold && arm.getArmVelocity() >= -velocityThreshold));
  }

  @Override
  protected boolean isFinished() {
    return finished;
  }

  @Override
  protected void end() {
    arm.driveManual(0);
  }

  @Override
  protected void interrupted() {
    end();
  }
}
