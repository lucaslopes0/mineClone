package org.engine;

import org.controller.*;
import org.view.*;

public interface IGameLogic {
    void init() throws Exception;
    void input(InputController input, Window window);
    void update(float interval);
    void render(Window window);
    void cleanup();
}
