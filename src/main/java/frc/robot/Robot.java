package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.command_groups.drivetrain.auto.*;
import frc.robot.command_groups.testing.TestingDriveAngle;
import frc.robot.commands.arm.*;
import frc.robot.commands.drivetrain.*;
import frc.robot.subsystems.*;
import frc.team6854.OI; 

public class Robot extends TimedRobot implements RobotMap {

  private static KitDrivetrain drivetrain;

  private static Scheduler scheduler;

  private static Arm arm;

  private static SendableChooser<Integer> autoChooser = new SendableChooser<Integer>();
  private static SendableChooser<Integer> autoChooserHatch = new SendableChooser<Integer>();

  private double gyroP = 0;
  private double gyroI = 0;
  private double gyroD = 0;
  
  @Override
  public void robotInit() {
    drivetrain = KitDrivetrain.getInstance();
    scheduler = Scheduler.getInstance();
    arm = Arm.getInstance();
    
    // Create Instance of OI to make sure LEDs work
    OI.getInstance();

    SmartDashboard.putNumber("Gyro P", Constants.pGyro);
    SmartDashboard.putNumber("Gyro I", Constants.iGyro);
    SmartDashboard.putNumber("Gyro D", Constants.dGyro);

    autoChooser.setDefaultOption("Drive 90 Drive", 1);
    autoChooser.addOption("Drive Around Trailer", 2);
    autoChooser.addOption("Drive 45 Drive", 3);
    autoChooser.addOption("Drive 90 Drive Hatch", 4);
    autoChooser.addOption("Testing", 5);

    autoChooserHatch.setDefaultOption("Bottom Stage", 1);
    autoChooserHatch.addOption("Middle Stage", 2);
    autoChooserHatch.addOption("Top", 3);
  }

  @Override
  public void robotPeriodic() {
    arm.updateFaults();

    SmartDashboard.putNumber("DT L Ticks", drivetrain.getLeftTicks());
    SmartDashboard.putNumber("DT R Ticks", drivetrain.getRightTicks());

    SmartDashboard.putNumber("Gyro Angle", drivetrain.getGyroAngle());

    SmartDashboard.putNumber("Left Velocity", drivetrain.getLeftVelocity());
    SmartDashboard.putNumber("Right Velocity", drivetrain.getRightVelocity());

    SmartDashboard.putNumber("Left Output", drivetrain.getLeftOutput());
    SmartDashboard.putNumber("Right Output", drivetrain.getRightOutput());

    gyroP = SmartDashboard.getNumber("Gyro P", Constants.pGyro);
    gyroI = SmartDashboard.getNumber("Gyro I", Constants.iGyro);
    gyroD = SmartDashboard.getNumber("Gyro D", Constants.dGyro);

    SmartDashboard.putBoolean("Front Sensor", drivetrain.getFrontSensor());
    SmartDashboard.putNumber("Distance", drivetrain.getDistanceSensor());
    
    SmartDashboard.putData("Auto Chooser", autoChooser);
    SmartDashboard.putData("Auto Rocket Leve", autoChooserHatch);

    scheduler.run();
  }

  @Override
  public void disabledInit() {
    scheduler.removeAll();

    drivetrain.fullStop();
  }

  @Override
  public void autonomousInit() {
    drivetrain.changeGyroGains(gyroP, gyroI, gyroD);

    scheduler.removeAll();

    switch (autoChooser.getSelected()) {
      case 1:
        scheduler.add(new Drive90Drive());
        break;
      case 2:
        scheduler.add(new DriveAroundTrailer());
        break;
      case 3:
        scheduler.add(new Drive45Drive());
        break;
      case 4:
        scheduler.add(new Drive90DriveHatch(autoChooserHatch.getSelected()));
        break;
      case 5:
        scheduler.add(new TestingDriveAngle());
        break;
    }    
  }

  @Override
  public void teleopInit() {
    scheduler.removeAll();
    scheduler.add(new ArcadeDrive());
    scheduler.add(new ZeroArm());
  }
}
