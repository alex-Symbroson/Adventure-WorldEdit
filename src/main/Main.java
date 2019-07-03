package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.function.BiConsumer;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

public class Main extends Application
{
    // note: using public static variables is discouraged
    public static Stage stage;
    public static Preferences prefs;

    @Override
    public void init()
    {
        prefs = Preferences.userRoot().node("ga_worledit");

        // set value if absent
        BiConsumer<String, String> put_def = (k, v) ->
        {
            if (prefs.get(k, v).equals(v))
                prefs.put(k, v);
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

    public static void main(String[] args) throws BackingStoreException
    {
        launch(args);
    }
}
