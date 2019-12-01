package de.stream.processing.g6.sensor;

import de.stream.processing.g6.Main;
import de.stream.processing.g6.Simulation;
import de.stream.processing.g6.util.RandomHelper;
import org.json.JSONObject;

import java.util.Date;

public class BatterySensor extends Sensor {

    private JSONObject sensorInfo;
    private JSONObject batteryInfo;

    public BatterySensor(int port, String name, int sendInterval) {
        super(port, name, sendInterval);

        sensorInfo = new JSONObject();
        sensorInfo.put("type", "CELF 461 v3");

        batteryInfo = new JSONObject();
        batteryInfo.put("Usable capacity", Main.BATTERY_CAPACITY);
        batteryInfo.put("depth of discharge", "100 %");
        batteryInfo.put("effectiveness", "90 %");
        batteryInfo.put("ambient temperature", "-20 C uo 50 C");
        batteryInfo.put("weight", "114 kg");
    }

    @Override
    public void sendValue(Date simTime) {
        JSONObject value = new JSONObject();

        float energy = Simulation.getInstance().getEnergySimulator().getBatteryLevel();

        value.put("value", energy);
        value.put("measuring Unit", "kWh");

        JSONObject ret = new JSONObject();
        ret.put("name", name);
        ret.put("measuring", value);
        ret.put("timestamp", simTime.getTime());
        ret.put("battery Info", batteryInfo);
        ret.put("sensor Info", sensorInfo);

        sender.sendData(ret.toString());
    }
}
