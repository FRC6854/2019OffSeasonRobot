package frc.robot.commands.gyro;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.KitDrivetrain;

public class ResetGyro extends InstantCommand {

  private KitDrivetrain drivetrain = null;

  public ResetGyro() {
    drivetrain = KitDrivetrain.getInstance();

    addRequirements(drivetrain);
  }

  @Override
  public void initialize() {
    drivetrain.resetGyro();
  }
}
