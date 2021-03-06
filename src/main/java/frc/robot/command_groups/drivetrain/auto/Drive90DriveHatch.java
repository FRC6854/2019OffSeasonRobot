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

public class Drive90DriveHatch extends CommandGroup {
  private KitDrivetrain drivetrain = null;
  private Arm arm = null;

  public Drive90DriveHatch(int stage) {
    drivetrain = KitDrivetrain.getInstance();
    arm = Arm.getInstance();

    requires(drivetrain);
    requires(arm);

    addParallel(new ZeroArm());
    addSequential(new ResetGyro());
    addSequential(new DriveDistance(2));
    addSequential(new DriveAngle(-90));
    addSequential(new DriveDistance(2));
    addSequential(new DriveAngle(0));
    switch (stage) {
      case 1:
        addSequential(new SetStage(1));
        addSequential(new DriveVisionTarget());
        addParallel(new DropHatch());
        addSequential(new WaitTime(0.2));
        addSequential(new DriveDistance(-2));
        break;

      case 2:
        addSequential(new SetStage(2));
        addSequential(new DriveDistance(0.929));
        addParallel(new DropHatch());
        addSequential(new WaitTime(0.1));
        addParallel(new DriveDistance(-1));
        break;

      case 3:
        addSequential(new SetStage(3));
        addSequential(new DriveVisionTarget());
        addParallel(new DropHatch());
        addSequential(new WaitTime(0.1));
        addParallel(new DriveDistance(-1));
        break;
    }
  }
}
