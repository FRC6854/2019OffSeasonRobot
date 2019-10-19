package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.arm.*;
import frc.robot.commands.drivetrain.*;
import frc.team6854.*;
import frc.robot.subsystems.*; 
import frc.team6854.Limelight;
import frc.team6854.Limelight.LightMode;

public class Robot extends TimedRobot implements RobotMap {
  public static CSVFileReader reader = new CSVFileReader();

  public static KitDrivetrain drivetrain = new KitDrivetrain(CAN_LEFT_FRONT, CAN_LEFT_BACK, CAN_RIGHT_FRONT, CAN_RIGHT_BACK);
  
  public static Limelight limelight = new Limelight(LightMode.OFF);

  public static Scheduler scheduler = Scheduler.getInstance();

  public static Arm arm = new Arm(CAN_ARM);

  public static LEDController leds = new LEDController();
  
  public static OI oi = new OI();
  
  @Override
  public void robotInit() {
  }

  @Override
  public void robotPeriodic() {
    arm.updateFaults();
    leds.setMode();

    SmartDashboard.putNumber("DT L Ticks", drivetrain.getLeftTicks());
    SmartDashboard.putNumber("DT R Ticks", drivetrain.getRightTicks());
    SmartDashboard.putData(drivetrain);

    SmartDashboard.putString("Current LED Mode", leds.currentMode.name());

    SmartDashboard.putString("Arm Control Mode", arm.getControlMode().name());
    SmartDashboard.putNumber("Arm Stage", arm.selectedStage);
    SmartDashboard.putNumber("Arm Ticks", arm.getTicks());
    SmartDashboard.putData(arm);

    SmartDashboard.putData(new MotionProfile("testing")); // RUN THE MOTION PROFILE IN THE DASHBOARD

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

    // Drive 10 Rotations
    scheduler.add(new DriveDistance(2));
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
