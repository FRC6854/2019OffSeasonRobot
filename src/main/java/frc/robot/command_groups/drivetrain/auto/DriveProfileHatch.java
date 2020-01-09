package frc.robot.command_groups.drivetrain.auto;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

import frc.robot.commands.arm.DropHatch;
import frc.robot.commands.arm.ZeroArm;
import frc.robot.commands.drivetrain.*;
import frc.robot.commands.gyro.ResetGyro;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.KitDrivetrain;

public class DriveProfileHatch extends SequentialCommandGroup {

  private KitDrivetrain drivetrain = null;
  private Arm arm = null;

  public DriveProfileHatch() {
    drivetrain = KitDrivetrain.getInstance();
    arm = Arm.getInstance();

    addRequirements(drivetrain, arm);

    addCommands(
      new ParallelCommandGroup(
        //new ZeroArm().withTimeout(6.0), 
        new ResetGyro()
      ),

      new DriveProfileWithArm("drive_rocket", new double[] { 2.0 }, new int[] { 1 }).withTimeout(15.0), 
      new DriveVisionTarget().withTimeout(5.0),

      new ParallelCommandGroup(
        new DropHatch().withTimeout(1.0),
        new WaitCommand(0.2)
      ),

      new DriveDistance(-1)
    );
  }
}
