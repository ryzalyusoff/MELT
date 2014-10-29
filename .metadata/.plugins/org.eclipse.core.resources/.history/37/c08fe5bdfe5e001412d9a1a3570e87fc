/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package melt.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import melt.Model.Section;
import melt.Util.SQLHelper;

/**
 *
 * @author eddychou
 */
public class Section_DAO {

    /**
     * add section into database
     * @param section
     * @return
     */
    public int add(melt.Model.Section section) {
        int generated_Sec_ID=-1;
        StringBuffer sql = new StringBuffer();
        sql.append("INSERT INTO Section");
        sql.append("(`Section_ID`,");
        sql.append("`Exam_ID`,");
        sql.append("`Section_Name`,");
        sql.append("`TimeLimit`)");
        sql.append("VALUES");
        sql.append("('" + section.getSection_ID() + "',");
        sql.append("'" +section.getExam_ID() + "',");
        sql.append("'" +section.getSection_Name() + "',");
        sql.append("'"+new SimpleDateFormat("HH:mm:ss").format(section.getTimeLimit()) + "');");

        try {
            SQLHelper sQLHelper = new SQLHelper();
            sQLHelper.sqlConnect();
            ResultSet rs = sQLHelper.runUpdate2(sql.toString());

            if (rs.next()) {
                generated_Sec_ID=rs.getInt(1);
            } 
        } catch (SQLException ex) {
            Logger.getLogger(Section_DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return generated_Sec_ID;
    }

    /**
     *
     * @param whereString
     * @return
     */
    public ArrayList<Section> getList(String whereString) {

        StringBuffer sql = new StringBuffer("");
        sql.append("SELECT Section_ID,Exam_ID,Section_Name,TimeLimit");
        sql.append(" FROM `Section`");
        if (whereString.trim() != "") {
            sql.append(" where " + whereString);
        }
        SQLHelper sQLHelper = new SQLHelper();
        sQLHelper.sqlConnect();
        ResultSet rs = sQLHelper.runQuery(sql.toString());
        ArrayList<Section> sections = new ArrayList<Section>();
        try {
            while (rs.next()) {
                Section section = new Section();
                section.setExam_ID(rs.getInt("Exam_ID"));
                section.setSection_ID(rs.getInt("Section_ID"));
                section.setSection_Name(rs.getString("Section_Name"));
                section.setTimeLimit(new SimpleDateFormat("HH:mm:ss").parse(rs.getString("TimeLimit")));
                sections.add(section);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Section_DAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(Section_DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        sQLHelper.sqlClose();
        return sections;
    }

    /**
     *
     * @param section_ID
     * @return
     */
    public Section getModel(int section_ID) {
        Section section=new Section();
        try {
            
            StringBuffer sql = new StringBuffer("");
            sql.append("SELECT Section_ID,Exam_ID,Section_Name,TimeLimit");
            sql.append(" FROM `Section`");
            sql.append(" where Section_ID="+section_ID);
            SQLHelper sQLHelper = new SQLHelper();
            sQLHelper.sqlConnect();
            ResultSet rs = sQLHelper.runQuery(sql.toString());
            if (rs.next()) {
                section.setExam_ID(rs.getInt("Exam_ID"));
                section.setSection_ID(rs.getInt("Section_ID"));
                section.setSection_Name(rs.getString("Section_Name"));
                section.setTimeLimit(new SimpleDateFormat("HH:mm:ss").parse(rs.getString("TimeLimit")));
            }
            sQLHelper.sqlClose();
           
        } catch (SQLException ex) {
            Logger.getLogger(Section_DAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(Section_DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return section;
    }

    /**
     * delete the data according to the whereString
     * @param whereString
     */
    public void delete(String whereString){
        StringBuffer sql = new StringBuffer("");
        sql.append("Delete from `Section`");
        if (whereString.trim() != "") {
            sql.append(" where " + whereString);
        }
        SQLHelper sQLHelper = new SQLHelper();
        sQLHelper.sqlConnect();
        sQLHelper.runUpdate(sql.toString());
        sQLHelper.sqlClose();
        
    }
}
