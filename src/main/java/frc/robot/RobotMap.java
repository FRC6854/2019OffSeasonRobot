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

  final int[] DIO = {
    2,
    3,
    4,
    5,
    6,
    7,
    8
  };
}
