package frc.robot.shuffleboard.tabs;

//import frc.robot.auto.AutoModeSelector;
import frc.robot.controls.ControlBoard.ScoringLocation;
import frc.robot.shuffleboard.ShuffleboardTabBase;
//import frc.robot.subsystems.  ;
import frc.robot.subsystems.Superstructure;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;

public class OperatorTab extends ShuffleboardTabBase {

   private Superstructure mSuperstructure = Superstructure.getInstance();
    
    private final GenericEntry[] left_entries = new GenericEntry[3];
    private final GenericEntry[] mid_entries = new GenericEntry[3];
    private final GenericEntry[] right_entries = new GenericEntry[3];

    public OperatorTab() {
        super();
        mTab = Shuffleboard.getTab("Operator");
        for (int i = 0; i < 3; i++) {
            left_entries[i] = mTab
                    .add("L" + (i + 1), false)
                    .withSize(1, 1)
                    .withPosition(0, 3 - i)
                    .getEntry();

            mid_entries[i] = mTab
                    .add("M" + (i + 1), false)
                    .withSize(1, 1)
                    .withPosition(1, 3 - i)
                    .getEntry();

            right_entries[i] = mTab
                    .add("R" + (i + 1), false)
                    .withSize(1, 1)
                    .withPosition(2, 3 - i)
                    .getEntry();
        }

       // mTab.add(AutoModeSelector.getModeChooser());
    }

    @Override
    public void createEntries() {
        mTab = Shuffleboard.getTab("Operator");
    }

    @Override
    public void update() {
       ScoringLocation target_location = mSuperstructure.getScoringLocation();
        for (int i = 1; i < 4; i++) {
            left_entries[i - 1].setBoolean(target_location.level == i && target_location.column == 1);
            mid_entries[i - 1].setBoolean(target_location.level == i && target_location.column == 2);
            right_entries[i - 1].setBoolean(target_location.level == i && target_location.column == 3);
        }
    }

}
