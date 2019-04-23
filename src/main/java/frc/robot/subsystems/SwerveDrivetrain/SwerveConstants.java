package frc.robot.subsystems.SwerveDrivetrain;

public interface SwerveConstants {
    final int zeroOffSet = 1000;

    final int slotIDX = 0;
    final int pidIDX = 0;

    final int kTimeOutMS = 10;

    final int kP = 100;
    final int kI = 2;
    final int kD = 1;
    final int kF = 10;
    final int IZone = 10;

    final double closedLoopRamp = 0.0;

    final int cruiseVelocity = 2000;
    final int acceleration = 1000;
}
