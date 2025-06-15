package Akademik.Dao;

import Akademik.DBConnection;
import Akademik.Model.Mahasiswa;

import java.sql.*;
import java.util.Scanner;

public class MahasiswaDao {
    static Scanner input = new Scanner(System.in);

    public static void kelolaMatkul() {
        Connection conn = DBConnection.connect();
        try {
            System.out.print("Masukkan NPM Anda: ");
            String npm = input.nextLine();

            System.out.println("1. Ambil Mata Kuliah");
            System.out.println("2. Hapus Mata Kuliah");
            System.out.print("Pilih: ");
            int opsi = Integer.parseInt(input.nextLine());

            // Tampilkan semua MK
            ResultSet rsMK = conn.createStatement().executeQuery("SELECT * FROM mata_kuliah");
            System.out.println("\n=== DAFTAR MATA KULIAH ===");
            while (rsMK.next()) {
                System.out.println(rsMK.getString("Kode_MK") + " - " + rsMK.getString("Nama_MK"));
            }

            System.out.print("Masukkan Kode MK: ");
            String kodeMK = input.nextLine();

            if (opsi == 1) {
                // Masukkan nilai kosong (sebagai bukti pengambilan MK)
                String sql = "INSERT INTO nilai (Nilai_uts, Nilai_uas, Nilai_Praktikum, Nilai_Akhir, Nilai_Huruf, Kredit, Bobot, Semester, Tahun_ajar, FK_Matkul, FK_Mahasiswa) " +
                        "VALUES (0, 0, 0, 0, '-', 0, 0, '-', 2024, ?, ?)";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, kodeMK);
                ps.setString(2, npm);
                ps.executeUpdate();
                System.out.println("✅ Mata kuliah berhasil diambil.");

            } else if (opsi == 2) {
                PreparedStatement ps = conn.prepareStatement(
                        "DELETE FROM nilai WHERE FK_Mahasiswa = ? AND FK_Matkul = ?");
                ps.setString(1, npm);
                ps.setString(2, kodeMK);
                int hasil = ps.executeUpdate();

                if (hasil > 0) {
                    System.out.println("✅ Mata kuliah berhasil dihapus.");
                } else {
                    System.out.println("❌ Data tidak ditemukan.");
                }

            } else {
                System.out.println("Pilihan tidak valid.");
            }

        } catch (Exception e) {
            System.out.println("❌ Kesalahan: " + e.getMessage());
        }
    }

    public static void lihatKHS() {
        Connection conn = DBConnection.connect();

        try {
            System.out.print("Masukkan NPM Anda: ");
            String npm = input.nextLine();

            String sql = "SELECT mk.Nama_MK, mk.SKS, n.Nilai_uts, n.Nilai_uas, n.Nilai_praktikum, " +
                         "n.Nilai_akhir, n.Nilai_Huruf, n.Kredit, n.Bobot " +
                         "FROM nilai n JOIN mata_kuliah mk ON n.FK_Matkul = mk.Kode_MK " +
                         "WHERE n.FK_Mahasiswa = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, npm);
            ResultSet rs = ps.executeQuery();

            double totalBobot = 0;
            int totalSKS = 0;

            System.out.println("\n=== KHS MAHASISWA ===");
            while (rs.next()) {
                String mk = rs.getString("Nama_MK");
                int sks = rs.getInt("SKS");
                double akhir = rs.getDouble("Nilai_akhir");
                String huruf = rs.getString("Nilai_Huruf");
                double bobot = rs.getDouble("Bobot");

                totalBobot += bobot;
                totalSKS += sks;

                System.out.println(mk + " | SKS: " + sks + " | Akhir: " + akhir + " | Huruf: " + huruf + " | Bobot: " + bobot);
            }

            if (totalSKS == 0) {
                System.out.println("Belum ada mata kuliah diambil.");
                return;
            }

            double ip = Math.round((totalBobot / totalSKS) * 100.0) / 100.0;
            System.out.println("\nIP Semester: " + ip);

            // Simulasi pengecekan nilai salah
            System.out.print("Apakah ada nilai yang salah? (y/n): ");
            String jawab = input.nextLine();
            if (jawab.equalsIgnoreCase("y")) {
                System.out.println("KHS akan diperbaiki.");
            }

        } catch (Exception e) {
            System.out.println("❌ Gagal melihat KHS: " + e.getMessage());
        }
    }
    
    public static void tambahMahasiswa(Mahasiswa m) {
        Connection conn = DBConnection.connect();
        try {
            String sql = "INSERT INTO mahasiswa (NPM, Nama_Mhs, Prodi, Semester_Aktif, FK_Dosen) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, m.getNpm());
            ps.setString(2, m.getNama());
            ps.setString(3, m.getProdi());
            ps.setInt(4, m.getSemester());
            ps.setString(5, m.getFkDosen());
            ps.executeUpdate();
            System.out.println("✅ Mahasiswa berhasil ditambahkan.");
        } catch (SQLException e) {
            System.out.println("❌ Gagal tambah mahasiswa: " + e.getMessage());
        }
    }

    public static void tampilkanMahasiswa() {
        Connection conn = DBConnection.connect();
        try {
            String sql = "SELECT * FROM mahasiswa";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            System.out.println("\n=== DAFTAR MAHASISWA ===");
            while (rs.next()) {
                System.out.println(rs.getString("NPM") + " - " + rs.getString("Nama_Mhs") +
                        " | Prodi: " + rs.getString("Prodi") +
                        " | Semester: " + rs.getInt("Semester_Aktif") +
                        " | Dosen Wali: " + rs.getString("FK_Dosen"));
            }
        } catch (SQLException e) {
            System.out.println("❌ Gagal ambil data: " + e.getMessage());
        }
    }

    public static void updateMahasiswa(Mahasiswa m) {
        Connection conn = DBConnection.connect();
        try {
            String sql = "UPDATE mahasiswa SET Nama_Mhs=?, Prodi=?, Semester_Aktif=?, FK_Dosen=? WHERE NPM=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, m.getNama());
            ps.setString(2, m.getProdi());
            ps.setInt(3, m.getSemester());
            ps.setString(4, m.getFkDosen());
            ps.setString(5, m.getNpm());
            int hasil = ps.executeUpdate();

            if (hasil > 0) {
                System.out.println("✅ Data mahasiswa berhasil diubah.");
            } else {
                System.out.println("❌ NPM tidak ditemukan.");
            }
        } catch (SQLException e) {
            System.out.println("❌ Gagal update mahasiswa: " + e.getMessage());
        }
    }

    public static void hapusMahasiswa(String npm) {
        Connection conn = DBConnection.connect();
        try {
            String sql = "DELETE FROM mahasiswa WHERE NPM=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, npm);
            int hasil = ps.executeUpdate();

            if (hasil > 0) {
                System.out.println("✅ Mahasiswa berhasil dihapus.");
            } else {
                System.out.println("❌ NPM tidak ditemukan.");
            }
        } catch (SQLException e) {
            System.out.println("❌ Gagal hapus mahasiswa: " + e.getMessage());
        }
    }
}
