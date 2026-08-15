-- =======================================================
-- HỆ THỐNG QUẢN LÝ ĐÀO TẠO ĐẠI HỌC CÔNG NGHIỆP HÀ NỘI (HaUI)
-- Database Script chuẩn PostgreSQL cho Supabase
-- =======================================================

-- 1. Xóa các bảng cũ nếu đã tồn tại
DROP TABLE IF EXISTS SinhVien CASCADE;
DROP TABLE IF EXISTS GiangVien CASCADE;
DROP TABLE IF EXISTS MonHoc CASCADE;
DROP TABLE IF EXISTS Account CASCADE;

-- 2. Tạo bảng Account (Tài khoản người dùng & Phân quyền)
CREATE TABLE Account (
    username VARCHAR(50) PRIMARY KEY,
    password VARCHAR(255) NOT NULL,
    fullName VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    role VARCHAR(20) DEFAULT 'USER'
);

-- 3. Tạo bảng MonHoc (Môn học đào tạo)
CREATE TABLE MonHoc (
    maMH VARCHAR(20) PRIMARY KEY,
    tenMH VARCHAR(100) NOT NULL,
    soTinChi INT NOT NULL CHECK (soTinChi > 0),
    chuyenNganh VARCHAR(100) NOT NULL
);

-- 4. Tạo bảng GiangVien (Giảng viên)
CREATE TABLE GiangVien (
    maGV VARCHAR(20) PRIMARY KEY,
    hoTen VARCHAR(100) NOT NULL,
    gioiTinh VARCHAR(10) NOT NULL,
    email VARCHAR(100),
    soDienThoai VARCHAR(20),
    hocVi VARCHAR(50),
    khoa VARCHAR(100) NOT NULL
);

-- 5. Tạo bảng SinhVien (Sinh viên)
CREATE TABLE SinhVien (
    maSV VARCHAR(20) PRIMARY KEY,
    hoTen VARCHAR(100) NOT NULL,
    ngaySinh DATE,
    gioiTinh VARCHAR(10) NOT NULL,
    lop VARCHAR(50) NOT NULL,
    khoa VARCHAR(100) NOT NULL,
    diemTB DOUBLE PRECISION CHECK (diemTB >= 0.0 AND diemTB <= 10.0)
);

-- =======================================================
-- 6. CHÈN DỮ LIỆU MẪU (10 DÒNG MỖI BẢNG)
-- =======================================================

-- 6.1 Dữ liệu mẫu cho bảng Account
INSERT INTO Account (username, password, fullName, email, role) VALUES
('admin', 'admin123', 'Quản Trị Viên Hệ Thống', 'admin@haui.edu.vn', 'ADMIN'),
('gv_hung', 'hung123', 'Nguyễn Văn Hùng', 'hungnv@haui.edu.vn', 'USER'),
('gv_lan', 'lan123', 'Trần Thị Lan', 'lantt@haui.edu.vn', 'USER'),
('gv_minh', 'minh123', 'Lê Quang Minh', 'minhlq@haui.edu.vn', 'USER'),
('gv_dung', 'dung123', 'Phạm Tiến Dũng', 'dungpt@haui.edu.vn', 'USER'),
('sv_nam', 'nam123', 'Vũ Thành Nam', 'namvt@gmail.com', 'USER'),
('sv_mai', 'mai123', 'Đỗ Phương Mai', 'maidp@gmail.com', 'USER'),
('sv_long', 'long123', 'Hoàng Đức Long', 'longhd@gmail.com', 'USER'),
('sv_hoa', 'hoa123', 'Bùi Thanh Hoa', 'hoabt@gmail.com', 'USER'),
('sv_tuan', 'tuan123', 'Ngô Anh Tuấn', 'tuanna@gmail.com', 'USER');

-- 6.2 Dữ liệu mẫu cho bảng MonHoc
INSERT INTO MonHoc (maMH, tenMH, soTinChi, chuyenNganh) VALUES
('IT6015', 'Lập trình Java nâng cao', 3, 'Công nghệ thông tin'),
('IT6020', 'Cơ sở dữ liệu', 4, 'Hệ thống thông tin'),
('IT6035', 'Cấu trúc dữ liệu và giải thuật', 3, 'Khoa học máy tính'),
('IT6040', 'Lập trình Web với Spring Boot', 3, 'Công nghệ thông tin'),
('IT6052', 'Mạng máy tính', 3, 'Kỹ thuật phần mềm'),
('IT6061', 'Phân tích thiết kế hệ thống', 3, 'Hệ thống thông tin'),
('EE4010', 'Kỹ thuật điện tử số', 3, 'Điện - Điện tử'),
('ME3025', 'Cơ học máy', 3, 'Cơ khí chế tạo'),
('BA2010', 'Kinh tế vi mô', 2, 'Quản trị kinh doanh'),
('FL1010', 'Tiếng Anh chuyên ngành CNTT', 2, 'Toàn trường');

