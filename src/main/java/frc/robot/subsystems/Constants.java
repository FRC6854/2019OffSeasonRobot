package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;

public interface Constants {
    /**
	 * --------------------
	 * 	DRIVETRAIN CONSTANTS
	 * --------------------
	 */
	public final double dt_kRadius = 3;
	public final double robotRadius = 11.25;

	public final int dt_kSlotIdx = 0;

	public final int dt_kPIDLoopIdx = 0;

	public final int dt_kTimeoutMs = 0;

	public final FeedbackDevice dt_kFeedbackDevice = FeedbackDevice.CTRE_MagEncoder_Relative;

    public final double dt_kP = 1.0;
    public final double dt_kI = 0.0; 
    public final double dt_kD = 0.0;
    public final double dt_kF = 0.5;

    public final double dt_kDefaultDeadband = 0.02;
    public final double dt_kDefaultMaxOutput = 1.0;

	public final double dt_rightSideInvertMultiplier = -1.0;
	
	public final double pDrive = 0.0013;
	public final double iDrive = 0.001;
	public final double dDrive = 0.0;

	public final double pDriveTarget = 0.0013;
	public final double iDriveTarget = 0.001;
	public final double dDriveTarget = 0.0;

	public final double pGyro = 0.023;
	public final double iGyro = 0.0;
	public final double dGyro = 0.1;

	//Drive Speed Scalar
	public final double jukeSpeedScale = 0.85;
	public final double slowSpeedScale = 0.5;
	public final double speedScale = 1;

	public final double minSpeedScale = 0.015;

	// Distance Sensor
	public final double analogVoltModifier = 100;

	/**
	 * --------------------
	 * 	ARM CONSTANTS
	 * --------------------
	 */
    static final int arm_kSlotIdx = 0;

	static final int arm_kPIDLoopIdx = 0;

	static final int arm_kTimeoutMs = 0;

	static final FeedbackDevice arm_kFeedbackDevice = FeedbackDevice.CTRE_MagEncoder_Relative;
	final int arm_encoderTicksPerRev = 4096;
	
    public final double arm_kP = 1.0;
    public final double arm_kI = 0.0; 
    public final double arm_kD = 0.0;
	public final double arm_kF = 0.2;
}
