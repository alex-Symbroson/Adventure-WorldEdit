package main;

import org.json.JSONArray;
import org.json.JSONObject;

import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;

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
        setBackground(new Background(new BackgroundImage(background, BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));

        JSONArray objects = level.getJSONArray("objects");

        for (Object obj : objects)
        {
            getChildren().add(new Sprite((JSONObject) obj));
        }
    }
}
