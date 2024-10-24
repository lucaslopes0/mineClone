package org.engine;

import org.graphics.Mesh;
import org.graphics.Vertex;
import org.maths.Vector;
import org.view.*;
import org.utils.*;
import org.controller.*;


public class GameEngineModel implements Runnable {
    private static final int TARGET_FPS = 60;
    private static final int TARGET_UPS = 30;

    private final Window window;
    private final Thread gameLoopThread;
    private final IGameLogic gameLogic;
    private final Timer timer;
    private final ImageLoader imageLoader;
    private final InputController inputHandler;
    private Renderer renderer;
    private Mesh mesh;

    //construtor Model.GameEngine
    public GameEngineModel(String windowTitle, int width, int height, boolean vSync, IGameLogic gameLogic) throws Exception {
        this.gameLoopThread = new Thread(this, "GAME_LOOP_THREAD");
        this.window = new Window(windowTitle, width, height, vSync);
        this.gameLogic = gameLogic;
        this.timer = new Timer();
        this.imageLoader = new ImageLoader(this.window);
        this.inputHandler = new InputController(this.window);
        this.renderer = new Renderer();
        this.mesh = new Mesh(new Vertex[]{
                new Vertex(new Vector(-0.5f,  0.5f, 0.0f)),
                new Vertex(new Vector( 0.5f,  0.5f, 0.0f)),
                new Vertex(new Vector( 0.5f, -0.5f, 0.0f)),
                new Vertex(new Vector(-0.5f, -0.5f, 0.0f))
        }, new int[]{
                0,1,2,
                0,3,2,
        });
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
        this.mesh.create();
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
        this.renderer.render(this.window);
        this.renderer.renderMesh(this.mesh);
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
