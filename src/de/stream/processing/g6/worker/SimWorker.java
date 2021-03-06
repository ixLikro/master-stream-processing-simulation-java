package de.stream.processing.g6.worker;

import de.stream.processing.g6.Main;
import de.stream.processing.g6.Simulation;
import de.stream.processing.g6.sensor.Sensor;
import de.stream.processing.g6.setting.Setting;
import de.stream.processing.g6.simulator.Simulator;
import de.stream.processing.g6.util.MutableInteger;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class SimWorker extends Thread {

    private final Setting settings;
    private final Map<Sensor, MutableInteger> allSensors;
    private final Map<Simulator, MutableInteger> allSimulator;
    private AtomicBoolean isPaused;
    private final Calendar simTime;

    public SimWorker(List<Sensor> sensors, List<Simulator> simulators, Setting initSetting){
        this.allSensors = new HashMap<>();
        this.allSimulator = new HashMap<>();
        sensors.forEach(sensor -> {
            allSensors.put(sensor, new MutableInteger(1));
        });
        simulators.forEach(simulator -> {
            allSimulator.put(simulator, new MutableInteger(1));
        });

        this.settings = initSetting;
        this.isPaused = new AtomicBoolean(false);
        simTime = new GregorianCalendar();
        simTime.setTime(settings.getStartDate());

        this.setDaemon(true);
        this.start();

    }

    /**
     * sets the a new simulation time
     * @param simSpeed
     */
    public void setNewSpeed(float simSpeed) {
        synchronized (settings){
            settings.setSimSpeed(simSpeed);

            if(settings.getSimSpeed() > 1000){
                settings.setSimSpeed(1000f);
                System.out.println("The simulation only supports a simSpeed between 0.001 and 1000!");
                System.out.println("The simulation speed was set to 1000");
            }
            if(settings.getSimSpeed() < 0){
                settings.setSimSpeed(0.001f);
                System.out.println("The simulation only supports a simSpeed between 0.001 and 1000!");
                System.out.println("The simulation speed was set to 0.001");
            }
        }
    }

    /**
     * jump to a new simulation time
     * @param newSimTime the new time
     */
    public void setNewSimTime(Date newSimTime){
        synchronized (simTime){
            simTime.setTime(newSimTime);
        }
    }

    @Override
    synchronized public void run() {
        while (!Thread.interrupted() ) {

            //calculate a simulated second
            long waitInMills = 0;
            synchronized (settings){
                waitInMills = (long)((1. / settings.getSimSpeed()) * 1000);
            }

            try {
                //tick every second in simulation time
                wait(waitInMills);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //if paused? then skip this simulated second
            if(isPaused.get()) continue;

            synchronized (simTime){
                simTime.add(Calendar.SECOND, 1);

                System.out.print(
                        "SimTime: "+ new Date(simTime.getTimeInMillis()).toString()
                        + "\n\tSpeed: "+ settings.getSimSpeed()
                        + "\n\toutTemp: "+ Simulation.getInstance().getEnvironmentSimulator().getOutsideTemperature()
                        + "\n\tcurrent Weather: " + Simulation.getInstance().getWeatherSimulator().getCurrentWeather()
                        + "\n\tcoming Weather: "+ Simulation.getInstance().getWeatherSimulator().getComingWeather()
                        + "\n\tppm Kitchen: "+ Simulation.getInstance().getKitchenRoom().getPpm()
                        + "\n\tppm LivingRoom: "+ Simulation.getInstance().getLivingRoom().getPpm()
                        + "\n\tppm Badroom: "+ Simulation.getInstance().getBedRoom().getPpm()
                        + "\n\tppm Bathroom: " + Simulation.getInstance().getBathroom().getPpm()
                        + "\n\te Consumption: "+ Simulation.getInstance().getEnergySimulator().getConsumption()
                        + "\n\te Produce: "+ Simulation.getInstance().getEnergySimulator().getProduce()
                        + "\n\tBattery Level: " + (Simulation.getInstance().getEnergySimulator().getBatteryLevel() / Main.BATTERY_CAPACITY) * 100 + "%"
                );
            }


            //call all simulator if counter is equals to the sensor send interval
            allSimulator.forEach((simulator, counter) -> {
                if(counter.get() >= simulator.getSendInterval()){
                    simulator.simulate(new Date(simTime.getTimeInMillis()));
                    counter.set(1);
                }else {
                    counter.increment();
                }
            });

            //send value if counter is equals to the sensor send interval
            allSensors.forEach((Sensor sensor, MutableInteger counter) -> {
                if(counter.get() >= sensor.getSendInterval()){
                    sensor.sendValue(new Date(simTime.getTimeInMillis()));
                    counter.set(1);
                }else {
                    counter.increment();
                }
            });
        }
    }

    public boolean isPaused(){
        return isPaused.get();
    }

    public void pause(){
        isPaused.set(true);
    }

    public void unPause(){
        isPaused.set(false);
    }

}
