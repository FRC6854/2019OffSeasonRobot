package frc.robot.command_groups.testing;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.drivetrain.DriveAngle;
import frc.robot.commands.gyro.ResetGyro;
import frc.robot.subsystems.KitDrivetrain;

public class TestingDriveAngle extends SequentialCommandGroup {

  private KitDrivetrain drivetrain = null;

  public TestingDriveAngle() {
    drivetrain = KitDrivetrain.getInstance();

    addRequirements(drivetrain);

    addCommands(
      new ResetGyro(),
      new DriveAngle(-180)
    );
  }
}
