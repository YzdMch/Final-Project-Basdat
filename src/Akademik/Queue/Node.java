package Akademik.Queue;

public class Node {
    public String npm;
    public String kodeMK;
    public String catatan;
    public Node next;

    public Node(String npm, String kodeMK, String catatan) {
        this.npm = npm;
        this.kodeMK = kodeMK;
        this.catatan = catatan;
        this.next = null;
    }
}
