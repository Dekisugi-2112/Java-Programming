package com.haui.dao;

import com.haui.model.SinhVien;
import com.haui.util.DatabaseConnection;
import com.haui.util.DateUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SinhVienDAO implements BaseDAO<SinhVien, String> {

    @Override
    public List<SinhVien> selectAll() {
        List<SinhVien> list = new ArrayList<>();
        String sql = "SELECT maSV, hoTen, ngaySinh, gioiTinh, lop, khoa, diemTB FROM SinhVien";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                SinhVien sv = new SinhVien(
                        rs.getString("maSV"),
                        rs.getString("hoTen"),
                        rs.getDate("ngaySinh"),
                        rs.getString("gioiTinh"),
                        rs.getString("lop"),
                        rs.getString("khoa"),
                        rs.getDouble("diemTB")
                );
                list.add(sv);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public SinhVien selectById(String maSV) {
        String sql = "SELECT maSV, hoTen, ngaySinh, gioiTinh, lop, khoa, diemTB FROM SinhVien WHERE maSV = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maSV);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new SinhVien(
                            rs.getString("maSV"),
                            rs.getString("hoTen"),
                            rs.getDate("ngaySinh"),
                            rs.getString("gioiTinh"),
                            rs.getString("lop"),
                            rs.getString("khoa"),
                            rs.getDouble("diemTB")
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<SinhVien> search(String keyword) {
        List<SinhVien> list = new ArrayList<>();
        String sql = "SELECT maSV, hoTen, ngaySinh, gioiTinh, lop, khoa, diemTB FROM SinhVien " +
                     "WHERE maSV LIKE ? OR hoTen LIKE ? OR lop LIKE ? OR khoa LIKE ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            String pattern = "%" + keyword + "%";
            ps.setString(1, pattern);
            ps.setString(2, pattern);
            ps.setString(3, pattern);
            ps.setString(4, pattern);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    SinhVien sv = new SinhVien(
                            rs.getString("maSV"),
                            rs.getString("hoTen"),
                            rs.getDate("ngaySinh"),
                            rs.getString("gioiTinh"),
                            rs.getString("lop"),
                            rs.getString("khoa"),
                            rs.getDouble("diemTB")
                    );
                    list.add(sv);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean insert(SinhVien sv) {
        String sql = "INSERT INTO SinhVien (maSV, hoTen, ngaySinh, gioiTinh, lop, khoa, diemTB) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, sv.getMaSV());
            ps.setString(2, sv.getHoTen());
            ps.setDate(3, DateUtil.toSqlDate(sv.getNgaySinh()));
            ps.setString(4, sv.getGioiTinh());
            ps.setString(5, sv.getLop());
            ps.setString(6, sv.getKhoa());
            ps.setDouble(7, sv.getDiemTB());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(SinhVien sv) {
        String sql = "UPDATE SinhVien SET hoTen = ?, ngaySinh = ?, gioiTinh = ?, lop = ?, khoa = ?, diemTB = ? WHERE maSV = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, sv.getHoTen());
            ps.setDate(2, DateUtil.toSqlDate(sv.getNgaySinh()));
            ps.setString(3, sv.getGioiTinh());
            ps.setString(4, sv.getLop());
            ps.setString(5, sv.getKhoa());
            ps.setDouble(6, sv.getDiemTB());
            ps.setString(7, sv.getMaSV());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(String maSV) {
        String sql = "DELETE FROM SinhVien WHERE maSV = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maSV);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
