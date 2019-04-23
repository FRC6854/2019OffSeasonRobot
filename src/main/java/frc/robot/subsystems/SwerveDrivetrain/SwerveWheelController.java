package frc.robot.subsystems.SwerveDrivetrain;

import edu.wpi.first.wpilibj.command.Subsystem;

public class SwerveWheelController extends Subsystem {

    public SwerveWheel frontLeft;
    public SwerveWheel frontRight;
    public SwerveWheel backLeft;
    public SwerveWheel backRight;

    public SwerveWheelController(SwerveWheel frontLeft, SwerveWheel frontRight, SwerveWheel backLeft,
            SwerveWheel backRight) {
        frontLeft = this.frontLeft;
        frontRight = this.frontRight;
        backLeft = this.backLeft;
        backRight = this.backRight;
    }

    public void swerveDrive(double driveSpeed, double rotation, double swerveX, double swerveY) {
        double speed = driveSpeed * -1;
        double rotationAngle = (Math.pow((rotation / 2.5), 3) * 15.225 * 90);// Create a dead zone and a curve to go
                                                                             // from 0 to +- 90deg
        frontRight.setAngle(swerveAngle(swerveX, swerveY) - rotationAngle);
        frontLeft.setAngle(swerveAngle(swerveX, swerveY) - rotationAngle);
        backRight.setAngle(swerveAngle(swerveX, swerveY) + rotationAngle);
        backLeft.setAngle(swerveAngle(swerveX, swerveY) + rotationAngle);

        System.out.println(swerveAngle(swerveX, swerveY) + " " + rotationAngle);

        frontRight.setSpeed(speed);
        frontLeft.setSpeed(speed * -1);
        backLeft.setSpeed(speed * -1);
        backRight.setSpeed(speed);
    }

    public void tankDrive(double leftSpeed, double rightSpeed) {
        frontRight.setAngle(0);
        frontLeft.setAngle(0);
        backLeft.setAngle(0);
        backRight.setAngle(0);

        frontLeft.setSpeed(leftSpeed);
        backLeft.setSpeed(leftSpeed);
        frontRight.setSpeed(rightSpeed);
        backRight.setSpeed(rightSpeed);
    }

    // We should remove this before we release the package since it is
    // Part of WPILib
    public void arcadeDrive(double xSpeed, double zRotation) {
        frontRight.setAngle(0);
        frontLeft.setAngle(0);
        backLeft.setAngle(0);
        backRight.setAngle(0);       
        
        xSpeed = applyDeadband(xSpeed, 0.02);
        zRotation = applyDeadband(zRotation, 0.02);

        double leftMotorOutput;
        double rightMotorOutput;
    
        double maxInput = Math.copySign(Math.max(Math.abs(xSpeed), Math.abs(zRotation)), xSpeed);
    
        if (xSpeed >= 0.0) {
          // First quadrant, else second quadrant
          if (zRotation >= 0.0) {
            leftMotorOutput = maxInput;
            rightMotorOutput = xSpeed - zRotation;
          } else {
            leftMotorOutput = xSpeed + zRotation;
            rightMotorOutput = maxInput;
          }
        } else {
          // Third quadrant, else fourth quadrant
          if (zRotation >= 0.0) {
            leftMotorOutput = xSpeed + zRotation;
            rightMotorOutput = maxInput;
          } else {
            leftMotorOutput = maxInput;
            rightMotorOutput = xSpeed - zRotation;
          }
        }

        frontLeft.setSpeed(leftMotorOutput * 1.0);
        backLeft.setSpeed(leftMotorOutput * 1.0);
        frontRight.setSpeed(rightMotorOutput * 1.0 * -1.0);
        frontLeft.setSpeed(rightMotorOutput * 1.0 * -1.0);
    }

    public void fullStop() {
        frontRight.fullStop();
        frontLeft.fullStop();
        backRight.fullStop();
        backLeft.fullStop();
    }

    // Remove this too...
    private double applyDeadband(double value, double deadband) {
        if (Math.abs(value) > deadband) {
            if (value > 0.0) {
                return (value - deadband) / (1.0 - deadband);
            } else {
                return (value + deadband) / (1.0 - deadband);
            }
        } else {
            return 0.0;
        }
    }

    private double swerveAngle(double swerveX, double swerveY) {
        double magnitude = Math.sqrt((Math.pow(swerveX, 2)) + (Math.pow(swerveY, 2)));
        double angle = 0.0;

        if (magnitude > 0.2) { // Dead band - to prevent wheels from turning when joystick is at idle
            if (swerveX != 0) { // If statement to prevent division by 0
                if (swerveX > 0) { // Check if joystick in quadrant I or IV
                    angle = 90 + Math.toDegrees(Math.atan(swerveY / swerveX));
                } else {
                    if (swerveY > 0) { // quadrant II or top left
                        angle = -1 * (90 - Math.toDegrees(Math.atan(swerveY / swerveX)));
                    } else { // Quadrant III bottom left
                        angle = -1 * (90 - Math.toDegrees(Math.atan(swerveY / swerveX)));
                    }
                }
            } else {
                angle = 0;
            }
        } else {
            angle = 0;
        }
        return angle;
    }

    @Override
    public void initDefaultCommand() {
    }
}
