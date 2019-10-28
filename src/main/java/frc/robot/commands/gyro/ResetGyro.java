package frc.robot.commands.gyro;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.subsystems.Gyro;

public class ResetGyro extends InstantCommand {
  private Gyro gyro;

  public ResetGyro() {
    super();
    gyro = Gyro.getInstance();
    requires(gyro);
  }
  @Override
  protected void initialize() {
    gyro.reset();
  }
}
