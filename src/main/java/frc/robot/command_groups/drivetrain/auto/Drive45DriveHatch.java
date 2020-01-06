package frc.robot.command_groups.drivetrain.auto;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

import frc.robot.commands.arm.DropHatch;
import frc.robot.commands.arm.SetStage;
import frc.robot.commands.arm.ZeroArm;
import frc.robot.commands.drivetrain.*;
import frc.robot.commands.gyro.ResetGyro;

import frc.robot.subsystems.Arm;
import frc.robot.subsystems.KitDrivetrain;

public class Drive45DriveHatch extends SequentialCommandGroup {
  private KitDrivetrain drivetrain = null;
  private Arm arm = null;

  public Drive45DriveHatch(final int stage) {
    drivetrain = KitDrivetrain.getInstance();
    arm = Arm.getInstance();

    addRequirements(drivetrain, arm);

    addCommands(
      new ParallelCommandGroup(
        new ZeroArm(),
        new ResetGyro()
      ),

      new DriveDistance(1),
      new DriveAngle(-60),
      new DriveDistance(2.5),
      new DriveAngle(0)
    );

    switch (stage) {
      case 1:
        addCommands(
          new SetStage(1),
          new DriveVisionTarget(),

          new ParallelCommandGroup(
            new DropHatch()
          ),

          new WaitCommand(0.2),
          new DriveDistance(-1)
        );
        break;

      case 3:
        addCommands(
          new SetStage(3),
          new DriveVisionTarget(),

          new ParallelCommandGroup(
            new DropHatch()
          ),

          new WaitCommand(0.1),
          new DriveDistance(-1)
        );
        break;
    }
  }
}
