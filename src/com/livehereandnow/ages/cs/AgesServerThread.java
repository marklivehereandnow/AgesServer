package com.livehereandnow.ages.cs;

import com.livehereandnow.ages.cs.State;
import com.livehereandnow.ages.exception.AgesException;
import java.io.*;
import java.net.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AgesServerThread extends Thread implements State {

    private int threadId;
    private Socket socket = null;
    private GameMonitor monitor = null;
    private GameBasic game;

    public AgesServerThread(Socket socket) throws AgesException {
        super();

        monitor = GameMonitor.getInstance();
        threadId = monitor.getAvailableThreadNumber();
        game = DummyEngine.getInstance();

        if (threadId > 0) {
            this.socket = socket;
        } else {
            debug("No more available thread");
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
            AgesProtocol agesProtocol = new AgesProtocol(threadId, game);

            outputLine = agesProtocol.processInput(null);
            out.println(outputLine);

//            out.print("username: ");
            while ((inputLine = in.readLine()) != null) {
                //
                //
                //

//                debug("from thread#" + threadId + " " + inputLine);
//                debug("current state is " + agesProtocol.getState());
                //
                //
                //
                int state = agesProtocol.getState();
                switch (state) {
                    case ADMIN:
//                        System.out.println("admin: "+inputLine);
                        DateFormat dateFormat = new SimpleDateFormat("dd/MM HH:mm:ss");
                        java.util.Date date = new java.util.Date();
//                        System.out.println("Current Date Time : " + dateFormat.format(date));
                        outputLine = "" + dateFormat.format(date)+game.doUserCmd("admin", "brief");
                        break;
                    case GAMING:
                        outputLine = game.doUserCmd(agesProtocol.currentPlayer, inputLine);
                        break;
                    default:
                        outputLine = agesProtocol.processInput(inputLine);// after protocol's process

                }
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
        } catch (AgesException ex) {
            Logger.getLogger(AgesServerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
