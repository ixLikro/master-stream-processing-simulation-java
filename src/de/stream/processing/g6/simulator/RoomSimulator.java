package de.stream.processing.g6.simulator;

import de.stream.processing.g6.util.RandomHelper;

import java.util.Date;
import java.util.List;

public class RoomSimulator extends Simulator {

    private boolean windowsAreClosed;
    private double size;
    private double ppm;

    private double goodAir = 500;
    private double worstAir = 2000;

    public RoomSimulator(double floorSize) {
        super(45);

        size = floorSize;
        windowsAreClosed = true;
        ppm = 800.;
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
            int random = RandomHelper.getInteger(-1, 6);
            ppm += (random / 70d) * simulationInterval;

            if(ppm > worstAir){
                ppm = worstAir;
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
}
