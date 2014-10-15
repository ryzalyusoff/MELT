package melt.View;

import melt.Model.QuestionTableModel;
import java.awt.Dimension;
import melt.DAO.QuestionDAO;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
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
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import melt.Util.SQLHelper;
import static melt.Util.SQLHelper.driver;
import static melt.Util.SQLHelper.password;
import static melt.Util.SQLHelper.url;
import static melt.Util.SQLHelper.user;
import melt.View.SettingQuestion;

/**
 *
 * @author ryzal_000
 */
public class AddQuestion extends javax.swing.JFrame {

    
    private Connection con;
    private Statement st;
    private ResultSet rs;
    
    private QuestionDAO questionDAO;
    
    private String addedQuestion;
    private String addedAnswer1;
    private String addedAnswer2;
    private String addedAnswer3;
    private String addedAnswer4;
    private String addedAnswer5;
    private String addedAnswer6;
    
    private String sql1;
    private String sql2;
    private String sql3;
    private String sql4;
    private String sql5;
    private String sql6;
    
    public static String url;
    public static String user;
    public static String password;
    public static String driver;
    
    
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
     * Creates new form addQuestion
     */
      public AddQuestion() {
        initComponents();
        
         try {
            questionDAO = new QuestionDAO();
            
            List<SettingQuestion> questions = null;

            questions = questionDAO.getAllQuestion();
            
            QuestionTableModel model = new QuestionTableModel(questions);
            questionTable.setModel(model);
    
        } catch (Exception exc) {
            JOptionPane.showMessageDialog(this, "Error: " + exc, "Error", JOptionPane.ERROR_MESSAGE);
        }
    
        // Set to full screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();
        setSize((int)width, (int)height); 
        
        setTable();
        
    }
    
