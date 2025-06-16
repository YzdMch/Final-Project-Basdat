package Akademik;

import Akademik.Menu.AdminMenu;
import Akademik.Menu.DosenMenu;
import Akademik.Menu.MahasiswaMenu;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        // TODO code application logic here
        
        Scanner input = new Scanner(System.in);
        int pilihan;

        while (true) {
            System.out.println("\n=== SISTEM KHS AKADEMIK ===");
            System.out.println("1. Admin");
            System.out.println("2. Dosen");
            System.out.println("3. Mahasiswa");
            System.out.println("4. Keluar");
            System.out.print("Pilih peran: ");
            pilihan = Integer.parseInt(input.nextLine());

            switch (pilihan) {
                case 1:
                    AdminMenu.menu();
                    break;
                case 2:
                    DosenMenu.menu();
                    break;
                case 3:
                    MahasiswaMenu.menu();
                    break;
                case 4:
                    System.out.println("Terima kasih telah menggunakan sistem KHS.");
                    System.exit(0);
                default:
                    System.out.println("Pilihan tidak valid.");
            }
        }
    }
}