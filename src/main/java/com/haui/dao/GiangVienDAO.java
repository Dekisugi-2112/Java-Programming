package com.haui.dao;

import com.haui.model.GiangVien;
import com.haui.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class GiangVienDAO implements BaseDAO<GiangVien, String> {

    @Override
    public List<GiangVien> selectAll() {
        List<GiangVien> list = new ArrayList<>();
        String sql = "SELECT maGV, hoTen, gioiTinh, email, soDienThoai, hocVi, khoa FROM GiangVien";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                GiangVien gv = new GiangVien(
                        rs.getString("maGV"),
                        rs.getString("hoTen"),
                        rs.getString("gioiTinh"),
                        rs.getString("email"),
                        rs.getString("soDienThoai"),
                        rs.getString("hocVi"),
                        rs.getString("khoa")
                );
                list.add(gv);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public GiangVien selectById(String maGV) {
        String sql = "SELECT maGV, hoTen, gioiTinh, email, soDienThoai, hocVi, khoa FROM GiangVien WHERE maGV = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maGV);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new GiangVien(
                            rs.getString("maGV"),
                            rs.getString("hoTen"),
                            rs.getString("gioiTinh"),
                            rs.getString("email"),
                            rs.getString("soDienThoai"),
                            rs.getString("hocVi"),
                            rs.getString("khoa")
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<GiangVien> search(String keyword) {
        List<GiangVien> list = new ArrayList<>();
        String sql = "SELECT maGV, hoTen, gioiTinh, email, soDienThoai, hocVi, khoa FROM GiangVien WHERE maGV LIKE ? OR hoTen LIKE ? OR khoa LIKE ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            String pattern = "%" + keyword + "%";
            ps.setString(1, pattern);
            ps.setString(2, pattern);
            ps.setString(3, pattern);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    GiangVien gv = new GiangVien(
                            rs.getString("maGV"),
                            rs.getString("hoTen"),
                            rs.getString("gioiTinh"),
                            rs.getString("email"),
                            rs.getString("soDienThoai"),
                            rs.getString("hocVi"),
                            rs.getString("khoa")
                    );
                    list.add(gv);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean insert(GiangVien gv) {
        String sql = "INSERT INTO GiangVien (maGV, hoTen, gioiTinh, email, soDienThoai, hocVi, khoa) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, gv.getMaGV());
            ps.setString(2, gv.getHoTen());
            ps.setString(3, gv.getGioiTinh());
            ps.setString(4, gv.getEmail());
            ps.setString(5, gv.getSoDienThoai());
            ps.setString(6, gv.getHocVi());
            ps.setString(7, gv.getKhoa());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(GiangVien gv) {
        String sql = "UPDATE GiangVien SET hoTen = ?, gioiTinh = ?, email = ?, soDienThoai = ?, hocVi = ?, khoa = ? WHERE maGV = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, gv.getHoTen());
            ps.setString(2, gv.getGioiTinh());
            ps.setString(3, gv.getEmail());
            ps.setString(4, gv.getSoDienThoai());
            ps.setString(5, gv.getHocVi());
            ps.setString(6, gv.getKhoa());
            ps.setString(7, gv.getMaGV());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(String maGV) {
        String sql = "DELETE FROM GiangVien WHERE maGV = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maGV);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
