import java.util.*;

/**
 * A node in a file system tree. Nodes contain the name of a directory
 * or file, and also the size of all the directories and files contained
 * in that directory.
 */
abstract class Node extends Observable implements Observer {

    /**
     * The name of the directory or file.
     */
    private String name;
    /**
     * The size of the node and all its children, in bytes.
     */
    private int byteSize;

    /**
     * A new Node representing a file or directory name of size byteSize.
     *
     * @param name     the name of the file or directory.
     * @param byteSize the size of the file or the directory and all its children.
     */
    public Node(String name, int byteSize) {
        this.name = name;
        this.byteSize = byteSize;
    }

    /**
     * Return the name of the file or directory.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Return the size in bytes of the file or the directory and all its children.
     *
     * @return the size in bytes
     */
    public int getByteSize() {
        return byteSize;
    }

    /**
     * Set the size of this node to byteSize and notify any observers.
     *
     * @param byteSize
     */
    public void setByteSize(int byteSize) {
        int oldSize = this.byteSize;
        this.byteSize = byteSize;
        setChanged();
        notifyObservers(byteSize - oldSize);
    }
}


/**
 * A directory in a file system, including the name of the directory and the
 * number of bytes used by the directory and its children.
 */
class Dir extends Node {

    /**
     * The list of directories contained in this directory.
     */
    private List<Dir> directories = new ArrayList<>();
    /**
     * The list of files contained in this directory.
     */
    private List<File> files = new ArrayList<>();

    public Dir(String name) {
        super(name, 100);
    }

    /**
     * Make info a child of this directory and notify any observers of the
     * size change. Also make this node observe info.
     *
     * @param info the new child
     */
    public void addDir(Dir info) {
        info.addObserver(this);
        directories.add(info);
        setByteSize(getByteSize() + info.getByteSize());
    }

    /**
     * Make info a child of this directory and notify any observers of the
     * size change. Also make this node observe info.
     *
     * @param info the new child
     */
    public void addFile(File info) {
        info.addObserver(this);
        files.add(info);
        setByteSize(getByteSize() + info.getByteSize());
    }

    /**
     * Update the size of this node by sizeDelta, the size difference from
     * this observed child.
     */
    @Override
    public void update(Observable o, Object sizeDelta) {
        setByteSize(getByteSize() + (Integer) sizeDelta);
    }

    /**
     * Return the directories contained in this directory.
     *
     * @return the directories contained in this directory
     */
    public List<Dir> getDirectories() {
        return directories;
    }

    /**
     * Return the files contained in this directory.
     *
     * @return the files contained in this directory
     */
    public List<File> getFiles() {
        return files;
    }
}

/**
 * A directory in a file system, including the number of bytes used
 * by the file.
 */
class File extends Node {

    /**
     * A file named name with size bytes.
     *
     * @param name the name
     * @param size the size in bytes
     */
    public File(String name, int size) {
        super(name, size);
    }

    /**
     * Change the size of a file and update any observers.
     *
     * @param newSize
     */
    public void changeSize(int newSize) {
        setByteSize(newSize);
    }

    @Override
    public void update(Observable o, Object arg) {
        // File objects do not observe any other objects so
        // this should never be reached.
        throw new UnsupportedOperationException();
    }
}
