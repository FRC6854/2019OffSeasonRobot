package frc.robot.commands.arm;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Arm;

public class DropHatch extends CommandBase {
  private Arm arm = null;
  int timer = 0;

  // execute() runs 50 times per second or every 20ms. So the command should run a minimum of half a second
  static final int waitForTime = 25;
  static final int velocityThreshold = 2;

  public DropHatch() {
    arm = Arm.getInstance();

    addRequirements(arm);
    
    withTimeout(1.0);
  }

  @Override
  public void initialize() {
    arm.dropStage();
  }

  @Override
  public void execute() {
    timer++;
  }

  @Override
  public boolean isFinished() {
    return (timer > waitForTime && (arm.getArmVelocity() <= velocityThreshold && arm.getArmVelocity() >= -velocityThreshold));
  }

  @Override
  public void end(boolean interrupted) {
    System.out.println("Done Dropping Hatch");
    arm.driveManual(0);
  }
}
