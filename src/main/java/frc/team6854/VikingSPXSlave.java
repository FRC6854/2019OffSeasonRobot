package frc.team6854;

import com.ctre.phoenix.motorcontrol.can.VictorSPX;

public class VikingSPXSlave {

    private VictorSPX motor;

    public VikingSPXSlave(int id, VikingSRX master, boolean inverted) {
        motor = new VictorSPX(id);

        motor.follow(master.getTalonSRX());
        motor.setInverted(inverted);
    }

    public VictorSPX getVictorSPX() {
        return motor;
    }
}
