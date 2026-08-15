package com.haui.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Quản lý kết nối Database bảo mật
 * Mật khẩu và thông tin kết nối được nạp động từ file cấu hình config.properties
 * hoặc Biến môi trường (Environment Variables), không bị lộ khi đưa lên GitHub.
 */
public class DatabaseConnection {
    private static String url;
    private static String user;
    private static String password;

    static {
        loadConfiguration();
    }

    private static void loadConfiguration() {
        Properties props = new Properties();

        // 1. Đọc từ classpath (trong thư mục resources khi chạy qua Maven / IDE)
        try (InputStream is = DatabaseConnection.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (is != null) {
                props.load(is);
            }
        } catch (Exception ignored) {
        }

        // 2. Nếu chưa tìm thấy trong classpath, tìm trực tiếp theo đường dẫn file cục bộ
        if (props.isEmpty()) {
            File[] candidateFiles = {
                new File("src/main/resources/config.properties"),
                new File("config.properties")
            };
            for (File file : candidateFiles) {
                if (file.exists() && file.isFile()) {
                    try (FileInputStream fis = new FileInputStream(file)) {
                        props.load(fis);
                        break;
                    } catch (Exception ignored) {
                    }
                }
            }
        }

        // 3. Ưu tiên lấy từ biến môi trường (Environment Variables), sau đó fallback sang file config
        url = getEnvOrProperty("DB_URL", props.getProperty("db.url"));
        user = getEnvOrProperty("DB_USER", props.getProperty("db.user"));
        password = getEnvOrProperty("DB_PASSWORD", props.getProperty("db.password"));
    }

    private static String getEnvOrProperty(String envKey, String propValue) {
        String envVal = System.getenv(envKey);
        if (envVal != null && !envVal.trim().isEmpty()) {
            return envVal.trim();
        }
        return propValue != null ? propValue.trim() : null;
    }

    public static Connection getConnection() throws SQLException {
        if (url == null || user == null || password == null ||
            url.trim().isEmpty() || user.trim().isEmpty() || password.trim().isEmpty()) {
            throw new SQLException(
                "Chưa cấu hình thông tin kết nối Cơ sở dữ liệu!\n" +
                "Vui lòng tạo file 'src/main/resources/config.properties' (dựa trên file mẫu 'config.properties.example') " +
                "hoặc thiết lập các biến môi trường DB_URL, DB_USER, DB_PASSWORD."
            );
        }

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("Không tìm thấy PostgreSQL JDBC Driver! Hãy kiểm tra thư viện pom.xml hoặc thư mục lib.", e);
        }
        return DriverManager.getConnection(url, user, password);
    }
}