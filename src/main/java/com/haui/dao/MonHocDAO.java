package com.haui.dao;

import com.haui.model.MonHoc;
import com.haui.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MonHocDAO implements BaseDAO<MonHoc, String> {

    @Override
    public List<MonHoc> selectAll() {
        List<MonHoc> list = new ArrayList<>();
        String sql = "SELECT maMH, tenMH, soTinChi, chuyenNganh FROM MonHoc";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                MonHoc mh = new MonHoc(
                        rs.getString("maMH"),
                        rs.getString("tenMH"),
                        rs.getInt("soTinChi"),
                        rs.getString("chuyenNganh")
                );
                list.add(mh);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public MonHoc selectById(String maMH) {
        String sql = "SELECT maMH, tenMH, soTinChi, chuyenNganh FROM MonHoc WHERE maMH = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maMH);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new MonHoc(
                            rs.getString("maMH"),
                            rs.getString("tenMH"),
                            rs.getInt("soTinChi"),
                            rs.getString("chuyenNganh")
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<MonHoc> search(String keyword) {
        List<MonHoc> list = new ArrayList<>();
        String sql = "SELECT maMH, tenMH, soTinChi, chuyenNganh FROM MonHoc WHERE maMH LIKE ? OR tenMH LIKE ? OR chuyenNganh LIKE ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            String pattern = "%" + keyword + "%";
            ps.setString(1, pattern);
            ps.setString(2, pattern);
            ps.setString(3, pattern);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    MonHoc mh = new MonHoc(
                            rs.getString("maMH"),
                            rs.getString("tenMH"),
                            rs.getInt("soTinChi"),
                            rs.getString("chuyenNganh")
                    );
                    list.add(mh);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean insert(MonHoc mh) {
        String sql = "INSERT INTO MonHoc (maMH, tenMH, soTinChi, chuyenNganh) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, mh.getMaMH());
            ps.setString(2, mh.getTenMH());
            ps.setInt(3, mh.getSoTinChi());
            ps.setString(4, mh.getChuyenNganh());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(MonHoc mh) {
        String sql = "UPDATE MonHoc SET tenMH = ?, soTinChi = ?, chuyenNganh = ? WHERE maMH = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, mh.getTenMH());
            ps.setInt(2, mh.getSoTinChi());
            ps.setString(3, mh.getChuyenNganh());
            ps.setString(4, mh.getMaMH());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(String maMH) {
        String sql = "DELETE FROM MonHoc WHERE maMH = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maMH);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
