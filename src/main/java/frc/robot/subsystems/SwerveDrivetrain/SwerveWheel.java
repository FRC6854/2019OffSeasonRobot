package frc.robot.subsystems.SwerveDrivetrain;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.AnalogInput;

public class SwerveWheel implements SwerveConstants {
    private TalonSRX steerMotor;
    private AnalogInput absEnc;

    private VictorSPX driveMotor;

    private final int ticksWhenFrwd;
    private final int steeringQuadTicksPerRot = 1658;

    public int analogPort;

    public SwerveWheel(int CAN_STEER, int ANALOG_ENC, int CAN_DRIVE) {
        analogPort = ANALOG_ENC;

        ticksWhenFrwd = zeroOffSet;

        steerMotor = new TalonSRX(CAN_STEER);
        absEnc = new AnalogInput(ANALOG_ENC);

        driveMotor = new VictorSPX(CAN_DRIVE);

        steerMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
        steerMotor.setSelectedSensorPosition(getAbsAngleDeg() * steeringQuadTicksPerRot / 360);
        steerMotor.set(ControlMode.Position, 0.0);

        steerMotor.selectProfileSlot(slotIDX, pidIDX);
        steerMotor.config_kF(slotIDX, kF, kTimeOutMS);
        steerMotor.config_kP(slotIDX, kP, kTimeOutMS);
        steerMotor.config_kI(slotIDX, kI, kTimeOutMS);
        steerMotor.config_kD(slotIDX, kD, kTimeOutMS);
        steerMotor.config_IntegralZone(0, IZone);

        steerMotor.configClosedloopRamp(closedLoopRamp, 10);

        steerMotor.configMotionCruiseVelocity(cruiseVelocity, 10);
        steerMotor.configMotionAcceleration(acceleration, 10);
    }

    public int getAbsAngleDeg() {
        return (int) (360 * (absEnc.getValue() - ticksWhenFrwd) / 4096);
    }

    private int angleToTicks(double angle) {
        return (int) (steeringQuadTicksPerRot * angle / 360);
    }

    public void setAngle(double angle) {
        steerMotor.set(ControlMode.MotionMagic, angleToTicks(angle));
    }

    public void setSpeed(double percentOut) {
        driveMotor.set(ControlMode.PercentOutput, percentOut);
    }

    public void fullStop() {
        driveMotor.set(ControlMode.Disabled, 0);
        steerMotor.set(ControlMode.Disabled, 0);
    }

    public void calibrateAbsEnc() {
        System.out.println(absEnc.getValue());
    }

    public void debug() {
        System.out.println("abs angle: " + getAbsAngleDeg() + " QuadAngle "
                + steerMotor.getSelectedSensorPosition() * 360 / steeringQuadTicksPerRot + " quad count error: "
                + steerMotor.getClosedLoopError());
    }

}