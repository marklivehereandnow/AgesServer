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

    int WAITING = 100;
    int ASK_PASSWORD = 200;
    int CHECK_PASSWORD = 220;
    int CHECK_PLAYERS = 300;
    int WAITING_FOR_PLAYER = 400;
    int GAMING = 500;
    int GUEST = 600;
    int GAME_OVER = 999;
}
