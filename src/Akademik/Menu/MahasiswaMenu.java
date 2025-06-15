package Akademik.Menu;

import Akademik.Dao.MahasiswaDao;

import java.util.Scanner;

public class MahasiswaMenu {
    public static void menu() {
        Scanner input = new Scanner(System.in);
        int pilihan;

        do {
            System.out.println("\n=== MENU MAHASISWA ===");
            System.out.println("1. Pilih Mata Kuliah (ambil / hapus)");
            System.out.println("2. Lihat KHS");
            System.out.println("3. Kembali ke Menu Utama");
            System.out.print("Pilih menu: ");
            pilihan = Integer.parseInt(input.nextLine());

            switch (pilihan) {
                case 1:
                    MahasiswaDao.kelolaMatkul();
                    break;
                case 2:
                    MahasiswaDao.lihatKHS();
                    break;
                case 3:
                    System.out.println("Kembali ke menu utama...");
                    break;
                default:
                    System.out.println("Pilihan tidak valid.");
            }

        } while (pilihan != 3);
    }
}
