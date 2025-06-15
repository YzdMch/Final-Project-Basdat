package Akademik.Menu;

import Akademik.Dao.*;
import Akademik.Model.*;

import java.util.Scanner;

public class AdminMenu {
    public static void menu() {
        Scanner input = new Scanner(System.in);
        int pilihan;

        do {
            System.out.println("\n=== MENU ADMIN ===");
            System.out.println("1. Kelola Dosen");
            System.out.println("2. Kelola Mahasiswa");
            System.out.println("3. Kelola Mata Kuliah");
            System.out.println("4. Kelola Nilai");
            System.out.println("5. Kembali ke Menu Utama");
            System.out.print("Pilih menu: ");
            pilihan = Integer.parseInt(input.nextLine());

            switch (pilihan) {
                case 1:
                    menuDosen();
                    break;
                case 2:
                    menuMahasiswa();
                    break;
                case 3:
                    menuMK();
                    break;
                case 4:
                    menuNilai();
                    break;
                case 5:
                    System.out.println("Kembali ke menu utama...");
                    break;
                default:
                    System.out.println("Pilihan tidak valid.");
            }

        } while (pilihan != 5);
    }

    public static void menuDosen() {
        Scanner input = new Scanner(System.in);
        int pilihan;

        do {
            System.out.println("\n=== KELOLA DOSEN ===");
            System.out.println("1. Tambah Dosen");
            System.out.println("2. Lihat Dosen");
            System.out.println("3. Ubah Nama Dosen");
            System.out.println("4. Hapus Dosen");
            System.out.println("5. Kembali");
            System.out.print("Pilih menu: ");
            pilihan = Integer.parseInt(input.nextLine());

            switch (pilihan) {
                case 1:
                    System.out.print("Masukkan NIDN: ");
                    String nidn = input.nextLine();
                    System.out.print("Masukkan Nama Dosen: ");
                    String nama = input.nextLine();
                    Dosen d = new Dosen(nidn, nama);
                    DosenDao.tambahDosen(d);
                    break;
                case 2:
                    DosenDao.tampilkanDosen();
                    break;
                case 3:
                    System.out.print("Masukkan NIDN yang ingin diubah: ");
                    String nidnUbah = input.nextLine();
                    System.out.print("Masukkan Nama Dosen baru: ");
                    String namaBaru = input.nextLine();
                    Dosen dUbah = new Dosen(nidnUbah, namaBaru);
                    DosenDao.updateDosen(dUbah);
                    break;
                case 4:
                    System.out.print("Masukkan NIDN yang ingin dihapus: ");
                    String nidnHapus = input.nextLine();
                    DosenDao.hapusDosen(nidnHapus);
                    break;
                case 5:
                    System.out.println("Kembali ke menu admin...");
                    break;
                default:
                    System.out.println("Pilihan tidak valid.");
            }

        } while (pilihan != 5);
    }
    
    public static void menuMahasiswa() {
        Scanner input = new Scanner(System.in);
        int pilihan;

        do {
            System.out.println("\n=== KELOLA MAHASISWA ===");
            System.out.println("1. Tambah Mahasiswa");
            System.out.println("2. Lihat Mahasiswa");
            System.out.println("3. Ubah Mahasiswa");
            System.out.println("4. Hapus Mahasiswa");
            System.out.println("5. Kembali");
            System.out.print("Pilih menu: ");
            pilihan = Integer.parseInt(input.nextLine());

            switch (pilihan) {
                case 1:
                    System.out.print("NPM: ");
                    String npm = input.nextLine();
                    System.out.print("Nama: ");
                    String nama = input.nextLine();
                    System.out.print("Prodi: ");
                    String prodi = input.nextLine();
                    System.out.print("Semester: ");
                    int semester = Integer.parseInt(input.nextLine());
                    System.out.print("NIDN Dosen Wali: ");
                    String dosen = input.nextLine();
                    Mahasiswa m = new Mahasiswa(npm, nama, prodi, semester, dosen);
                    MahasiswaDao.tambahMahasiswa(m);
                    break;
                case 2:
                    MahasiswaDao.tampilkanMahasiswa();
                    break;
                case 3:
                    System.out.print("NPM yang ingin diubah: ");
                    String npmUbah = input.nextLine();
                    System.out.print("Nama baru: ");
                    String namaBaru = input.nextLine();
                    System.out.print("Prodi baru: ");
                    String prodiBaru = input.nextLine();
                    System.out.print("Semester baru: ");
                    int semBaru = Integer.parseInt(input.nextLine());
                    System.out.print("NIDN Dosen Wali baru: ");
                    String dosBaru = input.nextLine();
                    Mahasiswa mBaru = new Mahasiswa(npmUbah, namaBaru, prodiBaru, semBaru, dosBaru);
                    MahasiswaDao.updateMahasiswa(mBaru);
                    break;
                case 4:
                    System.out.print("NPM yang ingin dihapus: ");
                    String npmHapus = input.nextLine();
                    MahasiswaDao.hapusMahasiswa(npmHapus);
                    break;
                case 5:
                    System.out.println("Kembali...");
                    break;
                default:
                    System.out.println("Pilihan tidak valid.");
            }

        } while (pilihan != 5);
    }
    
