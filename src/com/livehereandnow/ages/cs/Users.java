package com.livehereandnow.ages.cs;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author mark
 */
public class Users {
    private static int counter=0;
    private static String[] player=new String[10];
    {
        player[0]="server";
        player[1]="---";
        player[2]="---";
    }
    public static void setPlayer(int k, String name){
        player[k]=name;
        counter++;
    }
    public static String getStatus(){
        StringBuilder sb=new StringBuilder();
        sb.append("\nPlayer 1 is "+player[1]);
        sb.append("\nPlayer 2 is "+player[2]);
        if (counter==2){
        sb.append("\n === Two players show up, to start our game === ");
            
        }
        return sb.toString();
    }
    
}
