package main;

import java.io.File;
import java.util.Arrays;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

public class FileTreeItem extends TreeItem<TreeFile>
{

    private boolean isFirstChild = true;
    private boolean isLeaf;

    public FileTreeItem(TreeFile f)
    {
        super(f);
        isLeaf = f.isFile();
    }

    @Override
    public ObservableList<TreeItem<TreeFile>> getChildren()
    {
        // create children list on first load
        if (isFirstChild) {
            isFirstChild = false;
            super.getChildren().setAll(buildChildren(this));
        }
        return super.getChildren();
    }

    @Override
    public boolean isLeaf()
    {
        return isLeaf;
    }

    // Recursively create children
    private ObservableList<TreeItem<TreeFile>> buildChildren(TreeItem<TreeFile> TreeItem)
    {
        TreeFile f = TreeItem.getValue();

        if (f != null && f.isDirectory())
        {
            // list unhidden files
            File[] files = f.listFiles((file, name) -> !name.startsWith(".") );

            // return sorted list of children
            if (files != null)
            {
                Arrays.sort(files);
                ObservableList<TreeItem<TreeFile>> children = FXCollections.observableArrayList();
                for (File childFile : files) children.add(new FileTreeItem(new TreeFile(childFile)));
                return children;
            }
        }

        return FXCollections.emptyObservableList();
    }
}