package frc.robot.commands.drivetrain;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.Gyro;
import frc.robot.subsystems.KitDrivetrain;
import frc.team6854.LEDController;
import frc.team6854.LEDController.LEDMode;

public class DriveAngle extends Command {
  private KitDrivetrain drivetrain = null;
  private Gyro gyro = null;
  private LEDController leds = null;

  double angle = 0;
  double currentAngle = 0;

  double speed = 0;
  double maxSpeed = 0.5;

  double kP = 0.0425;
  double timer = 0;

  double error = 0;
  double toleranceDegrees = 3;

  public DriveAngle(double angle) {
    drivetrain = KitDrivetrain.getInstance();
    gyro = Gyro.getInstance();
    leds = LEDController.getInstance();

    requires(drivetrain);
    requires(gyro);

    this.angle = angle;
  }

  @Override
  protected void initialize() {
  }

  @Override
  protected void execute() {
    leds.setMode(LEDMode.AUTO);
    currentAngle = gyro.getAngle();
    error = angle - currentAngle;
    speed = kP * error;

    if(speed >= maxSpeed) {
      speed = maxSpeed;
    }
    if(speed <= -maxSpeed) {
      speed = -maxSpeed;
    }

    drivetrain.arcadeDrive(0, speed);

    if(currentAngle <= (angle + toleranceDegrees) && currentAngle >= (angle - toleranceDegrees)) {
      timer++;
    }
  }

  @Override
  protected boolean isFinished() {
    return (timer >= 25 && (currentAngle <= (angle + toleranceDegrees) && currentAngle >= (angle - toleranceDegrees)));
  }

  @Override
  protected void end() {
    System.out.println("Finished setting angle to: " + angle);
    drivetrain.arcadeDrive(0, 0);
  }
  
  @Override
  protected void interrupted() {
    System.out.println("Interrupted setting angle to: " + angle);
    end();
  }
}
