package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.command_groups.drivetrain.auto.Drive90Drive;
import frc.robot.commands.arm.*;
import frc.robot.commands.drivetrain.*;
import frc.robot.commands.gyro.*;
import frc.team6854.*;
import frc.robot.subsystems.*; 
import frc.team6854.Limelight;

public class Robot extends TimedRobot implements RobotMap {

  private static KitDrivetrain drivetrain;
  
  private static Limelight limelight;

  private static Scheduler scheduler;

  private static Arm arm;

  private static Gyro gyro;

  private static LEDController leds;
  
  private static OI oi = new OI();
  
  @Override
  public void robotInit() {
    drivetrain = KitDrivetrain.getInstance();
    limelight = Limelight.getInstance();
    arm = Arm.getInstance();
    scheduler = Scheduler.getInstance();
    gyro = Gyro.getInstance();
    leds = LEDController.getInstance();
  }

  @Override
  public void robotPeriodic() {
    arm.updateFaults();

    SmartDashboard.putNumber("DT L Ticks", drivetrain.getLeftTicks());
    SmartDashboard.putNumber("DT R Ticks", drivetrain.getRightTicks());

    SmartDashboard.putNumber("Gyro Angle", gyro.getAngle());

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
  public void autonomousPeriodic() {
  }

  @Override
  public void teleopInit() {
    scheduler.removeAll();
    scheduler.add(new ArcadeDrive());
    scheduler.add(new ZeroArm());
  }

  @Override
  public void teleopPeriodic() {
  }

  @Override
  public void testInit() {
    scheduler.removeAll();
  }

  @Override
  public void testPeriodic() {
    if (oi.getDriverAButtonPressed()) {
      drivetrain.zeroSensor();
      drivetrain.driveRotations(1);
    }
  }
}
