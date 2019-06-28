package main;

import java.io.File;

// class with overridden File.toString method for the file tree view
public class TreeFile extends File
{
    public TreeFile(String pathname)
    {
        super(pathname);
    }

    public TreeFile(File f)
    {
        super(f.getPath());
    }

    @Override
    public String toString()
    {
        return this.getName();
    }
}