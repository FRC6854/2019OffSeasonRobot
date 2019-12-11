package frc.robot.commands.arm;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.Arm;

import frc.team6854.led.LEDController;
import frc.team6854.led.LEDController.LEDMode;

public class DropHatch extends Command {
  private Arm arm = null;
  private LEDController leds = null;
 
  boolean finished = false;

  int timer = 0;

  // execute() runs 50 times per second or every 20ms. So the command should run a minimum of half a second
  static final int waitForTime = 25;
  static final int velocityThreshold = 2;

  public DropHatch() {
    arm = Arm.getInstance();
    leds = LEDController.getInstance();

    requires(arm);
    setTimeout(1.0);
  }

  @Override
  protected void initialize() {
    arm.dropStage();
  }

  @Override
  protected void execute() {
    timer++;

    finished = (timer > waitForTime && (arm.getArmVelocity() <= velocityThreshold && arm.getArmVelocity() >= -velocityThreshold));
  }

  @Override
  protected boolean isFinished() {
    if (isTimedOut()) {
      leds.setMode(LEDMode.ERROR);
      return true;
    }

    return finished;
  }

  @Override
  protected void end() {
    System.out.println("Done Dropping Hatch");
    arm.driveManual(0);
  }

  @Override
  protected void interrupted() {
    end();
  }
}
