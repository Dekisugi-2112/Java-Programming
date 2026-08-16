# 🎓 HỆ THỐNG QUẢN LÝ ĐÀO TẠO - ĐẠI HỌC CÔNG NGHIỆP HÀ NỘI (HaUI-MANAGE)

Ứng dụng Desktop quản lý đào tạo (Sinh viên, Giảng viên, Môn học, Tài khoản) được xây dựng theo **Kiến trúc 3 lớp (3-Tier Architecture)** chuẩn mực, sử dụng **Java Swing**, thư viện giao diện phẳng hiện đại **FlatLaf** và kết nối cơ sở dữ liệu qua **JDBC (PostgreSQL Supabase Cloud)**.

---

## 📌 1. Tính Năng Chính

### 🔐 1. Xác thực & Phân quyền (Authentication)
- **Đăng nhập:** Xác thực người dùng, hỗ trợ phím tắt `Enter`, phân quyền `ADMIN` và `USER`.
- **Đăng ký:** Tạo tài khoản mới, kiểm tra trùng lặp `username`, tự động mã hóa và kiểm tra khớp mật khẩu.
- **Đăng xuất:** Thoát phiên làm việc an toàn và quay về màn hình đăng nhập.

### 👨‍🎓 2. Quản lý Sinh viên (SinhVien)
- Hiển thị danh sách sinh viên dưới dạng bảng trực quan (`JTable`).
- Thêm mới, cập nhật thông tin và xóa sinh viên.
- Ràng buộc & Validation: Kiểm tra trùng mã sinh viên, định dạng ngày sinh (`dd/MM/yyyy`), giới hạn điểm trung bình (`0.0 - 10.0`).
- Tìm kiếm nhanh sinh viên theo **Mã SV, Họ tên, Lớp hoặc Khoa**.

### 👨‍🏫 3. Quản lý Giảng viên (GiangVien)
- Quản lý danh sách giảng viên kèm học vị (*Cử nhân, Thạc sĩ, Tiến sĩ, Phó Giáo sư, Giáo sư*).
- Thêm, sửa, xóa giảng viên.
- Tìm kiếm giảng viên theo tên, mã giảng viên hoặc bộ môn/khoa.

### 📚 4. Quản lý Môn học (MonHoc)
- Quản lý danh mục môn học đào tạo và số tín chỉ.
- Thêm mới, cập nhật và xóa môn học.
- Tìm kiếm môn học theo mã môn, tên môn hoặc chuyên ngành.

---

## 🏛️ 2. Kiến Trúc Dự Án (3-Tier Architecture)

Dự án được phân chia thành các tầng độc lập, đảm bảo tính đóng gói, dễ bảo trì và mở rộng:

```text
HAUI-MANAGE/
│
├── pom.xml                                      # Quản lý dependencies (Maven)
├── README.md                                    # Tài liệu hướng dẫn dự án
│
├── database/                                    # Script khởi tạo cơ sở dữ liệu
│   └── supabase_schema_and_data.sql             # Script chuẩn PostgreSQL (Dành cho Supabase Cloud)
│
└── src/
    └── main/
        ├── java/com/haui/
        │   ├── model/                           # [TẦNG 1: MODEL / ENTITY]
        │   │   ├── Account.java                 # Đối tượng Tài khoản người dùng
        │   │   ├── SinhVien.java                # Đối tượng Sinh viên
        │   │   ├── GiangVien.java               # Đối tượng Giảng viên
        │   │   └── MonHoc.java                  # Đối tượng Môn học
        │   │
        │   ├── dao/                             # [TẦNG 2: DATA ACCESS OBJECT - TRUY VẤN CSDL]
        │   │   ├── BaseDAO.java                 # Generic interface cho các thao tác CRUD
        │   │   ├── AccountDAO.java              # Thao tác CSDL bảng Account
        │   │   ├── SinhVienDAO.java             # Thao tác CSDL bảng SinhVien
        │   │   ├── GiangVienDAO.java            # Thao tác CSDL bảng GiangVien
        │   │   └── MonHocDAO.java               # Thao tác CSDL bảng MonHoc
        │   │
        │   ├── service/                         # [TẦNG 3: SERVICE - XỬ LÝ NGHIỆP VỤ & VALIDATION]
        │   │   ├── AccountService.java          # Xử lý logic đăng nhập, đăng ký
        │   │   ├── SinhVienService.java         # Xử lý logic kiểm tra dữ liệu sinh viên
        │   │   ├── GiangVienService.java        # Xử lý logic giảng viên
        │   │   └── MonHocService.java           # Xử lý logic môn học
        │   │
        │   ├── view/                            # [TẦNG GIAO DIỆN: JAVA SWING + FLATLAF]
        │   │   ├── auth/                        # Giao diện xác thực
        │   │   │   ├── LoginFrame.java          # Cửa sổ Đăng nhập
        │   │   │   └── RegisterFrame.java       # Cửa sổ Đăng ký
        │   │   ├── main/
        │   │   │   └── MainDashboardFrame.java  # Giao diện chính chứa các Tab quản lý
        │   │   └── panels/                      # Các User Control / Panel con
        │   │       ├── SinhVienPanel.java       # Panel Quản lý Sinh viên
        │   │       ├── GiangVienPanel.java      # Panel Quản lý Giảng viên
        │   │       └── MonHocPanel.java         # Panel Quản lý Môn học
        │   │
        │   ├── util/                            # [TIỆN ÍCH DÙNG CHUNG]
        │   │   ├── DatabaseConnection.java      # Quản lý kết nối JDBC
        │   │   ├── DialogHelper.java            # Hộp thoại thông báo, cảnh báo chuẩn
        │   │   └── DateUtil.java                # Chuyển đổi định dạng ngày tháng
        │   │
        │   └── Main.java                        # Entry Point khởi chạy ứng dụng
        │
        └── resources/
            ├── config.properties.example        # File mẫu cấu hình CSDL
            └── config.properties                # File cấu hình thông số CSDL thực tế
```

