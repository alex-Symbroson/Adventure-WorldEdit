package main;

import org.codehaus.janino.ScriptEvaluator;
import org.json.JSONObject;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

class Sprite extends ImageView
{
    public Image img;

    ScriptEvaluator onClick;

    Sprite(JSONObject sprite)
    {
        // load sprite image
        img = Loader.loadImage(sprite.getString("src"));
        setImage(img);

        // apply extra properties
        if (sprite.has("x"))
            setX(sprite.getInt("x"));
        if (sprite.has("y"))
            setY(sprite.getInt("y"));

        if (sprite.has("w"))
            setFitWidth(sprite.getInt("w"));
        if (sprite.has("h"))
            setFitHeight(sprite.getInt("h"));

        if (sprite.has("visible"))
            setVisible(sprite.getBoolean("visible"));
        if (sprite.has("onClick"))
            setOnMouseClicked(Loader.loadEvent(sprite.getString("onClick"), this));
    }

    // Create Sprite from json file
    Sprite(String path)
    {
        this(Loader.loadJson(path));
    }
}
