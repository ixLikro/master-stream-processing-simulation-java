package de.stream.processing.g6.simulator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RoomSimulator extends Simulator {

    private boolean windowsAreClosed;

    public RoomSimulator() {
        super(45);

        windowsAreClosed = true;
    }

    @Override
    public void simulate(Date simTime) {

    }
}
