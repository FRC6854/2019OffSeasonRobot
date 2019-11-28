package frc.robot.command_groups.drivetrain.auto;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.WaitTime;
import frc.robot.commands.arm.DropHatch;
import frc.robot.commands.arm.SetStage;
import frc.robot.commands.arm.ZeroArm;
import frc.robot.commands.drivetrain.*;
import frc.robot.commands.gyro.ResetGyro;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.KitDrivetrain;

public class DriveProfileHatch extends CommandGroup {

  private KitDrivetrain drivetrain = null;
  private Arm arm = null;

  public DriveProfileHatch() {
    drivetrain = KitDrivetrain.getInstance();
    arm = Arm.getInstance();

    requires(drivetrain);
    requires(arm);

    addParallel(new ZeroArm());
    addSequential(new ResetGyro());

    addSequential(new DriveProfileWithArm("drive_rocket", new double[] { 2.0 }, new int[] { 1 }));
    addSequential(new DriveVisionTarget());
    addParallel(new DropHatch());
    addSequential(new WaitTime(0.2));

    addSequential(new ProfileFollower("drive_retrieve"));
    addSequential(new DriveAngle(90));
    addSequential(new DriveDistance(0.4));
    addSequential(new SetStage(1));
    addSequential(new DriveDistance(-0.4));
    addSequential(new ZeroArm());
    addSequential(new DriveAngle(-90));

    addSequential(new DriveProfileWithArm("drive_back", new double[] { 0.0 }, new int[] { 3 }));
    addSequential(new DriveVisionTarget());
    addParallel(new DropHatch());
    addSequential(new WaitTime(0.2));
    addSequential(new DriveDistance(-0.5));
  }
}
