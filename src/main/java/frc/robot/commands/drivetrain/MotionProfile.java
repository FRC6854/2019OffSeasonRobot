/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.drivetrain;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class MotionProfile extends Command {
  public MotionProfile() {
    super();
    requires(Robot.drivetrain);
  }

  @Override
  protected void initialize() {
    
  }

  @Override
  protected void execute() {
    Robot.drivetrain.motionProfile();
  }

  @Override
  protected boolean isFinished() {
    if(Robot.drivetrain.isMotionProfileFinished() == true){
      return true;
    }
    return false;
  }

  @Override
  protected void end() {
    Robot.scheduler.add(new ArcadeDrive());
  }

  @Override
  protected void interrupted() {
    end();
  }
}
