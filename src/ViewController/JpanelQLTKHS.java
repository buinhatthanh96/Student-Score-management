/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ViewController;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


/**
 *
 * @author NhatThanh
 */
public class JpanelQLTKHS extends javax.swing.JPanel {

    /**
     * Creates new form JpanelQLTKHS
     */
    Connection con;
    ArrayList<qltkhs> listtkhs = getDBQLTKHS();
    int index;
    DefaultTableModel model = new DefaultTableModel();
    public JpanelQLTKHS() {
        initComponents();
        index = 1;
        model = (DefaultTableModel) jTableQLTKHS.getModel();
        settable();
        showdetail(index);
        btnsave.setEnabled(false);
    }
      public void showdetail(int index) {
        qltkhs get = listtkhs.get(index);
        tfmahs.setText(get.getMasv());
        tfname.setText(get.getName());
        tfuser.setText(get.getUsername());
        tfpass.setText(get.getPassword());
    }
    
    public void settable() {
        model.setRowCount(0);
        for (qltkhs x : listtkhs) {
            model.addRow(new Object[]{x.getMasv(), x.getName(), x.getUsername(), x.getPassword()});
        }

    }
    public void clear() {
        tfmahs.setText("");
        tfname.setText("");
        tfuser.setText("");
        tfpass.setText("");
        btndelete.setEnabled(false);
        btnupdate.setEnabled(false);
        btnsave.setEnabled(true);
    }
    public boolean validate1() {
        if (tfmahs.getText().equals("")) {
            tfmahs.requestFocus();
            JOptionPane.showMessageDialog(this, "Mã HS không được rỗng");
            return false;

        } else if (tfuser.getText().equals("")) {
            tfname.requestFocus();;
            JOptionPane.showMessageDialog(this, "User không được rỗng");
            return false;
        } else if (tfpass.getText().equals("")) {
            tfname.requestFocus();;
            JOptionPane.showMessageDialog(this, "Pass không được rỗng");
            return false;
        } 
        return true;
    }
    public ArrayList<qltkhs> getDBQLTKHS() {
        ArrayList<qltkhs> l = new ArrayList<>();
        String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        String url = "jdbc:sqlserver://localhost:1433;databaseName=Quanlyhocsinh";
        String username = "sa";
        String password = "123";
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url, username, password);
            Statement st = con.createStatement();
            
            String sql1 = "SELECT LOGIN_HS.MAHS, STUDENT.NAME, LOGIN_HS.USERNAME, LOGIN_HS.PASS\n" +
                            "FROM LOGIN_HS, STUDENT\n" +
                            "WHERE LOGIN_HS.MAHS=STUDENT.MAHS AND STUDENT.MALOP='" + JframeLogIn.maloplogin + "'\n"+
                            "ORDER BY MAHS ASC";
            ResultSet rs1 = st.executeQuery(sql1);
            while (rs1.next()) {
                l.add(new qltkhs(rs1.getString(1), rs1.getString(2),rs1.getString(3), rs1.getString(4)));
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return l;
    }
    
