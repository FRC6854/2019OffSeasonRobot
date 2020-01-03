package frc.robot.commands.arm;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Arm;

import viking.led.LEDController;
import viking.led.LEDController.LEDMode;

public class ZeroArm extends CommandBase {

  private Arm arm = null;
  private LEDController leds = null;

  public ZeroArm() {
    arm = Arm.getInstance();
    leds = LEDController.getInstance();

    withTimeout(6.0);

    addRequirements(arm);
  }

  @Override
  public void execute() {
    arm.updateFaults();
    arm.driveManual(-0.1);
  }

  @Override
  public boolean isFinished() {
    return arm.getReverseLimitSwitch();
  }

  @Override
  public void end(boolean interrupted) {
    if (interrupted == true) {
      leds.setMode(LEDMode.ERROR);
    }
    else {
      arm.zeroSensor();
    }
  }
}
