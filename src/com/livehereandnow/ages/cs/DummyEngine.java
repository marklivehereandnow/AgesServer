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
public class DummyEngine implements GameBasic{
  private static DummyEngine instance;
    public synchronized static DummyEngine getInstance() {
        if (instance == null) {
            instance = new DummyEngine();
        }
        return instance;
    }

    
    String activePlayer=null;
    public DummyEngine(){
       // activePlayer="max";
    }
    
    @Override
    public String getActivePlayer() {
        return activePlayer;
    }

    @Override
    public void setActivePlayer(String player) {
        activePlayer=player;
    }
    
}
