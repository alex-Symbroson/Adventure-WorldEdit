package main;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.*;

import org.codehaus.commons.compiler.CompileException;
import org.codehaus.janino.ScriptEvaluator;
import org.json.JSONObject;

import javafx.event.*;
import javafx.scene.image.Image;

public class Loader
{
    static Image loadImage(String url)
    {
        Image img = null;

        try
        {
            img = new Image(new FileInputStream(url));
            if (img.errorProperty().get()) img.getException().printStackTrace();
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

        return img;
    }

    static JSONObject loadJson(String path)
    {
        byte[] data = new byte[0];

        try
        {
            data = Files.readAllBytes(Paths.get(path));
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        return new JSONObject(new String(data));
    }

    public static ScriptEvaluator loadScript(String script, Sprite obj)
    {
        ScriptEvaluator se = new ScriptEvaluator();
        se.setNoPermissions();
        se.setParameters(new String[] { "self" }, new Class[] { Sprite.class });

        try
        {
            se.cook(script);
        } catch (CompileException e)
        {
            e.printStackTrace();
        }

        return se;
    }

    static EventHandler<Event> loadEvent(String script, Sprite obj)
    {
        ScriptEvaluator se = loadScript(script, obj);

        return e ->
        {
            try
            {
                se.evaluate(new Object[] { obj });
            } catch (InvocationTargetException ex)
            {
                ex.printStackTrace();
            }
        };
    }
}
