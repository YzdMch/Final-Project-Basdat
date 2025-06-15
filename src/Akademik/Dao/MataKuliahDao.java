package Akademik.Dao;

import Akademik.DBConnection;
import Akademik.Model.MataKuliah;

import java.sql.*;

public class MataKuliahDao {
    public static void tambahMK(MataKuliah mk) {
        Connection conn = DBConnection.connect();
        try {
            String sql = "INSERT INTO mata_kuliah (Kode_MK, Nama_MK, SKS, Kelas, FK_Dosen) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, mk.getKode());
            ps.setString(2, mk.getNama());
            ps.setInt(3, mk.getSks());
            ps.setString(4, mk.getKelas());
            ps.setString(5, mk.getFkDosen());
            ps.executeUpdate();
            System.out.println("✅ Mata kuliah berhasil ditambahkan.");
        } catch (SQLException e) {
            System.out.println("❌ Gagal tambah MK: " + e.getMessage());
        }
    }

    public static void tampilkanMK() {
        Connection conn = DBConnection.connect();
        try {
            String sql = "SELECT * FROM mata_kuliah";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            System.out.println("\n=== DAFTAR MATA KULIAH ===");
            while (rs.next()) {
                System.out.println(rs.getString("Kode_MK") + " - " + rs.getString("Nama_MK") +
                        " | SKS: " + rs.getInt("SKS") +
                        " | Kelas: " + rs.getString("Kelas") +
                        " | Dosen: " + rs.getString("FK_Dosen"));
            }
        } catch (SQLException e) {
            System.out.println("❌ Gagal ambil data MK: " + e.getMessage());
        }
    }

    public static void updateMK(MataKuliah mk) {
        Connection conn = DBConnection.connect();
        try {
            String sql = "UPDATE mata_kuliah SET Nama_MK=?, SKS=?, Kelas=?, FK_Dosen=? WHERE Kode_MK=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, mk.getNama());
            ps.setInt(2, mk.getSks());
            ps.setString(3, mk.getKelas());
            ps.setString(4, mk.getFkDosen());
            ps.setString(5, mk.getKode());
            int hasil = ps.executeUpdate();

            if (hasil > 0) {
                System.out.println("✅ Mata kuliah berhasil diubah.");
            } else {
                System.out.println("❌ Kode MK tidak ditemukan.");
            }
        } catch (SQLException e) {
            System.out.println("❌ Gagal update MK: " + e.getMessage());
        }
    }

    public static void hapusMK(String kode) {
        Connection conn = DBConnection.connect();
        try {
            String sql = "DELETE FROM mata_kuliah WHERE Kode_MK=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, kode);
            int hasil = ps.executeUpdate();

            if (hasil > 0) {
                System.out.println("✅ MK berhasil dihapus.");
            } else {
                System.out.println("❌ Kode MK tidak ditemukan.");
            }
        } catch (SQLException e) {
            System.out.println("❌ Gagal hapus MK: " + e.getMessage());
        }
    }
}
