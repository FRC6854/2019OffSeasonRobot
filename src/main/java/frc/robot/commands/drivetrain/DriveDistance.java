package frc.robot.commands.drivetrain;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class DriveDistance extends Command {
  
  double meters;

  int timer = 0;

  // execute() runs 50 times per second or every 20ms. So the command should run a minimum of half a second
  int waitForTime = 25;

  public DriveDistance(double meters) {
    super();
    requires(Robot.drivetrain);
    requires(Robot.gyro);
    this.meters = meters;
  }

  @Override
  protected void initialize() {
    Robot.drivetrain.zeroSensor();
    Robot.drivetrain.driveMeters(meters);
  }

  @Override
  protected void execute() {
    timer++;
  }

  @Override
  protected boolean isFinished() {
    return (timer > waitForTime && (Robot.drivetrain.getLeftVelocity() == 0 && Robot.drivetrain.getRightVelocity() == 0));
  }

  @Override
  protected void end() {
    System.out.println("Finished Driving " + meters + "m");
    Robot.drivetrain.arcadeDrive(0, 0);
  }

  @Override
  protected void interrupted() {
    System.out.println("Interrupted Driving " + meters + "m");
    end();
  }
}
