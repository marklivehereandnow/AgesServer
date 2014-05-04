/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.livehereandnow.ages.cs;

/**
 *
 * @author mark
 */
public class GameMonitor implements State {

    private static GameMonitor instance;

    public String getStateName(int state) {
        switch (state) {
            case State.WAITING:
                return "waiting user to connect";
            case State.ASK_PASSWORD:
                return "asking username";
            case State.CHECK_PASSWORD:
                return "asking password";
            case State.CHECK_PLAYERS:
                return "???checking players status";
            case State.GUEST:
                return "guest";
            case State.WAITING_FOR_PLAYER:
                return "waiting for other player(s)";
            case State.GAMING:
                return "gaming";
//            case State.TURN_AMY:
//                return "turn: Amy";
            case State.GAME_OVER:
                return "game over";

            default:
                return "***BUG*** UNKNOWN STATE";
        }

    }

    public synchronized static GameMonitor getInstance() {
        if (instance == null) {
            instance = new GameMonitor();

            {
                for (int k = 1; k <= MAX_USERS; k++) {
                    username[k] = "---";
                    state[k] = "...";
                }

            }

        }
        return instance;
    }

    private GameMonitor() {

    }

    public synchronized int getAvailableThreadNumber() {
        int num = 0;
        for (int k = 1; k <= MAX_USERS; k++) {
            if (username[k].equals("---")) {
                num = k;
                return num;
            }
        }
        return num;
    }
    private static int MAX_USERS = 6;
    private static String[] username = new String[MAX_USERS + 1];// for 12 users 
    private static String[] state = new String[MAX_USERS + 1];// for 12 users 

    public synchronized boolean setUsername(int t, String u) {
        for (int k = 1; k <= MAX_USERS; k++) {
//            System.out.printf("   ... checking  %3d %s%n", k, username[k]);
            if (username[k].equalsIgnoreCase("***")) {

            } else {
                if (username[k].equalsIgnoreCase(u)) {
                    username[k] = "---";
                    state[k] = "...";
                }
            }
        }

        username[t] = u;
        return true;
    }

    private void debug(String str) {
//        System.out.println(" ...[Monitor] " + str);
    }

    public synchronized boolean setState(int t, int s) {
        state[t] = getStateName(s);
        debug(" id=" + t + " stat=" + s);
        return true;
    }

    public boolean isThere(String name) {
        for (int k = 1; k <= MAX_USERS; k++) {
//            System.out.printf("   ... checking  %3d %s%n", k, username[k]);
            if (username[k].equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    public boolean isReadyToGame() {
        return isThere("max") && isThere("amy");
    }

    public void show(String title) {
        System.out.println("----- " + title+" -----");
        show();
    }
//  

    public void show() {
//        System.out.println("   -----------------");
//            System.out.printf("  %3d %s%n", k, username[k]);
        System.out.printf("\n  %s %s %s%n", "rec", "  username  ", "  state  ");
        System.out.printf("  %s %s %s%n", "===", "============", "============");

        for (int k = 1; k <= MAX_USERS; k++) {
            System.out.printf("  %3d %12s %s%n", k, username[k], state[k]);
            //  username[k]="---";
        }
//        System.out.println("   -----------------");

    }
}
