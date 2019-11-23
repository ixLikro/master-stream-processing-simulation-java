package de.stream.processing.g6.worker;

import de.stream.processing.g6.Sensor;
import de.stream.processing.g6.Settings;
import org.json.JSONObject;

import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class SimWorker extends Thread {

    private Settings currentSetting;
    private final List<Sensor> allSensors;

    public SimWorker(List<Sensor> sensors, Settings initSettings){
        this.allSensors = sensors;
        this.currentSetting = initSettings;

        this.setDaemon(true);
        this.start();

    }

    public void updateSettings(Settings currentSetting) {
        this.currentSetting = currentSetting;
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            try {
                Thread.sleep(500);
                allSensors.forEach(sensor -> sensor.sendValue(new Date()));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
