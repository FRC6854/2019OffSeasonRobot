package frc.robot.commands.drivetrain;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.Limelight.LightMode;

public class ArcadeDrive extends Command {
  public ArcadeDrive() {
    super();
    requires(Robot.drivetrain);
  }

  @Override
  protected void initialize() {
  }

  // Note: Based off chapter 8 of limelight docs
  
  @Override
  protected void execute() {
    
    double tX = Robot.limelight.targetX();
    double kP = 0.01;

    // B button to vision aim using vision
    if (Robot.oi.getDriverBButton()) {
      Robot.limelight.setLEDMode(LightMode.ON);

      double steeringAdjust = kP * tX;
      double operatorThrottle = Robot.oi.getDriverLeftStickY();

      Robot.drivetrain.tankDrive(operatorThrottle += steeringAdjust, operatorThrottle -= steeringAdjust);
    } else {
      Robot.limelight.setLEDMode(LightMode.OFF);

      Robot.drivetrain.arcadeDrive(Robot.oi.getDriverLeftStickY(), Robot.oi.getDriverRightStickX());
    }
  }

  @Override
  protected boolean isFinished() {
    return false;
  }

  @Override
  protected void end() {
  }

  @Override
  protected void interrupted() {
  }
}
