package Akademik.Dao;

import java.util.Scanner;
import Akademik.Dao.NilaiDao;
import Akademik.Queue.Node;
import Akademik.Queue.RevisiQueue;

public class RevisiDao {
    static RevisiQueue antrian = new RevisiQueue();
    static Scanner input = new Scanner(System.in);

    public static void tambahPermintaan(String npm, String kodeMK, String catatan) {
        antrian.enqueue(npm, kodeMK, catatan);
        System.out.println("✅ Permintaan revisi berhasil dikirim.");
    }

    public static void prosesRevisi() {
        if (antrian.isEmpty()) {
            System.out.println("🔃 Tidak ada permintaan revisi.");
            return;
        }

        antrian.printQueue();
        System.out.print("Lanjut proses permintaan pertama? (y/n): ");
        String jawaban = input.nextLine();

        if (jawaban.equalsIgnoreCase("y")) {
            Node revisi = antrian.dequeue();
            System.out.println("\n👨‍🏫 Memproses revisi nilai:");
            System.out.println("NPM    : " + revisi.npm);
            System.out.println("Kode MK: " + revisi.kodeMK);
            System.out.println("Catatan: " + revisi.catatan);

            // Langsung ke menu update nilai
            NilaiDao.updateNilai();
        }
    }
}
