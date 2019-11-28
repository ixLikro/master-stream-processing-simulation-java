package de.stream.processing.g6.sensor;

import org.json.JSONObject;

import java.util.Date;

public class SimTimeSensor extends Sensor {

    public SimTimeSensor(int port, String name) {
        super(port, name, 1);
    }

    @Override
    public void sendValue(Date simTime) {
        JSONObject ret = new JSONObject();
        ret.put("simTime", simTime.getTime());
        ret.put("name", name);
        sender.sendData(ret.toString());
    }
}
