public class GameEngine implements Runnable {
    private static final int TARGET_FPS = 60;
    private static final int TARGET_UPS = 30;

    private final Window window;
    private final Thread gameLoopThread;
    private final IGameLogic gameLogic;
    private final Timer timer;
    private final ImageLoader imageLoader;
    private final InputHandler inputHandler;



    //construtor GameEngine
    public GameEngine(String windowTitle, int width, int height, boolean vSync, IGameLogic gameLogic) throws Exception {
        this.gameLoopThread = new Thread(this, "GAME_LOOP_THREAD");
        this.window = new Window(windowTitle, width, height, vSync);
        this.gameLogic = gameLogic;
        this.timer = new Timer();
        this.imageLoader = new ImageLoader(this.window);
        this.inputHandler = new InputHandler(this.window);
    }

    public void start() {
        this.gameLoopThread.start();
    }

    @Override
    public void run() {
        try {
            init();
            gameLoop();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cleanup();
        }
    }

    protected void init() throws Exception {
        this.window.init();
        this.gameLogic.init();
        this.timer.init();
        this.imageLoader.init();
    }

    protected void gameLoop() {
        double elapsedTime;
        float accumulator = 0f;
        float interval = 1f / TARGET_UPS;

        while (!this.window.windowShouldClose()) {
            elapsedTime = this.timer.getElapsedTime();
            accumulator += elapsedTime;

            input();

            while (accumulator >= interval) {
                update(interval);
                accumulator -= interval;
            }
            render();
            if (!this.window.isvSync()) {
                sync();
            }
        }
    }

    protected void input() {
        this.gameLogic.input(this.inputHandler, this.window);
    }

    protected void update(float interval) {
        this.gameLogic.update(interval);
    }

    protected void render() {
        this.gameLogic.render(this.window);
        this.window.update();
    }

    private void sync() {
        float loopSlot = 1f / TARGET_FPS;
        double endTime = this.timer.getLastLoopTime() + loopSlot;
        while (this.timer.getTime() < endTime) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }
    }

    protected void cleanup() {
        this.gameLogic.cleanup();
        this.window.cleanup();
    }
}
