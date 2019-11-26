package de.stream.processing.g6;

import de.stream.processing.g6.sensor.Sensor;
import de.stream.processing.g6.sensor.TestSensor;
import de.stream.processing.g6.setting.Setting;
import de.stream.processing.g6.simulator.Simulator;
import de.stream.processing.g6.worker.SimWorker;

import java.util.ArrayList;
import java.util.List;

public class Main {

    //global settings
    public static final String SEND_TO = "127.0.0.1";
    public static final String PATH_TO_CONFIG = "settings.conf";
    public static final float PHOTOVOLTAIC_AREA = 10; //in m^2
    public static final float BATTERY_CAPACITY = 13.50f; //in kwh

    public static boolean run = true;


    public static void main(String[] args) {
	    System.out.println("Start of Senor-Data Generator");

        //start simulation
        Simulation.getInstance().initAndStartSimulation();

        //keep simulation alive
        while(run){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }

        System.out.println("End of Senor-Data Generator");
    }
}
