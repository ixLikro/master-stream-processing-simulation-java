package de.stream.processing.g6;

import de.stream.processing.g6.worker.DataSendWorker;

import java.util.Date;

public abstract class Sensor {

    private final int port;
    protected final String name;
    protected final DataSendWorker sender;

    /**
     * get called from the SimWorker.
     * If this method get called the sensor should send the Value of the given SimTime send to the Server
     *
     * @param simTime the simulated time
     */
    public abstract void sendValue(Date simTime);


    public Sensor(int port, String name) {
        this.port = port;
        this.name = name;
        sender = new DataSendWorker(port);
    }
}
