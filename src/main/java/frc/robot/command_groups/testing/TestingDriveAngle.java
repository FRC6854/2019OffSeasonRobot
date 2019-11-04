package frc.robot.command_groups.testing;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.drivetrain.DriveAngle;
import frc.robot.commands.gyro.ResetGyro;
import frc.robot.subsystems.KitDrivetrain;

public class TestingDriveAngle extends CommandGroup {

  private KitDrivetrain drivetrain = null;

  public TestingDriveAngle() {
    drivetrain = KitDrivetrain.getInstance();

    requires(drivetrain);

    System.out.println("Testing");

    addSequential(new ResetGyro());
    addSequential(new DriveAngle(-90));
  }
}
