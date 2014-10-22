/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sols.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import sols.Model.FIBAnswer;
import sols.Util.SQLHelper;

/**
 *
 * @author Aote Zhou
 */
public class FIBAnswer_DAO {
    public FIBAnswer[] getModel(int Question_ID) {
     /**
     *get mcqOPtions for a question
     * @param Question_ID
     * @return
     */
        ArrayList<FIBAnswer> fibAnswers=new ArrayList<FIBAnswer>();
        try {
           
            StringBuffer sql = new StringBuffer("");
            sql.append("SELECT AnswerID,QuestionID,AnswerContentText");
            sql.append(" FROM `FIBAnswer`");
            sql.append(" where Trim(Content)!='' and QuestionID=" + Question_ID);

            SQLHelper sQLHelper = new SQLHelper();
            sQLHelper.sqlConnect();
            ResultSet rs = sQLHelper.runQuery(sql.toString());
            while (rs.next()) {
                FIBAnswer fibAnswer=new FIBAnswer();
                
                fibAnswer.setFIBAnswer_ID(rs.getInt("AnswerID"));
                fibAnswer.setQuestion_ID(rs.getInt("QuestionID"));
                fibAnswer.setAnswer(rs.getString("AnswerContentText"));
                
                fibAnswers.add(fibAnswer);
               
            }
        } catch (SQLException ex) {
            Logger.getLogger(FIB_DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return fibAnswers.toArray(new FIBAnswer[fibAnswers.size()]);
    }
    /**
     *get a set of result for MCQOptions
     * @param whereString
     * @return
     */
    public ResultSet getList(String whereString){
        
        StringBuffer sql=new StringBuffer("");
        sql.append("SELECT AnswerID,QuestionID,AnswerContentText");
        sql.append(" FROM `FIBAnswer`");
        if (whereString.trim()!="") {
            sql.append(" where "+whereString);
        }
        SQLHelper sQLHelper=new SQLHelper();
        sQLHelper.sqlConnect();
        ResultSet rs=sQLHelper.runQuery(sql.toString());
        //sQLHelper.sqlClose();
        return rs;
    }
    
}
