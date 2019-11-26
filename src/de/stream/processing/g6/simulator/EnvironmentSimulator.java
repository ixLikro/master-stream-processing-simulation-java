package de.stream.processing.g6.simulator;

import de.stream.processing.g6.data.TemperatureData;

import java.util.Date;

public class EnvironmentSimulator extends Simulator {

    private float outsideTemperature;

    public EnvironmentSimulator(int simulationInterval) {
        super(simulationInterval);
    }

    @Override
    public void simulate(Date simTime) {

        outsideTemperature = TemperatureData.getInstance().getTemperature(simTime);

    }

    public float getOutsideTemperature() {
        return outsideTemperature;
    }
}
