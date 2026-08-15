package com.haui.view.panels;

import com.haui.model.GiangVien;
import com.haui.service.GiangVienService;
import com.haui.util.DialogHelper;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class GiangVienPanel extends JPanel {
    private final GiangVienService giangVienService;

    private JTextField txtMaGV, txtHoTen, txtEmail, txtSoDienThoai, txtKhoa, txtSearch;
    private JComboBox<String> cbGioiTinh, cbHocVi;
    private JTable tblGiangVien;
    private DefaultTableModel tableModel;
    private JButton btnThem, btnSua, btnXoa, btnLamMoi, btnTimKiem;

    public GiangVienPanel() {
        this.giangVienService = new GiangVienService();
        initComponents();
        loadDataToTable();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // 1. Form nhập liệu
        JPanel pnlForm = new JPanel(new GridBagLayout());
        pnlForm.setBorder(BorderFactory.createTitledBorder("Thông tin Giảng viên"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Hàng 1: Mã GV & Họ tên
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.1;
        pnlForm.add(new JLabel("Mã giảng viên:"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.4;
        txtMaGV = new JTextField();
        pnlForm.add(txtMaGV, gbc);

        gbc.gridx = 2; gbc.weightx = 0.1;
        pnlForm.add(new JLabel("Họ và tên:"), gbc);
        gbc.gridx = 3; gbc.weightx = 0.4;
        txtHoTen = new JTextField();
        pnlForm.add(txtHoTen, gbc);

        // Hàng 2: Giới tính & Học vị
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0.1;
        pnlForm.add(new JLabel("Giới tính:"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.4;
        cbGioiTinh = new JComboBox<>(new String[]{"Nam", "Nữ", "Khác"});
        pnlForm.add(cbGioiTinh, gbc);

        gbc.gridx = 2; gbc.weightx = 0.1;
        pnlForm.add(new JLabel("Học vị:"), gbc);
        gbc.gridx = 3; gbc.weightx = 0.4;
        cbHocVi = new JComboBox<>(new String[]{"Cử nhân", "Thạc sĩ", "Tiến sĩ", "Phó Giáo sư", "Giáo sư"});
        pnlForm.add(cbHocVi, gbc);

        // Hàng 3: Email & SĐT
        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0.1;
        pnlForm.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.4;
        txtEmail = new JTextField();
        pnlForm.add(txtEmail, gbc);

        gbc.gridx = 2; gbc.weightx = 0.1;
        pnlForm.add(new JLabel("Số điện thoại:"), gbc);
        gbc.gridx = 3; gbc.weightx = 0.4;
        txtSoDienThoai = new JTextField();
        pnlForm.add(txtSoDienThoai, gbc);

        // Hàng 4: Khoa
        gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 0.1;
        pnlForm.add(new JLabel("Khoa / Bộ môn:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 3; gbc.weightx = 0.9;
        txtKhoa = new JTextField();
        pnlForm.add(txtKhoa, gbc);

        // 2. Chức năng Actions
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
        pnlSearch.add(new JLabel("Tìm GV:"));
        pnlSearch.add(txtSearch);
        pnlSearch.add(btnTimKiem);

        pnlActions.add(pnlButtons, BorderLayout.WEST);
        pnlActions.add(pnlSearch, BorderLayout.EAST);

        JPanel pnlNorth = new JPanel(new BorderLayout());
        pnlNorth.add(pnlForm, BorderLayout.CENTER);
        pnlNorth.add(pnlActions, BorderLayout.SOUTH);
        add(pnlNorth, BorderLayout.NORTH);

        // 3. JTable
        String[] columns = {"STT", "Mã GV", "Họ và Tên", "Giới tính", "Học vị", "Khoa", "Email", "Số ĐT"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblGiangVien = new JTable(tableModel);
        tblGiangVien.setRowHeight(25);
        tblGiangVien.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(tblGiangVien);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Danh sách Giảng viên"));
        add(scrollPane, BorderLayout.CENTER);

        // Events
        tblGiangVien.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tblGiangVien.getSelectedRow() != -1) {
                displaySelectedRow(tblGiangVien.getSelectedRow());
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
        List<GiangVien> list = giangVienService.getAll();
        int stt = 1;
        for (GiangVien gv : list) {
            tableModel.addRow(new Object[]{
                    stt++,
                    gv.getMaGV(),
                    gv.getHoTen(),
                    gv.getGioiTinh(),
                    gv.getHocVi(),
                    gv.getKhoa(),
                    gv.getEmail(),
                    gv.getSoDienThoai()
            });
        }
    }

    private void displaySelectedRow(int row) {
        txtMaGV.setText(tableModel.getValueAt(row, 1).toString());
        txtMaGV.setEditable(false);
        txtHoTen.setText(tableModel.getValueAt(row, 2).toString());
        cbGioiTinh.setSelectedItem(tableModel.getValueAt(row, 3).toString());
        cbHocVi.setSelectedItem(tableModel.getValueAt(row, 4) != null ? tableModel.getValueAt(row, 4).toString() : "Thạc sĩ");
        txtKhoa.setText(tableModel.getValueAt(row, 5).toString());
        txtEmail.setText(tableModel.getValueAt(row, 6) != null ? tableModel.getValueAt(row, 6).toString() : "");
        txtSoDienThoai.setText(tableModel.getValueAt(row, 7) != null ? tableModel.getValueAt(row, 7).toString() : "");
    }

    private void resetForm() {
        txtMaGV.setText("");
        txtMaGV.setEditable(true);
        txtHoTen.setText("");
        cbGioiTinh.setSelectedIndex(0);
        cbHocVi.setSelectedIndex(1);
        txtKhoa.setText("");
        txtEmail.setText("");
        txtSoDienThoai.setText("");
        tblGiangVien.clearSelection();
        loadDataToTable();
    }

    private GiangVien getModelFromForm() {
        String maGV = txtMaGV.getText().trim();
        String hoTen = txtHoTen.getText().trim();
        String gioiTinh = (String) cbGioiTinh.getSelectedItem();
        String hocVi = (String) cbHocVi.getSelectedItem();
        String khoa = txtKhoa.getText().trim();
        String email = txtEmail.getText().trim();
        String soDienThoai = txtSoDienThoai.getText().trim();
        return new GiangVien(maGV, hoTen, gioiTinh, email, soDienThoai, hocVi, khoa);
    }

    private void performAdd() {
        try {
            GiangVien gv = getModelFromForm();
            if (giangVienService.addGiangVien(gv)) {
                DialogHelper.showInfo(this, "Thêm giảng viên thành công!");
                resetForm();
            }
        } catch (Exception ex) {
            DialogHelper.showError(this, ex.getMessage());
        }
    }

    private void performUpdate() {
        try {
            GiangVien gv = getModelFromForm();
            if (giangVienService.updateGiangVien(gv)) {
                DialogHelper.showInfo(this, "Cập nhật giảng viên thành công!");
                resetForm();
            }
        } catch (Exception ex) {
            DialogHelper.showError(this, ex.getMessage());
        }
    }

    private void performDelete() {
        String maGV = txtMaGV.getText().trim();
        if (maGV.isEmpty()) {
            DialogHelper.showWarning(this, "Vui lòng chọn giảng viên cần xóa từ bảng!");
            return;
        }
        if (DialogHelper.showConfirm(this, "Bạn có chắc muốn xóa giảng viên [" + maGV + "] không?")) {
            try {
                if (giangVienService.deleteGiangVien(maGV)) {
                    DialogHelper.showInfo(this, "Xóa giảng viên thành công!");
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
        List<GiangVien> list = giangVienService.search(keyword);
        int stt = 1;
        for (GiangVien gv : list) {
            tableModel.addRow(new Object[]{
                    stt++,
                    gv.getMaGV(),
                    gv.getHoTen(),
                    gv.getGioiTinh(),
                    gv.getHocVi(),
                    gv.getKhoa(),
                    gv.getEmail(),
                    gv.getSoDienThoai()
            });
        }
    }
}
