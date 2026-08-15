package com.haui.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    // Kết nối qua Supabase Pooler (hỗ trợ cả IPv4 và IPv6, tránh lỗi SocketTimeoutException trên mạng IPv4)
    private static final String URL = "jdbc:postgresql://aws-0-ap-southeast-1.pooler.supabase.com:6543/postgres?sslmode=require&connectTimeout=10";
    private static final String USER = "postgres.ylfekglsbjhqwtmclfui";
    private static final String PASSWORD = "Nghiandai2201@";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("Không tìm thấy PostgreSQL JDBC Driver! Hãy kiểm tra thư viện pom.xml hoặc thư mục lib.", e);
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}