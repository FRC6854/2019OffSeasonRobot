package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.command_groups.drivetrain.auto.Drive90Drive;
import frc.robot.commands.arm.*;
import frc.robot.commands.drivetrain.*;
import frc.robot.commands.gyro.*;
import frc.robot.subsystems.*;
import frc.team6854.OI; 

public class Robot extends TimedRobot implements RobotMap {

  private static KitDrivetrain drivetrain;

  private static OI oi;

  private static Scheduler scheduler;

  private static Arm arm;
  
  @Override
  public void robotInit() {
    drivetrain = KitDrivetrain.getInstance();
    scheduler = Scheduler.getInstance();
    arm = Arm.getInstance();
    oi = OI.getInstance();
  }

  @Override
  public void robotPeriodic() {
    arm.updateFaults();

    SmartDashboard.putNumber("DT L Ticks", drivetrain.getLeftTicks());
    SmartDashboard.putNumber("DT R Ticks", drivetrain.getRightTicks());

    SmartDashboard.putNumber("Gyro Angle", drivetrain.getGyroAngle());

    SmartDashboard.putNumber("Left Velocity", drivetrain.getLeftVelocity());
    SmartDashboard.putNumber("Right Velocity", drivetrain.getRightVelocity());

    SmartDashboard.putData("Reset Gyro", new ResetGyro());

    scheduler.run();
  }

  @Override
  public void disabledInit() {
    scheduler.removeAll();
    drivetrain.fullStop();
  }

  @Override
  public void autonomousInit() {
    scheduler.removeAll();

    // Drive 2 meters, turn left 90, drive 2 meters 
    scheduler.add(new Drive90Drive());
  }

  @Override
  public void teleopInit() {
    scheduler.removeAll();
    scheduler.add(new ArcadeDrive());
    scheduler.add(new ZeroArm());
  }
}
