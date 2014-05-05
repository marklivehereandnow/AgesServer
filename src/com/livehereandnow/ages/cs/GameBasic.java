/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.livehereandnow.ages.cs;

import com.livehereandnow.ages.exception.AgesException;
import java.io.IOException;

/**
 *
 * @author mark
 */
public interface GameBasic {
    String getActivePlayer() throws IOException, AgesException;
    void setActivePlayer(String player) throws IOException, AgesException;
    String doUserCmd(String player,String cmd) throws IOException, AgesException;
    
}
