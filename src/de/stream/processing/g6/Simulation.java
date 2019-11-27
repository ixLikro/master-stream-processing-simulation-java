package de.stream.processing.g6;

import de.stream.processing.g6.sensor.*;
import de.stream.processing.g6.setting.Setting;
import de.stream.processing.g6.simulator.*;
import de.stream.processing.g6.worker.SettingsReaderWorker;
import de.stream.processing.g6.worker.SimWorker;

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
    private RoomSimulator livingRoom = new RoomSimulator(40);
    private RoomSimulator bathroom = new RoomSimulator(15);
    private RoomSimulator bedroom = new RoomSimulator(20);
    private RoomSimulator kitchenRoom = new RoomSimulator(25);
    private EnergySimulator energySimulator = new EnergySimulator(60);


    //lists
    private List<Simulator> allSimulations;
    private List<Sensor> allSensors;


    private Simulation(){

        // ************************* sensors
        TemperatureSensor outsideTempSensor = new TemperatureSensor(54001, "Outside Temperature Sensor", 10 * 60);
        Barometer barometer1 = new Barometer(54002, "Barometer 1",  30 * 60);
        Barometer barometer2 = new Barometer(54003, "Barometer 2", 30 * 60);
        Barometer barometer3 = new Barometer(54004, "Barometer 3", 30 *60);
        AirQualitySensor livingAir = new AirQualitySensor(54005, "Air quality - living room", livingRoom, 60);
        AirQualitySensor bathroomAir = new AirQualitySensor(54006, "Air quality - bathroom", bathroom, 60);
        AirQualitySensor bedroomAir = new AirQualitySensor(54007, "Air quality - bedroom", bedroom, 60);
        AirQualitySensor kitchenAir = new AirQualitySensor(54009, "Air quality - kitchen", kitchenRoom, 60);
        WindowSensor livingWindow = new WindowSensor(54010, "Window - living room", livingRoom, 10);
        WindowSensor bathWindow = new WindowSensor(54011, "Window - bathroom", bathroom, 10);
        WindowSensor bedWindow = new WindowSensor(54012, "Window - bedroom", bedroom ,10);
        WindowSensor kitchenWindow = new WindowSensor(54013, "Window - kitchen", kitchenRoom, 10);
        EnergyConsumptionSensor energyConsumptionSensor = new EnergyConsumptionSensor(54014, "Energy Consumption Sensor" , 60);
        EnergyPhotovoltaicProduceSensor energyPhotovoltaicProduceSensor = new EnergyPhotovoltaicProduceSensor(54015, "Photovoltaic produce Sensor",  60);
        BatterySensor batterySensor = new BatterySensor(54016,"Battery Level", 60);



        //starts the settingsReaderWorker
        new SettingsReaderWorker();

        //register sensors an simulators
        allSensors = Arrays.asList(
                outsideTempSensor,
                barometer1,
                barometer2,
                barometer3,
                livingAir,
                bedroomAir,
                bathroomAir,
                kitchenAir,
                livingWindow,
                bathWindow,
                bedWindow,
                kitchenWindow,
                energyConsumptionSensor,
                energyPhotovoltaicProduceSensor,
                batterySensor);
        allSimulations = Arrays.asList(environmentSimulator,
                weatherSimulator,
                livingRoom,
                bathroom,
                bedroom,
                kitchenRoom,
                energySimulator);
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

    public RoomSimulator getLivingRoom() {
        return livingRoom;
    }

    public RoomSimulator getBathroom() {
        return bathroom;
    }

    public RoomSimulator getBedRoom() {
        return bedroom;
    }

    public RoomSimulator getKitchenRoom() {
        return kitchenRoom;
    }

    public EnergySimulator getEnergySimulator() {
        return energySimulator;
    }
}
