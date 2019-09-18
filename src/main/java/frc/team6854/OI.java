package frc.team6854;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import frc.robot.RobotMap;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.DriverStation;

public class OI implements RobotMap {
  XboxController driver = new XboxController(CONTROLLER_DRIVER);
  DriverStation ds = DriverStation.getInstance();
  SerialPort arduino;

  public OI() {
    arduino = new SerialPort(9600, SerialPort.Port.kUSB);
  }

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

  public boolean getDriverLBumperPressed(){
    return driver.getRawButtonPressed(5);
  }

  public boolean getDriverLBumper(){
    return driver.getRawButton(5);
  }

  public boolean getDriverRBumperPressed(){
    return driver.getRawButtonPressed(6);
  }

  public boolean getDriverRBumper(){
    return driver.getRawButton(6);
  }

  public boolean getDriverAButtonPressed() {
    return driver.getAButtonPressed();
  }

  public boolean getDriverAButton() {
    return driver.getAButton();
  }

  public boolean getDriverBButtonPressed() {
    return driver.getBButtonPressed();
  }

  public boolean getDriverBButton() {
    return driver.getBButton();
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

  public boolean getDriverStartButton(){
    return driver.getStartButton();
  }

  public Alliance getAlliance() {
    return ds.getAlliance();
  }

  public void ledDataSerialPort(int number) {
    // Read the current line of text in the Serial Channel
    System.out.println(arduino.readString());

    // Write the number to the Serial Channel
    arduino.writeString(Integer.toString(number));

    // Since the output buffer is 8 bytes and we usually only print 2 bytes, we must flush the buffer to send the line
    // The limitation of this is that we can only send up to 99,999,999
    arduino.flush();
  }
}