    public static void menuMK() {
        Scanner input = new Scanner(System.in);
        int pilihan;

        do {
            System.out.println("\n=== KELOLA MATA KULIAH ===");
            System.out.println("1. Tambah Mata Kuliah");
            System.out.println("2. Lihat Mata Kuliah");
            System.out.println("3. Ubah Mata Kuliah");
            System.out.println("4. Hapus Mata Kuliah");
            System.out.println("5. Kembali");
            System.out.print("Pilih menu: ");
            pilihan = Integer.parseInt(input.nextLine());

            switch (pilihan) {
                case 1:
                    System.out.print("Kode MK: ");
                    String kode = input.nextLine();
                    System.out.print("Nama MK: ");
                    String nama = input.nextLine();
                    System.out.print("SKS: ");
                    int sks = Integer.parseInt(input.nextLine());
                    System.out.print("Kelas: ");
                    String kelas = input.nextLine();
                    System.out.print("NIDN Dosen: ");
                    String dosen = input.nextLine();
                    MataKuliah mk = new MataKuliah(kode, nama, sks, kelas, dosen);
                    MataKuliahDao.tambahMK(mk);
                    break;
                case 2:
                    MataKuliahDao.tampilkanMK();
                    break;
                case 3:
                    System.out.print("Kode MK yang ingin diubah: ");
                    String kodeUbah = input.nextLine();
                    System.out.print("Nama baru: ");
                    String namaUbah = input.nextLine();
                    System.out.print("SKS baru: ");
                    int sksUbah = Integer.parseInt(input.nextLine());
                    System.out.print("Kelas baru: ");
                    String kelasUbah = input.nextLine();
                    System.out.print("NIDN Dosen baru: ");
                    String dosenUbah = input.nextLine();
                    MataKuliah mkUbah = new MataKuliah(kodeUbah, namaUbah, sksUbah, kelasUbah, dosenUbah);
                    MataKuliahDao.updateMK(mkUbah);
                    break;
                case 4:
                    System.out.print("Kode MK yang ingin dihapus: ");
                    String kodeHapus = input.nextLine();
                    MataKuliahDao.hapusMK(kodeHapus);
                    break;
                case 5:
                    System.out.println("Kembali ke menu admin...");
                    break;
            default:
                    System.out.println("Pilihan tidak valid.");
            }

        } while (pilihan != 5);
    }

    public static void menuNilai() {
        Scanner input = new Scanner(System.in);
        int pilihan;

        do {
            System.out.println("\n=== KELOLA NILAI MAHASISWA ===");
            System.out.println("1. Tambah Nilai");
            System.out.println("2. Lihat Nilai");
            System.out.println("3. Ubah Nilai");
            System.out.println("4. Hapus Nilai");
            System.out.println("5. Kembali");
            System.out.print("Pilih menu: ");
            pilihan = Integer.parseInt(input.nextLine());

            switch (pilihan) {
                case 1:
                    NilaiDao.inputNilai(); // sudah dibuat di DosenMenu
                    break;
                case 2:
                    NilaiDao.lihatNilai();
                    break;
                case 3:
                    NilaiDao.updateNilai(); // sudah dibuat di DosenMenu
                    break;
                case 4:
                    System.out.print("Masukkan ID Nilai yang ingin dihapus: ");
                    int id = Integer.parseInt(input.nextLine());
                    NilaiDao.hapusNilai(id);
                    break;
                case 5:
                    System.out.println("Kembali ke menu admin...");
                    break;
                default:
                    System.out.println("Pilihan tidak valid.");
            }
        } while (pilihan != 5);
    }
}
