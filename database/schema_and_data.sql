-- =======================================================
-- HỆ THỐNG QUẢN LÝ ĐÀO TẠO ĐẠI HỌC CÔNG NGHIỆP HÀ NỘI (HaUI)
-- Database Script: HAUI_MANAGE
-- Bao gồm cấu trúc bảng và 10 mẫu dữ liệu cho mỗi bảng
-- =======================================================

IF NOT EXISTS (SELECT * FROM sys.databases WHERE name = 'HAUI_MANAGE')
BEGIN
    CREATE DATABASE HAUI_MANAGE;
END
GO

USE HAUI_MANAGE;
GO

-- 1. Xóa các bảng cũ nếu đã tồn tại (theo thứ tự quan hệ)
IF OBJECT_ID('SinhVien', 'U') IS NOT NULL DROP TABLE SinhVien;
IF OBJECT_ID('GiangVien', 'U') IS NOT NULL DROP TABLE GiangVien;
IF OBJECT_ID('MonHoc', 'U') IS NOT NULL DROP TABLE MonHoc;
IF OBJECT_ID('Account', 'U') IS NOT NULL DROP TABLE Account;
GO

-- 2. Tạo bảng Account (Tài khoản người dùng & Phân quyền)
CREATE TABLE Account (
    username VARCHAR(50) PRIMARY KEY,
    password VARCHAR(255) NOT NULL,
    fullName NVARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    role VARCHAR(20) DEFAULT 'USER' -- 'ADMIN' hoặc 'USER'
);
GO

-- 3. Tạo bảng MonHoc (Môn học đào tạo)
CREATE TABLE MonHoc (
    maMH VARCHAR(20) PRIMARY KEY,
    tenMH NVARCHAR(100) NOT NULL,
    soTinChi INT NOT NULL CHECK (soTinChi > 0),
    chuyenNganh NVARCHAR(100) NOT NULL
);
GO

-- 4. Tạo bảng GiangVien (Giảng viên)
CREATE TABLE GiangVien (
    maGV VARCHAR(20) PRIMARY KEY,
    hoTen NVARCHAR(100) NOT NULL,
    gioiTinh NVARCHAR(10) NOT NULL,
    email VARCHAR(100),
    soDienThoai VARCHAR(20),
    hocVi NVARCHAR(50),
    khoa NVARCHAR(100) NOT NULL
);
GO

-- 5. Tạo bảng SinhVien (Sinh viên)
CREATE TABLE SinhVien (
    maSV VARCHAR(20) PRIMARY KEY,
    hoTen NVARCHAR(100) NOT NULL,
    ngaySinh DATE,
    gioiTinh NVARCHAR(10) NOT NULL,
    lop NVARCHAR(50) NOT NULL,
    khoa NVARCHAR(100) NOT NULL,
    diemTB FLOAT CHECK (diemTB >= 0.0 AND diemTB <= 10.0)
);
GO

-- =======================================================
-- 6. CHÈN DỮ LIỆU MẪU (10 DÒNG MỖI BẢNG)
-- =======================================================

-- 6.1 Dữ liệu mẫu cho bảng Account
INSERT INTO Account (username, password, fullName, email, role) VALUES
('admin', 'admin123', N'Quản Trị Viên Hệ Thống', 'admin@haui.edu.vn', 'ADMIN'),
('gv_hung', 'hung123', N'Nguyễn Văn Hùng', 'hungnv@haui.edu.vn', 'USER'),
('gv_lan', 'lan123', N'Trần Thị Lan', 'lantt@haui.edu.vn', 'USER'),
('gv_minh', 'minh123', N'Lê Quang Minh', 'minhlq@haui.edu.vn', 'USER'),
('gv_dung', 'dung123', N'Phạm Tiến Dũng', 'dungpt@haui.edu.vn', 'USER'),
('sv_nam', 'nam123', N'Vũ Thành Nam', 'namvt@gmail.com', 'USER'),
('sv_mai', 'mai123', N'Đỗ Phương Mai', 'maidp@gmail.com', 'USER'),
('sv_long', 'long123', N'Hoàng Đức Long', 'longhd@gmail.com', 'USER'),
('sv_hoa', 'hoa123', N'Bùi Thanh Hoa', 'hoabt@gmail.com', 'USER'),
('sv_tuan', 'tuan123', N'Ngô Anh Tuấn', 'tuanna@gmail.com', 'USER');
GO

