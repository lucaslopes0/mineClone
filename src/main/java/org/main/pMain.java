package org.main;


import org.engine.*;
import org.view.*;

public class pMain {
    public static void main (String[] args) {
        try {
            boolean vSync = true;
            IGameLogic gameLogic = new GameView();
            GameEngine gameEng = new GameEngine("Game", 600, 480, vSync, gameLogic);
            gameEng.start();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
