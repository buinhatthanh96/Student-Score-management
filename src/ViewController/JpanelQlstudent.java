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
public class JpanelQlstudent extends javax.swing.JPanel {

    /**
     * Creates new form JpanelQlstudent
     */
  
    ArrayList<Student> listsv = getdatebasestudent();
    Connection con;
    int index;
    DefaultTableModel model = new DefaultTableModel();
    public JpanelQlstudent() {
        initComponents();
        index = 1;
        model = (DefaultTableModel) jTable2.getModel();
        settable();
        showdetail(index);
        btnsave.setEnabled(false);
    }
    public String getgt() {
        String gt;
        if (rdo1.isSelected()) {
            gt = "Nam";
        } else {
            gt = "Nữ";
        }
        return gt;
    }
    
    public void settable() {
        model.setRowCount(0);
        for (Student x : listsv) {
            model.addRow(new Object[]{x.getID(), x.getName(), x.getLop(), x.getEmail(), x.getPhone(), x.getSex(), x.getAddress(), x.getAvartar()});
        }

    }

    public void showdetail(int index) {
        Student get = listsv.get(index);
        tfmahs.setText(get.getID());
        tfmalop.setText(get.getLop());
        tfemail.setText(get.getEmail());
        tfname.setText(get.getName());
        tfphone.setText(get.getPhone());
        String gt = get.getSex();
        switch (gt) {
            case "Nam":
                rdo1.setSelected(true);
                break;
            case "Nữ":   
                rdo2.setSelected(true);
                break;
        }
        textarea.setText(get.getAddress());
        jTable2.setRowSelectionInterval(index, index);
        lblanh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Avartar/" + get.getAvartar())));

    }

