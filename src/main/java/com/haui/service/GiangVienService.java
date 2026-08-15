package com.haui.service;

import com.haui.dao.GiangVienDAO;
import com.haui.model.GiangVien;

import java.util.List;

public class GiangVienService {
    private final GiangVienDAO giangVienDAO;

    public GiangVienService() {
        this.giangVienDAO = new GiangVienDAO();
    }

    public List<GiangVien> getAll() {
        return giangVienDAO.selectAll();
    }

    public GiangVien getById(String maGV) {
        return giangVienDAO.selectById(maGV);
    }

    public List<GiangVien> search(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAll();
        }
        return giangVienDAO.search(keyword.trim());
    }

    public boolean addGiangVien(GiangVien gv) throws Exception {
        validateGiangVien(gv);
        if (giangVienDAO.selectById(gv.getMaGV()) != null) {
            throw new Exception("Mã giảng viên '" + gv.getMaGV() + "' đã tồn tại!");
        }
        return giangVienDAO.insert(gv);
    }

    public boolean updateGiangVien(GiangVien gv) throws Exception {
        validateGiangVien(gv);
        if (giangVienDAO.selectById(gv.getMaGV()) == null) {
            throw new Exception("Giảng viên '" + gv.getMaGV() + "' không tồn tại để cập nhật!");
        }
        return giangVienDAO.update(gv);
    }

    public boolean deleteGiangVien(String maGV) throws Exception {
        if (maGV == null || maGV.trim().isEmpty()) {
            throw new Exception("Mã giảng viên không hợp lệ!");
        }
        return giangVienDAO.delete(maGV.trim());
    }

    private void validateGiangVien(GiangVien gv) throws Exception {
        if (gv == null) throw new Exception("Dữ liệu giảng viên rỗng!");
        if (gv.getMaGV() == null || gv.getMaGV().trim().isEmpty()) {
            throw new Exception("Mã giảng viên không được để trống!");
        }
        if (gv.getHoTen() == null || gv.getHoTen().trim().isEmpty()) {
            throw new Exception("Họ tên giảng viên không được để trống!");
        }
        if (gv.getKhoa() == null || gv.getKhoa().trim().isEmpty()) {
            throw new Exception("Khoa không được để trống!");
        }
    }
}
