package melt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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
            
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/melt","root","");
            st = con.createStatement();
            
        } catch(Exception ex) {
            System.out.println("Erro: "+ex);
        }
    }
    
    // Get all Question
    public List<Question> getAllQuestion() throws Exception {
    
        List<Question> list = new ArrayList<>();
        
        Statement myStmt = null;
        ResultSet myRs = null;
       
        Statement myQtmt = null;
        ResultSet myQs = null;
        
        try {
            myStmt = con.createStatement();
            myRs = st.executeQuery("SELECT * FROM questions");
            
        
            int count = 1;
            while (myRs.next()) {
                Question tempQuestion = convertRowToQuestion(myRs, count);
                list.add(tempQuestion);
                
                count++;
            }
            return list;
        
        } finally {
            close(myStmt, myRs);
        }    
    }
    
    private Question convertRowToQuestion(ResultSet myRs, int counter) throws SQLException {

            int id = myRs.getInt("id");
            //int id = counter;
            //String id= myRs.getString("id");
            String question = myRs.getString("question");
            String answer = myRs.getString("answer");

            Question tempQuestion = new Question(id, question, answer);

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
