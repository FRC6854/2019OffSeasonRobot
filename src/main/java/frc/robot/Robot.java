package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.auto.AutoManager;
import frc.robot.subsystems.*;
import frc.team6854.OI; 
import frc.robot.commands.drivetrain.ArcadeDrive;
import frc.robot.commands.arm.ZeroArm;

public class Robot extends TimedRobot implements RobotMap {

  private static KitDrivetrain drivetrain;

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
    
    // Create Instance of OI to make sure LEDs work
    OI.getInstance();

    SmartDashboard.putNumber("Gyro P", Constants.pGyro);
    SmartDashboard.putNumber("Gyro I", Constants.iGyro);
    SmartDashboard.putNumber("Gyro D", Constants.dGyro);
  }

  @Override
  public void robotPeriodic() {
    SmartDashboard.putNumber("Gyro Angle", drivetrain.getGyroAngle());

    gyroP = SmartDashboard.getNumber("Gyro P", Constants.pGyro);
    gyroI = SmartDashboard.getNumber("Gyro I", Constants.iGyro);
    gyroD = SmartDashboard.getNumber("Gyro D", Constants.dGyro);

    SmartDashboard.putBoolean("Front Sensor", drivetrain.getFrontSensor());
    SmartDashboard.putNumber("Distance", (int) drivetrain.getDistanceSensor());

    SmartDashboard.putData(drivetrain);
    SmartDashboard.putData(arm);
    
    SmartDashboard.putData("Auto Chooser", autoManager.getAutoChooser());
    SmartDashboard.putData("Auto Rocket Level", autoManager.getAutoHatch());
    SmartDashboard.putData("Scheduler", scheduler);

    scheduler.run();
  }

  @Override
  public void autonomousInit() {
    scheduler.removeAll();

    drivetrain.changeGyroGains(gyroP, gyroI, gyroD);

    scheduler.add(autoManager.getAutoChooerCommand());
  }

  @Override
  public void teleopInit() {
    scheduler.removeAll();
    scheduler.add(new ZeroArm());
  }
}
