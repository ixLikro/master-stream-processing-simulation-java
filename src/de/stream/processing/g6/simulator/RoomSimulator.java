package de.stream.processing.g6.simulator;

import de.stream.processing.g6.Simulation;
import de.stream.processing.g6.util.RandomHelper;

import java.util.Date;
import java.util.List;

public class RoomSimulator extends Simulator {

    private boolean windowsAreClosed;
    private double size;
    private double ppm;
    private int roomID;

    private double goodAir = 500;
    private double worstAir = 2000;

    public RoomSimulator(double floorSize, int roomID) {
        super(45);

        size = floorSize;
        windowsAreClosed = true;
        ppm = 600.;
        this.roomID = roomID;
    }

    @Override
    public void simulate(Date simTime) {
        //threshold source: https://www.fuehlersysteme.de/wiki/co2-konzentration
        if(!windowsAreClosed){
            ppm -= ((800d/900d) * (20 / size)) * simulationInterval;

            if(ppm < goodAir){
                ppm = goodAir;
            }
        }else {
            if(Simulation.getInstance().getEnvironmentSimulator().amIProduceBadAir(this)){
                int random = RandomHelper.getInteger(5, 10);
                ppm += ((random / (size)) * (simulationInterval / 2.) / 4);

                if(ppm > worstAir){
                    ppm = worstAir;
                }
            }else {
                ppm -= RandomHelper.getFloat(0.0001f, 0.005f) * simulationInterval;

                if(ppm < goodAir){
                    ppm = goodAir;
                }
            }

        }
    }

    public void setWindowsAreClosed(boolean windowsAreClosed) {
        this.windowsAreClosed = windowsAreClosed;
    }

    public double getPpm() {
        return ppm;
    }

    public boolean isWindowsAreClosed() {
        return windowsAreClosed;
    }

    public int getRoomID() {
        return roomID;
    }
}
