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
                
                // Cek apakah data sudah ada
                String cekSql = "SELECT * FROM nilai WHERE FK_Mahasiswa = ? AND FK_Matkul = ?";
                PreparedStatement cekPS = conn.prepareStatement(cekSql);
                cekPS.setString(1, npm);
                cekPS.setString(2, kodeMK);
                ResultSet cekRS = cekPS.executeQuery();

                if (cekRS.next()) {
                    System.out.println("❌ Anda sudah mengambil mata kuliah ini sebelumnya.");
                    return;
                }
                
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

            // Ambil biodata mahasiswa
            String bioSql = "SELECT m.Nama_Mhs, m.Semester_Aktif, n.Tahun_ajar, n.Semester, d.Nama_Dsn " +
                            "FROM mahasiswa m " +
                            "JOIN nilai n ON m.NPM = n.FK_Mahasiswa " +
                            "LEFT JOIN dosen d ON m.FK_Dosen = d.NIDN " +
                            "WHERE m.NPM = ? LIMIT 1";
            PreparedStatement bioPs = conn.prepareStatement(bioSql);
            bioPs.setString(1, npm);
            ResultSet bioRs = bioPs.executeQuery();

            if (!bioRs.next()) {
                System.out.println("Data tidak ditemukan.");
                return;
            }

            String nama = bioRs.getString("Nama_Mhs");
            String semester = bioRs.getString("Semester");
            String tahun = bioRs.getString("Tahun_ajar");
            String dosenWali = bioRs.getString("Nama_Dsn");

            // Tampilkan header
            System.out.println("\nNPM     : " + npm);
            System.out.println("Nama    : " + nama);
            System.out.println("Semester: " + semester + " TA. " + tahun);
            System.out.println("Dosen Wali: " + dosenWali);

            // Ambil nilai-nilai
            String sql = "SELECT mk.Kode_MK, mk.Nama_MK, mk.SKS, mk.Kelas, " +
                         "n.Nilai_uts, n.Nilai_uas, n.Nilai_praktikum, n.Nilai_akhir, " +
                         "n.Nilai_huruf, n.Kredit, n.Bobot " +
                         "FROM nilai n JOIN mata_kuliah mk ON n.FK_Matkul = mk.Kode_MK " +
                         "WHERE n.FK_Mahasiswa = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, npm);
            ResultSet rs = ps.executeQuery();

            // Header tabel
            System.out.println("\n+----+--------+------------------------------+-----+-------+-------+-------+-----------+--------+--------+--------+");
            System.out.println("| No | Kode   | Mata Kuliah                  | SKS | Kelas | UTS   | UAS   | Praktikum | Total  | Huruf  | NxK    |");
            System.out.println("+----+--------+------------------------------+-----+-------+-------+-------+-----------+--------+--------+--------+");

            int no = 1;
            double totalBobot = 0;
            int totalSKS = 0;

            while (rs.next()) {
                String kode = rs.getString("Kode_MK");
                String mk = rs.getString("Nama_MK");
                int sks = rs.getInt("SKS");
                String kelas = rs.getString("Kelas");
                double uts = rs.getDouble("Nilai_uts");
                double uas = rs.getDouble("Nilai_uas");
                double prak = rs.getDouble("Nilai_praktikum");
                double akhir = rs.getDouble("Nilai_akhir");
                String huruf = rs.getString("Nilai_huruf");
                double kredit = rs.getDouble("Kredit");
                double bobot = rs.getDouble("Bobot");

                System.out.printf("| %-2d | %-6s | %-28s | %-3d | %-5s | %-5.1f | %-5.1f | %-9.1f | %-6.2f | %-6s | %-6.2f |\n",
                        no++, kode, potong(mk, 28), sks, kelas, uts, uas, prak, akhir, huruf, bobot);

                totalBobot += bobot;
                totalSKS += sks;
            }

            System.out.println("+----+--------+------------------------------+-----+-------+-------+-------+-----------+--------+--------+--------+");

            if (totalSKS == 0) {
                System.out.println("Belum ada mata kuliah diambil.");
                return;
            }

            double ip = Math.round((totalBobot / totalSKS) * 1000.0) / 1000.0;
            System.out.println("Indeks Prestasi : " + ip);

            // Simulasi jika ada nilai salah
            System.out.print("Apakah ada nilai yang salah? (y/n): ");
            String jawab = input.nextLine();
            if (jawab.equalsIgnoreCase("y")) {
                System.out.println("KHS akan diperbaiki.");
            }

        } catch (Exception e) {
            System.out.println("❌ Gagal melihat KHS: " + e.getMessage());
        }
    }

// Fungsi bantu untuk memotong string
private static String potong(String teks, int max) {
    if (teks.length() > max) return teks.substring(0, max - 1) + ".";
    return teks;
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
