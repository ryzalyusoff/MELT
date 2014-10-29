/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package melt.View;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import melt.Util.SQLHelper;
import static melt.View.StudentUI.driver;
import static melt.View.StudentUI.password;
import static melt.View.StudentUI.url;
import static melt.View.StudentUI.user;

/**
 *
 * @author ryzal_000
 */
public class ExamMCQ extends javax.swing.JFrame {

    private Connection con;
    private Statement st;
    private Statement st2;
    private Statement st3;
    private ResultSet rs;
    private ResultSet rs2;
    private ResultSet rs3;
    
    public static String url;
    public static String user;
    public static String password;
    public static String driver;
    
    ArrayList<String> questionText = new ArrayList<>();
    ArrayList<Integer> questionID = new ArrayList<>();
    
    
    public int SectionID = 0;
    public int ExamID = 0;
    
    
    int num = 0;
    
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
        } catch (Exception e) {
            e.printStackTrace();
        } 
        
    }
    
    
    /**
     * Creates new form ExamMCQ
     */
    public ExamMCQ() {
        initComponents();
        questionField.setEditable(false); 
      
        
        connectDb();
        
        try {
            String sql1 = "SELECT * FROM section,exam WHERE section.Exam_ID = exam.Exam_ID AND section.Section_Name='Section 1' AND exam.Exam_ID = 1";
            rs = st.executeQuery(sql1);
        
            while(rs.next()) {
                SectionID = rs.getInt("Section_ID");
                ExamID = rs.getInt("Exam_ID");
            }
      
            String sql2 = "SELECT * FROM sectionquestion,question,mcq, fib WHERE "
                    + "sectionquestion.Section_ID='"+SectionID+"' "
                    + "AND sectionquestion.Question_ID = question.Question_ID ";
            
            rs2 = st2.executeQuery(sql2);
        
            int qID = 0;
            
            while(rs2.next()) {
                
                // Set question ID
                qID = rs2.getInt("Question_ID");
                this.questionID.add(qID);
                
                int questionType =rs2.getInt("QType_ID");
                    
                if (questionType == 1) {
                  
                    String sql3 = "SELECT * FROM mcq WHERE Question_ID='"+qID+"'";
                    rs3 = st3.executeQuery(sql3);
                    while (rs3.next()) {
                        String question = rs2.getString("Question_Text");
                        this.questionText.add(question);
                    }
                    
                } else if (questionType == 2) {
                    String sql3 = "SELECT * FROM fib WHERE QuestionID='"+qID+"'";
                    rs3 = st3.executeQuery(sql3);
                    while (rs3.next()) {
                        String question = rs2.getString("QuestionText");
                        this.questionText.add(question);
                    }
                } else {
                
                }
                
                /*
                String question = rs2.getString("Question_Text");
                int qID = rs2.getInt("Question_ID");
                System.out.println(question);
                this.questionText.add(question);
                this.questionID.add(qID);
                */
            }
 
        } catch (Exception exc) {
            exc.printStackTrace();
        } 
        
        
       
        setQuestion(num);
        setAnswer(num);
        
    }
    
    
    public void setQuestion(int num){
        String latestQuestion = questionText.get(num);        
        questionField.setText(num+1 + ". "+ latestQuestion);
 
    }
    
     public void setAnswer(int num){
         
        choice1.setVisible(false);
        choice2.setVisible(false);
        choice3.setVisible(false);
        choice4.setVisible(false);
        choice5.setVisible(false);
        choice6.setVisible(false);
        
         System.out.println("Question ID is: "+questionID.get(num)); 
         try {
            int latestQID = questionID.get(num);
            String sql = "SELECT * FROM mcqoption WHERE Question_ID ='"+latestQID+"' ";
            rs = st.executeQuery(sql);
            int counter = 1;
            while(rs.next()) {
                String answer = rs.getString("Content");
                
                if (!answer.equals("")) { 
                    if (counter == 1) {
                        choice1.setText(answer);
                        choice1.setVisible(true);
                    } else if (counter == 2) {
                        choice2.setText(answer);
                        choice2.setVisible(true);
                    } else if (counter == 3) {
                        choice3.setText(answer);
                        choice3.setVisible(true);
                    } else if (counter == 4) {
                        choice4.setText(answer);
                        choice4.setVisible(true);
                    } else if (counter == 5) {  
                        choice5.setText(answer);
                        choice5.setVisible(true);
                    } else if (counter == 6) {
                        choice6.setText(answer);
                        choice6.setVisible(true);
                    }  
                }
                
                counter++;
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

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        questionField = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        choice1 = new javax.swing.JCheckBox();
        choice2 = new javax.swing.JCheckBox();
        choice3 = new javax.swing.JCheckBox();
        choice4 = new javax.swing.JCheckBox();
        choice5 = new javax.swing.JCheckBox();
        choice6 = new javax.swing.JCheckBox();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));

        questionField.setBackground(new java.awt.Color(240, 240, 240));
        questionField.setColumns(20);
        questionField.setRows(5);
        jScrollPane1.setViewportView(questionField);

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setText("Choose correct answer(s) :");

        choice1.setText("Choice1");
        choice1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                choice1ActionPerformed(evt);
            }
        });

        choice2.setText("Choice 2");

        choice3.setText("Choice 3");

        choice4.setText("Choice 4");

        choice5.setText("Choice 5");

        choice6.setText("Choice 6");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel2))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(choice2)
                            .addComponent(choice1)
                            .addComponent(choice3)
                            .addComponent(choice4)
                            .addComponent(choice5)
                            .addComponent(choice6))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
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
                .addGap(0, 22, Short.MAX_VALUE))
        );

        jButton1.setText("SAVE");

        jButton2.setText("PREVIOUS");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("NEXT");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 268, Short.MAX_VALUE)
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton3))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        
        if (num!= questionText.size()-1) {
            num+=1;
            setQuestion(num);
            setAnswer(num);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
       
        if (num != 0) {
            num-=1;
            setQuestion(num);
            setAnswer(num);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

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
            java.util.logging.Logger.getLogger(ExamMCQ.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ExamMCQ.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ExamMCQ.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ExamMCQ.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ExamMCQ().setVisible(true);
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
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea questionField;
    // End of variables declaration//GEN-END:variables
}
