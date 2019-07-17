package main;

import java.io.File;
import java.util.*;
import java.util.logging.Logger;
import java.util.prefs.BackingStoreException;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.text.Font;
import javafx.stage.*;
import javafx.stage.FileChooser.ExtensionFilter;

public class Controller
{
    // debug logger
    private static final Logger logger = Logger.getLogger(Controller.class.getName());
    @FXML
    private Pane game;
    @FXML
    private TreeView<File> fileTreeView;
    @FXML
    private Menu prefsMenu;

    // initialize tree view with cell factory
    public void initialize()
    {
        Main.stage.setOnCloseRequest(e -> quit(e));

        fileTreeView.setCellFactory(ftv -> new TreeCell<File>() {
            // define custom item text
            @Override
            protected void updateItem(File item, boolean empty)
            {
                super.updateItem(item, empty);

                if (empty || item == null)
                    setText("");
                else
                    setText(item.getName());
            }
        });

        String[] keys = {};
        try
        {
            keys = Main.prefs.keys();
        } catch (BackingStoreException e)
        {
            e.printStackTrace();
        }
        for (String key : keys)
        {
            // TODO: add text for text preferences
            CheckBox box = new CheckBox(key.replace('_', ' '));
            box.setAllowIndeterminate(false);
            box.setSelected(Main.prefs.getBoolean(key, false));
            box.setOnAction(e -> Main.prefs.putBoolean(key, box.isSelected()));
            prefsMenu.getItems().add(new CustomMenuItem(box, false));
        }
    }

    @FXML
    private void loadNewFile(ActionEvent event)
    {
        logger.info("load new file");
    }

    // let user select a json file
    @FXML
    private void openFile(ActionEvent event)
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().add(new ExtensionFilter("WorldEdit file", "*.json"));
        File selectedFile = fileChooser.showOpenDialog(Main.stage);

        if (selectedFile != null)
            logger.info("open file " + selectedFile);
        else
            logger.info("open file aborted.");
    }

    // let user select a project folder and add it to workspace
    @FXML
    private void addFolder(ActionEvent event)
    {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Add Project Folder");
        File selectedDirectory = directoryChooser.showDialog(Main.stage);

        if (selectedDirectory != null)
        {
            logger.info("add folder " + selectedDirectory);
            addTreeFolder(selectedDirectory);
        } else
            logger.info("add folder aborted.");
    }

    // add folder to treeView
    private void addTreeFolder(File... folders)
    {
        TreeItem<File> root = fileTreeView.getRoot();
        if (root == null)
        {
            root = new TreeItem<>();
            fileTreeView.setShowRoot(false);
            fileTreeView.setRoot(root);
        }

        // add items to treeView
        ObservableList<TreeItem<File>> children = root.getChildren();
        List<File> folder_list = new ArrayList<>(Arrays.asList(folders));

        // update every folder already contained, add the others
        children.filtered(item -> folder_list.contains(item.getValue())).forEach(item ->
        {
            ((FileTreeItem) item).update();
            folder_list.remove(item.getValue());
        });
        folder_list.forEach(file -> children.add(new FileTreeItem(file)));
    }

    // update project folders
    @FXML
    private void updateFolders(ActionEvent event)
    {
        fileTreeView.getRoot().getChildren().forEach(item -> ((FileTreeItem) item).update());
    }

    // confirm and quit app
    @FXML
    private void quit(Event event)
    {
        boolean quit = true;

        if (Main.prefs.getBoolean("Show_Exit_Dialog", true))
        {
            // two reasons for using ButtonData.NO:
            // has ButtonData.defaultButton = false, inheriting style data from its DialogPane
            // is always to left of ButtonData.CANCEL_CLOSE, but without a big gap (ButtonBar BUTTON_ORDER_ constants)
            Alert dlg = new Alert(AlertType.CONFIRMATION, null, new ButtonType("OK", ButtonData.NO), ButtonType.CANCEL);
            dlg.setTitle("Exit confirmation");
            dlg.setHeaderText("Exit WorldEdit?");
            dlg.getDialogPane().setStyle("-fx-base: #000;");

            dlg.showAndWait();
            if (dlg.getResult() == ButtonType.CANCEL) quit = false;
        }

        if (quit)
        {
            logger.info("closing");
            Platform.exit();
        }
    }

    @FXML
    private void startPreview(ActionEvent event)
    {
        new Renderer().start(game);
    }

    private Label mkLabel(String text, Paint color, Font... font)
    {
        Label label = new Label(text);
        label.setTextFill(color);
        if (font.length > 0) label.setFont(font[0]);
        return label;
    }

    @FXML
    private void showAbout(ActionEvent event)
    {
        Alert alert = new Alert(AlertType.CONFIRMATION, "", new ButtonType("lolzers"),
                new ButtonType("Not funny, dude.", ButtonData.NO));
        alert.setTitle("About");
        alert.setHeaderText(null);
        alert.setGraphic(null);
        alert.getDialogPane().setStyle("-fx-base: #000;");

        GridPane labelpane = new GridPane();
        labelpane.setHgap(10);
        labelpane.setVgap(10);
        labelpane.add(mkLabel("\"I want to make my own level.\" you say.", Color.WHITE), 0, 0);
        labelpane.add(mkLabel("So, you want to make your own level.\nThat used to be hard, but it isn't anymore!",
                Color.AQUA), 0, 1);
        labelpane.add(mkLabel("Ha! That's what she said!", Color.SPRINGGREEN), 0, 2);
        alert.getDialogPane().setContent(labelpane);
        alert.getDialogPane().setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);

        alert.showAndWait();

        labelpane.getChildren().clear();
        alert.getButtonTypes().clear();
        if (alert.getResult().getButtonData() == ButtonData.NO)
        {
            labelpane.add(mkLabel("You are so mean!\nAlex, why are they mean to me?", Color.SPRINGGREEN), 0, 0);
            labelpane.add(mkLabel("Your jokes suck, Richard.", Color.AQUA), 0, 1);
            labelpane.add(mkLabel("...", Color.SPRINGGREEN), 0, 2);
            labelpane.add(mkLabel("Your mom sux.", Color.SPRINGGREEN), 0, 3);
            ButtonType btype = new ButtonType("No she doesn't!");
            alert.getButtonTypes().add(btype);
            Button btn = (Button) alert.getDialogPane().lookupButton(btype);
            btn.setTextFill(Color.AQUA);
            alert.showAndWait();

            labelpane.getChildren().clear();
            Label lbl = mkLabel("Yes she does!", Color.SPRINGGREEN);
            labelpane.add(lbl, 0, 0);
            alert.showAndWait();
            alert.showAndWait();

            btn.setText("Yes she does!");
            alert.showAndWait();

            lbl.setText("No she doesn't!");
            btn.setText("Hah!");
            alert.showAndWait();

            lbl.setText("Dammit.\nYou win this time!");
            alert.getButtonTypes().clear();
            alert.getButtonTypes().add(new ButtonType("Please stop this."));
            alert.showAndWait();
        } else
        {
            labelpane.add(mkLabel("EXACTLY", Color.SPRINGGREEN, Font.font(20)), 0, 0);
            alert.getButtonTypes().add(new ButtonType("OK"));
            alert.showAndWait();
        }
    }
}
