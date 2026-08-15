package com.haui.view.main;

import com.haui.model.Account;
import com.haui.util.DialogHelper;
import com.haui.view.auth.LoginFrame;
import com.haui.view.panels.GiangVienPanel;
import com.haui.view.panels.MonHocPanel;
import com.haui.view.panels.SinhVienPanel;

import javax.swing.*;
import java.awt.*;

public class MainDashboardFrame extends JFrame {
    private final Account currentUser;

    private JTabbedPane tabbedPane;
    private SinhVienPanel sinhVienPanel;
    private GiangVienPanel giangVienPanel;
    private MonHocPanel monHocPanel;

    public MainDashboardFrame(Account currentUser) {
        this.currentUser = currentUser;
        initComponents();
    }

    private void initComponents() {
        setTitle("Hệ Thống Quản Lý Đào Tạo HaUI - " + (currentUser != null ? currentUser.getFullName() : "User"));
        setSize(1000, 680);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Header Panel (Logo & User info)
        JPanel pnlHeader = new JPanel(new BorderLayout());
        pnlHeader.setBackground(new Color(24, 119, 242));
        pnlHeader.setPreferredSize(new Dimension(1000, 60));
        pnlHeader.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));

        JLabel lblTitle = new JLabel("HỆ THỐNG QUẢN LÝ ĐÀO TẠO ĐẠI HỌC CÔNG NGHIỆP HÀ NỘI");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 17));
        lblTitle.setForeground(Color.WHITE);
        pnlHeader.add(lblTitle, BorderLayout.WEST);

        JPanel pnlUser = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 15));
        pnlUser.setOpaque(false);

        String userDisplay = currentUser != null ? currentUser.getFullName() + " (" + currentUser.getRole() + ")" : "Khách";
        JLabel lblUser = new JLabel("Xin chào, " + userDisplay);
        lblUser.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblUser.setForeground(Color.WHITE);

        JButton btnLogout = new JButton("Đăng xuất");
        btnLogout.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btnLogout.setBackground(new Color(220, 53, 69));
        btnLogout.setForeground(Color.WHITE);
        btnLogout.setCursor(new Cursor(Cursor.HAND_CURSOR));

        pnlUser.add(lblUser);
        pnlUser.add(btnLogout);
        pnlHeader.add(pnlUser, BorderLayout.EAST);
        add(pnlHeader, BorderLayout.NORTH);

        // Center Content - Tabbed Pane
        tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setFont(new Font("Segoe UI", Font.BOLD, 14));

        sinhVienPanel = new SinhVienPanel();
        giangVienPanel = new GiangVienPanel();
        monHocPanel = new MonHocPanel();

        tabbedPane.addTab(" Quản lý Sinh Viên ", sinhVienPanel);
        tabbedPane.addTab(" Quản lý Giảng Viên ", giangVienPanel);
        tabbedPane.addTab(" Quản lý Môn Học ", monHocPanel);

        add(tabbedPane, BorderLayout.CENTER);

        // Footer / Status Bar
        JPanel pnlFooter = new JPanel(new BorderLayout());
        pnlFooter.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        JLabel lblStatus = new JLabel("Trạng thái: Đã kết nối cơ sở dữ liệu | Sẵn sàng làm việc");
        lblStatus.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        pnlFooter.add(lblStatus, BorderLayout.WEST);

        JLabel lblCopy = new JLabel("© 2026 Đại học Công nghiệp Hà Nội - HaUI");
        lblCopy.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        pnlFooter.add(lblCopy, BorderLayout.EAST);
        add(pnlFooter, BorderLayout.SOUTH);

        // Events
        btnLogout.addActionListener(e -> {
            if (DialogHelper.showConfirm(this, "Bạn có chắc chắn muốn đăng xuất không?")) {
                this.dispose();
                new LoginFrame().setVisible(true);
            }
        });
    }
}
