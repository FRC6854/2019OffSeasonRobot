package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.AnalogInput;

public class SwerveWheel implements SwerveConstants {
  private TalonSRX steerMotor;
  private AnalogInput absEnc;

  private VictorSPX driveMotor;

  private final int countsWhenFrwd;
  private final int steeringQuadCountPerRot = 1658;

  public int analogPort;

  public SwerveWheel(int CAN_STEER, int ANALOG_ENC, int CAN_DRIVE) {
      analogPort = ANALOG_ENC;
     
      countsWhenFrwd = zeroOffSet;
      
      steerMotor = new TalonSRX(CAN_STEER);
      absEnc = new AnalogInput(ANALOG_ENC);

      driveMotor = new VictorSPX(CAN_DRIVE);

      steerMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
      steerMotor.setSelectedSensorPosition(getAbsAngleDeg() * steeringQuadCountPerRot / 360); 
      steerMotor.set(ControlMode.Position, 0.0);
  
      steerMotor.selectProfileSlot(0, 0);
      steerMotor.config_kF(0, 0, 10); 
      steerMotor.config_kP(0, 100.0, 10); //10 
      steerMotor.config_kI(0, 2, 10); //0.05
      steerMotor.config_kD(0, 1, 10); //0
      steerMotor.config_IntegralZone(0, 10);

      steerMotor.configClosedloopRamp(0.0, 10);
      steerMotor.configOpenloopRamp(0.0, 10);

      steerMotor.configMotionCruiseVelocity(2000, 10);
      steerMotor.configMotionAcceleration(1000, 10);
   }

  public int getAbsAngleDeg(){
      return (int)(360 * (absEnc.getValue() - countsWhenFrwd) / 4096);   
  }

  private int angleToCounts(double angle){
      return (int)(steeringQuadCountPerRot * angle / 360);
  }

  public void setAngle(double angle){
      steerMotor.set(ControlMode.MotionMagic, angleToCounts(angle));
  }

  public void setSpeed(double percentOut){
      driveMotor.set(ControlMode.PercentOutput, percentOut);
  }

  public void debug(){
      System.out.println("absEncPort: " + analogPort + " " + absEnc.getValue());
      //System.out.println("abs angle: " + getAbsAngleDeg() + " QuadAngle " + steerMotor.getSelectedSensorPosition() * 360 / steeringQuadCountPerRot  + " quad count error: " + steerMotor.getClosedLoopError());
  }

}