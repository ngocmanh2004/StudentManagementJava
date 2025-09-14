package qlsv;

import java.sql.*;
import java.util.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.Date;

public class StudentDAO {

    // Thêm sinh viên
    public void addStudent(Student sv) throws Exception {
        String sql = "INSERT INTO sinhvien (maSV, hoTen, ngaySinh, nganh, diemTB, lop) VALUES (?,?,?,?,?,?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, sv.getMaSV());
            ps.setString(2, sv.getHoTen());
            ps.setDate(3, new java.sql.Date(sv.getNgaySinh().getTime()));
            ps.setString(4, sv.getNganh());
            ps.setDouble(5, sv.getDiemTB());
            ps.setString(6, sv.getLop());
            ps.executeUpdate();
        }
    }

    // Xóa sinh viên
    public boolean deleteStudent(String maSV) throws Exception {
        String sql = "DELETE FROM sinhvien WHERE maSV=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maSV);
            return ps.executeUpdate() > 0;
        }
    }

    // Sửa sinh viên
    public boolean updateStudent(String maSV, String hoTen, double diemTB) throws Exception {
        String sql = "UPDATE sinhvien SET hoTen=?, diemTB=? WHERE maSV=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, hoTen);
            ps.setDouble(2, diemTB);
            ps.setString(3, maSV);
            return ps.executeUpdate() > 0;
        }
    }

    // Lấy danh sách
    public List<Student> getStudents(String condition) throws Exception {
        List<Student> list = new ArrayList<>();
        String sql = "SELECT * FROM sinhvien " + (condition != null ? condition : "");
        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Student sv = new Student(
                        rs.getString("maSV"),
                        rs.getString("hoTen"),
                        rs.getDate("ngaySinh"),
                        rs.getString("nganh"),
                        rs.getDouble("diemTB"),
                        rs.getString("lop")
                );
                list.add(sv);
            }
        }
        return list;
    }
}
