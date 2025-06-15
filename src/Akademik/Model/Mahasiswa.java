package Akademik.Model;

public class Mahasiswa {
    private String npm;
    private String nama;
    private String prodi;
    private int semester;
    private String fkDosen;

    public Mahasiswa() {}

    public Mahasiswa(String npm, String nama, String prodi, int semester, String fkDosen) {
        this.npm = npm;
        this.nama = nama;
        this.prodi = prodi;
        this.semester = semester;
        this.fkDosen = fkDosen;
    }

    public String getNpm() {
        return npm;
    }

    public void setNpm(String npm) {
        this.npm = npm;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getProdi() {
        return prodi;
    }

    public void setProdi(String prodi) {
        this.prodi = prodi;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public String getFkDosen() {
        return fkDosen;
    }

    public void setFkDosen(String fkDosen) {
        this.fkDosen = fkDosen;
    }
}
