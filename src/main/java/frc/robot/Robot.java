package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.arm.OperateArm;
import frc.robot.commands.arm.ZeroArm;
import frc.robot.commands.drivetrain.ArcadeDrive;
import frc.robot.commands.drivetrain.DriveDistance;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.KitDrivetrain;

public class Robot extends TimedRobot implements RobotMap {
  public static OI oi = new OI();

  public static KitDrivetrain drivetrain = new KitDrivetrain(CAN_LEFT_FRONT, CAN_LEFT_BACK, CAN_RIGHT_FRONT, CAN_RIGHT_BACK);

  public static Scheduler scheduler = Scheduler.getInstance();

  public static Arm arm = new Arm(CAN_ARM);

  @Override
  public void robotInit() {
  }

  @Override
  public void robotPeriodic() {
    arm.updateFaults();

    SmartDashboard.putNumber("DT L Ticks", drivetrain.getLeftTicks());
    SmartDashboard.putNumber("DT R Ticks", drivetrain.getRightTicks());
    SmartDashboard.putData(drivetrain);
    
    SmartDashboard.putNumber("Arm Angle", arm.getAngle());
    SmartDashboard.putBoolean("Arm Reverse LS", arm.getReverseLimitSwitch());
    SmartDashboard.putBoolean("Arm Frwd LS", arm.getForwardLimitSwitch());
    SmartDashboard.putNumber("Arm Stage", arm.selectedStage);
    SmartDashboard.putNumber("Cmd Stage", OperateArm.stage);
    SmartDashboard.putBoolean("Manual Control", OperateArm.manualControl);

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
    //scheduler.add(new ArcadeDrive());
    scheduler.add(new DriveDistance(10));
    scheduler.add(new ZeroArm());
    
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
  }

  @Override
  public void testPeriodic() {
    if (oi.getDriverAButtonPressed()) {
      drivetrain.zeroSensor();
      drivetrain.driveRotations(1);
    }
  }
}
