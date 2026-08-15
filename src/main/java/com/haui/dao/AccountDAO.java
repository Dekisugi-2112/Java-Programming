package com.haui.dao;

import com.haui.model.Account;
import com.haui.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO implements BaseDAO<Account, String> {

    @Override
    public List<Account> selectAll() {
        List<Account> list = new ArrayList<>();
        String sql = "SELECT username, password, fullName, email, role FROM Account";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Account acc = new Account(
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("fullName"),
                        rs.getString("email"),
                        rs.getString("role")
                );
                list.add(acc);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Account selectById(String username) {
        String sql = "SELECT username, password, fullName, email, role FROM Account WHERE username = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Account(
                            rs.getString("username"),
                            rs.getString("password"),
                            rs.getString("fullName"),
                            rs.getString("email"),
                            rs.getString("role")
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Account checkLogin(String username, String password) throws Exception {
        String sql = "SELECT username, password, fullName, email, role FROM Account WHERE username = ? AND password = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Account(
                            rs.getString("username"),
                            rs.getString("password"),
                            rs.getString("fullName"),
                            rs.getString("email"),
                            rs.getString("role")
                    );
                }
            }
        }
        return null;
    }

    @Override
    public boolean insert(Account acc) {
        String sql = "INSERT INTO Account (username, password, fullName, email, role) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, acc.getUsername());
            ps.setString(2, acc.getPassword());
            ps.setString(3, acc.getFullName());
            ps.setString(4, acc.getEmail());
            ps.setString(5, acc.getRole());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Account acc) {
        String sql = "UPDATE Account SET password = ?, fullName = ?, email = ?, role = ? WHERE username = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, acc.getPassword());
            ps.setString(2, acc.getFullName());
            ps.setString(3, acc.getEmail());
            ps.setString(4, acc.getRole());
            ps.setString(5, acc.getUsername());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(String username) {
        String sql = "DELETE FROM Account WHERE username = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
