package de.stream.processing.g6;

import de.stream.processing.g6.data.TemperatureData;
import de.stream.processing.g6.sensor.Barometer;
import de.stream.processing.g6.sensor.Sensor;
import de.stream.processing.g6.sensor.TemperatureSensor;
import de.stream.processing.g6.sensor.TestSensor;
import de.stream.processing.g6.setting.Setting;
import de.stream.processing.g6.simulator.EnvironmentSimulator;
import de.stream.processing.g6.simulator.Simulator;
import de.stream.processing.g6.simulator.WeatherSimulator;
import de.stream.processing.g6.worker.SettingsReaderWorker;
import de.stream.processing.g6.worker.SimWorker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Singleton that holds the actual simulationWorker, all Simulator and all Sensor objects
 */
public class Simulation {

    private static Simulation instance = new Simulation();

    // simulation worker object
    private SimWorker simWorker;

    // ************************* simulator objects
    private EnvironmentSimulator environmentSimulator = new EnvironmentSimulator(10);
    private WeatherSimulator weatherSimulator = new WeatherSimulator();

    // ************************* sensors
    //private TestSensor testSensor = new TestSensor(54001, "Date-Echo Sensor 1", 10);
    private TemperatureSensor outsideTempSensor = new TemperatureSensor(54001, "Outside Temperature Sensor", 30);
    private Barometer barometer1 = new Barometer(54002, "Barometer 1", 60);
    private Barometer barometer2 = new Barometer(54003, "Barometer 2", 60);
    private Barometer barometer3 = new Barometer(54004, "Barometer 3", 60);


    //lists
    private List<Simulator> allSimulations;
    private List<Sensor> allSensors;


    private Simulation(){

        //starts the settingsReaderWorker
        new SettingsReaderWorker();

        //register sensors an simulators
        allSensors = Arrays.asList(outsideTempSensor, barometer1, barometer2, barometer3);
        allSimulations = Arrays.asList(environmentSimulator, weatherSimulator);
    }


    //getter
    public SimWorker getSimWorker() {
        return simWorker;
    }

    /**
     * starts the simulation initially. This method dose nothing if the simulation is already running.
     */
    public void initAndStartSimulation(){
        if(simWorker == null){
            //start the simulation
            simWorker = new SimWorker(allSensors, allSimulations, new Setting());
        }
    }

    public static Simulation getInstance() {
        return instance;
    }

    public EnvironmentSimulator getEnvironmentSimulator() {
        return environmentSimulator;
    }

    public WeatherSimulator getWeatherSimulator() {
        return weatherSimulator;
    }

    public List<Simulator> getAllSimulations() {
        return allSimulations;
    }

    public List<Sensor> getAllSensors() {
        return allSensors;
    }
}
