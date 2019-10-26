package frc.robot.commands.arm;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.Robot;

public class SetStage extends InstantCommand {
  int stage = 1;

  public SetStage(int stage) {
    super();
    requires(Robot.arm);

    this.stage = stage;
  }

  @Override
  protected void initialize() {
    Robot.arm.setStage(stage);
  }

}
