package de.stream.processing.g6.worker;

import de.stream.processing.g6.Main;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicReference;

/**
 * This class is reasonable to send the actual data.
 * Every Sensor should hold an instance to this Class.
 *
 */
public class DataSendWorker extends Thread {

    private final int port;
    private final AtomicReference<String> data;

    public DataSendWorker(int port) {
        this.port = port;
        data = new AtomicReference<>();

        this.setDaemon(true);
        this.start();
    }

    /**
     * send data the given Data to the server.
     * @param dataToSend data to send
     */
    synchronized public void sendData(String dataToSend){
        data.set(dataToSend);
        this.notify();
    }


    @Override
    synchronized public void run() {
        Socket socket;
        PrintWriter out;
        try{
            socket = new Socket(Main.SEND_TO, port);
            out = new PrintWriter(socket.getOutputStream(), true);
        }catch (Exception e){
            System.out.println("Error while creating a socket connection!");
            System.out.println("Port:" +port);
            e.printStackTrace();
            return;
        }

        while (!Thread.interrupted()){
            try {
                //wait until the DataSender#sendData method call this.notify()
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
                return;
            }

            out.write(data.get()+"\n");
            out.flush();
        }

        //clean up
        try {
            out.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
