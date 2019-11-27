package de.stream.processing.g6.simulator;

import de.stream.processing.g6.Main;
import de.stream.processing.g6.Simulation;
import de.stream.processing.g6.data.EnergyConsumptionData;
import de.stream.processing.g6.data.PhotovoltaicPowerData;
import de.stream.processing.g6.util.RandomHelper;
import de.stream.processing.g6.util.Weather;

import java.util.Date;

public class EnergySimulator extends Simulator {

    //in kw
    private float consumption;

    //in kw
    private float produce;

    //in kwh
    private float batteryLevel = 0;

    public EnergySimulator(int simulationInterval) {
        //simulate every hour
        super(simulationInterval);
    }


    @Override
    public void simulate(Date simTime) {

        consumption = EnergyConsumptionData.getInstance().getConsumption(simTime);

        produce = PhotovoltaicPowerData.instance.getPhotovoltaicEnergy(simTime);

        Weather currentWeather = Simulation.getInstance().getWeatherSimulator().getCurrentWeather();
        if(currentWeather != Weather.CLEAR_SKY){
            if(currentWeather == Weather.SLIGHTLY_CLOUDY)
                produce -= produce * 0.1;
            if(currentWeather == Weather.CLOUDY)
                produce -= produce * 0.5;
            if(currentWeather == Weather.LOW_PRECIPITATION)
                produce -= produce * 0.6;
            if(currentWeather == Weather.HIGH_PRECIPITATION)
                produce -= produce * 0.8;
        }

        batteryLevel += (produce - consumption) * (1d/60);

        if(batteryLevel > Main.BATTERY_CAPACITY){
            batteryLevel = Main.BATTERY_CAPACITY;
        }
        if(batteryLevel < 0f){
            batteryLevel = 0;
        }
    }

    public float getConsumption() {
        return consumption + RandomHelper.getFloat(-0.01f,0.01f);
    }

    public float getProduce() {
        if (produce < 0.01)
            return produce;
        return produce + RandomHelper.getFloat(-0.01f,0.01f);
    }

    public float getBatteryLevel() {
        return batteryLevel;
    }
}
