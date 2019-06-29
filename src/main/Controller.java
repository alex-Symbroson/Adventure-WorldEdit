package main;

import java.io.File;
import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class Controller
{
    @FXML
    private TreeView<File> fileTreeView;
    @FXML
    private SplitPane splitPane;

    private static final Logger logger = Logger.getLogger(Controller.class.getName());

    public void initialize()
    {
	fileTreeView.setCellFactory(tview -> new TreeCell<File>() {
	    @Override
	    protected void updateItem(File item, boolean empty)
	    {
		super.updateItem(item, empty);

		if (empty || item == null)
		    this.setText("");
		else
		    this.setText(item.getName());
	    }
	});
    }

    @FXML
    private void loadNewFile(ActionEvent event)
    {
	logger.info("load new file");
    }

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

    private void addTreeFolder(File... rootItems)
    {
	TreeItem<File> root = fileTreeView.getRoot();
	if (root == null)
	{
	    root = new TreeItem<>();
	    fileTreeView.setRoot(root);
	    fileTreeView.setShowRoot(false);
	}
	ObservableList<TreeItem<File>> children = root.getChildren();

	for (File item : rootItems)
	    children.add(new FileTreeItem(item));
    }

    @FXML
    private void quit(ActionEvent event)
    {
	Platform.exit();
    }
}
