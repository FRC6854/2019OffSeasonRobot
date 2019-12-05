package frc.robot.auto;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.command_groups.drivetrain.auto.*;
import frc.robot.command_groups.testing.TestingDriveAngle;
import frc.robot.commands.drivetrain.*;

public class AutoManager {

    private static AutoManager instance = null;

    private static SendableChooser<Integer> autoChooser = new SendableChooser<Integer>();
    private static SendableChooser<Integer> autoChooserHatch = new SendableChooser<Integer>();

    private static SendableChooser<Integer> slowModeChooser = new SendableChooser<Integer>();
    
    private static String path = "testing";

    private AutoManager () {
        autoChooser.setDefaultOption("90 Hatch", 1);
        autoChooser.addOption("Drive Profile Hatch", 2);
        autoChooser.addOption("45 Hatch", 3);
        autoChooser.addOption("Testing", 4);
        autoChooser.addOption("Profile Follow", 5);

        autoChooserHatch.setDefaultOption("Bottom Stage", 1);
        autoChooserHatch.addOption("Middle Stage", 2);
        autoChooserHatch.addOption("Top Stage", 3);

        slowModeChooser.setDefaultOption("Disabled", 0);
        slowModeChooser.addOption("50% MAX", 1);

        SmartDashboard.putString("Profile Path", path);
    }

    public SendableChooser<Integer> getAutoChooser() {
        return autoChooser;
    }

    public SendableChooser<Integer> getAutoHatch() {
        return autoChooserHatch;
    }

    public SendableChooser<Integer> getSlowModeChooser() {
      return slowModeChooser;
    }

    public Command getAutoChooserCommand() {
        String autoPath = SmartDashboard.getString("Profile Path", path);
        System.out.println(autoPath);

        switch (autoChooser.getSelected()) {
            case 1:
              return (new Drive90DriveHatch(autoChooserHatch.getSelected()));
            case 2:
              return (new DriveProfileHatch());
            case 3:
              return (new Drive45DriveHatch(autoChooserHatch.getSelected()));
            case 4:
              return (new TestingDriveAngle());
            case 5:
              return (new ProfileFollower(path));
        }

        return null;
    }

    public double getSpeedMultiplier() {
        switch(slowModeChooser.getSelected()) {
            case 0:
                return 1.0;
            case 1:
                return 0.5;
        }

        return 1.0;
    }

    public static AutoManager getInstance() {
        if (instance == null) {
            instance = new AutoManager();
        }

        return instance;
    }
}