-- 6.3 Dữ liệu mẫu cho bảng GiangVien
INSERT INTO GiangVien (maGV, hoTen, gioiTinh, email, soDienThoai, hocVi, khoa) VALUES
('GV001', 'Nguyễn Văn Hùng', 'Nam', 'hungnv@haui.edu.vn', '0912345678', 'Tiến sĩ', 'Công nghệ thông tin'),
('GV002', 'Trần Thị Lan', 'Nữ', 'lantt@haui.edu.vn', '0923456789', 'Thạc sĩ', 'Công nghệ thông tin'),
('GV003', 'Lê Quang Minh', 'Nam', 'minhlq@haui.edu.vn', '0934567890', 'Phó Giáo sư', 'Điện tử'),
('GV004', 'Phạm Tiến Dũng', 'Nam', 'dungpt@haui.edu.vn', '0945678901', 'Thạc sĩ', 'Kỹ thuật phần mềm'),
('GV005', 'Hoàng Thu Trang', 'Nữ', 'tranght@haui.edu.vn', '0956789012', 'Tiến sĩ', 'Hệ thống thông tin'),
('GV006', 'Vũ Đình Trọng', 'Nam', 'trongvd@haui.edu.vn', '0967890123', 'Thạc sĩ', 'Khoa học máy tính'),
('GV007', 'Đặng Phương Thảo', 'Nữ', 'thaodp@haui.edu.vn', '0978901234', 'Tiến sĩ', 'Công nghệ thông tin'),
('GV008', 'Bùi Đức Hạnh', 'Nam', 'hanhbd@haui.edu.vn', '0989012345', 'Thạc sĩ', 'Cơ khí'),
('GV009', 'Ngô Mai Phương', 'Nữ', 'phuongnm@haui.edu.vn', '0990123456', 'Thạc sĩ', 'Kinh tế'),
('GV010', 'Trịnh Xuân Bách', 'Nam', 'bachtx@haui.edu.vn', '0901234567', 'Tiến sĩ', 'Điện tử');

-- 6.4 Dữ liệu mẫu cho bảng SinhVien
INSERT INTO SinhVien (maSV, hoTen, ngaySinh, gioiTinh, lop, khoa, diemTB) VALUES
('2022600101', 'Vũ Thành Nam', '2004-03-15', 'Nam', 'CNTT01-K17', 'Công nghệ thông tin', 8.5),
('2022600102', 'Đỗ Phương Mai', '2004-07-22', 'Nữ', 'CNTT01-K17', 'Công nghệ thông tin', 9.1),
('2022600103', 'Hoàng Đức Long', '2004-11-05', 'Nam', 'KTPM02-K17', 'Kỹ thuật phần mềm', 7.8),
('2022600104', 'Bùi Thanh Hoa', '2004-01-30', 'Nữ', 'HTTT01-K17', 'Hệ thống thông tin', 8.2),
('2022600105', 'Ngô Anh Tuấn', '2004-09-12', 'Nam', 'KHMT01-K17', 'Khoa học máy tính', 6.9),
('2022600106', 'Phạm Thùy Linh', '2004-05-18', 'Nữ', 'CNTT02-K17', 'Công nghệ thông tin', 8.7),
('2022600107', 'Lê Minh Quân', '2004-08-25', 'Nam', 'KTPM01-K17', 'Kỹ thuật phần mềm', 7.5),
('2022600108', 'Trần Bảo Ngọc', '2004-12-03', 'Nữ', 'HTTT02-K17', 'Hệ thống thông tin', 9.4),
('2022600109', 'Đinh Quốc Huy', '2004-04-14', 'Nam', 'DDT01-K17', 'Điện tử', 8.0),
('2022600110', 'Nguyễn Thu Uyên', '2004-10-28', 'Nữ', 'QTKD01-K17', 'Kinh tế', 8.3);
