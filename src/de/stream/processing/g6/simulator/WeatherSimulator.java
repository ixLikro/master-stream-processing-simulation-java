package de.stream.processing.g6.simulator;

import de.stream.processing.g6.util.RandomHelper;
import de.stream.processing.g6.util.Weather;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

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

    private Weather getRandomWeather(Date simTime){

        //reduce the chance of bad weather in the summer
        Calendar temp = new GregorianCalendar();
        temp.setTimeInMillis(simTime.getTime());
        int month = temp.get(Calendar.MONTH);

        if(month == Calendar.JUNE || month == Calendar.JULY){
            //summer, 70% chance of good weather
            if(RandomHelper.hitPercentChance(70d)){
                //50:50 clear sky ot slightly cloudy
                return RandomHelper.hitPercentChance(50d) ? Weather.CLEAR_SKY : Weather.SLIGHTLY_CLOUDY;
            }
        }else {
            return Weather.values()[RandomHelper.getInteger(Weather.CLOUDY.ordinal(), Weather.values().length-1)];
        }
        return Weather.values()[RandomHelper.getInteger(0, Weather.values().length-1)];
    }

    @Override
    public void simulate(Date simTime) {
        currentWeather = comingWeather;
        comingWeather = getRandomWeather(simTime);
        //the weather changes between 20 min and 3h
        simulationInterval = RandomHelper.getInteger(20 * 60, 180 *60);
    }

    public Weather getCurrentWeather() {
        return currentWeather;
    }

    public Weather getComingWeather() {
        return comingWeather;
    }
}
