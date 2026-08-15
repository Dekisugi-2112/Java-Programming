package com.haui.view.panels;

import com.haui.model.MonHoc;
import com.haui.service.MonHocService;
import com.haui.util.DialogHelper;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class MonHocPanel extends JPanel {
    private final MonHocService monHocService;

    private JTextField txtMaMH, txtTenMH, txtSoTinChi, txtChuyenNganh, txtSearch;
    private JTable tblMonHoc;
    private DefaultTableModel tableModel;
    private JButton btnThem, btnSua, btnXoa, btnLamMoi, btnTimKiem;

    public MonHocPanel() {
        this.monHocService = new MonHocService();
        initComponents();
        loadDataToTable();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // 1. Form nhập liệu
        JPanel pnlForm = new JPanel(new GridBagLayout());
        pnlForm.setBorder(BorderFactory.createTitledBorder("Thông tin Môn học"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Hàng 1: Mã MH & Tên MH
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.1;
        pnlForm.add(new JLabel("Mã môn học:"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.4;
        txtMaMH = new JTextField();
        pnlForm.add(txtMaMH, gbc);

        gbc.gridx = 2; gbc.weightx = 0.1;
        pnlForm.add(new JLabel("Tên môn học:"), gbc);
        gbc.gridx = 3; gbc.weightx = 0.4;
        txtTenMH = new JTextField();
        pnlForm.add(txtTenMH, gbc);

        // Hàng 2: Số tín chỉ & Chuyên ngành
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0.1;
        pnlForm.add(new JLabel("Số tín chỉ:"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.4;
        txtSoTinChi = new JTextField();
        pnlForm.add(txtSoTinChi, gbc);

        gbc.gridx = 2; gbc.weightx = 0.1;
        pnlForm.add(new JLabel("Chuyên ngành / Khoa:"), gbc);
        gbc.gridx = 3; gbc.weightx = 0.4;
        txtChuyenNganh = new JTextField();
        pnlForm.add(txtChuyenNganh, gbc);

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
        pnlSearch.add(new JLabel("Tìm môn học:"));
        pnlSearch.add(txtSearch);
        pnlSearch.add(btnTimKiem);

        pnlActions.add(pnlButtons, BorderLayout.WEST);
        pnlActions.add(pnlSearch, BorderLayout.EAST);

        JPanel pnlNorth = new JPanel(new BorderLayout());
        pnlNorth.add(pnlForm, BorderLayout.CENTER);
        pnlNorth.add(pnlActions, BorderLayout.SOUTH);
        add(pnlNorth, BorderLayout.NORTH);

        // 3. JTable
        String[] columns = {"STT", "Mã MH", "Tên Môn Học", "Số Tín Chỉ", "Chuyên Ngành"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblMonHoc = new JTable(tableModel);
        tblMonHoc.setRowHeight(25);
        tblMonHoc.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(tblMonHoc);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Danh mục Môn học"));
        add(scrollPane, BorderLayout.CENTER);

        // Events
        tblMonHoc.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tblMonHoc.getSelectedRow() != -1) {
                displaySelectedRow(tblMonHoc.getSelectedRow());
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
        List<MonHoc> list = monHocService.getAll();
        int stt = 1;
        for (MonHoc mh : list) {
            tableModel.addRow(new Object[]{
                    stt++,
                    mh.getMaMH(),
                    mh.getTenMH(),
                    mh.getSoTinChi(),
                    mh.getChuyenNganh()
            });
        }
    }

    private void displaySelectedRow(int row) {
        txtMaMH.setText(tableModel.getValueAt(row, 1).toString());
        txtMaMH.setEditable(false);
        txtTenMH.setText(tableModel.getValueAt(row, 2).toString());
        txtSoTinChi.setText(tableModel.getValueAt(row, 3).toString());
        txtChuyenNganh.setText(tableModel.getValueAt(row, 4).toString());
    }

    private void resetForm() {
        txtMaMH.setText("");
        txtMaMH.setEditable(true);
        txtTenMH.setText("");
        txtSoTinChi.setText("");
        txtChuyenNganh.setText("");
        tblMonHoc.clearSelection();
        loadDataToTable();
    }

    private MonHoc getModelFromForm() throws Exception {
        String maMH = txtMaMH.getText().trim();
        String tenMH = txtTenMH.getText().trim();
        int soTinChi = 0;
        try {
            soTinChi = Integer.parseInt(txtSoTinChi.getText().trim());
        } catch (NumberFormatException e) {
            throw new Exception("Số tín chỉ phải là một số nguyên dương!");
        }
        String chuyenNganh = txtChuyenNganh.getText().trim();
        return new MonHoc(maMH, tenMH, soTinChi, chuyenNganh);
    }

    private void performAdd() {
        try {
            MonHoc mh = getModelFromForm();
            if (monHocService.addMonHoc(mh)) {
                DialogHelper.showInfo(this, "Thêm môn học thành công!");
                resetForm();
            }
        } catch (Exception ex) {
            DialogHelper.showError(this, ex.getMessage());
        }
    }

    private void performUpdate() {
        try {
            MonHoc mh = getModelFromForm();
            if (monHocService.updateMonHoc(mh)) {
                DialogHelper.showInfo(this, "Cập nhật môn học thành công!");
                resetForm();
            }
        } catch (Exception ex) {
            DialogHelper.showError(this, ex.getMessage());
        }
    }

    private void performDelete() {
        String maMH = txtMaMH.getText().trim();
        if (maMH.isEmpty()) {
            DialogHelper.showWarning(this, "Vui lòng chọn môn học cần xóa từ bảng!");
            return;
        }
        if (DialogHelper.showConfirm(this, "Bạn có chắc muốn xóa môn học [" + maMH + "] không?")) {
            try {
                if (monHocService.deleteMonHoc(maMH)) {
                    DialogHelper.showInfo(this, "Xóa môn học thành công!");
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
        List<MonHoc> list = monHocService.search(keyword);
        int stt = 1;
        for (MonHoc mh : list) {
            tableModel.addRow(new Object[]{
                    stt++,
                    mh.getMaMH(),
                    mh.getTenMH(),
                    mh.getSoTinChi(),
                    mh.getChuyenNganh()
            });
        }
    }
}
