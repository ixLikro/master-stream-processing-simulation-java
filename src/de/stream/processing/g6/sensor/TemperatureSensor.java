package de.stream.processing.g6.sensor;

import de.stream.processing.g6.Simulation;
import de.stream.processing.g6.util.RandomHelper;
import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class TemperatureSensor extends Sensor {

    private JSONObject sensorInfo;

    public TemperatureSensor(int port, String name, int sendInterval) {
        super(port, name, sendInterval);

        JSONObject accuracy = new JSONObject();
        accuracy.put("from", -50);
        accuracy.put("upTo", 150);
        accuracy.put("accuracy Unit", "C");

        sensorInfo = new JSONObject();
        sensorInfo.put("type", "TO92 PT 1000 CL. B");
        sensorInfo.put("design", "PT1000");
        sensorInfo.put("Measuring range Temperature", accuracy);
        sensorInfo.put("accuracy class", "TO-92");
        sensorInfo.put("temperature coefficient", 2850);
        sensorInfo.put("product type", "platinum temperature sensor");
    }

    @Override
    public void sendValue(Date simTime) {
        JSONObject value = new JSONObject();

        float tempValue = Simulation.getInstance().getEnvironmentSimulator().getOutsideTemperature();

        //may create a outlier
        if(RandomHelper.hitPercentChance(7)){
            tempValue = RandomHelper.hitPercentChance(50) ? (tempValue + RandomHelper.getFloat(205f, 250f)) : (tempValue - RandomHelper.getFloat(-150f,-100f));
        }

        value.put("value", tempValue + 273.15);
        value.put("measuring Unit", "K");

        JSONObject ret = new JSONObject();
        ret.put("name", name);
        ret.put("measuring", value);
        ret.put("timestamp", simTime.getTime());
        ret.put("sensor Info", sensorInfo);

        sender.sendData(ret.toString());
    }
}
