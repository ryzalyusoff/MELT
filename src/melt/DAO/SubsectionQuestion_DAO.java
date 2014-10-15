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
import melt.Util.SQLHelper;

/**
 *
 * @author eddychou
 */
public class SubsectionQuestion_DAO {
      /**
     * see if the question has Relationship with Any Subsection
     *
     * @param question_ID
     * @return
     */
    public boolean hasRelWithSubsection(int question_ID) {
        boolean hasRel=false;
        try {

            StringBuffer sql = new StringBuffer();
            sql.append("select Subsection_ID ");
            sql.append("from subsectionquestion_ID ");
            sql.append("where Question_ID=" + question_ID);

            SQLHelper sQLHelper = new SQLHelper();
            sQLHelper.sqlConnect();
            ResultSet rs = sQLHelper.runQuery(sql.toString());
            if (rs.next()) {
                hasRel=true;
            }

        } catch (SQLException ex) {
            Logger.getLogger(Question_DAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return hasRel;
    }
    /**
     * make the section_ID of specific subsection null
     *
     * @param section_ID
     */
    public void cancelRWithSubSec(int section_ID) {
        StringBuffer sql = new StringBuffer();
        sql.append("delete from SubsectionQuestion ");
        sql.append(" where SubSection_ID in");
        sql.append(" (select SubSection_ID from SubSection where Section_ID='" + section_ID + "')");

        SQLHelper sQLHelper = new SQLHelper();
        sQLHelper.sqlConnect();

        sQLHelper.runUpdate(sql.toString());
    }
    /**
     * get a set of result
     *
     * @param whereString
     * @return
     */
    public ResultSet getList(String whereString) {

        StringBuffer sql = new StringBuffer("");
        sql.append("SELECT Question_ID,SubSection_ID");
        sql.append(" FROM SubSectionQuestion");
        if (whereString.trim() != "") {
            sql.append(" where " + whereString);
        }
        SQLHelper sQLHelper = new SQLHelper();
        sQLHelper.sqlConnect();
        ResultSet rs = sQLHelper.runQuery(sql.toString());
        //sQLHelper.sqlClose();
        return rs;
    }
    public int add(int subsec_ID, int Q_ID){
        int generated_Subsec_ID=-1;
        
        StringBuffer sql=new StringBuffer();
        sql.append("INSERT INTO SubSectionQuestion");
        sql.append("(`SubSection_ID`,");
        sql.append("`Question_ID`)");
        sql.append("VALUES");
        sql.append("('"+subsec_ID+"',");
        sql.append("'"+Q_ID+"');");
        
        SQLHelper sQLHelper=new SQLHelper();
        sQLHelper.sqlConnect();
        sQLHelper.runUpdate(sql.toString());
       
        return generated_Subsec_ID;
    }
}
