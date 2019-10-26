package frc.robot.command_groups.drivetrain.auto;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Robot;
import frc.robot.commands.arm.SetStage;
import frc.robot.commands.drivetrain.*;
import frc.team6854.LEDController.LEDMode;

public class Drive90Drive extends CommandGroup {
  public Drive90Drive() {
    requires(Robot.drivetrain);
    requires(Robot.gyro);

    Robot.leds.currentMode = LEDMode.AUTO;

    addSequential(new DriveDistance(2));
    addSequential(new DriveAngle(-90));
    addSequential(new DriveDistance(2));
    addSequential(new DriveAngle(0));
    addSequential(new DriveDistance(1));
    addSequential(new SetStage(2));
  }
}
