package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application
{
    public static Stage stage;

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
