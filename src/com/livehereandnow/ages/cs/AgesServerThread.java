package com.livehereandnow.ages.cs;

import java.net.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AgesServerThread extends Thread {

    private int threadId;
    private Socket socket = null;

    public AgesServerThread(Socket socket) {
        super();
//        threadId = SerialNumberGenerator.INSTANCE.getNextSerial();
        threadId = GameMonitor.getInstance().getAvailableThreadNumber();
        if (threadId > 0) {
//        System.out.println(" thread id="+threadId);
//        this.setName("player00" + threadId);
//        super("mark00"+SerialNumberGenerator.INSTANCE.getNextSerial(););
            this.socket = socket;
        } else {
            System.out.println("No more available thread ... need to wait");
        }
    }

    private void debug(String str) {
        System.out.println(" ...[Thread] " + str);
    }

    public void run() {
        try (
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(
                                socket.getInputStream()));) {
            String inputLine, outputLine;
            AgesProtocol kkp = new AgesProtocol(threadId);
            outputLine = kkp.processInput(null);
            out.println(outputLine);

            while ((inputLine = in.readLine()) != null) {
                //
                //
                //

//                debug("from thread#" + threadId + " " + inputLine);
//                debug("current state is " + kkp.getState());

                //
                //
                //
                outputLine = kkp.processInput(inputLine);// after protocol's process
                //
                // to implement Game logic here
                //

                out.println("" + outputLine);// send to client
                if (outputLine.equals("Bye")) {
                    break;
                }
            }
            socket.close();
//            System.out.println("    [Thread#" + threadId + "] is finished!");
        } catch (IOException e) {
            e.printStackTrace();

            Logger.getLogger(AgesServerThread.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
