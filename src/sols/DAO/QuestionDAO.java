package sols.DAO;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import sols.Util.SQLHelper;
import sols.View.SettingQuestion;

public class QuestionDAO {
 
    public static String url;
    public static String user;
    public static String password;
    public static String driver;
    
    private Connection con;
    private Statement st;
    private ResultSet rs;
    
    // Make connection to database
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
    
    public QuestionDAO(){
         startSQL();
         connectDb();
    }
    
    
    // Get all Question
    public List<SettingQuestion> getAllQuestion() throws Exception {
    
        List<SettingQuestion> list = new ArrayList<>();
        
        Statement myStmt = null;
        ResultSet myRs = null;
       
        Statement myQtmt = null;
        ResultSet myQs = null;
        
        try {
            myStmt = con.createStatement();
            myRs = st.executeQuery("SELECT * FROM mcq");
            
        
            int counter = 1;
            int theid = 0;
            while (myRs.next()) {
                SettingQuestion tempQuestion = convertRowToQuestion(counter, theid, myRs);
                list.add(tempQuestion);
                
                counter++;
            }
            return list;
        
        } finally {
            close(myStmt, myRs);
        }    
    }
    
    // Get all Question
    public List<SettingQuestion> getAllFIBQuestion() throws Exception {
    
        List<SettingQuestion> list = new ArrayList<>();
        
        Statement myStmt = null;
        ResultSet myRs = null;
       
        Statement myQtmt = null;
        ResultSet myQs = null;
        
        try {
            myStmt = con.createStatement();
            myRs = st.executeQuery("SELECT * FROM fib");
            
        
            int counter = 1;
            int theid = 0;
            while (myRs.next()) {
                SettingQuestion tempQuestion = convertRowToFIBQuestion(counter, theid, myRs);
                list.add(tempQuestion);
                
                counter++;
            }
            return list;
        
        } finally {
            close(myStmt, myRs);
        }    
    }
    
    private SettingQuestion convertRowToQuestion(int counter, int theid, ResultSet myRs ) throws SQLException {

            int id = myRs.getInt("Question_ID");
            int count = counter;
            
            String question = myRs.getString("Question_Text");
            //String answer = myRs.getString("answer");

            SettingQuestion tempQuestion = new SettingQuestion(count, id, question );

            return tempQuestion;
    }
    
     private SettingQuestion convertRowToFIBQuestion(int counter, int theid, ResultSet myRs ) throws SQLException {

            int id = myRs.getInt("QuestionID");
            int count = counter;
            
            String question = myRs.getString("QuestionBlank");
            //String answer = myRs.getString("answer");

            SettingQuestion tempQuestion = new SettingQuestion(count, id, question );

            return tempQuestion;
    }
    
    /* Closing db connecttion starts */
    private void close(Statement myStmt, ResultSet myRs) throws SQLException {
		close(null, myStmt, myRs);		
    }
    
    private static void close(Connection myConn, Statement myStmt, ResultSet myRs)
			throws SQLException {

		if (myRs != null) {
			myRs.close();
		}

		if (myStmt != null) {
			
		}
		
		if (myConn != null) {
			myConn.close();
		}
    }
    /* Closing db connecttion starts */
    
    
    
}
