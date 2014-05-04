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
public interface State {

    int WAITING = 0;
    int ASK_USERNAME = 10;
//    private static final int STATE_PASSWORD = 15;
    int CHECK_USERNAME = 20;
    int WAITING_FOR_PLAYER = 22;
    int TURN_MAX = 25;
    int TURN_AMY = 26;
    int GAME_OVER = 30;
}
