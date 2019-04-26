package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import frc.robot.commands.SwerveDrive;
import frc.robot.commands.TankDrive;
import frc.robot.subsystems.SwerveDrivetrain.SwerveWheel;
import frc.robot.subsystems.SwerveDrivetrain.SwerveWheelController;

public class Robot extends TimedRobot implements RobotMap {
  public static OI oi = new OI();

  public static SwerveWheel frontLeft = new SwerveWheel(CAN_FL_STEER, ANALOG_FL_ABSENCODER, CAN_FL_DRIVE);
  public static SwerveWheel frontRight = new SwerveWheel(CAN_FR_STEER, ANALOG_FR_ABSENCODER, CAN_FR_DRIVE);
  public static SwerveWheel backLeft = new SwerveWheel(CAN_BL_STEER, ANALOG_BL_ABSENCODER, CAN_BL_DRIVE);
  public static SwerveWheel backRight = new SwerveWheel(CAN_BR_STEER, ANALOG_BR_ABSENCODER, CAN_BR_DRIVE);

  public static SwerveWheelController drivetrain = new SwerveWheelController(frontLeft, frontRight, backLeft, backRight);

  // Note for later
  // When IZone is used, the controller will automatically clear the integral
  // accumulated error if the closed loop error is outside the IZone.

  public static Scheduler scheduler = Scheduler.getInstance();

  @Override
  public void robotInit() {
  }

  @Override
  public void robotPeriodic() {
    scheduler.run();
  }

  @Override
  public void disabledInit() {
    scheduler.removeAll();
  }

  @Override
  public void autonomousInit() {
    scheduler.removeAll();
    scheduler.add(new SwerveDrive());
  }

  @Override
  public void autonomousPeriodic() {
  }

  @Override
  public void teleopInit() {
    scheduler.removeAll();
    scheduler.add(new SwerveDrive());
  }

  @Override
  public void teleopPeriodic() {
  }

  @Override
  public void testInit() {
    scheduler.removeAll();
    scheduler.add(new TankDrive());
  }
}