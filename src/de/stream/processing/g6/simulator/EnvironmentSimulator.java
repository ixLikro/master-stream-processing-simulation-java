package de.stream.processing.g6.simulator;

import de.stream.processing.g6.Simulation;
import de.stream.processing.g6.data.TemperatureData;
import de.stream.processing.g6.util.MutableInteger;
import de.stream.processing.g6.util.RandomHelper;
import de.stream.processing.g6.util.Weather;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class EnvironmentSimulator extends Simulator {

    private float outsideTemperature;
    private Map<RoomSimulator, MutableInteger> roomCounter;
    private int noRoomCounter = 0;

    public EnvironmentSimulator(RoomSimulator[] rooms, int simulationInterval) {
        super(simulationInterval);

        roomCounter = new HashMap<>();
        for (RoomSimulator room : rooms) {
            roomCounter.put(room,new MutableInteger(0));
        }
    }

    @Override
    public void simulate(Date simTime) {

        //get outside temperature, if its hot and raining reduce temperature
        outsideTemperature = TemperatureData.getInstance().getTemperature(simTime);
        if(outsideTemperature > 25f && Simulation.getInstance().getWeatherSimulator().getCurrentWeather() == Weather.LOW_PRECIPITATION){
            outsideTemperature -= RandomHelper.getFloat(-2,-1);
        }
        if(outsideTemperature > 25f && Simulation.getInstance().getWeatherSimulator().getCurrentWeather() == Weather.HIGH_PRECIPITATION){
            outsideTemperature -= RandomHelper.getFloat(-4,-1.5f);
        }

        //reduce all room counter
        roomCounter.forEach((roomSimulator, mutableInteger) -> {
            mutableInteger.set(mutableInteger.get() - simulationInterval);
            if(mutableInteger.get() < 0){
                mutableInteger.set(0);
            }
        });

        //reduce noRoom counter
        noRoomCounter -= simulationInterval;
        if(noRoomCounter < 0){
            noRoomCounter = 0;
        }

        if(countRoomsProducingBadAir() == 0 && noRoomCounter == 0){
            if(RandomHelper.hitPercentChance(30)){
                noRoomCounter = RandomHelper.getInteger(60 * 20, 60 * 60 * 3) / simulationInterval;
            }else {
                roomCounter.forEach((roomSimulator, mutableInteger) -> {
                    if(RandomHelper.hitPercentChance(100d / roomCounter.size())){
                        mutableInteger.set(RandomHelper.getInteger(60 * 60 * 2, 60 * 60 * 5) / simulationInterval);
                    }
                });
            }
        }
    }

    private int countRoomsProducingBadAir(){
        //final workaround
        final int[] count = {0};
        roomCounter.forEach((roomSimulator, mutableInteger) -> {
            if(mutableInteger.get() > 0) count[0]++;
        });
        return count[0];
    }

    public float getOutsideTemperature() {
        return outsideTemperature;
    }

    public boolean amIProduceBadAir(RoomSimulator roomSimulator){
        if(roomCounter.get(roomSimulator) != null){
            return roomCounter.get(roomSimulator).get() > 0;
        }
        return false;
    }
}
