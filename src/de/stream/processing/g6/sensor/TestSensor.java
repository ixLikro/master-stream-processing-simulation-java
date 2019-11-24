package de.stream.processing.g6.sensor;

import java.util.Date;

/**
 * A small Sensor implementation that act as timestamp echo
 */
public class TestSensor extends Sensor {
    public TestSensor(int port, String name, int sendInterval) {
        super(port, name, sendInterval);
    }

    @Override
    public void sendValue(Date simTime) {
        sender.sendData(name + " -> " + simTime.toString());
    }
}
