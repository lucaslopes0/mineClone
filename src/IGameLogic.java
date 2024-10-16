public interface IGameLogic {
    void init() throws Exception;
    void input(InputHandler input);
    void update(float interval);
    void render(Window window);
    void cleanup();
}
