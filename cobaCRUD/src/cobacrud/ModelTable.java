package cobacrud;

public class ModelTable {
    String id, nama, gender, npm;

    public ModelTable(String id, String nama, String gender, String npm) {
        this.id = id;
        this.nama = nama;
        this.gender = gender;
        this.npm = npm;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getNpm() {
        return npm;
    }

    public void setNpm(String npm) {
        this.npm = npm;
    }
    
}