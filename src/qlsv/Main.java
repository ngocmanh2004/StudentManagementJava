package qlsv;

import java.text.SimpleDateFormat;
import java.util.*;

public class Main {
    private static Scanner sc = new Scanner(System.in);
    private static StudentDAO dao = new StudentDAO();

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n===== MENU =====");
            System.out.println("1. Thêm sinh viên");
            System.out.println("2. Xóa sinh viên");
            System.out.println("3. Sửa sinh viên");
            System.out.println("4. Danh sách tất cả SV");
            System.out.println("5. Danh sách theo lớp");
            System.out.println("6. Danh sách theo ngành");
            System.out.println("7. Danh sách theo tháng sinh");
            System.out.println("8. Sắp xếp theo điểm TB");
            System.out.println("0. Thoát");
            System.out.print("Chọn: ");
            int c = Integer.parseInt(sc.nextLine());

            try {
                switch (c) {
                    case 1: themSV(); break;
                    case 2: xoaSV(); break;
                    case 3: suaSV(); break;
                    case 4: inDanhSach(null); break;
                    case 5: inTheoLop(); break;
                    case 6: inTheoNganh(); break;
                    case 7: inTheoThangSinh(); break;
                    case 8: inDanhSach("ORDER BY diemTB DESC"); break;
                    case 0: System.exit(0);
                }
            } catch (Exception e) {
                System.out.println("Lỗi: " + e.getMessage());
            }
        }
    }

    private static void themSV() throws Exception {
        System.out.print("Mã SV: ");
        String ma = sc.nextLine();
        System.out.print("Họ tên: ");
        String ten = chuanHoa(sc.nextLine());
        System.out.print("Ngày sinh (yyyy-MM-dd): ");
        String ns = sc.nextLine();
        System.out.print("Ngành (CNTT/KTPM): ");
        String nganh = sc.nextLine().toUpperCase();
        System.out.print("Điểm TB: ");
        double diem = Double.parseDouble(sc.nextLine());
        System.out.print("Lớp: ");
        String lop = sc.nextLine();

        if (!kiemTraHopLe(ma, ten, ns, nganh, diem)) return;

        Date d = new SimpleDateFormat("yyyy-MM-dd").parse(ns);
        dao.addStudent(new Student(ma, ten, d, nganh, diem, lop));
        System.out.println("✅ Thêm thành công!");
    }

    private static void xoaSV() throws Exception {
        System.out.print("Mã SV: ");
        String ma = sc.nextLine();
        if (dao.deleteStudent(ma)) System.out.println("✅ Xóa thành công!");
        else System.out.println("❌ Không tìm thấy!");
    }

    private static void suaSV() throws Exception {
        System.out.print("Mã SV: ");
        String ma = sc.nextLine();
        System.out.print("Tên mới: ");
        String ten = chuanHoa(sc.nextLine());
        System.out.print("Điểm mới: ");
        double diem = Double.parseDouble(sc.nextLine());
        if (dao.updateStudent(ma, ten, diem)) System.out.println("✅ Sửa thành công!");
        else System.out.println("❌ Không tìm thấy!");
    }

    private static void inDanhSach(String condition) throws Exception {
        List<Student> list = dao.getStudents(condition);
        for (Student sv : list) {
            System.out.println(sv);
        }
    }

    private static void inTheoLop() throws Exception {
        System.out.print("Nhập lớp: ");
        String lop = sc.nextLine();
        inDanhSach("WHERE lop='" + lop + "'");
    }

    private static void inTheoNganh() throws Exception {
        System.out.print("Ngành (CNTT/KTPM): ");
        String nganh = sc.nextLine().toUpperCase();
        inDanhSach("WHERE nganh='" + nganh + "'");
    }

    private static void inTheoThangSinh() throws Exception {
        System.out.print("Tháng (1-12): ");
        int thang = Integer.parseInt(sc.nextLine());
        inDanhSach("WHERE MONTH(ngaySinh)=" + thang);
    }

    // ====== Validation ======
    private static boolean kiemTraHopLe(String ma, String ten, String ns, String nganh, double diem) {
        if (!ma.matches("\\d{10}")) { System.out.println("❌ Mã SV không hợp lệ!"); return false; }
        if (nganh.equals("CNTT") && !ma.startsWith("455105")) { System.out.println("❌ Sai định dạng mã CNTT!"); return false; }
        if (nganh.equals("KTPM") && !ma.startsWith("455109")) { System.out.println("❌ Sai định dạng mã KTPM!"); return false; }
        try {
            Date d = new SimpleDateFormat("yyyy-MM-dd").parse(ns);
            Calendar c = Calendar.getInstance();
            int yearNow = c.get(Calendar.YEAR);
            c.setTime(d);
            int tuoi = yearNow - c.get(Calendar.YEAR);
            if (tuoi < 15 || tuoi > 110) { System.out.println("❌ Tuổi không hợp lý!"); return false; }
        } catch (Exception e) { System.out.println("❌ Ngày sinh không hợp lệ!"); return false; }
        if (diem < 0 || diem > 10) { System.out.println("❌ Điểm không hợp lệ!"); return false; }
        if (!nganh.equals("CNTT") && !nganh.equals("KTPM")) { System.out.println("❌ Ngành không hợp lệ!"); return false; }
        return true;
    }

    private static String chuanHoa(String ten) {
        ten = ten.trim().toLowerCase();
        String[] arr = ten.split("\\s+");
        StringBuilder sb = new StringBuilder();
        for (String w : arr) {
            sb.append(Character.toUpperCase(w.charAt(0))).append(w.substring(1)).append(" ");
        }
        return sb.toString().trim();
    }
}
