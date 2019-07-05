package main;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

public class FileTreeItem extends TreeItem<File>
{

    private boolean isFirstChild = true;
    private boolean isLeaf;

    public FileTreeItem(File f)
    {
        super(f);
        isLeaf = f.isFile();
    }

    @Override
    public ObservableList<TreeItem<File>> getChildren()
    {
        // create children list on first load
        if (isFirstChild)
        {
            isFirstChild = false;
            super.getChildren().setAll(buildChildren(this));
        }
        return super.getChildren();
    }

    public void update()
    {
        super.getChildren().setAll(buildChildren(this));
    }

    @Override
    public boolean isLeaf()
    {
        return isLeaf;
    }

    // Recursively create children
    private List<TreeItem<File>> buildChildren(TreeItem<File> TreeItem)
    {
        File f = TreeItem.getValue();

        if (f != null && f.isDirectory())
        {
            // list visible files (by filtering files not starting with ".")
            File[] files = f.listFiles((file, name) -> !name.startsWith("."));

            // return sorted list of children
            if (files != null)
            {
                Arrays.sort(files);
                List<TreeItem<File>> children = new ArrayList<>();

                for (File childFile : files)
                    children.add(new FileTreeItem(childFile));

                return children;
            }
        }

        return Collections.emptyList();
    }
}