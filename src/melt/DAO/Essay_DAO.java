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
import melt.Model.Essay;
import melt.Util.SQLHelper;

/**
 *
 * @author Aote Zhou
 */
public class Essay_DAO {

    /**
     * get mcq by question_ID
     *
     * @param Question_ID
     * @return
     */
    public Essay getModel(int Question_ID) {
        Essay essayQuestion = new Essay();
        try {

            StringBuffer sql = new StringBuffer("");
            sql.append("SELECT QuestionID,QuestionType,noofwords, instructions");
            sql.append(" FROM `Essay`");
            sql.append(" where QuestionID=" + Question_ID);

            SQLHelper sQLHelper = new SQLHelper();
            sQLHelper.sqlConnect();
            ResultSet rs = sQLHelper.runQuery(sql.toString());
            while (rs.next()) {

                essayQuestion.setQtype_ID(rs.getInt("QuestionType"));
                essayQuestion.setQuestion_ID(rs.getInt("QuestionID"));
                essayQuestion.setNoofwords(rs.getString("noofwords"));
                essayQuestion.setInstructions(rs.getString("instructions"));
               

            }
        } catch (SQLException ex) {
            Logger.getLogger(Essay.class.getName()).log(Level.SEVERE, null, ex);
        }
        return essayQuestion;
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
        sql.append("SELECT Question_ID,QType_ID,noofwords,instructions");
        sql.append(" FROM `Essay`");
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
