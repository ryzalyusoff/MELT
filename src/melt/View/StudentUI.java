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
        getSection();
        
        for (int i : sectionID) {
            System.out.print("Section ID: "+i+" ||");
            getSectionDetails(i);
            
            setSubSection(i);
            
            for (int j : subSectionID) { 
                getQuestion(j);
            }
            
            
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
                System.out.println("INSTRUCTION: "+instruction);
                System.out.println("____________________________");
                this.examID = examID;
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        } 
    }
    
    public void getSection() {
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
    
     public void setSubSection(int SectionID) {
         try {
            String sql = "SELECT * FROM subsection WHERE Section_ID='"+SectionID+"' ";
            rs = st.executeQuery(sql);
            
            while (rs.next()) {
                //String subSectionName = rs.getString("Section_Name");
                int subSectionID = rs.getInt("SubSection_ID");
                //System.out.println("Subsection ID: "+subSectionID);
                this.subSectionID.add(subSectionID);
            }
           
        } catch (Exception exc) {
            exc.printStackTrace();
        } 
    }
    
    public void getQuestion(int SubSection_ID) {
         try {
             
            int questionID = 0;
            int questionType = 0;
            
            String sql = "SELECT * FROM subsectionquestion WHERE SubSection_ID='"+SubSection_ID+"' ";
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

        studentUIPanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("MELT SYSTEM - Student");

        javax.swing.GroupLayout studentUIPanelLayout = new javax.swing.GroupLayout(studentUIPanel);
        studentUIPanel.setLayout(studentUIPanelLayout);
        studentUIPanelLayout.setHorizontalGroup(
            studentUIPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 576, Short.MAX_VALUE)
        );
        studentUIPanelLayout.setVerticalGroup(
            studentUIPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 338, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(studentUIPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(studentUIPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
    private javax.swing.JPanel studentUIPanel;
    // End of variables declaration//GEN-END:variables
}
