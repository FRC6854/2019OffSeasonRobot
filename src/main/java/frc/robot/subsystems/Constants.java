package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;

public interface Constants {
    /**
	 * --------------------
	 * 	DRIVETRAIN CONSTANTS
	 * --------------------
	 */
	public double dt_kRadius = 3;
	public double robotRadius = 11.25;

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

	public static final int POS_BOTTOM=1377;
	public static final int POS_MIDDLE=4000;
	public static final int POS_TOP=6281;

	public static final int POS_SAFE_BOTTOM = 1024;

	static final double maxHeight = 4096.0;
}
