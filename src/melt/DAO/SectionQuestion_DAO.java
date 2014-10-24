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
import melt.Util.SQLHelper;

/**
 *
 * @author eddychou
 */
public class SectionQuestion_DAO {

    /**
     * see if the question has Relationship with Any Subsection
     *
     * @param question_ID
     * @return
     */
    public boolean hasRelWithSection(int question_ID) {
        boolean hasRel = false;
        try {

            StringBuffer sql = new StringBuffer();
            sql.append("select Section_ID ");
            sql.append("from sectionquestion_ID ");
            sql.append("where Question_ID=" + question_ID);

            SQLHelper sQLHelper = new SQLHelper();
            sQLHelper.sqlConnect();
            ResultSet rs = sQLHelper.runQuery(sql.toString());
            if (rs.next()) {
                hasRel = true;
            }

        } catch (SQLException ex) {
            Logger.getLogger(Question_DAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return hasRel;
    }

    /**
     * make the section_ID of specific section null
     *
     * @param section_ID
     */
    public void cancelRWithSec(int section_ID) {
        StringBuffer sql = new StringBuffer();
        sql.append("delete from SectionQuestion ");
        sql.append(" where Section_ID='" + section_ID + "'");

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
    public ArrayList<int[]> getList(String whereString) {
        ArrayList<int[]> results = new ArrayList<int[]>();

        StringBuffer sql = new StringBuffer("");
        sql.append("SELECT SQ.Question_ID,SQ.Section_ID, Q.QType_ID");
        sql.append(" FROM SectionQuestion AS SQ");
        sql.append(" LEFT JOIN Question AS Q ON SQ.Question_ID = Q.Question_ID ");
        if (whereString.trim() != "") {
            sql.append(" where SQ." + whereString);
        }
        SQLHelper sQLHelper = new SQLHelper();
        sQLHelper.sqlConnect();
        ResultSet rs = sQLHelper.runQuery(sql.toString());
        //sQLHelper.sqlClose();
        try {
            while (rs.next()) {
                int[] ints = new int[3];

                ints[0] = rs.getInt("SQ.Question_ID");
                ints[1] = rs.getInt("SQ.Section_ID");
                ints[2] = rs.getInt("Q.QType_ID");

                results.add(ints);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SectionQuestion_DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        sQLHelper.sqlClose();
        return results;
    }
    
    public ArrayList<Integer> getQTypes(String whereString) {
        ArrayList<Integer> results = new ArrayList<Integer>();

        StringBuffer sql = new StringBuffer("");
        sql.append("SELECT distinct Q.QType_ID");
        sql.append(" FROM SectionQuestion AS SQ");
        sql.append(" LEFT JOIN Question AS Q ON SQ.Question_ID = Q.Question_ID ");
        if (whereString.trim() != "") {
            sql.append(" where SQ." + whereString);
        }
        SQLHelper sQLHelper = new SQLHelper();
        sQLHelper.sqlConnect();
        ResultSet rs = sQLHelper.runQuery(sql.toString());
        //sQLHelper.sqlClose();
        try {
           while (rs.next()) {
                

                results.add(rs.getInt(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(SectionQuestion_DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        sQLHelper.sqlClose();
        return results;
    }
    public void add(int sec_ID, int Q_ID) {
        

        StringBuffer sql = new StringBuffer();
        sql.append("INSERT INTO SectionQuestion");
        sql.append("(`Section_ID`,");
        sql.append("`Question_ID`)");
        sql.append("VALUES");
        sql.append("('" + sec_ID + "',");
        sql.append("'" + Q_ID + "');");

        SQLHelper sQLHelper = new SQLHelper();
        sQLHelper.sqlConnect();
        sQLHelper.runUpdate(sql.toString());

    }
}
