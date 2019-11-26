package de.stream.processing.g6.sensor;

import de.stream.processing.g6.Simulation;
import de.stream.processing.g6.util.RandomHelper;
import de.stream.processing.g6.util.Weather;
import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Barometer extends Sensor {

    private JSONObject sensorInfo;
    private Map<Weather, Float> pascalToWeather;

    public Barometer(int port, String name, int sendInterval) {
        super(port, name, sendInterval);

        pascalToWeather = new HashMap<>();
        pascalToWeather.put(Weather.CLEAR_SKY, 1038f);
        pascalToWeather.put(Weather.SLIGHTLY_CLOUDY, 1025f);
        pascalToWeather.put(Weather.CLOUDY, 1008f);
        pascalToWeather.put(Weather.LOW_PRECIPITATION, 990f);
        pascalToWeather.put(Weather.HIGH_PRECIPITATION, 980f);

        JSONObject accuracy = new JSONObject();
        accuracy.put("from", 800);
        accuracy.put("upTo", 1200);
        accuracy.put("accuracy Unit", "hPa");

        sensorInfo = new JSONObject();
        sensorInfo.put("type", "BMP085");
        sensorInfo.put("Measuring range", accuracy);
        sensorInfo.put("accuracy class", "SF-3");
        sensorInfo.put("product type", "Mercury barometer");
    }

    @Override
    public void sendValue(Date simTime) {
        JSONObject value = new JSONObject();

        Weather current = Simulation.getInstance().getWeatherSimulator().getCurrentWeather();
        Weather coming = Simulation.getInstance().getWeatherSimulator().getComingWeather();

        float pressure;
        if(current.ordinal() - coming.ordinal() > 0){
            //weather gets better
            pressure = RandomHelper.getFloat(pascalToWeather.get(coming) - 1f, pascalToWeather.get(coming) + 4f);
        }else {
            if(current.equals(coming)){
                //weather keep the same
                pressure = RandomHelper.getFloat(pascalToWeather.get(current) - 3f, pascalToWeather.get(current) + 3f);
            }else {
             //weather gets worse
                pressure = RandomHelper.getFloat(pascalToWeather.get(coming) - 4f, pascalToWeather.get(coming) + 1f);
            }
        }

        //may create a outlier
        if(RandomHelper.hitPercentChance(10)){
            pressure = pressure + RandomHelper.getFloat(300f, 500f);
        }

        value.put("value", pressure);
        value.put("measuring Unit", "hPa");

        JSONObject ret = new JSONObject();
        ret.put("name", name);
        ret.put("measuring", value);
        ret.put("timestamp", simTime.getTime());
        ret.put("sensor Info", sensorInfo);

        sender.sendData(ret.toString());
    }
}
