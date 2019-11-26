package de.stream.processing.g6.setting;

import java.util.Date;

public class Setting {
    private Date startDate;
    private float simSpeed;

    public Setting() {
        startDate = new Date();
        simSpeed = 10;
    }

    public Setting(Date startDate, float simSpeed) {
        this.startDate = startDate;
        this.simSpeed = simSpeed;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public float getSimSpeed() {
        return simSpeed;
    }

    public void setSimSpeed(float simSpeed) {
        this.simSpeed = simSpeed;
    }
}



