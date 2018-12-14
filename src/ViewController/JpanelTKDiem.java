/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ViewController;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author NhatThanh
 */
public class JpanelTKDiem extends javax.swing.JPanel {

    /**
     * Creates new form JpanelTKDiem
     */
    ArrayList<Point> listsv = qlpoint();
    
    DefaultTableModel model = new DefaultTableModel();
    private int index;
    Connection con;
    public JpanelTKDiem() {
        initComponents();
        model = (DefaultTableModel) table.getModel();
        index = 0;
        settable();
    }
    public void settable() {
        model.setRowCount(0);
        for (Point x : listsv) {
            model.addRow(new Object[]{x.getMasv(), x.getName(), x.getToan(), x.getVatly(), x.getHoahoc(), x.getSinhhoc(),
                x.getVanhoc(), x.getTienganh(), x.getLichsu(), x.getDialy(),
                (x.getToan()+x.getVatly()+x.getHoahoc()+x.getSinhhoc()+x.getVanhoc()+x.getTienganh()+x.getLichsu()+x.getDialy())/8});
        }
    }
    public ArrayList<Point> qlpoint() {
        ArrayList<Point> p = new ArrayList<>();
        String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        String url = "jdbc:sqlserver://localhost:1433;databaseName=Quanlyhocsinh";
        String username = "sa";
        String password = "123";
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url, username, password);
            Statement st = con.createStatement();
                       String sql = "SELECT STUDENT.MAHS, STUDENT.NAME, LOP.TENLOP, \n" +
                            "DIEM.TOAN, DIEM.VATLY, DIEM.HOAHOC, DIEM.SINHHOC, DIEM.NGUVAN, DIEM.TIENGANH, DIEM.LICHSU, DIEM.DIALY \n" +
                            "FROM STUDENT, DIEM, LOP \n" +
                            "WHERE DIEM.MAHS=STUDENT.MAHS \n" +
                            "AND STUDENT.MALOP=LOP.MALOP AND LOP.MALOP='" + JframeLogIn.maloplogin + "'\n" +
                            "ORDER BY STUDENT.MAHS ASC";
           
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                p.add(new Point(rs.getString(1), rs.getString(2), rs.getString(3), rs.getDouble(4), rs.getDouble(5), 
                        rs.getDouble(6), rs.getDouble(7), rs.getDouble(8), rs.getDouble(9), rs.getDouble(10), rs.getDouble(11)));
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return p;
    }
    public ArrayList<Point> pointHSG() {
        ArrayList<Point> p = new ArrayList<>();
        String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        String url = "jdbc:sqlserver://localhost:1433;databaseName=Quanlyhocsinh";
        String username = "sa";
        String password = "123";
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url, username, password);
            Statement st = con.createStatement();
                       String sql = "SELECT STUDENT.MAHS, STUDENT.NAME, LOP.TENLOP,\n" +
                "DIEM.TOAN, DIEM.VATLY, DIEM.HOAHOC, DIEM.SINHHOC, DIEM.NGUVAN, DIEM.TIENGANH, DIEM.LICHSU, DIEM.DIALY\n" +
                "FROM STUDENT, DIEM, LOP \n" +
                "WHERE DIEM.MAHS=STUDENT.MAHS \n" +
                "AND STUDENT.MALOP=LOP.MALOP\n" +
                "AND LOP.MALOP='" + JframeLogIn.maloplogin + "' AND (TOAN+VATLY+HOAHOC+SINHHOC+NGUVAN+TIENGANH+LICHSU+DIALY)/8 >=8\n" +
                "ORDER BY STUDENT.MAHS ASC";
           
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                p.add(new Point(rs.getString(1), rs.getString(2), rs.getString(3), rs.getDouble(4), rs.getDouble(5), 
                        rs.getDouble(6), rs.getDouble(7), rs.getDouble(8), rs.getDouble(9), rs.getDouble(10), rs.getDouble(11)));
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return p;
    }
    public ArrayList<Point> pointHSK() {
        ArrayList<Point> p = new ArrayList<>();
        String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        String url = "jdbc:sqlserver://localhost:1433;databaseName=Quanlyhocsinh";
        String username = "sa";
        String password = "123";
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url, username, password);
            Statement st = con.createStatement();
                       String sql = "SELECT STUDENT.MAHS, STUDENT.NAME, LOP.TENLOP,\n" +
                "DIEM.TOAN, DIEM.VATLY, DIEM.HOAHOC, DIEM.SINHHOC, DIEM.NGUVAN, DIEM.TIENGANH, DIEM.LICHSU, DIEM.DIALY\n" +
                "FROM STUDENT, DIEM, LOP \n" +
                "WHERE DIEM.MAHS=STUDENT.MAHS \n" +
                "AND STUDENT.MALOP=LOP.MALOP\n" +
                "AND LOP.MALOP='" + JframeLogIn.maloplogin + "' AND (TOAN+VATLY+HOAHOC+SINHHOC+NGUVAN+TIENGANH+LICHSU+DIALY)/8 >=6.5\n" +
                "AND (TOAN+VATLY+HOAHOC+SINHHOC+NGUVAN+TIENGANH+LICHSU+DIALY)/8 <8\n"+
                "ORDER BY STUDENT.MAHS ASC";
           
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                p.add(new Point(rs.getString(1), rs.getString(2), rs.getString(3), rs.getDouble(4), rs.getDouble(5), 
                        rs.getDouble(6), rs.getDouble(7), rs.getDouble(8), rs.getDouble(9), rs.getDouble(10), rs.getDouble(11)));
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return p;
    }
    public ArrayList<Point> pointHTB() {
        ArrayList<Point> p = new ArrayList<>();
        String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        String url = "jdbc:sqlserver://localhost:1433;databaseName=Quanlyhocsinh";
        String username = "sa";
        String password = "123";
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url, username, password);
            Statement st = con.createStatement();
                       String sql = "SELECT STUDENT.MAHS, STUDENT.NAME, LOP.TENLOP,\n" +
                "DIEM.TOAN, DIEM.VATLY, DIEM.HOAHOC, DIEM.SINHHOC, DIEM.NGUVAN, DIEM.TIENGANH, DIEM.LICHSU, DIEM.DIALY\n" +
                "FROM STUDENT, DIEM, LOP \n" +
                "WHERE DIEM.MAHS=STUDENT.MAHS \n" +
                "AND STUDENT.MALOP=LOP.MALOP\n" +
                "AND LOP.MALOP='" + JframeLogIn.maloplogin + "' AND (TOAN+VATLY+HOAHOC+SINHHOC+NGUVAN+TIENGANH+LICHSU+DIALY)/8 >=5\n" +
                "AND (TOAN+VATLY+HOAHOC+SINHHOC+NGUVAN+TIENGANH+LICHSU+DIALY)/8 <6.5\n"+
                "ORDER BY STUDENT.MAHS ASC";
           
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                p.add(new Point(rs.getString(1), rs.getString(2), rs.getString(3), rs.getDouble(4), rs.getDouble(5), 
                        rs.getDouble(6), rs.getDouble(7), rs.getDouble(8), rs.getDouble(9), rs.getDouble(10), rs.getDouble(11)));
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return p;
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();

        jLabel3.setText("Bảng điểm học sinh:");

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã SV", "Họ Và Tên", "Toán", "Vật Lý", "Hóa Học", "Sinh Học", "Ngữ Văn", "Tiếng Anh", "Lịch Sử", "Địa Lý", "ĐTB"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(table);
        if (table.getColumnModel().getColumnCount() > 0) {
            table.getColumnModel().getColumn(0).setPreferredWidth(80);
            table.getColumnModel().getColumn(1).setPreferredWidth(150);
        }

        jPanel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        jButton1.setText("DS Học sinh giỏi");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton3.setText("DS Học sinh khá");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton7.setText("DS Học sinh trung bình");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton8.setText("Bảng điểm tất cả học sinh");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton8)
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addGap(18, 18, 18)
                .addComponent(jButton3)
                .addGap(18, 18, 18)
                .addComponent(jButton7)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jButton1)
                .addComponent(jButton3)
                .addComponent(jButton7)
                .addComponent(jButton8))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1032, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 454, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableMouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_tableMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        listsv=pointHSG();
        settable();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        listsv=pointHSK();
        settable();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
        listsv=qlpoint();
        settable();
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        listsv=pointHTB();
        settable();        
    }//GEN-LAST:event_jButton7ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable table;
    // End of variables declaration//GEN-END:variables
}
