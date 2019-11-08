package frc.robot.commands.drivetrain;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.KitDrivetrain;

import com.ctre.phoenix.motion.SetValueMotionProfile;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;

import java.io.File;
import java.io.IOException;

public class ProfileFollower extends Command {

  private static final int min_points = 60;
  private Trajectory trajectory_left = null;
  private Trajectory trajectory_right = null;

  private KitDrivetrain drivetrain = null;

  public ProfileFollower(String leftFile, String rightFile) {
    drivetrain = KitDrivetrain.getInstance();
    requires(drivetrain);

    try {
      this.trajectory_left = Pathfinder.readFromCSV(new File(leftFile));
      this.trajectory_right = Pathfinder.readFromCSV(new File(rightFile));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  protected void initialize() {
    drivetrain.zeroSensors();
    System.out.println("Filling Talons...");
    drivetrain.startFillingLeft(drivetrain.pathfinderFormatToTalon(trajectory_left), trajectory_left.length());
    drivetrain.startFillingRight(drivetrain.pathfinderFormatToTalon(trajectory_right), trajectory_right.length());

    while (drivetrain.getLeftMpStatus().btmBufferCnt < min_points || drivetrain.getRightMpStatus().btmBufferCnt < min_points) {
      drivetrain.periodic();
    }

    System.out.println("Talons filled (enough)!");
  }

  @Override
  protected void execute() {
    drivetrain.leftMpControl(SetValueMotionProfile.Enable);
    drivetrain.rightMpControl(SetValueMotionProfile.Enable);
  }

  @Override
  protected boolean isFinished() {
    return (drivetrain.leftMpDone() || drivetrain.rightMpDone());
  }

  @Override
  protected void end() {
    System.out.println("Done MP driving!");
    drivetrain.leftMpControl(SetValueMotionProfile.Disable);
    drivetrain.rightMpControl(SetValueMotionProfile.Disable);
  }

  @Override
  protected void interrupted() {
  }
}
