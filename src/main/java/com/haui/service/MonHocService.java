package com.haui.service;

import com.haui.dao.MonHocDAO;
import com.haui.model.MonHoc;

import java.util.List;

public class MonHocService {
    private final MonHocDAO monHocDAO;

    public MonHocService() {
        this.monHocDAO = new MonHocDAO();
    }

    public List<MonHoc> getAll() {
        return monHocDAO.selectAll();
    }

    public MonHoc getById(String maMH) {
        return monHocDAO.selectById(maMH);
    }

    public List<MonHoc> search(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAll();
        }
        return monHocDAO.search(keyword.trim());
    }

    public boolean addMonHoc(MonHoc mh) throws Exception {
        validateMonHoc(mh);
        if (monHocDAO.selectById(mh.getMaMH()) != null) {
            throw new Exception("Mã môn học '" + mh.getMaMH() + "' đã tồn tại!");
        }
        return monHocDAO.insert(mh);
    }

    public boolean updateMonHoc(MonHoc mh) throws Exception {
        validateMonHoc(mh);
        if (monHocDAO.selectById(mh.getMaMH()) == null) {
            throw new Exception("Môn học '" + mh.getMaMH() + "' không tồn tại để cập nhật!");
        }
        return monHocDAO.update(mh);
    }

    public boolean deleteMonHoc(String maMH) throws Exception {
        if (maMH == null || maMH.trim().isEmpty()) {
            throw new Exception("Mã môn học không hợp lệ!");
        }
        return monHocDAO.delete(maMH.trim());
    }

    private void validateMonHoc(MonHoc mh) throws Exception {
        if (mh == null) throw new Exception("Dữ liệu môn học rỗng!");
        if (mh.getMaMH() == null || mh.getMaMH().trim().isEmpty()) {
            throw new Exception("Mã môn học không được để trống!");
        }
        if (mh.getTenMH() == null || mh.getTenMH().trim().isEmpty()) {
            throw new Exception("Tên môn học không được để trống!");
        }
        if (mh.getSoTinChi() <= 0) {
            throw new Exception("Số tín chỉ phải lớn hơn 0!");
        }
        if (mh.getChuyenNganh() == null || mh.getChuyenNganh().trim().isEmpty()) {
            throw new Exception("Chuyên ngành không được để trống!");
        }
    }
}
