package frc.robot.command_groups.drivetrain.auto;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.arm.SetStage;
import frc.robot.commands.drivetrain.*;
import frc.robot.commands.gyro.ResetGyro;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.KitDrivetrain;

public class DriveAroundTrailer extends CommandGroup {
  private KitDrivetrain drivetrain = null;
  private Arm arm = null;

  public DriveAroundTrailer() {
    drivetrain = KitDrivetrain.getInstance();
    arm = Arm.getInstance();

    requires(drivetrain);
    requires(arm);

    addSequential(new ResetGyro());
    addSequential(new DriveDistance(3.5));
    addSequential(new DriveAngle(90));
    addSequential(new DriveDistance(1));
    addSequential(new SetStage(2));
    addSequential(new SetStage(1));
    addSequential(new DriveDistance(-1));
    addSequential(new DriveAngle(0));
    addSequential(new DriveDistance(-3));
  }
}
