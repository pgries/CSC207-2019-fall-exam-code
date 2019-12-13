import java.util.*;

/**
 * A node in a file system tree. Nodes contain the name of a directory
 * or file, and also the size of all the directories and files contained
 * in that directory.
 */
abstract class Node extends Observable {

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
    Node(String name, int byteSize) {
        this.name = name;
        this.byteSize = byteSize;
    }

    /**
     * Return the name of the file or directory.
     *
     * @return the name
     */
    String getName() {
        return name;
    }

    /**
     * Return the size in bytes of the file or the directory and all its children.
     *
     * @return the size in bytes
     */
    int getByteSize() {
        return byteSize;
    }

    /**
     * Set the size of this node to byteSize and notify any observers.

     * @param byteSize the new size of this Node
     */
    void setByteSize(int byteSize) {
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
class Dir extends Node  implements Observer {

    /**
     * The list of directories contained in this directory.
     */
    private List<Node> children = new ArrayList<>();

    Dir(String name) {
        super(name, 100);
    }

    /**
     * Make info a child of this directory and notify any observers of the
     * size change. Also make this node observe info.
     *
     * @param node the new child
     */
    void addNode(Node node) {
        node.addObserver(this);
        children.add(node);
        setByteSize(getByteSize() + node.getByteSize());
    }

    /**
     * Make info a child of this directory and notify any observers of the
     * size change. Also make this node observe info.
     *
     * @param nodes is a list of child nodes to add to this directory
     */
    void addNodes(List<Node> nodes) {
        for (Node n : nodes) {
            this.addNode(n);
        }
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
     * Return the children contained in this directory.
     *
     * @return the children contained in this directory
     */
    List<Node> getChildren() {
        return children;
    }
}

/**
 * A directory in a file system, including the number of bytes used
 * by the file.
 */
class File extends Node {

    /**
     * A new Node representing a file or directory name of size byteSize.
     *
     * @param name     the name of the file or directory.
     * @param byteSize the size of the file or the directory and all its children.
     */
    File(String name, int byteSize) {
        super(name, byteSize);
    }
}
