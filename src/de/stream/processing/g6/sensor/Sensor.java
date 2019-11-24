package de.stream.processing.g6.sensor;

import de.stream.processing.g6.worker.DataSendWorker;

import java.util.Date;

public abstract class Sensor {

    private final int port;
    protected final String name;
    protected final DataSendWorker sender;
    /**
     * Describe how often the sensor Data should be send.
     * 1 means every simulated Second
     * 60 means every simulated Minute
     */
    protected int sendInterval;

    /**
     * get called from the SimWorker.
     * If this method get called the sensor should send the Value of the given SimTime send to the Server.
     * Data can be send by calling sender.sendData(String)
     *
     * @param simTime the simulated time
     */
    public abstract void sendValue(Date simTime);


    public Sensor(int port, String name) {
        this.port = port;
        this.name = name;
        sendInterval = 10;
        sender = new DataSendWorker(port);
    }

    public Sensor(int port, String name, int sendInterval) {
        this.port = port;
        this.name = name;
        this.sendInterval = sendInterval;
        sender = new DataSendWorker(port);
    }

    public int getSendInterval() {
        return sendInterval;
    }

    public void setSendInterval(int sendInterval) {
        this.sendInterval = sendInterval;
    }
}
