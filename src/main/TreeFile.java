package main;

import java.io.File;

// class with overridden File.toString method for the file tree view
public class TreeFile extends File
{

	private static final long serialVersionUID = 652111671130380615L;

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