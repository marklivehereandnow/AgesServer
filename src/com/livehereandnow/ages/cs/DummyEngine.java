/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.livehereandnow.ages.cs;

import com.livehereandnow.ages.engine.Engine;
import com.livehereandnow.ages.exception.AgesException;
import java.io.IOException;

/**
 *
 * @author mark
 */
public class DummyEngine implements GameBasic {

    private static DummyEngine instance;
    private Engine engine;
    public synchronized static DummyEngine getInstance() throws AgesException {
        if (instance == null) {
            instance = new DummyEngine();
        }
        return instance;
    }

    String activePlayer = null;

    public DummyEngine() throws AgesException {
        // activePlayer="max";
          engine=new Engine();
      
    }

    @Override
    public String getActivePlayer() {
        return activePlayer;
    }

    @Override
    public void setActivePlayer(String player) throws IOException, AgesException {
        activePlayer = player;
//        engine.doCmd("change-turn");
    }

   

    @Override
    public String doUserCmd(String user, String cmd) throws IOException, AgesException {
        return engine.doUserCmd(user, cmd);
    }


}
