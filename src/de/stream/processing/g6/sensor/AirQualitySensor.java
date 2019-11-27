package de.stream.processing.g6.sensor;

import de.stream.processing.g6.Simulation;
import de.stream.processing.g6.simulator.RoomSimulator;
import de.stream.processing.g6.util.RandomHelper;
import org.json.JSONObject;

import java.util.Date;

public class AirQualitySensor extends Sensor {

    private RoomSimulator roomSimulator;
    private JSONObject sensorInfo;

    public AirQualitySensor(int port, String name, RoomSimulator roomSimulator, int simulationInterval) {
        super(port, name, simulationInterval);

        this.roomSimulator = roomSimulator;

        JSONObject accuracy = new JSONObject();
        accuracy.put("from", 350);
        accuracy.put("upTo", 5000);
        accuracy.put("accuracy Unit", "ppm");

        sensorInfo = new JSONObject();
        sensorInfo.put("type", "BME680 Breakout");
        sensorInfo.put("Measuring range", accuracy);
        sensorInfo.put("product type", "Co2");
    }

    @Override
    public void sendValue(Date simTime) {
        JSONObject value = new JSONObject();

        double ppm = roomSimulator.getPpm();

        //may create a outlier
        if(RandomHelper.hitPercentChance(5)){
           ppm = ppm *(-1);
        }

        value.put("value", ppm + RandomHelper.getDouble(-5,5));
        value.put("measuring Unit", "ppm");

        JSONObject ret = new JSONObject();
        ret.put("name", name);
        ret.put("measuring", value);
        ret.put("timestamp", simTime.getTime());
        ret.put("sensor Info", sensorInfo);

        sender.sendData(ret.toString());
    }
}
