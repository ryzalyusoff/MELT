/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package melt.View;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.management.Query;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.management.Query;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import melt.DAO.QuestionDAO;
import melt.Model.QuestionTableModel;
import melt.Util.SQLHelper;
import static melt.Util.SQLHelper.driver;
import static melt.Util.SQLHelper.password;
import static melt.Util.SQLHelper.url;
import static melt.Util.SQLHelper.user;
import static sun.security.krb5.Config.refresh;

/**
 *
 * @author Ghader
 */
public class AddQuestionFib extends javax.swing.JFrame implements WindowListener {

    private Connection con;
    private Statement st;
    private ResultSet rs;

    private String sql1;
    private String sql2;

    public static String url;
    public static String user;
    public static String password;
    public static String driver;

    private String hlanswer;
    private String question;
    private String hlanswer2;
    private String instructions;
    private String tempQuestion;
    private String tempAnswer;

    String rowID = "";

    private QuestionDAO questionDAO;

    int counter = 0;

    private ChooseQuestionsPanel fatherPanel;
    private addingState addingFlag;// 0 ->original state 1->adding when edit the sections

    @Override
    public void windowOpened(WindowEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowClosing(WindowEvent e) {
        if (addingFlag != addingState.WHENEDITSECTIONS) {
            new melt.View.StartupPanel().setVisible(true);
        }
        this.dispose();
        new melt.View.QuestionChoicePanel().setVisible(true);
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
     * Creates new form AddQuestionFib
     */
    public AddQuestionFib(addingState addingFlag, ChooseQuestionsPanel fatherPanel) {
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

            List<SettingQuestion> questions = null;

            questions = questionDAO.getAllFIBQuestion();

            QuestionTableModel model = new QuestionTableModel(questions);
            questionTable.setModel(model);

        } catch (Exception exc) {
            JOptionPane.showMessageDialog(this, "Error: " + exc, "Error", JOptionPane.ERROR_MESSAGE);
        }

        setTable();
        addWindowListener(this);

    }

    /**
     * Creates new form AddQuestionFib
     */
    public AddQuestionFib() {
        initComponents();

        // Set to full screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();
        setSize((int) width, (int) height);

        try {
            questionDAO = new QuestionDAO();

            List<SettingQuestion> questions = null;

            questions = questionDAO.getAllFIBQuestion();

            QuestionTableModel model = new QuestionTableModel(questions);
            questionTable.setModel(model);

        } catch (Exception exc) {
            JOptionPane.showMessageDialog(this, "Error: " + exc, "Error", JOptionPane.ERROR_MESSAGE);
        }

        setTable();

    }

    public void setTable() {

        // Centering the first two column's cell content
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        questionTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        questionTable.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);

        // Set the columns width
        TableColumn column = null;
        for (int i = 0; i < 3; i++) {
            column = questionTable.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(69); //sport column is bigger
            } else if (i == 1) {
                column.setPreferredWidth(164);
            } else {
                column.setPreferredWidth(800);
            }
        }

    }

    public void refresh() {

        try {
            questionDAO = new QuestionDAO();

            List<SettingQuestion> questions = null;

            questions = questionDAO.getAllFIBQuestion();

            QuestionTableModel model = new QuestionTableModel(questions);
            questionTable.setModel(model);

        } catch (Exception exc) {
            JOptionPane.showMessageDialog(this, "Error: " + exc, "Error", JOptionPane.ERROR_MESSAGE);
        }

        setTable();

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        questionField = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        addBtn = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        instructionField = new javax.swing.JTextArea();
        addBlankBtn = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        questionTable = new javax.swing.JTable();
        editBtn = new javax.swing.JButton();
        deleteBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("MELT - FIB Question");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("FIB Question Overview"));

        jLabel3.setText("Write the question's instructions in the following field:");

        questionField.setColumns(20);
        questionField.setRows(5);
        questionField.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                questionFieldMouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(questionField);

        jLabel1.setText("Write the question in the following field:");

        addBtn.setText("ADD");
        addBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addBtnActionPerformed(evt);
            }
        });

        instructionField.setColumns(20);
        instructionField.setRows(3);
        instructionField.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                instructionFieldMouseReleased(evt);
            }
        });
        jScrollPane3.setViewportView(instructionField);

        addBlankBtn.setText("Add a Blank");
        addBlankBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addBlankBtnActionPerformed(evt);
            }
        });

        jButton1.setText("<<< Back to Main Menu");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(addBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1)
                    .addComponent(jScrollPane3)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(addBlankBtn))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1))
                        .addGap(0, 238, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jButton1)
                .addGap(51, 51, 51)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(addBlankBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(addBtn)
                .addContainerGap())
        );

        questionTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "No", "Question ID", "Question"
            }
        ));
        jScrollPane2.setViewportView(questionTable);

        editBtn.setText("EDIT");
        editBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editBtnActionPerformed(evt);
            }
        });

        deleteBtn.setText("DELETE");
        deleteBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 460, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(editBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(deleteBtn)
                .addGap(2, 2, 2))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 468, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(deleteBtn)
                    .addComponent(editBtn))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void addBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addBtnActionPerformed

        question = questionField.getText();
        instructions = instructionField.getText();

        // set the match patter of "[]"
        Matcher matcher = Pattern.compile("\\[([^\\]]+)").matcher(question);

        ArrayList<String> answers = new ArrayList<String>();

        int pos = -1;
        while (matcher.find(pos + 1)) {
            pos = matcher.start();
            // Add the matched word to the Arraylist
            answers.add(matcher.group(1));
        }

        // Find the word with bracket and replace it with blank
        String newQuestion = question.replaceAll("\\[.*?]", "_____");

        //System.out.println(answers);
        System.out.println(newQuestion);

        int key = 0;

        try {
            connectDb();

            int QType_ID = 2;

            String sql = "INSERT INTO question (QType_ID) VALUES(" + QType_ID + ")";

            /////////////// INSERT THE QUESTION & Get the last id that was inserted for the question ////////////////
            PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.executeUpdate();
            ResultSet keys = pstmt.getGeneratedKeys();
            keys.next();
            key = keys.getInt(1);

            // Insert Question into DB
            sql1 = "INSERT INTO fib (QuestionID,QuestionText,QuestionBlank, QuestionType,Instructions) VALUES(' " + key + "', '" + question + "', '" + newQuestion + "' , 2, '" + instructions + "')";
            st.executeUpdate(sql1);

            System.out.println(sql1);

        } catch (Exception exc) {
            exc.printStackTrace();
        }

        // Loop through ArrayList and Insert Answer into DB
        for (String answer : answers) {

            try {
                connectDb();

                // Insert answer to DB
                sql2 = "INSERT INTO fibanswer (AnswerContentText,QuestionID) VALUES('" + answer + "', '" + key + "')";
                st.executeUpdate(sql2);

                System.out.println(sql2);

            } catch (Exception exc) {
                exc.printStackTrace();
            }

        }

        refresh();
        if (addingFlag == addingState.WHENEDITSECTIONS) {
            fatherPanel.refresh();
            dispose();
                        //fatherPanel.setVisible(true);

        }

        instructionField.setText("");
        questionField.setText("");

    }//GEN-LAST:event_addBtnActionPerformed

    private void questionFieldMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_questionFieldMouseReleased
      }//GEN-LAST:event_questionFieldMouseReleased

    private void instructionFieldMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_instructionFieldMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_instructionFieldMouseReleased

    private void addBlankBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addBlankBtnActionPerformed
        try {

            question = questionField.getText();
            instructions = instructionField.getText();

            hlanswer = questionField.getSelectedText();
            hlanswer2 = "[" + hlanswer + "]";

            int start = questionField.getText().indexOf(hlanswer);

            if (start >= 0 && hlanswer.length() > 0) {
                questionField.replaceRange(hlanswer2, start, start + hlanswer.length());
            }

        } catch (Exception exc) {
            exc.printStackTrace();
        }

    }//GEN-LAST:event_addBlankBtnActionPerformed


    private void editBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editBtnActionPerformed

        EditQuestionFIB editQPanel = new EditQuestionFIB();
        editQPanel.setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);

        editQPanel.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                refresh();
            }
        });

        editQPanel.setVisible(true);

        connectDb();

        try {

            int rowcheck = questionTable.getSelectedRow();

            if (rowcheck > -1) {

                // Get the selected row
                int selectedRow = questionTable.getSelectedRow();

                // Get & set the question at column number 2 (from the table)
                String rowQuestion = (questionTable.getModel().getValueAt(selectedRow, 2).toString());

                // Get & set the id at column number 1 (from the table)
                rowID = (questionTable.getModel().getValueAt(selectedRow, 1).toString());

                editQPanel.questionID = rowID;

                // Get the full form of the question
                String query = "SELECT * FROM fib WHERE QuestionID='" + rowID + "' ";
                st = con.createStatement();
                rs = st.executeQuery(query);

                String QuestionText = "";
                String Instructions = "";

                while (rs.next()) {
                    QuestionText = rs.getString("QuestionText");
                    Instructions = rs.getString("Instructions");
                }

                editQPanel.instructionField.setText(Instructions);
                editQPanel.questionField.setText(QuestionText);

                // Set the Question field on the edit panel based on the selected row
                rowID = (questionTable.getModel().getValueAt(selectedRow, 1).toString());

            }

        } catch (NullPointerException ex) {
            System.out.println(ex);
        } catch (SQLException ex) {
            Logger.getLogger(AddQuestionFib.class.getName()).log(Level.SEVERE, null, ex);
        }


    }//GEN-LAST:event_editBtnActionPerformed

    private void deleteBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteBtnActionPerformed
        try {

            connectDb();
            int selectedRow = questionTable.getSelectedRow();
            rowID = (questionTable.getModel().getValueAt(selectedRow, 1).toString());

            int errors = 0;

            String deleteAnswer = "DELETE FROM fibanswer WHERE QuestionID='" + rowID + "'";
            int rows2 = st.executeUpdate(deleteAnswer);
            if (rows2 == 0) {
                errors++;
            }

            String deleteQuestion = "DELETE FROM fib WHERE QuestionID='" + rowID + "'";
            int rows1 = st.executeUpdate(deleteQuestion);
            if (rows1 == 0) {
                errors++;
            }

            if (errors == 0) {
                JOptionPane.showMessageDialog(null, "Question was successfully deleted!");
                refresh();
            } else {
                JOptionPane.showMessageDialog(null, "ERROR : Delete was not successfull!");
                refresh();
            }

        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }//GEN-LAST:event_deleteBtnActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        dispose();
        new melt.View.QuestionChoicePanel().setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing

        new melt.View.QuestionChoicePanel().setVisible(true);

    }//GEN-LAST:event_formWindowClosing


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addBlankBtn;
    private javax.swing.JButton addBtn;
    private javax.swing.JButton deleteBtn;
    private javax.swing.JButton editBtn;
    private javax.swing.JTextArea instructionField;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextArea questionField;
    private javax.swing.JTable questionTable;
    // End of variables declaration//GEN-END:variables
}
