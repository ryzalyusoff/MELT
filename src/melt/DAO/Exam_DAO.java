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
public class Exam_DAO {
    public ResultSet getList(String whereString) {

        StringBuffer sql = new StringBuffer("");
        sql.append("SELECT Exam_ID,Instructions,isPublic");
        sql.append(" FROM `Exam`");
        if (whereString.trim() != "") {
            sql.append(" where " + whereString);
        }
        SQLHelper sQLHelper = new SQLHelper();
        sQLHelper.sqlConnect();
        ResultSet rs = sQLHelper.runQuery(sql.toString());
        //sQLHelper.sqlClose();
        return rs;
    }
    public int add(melt.Model.Exam exam){
        int generated_Exam_ID=-1;
        
        StringBuffer sql=new StringBuffer();
        sql.append("INSERT INTO Exam");
        sql.append("(`Exam_ID`,");
        sql.append("`Instructions`,");
        sql.append("`isPublic`)");
        sql.append("VALUES");
        sql.append("(null,");
        sql.append("'"+exam.getInstructions()+"',");
        if (exam.getIsPublic()) {
            sql.append("1);");
        }else{
            sql.append("0);");
        }
        
        
        SQLHelper sQLHelper=new SQLHelper();
        sQLHelper.sqlConnect();
        ResultSet rs=sQLHelper.runUpdate2(sql.toString());
        try {
            if (rs.next()) {
                generated_Exam_ID=rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Subsection_DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return generated_Exam_ID;
    }
    public void makeItPublic(int exam_ID){
        StringBuffer sql1=new StringBuffer();
        sql1.append("UPDATE Exam ");
        sql1.append("SET isPublic=1");
        sql1.append(" where Exam_ID='");
        sql1.append(exam_ID+"';");
        StringBuffer sql2=new StringBuffer();
        sql2.append("UPDATE Exam ");
        sql2.append("SET isPublic=0");
        sql2.append(" where isPublic=1;");

        
        SQLHelper sQLHelper = new SQLHelper();
        sQLHelper.sqlConnect();
        
        sQLHelper.runUpdate(sql2.toString());
        sQLHelper.runUpdate(sql1.toString());
        
    }
}
