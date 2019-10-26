package frc.robot.commands.gyro;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.Robot;

public class ResetGyro extends InstantCommand {
  public ResetGyro() {
    super();
    requires(Robot.gyro);
  }
  @Override
  protected void initialize() {
    Robot.gyro.reset();
  }

}
