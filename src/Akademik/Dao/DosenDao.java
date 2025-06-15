package Akademik.Dao;

import Akademik.DBConnection;
import Akademik.Model.Dosen;

import java.sql.*;

public class DosenDao {
    public static void tambahDosen(Dosen d) {
        Connection conn = DBConnection.connect();
        try {
            String sql = "INSERT INTO dosen (NIDN, Nama_Dsn) VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, d.getNidn());
            stmt.setString(2, d.getNama());
            stmt.executeUpdate();
            System.out.println("Dosen berhasil ditambahkan.");
        } catch (SQLException e) {
            System.out.println("Gagal menambah dosen: " + e.getMessage());
        }
    }

    public static void tampilkanDosen() {
        Connection conn = DBConnection.connect();
        try {
            String sql = "SELECT * FROM dosen";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            System.out.println("\n=== Daftar Dosen ===");
            while (rs.next()) {
                System.out.println(rs.getString("NIDN") + " - " + rs.getString("Nama_Dsn"));
            }
        } catch (SQLException e) {
            System.out.println("Gagal mengambil data dosen: " + e.getMessage());
        }
    }

    public static void updateDosen(Dosen d) {
        Connection conn = DBConnection.connect();
        try {
            String sql = "UPDATE dosen SET Nama_Dsn = ? WHERE NIDN = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, d.getNama());
            stmt.setString(2, d.getNidn());
            int rows = stmt.executeUpdate();

            if (rows > 0) {
                System.out.println("Data dosen berhasil diperbarui.");
            } else {
                System.out.println("NIDN tidak ditemukan.");
            }
        } catch (SQLException e) {
            System.out.println("Gagal memperbarui dosen: " + e.getMessage());
        }
    }

    public static void hapusDosen(String nidn) {
        Connection conn = DBConnection.connect();
        try {
            String sql = "DELETE FROM dosen WHERE NIDN = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nidn);
            int rows = stmt.executeUpdate();

            if (rows > 0) {
                System.out.println("Data dosen berhasil dihapus.");
            } else {
                System.out.println("NIDN tidak ditemukan.");
            }
        } catch (SQLException e) {
            System.out.println("Gagal menghapus dosen: " + e.getMessage());
        }
    }
}