    public void deletedtb() {
        String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        String url = "jdbc:sqlserver://localhost:1433;databaseName=Quanlyhocsinh";
        String username = "sa";
        String password = "123";
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url, username, password);
            String sql = "DELETE FROM STUDENT WHERE MAHS = ?";
            PreparedStatement pr = con.prepareStatement(sql);
            pr.setString(1, String.valueOf(jTable2.getValueAt(jTable2.getSelectedRow(), 0)));
            pr.executeUpdate();
            con.close();
            JOptionPane.showMessageDialog(this, "Xóa thành công");
            listsv = getdatebasestudent();
            settable();
            clear();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Không thể xóa, Lỗi xung đột dữ liệu với bảng Điểm,\n" + "Muốn xóa học sinh phải xóa điểm của học sinh đó trước!");
        }
    }
    
    public ArrayList<Student> getdatebasestudent() {
        ArrayList<Student> l = new ArrayList<>();
        String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        String url = "jdbc:sqlserver://localhost:1433;databaseName=Quanlyhocsinh";
        String username = "sa";
        String password = "123";
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url, username, password);
            Statement st = con.createStatement();
            
            String sql1 = "SELECT * FROM STUDENT WHERE STUDENT.MALOP='" + JframeLogIn.maloplogin + "'";
            ResultSet rs1 = st.executeQuery(sql1);
            while (rs1.next()) {
                l.add(new Student(rs1.getString(1), rs1.getString(2),rs1.getString(3), rs1.getString(4), rs1.getString(5), rs1.getString(6), rs1.getString(7), rs1.getString(8)));
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return l;
    }
    public String getidDBDiem(){
        String id="";
        String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        String url = "jdbc:sqlserver://localhost:1433;databaseName=Quanlyhocsinh";
        String username = "sa";
        String password = "123";
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url, username, password);
            Statement st = con.createStatement();
            
            String sql1 = "SELECT COUNT (DIEM.ID) SOLUONG_ID\n" +
                            "FROM STUDENT, DIEM, LOP \n" +
                            "WHERE DIEM.MAHS=STUDENT.MAHS \n" +
                            "AND STUDENT.MALOP=LOP.MALOP\n" +
                            "AND LOP.MALOP='" + JframeLogIn.maloplogin + "'";
            ResultSet rs1 = st.executeQuery(sql1);
          
            while (rs1.next()) {
                id = rs1.getString(1);
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id+1;
    }
    public void insertdtb() {
        String linkanh = JOptionPane.showInputDialog("Nhập link Avatar");
        String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        String url = "jdbc:sqlserver://localhost:1433;databaseName=Quanlyhocsinh";
        String username = "sa";
        String password = "123";
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url, username, password);
            String sql = "insert into STUDENT values(?,?,?,?,?,?,?,?)";
            PreparedStatement pr = con.prepareStatement(sql);
            pr.setString(1, tfmahs.getText());
            pr.setString(2, tfname.getText());
            pr.setString(3, tfmalop.getText());
            pr.setString(4, tfemail.getText());
            pr.setString(5, tfphone.getText());
            pr.setString(6, getgt());
            pr.setString(7, textarea.getText());
            pr.setString(8, linkanh);
            pr.executeUpdate();
            
            String sql2 = "INSERT INTO DIEM (ID,MAHS,TOAN,VATLY,HOAHOC,SINHHOC,NGUVAN,TIENGANH,LICHSU,DIALY) VALUES (?,?,0,0,0,0,0,0,0,0)";
            PreparedStatement pr2 = con.prepareStatement(sql2);
            pr2.setString(1, getidDBDiem());
            pr2.setString(2, tfmahs.getText());
            pr2.executeUpdate();
            
            con.close();
            JOptionPane.showMessageDialog(this, "Thêm đối tượng thành công");
            listsv = getdatebasestudent();
            settable();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
   
    
    public void updaatedatabase() {

        String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        String url = "jdbc:sqlserver://localhost:1433;databaseName=Quanlyhocsinh";
        String username = "sa";
        String password = "123";
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url, username, password);
            String sql = "UPDATE STUDENT SET MAHS = ? ,NAME = ? , MALOP = ?, EMAIL = ? , PHONE = ? , GIOITINH = ? ,DIACHI = ?  WHERE MAHS = ?  ";
            PreparedStatement pr = con.prepareStatement(sql);
            pr.setString(1, tfmahs.getText());
            pr.setString(2, tfname.getText());
            pr.setString(3, tfmalop.getText());
            pr.setString(4, tfemail.getText());
            pr.setString(5, tfphone.getText());
            pr.setString(6, getgt());
            pr.setString(7, textarea.getText());
            pr.setString(8, String.valueOf(jTable2.getValueAt(jTable2.getSelectedRow(), 0)));
            pr.executeUpdate();

            con.close();
            JOptionPane.showMessageDialog(this, "Cập nhập thành công");
            listsv = getdatebasestudent();
            settable();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clear() {
        tfemail.setText("");
        tfmahs.setText("");
        tfname.setText("");
        tfmalop.setText("");
        tfphone.setText("");
        textarea.setText("");
        tfmahs.setEnabled(true);
        btndelete.setEnabled(false);
        btnupdate.setEnabled(false);
        btnsave.setEnabled(true);
        lblanh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Avartar/" + "")));
    }

    public boolean validate1() {
        if (tfmahs.getText().equals("")) {
            tfmahs.requestFocus();
            JOptionPane.showMessageDialog(this, "Mã HS không được rỗng");
            return false;

        } else if (tfname.getText().equals("")) {
            tfname.requestFocus();;
            JOptionPane.showMessageDialog(this, "Tên không được rỗng");
            return false;
        } else if (tfmalop.getText().equals(JframeLogIn.maloplogin)==false) {
            tfmalop.requestFocus();;
            JOptionPane.showMessageDialog(this, "Mã lớp phải là mã lớp của bạn");
            return false;
        }else if (!tfemail.getText().matches("\\w+@\\w+\\.\\w+")) {
            tfemail.requestFocus();
            JOptionPane.showMessageDialog(this, "Chưa đúng định dạng email: abc@gmail.com");
            return false;

        } else if (tfphone.getText().length() < 9 || tfphone.getText().length() > 11) {

            tfphone.requestFocus();
            JOptionPane.showMessageDialog(this, "Số điện thoại từ 10 đến 11 số");
            return false;

        } else if (!rdo1.isSelected() && !rdo2.isSelected()) {
            JOptionPane.showMessageDialog(this, "Chưa chọn giới tính");
            return false;

        } else if (textarea.getText().equals("")) {
            textarea.getText().equals("");
            JOptionPane.showMessageDialog(this, "Địa chỉ không được rỗng");
            return false;

        }

        return true;
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        tfmahs = new javax.swing.JTextField();
        tfname = new javax.swing.JTextField();
        tfmalop = new javax.swing.JTextField();
        tfemail = new javax.swing.JTextField();
        tfphone = new javax.swing.JTextField();
        rdo1 = new javax.swing.JRadioButton();
        rdo2 = new javax.swing.JRadioButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        textarea = new javax.swing.JTextArea();
        jPanel3 = new javax.swing.JPanel();
        btnnew = new javax.swing.JButton();
        btnsave = new javax.swing.JButton();
        btndelete = new javax.swing.JButton();
        btnupdate = new javax.swing.JButton();
        lblanh = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();

        setMaximumSize(new java.awt.Dimension(1050, 554));
        setPreferredSize(new java.awt.Dimension(1050, 554));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel2.setText("Mã HS:");

        jLabel3.setText("Họ Và Tên : ");

        jLabel4.setText("Email :");

        jLabel5.setText("Số Điện Thoại :");

        jLabel6.setText("Giới Tính :");

        jLabel7.setText("Địa Chỉ :");

        jLabel8.setText("Mã lớp:");

        buttonGroup1.add(rdo1);
        rdo1.setText("Nam");

        buttonGroup1.add(rdo2);
        rdo2.setText("Nữ");

        textarea.setColumns(20);
        textarea.setRows(5);
        jScrollPane1.setViewportView(textarea);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 352, Short.MAX_VALUE)
                    .addComponent(tfmahs)
                    .addComponent(tfname)
                    .addComponent(tfemail)
                    .addComponent(tfphone)
                    .addComponent(tfmalop)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(rdo1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(rdo2)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap(40, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(tfmahs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(tfname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(tfmalop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfemail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(tfphone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(rdo1)
                    .addComponent(rdo2))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(0, 69, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

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

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnsave, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnnew, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnupdate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btndelete, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnnew)
                    .addComponent(btndelete))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnupdate)
                    .addComponent(btnsave))
                .addContainerGap())
        );

        lblanh.setMaximumSize(new java.awt.Dimension(5, 5));
        lblanh.setMinimumSize(new java.awt.Dimension(5, 5));
        lblanh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblanhMouseClicked(evt);
            }
        });

        jButton1.setText("Thay Avatar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã HS", "Họ và Tên", "Mã lớp", "Email", "SĐT", "Giới Tính", "Địa chỉ", "Avatar"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable2MouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(jTable2);
        if (jTable2.getColumnModel().getColumnCount() > 0) {
            jTable2.getColumnModel().getColumn(0).setResizable(false);
            jTable2.getColumnModel().getColumn(0).setPreferredWidth(40);
            jTable2.getColumnModel().getColumn(1).setResizable(false);
            jTable2.getColumnModel().getColumn(1).setPreferredWidth(90);
            jTable2.getColumnModel().getColumn(2).setResizable(false);
            jTable2.getColumnModel().getColumn(2).setPreferredWidth(40);
            jTable2.getColumnModel().getColumn(3).setResizable(false);
            jTable2.getColumnModel().getColumn(3).setPreferredWidth(90);
            jTable2.getColumnModel().getColumn(4).setResizable(false);
            jTable2.getColumnModel().getColumn(4).setPreferredWidth(80);
            jTable2.getColumnModel().getColumn(5).setResizable(false);
            jTable2.getColumnModel().getColumn(5).setPreferredWidth(40);
            jTable2.getColumnModel().getColumn(6).setResizable(false);
            jTable2.getColumnModel().getColumn(6).setPreferredWidth(90);
            jTable2.getColumnModel().getColumn(7).setResizable(false);
            jTable2.getColumnModel().getColumn(7).setPreferredWidth(80);
        }
        jTable2.getAccessibleContext().setAccessibleParent(this);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addGap(202, 202, 202))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblanh, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(152, 152, 152))))
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 1038, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblanh, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 195, Short.MAX_VALUE)
                .addGap(12, 12, 12))
        );

        getAccessibleContext().setAccessibleParent(this);
    }// </editor-fold>//GEN-END:initComponents

    private void btnnewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnewActionPerformed
        // TODO add your handling code here:
        clear();
    }//GEN-LAST:event_btnnewActionPerformed

    private void btnsaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsaveActionPerformed
        // TODO add your handling code here:
        if (validate1() == true) {
            insertdtb();
        }
    }//GEN-LAST:event_btnsaveActionPerformed

    private void btndeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btndeleteActionPerformed
        // TODO add your handling code here:
        int chon = JOptionPane.showConfirmDialog(this, "Bạn có muốn xóa không", "xóa", JOptionPane.YES_NO_OPTION, 3);
        if (chon == JOptionPane.YES_OPTION) {
            deletedtb();
        }
    }//GEN-LAST:event_btndeleteActionPerformed

    private void btnupdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnupdateActionPerformed
        // TODO add your handling code here:
        if (validate1() == true) {
            tfmahs.setEnabled(true);
            updaatedatabase();
        }
    }//GEN-LAST:event_btnupdateActionPerformed

    private void lblanhMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblanhMouseClicked
        // TODO add your handling code here:
        String anh = JOptionPane.showInputDialog("Nhận Link Ảnh");

        //        if (!anh.equals("")) {
            //            String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
            //            String url = "jdbc:sqlserver://localhost:1433;databaseName=Quanlysinhvien";
            //            String username = "sa";
            //            String password = "123";
            //            try {
                //                Class.forName(driver);
                //                con = DriverManager.getConnection(url, username, password);
                //                String sql = "UPDATE STUDENT SET AVATAR = ? WHERE MASV = ?  ";
                //                PreparedStatement pr = con.prepareStatement(sql);
                //                pr.setString(1, anh);
                //                pr.setString(2, String.valueOf(jTable2.getValueAt(jTable2.getSelectedRow(), 0)));
                //                JOptionPane.showMessageDialog(this, "Cập nhập ảnh thành công");
                //                con.close();
                //                listsv = getdatebasestudent();
                //                settable();
                //                showdetail(index);
                //
                //            } catch (Exception e) {
                //                e.printStackTrace();
                //            }
            //        }
    }//GEN-LAST:event_lblanhMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        String anh = JOptionPane.showInputDialog("Nhận Link Ảnh");

        if (!anh.equals("")  ) {
            String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
            String url = "jdbc:sqlserver://localhost:1433;databaseName=Quanlyhocsinh";
            String username = "sa";
            String password = "123";
            try {
                Class.forName(driver);
                con = DriverManager.getConnection(url, username, password);
                String sql = "UPDATE STUDENT SET AVATAR = ? WHERE MAHS = ?";
                PreparedStatement pr = con.prepareStatement(sql);
                pr.setString(1, anh);
                pr.setString(2, String.valueOf(jTable2.getValueAt(jTable2.getSelectedRow(), 0)));
                pr.executeUpdate();
                con.close();
                listsv = getdatebasestudent();
                settable();
                index = 0;
                showdetail(index);
                JOptionPane.showMessageDialog(this, "Cập nhập avatar thành công");

            }catch (Exception e ){
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked
        // TODO add your handling code here:
        index = jTable2.getSelectedRow();
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

    }//GEN-LAST:event_jTable2MouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btndelete;
    private javax.swing.JButton btnnew;
    private javax.swing.JButton btnsave;
    private javax.swing.JButton btnupdate;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable2;
    private javax.swing.JLabel lblanh;
    private javax.swing.JRadioButton rdo1;
    private javax.swing.JRadioButton rdo2;
    private javax.swing.JTextArea textarea;
    private javax.swing.JTextField tfemail;
    private javax.swing.JTextField tfmahs;
    private javax.swing.JTextField tfmalop;
    private javax.swing.JTextField tfname;
    private javax.swing.JTextField tfphone;
    // End of variables declaration//GEN-END:variables
}
