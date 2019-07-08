package main;

import java.util.function.BiConsumer;
import java.util.prefs.*;

import javafx.application.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.stage.Stage;

public class Main extends Application
{
    // note: using public static (non-final) variables is discouraged
    public static Stage stage;
    public static Preferences prefs;

    @Override
    public void init()
    {
        prefs = Preferences.userRoot().node("ga_worledit");

        // set value if absent
        BiConsumer<String, String> put_def = (k, v) ->
        {
            if (prefs.get(k, v).equals(v)) prefs.put(k, v);
        };
        put_def.accept("Show_Exit_Dialog", "true");
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        stage = primaryStage;

        // load GUI from xml
        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));

        primaryStage.setTitle("Adventure WorldEdit");
        setUserAgentStylesheet(STYLESHEET_MODENA);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
