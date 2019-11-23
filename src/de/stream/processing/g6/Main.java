package de.stream.processing.g6;

import de.stream.processing.g6.sensorImplemantations.TestSensor;
import de.stream.processing.g6.worker.SimWorker;

import java.util.ArrayList;
import java.util.List;

public class Main {

    //global settings
    public static final String SEND_TO = "127.0.0.1";

    public static void main(String[] args) {
	    System.out.println("Start of Senor-Data Generator");

        List<Sensor> allSensors = new ArrayList<>();
        allSensors.add(new TestSensor(54001, "Date-Echo Sesor 1"));

        new SimWorker(allSensors, new Settings());

        try {
            Thread.sleep(50000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("End of Senor-Data Generator");
    }
}
