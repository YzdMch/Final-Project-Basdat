package Akademik.Dao;

import Akademik.DBConnection;

import java.sql.*;
import java.util.Scanner;

public class NilaiDao {
    static Scanner input = new Scanner(System.in);

    public static void inputNilai() {
        Connection conn = DBConnection.connect();

        try {
            // Tampilkan daftar mata kuliah
            Statement stmt = conn.createStatement();
            ResultSet rsMK = stmt.executeQuery("SELECT * FROM mata_kuliah");

            System.out.println("\n=== DAFTAR MATA KULIAH ===");
            while (rsMK.next()) {
                System.out.println(rsMK.getString("Kode_MK") + " - " + rsMK.getString("Nama_MK"));
            }

            System.out.print("Masukkan Kode MK: ");
            String kodeMK = input.nextLine();

            // Tampilkan mahasiswa yang mengambil MK tsb
            ResultSet rsMhs = conn.createStatement().executeQuery(
                "SELECT NPM, Nama_Mhs FROM mahasiswa WHERE NPM NOT IN " +
                "(SELECT FK_Mahasiswa FROM nilai WHERE FK_Matkul = '" + kodeMK + "')");

            System.out.println("\n=== Mahasiswa yang BELUM memiliki nilai pada MK tersebut ===");
            while (rsMhs.next()) {
                System.out.println(rsMhs.getString("NPM") + " - " + rsMhs.getString("Nama_Mhs"));
            }

            System.out.print("Masukkan NPM Mahasiswa: ");
            String npm = input.nextLine();
            
            // Cek apakah data sudah ada
            String cekSql = "SELECT * FROM nilai WHERE FK_Mahasiswa = ? AND FK_Matkul = ?";
            PreparedStatement cekPS = conn.prepareStatement(cekSql);
            cekPS.setString(1, npm);
            cekPS.setString(2, kodeMK);
            ResultSet cekRS = cekPS.executeQuery();

            if (cekRS.next()) {
                System.out.println("❌ Mahasiswa ini sudah memiliki nilai untuk mata kuliah tersebut.");
                return;
            }
            
            // Input nilai
            System.out.print("Nilai UTS: ");
            double uts = Double.parseDouble(input.nextLine());

            System.out.print("Nilai UAS: ");
            double uas = Double.parseDouble(input.nextLine());

            System.out.print("Nilai Praktikum: ");
            double prak = Double.parseDouble(input.nextLine());

            double akhir = Math.round((uts * 0.3 + uas * 0.4 + prak * 0.3) * 100.0) / 100.0;
            String huruf = konversiHuruf(akhir);
            double kredit = konversiKredit(huruf);

            // Ambil SKS
            PreparedStatement psSKS = conn.prepareStatement("SELECT SKS FROM mata_kuliah WHERE Kode_MK = ?");
            psSKS.setString(1, kodeMK);
            ResultSet rsSKS = psSKS.executeQuery();
            rsSKS.next();
            int sks = rsSKS.getInt("SKS");
            double bobot = sks * kredit;

            // Input semester dan tahun
            System.out.print("Semester (Ganjil/Genap): ");
            String semester = input.nextLine();

            System.out.print("Tahun Ajar (contoh: 2024): ");
            int tahun = Integer.parseInt(input.nextLine());

            // Insert ke tabel nilai
            PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO nilai (Nilai_uts, Nilai_uas, Nilai_Praktikum, Nilai_Akhir, Nilai_Huruf, Kredit, Bobot, Semester, Tahun_ajar, FK_Matkul, FK_Mahasiswa) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

            ps.setDouble(1, uts);
            ps.setDouble(2, uas);
            ps.setDouble(3, prak);
            ps.setDouble(4, akhir);
            ps.setString(5, huruf);
            ps.setDouble(6, kredit);
            ps.setDouble(7, bobot);
            ps.setString(8, semester);
            ps.setInt(9, tahun);
            ps.setString(10, kodeMK);
            ps.setString(11, npm);

            ps.executeUpdate();
            System.out.println("✅ Nilai berhasil ditambahkan.");

        } catch (Exception e) {
            System.out.println("❌ Gagal input nilai: " + e.getMessage());
        }
    }

    public static void updateNilai() {
        Connection conn = DBConnection.connect();

        try {
            System.out.print("Masukkan Kode MK: ");
            String kodeMK = input.nextLine();

            // Tampilkan mahasiswa yang memiliki nilai pada MK tersebut
            ResultSet rs = conn.createStatement().executeQuery(
                "SELECT n.Id_Nilai, m.Nama_Mhs, m.NPM, n.Nilai_Akhir " +
                "FROM nilai n JOIN mahasiswa m ON n.FK_Mahasiswa = m.NPM " +
                "WHERE FK_Matkul = '" + kodeMK + "'");

            System.out.println("\n=== Daftar Nilai Mahasiswa ===");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("Id_Nilai") + " | NPM: " +
                    rs.getString("NPM") + " - " + rs.getString("Nama_Mhs") +
                    " | Nilai Akhir: " + rs.getDouble("Nilai_Akhir"));
            }

