package com.livehereandnow.ages.cs;

import com.livehereandnow.ages.exception.AgesException;
import java.net.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.livehereandnow.ages.cs.State;

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
                if (agesProtocol.getState() == GAMING) {
//                    game.doProtocol(inputLine)
                    
                    outputLine = game.doUserCmd(agesProtocol.currentPlayer, inputLine);
                    
                } else {
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
