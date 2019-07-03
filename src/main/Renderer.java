package main;

import javafx.animation.AnimationTimer;
import javafx.scene.layout.Pane;

public class Renderer
{
    public boolean running = true;

    private Pane game;

    Sprite bunny;

    // initialize game
    private void init()
    {
        // load sprite demo
        bunny = new Sprite("res/bunny.json");
        game.getChildren().add(bunny);
    }

    // start render loop
    void start(Pane target)
    {
        game = target;
        init();

        // render loop
        new AnimationTimer() {
            @Override
            public void handle(long now)
            {
            }
        };
    }
}
