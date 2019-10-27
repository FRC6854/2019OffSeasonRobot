package frc.team6854;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import frc.robot.RobotMap;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.DriverStation;

public class OI implements RobotMap {
  private static OI instance = null;

  private XboxController driver = new XboxController(CONTROLLER_DRIVER);
  private DriverStation ds;
  private SerialPort arduino;

  private String lastCommandArduino = null;
  private boolean connected = false;

  public OI() {
    ds = DriverStation.getInstance();
    
    try {
      // Init the SerialPort on baud 9600
      arduino = new SerialPort(9600, SerialPort.Port.kUSB1);

      // Whenever the readString function recieves a \n it will return
      // less bytes than it requested from the arduino
      arduino.enableTermination();

      // Will wait a max of half a second
      arduino.setTimeout(0.5);

      // Set connected to be false
      connected = true;
    }
    catch (Exception e) {
      System.out.print("Failed to connect to Arduino: ");
      System.out.println(e.toString());
    }
  }

  public double getDriverLeftStickY() {
    return driver.getRawAxis(1) * -1;
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
    // Set a string variable to the number
    String dataWrite = Integer.toString(number);

    if (lastCommandArduino != dataWrite && connected == true) {
      // Set the last command to the command about to be sent
      lastCommandArduino = dataWrite;

      // Write the number to the Serial Channel
      arduino.writeString(dataWrite);

      // Since the output buffer is 8 bytes and we usually only print 2 bytes, we must flush the buffer to send the line
      // The limitation of this is that we can only send up to 99,999,999
      arduino.flush();

      // Read the current line of text in the Serial Channel
      String dataReceived = arduino.readString();

      // Print out the data received
      System.out.println("Arduino Sent: " + dataReceived);
    }
  }

  public static OI getInstance () {
    if (instance == null)
      instance = new OI();
    return instance;
  }
}
