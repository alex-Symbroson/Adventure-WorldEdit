package main;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.*;

import java.io.File;


public class Controller
{
    @FXML private TreeView<TreeFile> fileTreeView;
    @FXML private SplitPane splitPane;

    public void initialize()
    {
        //addTreeFolder(System.getenv("HOME"));
    }

    @FXML
    private void loadNewFile(ActionEvent event)
    {
        System.out.println("loadNewFile");
    }

    @FXML
    private void openFile(ActionEvent event)
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().add(new ExtensionFilter("WorldEdit file", "*.json"));
        File selectedFile = fileChooser.showOpenDialog(Main.stage);

        if (selectedFile != null) System.out.println(selectedFile.toString());
        else System.out.println("open file aborted.");
    }

    @FXML
    private void addFolder(ActionEvent event)
    {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Add Project Folder");
        File selectedDirectory = directoryChooser.showDialog(Main.stage);

        if (selectedDirectory != null) {
            System.out.println(selectedDirectory.toString());
            addTreeFolder(selectedDirectory.toString());
        } else System.out.println("add folder aborted.");
    }

    private void addTreeFolder(String... rootItems)
    {
        TreeItem<TreeFile> root = fileTreeView.getRoot();
        if(root == null) {
            root = new TreeItem<> ();
            fileTreeView.setRoot(root);
            fileTreeView.setShowRoot(false);
        }
        ObservableList<TreeItem<TreeFile>> children = root.getChildren();

        for(String item : rootItems)
            children.add(new FileTreeItem(new TreeFile(item)));
    }
    
    @FXML
    private void quit(ActionEvent event)
    {
    	Platform.exit();
    }
}
