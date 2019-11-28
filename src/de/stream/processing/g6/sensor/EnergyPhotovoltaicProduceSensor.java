package de.stream.processing.g6.sensor;

import de.stream.processing.g6.Simulation;
import de.stream.processing.g6.util.RandomHelper;
import org.json.JSONObject;

import java.util.Date;

public class EnergyPhotovoltaicProduceSensor extends Sensor {

    private JSONObject sensorInfo;

    public EnergyPhotovoltaicProduceSensor(int port, String name, int sendInterval) {
        super(port, name, sendInterval);

        sensorInfo = new JSONObject();
        sensorInfo.put("type", "PM 231 E");
        sensorInfo.put("product type", "Energy Consumption");
    }

    @Override
    public void sendValue(Date simTime) {
        JSONObject value = new JSONObject();

        float energy = Simulation.getInstance().getEnergySimulator().getProduce();

        //may create a outlier
        if(RandomHelper.hitPercentChance(3)){
            energy = energy *(-1);
        }

        value.put("value", energy);
        value.put("measuring Unit", "kw");

        JSONObject ret = new JSONObject();
        ret.put("name", name);
        ret.put("measuring", value);
        ret.put("timestamp", simTime.getTime());
        ret.put("sensor Info", sensorInfo);

        sender.sendData(ret.toString());
    }
}
