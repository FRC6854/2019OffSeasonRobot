package frc.robot.commands.drivetrain;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.KitDrivetrain;
import frc.robot.subsystems.Gyro;

public class DriveDistance extends Command {
  private KitDrivetrain drivetrain = null;
  private Gyro gyro = null;
  
  double meters;

  int timer = 0;

  // execute() runs 50 times per second or every 20ms. So the command should run a minimum of half a second
  static final int waitForTime = 25;

  public DriveDistance(double meters) {
    drivetrain = KitDrivetrain.getInstance();
    gyro = Gyro.getInstance();
    
    requires(drivetrain);
    requires(gyro);

    this.meters = meters;
  }

  @Override
  protected void initialize() {
    drivetrain.zeroSensor();
    drivetrain.driveMeters(meters);
  }

  @Override
  protected void execute() {
    timer++;
  }

  @Override
  protected boolean isFinished() {
    return (timer > waitForTime && (drivetrain.getLeftVelocity() == 0 && drivetrain.getRightVelocity() == 0));
  }

  @Override
  protected void end() {
    System.out.println("Finished Driving " + meters + "m");
    drivetrain.arcadeDrive(0, 0);
  }

  @Override
  protected void interrupted() {
    System.out.println("Interrupted Driving " + meters + "m");
    end();
  }
}
