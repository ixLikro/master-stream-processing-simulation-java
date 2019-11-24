package de.stream.processing.g6.simulator;

import java.util.Date;

public abstract class Simulator {

    /**
     * Describe how often the simulator should be called to simulate stuff.
     * 1 means every simulated Second
     * 60 means every simulated Minute
     */
    protected int simulationInterval;

    /**
     * get called from the SimWorker.
     * If this method get called the simulator should simulate new values based on the given simulation time.
     *
     * @param simTime the simulated time
     */
    public abstract void simulate(Date simTime);

    public Simulator(int simulationInterval) {
        this.simulationInterval = simulationInterval;
    }

    public int getSendInterval() {
        return simulationInterval;
    }
}
