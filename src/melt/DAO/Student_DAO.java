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
public class Student_DAO {

    public boolean Exist(String Stu_ID, String PassCode) {
        try {
            StringBuffer sql = new StringBuffer();
            sql.append(" select * from student where");
            sql.append(" stu_ID='");
            sql.append(Stu_ID);
            sql.append("' && stu_Passcode='");
            sql.append(PassCode);
            sql.append("';");

            SQLHelper sqlHelper = new SQLHelper();
            sqlHelper.sqlConnect();
            ResultSet rs = sqlHelper.runQuery(sql.toString());
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Student_DAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return false;

    }

}
