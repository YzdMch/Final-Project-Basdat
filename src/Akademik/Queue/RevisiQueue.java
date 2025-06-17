package Akademik.Queue;

public class RevisiQueue {
    private Node front, rear;

    public void enqueue(String npm, String kodeMK, String catatan) {
        Node baru = new Node(npm, kodeMK, catatan);
        if (rear == null) {
            front = rear = baru;
        } else {
            rear.next = baru;
            rear = baru;
        }
    }

    public Node dequeue() {
        if (front == null) return null;
        Node temp = front;
        front = front.next;
        if (front == null) rear = null;
        return temp;
    }

    public Node peek() {
        return front;
    }

    public boolean isEmpty() {
        return front == null;
    }

    public void printQueue() {
        Node temp = front;
        int no = 1;
        System.out.println("\n=== DAFTAR ANTRIAN REVISI NILAI ===");
        while (temp != null) {
            System.out.println(no++ + ". NPM: " + temp.npm + " | MK: " + temp.kodeMK + " | Catatan: " + temp.catatan);
            temp = temp.next;
        }
        if (no == 1) System.out.println("(Kosong)");
    }
}
