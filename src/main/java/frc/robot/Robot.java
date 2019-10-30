package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.command_groups.drivetrain.auto.*;
import frc.robot.commands.arm.*;
import frc.robot.commands.drivetrain.*;
import frc.robot.subsystems.*;
import frc.team6854.OI; 

public class Robot extends TimedRobot implements RobotMap {

  private static KitDrivetrain drivetrain;

  // We need to declare OI as a variable to make sure it has an instance before other code starts to run
  private static OI oi;

  private static Scheduler scheduler;

  private static Arm arm;

  private static SendableChooser<Integer> autoChooser = new SendableChooser<Integer>();
  
  @Override
  public void robotInit() {
    drivetrain = KitDrivetrain.getInstance();
    scheduler = Scheduler.getInstance();
    arm = Arm.getInstance();
    oi = OI.getInstance();

    autoChooser.setDefaultOption("Drive 90 Drive", 1);
    autoChooser.addOption("Drive Around Trailer", 2);
    autoChooser.addOption("Drive 45 Drive", 3);
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
    
    SmartDashboard.putData("Auto Chooser", autoChooser);

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
    }    
  }

  @Override
  public void teleopInit() {
    scheduler.removeAll();
    scheduler.add(new ArcadeDrive());
    scheduler.add(new ZeroArm());
  }
}
