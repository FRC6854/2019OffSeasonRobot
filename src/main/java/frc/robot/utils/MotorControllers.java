package frc.robot.utils;

import com.ctre.phoenix.motorcontrol.Faults;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.kauailabs.navx.frc.AHRS;

import frc.robot.RobotMap;

public class MotorControllers implements RobotMap {
    private static MotorControllers instance = null;

    private TalonSRX leftMaster = null;
    private VictorSPX leftSlave = null;

    private TalonSRX rightMaster = null;
    private VictorSPX rightSlave = null;

    private TalonSRX arm = null;
    private Faults armFaults = null;

    private AHRS gyro = null;

    public MotorControllers() {
        leftMaster = new TalonSRX(CAN_LEFT_FRONT);
        leftSlave = new VictorSPX(CAN_LEFT_BACK);
        rightMaster = new TalonSRX(CAN_RIGHT_FRONT);
        rightSlave = new VictorSPX(CAN_RIGHT_BACK);
        arm = new TalonSRX(CAN_ARM);
        gyro = new AHRS(GRYO_PORT);
        armFaults = new Faults();
    }

    public static MotorControllers getInstance() {
		if(instance == null) {
			instance = new MotorControllers();
        }
            
		return instance;
    }
    
    public TalonSRX getLeftMaster() {
        return leftMaster;
    }

    public VictorSPX getLeftSlave() {
        return leftSlave;
    }

    public TalonSRX getRightMaster() {
        return rightMaster;
    }

    public VictorSPX getRightSlave() {
        return rightSlave;
    }

    public TalonSRX getArm() {
        return arm;
    }

    public Faults getArmFaults() {
        return armFaults;
    }

    public AHRS getGyro() {
        return gyro;
    }
}