---

## 🛠️ 3. Công Nghệ Sử Dụng

- **Ngôn ngữ:** Java (Tương thích Java 17, 21, 24 LTS).
- **Quản lý dự án & Build Tool:** Apache Maven.
- **Thư viện Giao diện:** [FlatLaf](https://www.formdev.com/flatlaf/) `3.4.1` (Giao diện phẳng phong cách Clean Modern, hỗ trợ Dark/Light Theme).
- **Cơ sở dữ liệu:** PostgreSQL trên **Supabase Cloud**.
- **Driver kết nối:** `org.postgresql:postgresql:42.7.2`

---

## 🚀 4. Hướng Dẫn Cài Đặt & Khởi Chạy

### Bước 1: Khởi tạo Cơ Sở Dữ Liệu trên Supabase
1. Đăng nhập [Supabase Dashboard](https://supabase.com/dashboard).
2. Vào mục **SQL Editor** (`>_`).
3. Mở file [supabase_schema_and_data.sql](database/supabase_schema_and_data.sql), copy toàn bộ và bấm **Run**.

---

### Bước 2: Cấu hình thông tin kết nối
Mở file [config.properties](src/main/resources/config.properties) (hoặc tạo từ file mẫu `config.properties.example`) và điền thông số kết nối qua **Connection Pooler**:

```properties
db.url=jdbc:postgresql://aws-0-ap-southeast-1.pooler.supabase.com:6543/postgres?sslmode=require&connectTimeout=10
db.user=postgres.YOUR_PROJECT_REF
db.password=YOUR_DATABASE_PASSWORD
```

---

### Bước 3: Tải thư viện Maven trong IntelliJ IDEA
1. Mở dự án trong **IntelliJ IDEA**.
2. Nhấp chuột phải vào file [pom.xml](pom.xml) ➔ Chọn **Maven** ➔ **Reload project** (hoặc phím tắt `Ctrl + Shift + O`).

---

### Bước 4: Chạy ứng dụng
1. Điều hướng tới file [Main.java](src/main/java/com/haui/Main.java).
2. Nhấp chuột phải ➔ Chọn **`Run 'Main.main()'`** (hoặc tổ hợp `Ctrl + Shift + F10`).

---

## 🔑 5. Tài Khoản Mẫu Đăng Nhập

Hệ thống đã có sẵn 10 tài khoản mẫu trong CSDL để thử nghiệm:

| Quyền hạn | Tên đăng nhập | Mật khẩu | Họ và Tên |
| :--- | :--- | :--- | :--- |
| **Quản trị viên (Admin)** | `admin` | `admin123` | Quản Trị Viên Hệ Thống |
| **Giảng viên** | `gv_hung` | `hung123` | Nguyễn Văn Hùng |
| **Giảng viên** | `gv_lan` | `lan123` | Trần Thị Lan |
| **Sinh viên** | `sv_nam` | `nam123` | Vũ Thành Nam |
| **Sinh viên** | `sv_mai` | `mai123` | Đỗ Phương Mai |

---

## 👨‍💻 Thông Tin Dự Án
- **Môn học:** Lập trình Java / Lập trình hướng đối tượng.
- **Trường:** Trường Đại học Công nghiệp Hà Nội (HaUI).
- **Mô hình kiến trúc:** 3-Tier Layered Architecture (Model - DAO - Service - View).
