package de.stream.processing.g6.data;

import de.stream.processing.g6.Main;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class PhotovoltaicPowerData {

    private static PhotovoltaicPowerData instance = new PhotovoltaicPowerData();
    public static PhotovoltaicPowerData getInstance() {
        return instance;
    }

    //watt per m^2 in Wolfenbüttel,
    //rawData[Month][Hour]
    //Source https://re.jrc.ec.europa.eu/pvg_tools/en/tools.html
    private final float[][] rawData = {
            {0f, 0f, 0f, 0f, 0f, 0f, 44f, 133f, 212f, 256f, 257f, 215f, 138f, 49f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f},
            {0f, 0f, 0f, 0f, 0f, 28f, 133f, 252f, 346f, 399f, 404f, 361f, 274f, 159f, 45f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f},
            {0f, 0f, 0f, 0f, 33f, 149f, 292f, 421f, 516f, 567f, 568f, 519f, 426f, 298f, 155f, 36f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f},
            {0f, 0f, 0f, 46f, 174f, 333f, 486f, 614f, 703f, 746f, 740f, 684f, 584f, 448f, 291f, 136f, 24f, 0f, 0f, 0f, 0f, 0f, 0f, 0f},
            {0f, 0f, 34f, 138f, 283f, 438f, 582f, 699f, 779f, 816f, 807f, 753f, 657f, 528f, 377f, 223f, 90f, 10f, 0f, 0f, 0f, 0f, 0f, 0f},
            {0f, 0f, 65f, 182f, 329f, 483f, 625f, 741f, 820f, 858f, 852f, 805f, 710f, 586f, 439f, 285f, 143f, 41f, 0f, 0f, 0f, 0f, 0f, 0f},
            {0f, 0f, 38f, 140f, 280f, 432f, 575f, 694f, 777f, 820f, 819f, 773f, 686f, 565f, 421f, 296f, 131f, 33, 0f, 0f, 0f, 0f, 0f, 0f},
            {0f, 0f, 0f, 66f, 191f, 340f, 485f, 606f, 693f, 736f, 733f, 684f, 592f, 467f, 320f, 173f, 45f, 0f, 0f, 0f, 0f, 0f, 0f, 0f},
            {0f, 0f, 0f, 96f, 235f, 378f, 498f, 581f, 618f, 607f, 548f, 446f, 314f, 169f, 46f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f},
            {0f, 0f, 0f, 0f, 17f, 118f, 246f, 358f, 434f, 464f, 445f, 379f, 274f, 147f, 35f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f},
            {0f, 0f, 0f, 0f, 0f, 23f, 108f, 203f, 272f, 299f, 282f, 222f, 131f, 38f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f},
            {0f, 0f, 0f, 0f, 0f, 0f, 43f, 124f, 191f, 223f, 212f, 162f, 84f, 13f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f},
    };

    public PhotovoltaicPowerData() {

    }

    /**
     * @param simTime the simulation time
     * @return the produced energy for the given simulation time in kw
     */
    public float getPhotovoltaicEnergy(Date simTime){
        Calendar calender = new GregorianCalendar();
        calender.setTimeInMillis(simTime.getTime());

        int month = calender.get(Calendar.MONTH);
        int hour = calender.get(Calendar.HOUR_OF_DAY);

        return (rawData[month][hour] * Main.PHOTOVOLTAIC_AREA) / 1000;
    }
}
