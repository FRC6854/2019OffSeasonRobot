package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.drivetrain.ArcadeDrive;
import frc.robot.commands.drivetrain.DriveDistance;
import frc.robot.subsystems.KitDrivetrain;

public class Robot extends TimedRobot implements RobotMap {

  public static OI oi = new OI();

  public static KitDrivetrain drivetrain = new KitDrivetrain(CAN_LEFT_FRONT, CAN_LEFT_BACK, CAN_RIGHT_FRONT, CAN_RIGHT_BACK);

  public static Scheduler scheduler = Scheduler.getInstance();

  @Override
  public void robotInit() {
  }

  @Override
  public void robotPeriodic() {
    SmartDashboard.putNumber("DT L Ticks", drivetrain.getLeftTicks());
    SmartDashboard.putNumber("DT R Ticks", drivetrain.getLeftTicks());
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
    scheduler.add(new ArcadeDrive());
  }

  @Override
  public void autonomousPeriodic() {
  
  }

  @Override
  public void teleopInit() {
    scheduler.removeAll();
    scheduler.add(new ArcadeDrive());
    
  }

  @Override
  public void teleopPeriodic() {
  }

  @Override
  public void testInit() {
    scheduler.removeAll();
    scheduler.add(new DriveDistance(10));
    //drivetrain.driveRotations(10);
  }

  @Override
  public void testPeriodic() {
    if (oi.getDriverAButtonPressed()) {
      drivetrain.zeroSensor();
      drivetrain.driveRotations(10);
    }
  }


}
