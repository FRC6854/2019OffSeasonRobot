package frc.robot;

import edu.wpi.first.wpilibj.XboxController;

public class OI implements RobotMap {
  XboxController driver = new XboxController(CONTROLLER_DRIVER);
  
  public double getDriverLeftStickY() {
    return driver.getRawAxis(1)*-1;
  }

  public double getDriverLeftStickX() {
    return driver.getRawAxis(0);
  }

  public double getDriverRightStickX() {
    return driver.getRawAxis(4);
  }

  public double getDriverRightStickY() {
    return driver.getRawAxis(5);
  }

  public double getDriverLTrigger() {
    return driver.getRawAxis(2);
  }

  public double getDriverRTrigger() {
    return driver.getRawAxis(3);
  }

  public boolean getDriverAButtonPressed() {
    return driver.getAButtonPressed();
  }

  public boolean getDriverBButtonPressed() {
    return driver.getBButtonPressed();
  }

  public boolean getDriverXButtonPressed() {
    return driver.getXButtonPressed();
  }

  public boolean getDriverYButtonPressed() {
    return driver.getYButtonPressed();
  }
 
  public boolean getDriverStartButtonPressed() {
    return driver.getStartButtonPressed();
  }
}
