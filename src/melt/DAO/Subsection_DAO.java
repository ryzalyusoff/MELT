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
 * @author Aote Zhou
 */
public class Subsection_DAO {
    
    public ResultSet getList(String whereString) {

        StringBuffer sql = new StringBuffer("");
        sql.append("SELECT SubSection_ID,Section_ID,SubSection_Name");
        sql.append(" FROM MELTSystem.`SubSection`");
        if (whereString.trim() != "") {
            sql.append(" where " + whereString);
        }
        SQLHelper sQLHelper = new SQLHelper();
        sQLHelper.sqlConnect();
        ResultSet rs = sQLHelper.runQuery(sql.toString());
        //sQLHelper.sqlClose();
        return rs;
    }
    
    public int add(melt.Model.SubSection subSection){
        int generated_Subsec_ID=-1;
        
        StringBuffer sql=new StringBuffer();
        sql.append("INSERT INTO SubSection");
        sql.append("(`SubSection_ID`,");
        sql.append("`Section_ID`,");
        sql.append("`SubSection_Name`)");
        sql.append("VALUES");
        sql.append("('"+subSection.getSection_ID()+"',");
        sql.append("'"+subSection.getSection_ID()+"',");
        sql.append("'"+subSection.getSubSection_Name()+"');");
        
        SQLHelper sQLHelper=new SQLHelper();
        sQLHelper.sqlConnect();
        ResultSet rs=sQLHelper.runUpdate2(sql.toString());
        try {
            if (rs.next()) {
                generated_Subsec_ID=rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Subsection_DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return generated_Subsec_ID;
    }
    public void delete(String whereString){
        StringBuffer sql = new StringBuffer("");
        sql.append("Delete from MELTSystem.`SubSection`");
        if (whereString.trim() != "") {
            sql.append(" where " + whereString);
        }
        SQLHelper sQLHelper = new SQLHelper();
        sQLHelper.sqlConnect();
        sQLHelper.runUpdate(sql.toString());
        sQLHelper.sqlClose();
        
    }
}
