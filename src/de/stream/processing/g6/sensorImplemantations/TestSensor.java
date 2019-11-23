package de.stream.processing.g6.sensorImplemantations;

import de.stream.processing.g6.Sensor;

import java.util.Date;

/**
 * A small Sensor implementation that act as timestamp echo
 */
public class TestSensor extends Sensor {
    public TestSensor(int port, String name) {
        super(port, name);
    }

    @Override
    public void sendValue(Date simTime) {
        sender.sendData(name + " -> " + simTime.toString());
    }
}
