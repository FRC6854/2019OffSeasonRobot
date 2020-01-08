package frc.robot;

import edu.wpi.first.wpilibj.SPI.Port;

public interface RobotMap {
  //Controller DriverStation Number
  final int CONTROLLER_DRIVER = 0;

  // Gyro Port on RoboRIO
  final Port GRYO_PORT = Port.kMXP;

  // Ultrasonic Sensor
  final int ANALOG_ULTRASONIC = 1;

  // Distance Sensor
  final int DIGITAL_DISTANCE = 1;

  //Drivetrain Motorcontroller CAN Id's
  final int CAN_LEFT_FRONT = 1;
  final int CAN_LEFT_BACK = 2;
  final int CAN_RIGHT_FRONT = 3;
  final int CAN_RIGHT_BACK = 0;

  // Arm TalonSRX
  final int CAN_ARM = 4;
}
