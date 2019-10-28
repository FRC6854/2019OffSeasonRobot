package frc.robot.commands.gyro;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.subsystems.KitDrivetrain;

public class ResetGyro extends InstantCommand {

  private KitDrivetrain drivetrain = null;

  public ResetGyro() {
    drivetrain = KitDrivetrain.getInstance();
    requires(drivetrain);
  }
  @Override
  protected void initialize() {
    drivetrain.resetGyro();
  }
}
