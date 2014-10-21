/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package melt.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import melt.Model.FIB;
import melt.Util.SQLHelper;

/**
 *
 * @author Aote Zhou
 */
public class FIB_DAO {

    /**
     * get mcq by question_ID
     *
     * @param Question_ID
     * @return
     */
    public FIB getModel(int Question_ID) {
        FIB fibQuestion = new FIB();
        try {

            StringBuffer sql = new StringBuffer("");
            sql.append("SELECT QuestionID,QuestionType,QuestionText, QuestionInstructions");
            sql.append(" FROM MELTSystem.`FIB`");
            sql.append(" where QuestionID=" + Question_ID);

            SQLHelper sQLHelper = new SQLHelper();
            sQLHelper.sqlConnect();
            ResultSet rs = sQLHelper.runQuery(sql.toString());
            while (rs.next()) {

                fibQuestion.setQtype_ID(rs.getInt("QuestionType"));
                fibQuestion.setQuestion_ID(rs.getInt("QuestionID"));
                fibQuestion.setQuestionText(rs.getString("QuestionText"));
                fibQuestion.setQuestionInstructions(rs.getString("QuestionInstructions"));
               

            }
        } catch (SQLException ex) {
            Logger.getLogger(FIB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return fibQuestion;
    }

    /**
     * update the subsec_ID of a specific question
     *
     * @param subsec_ID
     * @param Q_ID
     */
//    public void update(int subsec_ID, int Q_ID) {
//        if (new SubsectionQuestion_DAO().hasRelWithSubsection(Q_ID)) {
//            StringBuffer sql = new StringBuffer();
//            sql.append("UPDATE MCQ ");
//            sql.append("SET SubSection_ID='");
//            sql.append(subsec_ID + "'");
//            sql.append(" where Question_ID='");
//            sql.append(Q_ID + "'");
//
//            SQLHelper sQLHelper = new SQLHelper();
//            sQLHelper.sqlConnect();
//
//            sQLHelper.runUpdate(sql.toString());
//        }else{
//        StringBuffer sql=new StringBuffer();
//            sql.append("insert into MCQ select Question_ID,'"+subsec_ID+"',QType_ID,Question_Text from MCQ where Question_ID="+Q_ID);
//            
//            SQLHelper sQLHelper=new SQLHelper();
//            sQLHelper.sqlConnect();
//            sQLHelper.runUpdate(sql.toString());
//            sQLHelper.sqlClose();
//        }
//
//    }
    /**
     * get a set of result
     *
     * @param whereString
     * @return
     */
    public ResultSet getList(String whereString) {

        StringBuffer sql = new StringBuffer("");
        sql.append("SELECT QuestionID,QuestionType,QuestionText, QuestionInstructions");
        sql.append(" FROM MELTSystem.`FIB`");
        if (whereString.trim() != "") {
            sql.append(" where " + whereString);
        }
        SQLHelper sQLHelper = new SQLHelper();
        sQLHelper.sqlConnect();
        ResultSet rs = sQLHelper.runQuery(sql.toString());
        //sQLHelper.sqlClose();
        return rs;
    }

  

    

    public static void main(String[] args) {
        
    }
}
