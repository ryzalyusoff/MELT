/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package melt.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import melt.Model.MCQOption;
import melt.Util.SQLHelper;

/**
 *
 * @author Aote Zhou
 */
public class MCQOption_DAO {

    /**
     *get mcqOPtions for a question
     * @param Question_ID
     * @return
     */
    public MCQOption[] getModel(int Question_ID) {
        ArrayList<MCQOption> mCQOptions=new ArrayList<MCQOption>();
        try {
           
            StringBuffer sql = new StringBuffer("");
            sql.append("SELECT MCQOption_ID,Question_ID,Content,isAnswerOrNot");
            sql.append(" FROM MELTSystem.`MCQOption`");
            sql.append(" where Question_ID=" + Question_ID);

            SQLHelper sQLHelper = new SQLHelper();
            sQLHelper.sqlConnect();
            ResultSet rs = sQLHelper.runQuery(sql.toString());
            while (rs.next()) {
                MCQOption mCQOption=new MCQOption();
                
                mCQOption.setContent(rs.getString("Content"));
                mCQOption.setIsAnswerOrNot(rs.getBoolean("isAnswerOrNot"));
                mCQOption.setMCQOption_ID(rs.getInt("MCQOption_ID"));
                mCQOption.setQuestion_ID(rs.getInt("Question_ID"));
                
                mCQOptions.add(mCQOption);
               
            }
        } catch (SQLException ex) {
            Logger.getLogger(Question_DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return mCQOptions.toArray(new MCQOption[mCQOptions.size()]);
    }
    
    /**
     *get a set of result for MCQOptions
     * @param whereString
     * @return
     */
    public ResultSet getList(String whereString){
        
        StringBuffer sql=new StringBuffer("");
        sql.append("SELECT MCQOption_ID,Question_ID,Content,isAnswerOrNot");
        sql.append(" FROM `MELTSystem`.`MCQOption`");
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
