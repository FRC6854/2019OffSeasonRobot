package frc.robot.commands.drivetrain;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

import frc.team6854.Limelight.LightMode;
import frc.team6854.LEDController.LEDMode;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ArcadeDrive extends Command {
  double tX = Robot.limelight.targetX();
  double kP = 0.04;
  double maxCommand = 0.5;

  public ArcadeDrive() {
    super();
    requires(Robot.drivetrain);
  }

  @Override
  protected void initialize() {
  }
  
  @Override
  protected void execute() {
    tX = Robot.limelight.targetX();

    // B button to vision aim using vision
    if (Robot.oi.getDriverAButton() == true) {
      Robot.leds.currentMode = LEDMode.VISION;
      Robot.limelight.setLEDMode(LightMode.ON);

      double steeringAdjust = kP * tX;
      if(steeringAdjust >= maxCommand) {
        steeringAdjust = maxCommand;
      }
      double operatorThrottle = Robot.oi.getDriverLeftStickY();

      Robot.drivetrain.arcadeDrive(operatorThrottle, steeringAdjust);
    } 

    else {
      Robot.leds.currentMode = LEDMode.TELEOP;
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
