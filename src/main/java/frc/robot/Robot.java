package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.auto.AutoManager;
import frc.robot.subsystems.*;
import team6854.Controller;
import team6854.OI;
import team6854.led.LEDController;
import team6854.led.LEDController.LEDMode;
import frc.robot.commands.arm.ZeroArm;

public class Robot extends TimedRobot implements RobotMap {

  public static Controller driver;

  private static KitDrivetrain drivetrain;

  private static LEDController leds;

  private static Scheduler scheduler;

  private static Arm arm;

  private static AutoManager autoManager;

  private double gyroP = 0;
  private double gyroI = 0;
  private double gyroD = 0;

  
  @Override
  public void robotInit() {
    drivetrain = KitDrivetrain.getInstance();
    scheduler = Scheduler.getInstance();
    arm = Arm.getInstance();
    autoManager = AutoManager.getInstance();
    leds = LEDController.getInstance();

    driver = new Controller(CONTROLLER_DRIVER);
    
    // Create Instance of OI to make sure LEDs work
    OI.getInstance();

    SmartDashboard.putNumber("Gyro P", Constants.pGyro0);
    SmartDashboard.putNumber("Gyro I", Constants.iGyro0);
    SmartDashboard.putNumber("Gyro D", Constants.dGyro0);
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
    scheduler.removeAll();
    drivetrain.setSpeedMultiplier(autoManager.getSpeedMultiplier());
    scheduler.add(new ZeroArm());
  }

  @Override
  public void autonomousInit() {
    scheduler.removeAll();

    drivetrain.changeGyroGains(gyroP, gyroI, gyroD);

    scheduler.add(autoManager.getAutoChooserCommand());
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
