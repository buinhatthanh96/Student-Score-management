/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ViewController;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author NhatThanh
 */
public class JpanelQlPoint extends javax.swing.JPanel {

    /**
     * Creates new form JpanelQlPoint
     */
    
    ArrayList<Point> listsv = qlpoint();
    DefaultTableModel model = new DefaultTableModel();
    private int index;
    Connection con;
    public JpanelQlPoint() {
        initComponents();
        model = (DefaultTableModel) table.getModel();
        index = 0;
        settable();
        showdetail(index);
    }
    public void showdetail(int index) {
        tfname.setText(listsv.get(index).getName());
        tfmasv.setText(listsv.get(index).getMasv());
        tftenlop.setText(listsv.get(index).getTenlop());
        tfdiemtoan.setText(listsv.get(index).getToan()+ "");
        tfdiemvatly.setText(listsv.get(index).getVatly()+ "");
        tfdiemhoahoc.setText(listsv.get(index).getHoahoc()+ "");
        tfdiemsinhhoc.setText(listsv.get(index).getSinhhoc()+"");
        tfdiemnguvan.setText(listsv.get(index).getVanhoc()+"");
        tfdiemtienganh.setText(listsv.get(index).getTienganh()+"");
        tfdiemlichsu.setText(listsv.get(index).getLichsu()+"");
        tfdiemdialy.setText(listsv.get(index).getDialy()+"");
        lbltb.setText((listsv.get(index).getToan() + listsv.get(index).getVatly() + listsv.get(index).getHoahoc() 
                + listsv.get(index).getSinhhoc() + listsv.get(index).getVanhoc() + listsv.get(index).getTienganh()
                + listsv.get(index).getLichsu() + listsv.get(index).getDialy() ) / 8 + "");
        table.setRowSelectionInterval(index, index);
        lblrecord.setText(index + 1 + "/" + listsv.size());

    }

    public void settable() {
        model.setRowCount(0);
        for (Point x : listsv) {
            model.addRow(new Object[]{x.getMasv(), x.getName(), x.getTenlop(), x.getToan(), x.getVatly(), x.getHoahoc(), x.getSinhhoc(),
                x.getVanhoc(), x.getTienganh(), x.getLichsu(), x.getDialy(),
                (x.getToan()+x.getVatly()+x.getHoahoc()+x.getSinhhoc()+x.getVanhoc()+x.getTienganh()+x.getLichsu()+x.getDialy())/8});
        }
    }

    public void search1() {
        String msv = tfseach.getText();
        if (!msv.equals("")) {
            for (int i = 0; i < listsv.size(); i++) {
                if (listsv.get(i).getMasv().equals(msv)) {
//                    model.setRowCount(0);
//                    model.addRow(new Object[]{listsv.get(i).getMasv(),listsv.get(i).getName(),listsv.get(i).getEnglish()});
                    showdetail(i);
                    return;
                }

            }
            JOptionPane.showMessageDialog(this, "Không tìm thấy Sinh viên");
        } else {
            JOptionPane.showMessageDialog(this, "bạn chưa nhập mã SV");
        }
    }

