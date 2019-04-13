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

}
