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
import frc.team6854.Limelight.LightMode;

public class Robot extends TimedRobot implements RobotMap {

  public static CSVFileReader reader = new CSVFileReader();

  public static KitDrivetrain drivetrain;
  
  public static Limelight limelight = new Limelight(LightMode.OFF);

  public static Scheduler scheduler;

  public static Arm arm;

  public static Gyro gyro;

  public static LEDController leds = new LEDController();
  
  public static OI oi = new OI();
  
  @Override
  public void robotInit() {
    drivetrain = KitDrivetrain.getInstance();
    arm = Arm.getInstance();
    scheduler = Scheduler.getInstance();
    gyro = Gyro.getInstance();
  }

  @Override
  public void robotPeriodic() {
    arm.updateFaults();
    leds.setMode();

    SmartDashboard.putNumber("DT L Ticks", drivetrain.getLeftTicks());
    SmartDashboard.putNumber("DT R Ticks", drivetrain.getRightTicks());

    SmartDashboard.putString("Current LED Mode", leds.currentMode.name());

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
    leds.resetLastMode();

    // Drive 2 meters, turn left 90, drive 2 meters 
    scheduler.add(new Drive90Drive());
  }

  @Override
  public void autonomousPeriodic() {
  }

  @Override
  public void teleopInit() {
    scheduler.removeAll();
    leds.resetLastMode();
    scheduler.add(new ArcadeDrive());
    scheduler.add(new ZeroArm());
  }

  @Override
  public void teleopPeriodic() {
  }

  @Override
  public void testInit() {
    leds.resetLastMode();
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
