package de.stream.processing.g6.worker;

import de.stream.processing.g6.Main;
import de.stream.processing.g6.Simulation;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class SettingsReaderWorker extends Thread {

    public SettingsReaderWorker() {
        this.setDaemon(true);
        this.start();
    }

    @Override
    synchronized public void run() {

        long lastDate = 0;
        float lastSimSpeed = 10f;
        String lastState = "false";

        boolean lastLivingWindowClosed = false;
        boolean lastBedWindowClosed = false;
        boolean lastKitchenWindowClosed = false;
        boolean lastBathWindowClosed = false;

        while (!interrupted()){

            //read file every second
            try {
                wait(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            BufferedReader br = null;
            try {
                br = new BufferedReader(new FileReader(Main.PATH_TO_CONFIG));
            } catch (FileNotFoundException e) {
                System.out.println("Error while reading Setting file");
                e.printStackTrace();
                return;
            }

            try {
                String currentLine;
                while ((currentLine = br.readLine()) != null){

                    //ignore comments and empty stings
                    if(currentLine.isBlank() | currentLine.startsWith("#")){
                        continue;
                    }

                    String[] split = currentLine.split("=");
                    if(split.length == 2){
                        switch (split[0].strip()){
                            case "simSpeed":
                                float simSpeed = Float.parseFloat(split[1].strip());
                                if(simSpeed != lastSimSpeed){
                                    lastSimSpeed = simSpeed;
                                    Simulation.getInstance().getSimWorker().setNewSpeed(simSpeed);
                                }
                                break;
                            case "isPaused":
                                String newState = split[1].strip();
                                if(!newState.equals(lastState)){
                                    lastState = newState;
                                    if(newState.equals("false")){
                                        Simulation.getInstance().getSimWorker().unPause();
                                    }
                                    if(newState.equals("true")){
                                        Simulation.getInstance().getSimWorker().pause();
                                    }
                                    if(newState.equals("stop")){
                                        Main.run = false;
                                    }
                                }
                                break;
                            case "startDate":
                                String[] dateSplit = split[1].strip().split(":");
                                if(dateSplit.length == 6){
                                    Calendar newDate = new GregorianCalendar();

                                    newDate.set(Calendar.YEAR, Integer.parseInt(dateSplit[0]));
                                    newDate.set(Calendar.MONTH, Integer.parseInt(dateSplit[1])-1);
                                    newDate.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dateSplit[2]));
                                    newDate.set(Calendar.HOUR_OF_DAY, Integer.parseInt(dateSplit[3]));
                                    newDate.set(Calendar.MINUTE, Integer.parseInt(dateSplit[4]));
                                    newDate.set(Calendar.SECOND, Integer.parseInt(dateSplit[5]));
                                    newDate.set(Calendar.MILLISECOND, 0);

                                    if(lastDate != newDate.getTimeInMillis()){
                                        lastDate = newDate.getTimeInMillis();
                                        Simulation.getInstance().getSimWorker().setNewSimTime(new Date(lastDate));
                                    }
                                }
                                break;
                            case "livingRoomWindowsAreClosed":
                                boolean livingClosed = split[1].strip().equals("1");
                                if(!livingClosed == lastLivingWindowClosed){
                                    lastLivingWindowClosed = livingClosed;
                                    Simulation.getInstance().getLivingRoom().setWindowsAreClosed(livingClosed);
                                }
                                break;
                            case "bathroomWindowsAreClosed":
                                boolean bathClosed = split[1].strip().equals("1");
                                if(!bathClosed == lastBathWindowClosed){
                                    lastBathWindowClosed = bathClosed;
                                    Simulation.getInstance().getBathroom().setWindowsAreClosed(bathClosed);
                                }
                                break;
                            case "bedRoomWindowsAreClosed":
                                boolean bedCloased = split[1].strip().equals("1");
                                if(!bedCloased == lastBedWindowClosed){
                                    lastBedWindowClosed = bedCloased;
                                    Simulation.getInstance().getBedRoom().setWindowsAreClosed(bedCloased);
                                }
                                break;
                            case "kitchenWindowsAreClosed":
                                boolean kitchenClosed = split[1].strip().equals("1");
                                if(!kitchenClosed == lastKitchenWindowClosed){
                                    lastKitchenWindowClosed = kitchenClosed;
                                    Simulation.getInstance().getKitchenRoom().setWindowsAreClosed(kitchenClosed);
                                }
                                break;
                        }
                    }
                }
            }catch (Exception e){
                System.out.println("Error while reading setting line");
                e.printStackTrace();
            }

            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
