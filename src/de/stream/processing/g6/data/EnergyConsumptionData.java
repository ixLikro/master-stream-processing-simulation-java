package de.stream.processing.g6.data;

import de.stream.processing.g6.util.RandomHelper;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class EnergyConsumptionData {

    private static EnergyConsumptionData instance = new EnergyConsumptionData();

    //energy consumption family home in kwh - 24h
    float[] rawDataWorkingDay
            = {0.1f, 0.1f, 0.1f, 0.1f, 0.1f, 0.56f, 0.4f, 0.2f, 0.2f, 0.8f, 1.1f, 2.0f, 1.5f, 1.8f, 1.0f, 0.29f, 0.31f, 0.3f, 1.2f, 1.8f, 1.3f, 0.8f, 0.3f, 0.2f};
    float[] rawDataWeekend
            = {0.5f, 0.2f, 0.1f, 0.1f, 0.1f, 0.56f, 0.4f, 0.2f, 0.2f, 0.8f, 1.1f, 1.5f, 1.3f, 1.2f, 0.8f, 0.29f, 0.31f, 0.3f, 1.5f, 2.2f, 1.5f, 1.0f, 0.7f, 0.6f};

    public static EnergyConsumptionData getInstance() {
        return instance;
    }

    /**
     * @param simTime the simulation time
     * @return energy in kw
     */
    public float getConsumption(Date simTime){

        Calendar time = new GregorianCalendar();
        time.setTimeInMillis(simTime.getTime());

        int dayOfWeek = time.get(Calendar.DAY_OF_WEEK);
        int hour = time.get(Calendar.HOUR_OF_DAY);

        float ret;
        if(dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY){
            //weekend
            ret = rawDataWeekend[hour] + RandomHelper.getFloat(-0.01f, 0.01f);
        }else {
            //workday
            ret = rawDataWorkingDay[hour] + RandomHelper.getFloat(-0.01f, 0.01f);
        }

        return ret;
    }
}
