package de.stream.processing.g6.simulator;

import de.stream.processing.g6.util.RandomHelper;
import de.stream.processing.g6.util.Weather;

import java.util.Date;

public class WeatherSimulator extends Simulator {

    private Weather currentWeather;
    private Weather comingWeather;

    public WeatherSimulator() {
        super(RandomHelper.getInteger(20 * 60, 180 *60));

        currentWeather = getRandomWeather();
        comingWeather = getRandomWeather();
    }

    private Weather getRandomWeather(){
        return Weather.values()[RandomHelper.getInteger(0, Weather.values().length-1)];
    }

    @Override
    public void simulate(Date simTime) {
        currentWeather = comingWeather;
        comingWeather = getRandomWeather();
        simulationInterval = RandomHelper.getInteger(20 * 60, 180 *60);
    }

    public Weather getCurrentWeather() {
        return currentWeather;
    }

    public Weather getComingWeather() {
        return comingWeather;
    }
}
