package melt.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import melt.getQuestion;

public class QuestionDAO {
 
    private Connection con;
    private Statement st;
    private ResultSet rs;
    
    public QuestionDAO(){
         connectDb();
    }
    
    // Make connection to database
    public void connectDb() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/meltsystem","root","root");
            st = con.createStatement();
            
        } catch(Exception ex) {
            System.out.println("Erro: "+ex);
        }
    }
    
    // Get all Question
    public List<getQuestion> getAllQuestion() throws Exception {
    
        List<getQuestion> list = new ArrayList<>();
        
        Statement myStmt = null;
        ResultSet myRs = null;
       
        Statement myQtmt = null;
        ResultSet myQs = null;
        
        try {
            myStmt = con.createStatement();
            myRs = st.executeQuery("SELECT * FROM mcq");
            
        
            int count = 1;
            while (myRs.next()) {
                getQuestion tempQuestion = convertRowToQuestion(myRs, count);
                list.add(tempQuestion);
                
                count++;
            }
            return list;
        
        } finally {
            close(myStmt, myRs);
        }    
    }
    
    private getQuestion convertRowToQuestion(ResultSet myRs, int counter) throws SQLException {

            int id = myRs.getInt("Question_ID");
            //int id = counter;
            
            String question = myRs.getString("Question_Text");
            //String answer = myRs.getString("answer");

            getQuestion tempQuestion = new getQuestion(id, question);

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