    public void clear() {
//        tfname.setText("");
//        tfmasv.setText("");
        
        tfdiemtoan.setText("");
        tfdiemvatly.setText("");
        tfdiemhoahoc.setText("");
        tfdiemsinhhoc.setText("");
        tfdiemnguvan.setText("");
        tfdiemtienganh.setText("");
        tfdiemlichsu.setText("");
        tfdiemdialy.setText("");
     
        btndelete.setEnabled(false);
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
                            "AND STUDENT.MALOP=LOP.MALOP AND LOP.MALOP='" + JframeLogIn.maloplogin + "'";
           
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
     public boolean validate1() {
       if(Double.parseDouble(tfdiemtoan.getText())<0 || Double.parseDouble(tfdiemtoan.getText())>10){
           JOptionPane.showMessageDialog(this, "Điểm Toán phải từ 1 đến 10");
           tfdiemtoan.requestFocus();
           return false;
       }else if(Double.parseDouble(tfdiemvatly.getText())<0 || Double.parseDouble(tfdiemvatly.getText())>10){
           JOptionPane.showMessageDialog(this, "Điểm Vật lý phải từ 1 đến 10");
           tfdiemvatly.requestFocus();
           return false;
           
       }else if(Double.parseDouble(tfdiemhoahoc.getText())<0 || Double.parseDouble(tfdiemhoahoc.getText())>10){
            JOptionPane.showMessageDialog(this, "Điểm Hóa học phải từ 1 đến 10");
            tfdiemhoahoc.requestFocus();
            return false;
           
       }else if(Double.parseDouble(tfdiemsinhhoc.getText())<0 || Double.parseDouble(tfdiemhoahoc.getText())>10){
            JOptionPane.showMessageDialog(this, "Điểm Sinh học phải từ 1 đến 10");
            tfdiemhoahoc.requestFocus();
            return false;
           
       }else if(Double.parseDouble(tfdiemnguvan.getText())<0 || Double.parseDouble(tfdiemhoahoc.getText())>10){
            JOptionPane.showMessageDialog(this, "Điểm Ngữ văn phải từ 1 đến 10");
            tfdiemhoahoc.requestFocus();
            return false;
           
       }else if(Double.parseDouble(tfdiemtienganh.getText())<0 || Double.parseDouble(tfdiemhoahoc.getText())>10){
            JOptionPane.showMessageDialog(this, "Điểm Tiếng anh phải từ 1 đến 10");
            tfdiemhoahoc.requestFocus();
            return false;
           
       }else if(Double.parseDouble(tfdiemlichsu.getText())<0 || Double.parseDouble(tfdiemhoahoc.getText())>10){
            JOptionPane.showMessageDialog(this, "Điểm Lịch sử phải từ 1 đến 10");
            tfdiemhoahoc.requestFocus();
            return false;
           
       }else if(Double.parseDouble(tfdiemdialy.getText())<0 || Double.parseDouble(tfdiemhoahoc.getText())>10){
            JOptionPane.showMessageDialog(this, "Điểm Địa lý phải từ 1 đến 10");
            tfdiemhoahoc.requestFocus();
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

        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        tfseach = new javax.swing.JTextField();
        btnsearch = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        tfname = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        tfmasv = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        lbltb = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        tftenlop = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        tfdiemtoan = new javax.swing.JTextField();
        tfdiemvatly = new javax.swing.JTextField();
        tfdiemhoahoc = new javax.swing.JTextField();
        lblrecord = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        tfdiemsinhhoc = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        tfdiemnguvan = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        tfdiemtienganh = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        tfdiemlichsu = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        tfdiemdialy = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        btnnew = new javax.swing.JButton();
        btndelete = new javax.swing.JButton();
        btnupdate = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel3.setText("Mã SV:");

        btnsearch.setForeground(new java.awt.Color(51, 51, 255));
        btnsearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/btn_search.png"))); // NOI18N
        btnsearch.setText("Search");
        btnsearch.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        btnsearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsearchActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addComponent(tfseach)
                .addGap(18, 18, 18)
                .addComponent(btnsearch)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(tfseach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnsearch, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );

        tfname.setForeground(new java.awt.Color(51, 51, 255));
        tfname.setDisabledTextColor(new java.awt.Color(0, 0, 255));
        tfname.setEnabled(false);

        jLabel5.setText("Mã SV :");

        tfmasv.setForeground(new java.awt.Color(0, 0, 204));
        tfmasv.setDisabledTextColor(new java.awt.Color(0, 0, 204));
        tfmasv.setEnabled(false);

        jLabel4.setText("Họ Và Tên HS :");

        jLabel9.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 255));
        jLabel9.setText("Điểm Trung Bình");

        lbltb.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        lbltb.setForeground(new java.awt.Color(255, 0, 51));

        jLabel1.setText("Lớp:");

        tftenlop.setForeground(new java.awt.Color(0, 0, 204));
        tftenlop.setDisabledTextColor(new java.awt.Color(0, 0, 204));
        tftenlop.setEnabled(false);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel4)
                                .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING))
                            .addComponent(jLabel1))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tftenlop)
                            .addComponent(tfname)
                            .addComponent(tfmasv)))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(101, 101, 101)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addComponent(lbltb, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 42, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(tfname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(tfmasv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(tftenlop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbltb, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(22, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel6.setText("Toán:");

        jLabel7.setText("Vật Lý:");

        jLabel8.setText("Hóa Học:");

        lblrecord.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        lblrecord.setForeground(new java.awt.Color(204, 0, 51));

        jLabel12.setText("Sinh Học:");

        jLabel11.setText("Ngữ Văn:");

        jLabel13.setText("Tiếng Anh:");

        jLabel14.setText("Lịch Sử:");

        jLabel15.setText("Địa Lý:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.LEADING))
                .addGap(48, 48, 48)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblrecord, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(tfdiemsinhhoc, javax.swing.GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE)
                            .addComponent(tfdiemhoahoc, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tfdiemvatly)
                            .addComponent(tfdiemtoan, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGap(76, 76, 76)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(tfdiemtienganh, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(tfdiemnguvan, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel14)
                                    .addComponent(jLabel15))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(tfdiemlichsu, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(tfdiemdialy, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addGap(0, 15, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(tfdiemtoan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(tfdiemnguvan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(tfdiemvatly, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13)
                    .addComponent(tfdiemtienganh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(tfdiemhoahoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14)
                    .addComponent(tfdiemlichsu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(tfdiemsinhhoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15)
                    .addComponent(tfdiemdialy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, Short.MAX_VALUE)
                .addComponent(lblrecord))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        btnnew.setForeground(new java.awt.Color(0, 0, 255));
        btnnew.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/contact-new.png"))); // NOI18N
        btnnew.setText("New");
        btnnew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnnewActionPerformed(evt);
            }
        });

        btndelete.setForeground(new java.awt.Color(0, 0, 255));
        btndelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/btn_delete.png"))); // NOI18N
        btndelete.setText("Delete");
        btndelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btndeleteActionPerformed(evt);
            }
        });

        btnupdate.setForeground(new java.awt.Color(0, 0, 255));
        btnupdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Transfer.png"))); // NOI18N
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
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btndelete, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnupdate, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnnew, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(btnnew)
                .addGap(18, 18, 18)
                .addComponent(btndelete)
                .addGap(18, 18, 18)
                .addComponent(btnupdate)
                .addContainerGap(27, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/btn_first.png"))); // NOI18N
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/btn_previous.png"))); // NOI18N
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/btn_next.png"))); // NOI18N
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jButton9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/btn_last.png"))); // NOI18N
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton6)
                .addGap(30, 30, 30)
                .addComponent(jButton7)
                .addGap(36, 36, 36)
                .addComponent(jButton8)
                .addGap(18, 18, 18)
                .addComponent(jButton9)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton6)
                    .addComponent(jButton7)
                    .addComponent(jButton8)
                    .addComponent(jButton9))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã HS", "Họ Và Tên", "Lớp", "Toán", "Vật Lý", "Hóa Học", "Sinh Học", "Ngữ Văn", "Tiếng Anh", "Lịch Sử", "Địa Lý", "ĐTB"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false
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
            table.getColumnModel().getColumn(0).setResizable(false);
            table.getColumnModel().getColumn(0).setPreferredWidth(40);
            table.getColumnModel().getColumn(1).setResizable(false);
            table.getColumnModel().getColumn(1).setPreferredWidth(90);
            table.getColumnModel().getColumn(2).setResizable(false);
            table.getColumnModel().getColumn(2).setPreferredWidth(40);
            table.getColumnModel().getColumn(3).setResizable(false);
            table.getColumnModel().getColumn(3).setPreferredWidth(30);
            table.getColumnModel().getColumn(4).setResizable(false);
            table.getColumnModel().getColumn(4).setPreferredWidth(30);
            table.getColumnModel().getColumn(5).setResizable(false);
            table.getColumnModel().getColumn(5).setPreferredWidth(30);
            table.getColumnModel().getColumn(6).setResizable(false);
            table.getColumnModel().getColumn(6).setPreferredWidth(30);
            table.getColumnModel().getColumn(7).setResizable(false);
            table.getColumnModel().getColumn(7).setPreferredWidth(30);
            table.getColumnModel().getColumn(8).setResizable(false);
            table.getColumnModel().getColumn(8).setPreferredWidth(30);
            table.getColumnModel().getColumn(9).setResizable(false);
            table.getColumnModel().getColumn(9).setPreferredWidth(30);
            table.getColumnModel().getColumn(10).setResizable(false);
            table.getColumnModel().getColumn(10).setPreferredWidth(30);
            table.getColumnModel().getColumn(11).setResizable(false);
            table.getColumnModel().getColumn(11).setPreferredWidth(30);
        }

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(131, 131, 131)))
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(30, Short.MAX_VALUE))
            .addComponent(jScrollPane1)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(23, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnsearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsearchActionPerformed
        search1();        // TODO add your handling code here:
    }//GEN-LAST:event_btnsearchActionPerformed

    private void btnnewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnewActionPerformed
        clear();        // TODO add your handling code here:
    }//GEN-LAST:event_btnnewActionPerformed

    private void btndeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btndeleteActionPerformed
        // TODO add your handling code here:
        int ret = JOptionPane.showConfirmDialog(this, "Bạn có muốn xóa không", "Xóa", JOptionPane.YES_NO_OPTION, 3);
        if (ret == JOptionPane.YES_OPTION) {
            String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
            String url = "jdbc:sqlserver://localhost:1433;databaseName=Quanlyhocsinh";
            String username = "sa";
            String password = "123";
            try {
                try {
                    Class.forName(driver);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(JpanelQlPoint.class.getName()).log(Level.SEVERE, null, ex);
                    ex.printStackTrace();
                }
                con = DriverManager.getConnection(url, username, password);
                String sql = "delete from DIEM where MAHS =? ";

                PreparedStatement pr = con.prepareStatement(sql);
                pr.setString(1, String.valueOf(table.getValueAt(table.getSelectedRow(), 0)));
                pr.execute();
                JOptionPane.showMessageDialog(this, "Xóa điểm thành công");
                listsv = qlpoint();
                settable();

            } catch (SQLException sQLException) {
                sQLException.printStackTrace();

            }
        }
    }//GEN-LAST:event_btndeleteActionPerformed

    private void btnupdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnupdateActionPerformed
        if(validate1() == true ){
            String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
            String url = "jdbc:sqlserver://localhost:1433;databaseName=Quanlyhocsinh";
            String username = "sa";
            String password = "123";
            try {
                try {
                    Class.forName(driver);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(JpanelQlPoint.class.getName()).log(Level.SEVERE, null, ex);
                    ex.printStackTrace();
                }
                con = DriverManager.getConnection(url, username, password);
                String sql = "UPDATE DIEM SET TOAN=?, VATLY=?, HOAHOC=?, SINHHOC=?, NGUVAN=?, TIENGANH=?, LICHSU=?, DIALY=? WHERE MAHS=?";
                PreparedStatement pr = con.prepareStatement(sql);
                pr.setString(1,tfdiemtoan.getText());
                pr.setString(2, tfdiemvatly.getText());
                pr.setString(3, tfdiemhoahoc.getText());
                pr.setString(4, tfdiemsinhhoc.getText());
                pr.setString(5, tfdiemnguvan.getText());
                pr.setString(6, tfdiemnguvan.getText());
                pr.setString(7, tfdiemlichsu.getText());
                pr.setString(8, tfdiemdialy.getText());
                pr.setString(9, String.valueOf(table.getValueAt(table.getSelectedRow(), 0)));
                pr.executeUpdate();
                JOptionPane.showMessageDialog(this, "UPDATE thành công");
                con.close();
                
                listsv = qlpoint();
                index = 0;

                settable();
                showdetail(index);
                
                btndelete.setEnabled(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_btnupdateActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        index = 0;
        showdetail(index);
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        if (index > 0) {
            index--;
            showdetail(index);
        }
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
        if (index < listsv.size() - 1) {
            index++;
            showdetail(index);
        }
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        // TODO add your handling code here:
        index = listsv.size() - 1;
        showdetail(index);
    }//GEN-LAST:event_jButton9ActionPerformed

    private void tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableMouseClicked
        // TODO add your handling code here:
        index = table.getSelectedRow();
        if (index >= 0) {
            showdetail(index);
          
            btndelete.setEnabled(true);
        }
    }//GEN-LAST:event_tableMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btndelete;
    private javax.swing.JButton btnnew;
    private javax.swing.JButton btnsearch;
    private javax.swing.JButton btnupdate;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblrecord;
    private javax.swing.JLabel lbltb;
    private javax.swing.JTable table;
    private javax.swing.JTextField tfdiemdialy;
    private javax.swing.JTextField tfdiemhoahoc;
    private javax.swing.JTextField tfdiemlichsu;
    private javax.swing.JTextField tfdiemnguvan;
    private javax.swing.JTextField tfdiemsinhhoc;
    private javax.swing.JTextField tfdiemtienganh;
    private javax.swing.JTextField tfdiemtoan;
    private javax.swing.JTextField tfdiemvatly;
    private javax.swing.JTextField tfmasv;
    private javax.swing.JTextField tfname;
    private javax.swing.JTextField tfseach;
    private javax.swing.JTextField tftenlop;
    // End of variables declaration//GEN-END:variables
}
