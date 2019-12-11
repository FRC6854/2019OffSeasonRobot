package frc.robot.commands.arm;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.Arm;

import frc.team6854.led.LEDController;
import frc.team6854.led.LEDController.LEDMode;

public class ZeroArm extends Command {

  private Arm arm = null;
  private LEDController leds = null;

  public ZeroArm() {
    arm = Arm.getInstance();
    leds = LEDController.getInstance();

    setTimeout(6.0);

    requires(arm);
  }

  @Override
  protected void initialize() {
  }

  @Override
  protected void execute() {
    arm.updateFaults();
    arm.driveManual(-0.1);
  }

  @Override
  protected boolean isFinished() {
    if (isTimedOut()) {
      leds.setMode(LEDMode.ERROR);
      return true;
    }
    
    return arm.getReverseLimitSwitch();
  }

  @Override
  protected void end() {
    arm.zeroSensor();
  }

  @Override
  protected void interrupted() {
  }
}
