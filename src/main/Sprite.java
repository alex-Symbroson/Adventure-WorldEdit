package main;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

class Sprite extends ImageView
{
    public Image img;

    // Create Sprite from json file
    Sprite(String path)
    {
        try
        {
            // read and load json
            byte[] data = Files.readAllBytes(Paths.get(path));
            JSONObject sprite = new JSONObject(new String(data));

            // load sprite image
            img = new Image(new FileInputStream(sprite.getString("src")));
            if (img.errorProperty().get()) img.getException().printStackTrace();
            setImage(img);

            // apply extra properties
            if (sprite.has("x")) setX(sprite.getInt("x"));
            if (sprite.has("y")) setY(sprite.getInt("y"));

            if (sprite.has("w")) setFitWidth(sprite.getInt("w"));
            if (sprite.has("h")) setFitHeight(sprite.getInt("h"));

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