            System.out.print("Masukkan ID Nilai yang ingin diupdate: ");
            int id = Integer.parseInt(input.nextLine());

            // Input ulang nilai
            System.out.print("Nilai UTS baru: ");
            double uts = Double.parseDouble(input.nextLine());

            System.out.print("Nilai UAS baru: ");
            double uas = Double.parseDouble(input.nextLine());

            System.out.print("Nilai Praktikum baru: ");
            double prak = Double.parseDouble(input.nextLine());

            double akhir = Math.round((uts * 0.3 + uas * 0.4 + prak * 0.3) * 100.0) / 100.0;
            String huruf = konversiHuruf(akhir);
            double kredit = konversiKredit(huruf);

            // Ambil SKS berdasarkan ID nilai
            PreparedStatement psSKS = conn.prepareStatement(
                "SELECT mk.SKS FROM nilai n JOIN mata_kuliah mk ON n.FK_Matkul = mk.Kode_MK WHERE n.Id_Nilai = ?");
            psSKS.setInt(1, id);
            ResultSet rsSKS = psSKS.executeQuery();
            rsSKS.next();
            int sks = rsSKS.getInt("SKS");
            double bobot = sks * kredit;

            // Update nilai
            PreparedStatement ps = conn.prepareStatement(
                "UPDATE nilai SET Nilai_uts=?, Nilai_uas=?, Nilai_Praktikum=?, Nilai_Akhir=?, Nilai_Huruf=?, Kredit=?, Bobot=? " +
                        "WHERE Id_Nilai = ?");
            ps.setDouble(1, uts);
            ps.setDouble(2, uas);
            ps.setDouble(3, prak);
            ps.setDouble(4, akhir);
            ps.setString(5, huruf);
            ps.setDouble(6, kredit);
            ps.setDouble(7, bobot);
            ps.setInt(8, id);

            ps.executeUpdate();
            System.out.println("✅ Nilai berhasil diperbarui.");

        } catch (Exception e) {
            System.out.println("❌ Gagal update nilai: " + e.getMessage());
        }
    }

    private static String konversiHuruf(double nilai) {
        if (nilai >= 80) return "A";
        if (nilai >= 75) return "B+";
        if (nilai >= 70) return "B";
        if (nilai >= 65) return "C+";
        if (nilai >= 60) return "C";
        if (nilai >= 50) return "D";
        return "E";
    }

    private static double konversiKredit(String huruf) {
        switch (huruf) {
            case "A": return 4.00;
            case "B+": return 3.50;
            case "B": return 3.00;
            case "C+": return 2.50;
            case "C": return 2.00;
            case "D": return 1.00;
            default: return 0.00;
        }
    }
    
    public static void lihatNilai() {
        Connection conn = DBConnection.connect();
        try {
            String sql = "SELECT n.Id_Nilai, m.Nama_Mhs, mk.Nama_MK, n.Nilai_Akhir, n.Nilai_Huruf, n.Bobot " +
                         "FROM nilai n " +
                         "JOIN mahasiswa m ON n.FK_Mahasiswa = m.NPM " +
                         "JOIN mata_kuliah mk ON n.FK_Matkul = mk.Kode_MK";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            System.out.println("\n=== DATA NILAI ===");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("Id_Nilai") +
                        " | Mahasiswa: " + rs.getString("Nama_Mhs") +
                        " | MK: " + rs.getString("Nama_MK") +
                        " | Akhir: " + rs.getDouble("Nilai_Akhir") +
                        " | Huruf: " + rs.getString("Nilai_Huruf") +
                        " | Bobot: " + rs.getDouble("Bobot"));
            }
        } catch (Exception e) {
            System.out.println("❌ Gagal menampilkan nilai: " + e.getMessage());
        }
    }

    public static void hapusNilai(int id) {
        Connection conn = DBConnection.connect();
        try {
            String sql = "DELETE FROM nilai WHERE Id_Nilai = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            int hasil = ps.executeUpdate();

            if (hasil > 0) {
                System.out.println("✅ Nilai berhasil dihapus.");
            } else {
                System.out.println("❌ ID tidak ditemukan.");
            }
        } catch (Exception e) {
            System.out.println("❌ Gagal hapus nilai: " + e.getMessage());
        }
    }
}
