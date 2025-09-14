package qlsv;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Student {
    private String maSV;
    private String hoTen;
    private Date ngaySinh;
    private String nganh;
    private double diemTB;
    private String lop;

    public Student(String maSV, String hoTen, Date ngaySinh, String nganh, double diemTB, String lop) {
        this.maSV = maSV;
        this.hoTen = hoTen;
        this.ngaySinh = ngaySinh;
        this.nganh = nganh;
        this.diemTB = diemTB;
        this.lop = lop;
    }

    public String getMaSV() { return maSV; }
    public String getHoTen() { return hoTen; }
    public Date getNgaySinh() { return ngaySinh; }
    public String getNganh() { return nganh; }
    public double getDiemTB() { return diemTB; }
    public String getLop() { return lop; }

    public void setHoTen(String hoTen) { this.hoTen = hoTen; }
    public void setNgaySinh(Date ngaySinh) { this.ngaySinh = ngaySinh; }
    public void setNganh(String nganh) { this.nganh = nganh; }
    public void setDiemTB(double diemTB) { this.diemTB = diemTB; }
    public void setLop(String lop) { this.lop = lop; }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return maSV + " | " + hoTen + " | " + sdf.format(ngaySinh) + " | " + nganh + " | " + diemTB + " | " + lop;
    }
}
