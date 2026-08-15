package com.haui.service;

import com.haui.dao.AccountDAO;
import com.haui.model.Account;

import java.util.List;

public class AccountService {
    private final AccountDAO accountDAO;

    public AccountService() {
        this.accountDAO = new AccountDAO();
    }

    public Account login(String username, String password) throws Exception {
        if (username == null || username.trim().isEmpty()) {
            throw new Exception("Vui lòng nhập tên đăng nhập!");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new Exception("Vui lòng nhập mật khẩu!");
        }
        Account acc = accountDAO.checkLogin(username.trim(), password.trim());
        if (acc == null) {
            throw new Exception("Tên đăng nhập hoặc mật khẩu không chính xác!");
        }
        return acc;
    }

    public boolean register(Account account, String confirmPassword) throws Exception {
        if (account.getUsername() == null || account.getUsername().trim().isEmpty()) {
            throw new Exception("Tên đăng nhập không được để trống!");
        }
        if (account.getPassword() == null || account.getPassword().trim().isEmpty()) {
            throw new Exception("Mật khẩu không được để trống!");
        }
        if (!account.getPassword().equals(confirmPassword)) {
            throw new Exception("Mật khẩu xác nhận không khớp!");
        }
        if (account.getFullName() == null || account.getFullName().trim().isEmpty()) {
            throw new Exception("Họ và tên không được để trống!");
        }
        if (account.getEmail() == null || account.getEmail().trim().isEmpty()) {
            throw new Exception("Email không được để trống!");
        }

        // Kiểm tra xem username đã tồn tại chưa
        Account existing = accountDAO.selectById(account.getUsername().trim());
        if (existing != null) {
            throw new Exception("Tên đăng nhập '" + account.getUsername() + "' đã tồn tại!");
        }

        if (account.getRole() == null || account.getRole().trim().isEmpty()) {
            account.setRole("USER");
        }

        return accountDAO.insert(account);
    }

    public List<Account> getAllAccounts() {
        return accountDAO.selectAll();
    }

    public boolean updateAccount(Account account) throws Exception {
        if (account.getFullName() == null || account.getFullName().trim().isEmpty()) {
            throw new Exception("Họ tên không được để trống!");
        }
        return accountDAO.update(account);
    }

    public boolean deleteAccount(String username) throws Exception {
        if ("admin".equalsIgnoreCase(username)) {
            throw new Exception("Không thể xóa tài khoản Quản trị viên (admin)!");
        }
        return accountDAO.delete(username);
    }
}
