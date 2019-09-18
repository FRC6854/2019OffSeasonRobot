package frc.robot;

public interface RobotMap {
  //Controller DriverStation Number
  final int CONTROLLER_DRIVER = 0;

  //Drivetrain Motorcontroller CAN Id's
  final int CAN_LEFT_FRONT = 2;
  final int CAN_LEFT_BACK = 1;
  final int CAN_RIGHT_FRONT = 0;
  final int CAN_RIGHT_BACK = 3;

  // Arm TalonSRX
  final int CAN_ARM = 4;

  final int RED_TELEOP = 30;
  final int RED_DEFAULT = 31;
  final int RED_AUTO = 32;
  final int RED_VISION = 33;

  final int BLUE_TELEOP = 40;
  final int BLUE_DEFAULT = 41;
  final int BLUE_AUTO = 42;
  final int BLUE_VISION = 43;
}
