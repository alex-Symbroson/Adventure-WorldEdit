package main;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

import java.io.File;
import java.util.logging.Logger;

public class Controller
{
    // debug logger
    private static final Logger logger = Logger.getLogger(Controller.class.getName());
    @FXML
    private Pane game;
    @FXML
    private TreeView<File> fileTreeView;

    // initialize tree view with cell factory
    public void initialize()
    {
        fileTreeView.setCellFactory(ftv -> new TreeCell<File>()
        {
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
        }
        else
            logger.info("add folder aborted.");
    }

    // add folder to treeView
    private void addTreeFolder(File... rootItems)
    {
        TreeItem<File> root = fileTreeView.getRoot();
        if (root == null)
        {
            root = new TreeItem<>();
            fileTreeView.setRoot(root);
            fileTreeView.setShowRoot(false);
        }

        // add items to treeView
        ObservableList<TreeItem<File>> children = root.getChildren();
        for (File item : rootItems)
            children.add(new FileTreeItem(item));
    }

    // confirm and quit app
    @FXML
    private void quit(ActionEvent event)
    {
        Alert dlg = new Alert(Alert.AlertType.CONFIRMATION);
        dlg.setTitle("Exit confirmation");
        dlg.setHeaderText("Exit WorldEdit?");

        dlg.setOnCloseRequest(e -> {
            if (dlg.getResult() == ButtonType.OK)
            {
                logger.info("closing");
                Platform.exit();
            }
        });
        dlg.show();
    }

    @FXML
    private void startPreview(ActionEvent event)
    {
        new Renderer().start(game);
    }
}
