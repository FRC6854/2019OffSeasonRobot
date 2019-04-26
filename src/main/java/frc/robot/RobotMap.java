package frc.robot;

public interface RobotMap {
  //Controller DriverStation Number
  final int CONTROLLER_DRIVER = 0;

  //Drivrtrain Motorcontroller CAN Id's
  final int CAN_FL_STEER = 0;
  final int CAN_FL_DRIVE = 1;
  final int CAN_FR_STEER = 2;
  final int CAN_FR_DRIVE = 3;
  final int CAN_BL_STEER = 4;
  final int CAN_BL_DRIVE = 5;
  final int CAN_BR_STEER = 6;
  final int CAN_BR_DRIVE = 7;

  final int ANALOG_FL_ABSENCODER = 0;
  final int ANALOG_FR_ABSENCODER = 1;
  final int ANALOG_BL_ABSENCODER = 2;
  final int ANALOG_BR_ABSENCODER = 3;
}
