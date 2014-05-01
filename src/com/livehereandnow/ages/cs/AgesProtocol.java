package com.livehereandnow.ages.cs;

/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
import java.io.*;
import java.net.*;

public class AgesProtocol {

//    Server ages;
    private static final int WAITING = 0;
    private static final int HELLO = 10;
    private static final int PLAYER_NAME = 20;
    private static final int GAMING = 25;

    private static final int GAME_OVER = 30;
//    private static final int ANOTHER = 40;

//    private static final int NUMJOKES = 50;
    private int state = WAITING;
    private int currentJoke = 0;
    String playerName = "---";

    private String[] clues = {"Turnip", "Little Old Lady", "Atch", "Who", "Who"};
    private String[] answers = {"Turnip the heat, it's cold in here!",
        "I didn't know you could yodel!",
        "Bless you!",
        "Is there an owl in here?",
        "Is there an echo in here?"};

    public AgesProtocol(String threadName) {
//        this.ages = new Server();
//        System.out.println(" protocol: threadName is "+threadName);
    }

    public String processInput(String input) {
        String output = null;
        switch (state) {
            case WAITING:
                output = "[Ages Server] May I help you?(y/n)";
                state = HELLO;
                break;
            case HELLO:
                if (input.equalsIgnoreCase("y")) {
                    output = "What's your name?";
                    state = PLAYER_NAME;
                } else {
                    output = "[Ages Server] May I help you?(y/n)";

                    state = WAITING;
                    //return "NO";
//                    output = "We cannot continue if you don't say 'y'";
                }
                break;

            case PLAYER_NAME:
                if (input.equalsIgnoreCase("max") || input.equalsIgnoreCase("amy")) {
                    output = "Welcome to our game! Please start to send your command!";
                    state = GAMING;
                } else {
                    output = "Sorry, for Max/Amy only! What's your name?";

//                    state = HELLO;
                    // output = "This is  *** Through the Ages *** server. May I help you?";
                }
                break;

            case GAMING:
                if (!input.equalsIgnoreCase("end-game")) {
                    output = "Your command is " + input;   // 90% further development is here!

                } else {
                    state = GAME_OVER;
                    output = "Are you sure you want to end this game?";
// output = "This is  *** Through the Ages *** server. May I help you?";
                }
                break;

            case GAME_OVER:
                if (input.equalsIgnoreCase("y")) {
                    output = "Game is over! ";
                    state = WAITING;

                } else {
                    state = GAMING;
                    output = "Your command?";
// output = "This is  *** Through the Ages *** server. May I help you?";
                }
                break;

            default:
                output = "FURTHER DEV HERE ...";

        }

        return output;
    }
}
