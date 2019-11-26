package de.stream.processing.g6.simulator;

import de.stream.processing.g6.Simulation;
import de.stream.processing.g6.data.TemperatureData;
import de.stream.processing.g6.util.RandomHelper;
import de.stream.processing.g6.util.Weather;

import java.util.Date;

public class EnvironmentSimulator extends Simulator {

    private float outsideTemperature;

    public EnvironmentSimulator(int simulationInterval) {
        super(simulationInterval);
    }

    @Override
    public void simulate(Date simTime) {

        //get outside temperature, if its hot and raining reduce temperature
        outsideTemperature = TemperatureData.getInstance().getTemperature(simTime);
        if(outsideTemperature > 25f && Simulation.getInstance().getWeatherSimulator().getCurrentWeather() == Weather.LOW_PRECIPITATION){
            outsideTemperature -= RandomHelper.getFloat(-2,-1);
        }
        if(outsideTemperature > 25f && Simulation.getInstance().getWeatherSimulator().getCurrentWeather() == Weather.HIGH_PRECIPITATION){
            outsideTemperature -= RandomHelper.getFloat(-4,-1.5f);
        }

    }

    public float getOutsideTemperature() {
        return outsideTemperature;
    }
}
