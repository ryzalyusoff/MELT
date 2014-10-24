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
import sols.Util.SQLHelper;

/**
 *
 * @author eddychou
 */
public class Teacher_DAO {

    public boolean Exist(String Tea_ID, String PassCode) {
        try {
            StringBuffer sql = new StringBuffer();
            sql.append(" select * from teacher where");
            sql.append(" tea_ID='");
            sql.append(Tea_ID);
            sql.append("' && teacher_Passcode='");
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

    public static void main(String[] args) {
        Teacher_DAO teacher_DAO = new Teacher_DAO();
        if (teacher_DAO.Exist("10060120", "jjhsjl")) {
            System.out.println("hahahhah");
        };
    }
}
