package frc.team6854;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import frc.robot.RobotMap;
import frc.team6854.BinaryMath;

import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.DriverStation;

public class OI implements RobotMap {
  XboxController driver = new XboxController(CONTROLLER_DRIVER);
  DriverStation ds = DriverStation.getInstance();

  DigitalOutput bit1 = new DigitalOutput(DIO[0]);
  DigitalOutput bit2 = new DigitalOutput(DIO[1]);
  DigitalOutput bit4 = new DigitalOutput(DIO[2]);
  DigitalOutput bit8 = new DigitalOutput(DIO[3]);
  DigitalOutput bit16 = new DigitalOutput(DIO[4]);
  DigitalOutput bit32 = new DigitalOutput(DIO[5]);
  DigitalOutput bit64 = new DigitalOutput(DIO[6]);
  DigitalOutput bit128 = new DigitalOutput(DIO[7]);
  
  DigitalOutput[] binaryOutput = {
    bit1,
    bit2,
    bit4,
    bit8,
    bit16,
    bit32,
    bit64,
    bit128
  };

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

  public void getLedData() {
    for(int i = 0; i < binaryOutput.length; i++) {
      System.out.print(binaryOutput[i].get());
    }
    System.out.println();
  }

  public void ledData(int number) {
    boolean[] binary = BinaryMath.getBinaryform(number);

    for(int i = 0; i < binaryOutput.length; i++) {
      binaryOutput[i].set(binary[i]);
    }
  }
}
