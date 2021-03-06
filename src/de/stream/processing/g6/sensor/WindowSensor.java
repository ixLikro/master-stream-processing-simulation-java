package de.stream.processing.g6.sensor;

import de.stream.processing.g6.simulator.RoomSimulator;
import de.stream.processing.g6.util.RandomHelper;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WindowSensor extends Sensor {

    private JSONObject sensorInfo;
    private int windowCount;
    private RoomSimulator roomSimulator;
    private List<Integer> windowIDs;

    public WindowSensor(int port, String name, RoomSimulator roomSimulator, int sendInterval) {
        super(port, name, sendInterval);

        windowIDs = new ArrayList<>();

        windowCount = RandomHelper.getInteger(1,5);

        for(int i  = 0; i < windowCount; i++){
            windowIDs.add(RandomHelper.getInteger(100000,999999));
        }

        this.roomSimulator = roomSimulator;

        sensorInfo = new JSONObject();
        sensorInfo.put("type", "RCFL-0516");
        sensorInfo.put("Transmitting", "20mW");
        sensorInfo.put("product type", "induction");
    }

    @Override
    public void sendValue(Date simTime) {
        boolean isClosed = roomSimulator.isWindowsAreClosed();

        JSONArray values = new JSONArray();
        int count = 0;
        for(int i  = 0; i < windowCount; i++){
            JSONObject oneWindow = new JSONObject();
            oneWindow.put("id", windowIDs.get(i));
            if(!isClosed){
                if(RandomHelper.hitPercentChance(50d)){
                    oneWindow.put("isOpen", true);
                    count++;
                }else {
                    oneWindow.put("isOpen", false);
                }
            }
            oneWindow.put("isOpen", !isClosed);
            values.put(oneWindow);
        }
        if(count == 0 && !isClosed){
            ((JSONObject)values.get(0)).put("isOpen", true);
        }

        JSONObject ret = new JSONObject();
        ret.put("name", name);
        ret.put("windows", values);
        ret.put("timestamp", simTime.getTime());
        ret.put("room ID", roomSimulator.getRoomID());
        ret.put("sensor Info", sensorInfo);

        sender.sendData(ret.toString());
    }
}
