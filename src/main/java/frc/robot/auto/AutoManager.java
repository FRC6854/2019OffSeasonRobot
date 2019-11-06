package frc.robot.auto;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.command_groups.drivetrain.auto.*;
import frc.robot.command_groups.testing.TestingDriveAngle;
import frc.robot.commands.arm.*;
import frc.robot.commands.drivetrain.*;

public class AutoManager {

    private static AutoManager instance = null;

    private static SendableChooser<Integer> autoChooser = new SendableChooser<Integer>();
    private static SendableChooser<Integer> autoChooserHatch = new SendableChooser<Integer>();

    private AutoManager () {
        init();
    }

    private void init() {
        autoChooser.setDefaultOption("Drive 90 Drive", 1);
        autoChooser.addOption("Drive Around Trailer", 2);
        autoChooser.addOption("Drive 45 Drive", 3);
        autoChooser.addOption("Drive 90 Drive Hatch", 4);
        autoChooser.addOption("Testing", 5);
        autoChooser.addOption("Drive Vision", 6);

        autoChooserHatch.setDefaultOption("Bottom Stage", 1);
        autoChooserHatch.addOption("Middle Stage", 2);
        autoChooserHatch.addOption("Top", 3);
    }

    public SendableChooser getAutoChooser() {
        return autoChooser;
    }

    public SendableChooser getAutoHatch() {
        return autoChooserHatch;
    }

    public Command getAutoChooerCommand() {
        switch (autoChooser.getSelected()) {
            case 1:
              return (new Drive90Drive());
            case 2:
              return (new DriveAroundTrailer());
            case 3:
              return (new Drive45Drive());
            case 4:
              return (new Drive90DriveHatch(autoChooserHatch.getSelected()));
            case 5:
              return (new TestingDriveAngle());
            case 6:
              return (new DriveVisionTarget(0.0));
        }

        return null;
    }

    public static AutoManager getInstance() {
        if (instance == null) {
            instance = new AutoManager();
        }

        return instance;
    }
}
