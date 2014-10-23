/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sols.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import sols.Model.MCQ;
import sols.Util.SQLHelper;

/**
 *
 * @author Aote Zhou
 */
public class MCQ_DAO {

    /**
     * get mcq by question_ID
     *
     * @param Question_ID
     * @return
     */
    public MCQ getModel(int Question_ID) {
        MCQ mcquestion = new MCQ();
        try {

            StringBuffer sql = new StringBuffer("");
            sql.append("SELECT Question_ID,QType_ID,Question_Text");
            sql.append(" FROM `MCQ`");
            sql.append(" where Question_ID=" + Question_ID);

            SQLHelper sQLHelper = new SQLHelper();
            sQLHelper.sqlConnect();
            ResultSet rs = sQLHelper.runQuery(sql.toString());
            while (rs.next()) {

                mcquestion.setQtype_ID(rs.getInt("QType_ID"));
                mcquestion.setQuestion_ID(rs.getInt("Question_ID"));
                mcquestion.setQuestion_Text(rs.getString("Question_Text"));
               

            }
        } catch (SQLException ex) {
            Logger.getLogger(MCQ.class.getName()).log(Level.SEVERE, null, ex);
        }
        return mcquestion;
    }

    /**
     * update the subsec_ID of a specific question
     *
     * @param subsec_ID
     * @param Q_ID
     */
//    public void update(int subsec_ID, int Q_ID) {
//        if (new SubsectionQuestion_DAO().hasRelWithSubsection(Q_ID)) {
//            StringBuffer sql = new StringBuffer();
//            sql.append("UPDATE MCQ ");
//            sql.append("SET SubSection_ID='");
//            sql.append(subsec_ID + "'");
//            sql.append(" where Question_ID='");
//            sql.append(Q_ID + "'");
//
//            SQLHelper sQLHelper = new SQLHelper();
//            sQLHelper.sqlConnect();
//
//            sQLHelper.runUpdate(sql.toString());
//        }else{
//        StringBuffer sql=new StringBuffer();
//            sql.append("insert into MCQ select Question_ID,'"+subsec_ID+"',QType_ID,Question_Text from MCQ where Question_ID="+Q_ID);
//            
//            SQLHelper sQLHelper=new SQLHelper();
//            sQLHelper.sqlConnect();
//            sQLHelper.runUpdate(sql.toString());
//            sQLHelper.sqlClose();
//        }
//
//    }
    /**
     * get a set of result
     *
     * @param whereString
     * @return
     */
    public ArrayList<MCQ> getList(String whereString) {

        StringBuffer sql = new StringBuffer("");
        sql.append("SELECT Question_ID,QType_ID,Question_Text");
        sql.append(" FROM `MCQ`");
        if (whereString.trim() != "") {
            sql.append(" where " + whereString);
        }
        SQLHelper sQLHelper = new SQLHelper();
        sQLHelper.sqlConnect();
        ResultSet rs = sQLHelper.runQuery(sql.toString());
        ArrayList<MCQ> mcqs = new ArrayList<MCQ>();
        try {
            while (rs.next()) {
                MCQ mcq=new MCQ();
                mcq.setQtype_ID(rs.getInt("Qtype_ID"));
                mcq.setQuestion_ID(rs.getInt("Question_ID"));
                mcq.setQuestion_Text(rs.getString("Question_Text"));
                mcqs.add(mcq);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Section_DAO.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        sQLHelper.sqlClose();
        //sQLHelper.sqlClose();
        return mcqs;
    }

  

    

    public static void main(String[] args) {
        
    }
}
