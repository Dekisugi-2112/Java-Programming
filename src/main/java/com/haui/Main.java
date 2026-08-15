package com.haui;

import com.formdev.flatlaf.FlatLightLaf;
import com.haui.view.auth.LoginFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Thiết lập giao diện FlatLaf hiện đại
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception e) {
            System.err.println("Không thể khởi động FlatLaf, sử dụng giao diện mặc định.");
        }

        // Khởi động màn hình Đăng nhập
        SwingUtilities.invokeLater(() -> {
            try {
                LoginFrame loginFrame = new LoginFrame();
                loginFrame.setVisible(true);
                System.out.println(">>> Ứng dụng đã khởi chạy thành công! Cửa sổ Đăng nhập đang hiển thị trên màn hình.");
            } catch (Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
