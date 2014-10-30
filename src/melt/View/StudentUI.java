/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package melt.View;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;
import melt.Util.SQLHelper;
import static melt.View.AddQuestion.driver;
import static melt.View.AddQuestion.password;
import static melt.View.AddQuestion.url;
import static melt.View.AddQuestion.user;

/**
 *
 * @author ryzal_000
 */
public final class StudentUI extends javax.swing.JFrame {

    private Connection con;
    private Statement st;
    private Statement st2;
    private Statement st3;
    private Statement st4;
    private Statement st5;
    private ResultSet rs;
    
    private ResultSet rs2;
    private ResultSet rs3;
    private ResultSet rs4;
    private ResultSet rs5;
    
    public static String url;
    public static String user;
    public static String password;
    public static String driver;
    
     public JLabel questionLabel;
    
    // Class important variables
    int examID = 0;
    ArrayList<Integer> sectionID = new ArrayList<>();
    ArrayList<String> sectionName = new ArrayList<>();
    
    ArrayList<Integer> subSectionID = new ArrayList<>();
    
    /**
     * Creates new form StudentUI
     */
    public StudentUI() {
        initComponents();

        connectDb();
        
        getExam();
        //System.out.println(this.examID);
        setSection();
        
        for (int i : sectionID) {
            System.out.print("Section ID: "+i+" ||");
            getSectionDetails(i);
            
             
                getQuestion(i);
            
            
            //System.out.println("-------------------------------------");
            
            
        }
        
        
        
    }
    
    public void startSQL() {
        
        try {

            InputStream in = this.getClass().getResourceAsStream("/melt/Util/jdbc.properties");
            Properties pp = new Properties();
            pp.load(in);
            url = pp.getProperty("jdbc.url");
            user = pp.getProperty("jdbc.username");
            password = pp.getProperty("jdbc.password");
            driver = pp.getProperty("jdbc.driver");

        } catch (IOException ex) {
            Logger.getLogger(SQLHelper.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        
    }
    
    public void connectDb() {
        startSQL(); 
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            con = DriverManager.getConnection(url, user, password);
            
            st = con.createStatement();
            st2 = con.createStatement();
            st3 = con.createStatement();
            st4 = con.createStatement();
            st5 = con.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        } 
        
    }
    
    
    public void getExam() {
        
        try {
            String sql = "SELECT * FROM exam WHERE isPublic='1' ";
            rs = st.executeQuery(sql);
            
            while (rs.next()) {
                String instruction = rs.getString("instructions");
                int examID = rs.getInt("Exam_ID");
                //System.out.println("INSTRUCTION: "+instruction);
                questionLabel = new JLabel("INSTRUCTION: "+instruction);
               studentUIPanel.add(questionLabel);
                System.out.println("____________________________");
                this.examID = examID;
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        } 
    }
    
    public void setSection() {
         try {
            String sql = "SELECT * FROM section WHERE Exam_ID='"+examID+"' ";
            rs = st.executeQuery(sql);
            
            while (rs.next()) {
                String sectionName = rs.getString("Section_Name");
                int sectionID = rs.getInt("Section_ID");
                this.sectionID.add(sectionID);
            }
           
        } catch (Exception exc) {
            exc.printStackTrace();
        } 
    }
    
     public void getSectionDetails(int SectionID) {
         try {
            String sql = "SELECT * FROM section WHERE Section_ID='"+SectionID+"' ";
            rs = st.executeQuery(sql);
            
            while (rs.next()) {
                String sectionName = rs.getString("Section_Name");
                System.out.println(" Section Name: "+sectionName);
            }
           
        } catch (Exception exc) {
            exc.printStackTrace();
        } 
    }
    

    
    public void getQuestion(int section_ID) {
         try {
             
            int questionID = 0;
            int questionType = 0;
            
            String sql = "SELECT * FROM sectionquestion WHERE Section_ID='"+section_ID+"' ";
            rs = st.executeQuery(sql);
            
            while (rs.next()) {
                questionID = rs.getInt("Question_ID"); 
                
                
                String sql2 = "SELECT * FROM question WHERE Question_ID='"+questionID+"'";
                rs2 = st2.executeQuery(sql2);
                
                while (rs2.next()) {
                    questionType = rs2.getInt("QType_ID"); 
                    
                    if (questionType == 1) {
                        String sql3 = "SELECT * FROM mcq WHERE Question_ID='"+questionID+"'";
                        rs3 = st3.executeQuery(sql3);
                        
                        while (rs3.next()) {
                            String questionText = rs3.getString("Question_Text");
                            System.out.println(questionText);
                        }
                        
                        String sql4 = "SELECT * FROM mcqoption WHERE Question_ID='"+questionID+"'";
                        rs4 = st4.executeQuery(sql4);
                        
                        System.out.println("Choose one answer: ");
                        
                        while (rs4.next()) {
                            String answer = rs4.getString("Content");
                            System.out.println(answer);
                        }
                        
                        
                        
                    } else {
                        
                    }
                    
                }
            
            }
           
            
        } catch (Exception exc) {
            exc.printStackTrace();
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

        jMenu1 = new javax.swing.JMenu();
        studentUIPanel = new javax.swing.JPanel();
        choice1 = new javax.swing.JCheckBox();
        choice2 = new javax.swing.JCheckBox();
        choice3 = new javax.swing.JCheckBox();
        choice4 = new javax.swing.JCheckBox();
        choice5 = new javax.swing.JCheckBox();
        choice6 = new javax.swing.JCheckBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        jMenu1.setText("jMenu1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("MELT SYSTEM - Student");

        choice1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                choice1ActionPerformed(evt);
            }
        });

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        javax.swing.GroupLayout studentUIPanelLayout = new javax.swing.GroupLayout(studentUIPanel);
        studentUIPanel.setLayout(studentUIPanelLayout);
        studentUIPanelLayout.setHorizontalGroup(
            studentUIPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(studentUIPanelLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(studentUIPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(choice2)
                    .addComponent(choice1)
                    .addComponent(choice3)
                    .addComponent(choice4)
                    .addComponent(choice5)
                    .addComponent(choice6))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(studentUIPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        studentUIPanelLayout.setVerticalGroup(
            studentUIPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, studentUIPanelLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addComponent(choice1)
                .addGap(18, 18, 18)
                .addComponent(choice2)
                .addGap(18, 18, 18)
                .addComponent(choice3)
                .addGap(18, 18, 18)
                .addComponent(choice4)
                .addGap(18, 18, 18)
                .addComponent(choice5)
                .addGap(18, 18, 18)
                .addComponent(choice6)
                .addGap(31, 31, 31))
        );

        jButton1.setText("SAVE");

        jButton2.setText("NEXT");

        jButton3.setText("BACK");

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("Time Left :");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(studentUIPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(368, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addGap(14, 14, 14)
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton2)
                        .addGap(31, 31, 31))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(109, 109, 109))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(studentUIPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addGap(24, 24, 24))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void choice1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_choice1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_choice1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(StudentUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(StudentUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(StudentUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(StudentUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new StudentUI().setVisible(true);
            }
        });
    }
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox choice1;
    private javax.swing.JCheckBox choice2;
    private javax.swing.JCheckBox choice3;
    private javax.swing.JCheckBox choice4;
    private javax.swing.JCheckBox choice5;
    private javax.swing.JCheckBox choice6;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JPanel studentUIPanel;
    // End of variables declaration//GEN-END:variables
}
