/**
 * Demonstrate the directory and file sizes.
 */
public abstract class FileSystemSizes {

    /**
     * Demonstrate the process of creating a few directories and files
     * and making a file system size tree.
     * @param args main args
     */
    public static void main(String[] args) {

        Dir root = new Dir("root");
        Dir child1 = new Dir("dir1");
        Dir child2 = new Dir("dir2");
        File child3 = new File("f1.txt", 10);
        Dir grandchild1 = new Dir("dirdir");
        File grandchild2 = new File("g1.txt", 20);

        root.addNode(child1);
        root.addNode(child2);
        root.addNode(child3);
        child2.addNode(grandchild1);
        child2.addNode(grandchild2);

        System.out.println("--------------------------------");
        printTree(root, "");
        File newLeaf = new File("leaf.txt", 100);
        grandchild1.addNode(newLeaf);

        System.out.println("--------------------------------");
        printTree(root, "");
        newLeaf.setByteSize(200);

        System.out.println("--------------------------------");
        printTree(root, "");
    }

    /**
     * Print the names and sizes of all nodes in a file system size tree.
     * @param node the root of the current subtree
     * @param indent the prefix of spaces to print before node
     */
    private static void printTree(Node node, String indent) {
        System.out.println(
                String.format("%s %s %d bytes",
                        indent, node.getName(), node.getByteSize()));

        if (node instanceof Dir) {
            Dir d = (Dir) node;
            for (Node child : d.getChildren()) {
                printTree(child, indent + "  ");
            }
        }
    }
}