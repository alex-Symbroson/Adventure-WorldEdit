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
import javafx.scene.layout.Pane;
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
            Alert dlg = new Alert(Alert.AlertType.CONFIRMATION);
            dlg.setTitle("Exit confirmation");
            dlg.setHeaderText("Exit WorldEdit?");

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
}
