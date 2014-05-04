package com.livehereandnow.ages.cs;

import java.io.*;
import java.net.*;

public class AgesProtocol implements State {

    int id = 0;
////    Server ages;
//    private static final int WAITING = 0;
//    private static final int ASK_USERNAME = 10;
////    private static final int STATE_PASSWORD = 15;
//    private static final int CHECK_USERNAME = 20;
//    private static final int WAITING_FOR_PLAYER = 22;
//    private static final int TURN_MAX = 25;
//    private static final int TURN_AMY = 26;
//    
//
//    private static final int GAME_OVER = 30;
//    private static final int ANOTHER = 40;

//    private static final int NUMJOKES = 50;
    private int state = WAITING;
//    private int state = State.ASK_USERNAME;
    
//    private int state = ASK_USERNAME;

//    private int currentJoke = 0;
    String currentPlayer = "***";
    String cmd = "";
    private GameMonitor monitor = GameMonitor.getInstance();

    public int getState() {
        return state;
    }

    public AgesProtocol(int threadId) {
//        this.ages = new Server();
        id = threadId;
//        GameMonitor.getInstance().setUsername(id, "???");// reversed!!!
//        setUserState(id, "???");
        monitor.setUsername(id, "?");
        monitor.setState(id, state);
        monitor.show();

        //GameMonitor.getInstance().setState(id, getStateName());
        debug("    [Thread#" + threadId + "] is connected.");
//        GameMonitor.getInstance().show();

    }

//    private void setUserState(int id, String name) {
//        GameMonitor.getInstance().setUsername(id, name);
////        GameMonitor.getInstance().setState(id, getStateName());
//        GameMonitor.getInstance().show();
//
//    }
//    private void updateUserState(int id, String name, int state) {
//        GameMonitor.getInstance().setUserState(id, name);
//        GameMonitor.getInstance().setState(id, getStateName());
//
//        GameMonitor.getInstance().show();
//
//    }
    private void debug(String str) {
        System.out.println(" ...[Protocol] " + str);
    }

    public String processInput(String input) {
        monitor.show(" before");
        cmd = input;
        String output = null;
        switch (state) {
            case WAITING:
                output = "to connect? (y/n)";
                state = ASK_USERNAME;
                monitor.setState(id, state);
                break;
            case ASK_USERNAME:
                if (input.equalsIgnoreCase("y")) {
                    output = "What's your name?";
                    state = CHECK_USERNAME;
                } else {
//                    System.exit(0);
                    output = "Bye";
                    debug("[Thread#" + id + "] is finished!");
                    //   System.out.println(
                    //                    GameMonitor.getInstance().setUserState(id, "---");// reversed!!!
                    //                    GameMonitor.getInstance().show();

//                    setUserState(id, "---");
                    monitor.setUsername(id, "?");
                    monitor.setState(id, state);
//                    updateUserState(id, "---", state);

//                    output = "[Ages Server] May I help you?(y/n)";
//
//                    state = WAITING;
                    //return "NO";
//                    output = "We cannot continue if you don't say 'y'";
                }
                break;

            case CHECK_USERNAME:
//                Users.setPlayer(id, input);
//                GameMonitor.getInstance().setUserState(id, input);
                currentPlayer = input.toLowerCase();
                monitor.setUsername(id, input);

                //GameMonitor.getInstance().show();
                if (((currentPlayer.equalsIgnoreCase("Max")) || currentPlayer.equalsIgnoreCase("Amy"))) {
                    if (GameMonitor.getInstance().isReadyToGame()) {
                        output = "Your command?";
                        state = TURN_MAX;
                    } else {
                        if (!GameMonitor.getInstance().isThere("Max")) {
                            output = "Please wait for Max.";
                            state = WAITING_FOR_PLAYER;
                        } else if (!GameMonitor.getInstance().isThere("Amy")) {
                            output = "Please wait for Amy.";
                            state = WAITING_FOR_PLAYER;

                        }
                    }
                } else {
                    output = "Bye";
                }
                break;

            case WAITING_FOR_PLAYER:
                if (((currentPlayer.equalsIgnoreCase("Max")) || currentPlayer.equalsIgnoreCase("Amy"))) {
                    if (GameMonitor.getInstance().isReadyToGame()) {
                        output = "Your command?";
                        state = TURN_MAX;
                    } else {
                        if (!GameMonitor.getInstance().isThere("Max")) {
                            output = "Please wait for Max.";
                            state = WAITING_FOR_PLAYER;
                        } else if (!GameMonitor.getInstance().isThere("Amy")) {
                            output = "Please wait for Amy.";
                            state = WAITING_FOR_PLAYER;

                        }
                    }
                } else {
                    output = "Bye";
                }
                break;

            case TURN_MAX:
                switch (currentPlayer) {
                    case "max":
                        if (cmd.equalsIgnoreCase("change-turn")){
                            state=TURN_AMY;
                            
                            output="change-turn to Amy";
                            break;
                        }
                        output = " doing Max's command... " + cmd;   // 90% further development is here!
                        break;
                    default:
                        output = currentPlayer+", not your turn!";   // 90% further development is here!
                }
                break;

            case TURN_AMY:
                switch (currentPlayer) {
                    case "amy":
                        if (cmd.equalsIgnoreCase("change-turn")){
                            state=TURN_MAX;
                            output="change-turn to Max";
                            break;
                        }
                       
                        output = " doing Amy's command... " + cmd;   // 90% further development is here!
                        break;
                    default:
                        output = currentPlayer+", not your turn!";   // 90% further development is here!
                }
                break;

            case GAME_OVER:
                if (input.equalsIgnoreCase("y")) {
//                    output = "Game is over! ";
//                    state = WAITING;
//                    System.exit(0);
                    output = "Bye";
//                 
                } else {
                    state = TURN_MAX;
                    output = "Your command?";
// output = "This is  *** Through the Ages *** server. May I help you?";
                }
                break;

            default:
                output = "FURTHER DEV HERE ...";

        }

        monitor.setUsername(id, currentPlayer);
        monitor.setState(id, state);

        monitor.show(" after");
        return output;
    }
}