    public void inserttkhs() {
        String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        String url = "jdbc:sqlserver://localhost:1433;databaseName=Quanlyhocsinh";
        String username = "sa";
        String password = "123";
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url, username, password);
            String sql = "INSERT INTO LOGIN_HS (USERNAME,PASS,QUYEN,MAHS) VALUES(?,?,1,?)";
            PreparedStatement pr = con.prepareStatement(sql);
            pr.setString(1, tfuser.getText());
            pr.setString(2, tfpass.getText());
            pr.setString(3, tfmahs.getText());
            pr.executeUpdate();
            con.close();
            JOptionPane.showMessageDialog(this, "Thêm đối tượng thành công");
            listtkhs = getDBQLTKHS();
            settable();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Mã học sinh không tồn tại!\n"+"Cần thêm học sinh trước khi thêm tk");
        }
    }
    
    public void deletedtkhs() {
        String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        String url = "jdbc:sqlserver://localhost:1433;databaseName=Quanlyhocsinh";
        String username = "sa";
        String password = "123";
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url, username, password);
            String sql = "DELETE FROM LOGIN_HS WHERE MAHS=?";
            PreparedStatement pr = con.prepareStatement(sql);
            pr.setString(1, String.valueOf(jTableQLTKHS.getValueAt(jTableQLTKHS.getSelectedRow(), 0)));
            pr.executeUpdate();
            con.close();
            JOptionPane.showMessageDialog(this, "Xóa thành công");
            listtkhs = getDBQLTKHS();
            settable();
            clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void updaateTKHS() {

        String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        String url = "jdbc:sqlserver://localhost:1433;databaseName=Quanlyhocsinh";
        String username = "sa";
        String password = "123";
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url, username, password);
            String sql = "UPDATE LOGIN_HS SET USERNAME = ? , PASS = ?, QUYEN = ?, MAHS = ? WHERE MAHS=?";
            PreparedStatement pr = con.prepareStatement(sql);
            pr.setString(1, tfuser.getText());
            pr.setString(2, tfpass.getText());
            pr.setString(3, "1");
            pr.setString(4, tfmahs.getText());
            pr.setString(5, String.valueOf(jTableQLTKHS.getValueAt(jTableQLTKHS.getSelectedRow(), 0)));
            pr.executeUpdate();

            con.close();
            JOptionPane.showMessageDialog(this, "Cập nhập thành công");
            listtkhs = getDBQLTKHS();
            settable();

        } catch (Exception e) {
            e.printStackTrace();
        }
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
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        tfmahs = new javax.swing.JTextField();
        tfname = new javax.swing.JTextField();
        tfuser = new javax.swing.JTextField();
        tfpass = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTableQLTKHS = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        btndelete = new javax.swing.JButton();
        btnupdate = new javax.swing.JButton();
        btnnew = new javax.swing.JButton();
        btnsave = new javax.swing.JButton();

        jLabel3.setText("User:");

        jLabel4.setText("Mã HS:");

        jLabel5.setText("Họ và Tên:");

        jLabel8.setText("Password:");

        tfmahs.setDisabledTextColor(new java.awt.Color(0, 0, 255));

        tfname.setBackground(new java.awt.Color(240, 240, 240));
        tfname.setDisabledTextColor(new java.awt.Color(0, 0, 255));
        tfname.setEnabled(false);

        jTableQLTKHS.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã HS", "Họ và Tên", "User", "Password"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableQLTKHS.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableQLTKHSMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(jTableQLTKHS);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        btndelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/btn_delete.png"))); // NOI18N
        btndelete.setText("Delete");
        btndelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btndeleteActionPerformed(evt);
            }
        });

        btnupdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Transfermanage.png"))); // NOI18N
        btnupdate.setText("Update");
        btnupdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnupdateActionPerformed(evt);
            }
        });

        btnnew.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/btn_add.png"))); // NOI18N
        btnnew.setText("New");
        btnnew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnnewActionPerformed(evt);
            }
        });

        btnsave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/btn_save.png"))); // NOI18N
        btnsave.setText("Save");
        btnsave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsaveActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btndelete, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnupdate)
                    .addComponent(btnsave, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnnew, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(btnnew)
                .addGap(18, 18, 18)
                .addComponent(btnsave)
                .addGap(18, 18, Short.MAX_VALUE)
                .addComponent(btnupdate)
                .addGap(18, 18, 18)
                .addComponent(btndelete)
                .addGap(16, 16, 16))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(tfuser, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 260, Short.MAX_VALUE)
                            .addComponent(tfmahs, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tfpass))
                        .addGap(72, 72, 72)
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(tfname, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 94, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(72, 72, 72))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(tfmahs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(tfname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(tfuser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(tfpass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 195, Short.MAX_VALUE))
                .addGap(198, 198, 198))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jTableQLTKHSMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableQLTKHSMouseClicked
        // TODO add your handling code here:
        index = jTableQLTKHS.getSelectedRow();
        if(tfmahs.getText().length() == 0){

            showdetail(index);
            btnupdate.setEnabled(true);
            btndelete.setEnabled(true);
            btnsave.setEnabled(false);
        }

        else {
            showdetail(index);
            btnupdate.setEnabled(true);
            btndelete.setEnabled(true);
            btnsave.setEnabled(false);
        }
    }//GEN-LAST:event_jTableQLTKHSMouseClicked

    private void btndeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btndeleteActionPerformed
        // TODO add your handling code here:
        int chon = JOptionPane.showConfirmDialog(this, "Bạn có muốn xóa không", "xóa", JOptionPane.YES_NO_OPTION, 3);
        if (chon == JOptionPane.YES_OPTION) {
            deletedtkhs();
        }
    }//GEN-LAST:event_btndeleteActionPerformed

    private void btnupdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnupdateActionPerformed
        // TODO add your handling code here:
        if (validate1() == true) {
            tfmahs.setEnabled(true);
            updaateTKHS();
        }
    }//GEN-LAST:event_btnupdateActionPerformed

    private void btnnewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnewActionPerformed
        // TODO add your handling code here:
        clear();
    }//GEN-LAST:event_btnnewActionPerformed

    private void btnsaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsaveActionPerformed
        // TODO add your handling code here:
        if (validate1() == true) {
            inserttkhs();
        }
    }//GEN-LAST:event_btnsaveActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btndelete;
    private javax.swing.JButton btnnew;
    private javax.swing.JButton btnsave;
    private javax.swing.JButton btnupdate;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTableQLTKHS;
    private javax.swing.JTextField tfmahs;
    private javax.swing.JTextField tfname;
    private javax.swing.JTextField tfpass;
    private javax.swing.JTextField tfuser;
    // End of variables declaration//GEN-END:variables
}
