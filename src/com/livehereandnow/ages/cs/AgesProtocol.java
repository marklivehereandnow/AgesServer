package com.livehereandnow.ages.cs;

import com.livehereandnow.ages.exception.AgesException;
import java.io.*;
import java.net.*;

public class AgesProtocol implements State {

    int id = 0;
////    Server ages;
//    private static final int WAITING = 0;
//    private static final int ASK_PASSWORD = 10;
////    private static final int STATE_PASSWORD = 15;
//    private static final int CHECK_PASSWORD = 20;
//    private static final int WAITING_FOR_PLAYER = 22;
//    private static final int GAMING = 25;
//    private static final int TURN_AMY = 26;
//    
//
//    private static final int GAME_OVER = 30;
//    private static final int ANOTHER = 40;

//    private static final int NUMJOKES = 50;
    private int state = WAITING;
//    private int state = State.ASK_PASSWORD;

//    private int state = ASK_PASSWORD;
//    private int currentJoke = 0;
    String currentPlayer = "***";
    String cmd = "";
    private GameMonitor monitor = GameMonitor.getInstance();
    private GameBasic game;
//    String username=null;
    String password=null;
    public int getState() {
        return state;
    }

    public AgesProtocol(int threadId, GameBasic game) {
//        this.ages = new Server();
        id = threadId;
        this.game = game;
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

    public String processInput(String input) throws AgesException, IOException {
//        monitor.show(" before");
//        cmd = input;
        String output = null;
        switch (state) {
            case WAITING:
                output = "username:";
                state = ASK_PASSWORD;
                monitor.setState(id, state);
                break;
            case ASK_PASSWORD:
//                debug("case ASK_PASSWORD:---");
                currentPlayer=input;
//                debug(" current player is "+currentPlayer);
                
                monitor.setUsername(id, currentPlayer);
                output="password:";
                state = CHECK_PASSWORD;
                
                break;

            case CHECK_PASSWORD:
                password=input;
                if (currentPlayer.equalsIgnoreCase("admin")){
                    state=ADMIN;
                    output="admin is ready";
                    break;
                }
                
                
                
                state=CHECK_PLAYERS;
//                output="--- welcome to xxx ---";
//                break;
                
            case CHECK_PLAYERS:
//                Users.setPlayer(id, input);
//                GameMonitor.getInstance().setUserState(id, input);
//                currentPlayer = username;
             
                //GameMonitor.getInstance().show();
                if (((currentPlayer.equalsIgnoreCase("Max")) || currentPlayer.equalsIgnoreCase("Amy"))) {
                    if (monitor.isReadyToGame()) {
                        output = "game command?";
                        state = GAMING;
                    } else {
                        if (!monitor.isThere("Max")) {
                            output = "wait for Max.";
                            state = WAITING_FOR_PLAYER;
                        } else if (!monitor.isThere("Amy")) {
                            output = "wait for Amy.";
                            state = WAITING_FOR_PLAYER;

                        }
                    }
                } else {
                    output = "   "+currentPlayer+", you are our guest!";
                    state=GUEST;
                }
                break;

            case ADMIN:
                output="  ... something for admin only!";
                break;
            case GUEST:
                output="  ... something for guest only!";
                break;
                
            case WAITING_FOR_PLAYER:
                // default first play is Max
                game.setActivePlayer("max");

                if (((currentPlayer.equalsIgnoreCase("Max")) || currentPlayer.equalsIgnoreCase("Amy"))) {
                    if (GameMonitor.getInstance().isReadyToGame()) {
                        output = "game command?";
                        state = GAMING;
                    } else {
                        if (!GameMonitor.getInstance().isThere("Max")) {
                            output = "wait for Max...";
                            state = WAITING_FOR_PLAYER;
                        } else if (!GameMonitor.getInstance().isThere("Amy")) {
                            output = "wait for Amy...";
                            state = WAITING_FOR_PLAYER;

                        }
                    }
                } else {
                    output = "Bye";
                }
                break;

            case GAMING:
//                if (game==null){
//                    game=DummyEngine.getInstance();
//                }
//                if (currentPlayer==null){
//                    currentPlayer="?????????";
//                }
//                if (!game.getActivePlayer().equalsIgnoreCase(currentPlayer)) {
//                    output ="   "+ currentPlayer + ", not your turn!";
//                    break;
//                }
//
//                switch (currentPlayer) {
//                    case "max":
//                        if (cmd.equalsIgnoreCase("change-turn")) {
//                            output = "change-turn to Amy";
//                            game.setActivePlayer("amy");
//                            break;
//                        }
//                        if (cmd==null){
//                            cmd="(no cmd)";
//                        }
//                        output = " doing Max's command... " + game.doProtocol(cmd);   // 90% further development is here!
//                        break;
//                    case "amy":
//                        if (cmd.equalsIgnoreCase("change-turn")) {
//                            output = "change-turn to Max";
//                            game.setActivePlayer("max");
//                            break;
//                        }
//                        if (cmd==null){
//                            cmd="(no cmd)";
//                        }
//                        output = " doing Amy's command... " + game.doProtocol(cmd);   // 90% further development is here!
//                        break;
//
//                    default:
//                        output = currentPlayer + "??? UNKNOWN player ***BUG***";   // 90% further development is here!
//                }
                break;

            case GAME_OVER:
                if (input.equalsIgnoreCase("y")) {
//                    output = "Game is over! ";
//                    state = WAITING;
//                    System.exit(0);
                    output = "Bye";
//                 
                } else {
                    state = GAMING;
                    output = "game command?";
// output = "This is  *** Through the Ages *** server. May I help you?";
                }
                break;

            default:
                output = "FURTHER DEV HERE ...";

        }

        monitor.setUsername(id, currentPlayer);
        monitor.setState(id, state);

        monitor.show();
        return output;
    }
}