-- 6.2 Dữ liệu mẫu cho bảng MonHoc
INSERT INTO MonHoc (maMH, tenMH, soTinChi, chuyenNganh) VALUES
('IT6015', N'Lập trình Java nâng cao', 3, N'Công nghệ thông tin'),
('IT6020', N'Cơ sở dữ liệu', 4, N'Hệ thống thông tin'),
('IT6035', N'Cấu trúc dữ liệu và giải thuật', 3, N'Khoa học máy tính'),
('IT6040', N'Lập trình Web với Spring Boot', 3, N'Công nghệ thông tin'),
('IT6052', N'Mạng máy tính', 3, N'Kỹ thuật phần mềm'),
('IT6061', N'Phân tích thiết kế hệ thống', 3, N'Hệ thống thông tin'),
('EE4010', N'Kỹ thuật điện tử số', 3, N'Điện - Điện tử'),
('ME3025', N'Cơ học máy', 3, N'Cơ khí chế tạo'),
('BA2010', N'Kinh tế vi mô', 2, N'Quản trị kinh doanh'),
('FL1010', N'Tiếng Anh chuyên ngành CNTT', 2, N'Toàn trường');
GO

-- 6.3 Dữ liệu mẫu cho bảng GiangVien
INSERT INTO GiangVien (maGV, hoTen, gioiTinh, email, soDienThoai, hocVi, khoa) VALUES
('GV001', N'Nguyễn Văn Hùng', N'Nam', 'hungnv@haui.edu.vn', '0912345678', N'Tiến sĩ', N'Công nghệ thông tin'),
('GV002', N'Trần Thị Lan', N'Nữ', 'lantt@haui.edu.vn', '0923456789', N'Thạc sĩ', N'Công nghệ thông tin'),
('GV003', N'Lê Quang Minh', N'Nam', 'minhlq@haui.edu.vn', '0934567890', N'Phó Giáo sư', N'Điện tử'),
('GV004', N'Phạm Tiến Dũng', N'Nam', 'dungpt@haui.edu.vn', '0945678901', N'Thạc sĩ', N'Kỹ thuật phần mềm'),
('GV005', N'Hoàng Thu Trang', N'Nữ', 'tranght@haui.edu.vn', '0956789012', N'Tiến sĩ', N'Hệ thống thông tin'),
('GV006', N'Vũ Đình Trọng', N'Nam', 'trongvd@haui.edu.vn', '0967890123', N'Thạc sĩ', N'Khoa học máy tính'),
('GV007', N'Đặng Phương Thảo', N'Nữ', 'thaodp@haui.edu.vn', '0978901234', N'Tiến sĩ', N'Công nghệ thông tin'),
('GV008', N'Bùi Đức Hạnh', N'Nam', 'hanhbd@haui.edu.vn', '0989012345', N'Thạc sĩ', N'Cơ khí'),
('GV009', N'Ngô Mai Phương', N'Nữ', 'phuongnm@haui.edu.vn', '0990123456', N'Thạc sĩ', N'Kinh tế'),
('GV010', N'Trịnh Xuân Bách', N'Nam', 'bachtx@haui.edu.vn', '0901234567', N'Tiến sĩ', N'Điện tử');
GO

-- 6.4 Dữ liệu mẫu cho bảng SinhVien
INSERT INTO SinhVien (maSV, hoTen, ngaySinh, gioiTinh, lop, khoa, diemTB) VALUES
('2022600101', N'Vũ Thành Nam', '2004-03-15', N'Nam', N'CNTT01-K17', N'Công nghệ thông tin', 8.5),
('2022600102', N'Đỗ Phương Mai', '2004-07-22', N'Nữ', N'CNTT01-K17', N'Công nghệ thông tin', 9.1),
('2022600103', N'Hoàng Đức Long', '2004-11-05', N'Nam', N'KTPM02-K17', N'Kỹ thuật phần mềm', 7.8),
('2022600104', N'Bùi Thanh Hoa', '2004-01-30', N'Nữ', N'HTTT01-K17', N'Hệ thống thông tin', 8.2),
('2022600105', N'Ngô Anh Tuấn', '2004-09-12', N'Nam', N'KHMT01-K17', N'Khoa học máy tính', 6.9),
('2022600106', N'Phạm Thùy Linh', '2004-05-18', N'Nữ', N'CNTT02-K17', N'Công nghệ thông tin', 8.7),
('2022600107', N'Lê Minh Quân', '2004-08-25', N'Nam', N'KTPM01-K17', N'Kỹ thuật phần mềm', 7.5),
('2022600108', N'Trần Bảo Ngọc', '2004-12-03', N'Nữ', N'HTTT02-K17', N'Hệ thống thông tin', 9.4),
('2022600109', N'Đinh Quốc Huy', '2004-04-14', N'Nam', N'DDT01-K17', N'Điện tử', 8.0),
('2022600110', N'Nguyễn Thu Uyên', '2004-10-28', N'Nữ', N'QTKD01-K17', N'Kinh tế', 8.3);
GO

-- Kiểm tra lại số lượng dữ liệu đã nạp
SELECT 'Account' AS TableName, COUNT(*) AS TotalRows FROM Account
UNION ALL
SELECT 'MonHoc', COUNT(*) FROM MonHoc
UNION ALL
SELECT 'GiangVien', COUNT(*) FROM GiangVien
UNION ALL
SELECT 'SinhVien', COUNT(*) FROM SinhVien;
GO
