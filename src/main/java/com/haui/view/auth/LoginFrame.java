package com.haui.view.auth;

import com.haui.model.Account;
import com.haui.service.AccountService;
import com.haui.util.DialogHelper;
import com.haui.view.main.MainDashboardFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class LoginFrame extends JFrame {
    private final AccountService accountService;

    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private JButton btnRegister;
    private JButton btnExit;

    public LoginFrame() {
        this.accountService = new AccountService();
        initComponents();
    }

    private void initComponents() {
        setTitle("Đăng nhập hệ thống - HaUI Manager");
        setSize(420, 320);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout(10, 10));

        // Header Panel
        JPanel pnlHeader = new JPanel();
        pnlHeader.setBackground(new Color(24, 119, 242));
        pnlHeader.setPreferredSize(new Dimension(420, 60));
        JLabel lblTitle = new JLabel("HỆ THỐNG QUẢN LÝ ĐÀO TẠO");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitle.setForeground(Color.WHITE);
        pnlHeader.add(lblTitle);
        add(pnlHeader, BorderLayout.NORTH);

        // Form Panel
        JPanel pnlForm = new JPanel(new GridBagLayout());
        pnlForm.setBorder(BorderFactory.createEmptyBorder(15, 25, 10, 25));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 8, 8, 8);

        // Username
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.3;
        JLabel lblUser = new JLabel("Tên đăng nhập:");
        lblUser.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        pnlForm.add(lblUser, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        txtUsername = new JTextField("admin");
        txtUsername.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        pnlForm.add(txtUsername, gbc);

        // Password
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.3;
        JLabel lblPass = new JLabel("Mật khẩu:");
        lblPass.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        pnlForm.add(lblPass, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        txtPassword = new JPasswordField("admin123");
        txtPassword.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        pnlForm.add(txtPassword, gbc);

        add(pnlForm, BorderLayout.CENTER);

        // Button Panel
        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        btnLogin = new JButton("Đăng nhập");
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnLogin.setBackground(new Color(24, 119, 242));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnRegister = new JButton("Đăng ký");
        btnRegister.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btnRegister.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnExit = new JButton("Thoát");
        btnExit.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btnExit.setCursor(new Cursor(Cursor.HAND_CURSOR));

        pnlButtons.add(btnLogin);
        pnlButtons.add(btnRegister);
        pnlButtons.add(btnExit);
        add(pnlButtons, BorderLayout.SOUTH);

        // Sự kiện
        btnLogin.addActionListener(e -> performLogin());
        btnRegister.addActionListener(e -> {
            new RegisterFrame(this).setVisible(true);
            this.setVisible(false);
        });
        btnExit.addActionListener(e -> System.exit(0));

        // Nhấn Enter để đăng nhập
        KeyAdapter enterListener = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    performLogin();
                }
            }
        };
        txtUsername.addKeyListener(enterListener);
        txtPassword.addKeyListener(enterListener);
    }

    private void performLogin() {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();
        try {
            Account account = accountService.login(username, password);
            DialogHelper.showInfo(this, "Đăng nhập thành công! Xin chào " + account.getFullName());
            new MainDashboardFrame(account).setVisible(true);
            this.dispose();
        } catch (Exception ex) {
            DialogHelper.showError(this, ex.getMessage());
        }
    }
}
