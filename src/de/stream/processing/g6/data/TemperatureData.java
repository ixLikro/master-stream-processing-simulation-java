package de.stream.processing.g6.data;

import de.stream.processing.g6.util.RandomHelper;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Temperature Data for Wolfenbüttel
 * Source: https://re.jrc.ec.europa.eu/pvg_tools/en/tools.html - Daily Data - Temperature
 */
public class TemperatureData {

    private static TemperatureData instance = new TemperatureData();

    private final float[][] rawData = {
        {0.8f, 0.8f, 0.7f, 0.7f, 0.6f, 0.9f, 1.1f, 1.4f, 1.9f, 2.4f, 3.0f, 2.8f, 2.7f, 2.5f, 2.2f, 1.9f, 1.6f, 1.5f, 1.3f, 1.2f, 1.3f, 1.2f, 0.9f, 0.8f},
        {0.9f, 0.8f, 0.8f, 0.8f, 0.7f, 0.6f, 0.6f, 1.2f, 1.9f, 2.5f, 3.2f, 3.8f, 4.4f, 4.4f, 4.4f, 4.5f, 3.8f, 3.1f, 2.4f, 2.1f, 1.9f, 1.6f, 1.4f, 1.2f},
        {3.2f, 3.0f, 2.9f, 2.7f, 2.7f, 2.7f, 2.7f, 3.7f, 4.8f, 5.9f, 6.5f, 7.1f, 7.7f, 7.9f, 8.1f, 8.3f, 7.4f, 6.5f, 5.5f, 5.0f, 4.5f, 4.1f, 3.8f, 3.5f},
        {7.0f, 6.7f, 6.3f, 6.0f, 6.5f, 6.9f, 7.3f, 8.7f, 10.1f, 11.5f, 12.2f, 12.8f, 13.5f, 13.7f, 13.9f, 14.0f, 13.0f, 12.0f, 11.0f, 10.2f, 9.3f, 8.4f, 8.0f, 7.6f},
        {10.7f, 10.3f, 10.0f, 9.6f, 10.4f, 11.1f, 11.9f, 13.1f, 14.3f, 15.5f, 15.9f, 16.3f, 16.6f, 16.8f, 17.0f, 17.2f, 16.5f, 15.9f, 15.3f, 14.2f, 13.2f, 12.2f, 11.7f, 11.3f},
        {13.6f, 13.2f, 12.9f, 12.5f, 13.5f, 14.4f, 15.3f, 16.3f, 17.4f, 18.4f, 18.8f, 19.2f, 19.6f, 19.9f, 20.1f, 19.6f, 19.1f, 18.6f, 17.5f, 16.4f, 15.3f, 14.8f, 14.2f, 14.0f},
        {16.3f, 15.9f, 15.6f, 15.2f, 16.0f, 16.7f, 17.5f, 18.5f, 19.6f, 20.7f, 21.1f, 21.6f, 22.1f, 22.3f, 22.5f, 22.7f, 22.1f, 21.6f, 21.0f, 19.9f, 18.8f, 17.7f, 17.3f, 16.8f},
        {16.1f, 15.7f, 15.3f, 15.0f, 15.5f, 15.9f, 16.4f, 17.7f, 18.9f, 20.1f, 20.7f, 21.3f, 21.9f, 22.0f, 22.1f, 22.3f, 21.5f, 20.7f, 19.9f, 19.0f, 18.1f, 17.2f, 16.8f, 16.4f},
        {13.1f, 12.8f, 12.5f, 12.2f, 12.3f, 12.5f, 12.6f, 13.8f, 15.1f, 16.4f, 16.9f, 17.4f, 18.0f, 18.0f, 18.1f, 18.2f, 17.2f, 16.3f, 15.4f, 14.8f, 14.3f, 13.8f, 13.5f, 13.2f},
        {8.7f, 8.6f, 8.4f, 8.2f, 8.2f, 8.1f, 8.1f, 9.1f, 10.2f, 11.2f, 11.8f, 12.4f, 13.0f, 12.9f, 12.8f, 12.7f, 11.9f, 11.1f, 10.3f, 9.9f, 9.6f, 9.3f, 9.1f, 8.8f},
        {5.4f, 5.3f, 5.3f, 5.2f, 5.2f, 5.2f, 5.7f, 6.2f, 6.8f, 7.2f, 7.7f, 8.2f, 7.9f, 7.8f, 7.2f, 6.8f, 6.4f, 6.0f, 5.9f, 5.7f, 5.8f, 5.5f, 5.4f, 5.2f},
        {2.3f, 2.3f, 2.2f, 2.2f, 2.3f, 2.3f, 2.5f, 2.7f, 2.9f, 3.4f, 3.9f, 4.4f, 4.1f, 3.8f, 3.5f, 3.3f, 3.0f, 2.8f, 2.7f, 2.6f, 2.5f, 2.4f, 2.3f, 2.0f},
    };

    private final float[][][] data = new float[12][31][24];

    private TemperatureData() {

        /*
            Due to the average calculation of the raw data is not very extreme. We make the cold month a bit colder and the hot ones hotter.
            And we add the day to the data. Every month and day get a specific random manipulator that will be added to the row data. This
            allow us to generate a 'today is a cold Day'-feeling.
        */
        for (int month = 0; month < rawData.length; month++) {
            float monthManipulator = RandomHelper.getFloat(-1f,1f);

            if(month <= 1 || month >= 10){
                //may make the cold month a bit colder
                monthManipulator = RandomHelper.getFloat(-3.5f, 1f);
            }

            if(month >= 5 && month <= 7){
                //make the hot month hotter
                monthManipulator = RandomHelper.getFloat(0.5f,6f);
            }

            for(int day = 0; day < data[month].length; day++){
                float dayManipulator = RandomHelper.getFloat(-1f, 1f);

                for(int hour = 0; hour < data[month][day].length; day++){
                    //add manipulator
                    data[month][day][hour] = rawData[month][hour] + monthManipulator + dayManipulator;
                }
            }
        }
    }

    public static TemperatureData getInstance() {
        return instance;
    }

    public float getTemperature(Date date){
        Calendar calender = new GregorianCalendar();
        calender.setTimeInMillis(date.getTime());

        int month = calender.get(Calendar.MONTH);
        int day = calender.get(Calendar.DAY_OF_MONTH);
        int hour = calender.get(Calendar.HOUR_OF_DAY);

        //return the value and add some sensor noise
        return data[month][day][hour] + RandomHelper.getFloat(-0.15f,0.15f);
    }
}
