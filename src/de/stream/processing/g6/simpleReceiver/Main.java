package de.stream.processing.g6.simpleReceiver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class Main {

    /**
     * Starts a simple reviver that prints the received Data.
     * @param args ignored
     */
    public static void main(String[] args) {
        System.out.println("Start of Senor-Data Receiver");

        Map<Integer, String> sensors = new HashMap<>();
        Map<Integer, CompletableFuture<Void>> futures = new HashMap<>();

        //create receiver
        sensors.put(54001, "Photovoltaic");
        sensors.put(54002, "Battery");
        sensors.put(54003, "Enis");

        //start receiver
        for (Map.Entry<Integer, String> entry : sensors.entrySet()) {
            futures.put(entry.getKey(), startReceiver(entry.getValue(), entry.getKey()));
        }

        //wait until jobs are finished
        futures.forEach((integer, voidCompletableFuture) -> voidCompletableFuture.join());

        System.out.println("");
        System.out.println("End of Senor-Data Senor-Data Receiver");
    }

    private static CompletableFuture<Void> startReceiver(String name, int port){
        return CompletableFuture.supplyAsync(() -> {
            try {
                ServerSocket serverSocket = new ServerSocket(port);

                while (true){
                    try{
                        System.out.println("Sensor "+ name+" is now listening on Port: "+port);
                        Socket socket = serverSocket.accept();
                        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        System.out.println("Sensor "+ name+" has now a valid connection");


                        while (true){
                            String received = in.readLine();
                            System.out.println("[NEW Message] from "+ name+"\n\t"+received);
                        }

                    }catch (Exception e){
                        System.out.println("Sensor "+ name+" lost connection!");
                    }
                }
            } catch (IOException e) {
                System.out.println("Stop Sensor "+ name);
                e.printStackTrace();
            }
            return null;
        });
    }
}
