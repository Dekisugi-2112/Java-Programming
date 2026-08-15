package com.haui.view.panels;

import com.haui.model.SinhVien;
import com.haui.service.SinhVienService;
import com.haui.util.DateUtil;
import com.haui.util.DialogHelper;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Date;
import java.util.List;

public class SinhVienPanel extends JPanel {
    private final SinhVienService sinhVienService;

    private JTextField txtMaSV, txtHoTen, txtNgaySinh, txtLop, txtKhoa, txtDiemTB, txtSearch;
    private JComboBox<String> cbGioiTinh;
    private JTable tblSinhVien;
    private DefaultTableModel tableModel;
    private JButton btnThem, btnSua, btnXoa, btnLamMoi, btnTimKiem;

    public SinhVienPanel() {
        this.sinhVienService = new SinhVienService();
        initComponents();
        loadDataToTable();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // 1. Panel Form nhập liệu
        JPanel pnlForm = new JPanel(new GridBagLayout());
        pnlForm.setBorder(BorderFactory.createTitledBorder("Thông tin Sinh viên"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Hàng 1: Mã SV & Họ tên
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.1;
        pnlForm.add(new JLabel("Mã sinh viên:"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.4;
        txtMaSV = new JTextField();
        pnlForm.add(txtMaSV, gbc);

        gbc.gridx = 2; gbc.weightx = 0.1;
        pnlForm.add(new JLabel("Họ và tên:"), gbc);
        gbc.gridx = 3; gbc.weightx = 0.4;
        txtHoTen = new JTextField();
        pnlForm.add(txtHoTen, gbc);

        // Hàng 2: Ngày sinh & Giới tính
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0.1;
        pnlForm.add(new JLabel("Ngày sinh (dd/MM/yyyy):"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.4;
        txtNgaySinh = new JTextField();
        pnlForm.add(txtNgaySinh, gbc);

        gbc.gridx = 2; gbc.weightx = 0.1;
        pnlForm.add(new JLabel("Giới tính:"), gbc);
        gbc.gridx = 3; gbc.weightx = 0.4;
        cbGioiTinh = new JComboBox<>(new String[]{"Nam", "Nữ", "Khác"});
        pnlForm.add(cbGioiTinh, gbc);

        // Hàng 3: Lớp & Khoa
        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0.1;
        pnlForm.add(new JLabel("Lớp:"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.4;
        txtLop = new JTextField();
        pnlForm.add(txtLop, gbc);

        gbc.gridx = 2; gbc.weightx = 0.1;
        pnlForm.add(new JLabel("Khoa / Viện:"), gbc);
        gbc.gridx = 3; gbc.weightx = 0.4;
        txtKhoa = new JTextField();
        pnlForm.add(txtKhoa, gbc);

        // Hàng 4: Điểm TB
        gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 0.1;
        pnlForm.add(new JLabel("Điểm trung bình (hệ 10):"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.4;
        txtDiemTB = new JTextField();
        pnlForm.add(txtDiemTB, gbc);

        // 2. Panel Chức năng (Buttons & Tìm kiếm)
        JPanel pnlActions = new JPanel(new BorderLayout(10, 10));
        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));

        btnThem = new JButton("Thêm mới");
        btnThem.setBackground(new Color(40, 167, 69));
        btnThem.setForeground(Color.WHITE);

        btnSua = new JButton("Cập nhật");
        btnSua.setBackground(new Color(255, 193, 7));

        btnXoa = new JButton("Xóa");
        btnXoa.setBackground(new Color(220, 53, 69));
        btnXoa.setForeground(Color.WHITE);

        btnLamMoi = new JButton("Làm mới Form");

        pnlButtons.add(btnThem);
        pnlButtons.add(btnSua);
        pnlButtons.add(btnXoa);
        pnlButtons.add(btnLamMoi);

        JPanel pnlSearch = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 5));
        txtSearch = new JTextField(15);
        btnTimKiem = new JButton("Tìm kiếm");
        pnlSearch.add(new JLabel("Tìm theo tên/mã/lớp:"));
        pnlSearch.add(txtSearch);
        pnlSearch.add(btnTimKiem);

        pnlActions.add(pnlButtons, BorderLayout.WEST);
        pnlActions.add(pnlSearch, BorderLayout.EAST);

        // Ghép Form và Actions vào vùng NORTH
        JPanel pnlNorth = new JPanel(new BorderLayout());
        pnlNorth.add(pnlForm, BorderLayout.CENTER);
        pnlNorth.add(pnlActions, BorderLayout.SOUTH);
        add(pnlNorth, BorderLayout.NORTH);

        // 3. Panel Bảng dữ liệu JTable
        String[] columns = {"STT", "Mã SV", "Họ và Tên", "Ngày sinh", "Giới tính", "Lớp", "Khoa", "Điểm TB"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblSinhVien = new JTable(tableModel);
        tblSinhVien.setRowHeight(25);
        tblSinhVien.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(tblSinhVien);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Danh sách Sinh viên"));
        add(scrollPane, BorderLayout.CENTER);

        // Events
        tblSinhVien.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tblSinhVien.getSelectedRow() != -1) {
                displaySelectedRow(tblSinhVien.getSelectedRow());
            }
        });

        btnThem.addActionListener(e -> performAdd());
        btnSua.addActionListener(e -> performUpdate());
        btnXoa.addActionListener(e -> performDelete());
        btnLamMoi.addActionListener(e -> resetForm());
        btnTimKiem.addActionListener(e -> performSearch());
        txtSearch.addActionListener(e -> performSearch());
    }

    public void loadDataToTable() {
        tableModel.setRowCount(0);
        List<SinhVien> list = sinhVienService.getAll();
        int stt = 1;
        for (SinhVien sv : list) {
            tableModel.addRow(new Object[]{
                    stt++,
                    sv.getMaSV(),
                    sv.getHoTen(),
                    DateUtil.formatDate(sv.getNgaySinh()),
                    sv.getGioiTinh(),
                    sv.getLop(),
                    sv.getKhoa(),
                    sv.getDiemTB()
            });
        }
    }

    private void displaySelectedRow(int row) {
        txtMaSV.setText(tableModel.getValueAt(row, 1).toString());
        txtMaSV.setEditable(false);
        txtHoTen.setText(tableModel.getValueAt(row, 2).toString());
        txtNgaySinh.setText(tableModel.getValueAt(row, 3) != null ? tableModel.getValueAt(row, 3).toString() : "");
        cbGioiTinh.setSelectedItem(tableModel.getValueAt(row, 4).toString());
        txtLop.setText(tableModel.getValueAt(row, 5).toString());
        txtKhoa.setText(tableModel.getValueAt(row, 6).toString());
        txtDiemTB.setText(tableModel.getValueAt(row, 7).toString());
    }

    private void resetForm() {
        txtMaSV.setText("");
        txtMaSV.setEditable(true);
        txtHoTen.setText("");
        txtNgaySinh.setText("");
        cbGioiTinh.setSelectedIndex(0);
        txtLop.setText("");
        txtKhoa.setText("");
        txtDiemTB.setText("");
        tblSinhVien.clearSelection();
        loadDataToTable();
    }

    private SinhVien getModelFromForm() throws Exception {
        String maSV = txtMaSV.getText().trim();
        String hoTen = txtHoTen.getText().trim();
        Date ngaySinh = DateUtil.parseDate(txtNgaySinh.getText().trim());
        String gioiTinh = (String) cbGioiTinh.getSelectedItem();
        String lop = txtLop.getText().trim();
        String khoa = txtKhoa.getText().trim();
        double diemTB = 0.0;
        try {
            if (!txtDiemTB.getText().trim().isEmpty()) {
                diemTB = Double.parseDouble(txtDiemTB.getText().trim());
            }
        } catch (NumberFormatException e) {
            throw new Exception("Điểm trung bình phải là một số thực hợp lệ!");
        }

        return new SinhVien(maSV, hoTen, ngaySinh, gioiTinh, lop, khoa, diemTB);
    }

    private void performAdd() {
        try {
            SinhVien sv = getModelFromForm();
            if (sinhVienService.addSinhVien(sv)) {
                DialogHelper.showInfo(this, "Thêm sinh viên thành công!");
                resetForm();
            }
        } catch (Exception ex) {
            DialogHelper.showError(this, ex.getMessage());
        }
    }

    private void performUpdate() {
        try {
            SinhVien sv = getModelFromForm();
            if (sinhVienService.updateSinhVien(sv)) {
                DialogHelper.showInfo(this, "Cập nhật thông tin sinh viên thành công!");
                resetForm();
            }
        } catch (Exception ex) {
            DialogHelper.showError(this, ex.getMessage());
        }
    }

    private void performDelete() {
        String maSV = txtMaSV.getText().trim();
        if (maSV.isEmpty()) {
            DialogHelper.showWarning(this, "Vui lòng chọn sinh viên cần xóa từ bảng!");
            return;
        }
        if (DialogHelper.showConfirm(this, "Bạn có chắc chắn muốn xóa sinh viên [" + maSV + "] không?")) {
            try {
                if (sinhVienService.deleteSinhVien(maSV)) {
                    DialogHelper.showInfo(this, "Xóa sinh viên thành công!");
                    resetForm();
                }
            } catch (Exception ex) {
                DialogHelper.showError(this, ex.getMessage());
            }
        }
    }

    private void performSearch() {
        String keyword = txtSearch.getText().trim();
        tableModel.setRowCount(0);
        List<SinhVien> list = sinhVienService.search(keyword);
        int stt = 1;
        for (SinhVien sv : list) {
            tableModel.addRow(new Object[]{
                    stt++,
                    sv.getMaSV(),
                    sv.getHoTen(),
                    DateUtil.formatDate(sv.getNgaySinh()),
                    sv.getGioiTinh(),
                    sv.getLop(),
                    sv.getKhoa(),
                    sv.getDiemTB()
            });
        }
    }
}
