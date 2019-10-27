package frc.robot.command_groups.drivetrain.auto;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.arm.SetStage;
import frc.robot.commands.drivetrain.*;
import frc.robot.subsystems.Gyro;
import frc.robot.subsystems.KitDrivetrain;

public class Drive90Drive extends CommandGroup {
  private KitDrivetrain drivetrain = null;
  private Gyro gyro = null;

  public Drive90Drive() {
    drivetrain = KitDrivetrain.getInstance();
    gyro = Gyro.getInstance();

    requires(drivetrain);
    requires(gyro);

    addSequential(new DriveDistance(2));
    addSequential(new DriveAngle(-90));
    addSequential(new DriveDistance(2));
    addSequential(new DriveAngle(0));
    addSequential(new DriveDistance(1));
    addSequential(new SetStage(2));
    addSequential(new DriveDistance(-1));
  }
}
