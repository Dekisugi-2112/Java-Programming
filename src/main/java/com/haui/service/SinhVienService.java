package com.haui.service;

import com.haui.dao.SinhVienDAO;
import com.haui.model.SinhVien;

import java.util.List;

public class SinhVienService {
    private final SinhVienDAO sinhVienDAO;

    public SinhVienService() {
        this.sinhVienDAO = new SinhVienDAO();
    }

    public List<SinhVien> getAll() {
        return sinhVienDAO.selectAll();
    }

    public SinhVien getById(String maSV) {
        return sinhVienDAO.selectById(maSV);
    }

    public List<SinhVien> search(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAll();
        }
        return sinhVienDAO.search(keyword.trim());
    }

    public boolean addSinhVien(SinhVien sv) throws Exception {
        validateSinhVien(sv);
        if (sinhVienDAO.selectById(sv.getMaSV()) != null) {
            throw new Exception("Mã sinh viên '" + sv.getMaSV() + "' đã tồn tại!");
        }
        return sinhVienDAO.insert(sv);
    }

    public boolean updateSinhVien(SinhVien sv) throws Exception {
        validateSinhVien(sv);
        if (sinhVienDAO.selectById(sv.getMaSV()) == null) {
            throw new Exception("Sinh viên '" + sv.getMaSV() + "' không tồn tại để cập nhật!");
        }
        return sinhVienDAO.update(sv);
    }

    public boolean deleteSinhVien(String maSV) throws Exception {
        if (maSV == null || maSV.trim().isEmpty()) {
            throw new Exception("Mã sinh viên không hợp lệ!");
        }
        return sinhVienDAO.delete(maSV.trim());
    }

    private void validateSinhVien(SinhVien sv) throws Exception {
        if (sv == null) throw new Exception("Dữ liệu sinh viên rỗng!");
        if (sv.getMaSV() == null || sv.getMaSV().trim().isEmpty()) {
            throw new Exception("Mã sinh viên không được để trống!");
        }
        if (sv.getHoTen() == null || sv.getHoTen().trim().isEmpty()) {
            throw new Exception("Họ tên sinh viên không được để trống!");
        }
        if (sv.getLop() == null || sv.getLop().trim().isEmpty()) {
            throw new Exception("Lớp không được để trống!");
        }
        if (sv.getKhoa() == null || sv.getKhoa().trim().isEmpty()) {
            throw new Exception("Khoa không được để trống!");
        }
        if (sv.getDiemTB() < 0.0 || sv.getDiemTB() > 10.0) {
            throw new Exception("Điểm trung bình phải nằm trong khoảng từ 0.0 đến 10.0!");
        }
    }
}
