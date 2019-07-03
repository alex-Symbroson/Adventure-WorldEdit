package main;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;

public class Renderer
{
    public boolean running = true;

    private Pane game;
    private GraphicsContext ctx;

    Sprite bunny;

    // initialize game
    private void init()
    {
        // load sprite demo
        Level level01 = new Level("res/level/level01.json", game);
        game.getChildren().add(level01);
    }

    // start render loop
    void start(Pane target)
    {
        game = target;
        init();

        // render loop
        new AnimationTimer()
        {
            @Override
            public void handle(long now) {}
        };//.start();
    }
}
