public class pMain {
    public static void main (String[] args) {
        //new SimplyWIndow().run();
       try {
            boolean vSync = true;
            IGameLogic gameLogic = new GameFicticio();
            GameEngine gameEng = new GameEngine("Game", 600, 480, vSync, gameLogic);
            gameEng.start();
       } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
       }

    }
}
