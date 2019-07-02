package main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

import java.io.File;
import java.util.Arrays;

public class FileTreeItem extends TreeItem<File>
{
    private boolean firstLoad = true;
    private boolean isLeaf;

    // Create new item
    public FileTreeItem(File f)
    {
        super(f);
        isLeaf = f.isFile();
    }

    // return cached children (load on first run)
    @Override
    public ObservableList<TreeItem<File>> getChildren()
    {
        if (firstLoad)
        {
            firstLoad = false;
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
    private ObservableList<TreeItem<File>> buildChildren(TreeItem<File> TreeItem)
    {
        File f = TreeItem.getValue();

        if (f != null && f.isDirectory())
        {
            // list unhidden files (by filtering files not starting with ".")
            File[] files = f.listFiles((file, name) -> !name.startsWith("."));

            // return sorted list of children
            if (files != null)
            {
                Arrays.sort(files);
                ObservableList<TreeItem<File>> children = FXCollections.observableArrayList();

                for (File childFile : files)
                    children.add(new FileTreeItem(childFile));

                return children;
            }
        }

        return FXCollections.emptyObservableList();
    }
}