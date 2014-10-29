/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package melt.View;

import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import melt.DAO.QuestionDAO;
import melt.Model.QuestionTableModel;
import melt.Util.SQLHelper;
import melt.Model.QuestionTableModel;

/**
 *
 * @author Ghader
 */
public class AddEssay extends javax.swing.JFrame implements WindowListener{
     private Connection con;
    private Statement st;
    private ResultSet rs;
    
    private String sql1;

    private String sql;
     
    public static String url;
    public static String user;
    public static String password;
    public static String driver;
   
    private String instructions;
    private String noofwords;
    private int QType_ID;
    
    private QuestionDAO questionDAO;
    
    private ChooseQuestionsPanel fatherPanel;
    private AddEssay.addingState addingFlag;// 0 ->original state 1->adding when edit the sections

    
    @Override
    public void windowOpened(WindowEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowClosing(WindowEvent e) {
        if (addingFlag != AddEssay.addingState.WHENEDITSECTIONS) {
//            new melt.View.StartupPanel().setVisible(true);
        new melt.View.QuestionChoicePanel().setVisible(true);
        }
        this.dispose();
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowClosed(WindowEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowIconified(WindowEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowActivated(WindowEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    /**
     * Creates new form AddEssay
     */
    public AddEssay() {
        initComponents();
        
        startSQL() ;
        connectDb();
        
       jTextArea2.setEditable(false);
        
         Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();
        setSize((int)width, (int)height); 
        
    }
    
     /**
     * Creates new form AddEssay
     */
    public AddEssay(AddEssay.addingState addingFlag, ChooseQuestionsPanel fatherPanel) {
        initComponents();
        this.addingFlag = addingFlag;
        this.fatherPanel = fatherPanel;

        // Set to full screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();
        setSize((int) width, (int) height);

        try {
            questionDAO = new QuestionDAO();

            java.util.List<SettingQuestion> questions = null;

            questions = questionDAO.getAllFIBQuestion();

            QuestionTableModel model = new QuestionTableModel(questions);
            //questionTable.setModel(model);

        } catch (Exception exc) {
            JOptionPane.showMessageDialog(this, "Error: " + exc, "Error", JOptionPane.ERROR_MESSAGE);
        }

        //setTable();
        //addWindowListener(this);

    }
    
    public static enum addingState {

        ORIGINAL, WHENEDITSECTIONS
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

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        SaveBtn = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jLabel1.setText("Enter the essay's instructions in the following field:");

        jLabel2.setText("Enter the number of words");

        SaveBtn.setText("Save");
        SaveBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SaveBtnActionPerformed(evt);
            }
        });

        jLabel5.setText("The answer's  area:");

        jTextArea2.setColumns(20);
        jTextArea2.setRows(5);
        jScrollPane2.setViewportView(jTextArea2);

        jButton1.setText("<<< Back to Main Menu");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addComponent(jScrollPane2)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addGap(69, 69, 69)
                                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(SaveBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 252, Short.MAX_VALUE)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(65, 65, 65)))))
                .addGap(10, 10, 10))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19)
                .addComponent(jLabel5)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(53, 53, 53)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 86, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton1)
                            .addComponent(SaveBtn))
                        .addGap(19, 19, 19))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

   
    private void SaveBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SaveBtnActionPerformed

        instructions = jTextArea1.getText();
        noofwords= jTextField1.getText();
        

     
          if (!instructions.equals("")&& instructions!=null){
                  
                  
                  if (!noofwords.equals("") && noofwords!= null){
           
                      try {
                          QType_ID = 3;
                          
                          sql = "INSERT INTO question (QType_ID) VALUES("+QType_ID+")";
                          
                          int key = 0;
                          /////////////// INSERT THE QUESTION & Get the last id that was inserted for the question ////////////////
                          connectDb();
                          PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                          pstmt.executeUpdate();
                          ResultSet keys = pstmt.getGeneratedKeys();
                          keys.next();
                          key = keys.getInt(1);
                          
                          
                          /* instructions = jTextArea1.getText();
                          noofwords=  Integer.parseInt(jTextField1.getText());
                          */
                         sql1 = "INSERT INTO essay (noofwords,instructions,QType_ID,Question_ID) VALUES(' "+noofwords+"', '"+instructions+"', 3,' " + key + "')";
                          int rows = st.executeUpdate(sql1);
                          if (rows > 0) {
                              JOptionPane.showMessageDialog(this, "Question is saved successfully!");
                          }
                          jTextArea1.setText("");
                          jTextField1.setText("");
                          if (addingFlag==AddEssay.addingState.WHENEDITSECTIONS) {
                        fatherPanel.refresh();
                        dispose();
                        //fatherPanel.setVisible(true);
                
                    }
                          System.out.println(sql1);
                          
                      } catch (Exception exc) {
                          exc.printStackTrace();
                      }
                          
            }
 else
                        JOptionPane.showMessageDialog(null, "you have to add the number of words!");
                  }
                  else
                        JOptionPane.showMessageDialog(null, "you have to add instructions!");
          
    }//GEN-LAST:event_SaveBtnActionPerformed
/*public String Save(String inst, String nw){
       instructions = inst;
        noofwords= nw;
      

     
          if (!instructions.equals("")&& instructions!=null){
                  
                  
                  if (!noofwords.equals("") && noofwords!= null){
           
                      try {
                          QType_ID = 3;
                          
                          sql = "INSERT INTO question (QType_ID) VALUES("+QType_ID+")";
                          
                          int key = 0;
                          /////////////// INSERT THE QUESTION & Get the last id that was inserted for the question ////////////////
                          connectDb();
                          PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                          pstmt.executeUpdate();
                          ResultSet keys = pstmt.getGeneratedKeys();
                          keys.next();
                          key = keys.getInt(1);
                          
                          
                          /* instructions = jTextArea1.getText();
                          noofwords=  Integer.parseInt(jTextField1.getText());
                          */
                  /*      sql1 = "INSERT INTO essay (noofwords,instructions,QType_ID,Question_ID) VALUES(' "+noofwords+"', '"+instructions+"', 3,' " + key + "')";
                          int rows = st.executeUpdate(sql1);
                          if (rows > 0) {
                              JOptionPane.showMessageDialog(this, "Question is saved successfully!");
                          }
                          //jTextArea1.setText("");
                          //jTextField1.setText("");
                          System.out.println(sql1);
                          
                      } catch (Exception exc) {
                          exc.printStackTrace();
                      }
                      
               
                  
                      
                      
                 
            }
 else
                        JOptionPane.showMessageDialog(null, "you have to add the number of words!");
                  }
                  else
                        JOptionPane.showMessageDialog(null, "you have to add instructions!");
          return inst + nw;
}

*/

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        dispose();
        new melt.View.QuestionChoicePanel().setVisible(true);


    }//GEN-LAST:event_jButton1ActionPerformed

    
  

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton SaveBtn;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}