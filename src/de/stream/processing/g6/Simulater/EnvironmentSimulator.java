package de.stream.processing.g6.Simulater;

import de.stream.processing.g6.Simulator;

import java.util.Date;

public class EnvironmentSimulator extends Simulator {

    private float outsideTemperature;

    public EnvironmentSimulator(int simulationInterval) {
        super(simulationInterval);
    }

    @Override
    public void simulate(Date simTime) {

    }
}
