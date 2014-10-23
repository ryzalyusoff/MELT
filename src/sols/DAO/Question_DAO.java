/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sols.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import sols.Model.Question;
import sols.Util.SQLHelper;

/**
 *
 * @author Aote Zhou
 */
public class Question_DAO {

    /**
     * get question by Question_ID
     *
     * @param Question_ID
     * @return
     */
    public Question getModel(int Question_ID) {
        Question question = new Question();
        try {

            StringBuffer sql = new StringBuffer("");
            sql.append("SELECT Question_ID,QType_ID");
            sql.append(" FROM MELTSystem.`Question`");
            sql.append(" where Question_ID=" + Question_ID);

            SQLHelper sQLHelper = new SQLHelper();
            sQLHelper.sqlConnect();
            ResultSet rs = sQLHelper.runQuery(sql.toString());
            while (rs.next()) {

                question.setQtype_ID(rs.getInt("QType_ID"));
                question.setQuestion_ID(rs.getInt("Question_ID"));
                

            }
        } catch (SQLException ex) {
            Logger.getLogger(Question_DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return question;
    }

    /**
     * get a set of result
     *
     * @param whereString
     * @return
     */
    public ResultSet getList(String whereString) {

        StringBuffer sql = new StringBuffer("");
        sql.append("SELECT Question_ID,QType_ID");
        sql.append(" FROM MELTSystem.`Question`");
        if (whereString.trim() != "") {
            sql.append(" where " + whereString);
        }
        SQLHelper sQLHelper = new SQLHelper();
        sQLHelper.sqlConnect();
        ResultSet rs = sQLHelper.runQuery(sql.toString());
        //sQLHelper.sqlClose();
        return rs;
    }

    

//    /**
//     * update subsec_ID
//     *
//     * @param subsec_ID
//     */
//    public void update(int subsec_ID, int Q_ID) {
//        if (isNoRelWithSubsection(Q_ID)) {
//            StringBuffer sql = new StringBuffer();
//            sql.append("UPDATE Question ");
//            sql.append("SET SubSection_ID='");
//            sql.append(subsec_ID + "'");
//            sql.append(" where Question_ID='");
//            sql.append(Q_ID + "'");
//
//            SQLHelper sQLHelper = new SQLHelper();
//            sQLHelper.sqlConnect();
//
//            sQLHelper.runUpdate(sql.toString());
//            sQLHelper.sqlClose();
//        }else{
//            StringBuffer sql=new StringBuffer();
//            sql.append("insert into Question select Question_ID,'"+subsec_ID+"',QType_ID,Question_Text from Question where Question_ID="+Q_ID);
//            
//            SQLHelper sQLHelper=new SQLHelper();
//            sQLHelper.sqlConnect();
//            sQLHelper.runUpdate(sql.toString());
//            sQLHelper.sqlClose();
//        }
//
//    }

    

    public static void main(String[] args) {
       
    }
}
