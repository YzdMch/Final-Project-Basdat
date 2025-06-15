package Akademik.Model;

public class MataKuliah {
    private String kode;
    private String nama;
    private int sks;
    private String kelas;
    private String fkDosen;

    public MataKuliah() {}

    public MataKuliah(String kode, String nama, int sks, String kelas, String fkDosen) {
        this.kode = kode;
        this.nama = nama;
        this.sks = sks;
        this.kelas = kelas;
        this.fkDosen = fkDosen;
    }

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public int getSks() {
        return sks;
    }

    public void setSks(int sks) {
        this.sks = sks;
    }

    public String getKelas() {
        return kelas;
    }

    public void setKelas(String kelas) {
        this.kelas = kelas;
    }

    public String getFkDosen() {
        return fkDosen;
    }

    public void setFkDosen(String fkDosen) {
        this.fkDosen = fkDosen;
    }
}
