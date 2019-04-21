package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import frc.robot.subsystems.SwerveWheel;
import frc.robot.subsystems.SwerveWheelController;

public class Robot extends TimedRobot implements RobotMap {

  public static OI oi = new OI();

  public SwerveWheel frontLeft = new SwerveWheel(CAN_FL_STEER, ANALOG_FL_ABSENCODER, CAN_FL_DRIVE);
  public SwerveWheel frontRight = new SwerveWheel(CAN_FR_STEER, ANALOG_FR_ABSENCODER, CAN_FR_DRIVE);
  public SwerveWheel backLeft = new SwerveWheel(CAN_BL_STEER, ANALOG_BL_ABSENCODER, CAN_BL_DRIVE);
  public SwerveWheel backRight = new SwerveWheel(CAN_BR_STEER, ANALOG_BR_ABSENCODER, CAN_BR_DRIVE);

  //HELP WHY WONT THIS WORK
  //public SwerveWheelController = new SwerveWheelController(frontLeft, frontRight, backLeft, backRight);

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
  }

  @Override
  public void autonomousPeriodic() {
  }

  @Override
  public void teleopInit() {
    scheduler.removeAll();
  }

  @Override
  public void teleopPeriodic() {
  }

  @Override
  public void testInit() {
  }

}
