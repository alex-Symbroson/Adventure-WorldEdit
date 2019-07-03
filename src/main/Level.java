package main;

import javafx.scene.image.Image;
import javafx.scene.layout.*;
import org.json.JSONArray;
import org.json.JSONObject;

public class Level extends Pane
{
    public Image background;

    public Level(String path, Pane game)
    {

        // load level data
        JSONObject level = Loader.loadJson(path);

        // load sprite image
        background = Loader.loadImage(level.getString("background"));

        setPrefWidth(background.getWidth());
        setPrefHeight(background.getHeight());

        // this construction line...
        setBackground(new Background(new BackgroundImage(
            background,
            BackgroundRepeat.NO_REPEAT,
            BackgroundRepeat.NO_REPEAT,
            BackgroundPosition.DEFAULT,
            BackgroundSize.DEFAULT
        )));

        JSONArray objects = level.getJSONArray("objects");

        for (Object obj : objects)
        {
            getChildren().add(new Sprite((JSONObject) obj));
        }
    }
}
