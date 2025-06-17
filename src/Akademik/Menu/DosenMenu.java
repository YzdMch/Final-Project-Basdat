package Akademik.Menu;

import Akademik.Dao.*;

import java.util.Scanner;

public class DosenMenu {
    public static void menu() {
        Scanner input = new Scanner(System.in);
        int pilihan;

        do {
            System.out.println("\n=== MENU DOSEN ===");
            System.out.println("1. Input Nilai Mahasiswa");
            System.out.println("2. Update Nilai Mahasiswa");
            System.out.println("3. Proses Permintaan Revisi");
            System.out.println("4. Kembali ke Menu Utama");
            System.out.print("Pilih menu: ");
            pilihan = Integer.parseInt(input.nextLine());

            switch (pilihan) {
                case 1:
                    NilaiDao.inputNilai();
                    break;
                case 2:
                    NilaiDao.updateNilai();
                    break;
                case 3:
                    RevisiDao.prosesRevisi();
                    break;
                case 4:
                    System.out.println("Kembali ke menu utama...");
                    break;
                default:
                    System.out.println("Pilihan tidak valid.");
            }

        } while (pilihan != 4);
    }
}
