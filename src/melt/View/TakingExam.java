/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package melt.View;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.Timer;
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
public class TakingExam extends javax.swing.JFrame {

    private Connection con;
    private Statement st;
    private Statement st1;
    private Statement st2;
    private Statement st3;
    private ResultSet rs;
    private ResultSet rs1;
    private ResultSet rs2;
    private ResultSet rs3;
    
    public static String url;
    public static String user;
    public static String password;
    public static String driver;
    
    ArrayList<String> questionText = new ArrayList<>();
    ArrayList<Integer> questionID = new ArrayList<>();
    ArrayList<Integer> questionType = new ArrayList<>();
    ArrayList<String> correctAnswer = new ArrayList<>();
    
    public int SectionID = 0;
    public int ExamID = 0;
    public String SectionName = "";
    public String SubSectionName = "";
    public int TotalMark = 0;
    public int TotalScore = 0;
    int sessionkey = 0;
    int latestQID = 0;
    Timer timecounter;
    Date timeLimit;
    JTextArea essayAnswer = new JTextArea();
    
    Date currentDate;
    Date firstLoginDate;
    
    // For FIB answers
    List<JTextField> answerFields= new ArrayList<JTextField>();
    List<JLabel> answerLabel= new ArrayList<JLabel>();
    
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
            st1 = con.createStatement();
            st2 = con.createStatement();
            st3 = con.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        } 
        
    }
    
    public static int safeLongToInt(long l) {
    if (l < Integer.MIN_VALUE || l > Integer.MAX_VALUE) {
        throw new IllegalArgumentException
            (l + " cannot be cast to int without changing its value.");
    }
    return (int) l;
}
    
     public class MyActionListener implements ActionListener {
         int Hours;
         int Minute;
         int Second;
         public void actionPerformed(ActionEvent e) {
            
             if(false){
             //finish
             }else{
                 Second++;
                 if(Second>60){
                     Second=00;
                     Minute++;
                 }
                 
                     
                 if(Minute>60){
                     Minute=00;
                     Hours++;
                 }
                
                 if(Hours>24){
                   Hours=00;
                 }
                  
                     
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(timeLimit);
                
                int hour = calendar.get(Calendar.HOUR);
                int minute = calendar.get(Calendar.MINUTE);
                int seconds = calendar.get(Calendar.SECOND);
                
                int resultHour = hour - Hours;
                int resultMinute = minute - Minute;
                int resultSecond = seconds - Second;
                
                 
                 timeCounter.setText(Hours+":"+Minute+":"+Second);
                timeCounter.revalidate();
                timeCounter.repaint();
             }
             
         }
     }
    
    /**
     * Creates new form ExamMCQ
     */
    public TakingExam(int exam_ID,String sectionName) {
        this.ExamID=exam_ID;
        this.SectionName=sectionName;
        initComponents();
        questionField.setEditable(false); 
      
        
        
        
        connectDb();
        
        try {
            String sql1 = "SELECT * FROM section,exam WHERE section.Exam_ID = exam.Exam_ID AND section.Section_Name='"+SectionName+"' AND exam.Exam_ID ='"+ExamID+"'  ";
            rs = st.executeQuery(sql1);
        
            while(rs.next()) {
                SectionID = rs.getInt("Section_ID");
                //ExamID = rs.getInt("Exam_ID");
                SectionName = rs.getString("Section_Name");
                sectionNameLabel.setText(SectionName);
                //Time timeLimit = rs.getTime("TimeLimit");
                timeLimit = new SimpleDateFormat("HH:mm:ss").parse(rs.getString("TimeLimit"));
               
                //timeCounter.setText();
                //timeLimit.getTime();
                MyActionListener myActionListener=new MyActionListener();
                timecounter = new Timer(1000, myActionListener);
                timecounter.start();
                
               
                
                
            }
            
            // Create new session
            String sql = "INSERT INTO exam_session(exam_id,section_id) VALUES('"+ExamID+"','"+SectionID+"') ";
           
            /////////////// Create new Session & Get the id created ////////////////
             PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
             pstmt.executeUpdate();
             ResultSet keys = pstmt.getGeneratedKeys();
             keys.next();
             sessionkey = keys.getInt(1);  
           
      
            String sql2 = "SELECT * FROM sectionquestion,question WHERE "
                    + "sectionquestion.Section_ID='"+SectionID+"' "
                    + "AND sectionquestion.Question_ID = question.Question_ID ";
            
            rs2 = st2.executeQuery(sql2);
        
            int qID = 0;
            
            
            while(rs2.next()) {
                
                // Get & Set Set question ID
                qID = rs2.getInt("Question_ID");
                this.questionID.add(qID);
                
                
                
                // Get the question type
                int questionType =rs2.getInt("QType_ID");
                
                // Store the question Type
                this.questionType.add(questionType);
                
                if (questionType == 1) {
                  
                    String sql3 = "SELECT * FROM mcq WHERE Question_ID='"+qID+"'";
                    rs3 = st3.executeQuery(sql3);
                    while (rs3.next()) {
                        String question = rs3.getString("Question_Text");
                        this.questionText.add(question);
                    }
                    
                } else if (questionType == 2) {
                    String sql3 = "SELECT * FROM fib WHERE QuestionID='"+qID+"'";
                    rs3 = st3.executeQuery(sql3);
                    while (rs3.next()) {
                        String question = rs3.getString("QuestionBlank");
                        this.questionText.add(question);
                    }
                } else if (questionType == 3) { 
                   //questionField.setVisible(false);
                   //answerLabel1.setVisible(false);
                    String sql3 = "SELECT * FROM essay WHERE Question_ID='"+qID+"'";
                    rs3 = st3.executeQuery(sql3);
                    while (rs3.next()) {
                        String instruction = rs3.getString("instructions");
                        this.questionText.add(instruction);
                    }
                }else {
                
                }
                
                /*
                String question = rs2.getString("Question_Text");
                int qID = rs2.getInt("Question_ID");
                System.out.println(question);
                this.questionText.add(question);
                this.questionID.add(qID);
                */
            }
            
            for (int i : questionType) {
                   System.out.println("q types :"+i);
                }
 
        } catch (Exception exc) {
            exc.printStackTrace();
        } 
        
        CalculateMark(); 
        totalMarkLabel.setText(new Integer(TotalMark).toString());
        
        setQuestion(num);
        setAnswer(num);
        
       // System.out.println("question size: "+questionText.size());
        
    }
    
    
    public void setQuestion(int num){
        
        if (questionType.size() > 0 ) {
        
       if (questionType.get(num) == 1) {
           SubSectionName = "MCQ";
           SubsectionNameLabel.setText(SubSectionName);
       } else if (questionType.get(num) == 2) {
           SubSectionName = "FIB";
           SubsectionNameLabel.setText(SubSectionName);
       } else {
           SubSectionName = "ESSAY";
           SubsectionNameLabel.setText(SubSectionName);
       }
          
        String latestQuestion = questionText.get(num);        
        questionField.setText(num+1 + ". "+ latestQuestion);
      }  
    }
    
     public void setAnswer(int num){
        
         // Hide choices
        choice1.setVisible(false);
        choice2.setVisible(false);
        choice3.setVisible(false);
        choice4.setVisible(false);
        choice5.setVisible(false);
        choice6.setVisible(false); 
        
        // Unchecked choices
        choice1.setSelected(false);
        choice2.setSelected(false);
        choice3.setSelected(false);
        choice4.setSelected(false);
        choice5.setSelected(false);
        choice6.setSelected(false);
        
        correctAnswer.clear();
         
        // Get the question type
        int questionType = this.questionType.get(num);
        
        if (questionType == 1) {
            
            answersPanel.setVisible(true);
            jPanel2.setVisible(false);
        
             try {
                //ArrayList<String> userAnswer = new ArrayList<>();
                 
                latestQID = questionID.get(num);
                
                String sql = "SELECT * FROM mcqoption WHERE Question_ID ='"+latestQID+"' ";
                rs = st.executeQuery(sql);
                
                int counter = 1;
                while(rs.next()) {
                    String answer = rs.getString("Content");
                    int isAnswer = rs.getInt("isAnswerOrNot");

                    if (!answer.equals("")) { 
                        if (counter == 1) {
                            choice1.setText(answer);
                            choice1.setVisible(true);
                            if (isAnswer == 1) {
                                correctAnswer.add("choice1");
                            }
                        } else if (counter == 2) {
                            choice2.setText(answer);
                            choice2.setVisible(true);
                            if (isAnswer == 1) {
                                correctAnswer.add("choice2");
                            }
                        } else if (counter == 3) {
                            choice3.setText(answer);
                            choice3.setVisible(true);
                            if (isAnswer == 1) {
                                correctAnswer.add("choice3");
                            }
                        } else if (counter == 4) {
                            choice4.setText(answer);
                            choice4.setVisible(true);
                            if (isAnswer == 1) {
                                correctAnswer.add("choice4");
                            }
                        } else if (counter == 5) {  
                            choice5.setText(answer);
                            choice5.setVisible(true);
                            if (isAnswer == 1) {
                                correctAnswer.add("choice5");
                            }
                        } else if (counter == 6) {
                            choice6.setText(answer);
                            choice6.setVisible(true);
                            if (isAnswer == 1) {
                                correctAnswer.add("choice6");
                            }
                        }  
                    }

                    counter++;
                }
                
                // Check if the question is already answered and setSelected answer accordingly
                String sql2 = "SELECT * FROM exam_answer WHERE session_id='"+sessionkey+"' AND Question_ID='"+latestQID+"' ";
                rs2 = st2.executeQuery(sql2);
                int answerCounter = 0;
                String answers="";
                while(rs2.next()) {
                    answerCounter++;
                    if (answerCounter > 0) {
                        answers = rs2.getString("answer");
                    }
                }
                
                //List<String> userAnswer = Arrays.asList(answers.split("\\s*,\\s*"));
                List<String> userAnswer = new ArrayList<String>(Arrays.asList(answers.split("\\s*,\\s*")));

                //System.out.print("\n got "+userAnswer.size()+" userAnswers & there are: \n");
                for (String s : userAnswer) {
                    if (s.equals("choice1")) { choice1.setSelected(true); }
                    if (s.equals("choice2")) { choice2.setSelected(true); }
                    if (s.equals("choice3")) { choice3.setSelected(true); }
                    if (s.equals("choice4")) { choice4.setSelected(true); }
                    if (s.equals("choice5")) { choice5.setSelected(true); }
                    if (s.equals("choice6")) { choice6.setSelected(true); }
                    
                    System.out.println(s);
                }
                

            } catch (Exception exc) {
                exc.printStackTrace();
            } 
             
              System.out.print("\n Correct Answer is: ");
                for (String s : correctAnswer) {
                    System.out.print(s + " ");
                }
       
        } else if (questionType == 2) {
 
              // Reset the panel to show new TextField
            jPanel2.removeAll();
            jPanel2.revalidate();
            jPanel2.repaint(); 
            
            jPanel2.setVisible(true);
            answersPanel.setVisible(false);
   
            answersLabel.setText("Fill in the correct answer(s) :");

            jPanel2.setLayout(new FlowLayout());
            
            
            System.out.println("\n");
            
            try {
                latestQID = questionID.get(num);
                String sql = "SELECT * FROM fibanswer WHERE QuestionID ='"+latestQID+"' ";
                rs = st.executeQuery(sql);
                
                int counter = 1;
                while(rs.next()) {
                    String answer = rs.getString("AnswerContentText");
                    
                    System.out.println("Answer "+counter+": ______________ "+answer);
                  
                    answerLabel.add(new JLabel("Answer "+counter+": "));
                    answerFields.add(new JTextField(25));
                    
                    counter++;
                }
                
                int compCounter2 = 0;
                for (JTextField J : answerFields ) {
                    
                    jPanel2.add(answerLabel.get(compCounter2));
                    jPanel2.add(J);
                    
                    compCounter2++;
                }
                
                // Check & Set if the question is already being answered ::
                String sql2 = "SELECT * FROM exam_answer WHERE session_id='"+sessionkey+"' AND Question_ID='"+latestQID+"' ";
                rs2 = st2.executeQuery(sql2);
                int answerCounter = 0;
                String answers="";
                
                while(rs2.next()) {
                    answerCounter++;
                    if (answerCounter > 0) {
                        answers = rs2.getString("answer");
                    }
                }
                
                if (answerCounter > 0 && !answers.equals("")) {
                
                    //List<String> userAnswer = Arrays.asList(answers.split("\\s*,\\s*"));
                    List<String> userAnswer = new ArrayList<String>(Arrays.asList(answers.split("\\s*,\\s*")));
                    //System.out.print("\n got "+userAnswer.size()+" userAnswers & there are: \n");
                    for (String s : userAnswer) {
                        System.out.println(s);
                    }

                    int compCounter3 = 0;
                    for (JTextField J : answerFields ) {

                        J.setText(userAnswer.get(compCounter3));

                        compCounter3++;
                    }

                }

            } catch (Exception exc) {
                exc.printStackTrace();
            } 
            
            
        } else if (questionType == 3) {
        
            // Reset the panel to show new TextField
            jPanel2.removeAll();
            jPanel2.revalidate();
            jPanel2.repaint(); 
            
            jPanel2.setVisible(true);
            answersPanel.setVisible(false);
   
            answersLabel.setText("Fill in your answer part :");

            jPanel2.setLayout(new FlowLayout());
            
            
            essayAnswer.setColumns(40);
            essayAnswer.setRows(10);
            jPanel2.add(essayAnswer);

            System.out.println("\n");
            
            try {
                // Check & Set if the question is already being answered ::
                String sql2 = "SELECT * FROM exam_answer WHERE session_id='"+sessionkey+"' AND Question_ID='"+latestQID+"' ";
                rs2 = st2.executeQuery(sql2);
                int answerCounter = 0;
                
                String answers="";
                
                String essayAnswerText ="";
                
                while(rs2.next()) {
                    essayAnswerText = rs2.getString("answer");
                }
                
                essayAnswer.setText(essayAnswerText);
                
            } catch (Exception exc) {
                exc.printStackTrace();
            } 
            

        } else {
        
        }
    }
     
    int countRows = 0;
     
    public void CalculateMark() {
        try {
            
                String sql = "SELECT * FROM sectionquestion WHERE Section_ID = '"+SectionID+"' ";
                rs = st.executeQuery(sql);
                
                int qid = 0; 
                
                while (rs.next()) {
                   
                    qid = rs.getInt("Question_ID");
                    
                    String sql2 = "SELECT * FROM mcq, mcqoption WHERE mcq.Question_ID = '"+qid+"' AND mcq.Question_ID = mcqoption.Question_ID AND mcqoption.isAnswerOrNot = 1 ";
                    rs2 = st2.executeQuery(sql2);
                    
                    while (rs2.next()) {
                        countRows++;
                    }
                    
                    String sql3 = "SELECT * FROM fib, fibanswer WHERE fib.QuestionID = '"+qid+"' AND fib.QuestionID = fibanswer.QuestionID ";
                    rs3 = st3.executeQuery(sql3);
                    while (rs3.next()) {
                        countRows++;
                    }
                    
                }
              
        }  catch (Exception exc) {
                exc.printStackTrace();
        } 
        
        System.out.println("\n\n OVERALL MARK FOR THIS SECTION IS: "+countRows);
        TotalMark = countRows;
    } 
    
    private void storeAnswers (int num) {
        
        
        ArrayList<String> userAnswers = new ArrayList<>();
        int selectedAnswer = 0;

        
        // Get the question type
        int questionType = this.questionType.get(num);
        
        if (questionType == 1) {
            
            if (choice1.isSelected()) { userAnswers.add("choice1"); selectedAnswer++; }
            if (choice2.isSelected()) { userAnswers.add("choice2"); selectedAnswer++; }
            if (choice3.isSelected()) { userAnswers.add("choice3"); selectedAnswer++; }
            if (choice4.isSelected()) { userAnswers.add("choice4"); selectedAnswer++; }
            if (choice5.isSelected()) { userAnswers.add("choice5"); selectedAnswer++; }
            if (choice6.isSelected()) { userAnswers.add("choice6"); selectedAnswer++; }
            
        } else if (questionType == 2) {
            
            for (JTextField s : answerFields) {
                if (!s.getText().equals("")) {
                    userAnswers.add(s.getText());
                }
            }
            
            answerFields.clear();
            answerLabel.clear();
        } else if (questionType == 3) {
            
            userAnswers.add(essayAnswer.getText());
            
            answerFields.clear();
            answerLabel.clear();
        }else {
                
        }
        
        
        if (userAnswers.size() > 0) {
            
            String comma="";
            StringBuilder Answers = new StringBuilder();
            for (String answer : userAnswers ) {
                Answers.append(comma);
                Answers.append(answer);
                comma = ",";
            }
           String allAnswers = Answers.toString();

           //System.out.println("All answers stored are: "+allAnswers);
            try {
                String query = "INSERT INTO exam_answer(session_id, question_id, question_type,answer)" + "VALUES (?, ?, ?, ?)";
                PreparedStatement preparedStmt = con.prepareStatement(query);
                preparedStmt.setInt(1, sessionkey);
                preparedStmt.setInt (2, latestQID);
                preparedStmt.setInt (3, questionType);
                preparedStmt.setString (4, allAnswers);
                 preparedStmt.executeUpdate();
            } catch (Exception exc) {
                exc.printStackTrace();
            }
            
        }
        userAnswers.clear();
    }
    
    /*
    private void calculateScore() {
         int counter = 0;
        for (String s : correctAnswer) {
            if (s=="choice1") {
                if (choice1.isSelected()) {
                    TotalScore++;
                }
            } else if (s=="choice2") {
                if (choice2.isSelected()) {
                    TotalScore++;
                }
            } else if (s=="choice3") {
                if (choice3.isSelected()) {
                    TotalScore++;
                }
            } else if (s=="choice4") {
                if (choice4.isSelected()) {
                    TotalScore++;
                }
            } else if (s=="choice5") {
                if (choice5.isSelected()) {
                    TotalScore++;
                }
            } else if (s=="choice6") {
                if (choice6.isSelected()) {
                    TotalScore++;
                }
            } else {
            }
            
        }
        
        System.out.println("TOTAL SCORE NOW IS: "+ TotalScore+"/"+TotalMark);
    }
    */
    
     private void TotalScore() {
        int questionID = 0; 
        String userAnswers = "";
        int qType = 0; 
        
        int totalScore = 0;
        
        ArrayList<String> finalUserAnswer = new ArrayList<>();
        ArrayList<String> finalCorrectAnswer = new ArrayList<>();
        
        try {
            String sql = "SELECT * FROM exam_answer WHERE session_id='"+sessionkey+"' ORDER BY exam_answer_id DESC";
            rs = st.executeQuery(sql);
            while(rs.next()) {
                
                //finalUserAnswer.clear();
                //finalCorrectAnswer.clear();
                
                questionID = rs.getInt("question_id");
                qType = rs.getInt("question_type");
                userAnswers = rs.getString("answer");
                
                // Array List of User Answers for this question
                List<String> userAnswer = new ArrayList<String>(Arrays.asList(userAnswers.split("\\s*,\\s*")));

                // Array List of Correct Answers for this question
                ArrayList<String> correctAnswers = new ArrayList<>();

                if (qType == 1) {

                    String sql2 = "SELECT * FROM mcqoption WHERE Question_ID='"+questionID+"'";
                    rs2 = st2.executeQuery(sql2);
                    
                    int positionCounter = 1;
                    while(rs2.next()) {
                        //String actualAnswer = rs2.getString("Content");
                        //correctAnswer.add(actualAnswer);
                        int isAnswerOrNot = rs2.getInt("isAnswerOrNot");
                        
                        if (isAnswerOrNot == 1) {
                            if (positionCounter == 1) { correctAnswers.add("choice1"); } 
                            if (positionCounter == 2) { correctAnswers.add("choice2"); }
                            if (positionCounter == 3) { correctAnswers.add("choice3"); }
                            if (positionCounter == 4) { correctAnswers.add("choice4"); }
                            if (positionCounter == 5) { correctAnswers.add("choice5"); }
                            if (positionCounter == 6) { correctAnswers.add("choice6"); }
                        } else {
                        
                        }
                        positionCounter++;
                    }
                    
                    for (String s : userAnswer) {
                       finalUserAnswer.add(s);
                    }

                    for (String s : correctAnswers) {
                       finalCorrectAnswer.add(s);
                    }    
                    
                    int count = 0;
                    for (String CA : correctAnswers) {
                        String UA = userAnswer.get(count);
                        System.out.println(CA + " is compare to " + UA);
                        if (UA.equals(CA)) {
                            totalScore++;
                        }
                        count++;
                    }
                    
                    userAnswer.clear();
                    correctAnswers.clear();
                
                } else if (qType == 2) { 
                    
                    String sql2 = "SELECT * FROM fibanswer WHERE QuestionID='"+questionID+"'";
                    rs2 = st2.executeQuery(sql2);
                    
                    int positionCounter = 1;
                    while(rs2.next()) {
                        //String actualAnswer = rs2.getString("Content");
                        //correctAnswer.add(actualAnswer);
                        String answerContent = rs2.getString("AnswerContentText");
                        
                        correctAnswers.add(answerContent);
                       
                    }
                    
                    for (String s : userAnswer) {
                       finalUserAnswer.add(s);
                    }

                    for (String s : correctAnswers) {
                       finalCorrectAnswer.add(s);
                    }    
                    
                    int count = 0;
                    for (String CA : correctAnswers) {
                        String UA = userAnswer.get(count);
                        System.out.println(CA + " is compare to " + UA);
                        
                        // convert both to smallcases before compare
                        UA = UA.toLowerCase();
                        CA = CA.toLowerCase();
                        
                        if (UA.equals(CA)) {
                            totalScore++;
                        }
                        count++;
                    }
                    
                    userAnswer.clear();
                    correctAnswers.clear();
                    
                    
                } else {
                
                    
                    
                }
                
            }
            
            
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        
        System.out.println("USER ANSWER ARE:");
        for (String s : finalUserAnswer) {
           System.out.println(s);
        }

        System.out.println("CORRECT ANSWER ARE:");
        for (String s : finalCorrectAnswer) {
           System.out.println(s);
        }   
       
        System.out.println("TOTAL SCORE IS: "+totalScore);
        
        jPanel1.setLayout(new FlowLayout());
        jPanel1.add(new JLabel("Your Total Score for this section is: "));
        jPanel1.add(new JLabel(totalScore+"/"+TotalMark));
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
        answersLabel = new javax.swing.JLabel();
        answerLabel1 = new javax.swing.JLabel();
        answersPanel = new javax.swing.JPanel();
        choice1 = new javax.swing.JCheckBox();
        choice2 = new javax.swing.JCheckBox();
        choice3 = new javax.swing.JCheckBox();
        choice4 = new javax.swing.JCheckBox();
        choice5 = new javax.swing.JCheckBox();
        choice6 = new javax.swing.JCheckBox();
        jPanel2 = new javax.swing.JPanel();
        submitBtn = new javax.swing.JButton();
        prevBtn = new javax.swing.JButton();
        nextBtn = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        sectionNameLabel = new javax.swing.JLabel();
        SubsectionNameLabel = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        totalMarkLabel = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel4 = new javax.swing.JLabel();
        timeCounter = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));

        questionField.setBackground(new java.awt.Color(240, 240, 240));
        questionField.setColumns(20);
        questionField.setRows(5);
        jScrollPane1.setViewportView(questionField);

        answersLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        answersLabel.setText("Choose correct answer(s) :");

        answerLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        answerLabel1.setText("Question :");

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

        javax.swing.GroupLayout answersPanelLayout = new javax.swing.GroupLayout(answersPanel);
        answersPanel.setLayout(answersPanelLayout);
        answersPanelLayout.setHorizontalGroup(
            answersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(answersPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(answersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(choice4)
                    .addComponent(choice5)
                    .addComponent(choice6)
                    .addComponent(choice2)
                    .addComponent(choice1)
                    .addComponent(choice3))
                .addContainerGap(176, Short.MAX_VALUE))
        );
        answersPanelLayout.setVerticalGroup(
            answersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, answersPanelLayout.createSequentialGroup()
                .addContainerGap(13, Short.MAX_VALUE)
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
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(answersLabel)
                            .addComponent(answerLabel1))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 562, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(answersPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(answerLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(answersLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(answersPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(30, 30, 30))
        );

        submitBtn.setText("SUBMIT");
        submitBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                submitBtnActionPerformed(evt);
            }
        });

        prevBtn.setText("PREVIOUS");
        prevBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                prevBtnActionPerformed(evt);
            }
        });

        nextBtn.setText("NEXT");
        nextBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextBtnActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setText("SECTION :");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setText("SUB SECTION :");

        sectionNameLabel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        sectionNameLabel.setText("SectionNameLabel");

        SubsectionNameLabel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        SubsectionNameLabel.setText("SubSectionNameLabel");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setText("SUB SECTION :");

        totalMarkLabel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        totalMarkLabel.setText("SubSectionNameLabel");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel4.setText("Time Left: ");

        timeCounter.setText("TimeCounter");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(prevBtn)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(nextBtn)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(submitBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(totalMarkLabel)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jSeparator1))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(SubsectionNameLabel))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(sectionNameLabel)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(timeCounter)
                        .addGap(32, 32, 32))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(sectionNameLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(SubsectionNameLabel)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(timeCounter))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(totalMarkLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(submitBtn)
                    .addComponent(prevBtn)
                    .addComponent(nextBtn))
                .addGap(36, 36, 36))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void nextBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextBtnActionPerformed
        
        storeAnswers (num);
        
        
        if (num!= questionText.size()-1) {
            num+=1;
            setQuestion(num);
            setAnswer(num);
        }
       
    }//GEN-LAST:event_nextBtnActionPerformed

    private void prevBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_prevBtnActionPerformed

        storeAnswers (num);
        
        if (num != 0) {
            num-=1;
            setQuestion(num);
            setAnswer(num);
        }
    }//GEN-LAST:event_prevBtnActionPerformed

    private void choice1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_choice1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_choice1ActionPerformed

    private void submitBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_submitBtnActionPerformed
        
        // Reset the panel to show new TextField
         jPanel1.removeAll();
         jPanel1.revalidate();
         jPanel1.repaint(); 
         
         TotalScore();
         
    }//GEN-LAST:event_submitBtnActionPerformed

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
            java.util.logging.Logger.getLogger(TakingExam.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TakingExam.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TakingExam.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TakingExam.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //new TakingExam().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel SubsectionNameLabel;
    private javax.swing.JLabel answerLabel1;
    private javax.swing.JLabel answersLabel;
    private javax.swing.JPanel answersPanel;
    private javax.swing.JCheckBox choice1;
    private javax.swing.JCheckBox choice2;
    private javax.swing.JCheckBox choice3;
    private javax.swing.JCheckBox choice4;
    private javax.swing.JCheckBox choice5;
    private javax.swing.JCheckBox choice6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JButton nextBtn;
    private javax.swing.JButton prevBtn;
    private javax.swing.JTextArea questionField;
    private javax.swing.JLabel sectionNameLabel;
    private javax.swing.JButton submitBtn;
    private javax.swing.JLabel timeCounter;
    private javax.swing.JLabel totalMarkLabel;
    // End of variables declaration//GEN-END:variables
}
