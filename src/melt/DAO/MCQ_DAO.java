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
import melt.Model.MCQ;
import melt.Util.SQLHelper;

/**
 *
 * @author Aote Zhou
 */
public class MCQ_DAO {
    public MCQ getModel(int Question_ID) {
        MCQ mcquestion = new MCQ();
        try {
           
            StringBuffer sql = new StringBuffer("");
            sql.append("SELECT Question_ID,SubSection_ID,QType_ID,Question_Text");
            sql.append(" FROM MELTSystem.`MCQ`");
            sql.append(" where Question_ID=" + Question_ID);

            SQLHelper sQLHelper = new SQLHelper();
            sQLHelper.sqlConnect();
            ResultSet rs = sQLHelper.runQuery(sql.toString());
            while (rs.next()) {

               mcquestion.setQtype_ID(rs.getInt("QType_ID"));
               mcquestion.setQuestion_ID(rs.getInt("Question_ID"));
               mcquestion.setQuestion_Text(rs.getString("Question_Text"));
               mcquestion.setSubSection_ID(rs.getInt("SubSection_ID"));

            }
        } catch (SQLException ex) {
            Logger.getLogger(MCQ.class.getName()).log(Level.SEVERE, null, ex);
        }
        return mcquestion;
    }
    public void update(int subsec_ID,int Q_ID){
        StringBuffer sql=new StringBuffer();
        sql.append("UPDATE MCQ ");
        sql.append("SET SubSection_ID='");
        sql.append(subsec_ID+"'");
        sql.append(" where Question_ID='");
        sql.append(Q_ID+"'");
        
        SQLHelper sQLHelper = new SQLHelper();
        sQLHelper.sqlConnect();
        
        sQLHelper.runUpdate(sql.toString());
                
    }
    public void cancelRWithSubSec(int section_ID){
        StringBuffer sql=new StringBuffer();
        sql.append("UPDATE MCQ ");
        sql.append("SET SubSection_ID=null");
        sql.append(" where SubSection_ID in");
        sql.append(" (select SubSection_ID from SubSection where Section_ID='"+section_ID+"')");
        
        SQLHelper sQLHelper = new SQLHelper();
        sQLHelper.sqlConnect();
        
        sQLHelper.runUpdate(sql.toString());
    }
    public static void main(String[] args) {
        Question_DAO question_DAO=new Question_DAO();
        question_DAO.update(5, 1);
    }
}
