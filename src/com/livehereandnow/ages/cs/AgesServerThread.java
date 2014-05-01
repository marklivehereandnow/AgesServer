package com.livehereandnow.ages.cs;

import java.net.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AgesServerThread extends Thread {

    private Socket socket = null;

    public AgesServerThread(Socket socket) {
        super();
        int Id = SerialNumberGenerator.INSTANCE.getNextSerial();
        this.setName("player00" + Id);
//        super("mark00"+SerialNumberGenerator.INSTANCE.getNextSerial(););
        this.socket = socket;
    }

    public void run() {
        System.out.println("   Server's thread starting ..." + this.getName());
        try (
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(
                                socket.getInputStream()));) {
            String inputLine, outputLine;
            AgesProtocol kkp = new AgesProtocol(this.getName());
            outputLine = kkp.processInput(null);
            out.println(outputLine);

            while ((inputLine = in.readLine()) != null) {
                outputLine = kkp.processInput(inputLine);
                out.println(outputLine);
                if (outputLine.equals("Bye")) {
                    break;
                }
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
       
            Logger.getLogger(AgesServerThread.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}