    public void setTable() {
    
        // Centering the first two column's cell content
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        questionTable.getColumnModel().getColumn(0).setCellRenderer( centerRenderer );
        questionTable.getColumnModel().getColumn(1).setCellRenderer( centerRenderer );
    
        // Set the columns width
        TableColumn column = null;
        for (int i = 0; i < 3; i++) {
            column = questionTable.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(10); //sport column is bigger
            } else if (i == 1) {
                column.setPreferredWidth(30);
            } else {
                column.setPreferredWidth(780);
            }
        } 
        
    }  
      
    public void refresh(){
    
         try {
            questionDAO = new QuestionDAO();
            
            List<SettingQuestion> questions = null;

            questions = questionDAO.getAllQuestion();
            QuestionTableModel model = new QuestionTableModel(questions);
            questionTable.setModel(model);
          
            
            setTable();
            
        } catch (Exception exc) {
            JOptionPane.showMessageDialog(this, "Error: " + exc, "Error", JOptionPane.ERROR_MESSAGE);
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

        jScrollPane3 = new javax.swing.JScrollPane();
        jEditorPane1 = new javax.swing.JEditorPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        addBtn = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        questionField = new javax.swing.JTextArea();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        answerField1 = new javax.swing.JTextField();
        answerField2 = new javax.swing.JTextField();
        answerField3 = new javax.swing.JTextField();
        answerField4 = new javax.swing.JTextField();
        answerField5 = new javax.swing.JTextField();
        answerField6 = new javax.swing.JTextField();
        choice6 = new javax.swing.JCheckBox();
        choice5 = new javax.swing.JCheckBox();
        choice4 = new javax.swing.JCheckBox();
        choice3 = new javax.swing.JCheckBox();
        choice2 = new javax.swing.JCheckBox();
        choice1 = new javax.swing.JCheckBox();
        editBtn = new javax.swing.JButton();
        deleteBtn = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        questionTable = new javax.swing.JTable();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();

        jScrollPane3.setViewportView(jEditorPane1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("MELT");

        jPanel1.setBackground(new java.awt.Color(153, 153, 153));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(""), "Add Question"));

        jLabel1.setText("Question :");

        jLabel2.setText("Answers   : ");

        addBtn.setText("Add");
        addBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addBtnActionPerformed(evt);
            }
        });

        questionField.setColumns(20);
        questionField.setRows(5);
        jScrollPane2.setViewportView(questionField);

        jLabel5.setText("Answer Text");
        jLabel5.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        jLabel6.setText("Correct Answer");
        jLabel6.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        jPanel2.setBackground(new java.awt.Color(153, 153, 153));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(choice1))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(choice2, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(choice3, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(choice4, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(choice5, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(choice6, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(answerField5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 193, Short.MAX_VALUE)
                    .addComponent(answerField4, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(answerField3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(answerField2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(answerField1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(answerField6))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(choice1)
                    .addComponent(answerField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(choice2)
                    .addComponent(answerField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(answerField3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(choice3, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(33, 33, 33)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(answerField4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(choice4, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(31, 31, 31)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(answerField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(choice5))
                .addGap(26, 26, 26)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(answerField6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(choice6, javax.swing.GroupLayout.Alignment.TRAILING)))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(addBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addGap(92, 92, 92)
                                .addComponent(jLabel5)))
                        .addGap(0, 70, Short.MAX_VALUE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addGap(24, 24, 24)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                .addComponent(addBtn))
        );

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
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        questionTable.setMinimumSize(new java.awt.Dimension(46, 64));
        jScrollPane1.setViewportView(questionTable);
        if (questionTable.getColumnModel().getColumnCount() > 0) {
            questionTable.getColumnModel().getColumn(0).setPreferredWidth(5);
        }

        jScrollPane4.setViewportView(jScrollPane1);

        jMenuBar1.setPreferredSize(new java.awt.Dimension(206, 25));

        jMenu1.setText("File");

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, java.awt.event.InputEvent.ALT_MASK));
        jMenuItem1.setText("Exit");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 475, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(editBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(deleteBtn)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane4)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(deleteBtn)
                    .addComponent(editBtn))
                .addGap(19, 19, 19))
        );

        jPanel1.getAccessibleContext().setAccessibleDescription("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
       
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    String rowID = "";
    
    private void deleteBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteBtnActionPerformed
        // TODO add your handling code here:
        try  {
            
            connectDb();
             int selectedRow = questionTable.getSelectedRow();
             rowID = (questionTable.getModel().getValueAt(selectedRow,0).toString());
             
             
            int errors = 0;
            
            String deleteAnswer = "DELETE FROM mcqoption WHERE Question_ID='"+rowID+"'";
            int rows1 = st.executeUpdate(deleteAnswer);
            if (rows1 == 0) {
                 errors++;
            }
            
             
            String deleteQuestion = "DELETE FROM question WHERE Question_ID='"+rowID+"'";
            int rows2 = st.executeUpdate(deleteQuestion);
             if (rows2 == 0) {
                 errors++;
             }
            
             
            String deleteMCQ = "DELETE FROM mcq WHERE Question_ID='"+rowID+"'";
            int rows3 = st.executeUpdate(deleteMCQ);
            if (rows3 == 0) {
                 errors++;
            } 
             
             if (errors != 0) {
                JOptionPane.showMessageDialog(null, "Question was successfully deleted!"); 
                refresh();
             } else {
                 JOptionPane.showMessageDialog(null, "ERROR : Delete was not successfull!");
                 refresh();
             }
             

        }  catch (Exception exc) {
            exc.printStackTrace();
        }
    }//GEN-LAST:event_deleteBtnActionPerformed

     
    
    private void editBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editBtnActionPerformed
        
        EditQuestion editQPanel = new EditQuestion();
        editQPanel.setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
        
        editQPanel.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent windowEvent) { 
                refresh();
            }
        });
        
        int selectedRow = questionTable.getSelectedRow();
        String rowQuestion = (questionTable.getModel().getValueAt(selectedRow,2).toString());
        
        int rowSelected = questionTable.getSelectedRow();
        rowID = (questionTable.getModel().getValueAt(rowSelected,1).toString());
        editQPanel.questionID = rowID;
        
        editQPanel.questionField.setText(rowQuestion);
        
        // Get all the answers
        String questionID = rowID;
        connectDb();
        //String st = "SELECT * FROM questions WHERE id='"+rowID+"'";
        
        
        try {
            
            connectDb();

            rowID = (questionTable.getModel().getValueAt(rowSelected,1).toString());
            
            String query = "SELECT * FROM mcqoption WHERE Question_ID='"+rowID+"' ";
            
            
            // create the java statement
            Statement st = con.createStatement();

            // execute the query, and get a java resultset
            ResultSet rs = st.executeQuery(query);
            
            String answer1 = "";
            String answer2 = "";
            String answer3 = "";
            String answer4 = "";
            String answer5 = "";
            String answer6 = "";
            
            int answerID1 = 0;
            int answerID2 = 0;
            int answerID3 = 0;
            int answerID4 = 0;
            int answerID5 = 0;
            int answerID6 = 0;
            
            
            
            int counter = 1;
            while (rs.next())
            {
              int id = rs.getInt("MCQOption_id");
              int question = rs.getInt("Question_ID");
              String answer = rs.getString("Content");
              int correct = rs.getInt("isAnswerOrNot");
              
              
              // print the results
              //System.out.format("%s, %s, %s\n", id, question, answer);
              
              if (counter == 1){
                  answer1 = answer;
                  answerID1 = id;
                  if (correct == 1) {
                      editQPanel.choice1.setSelected(true);
                  }
              } 
              
              if (counter == 2) {
                  answer2 = answer;
                  answerID2 = id;
                  if (correct == 1) {
                      editQPanel.choice2.setSelected(true);
                  }
              } 
              
              if (counter == 3) {
                  answer3 = answer;
                  answerID3 = id;
                  if (correct == 1) {
                     editQPanel.choice3.setSelected(true);
                  }
              } 
              
              if (counter == 4) {
                  answer4 = answer;
                  answerID4 = id;
                  if (correct == 1) {
                      editQPanel.choice5.setSelected(true);
                  }
              }
              
              if (counter == 5) {
                  answer5 = answer;
                  answerID5 = id;
                  if (correct == 1) {
                      editQPanel.choice4.setSelected(true);
                  }
              }
              
              if (counter == 6) {
                  answer6 = answer;
                  answerID6 = id;
                  if (correct == 1) {
                      editQPanel.choice6.setSelected(true);
                  }
              }
                  
              
              
              counter++;
              
            }
            st.close();
            
            
            editQPanel.answer1.setText(answer1);
            editQPanel.answer2.setText(answer2);
            editQPanel.answer3.setText(answer3);
            editQPanel.answer4.setText(answer4);
            editQPanel.answer5.setText(answer5);
            editQPanel.answer6.setText(answer6);
    
            editQPanel.answerID1 =  answerID1;
            editQPanel.answerID2 =  answerID2;
            editQPanel.answerID3 =  answerID3;  
            editQPanel.answerID4 =  answerID4;
            editQPanel.answerID5 =  answerID5;
            editQPanel.answerID6 =  answerID6;
            
            
        } catch (SQLException ex) {
            Logger.getLogger(AddQuestion.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //close(st, rs);
        }    
       
        
        editQPanel.setVisible(true);
        
        
    }//GEN-LAST:event_editBtnActionPerformed

    private void addBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addBtnActionPerformed
        try  {
            connectDb();

            this.addedQuestion = questionField.getText();
            this.addedAnswer1 = answerField1.getText();
            this.addedAnswer2 = answerField2.getText();
            this.addedAnswer3 = answerField3.getText();
            this.addedAnswer4 = answerField4.getText();
            this.addedAnswer5 = answerField5.getText();
            this.addedAnswer6 = answerField6.getText( );

            //String finalAnswer = addedAnswer1 +", "+ addedAnswer2+ ", " + addedAnswer3 + ", " + addedAnswer4;

            /////////////// ADDED question to DB ////////////////
            //String sql = "INSERT INTO questions (question, answer) VALUES('"+addedQuestion+"','"+finalAnswer+"')";

            int QType_ID = 1;
            
            // Insert Question into question table
            String sql = "INSERT INTO question (QType_ID) VALUES('"+QType_ID+"')";

             int key = 0;
            
            /////////////// INSERT THE QUESTION & Get the last id that was inserted for the question ////////////////
             PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
             pstmt.executeUpdate();
             ResultSet keys = pstmt.getGeneratedKeys();
             keys.next();
             key = keys.getInt(1);  
            
            // Insert Question into mcq table 
            String sql0 = "INSERT INTO mcq (Question_ID, Question_Text, QType_ID) VALUES('"+key+"','"+addedQuestion+"', '"+QType_ID+"')";
            st.executeUpdate(sql0);

            /////////////// INSERT the correct choice (of answer) to DB  ////////////////

            char correctAnswer;

            int isSelected = 0;

            if (choice1.isSelected()) {
                sql1 = "INSERT INTO mcqoption (Content,Question_ID, isAnswerOrNot) VALUES('"+addedAnswer1+"', '"+key+"' , 1)";
                isSelected ++;
            } else {
                sql1 = "INSERT INTO mcqoption (Content,Question_ID) VALUES('"+addedAnswer1+"', '"+key+"')";
            }

            if (choice2.isSelected()) {
                sql2 = "INSERT INTO mcqoption (Content,Question_ID, isAnswerOrNot) VALUES('"+addedAnswer2+"', '"+key+"' , 1)";
                isSelected ++;
            } else {
                sql2 = "INSERT INTO mcqoption (Content,Question_ID) VALUES('"+addedAnswer2+"', '"+key+"')";
            }

            if (choice3.isSelected()) {
                sql3 = "INSERT INTO mcqoption (Content,Question_ID, isAnswerOrNot) VALUES('"+addedAnswer3+"', '"+key+"' , 1)";
                isSelected ++;
            } else {
                sql3 = "INSERT INTO mcqoption (Content,Question_ID) VALUES('"+addedAnswer3+"', '"+key+"')";
            }

            if (choice4.isSelected()) {
                sql4 = "INSERT INTO mcqoption (Content,Question_ID, isAnswerOrNot) VALUES('"+addedAnswer4+"', '"+key+"' , 1)";
                isSelected ++;
            } else {
                sql4 = "INSERT INTO mcqoption (Content,Question_ID) VALUES('"+addedAnswer4+"', '"+key+"')";
            }

            if (choice5.isSelected()) {
                sql5 = "INSERT INTO mcqoption (Content,Question_ID, isAnswerOrNot) VALUES('"+addedAnswer5+"', '"+key+"' , 1)";
                isSelected ++;
            } else {
                sql5 = "INSERT INTO mcqoption (Content,Question_ID) VALUES('"+addedAnswer5+"', '"+key+"')";
            }

            if (choice6.isSelected()) {
                sql6 = "INSERT INTO mcqoption (Content,Question_ID, isAnswerOrNot) VALUES('"+addedAnswer6+"', '"+key+"' , 1)";
                isSelected ++;
            } else {
                sql6 = "INSERT INTO mcqoption (Content,Question_ID) VALUES('"+addedAnswer6+"', '"+key+"')";
            }

            int emptycounter = 0;
            
            if (!this.addedAnswer1.equals("")) { emptycounter++;} 
            if (!this.addedAnswer2.equals("")) { emptycounter++;}
            if (!this.addedAnswer3.equals("")) { emptycounter++;}
            if (!this.addedAnswer4.equals("")) { emptycounter++;}
            if (!this.addedAnswer5.equals("")) { emptycounter++;}
            if (!this.addedAnswer6.equals("")) { emptycounter++;}
            
          
            
            if (!this.addedQuestion.equals("")) {
                
                if (emptycounter >= 2 ){
               //System.out.println("Answer is:" + emptycounter);

                     if ( isSelected > 0 )    {
  
                        /////////////// INSERT the answers to DB  ////////////////
                        st.executeUpdate(sql1);
                        st.executeUpdate(sql2);
                        st.executeUpdate(sql3);
                        st.executeUpdate(sql4);
                        st.executeUpdate(sql5);
                        st.executeUpdate(sql6);
                        
                        // RESET the filed
                        questionField.setText("");
                        answerField1.setText("");
                        answerField2.setText("");
                        answerField3.setText("");
                        answerField4.setText("");
                        answerField5.setText("");
                        answerField6.setText("");

                    refresh();
                    } else {
                        JOptionPane.showMessageDialog(null, "Please select at least one correct answer!", "error", JOptionPane.ERROR_MESSAGE);
                        String deletesql = "DELETE FROM question WHERE Question_ID='"+key+"'";
                        st.executeUpdate(deletesql);
                        String deletesql2 = "DELETE FROM mcq WHERE Question_ID='"+key+"'";
                        st.executeUpdate(deletesql2);
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Please fill in at least 2 answers!", "error", JOptionPane.ERROR_MESSAGE);
                    String deletesql = "DELETE FROM question WHERE Question_ID='"+key+"'";
                    st.executeUpdate(deletesql);
                    String deletesql2 = "DELETE FROM mcq WHERE Question_ID='"+key+"'";
                    st.executeUpdate(deletesql2);
                }
                
            } else {
                JOptionPane.showMessageDialog(null, "Please enter the question!", "error", JOptionPane.ERROR_MESSAGE);
                String deletesql = "DELETE FROM question WHERE Question_ID='"+key+"'";
                st.executeUpdate(deletesql);
                String deletesql2 = "DELETE FROM mcq WHERE Question_ID='"+key+"'";
                st.executeUpdate(deletesql2);
                
            }
            

        } catch (Exception exc) {
            exc.printStackTrace();
            
        }
    }//GEN-LAST:event_addBtnActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addBtn;
    private javax.swing.JTextField answerField1;
    private javax.swing.JTextField answerField2;
    private javax.swing.JTextField answerField3;
    private javax.swing.JTextField answerField4;
    private javax.swing.JTextField answerField5;
    private javax.swing.JTextField answerField6;
    private javax.swing.JCheckBox choice1;
    private javax.swing.JCheckBox choice2;
    private javax.swing.JCheckBox choice3;
    private javax.swing.JCheckBox choice4;
    private javax.swing.JCheckBox choice5;
    private javax.swing.JCheckBox choice6;
    private javax.swing.JButton deleteBtn;
    private javax.swing.JButton editBtn;
    private javax.swing.JEditorPane jEditorPane1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTextArea questionField;
    public javax.swing.JTable questionTable;
    // End of variables declaration//GEN-END:variables
}
