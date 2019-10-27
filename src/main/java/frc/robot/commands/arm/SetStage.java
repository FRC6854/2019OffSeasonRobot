package frc.robot.commands.arm;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.Arm;

public class SetStage extends Command {
  private Arm arm = null;

  int stage = 1;
  final int error = 50;
  boolean finished = false;

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
    finished = ((arm.getTicks() + error < arm.getTicks() && arm.getTicks() - error > arm.getTicks()) && arm.getArmVelocity() == 0);
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
