package com.haui.view.auth;

import com.haui.model.Account;
import com.haui.service.AccountService;
import com.haui.util.DialogHelper;

import javax.swing.*;
import java.awt.*;

public class RegisterFrame extends JFrame {
    private final AccountService accountService;
    private final JFrame loginFrame;

    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JPasswordField txtConfirmPassword;
    private JTextField txtFullName;
    private JTextField txtEmail;
    private JButton btnSubmit;
    private JButton btnBack;

    public RegisterFrame(JFrame loginFrame) {
        this.loginFrame = loginFrame;
        this.accountService = new AccountService();
        initComponents();
    }

    private void initComponents() {
        setTitle("Đăng ký tài khoản - HaUI Manager");
        setSize(440, 420);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout(10, 10));

        // Header Panel
        JPanel pnlHeader = new JPanel();
        pnlHeader.setBackground(new Color(40, 167, 69));
        pnlHeader.setPreferredSize(new Dimension(440, 50));
        JLabel lblTitle = new JLabel("ĐĂNG KÝ TÀI KHOẢN MỚI");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTitle.setForeground(Color.WHITE);
        pnlHeader.add(lblTitle);
        add(pnlHeader, BorderLayout.NORTH);

        // Form Panel
        JPanel pnlForm = new JPanel(new GridBagLayout());
        pnlForm.setBorder(BorderFactory.createEmptyBorder(15, 25, 10, 25));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(6, 6, 6, 6);

        // Username
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.35;
        pnlForm.add(new JLabel("Tên đăng nhập:"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.65;
        txtUsername = new JTextField();
        pnlForm.add(txtUsername, gbc);

        // Password
        gbc.gridx = 0; gbc.gridy = 1;
        pnlForm.add(new JLabel("Mật khẩu:"), gbc);
        gbc.gridx = 1;
        txtPassword = new JPasswordField();
        pnlForm.add(txtPassword, gbc);

        // Confirm Password
        gbc.gridx = 0; gbc.gridy = 2;
        pnlForm.add(new JLabel("Nhập lại mật khẩu:"), gbc);
        gbc.gridx = 1;
        txtConfirmPassword = new JPasswordField();
        pnlForm.add(txtConfirmPassword, gbc);

        // Full Name
        gbc.gridx = 0; gbc.gridy = 3;
        pnlForm.add(new JLabel("Họ và tên:"), gbc);
        gbc.gridx = 1;
        txtFullName = new JTextField();
        pnlForm.add(txtFullName, gbc);

        // Email
        gbc.gridx = 0; gbc.gridy = 4;
        pnlForm.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        txtEmail = new JTextField();
        pnlForm.add(txtEmail, gbc);

        add(pnlForm, BorderLayout.CENTER);

        // Button Panel
        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        btnSubmit = new JButton("Đăng ký");
        btnSubmit.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnSubmit.setBackground(new Color(40, 167, 69));
        btnSubmit.setForeground(Color.WHITE);
        btnSubmit.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnBack = new JButton("Quay lại");
        btnBack.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btnBack.setCursor(new Cursor(Cursor.HAND_CURSOR));

        pnlButtons.add(btnSubmit);
        pnlButtons.add(btnBack);
        add(pnlButtons, BorderLayout.SOUTH);

        // Events
        btnSubmit.addActionListener(e -> performRegister());
        btnBack.addActionListener(e -> {
            this.dispose();
            if (loginFrame != null) loginFrame.setVisible(true);
        });
    }

    private void performRegister() {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();
        String confirm = new String(txtConfirmPassword.getPassword()).trim();
        String fullName = txtFullName.getText().trim();
        String email = txtEmail.getText().trim();

        Account account = new Account(username, password, fullName, email, "USER");
        try {
            boolean success = accountService.register(account, confirm);
            if (success) {
                DialogHelper.showInfo(this, "Đăng ký tài khoản thành công! Bạn có thể đăng nhập ngay bây giờ.");
                this.dispose();
                if (loginFrame != null) loginFrame.setVisible(true);
            }
        } catch (Exception ex) {
            DialogHelper.showError(this, ex.getMessage());
        }
    }
}
