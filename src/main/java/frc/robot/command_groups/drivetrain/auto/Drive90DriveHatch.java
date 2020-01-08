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

public class Drive90DriveHatch extends SequentialCommandGroup {
  private KitDrivetrain drivetrain = null;
  private Arm arm = null;

  public Drive90DriveHatch(int stage) {
    drivetrain = KitDrivetrain.getInstance();
    arm = Arm.getInstance();

    addRequirements(drivetrain, arm);
    
    addCommands(
      new ParallelCommandGroup(
        new ZeroArm().withTimeout(6.0),
        new ResetGyro()
      ),

      new DriveDistance(2),
      new DriveAngle(-90).withTimeout(1.5),
      new DriveDistance(2),
      new DriveAngle(0).withTimeout(1.5)
    );

    switch (stage) {
      case 1:
        addCommands(
          new SetStage(1),
          new DriveVisionTarget().withTimeout(5.0),

          new ParallelCommandGroup(
            new DropHatch().withTimeout(1.0)
          ),

          new WaitCommand(0.2),
          new DriveDistance(-1)
        );
        break;

      case 3:
        addCommands(
          new SetStage(3),
          new DriveVisionTarget().withTimeout(5.0),

          new ParallelCommandGroup(
            new DropHatch().withTimeout(1.0)
          ),

          new WaitCommand(0.1),
          new DriveDistance(-1)
        );
        break;
    }
  }
}
