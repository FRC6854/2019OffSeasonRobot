package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

import frc.robot.auto.AutoManager;
import frc.robot.subsystems.*;

import frc.robot.commands.arm.OperateArm;
import frc.robot.commands.arm.ZeroArm;
import frc.robot.commands.drivetrain.ArcadeDrive;

import viking.Controller;
import viking.OI;
import viking.led.LEDController;
import viking.led.LEDController.LEDMode;

public class Robot extends TimedRobot implements RobotMap {

  public static Controller driver;

  private static KitDrivetrain drivetrain;

  private static LEDController leds;

  private static CommandScheduler scheduler;

  private static Arm arm;

  private static AutoManager autoManager;

  private double gyroP = 0;
  private double gyroI = 0;
  private double gyroD = 0;

  
  @Override
  public void robotInit() {
    scheduler = CommandScheduler.getInstance();
    drivetrain = KitDrivetrain.getInstance();
    arm = Arm.getInstance();
    autoManager = AutoManager.getInstance();
    leds = LEDController.getInstance();

    driver = new Controller(CONTROLLER_DRIVER);
    
    // Create Instance of OI to make sure LEDs work
    OI.getInstance();

    SmartDashboard.putNumber("Gyro P", Constants.pGyro0);
    SmartDashboard.putNumber("Gyro I", Constants.iGyro0);
    SmartDashboard.putNumber("Gyro D", Constants.dGyro0);

    drivetrain.setDefaultCommand(new ArcadeDrive());
    arm.setDefaultCommand(new OperateArm());
  }

  @Override
  public void robotPeriodic() {
    SmartDashboard.putNumber("Gyro Angle", drivetrain.getGyroAngle());

    gyroP = SmartDashboard.getNumber("Gyro P", Constants.pGyro0);
    gyroI = SmartDashboard.getNumber("Gyro I", Constants.iGyro0);
    gyroD = SmartDashboard.getNumber("Gyro D", Constants.dGyro0);

    SmartDashboard.putBoolean("Front Sensor", drivetrain.getFrontSensor());
    SmartDashboard.putNumber("Distance", (int) drivetrain.getDistanceSensor());

    SmartDashboard.putData(drivetrain);
    SmartDashboard.putData(arm);
    
    SmartDashboard.putData("Auto Chooser", autoManager.getAutoChooser());
    SmartDashboard.putData("Auto Rocket Level", autoManager.getAutoHatch());
    SmartDashboard.putData("Slow Mode", autoManager.getSlowModeChooser());
    SmartDashboard.putData("Scheduler", scheduler);
  }

  @Override
  public void teleopInit() {
    scheduler.cancelAll();
    drivetrain.setSpeedMultiplier(autoManager.getSpeedMultiplier());
    scheduler.schedule(new ZeroArm());
  }

  @Override
  public void autonomousInit() {
    scheduler.cancelAll();

    drivetrain.changeGyroGains(gyroP, gyroI, gyroD);

    scheduler.schedule(autoManager.getAutoChooserCommand());
  }

  @Override
  public void teleopPeriodic() {
    scheduler.run();
    drivetrain.updateTable();
  }

  @Override
  public void autonomousPeriodic() {
    scheduler.run();
  }

  @Override
  public void disabledInit() {
    leds.setMode(LEDMode.DEFAULT);
    drivetrain.writeTable();
    drivetrain.clearTable();
  }
}
