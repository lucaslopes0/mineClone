package org.main;

import org.engine.GameEngineModel;
import org.engine.IGameLogic;
import org.view.*;

public class pMain {
    public static void main (String[] args) {
        //new SimplyWIndow().run();

        try {
            boolean vSync = true;
            IGameLogic gameLogic = new GameView();
            GameEngineModel gameEng = new GameEngineModel("Game", 600, 480, vSync, gameLogic);
            gameEng.start();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
