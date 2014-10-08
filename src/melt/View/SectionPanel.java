/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package melt.View;

import com.mysql.jdbc.interceptors.ResultSetScannerInterceptor;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import melt.DAO.*;
import melt.Model.*;

/**
 *
 * @author Aote Zhou
 */
public class SectionPanel extends JPanel implements ActionListener {

    //components 
    public JLabel sectionLabel, l1, l2;
    public JTextField sectionTimeField_h, sectionTimeField_m, sectionTimeField_s;
    public JButton sectionButton1, sectionButton2, sectionButton3;
    public JButton sectionSubmitButton;
    public JPanel p1;
    public JPanel sectionContentPanel;

    public enum sectionContentState {

        NOSUB, TWOSUB
    };
    public sectionContentState sectionContentFlag;

    Section section;

    int numOfSub;

    public SectionPanel() {

    }

    public SectionPanel(int numOfSub) {
        this.numOfSub = numOfSub;
    }

    public void getGUI(String sectionName, int timelimit_h, int timelimit_m, int timelimit_s) {

        GroupLayout groupLayout;
        GroupLayout.ParallelGroup horizontalGroup_P;
        GroupLayout.SequentialGroup verticalGroup_S;

        section = new Section();
        section.setSection_Name(sectionName);
        try {
            Date timeLimit = new SimpleDateFormat("HH:mm:ss").parse(timelimit_h + ":" + timelimit_m + ":" + timelimit_s);
            section.setTimeLimit(timeLimit);
        } catch (ParseException ex) {
            Logger.getLogger(SectionPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        section.setExam_ID(1);

        //create p1
        //p1 = new JPanel();
        //p1.setLayout(new BoxLayout(p1, BoxLayout.X_AXIS));
        sectionContentPanel = new JPanel();
        //sectionContentPanel.setBackground(Color.red);
        sectionContentPanel.setLayout(new BoxLayout(sectionContentPanel, BoxLayout.Y_AXIS));
        for (int i = 0; i < numOfSub; i++) {
            SubsectionPanel subSectionPanel1 = new SubsectionPanel();

            subSectionPanel1.getGUI();
            sectionContentPanel.add(subSectionPanel1);
            sectionContentFlag = sectionContentState.TWOSUB;
        }

        sectionLabel = new JLabel("Section1 " + sectionName);
        l1 = new JLabel(":");
        l2 = new JLabel(":");
        sectionTimeField_h = new JTextField(timelimit_h + "");
        sectionTimeField_h.setMaximumSize(new Dimension(30, 4));
        sectionTimeField_m = new JTextField(timelimit_m + "");
        sectionTimeField_m.setMaximumSize(new Dimension(30, 4));
        sectionTimeField_s = new JTextField(timelimit_s + "");
        sectionTimeField_s.setMaximumSize(new Dimension(30, 4));
        sectionButton1 = new JButton("Add a Section");
        sectionButton2 = new JButton("Add a Question");
//        sectionButton3 = new JButton("Delete");

        sectionSubmitButton = new JButton("Submit");

        sectionButton1.addActionListener(this);
        sectionButton2.addActionListener(this);
        sectionSubmitButton.addActionListener(this);

        groupLayout = new GroupLayout(this);
        groupLayout.setAutoCreateContainerGaps(true);
        groupLayout.setAutoCreateGaps(true);

        verticalGroup_S = groupLayout.createSequentialGroup();
        horizontalGroup_P = groupLayout.createParallelGroup();

        horizontalGroup_P.addGroup(groupLayout.createSequentialGroup()
                .addComponent(sectionLabel)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                .addComponent(sectionTimeField_h)
                .addComponent(l1)
                .addComponent(sectionTimeField_m)
                .addComponent(l2)
                .addComponent(sectionTimeField_s)
                .addComponent(sectionButton1)
                .addComponent(sectionButton2))
                .addGroup(groupLayout.createSequentialGroup()
                        .addGap(20)
                        .addComponent(sectionContentPanel));
        verticalGroup_S.addGroup(groupLayout.createParallelGroup()
                .addComponent(sectionLabel)
                .addComponent(sectionTimeField_h)
                .addComponent(l1)
                .addComponent(sectionTimeField_m)
                .addComponent(l2)
                .addComponent(sectionTimeField_s)
                .addComponent(sectionButton1)
                .addComponent(sectionButton2))
                .addComponent(sectionContentPanel);
        groupLayout.setHorizontalGroup(horizontalGroup_P);
        groupLayout.setVerticalGroup(verticalGroup_S);

        this.setLayout(groupLayout);

        //return p1;
    }

    public void getGUI(int section_ID) {
        Section_DAO section_DAO = new Section_DAO();
        section = section_DAO.getModel(section_ID);
        Date sectionTimeLimit = section.getTimeLimit();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(sectionTimeLimit);
        getGUI(section.getSection_Name(), calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND));

        //get subsections
        Subsection_DAO subsection_DAO = new Subsection_DAO();
        ResultSet rs = subsection_DAO.getList("Section_ID='" + section_ID + "'");
        ArrayList<SubSection> subSections = new ArrayList<SubSection>();
        try {
            while (rs.next()) {
                SubSection subSection = new SubSection();

                subSection.setSection_ID(rs.getInt("section_ID"));
                subSection.setSubSection_ID(rs.getInt("SubSection_ID"));
                subSection.setSubSection_Name(rs.getString("SubSection_Name"));

                subSections.add(subSection);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SectionPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (subSections.size() == 1) {
            //the condition that have only one subsection in the database now
            SubSection subSection = subSections.get(0);
            if (subSection.getSubSection_Name().equals("0")) {
                //on the condition no subsection
                Question_DAO question_DAO = new Question_DAO();
                ResultSet rs1 = question_DAO.getList("subsection_ID='" + subSection.getSubSection_ID() + "'");
                try {
                    while (rs1.next()) {
                        MCQPanel mCQPanel = new MCQPanel(rs1.getInt("Question_ID"));
                        addQ(mCQPanel);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(SubsectionPanel.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else {
                //on the condition has two subsection but only add a subsection
                SubsectionPanel subSectionPanel1 = new SubsectionPanel();

                subSectionPanel1.getGUI(subSections.get(0));

                sectionContentPanel.add(subSectionPanel1);
                sectionContentFlag = sectionContentState.TWOSUB;
            }

        } else if (subSections.size() == 2) {
            SubsectionPanel subSectionPanel1 = new SubsectionPanel();
            SubsectionPanel subSectionPanel2 = new SubsectionPanel();

            subSectionPanel1.getGUI(subSections.get(0));
            subSectionPanel2.getGUI(subSections.get(1));

            sectionContentPanel.add(subSectionPanel1);
            sectionContentPanel.add(subSectionPanel2);
            sectionContentFlag = sectionContentState.TWOSUB;
        }

        //return p1;
    }

    public void addQ(MCQPanel mCQPanel) {
        mCQPanel.getGUI();
        sectionContentPanel.add(mCQPanel);
    }

    public void subQPanelRepaint() {
        sectionContentPanel.revalidate();
        sectionContentPanel.repaint();
    }

    public boolean submitSection() {
        if (sectionContentFlag == sectionContentState.NOSUB) {    //on the condition that section has no subsection
            Section_DAO section_DAO = new Section_DAO();
            Subsection_DAO subsection_DAO = new Subsection_DAO();
            Question_DAO question_DAO = new Question_DAO();
            MCQ_DAO mcq_dao = new MCQ_DAO();

            String final_timelimit_h = sectionTimeField_h.getText();
            String final_timelimit_m = sectionTimeField_m.getText();
            String final_timelimit_s = sectionTimeField_s.getText();

            Date timeLimit = new Date();
            try {
                timeLimit = new SimpleDateFormat("HH:mm:ss").parse(final_timelimit_h + ":" + final_timelimit_m + ":" + final_timelimit_s);
            } catch (ParseException ex) {
                Logger.getLogger(SectionPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
            section.setTimeLimit(timeLimit);

            int section_ID = section_DAO.add(section);
            SubSection subSection = new SubSection();
            subSection.setSection_ID(section_ID);
            subSection.setSubSection_Name("0");
            int subsection_ID = subsection_DAO.add(subSection);
            Component[] components = sectionContentPanel.getComponents();
            for (Component component : components) {
                if (component instanceof MCQPanel) {
                    //System.out.println(((JPanel)component).getName());

                    question_DAO.update(subsection_ID, Integer.parseInt(((JPanel) component).getName()));
                    mcq_dao.update(subsection_ID, Integer.parseInt(((JPanel) component).getName()));
                }

            }
            JOptionPane.showMessageDialog(sectionLabel, "Successfully saved!", "Success", JOptionPane.PLAIN_MESSAGE);
            return true;
        } else if (sectionContentFlag == sectionContentState.TWOSUB) { //the condition that section has two subsection

            Section_DAO section_DAO = new Section_DAO();

            String final_timelimit_h = sectionTimeField_h.getText();
            String final_timelimit_m = sectionTimeField_m.getText();
            String final_timelimit_s = sectionTimeField_s.getText();

            Date timeLimit = new Date();
            try {
                timeLimit = new SimpleDateFormat("HH:mm:ss").parse(final_timelimit_h + ":" + final_timelimit_m + ":" + final_timelimit_s);
            } catch (ParseException ex) {
                Logger.getLogger(SectionPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
            section.setTimeLimit(timeLimit);

            int section_ID = section_DAO.add(section);
            Component[] components = sectionContentPanel.getComponents();//Subsection
            for (Component component : components) {
                if (component instanceof SubsectionPanel) {
                    Subsection_DAO subsection_DAO = new Subsection_DAO();
                    SubSection subSection = new SubSection();
                    subSection.setSection_ID(section_ID);
                    subSection.setSubSection_Name("");
                    int subsection_ID = subsection_DAO.add(subSection);
                    Component[] components2 = ((JPanel) component).getComponents();  //SubsectionQuestion
                    for (Component component2 : components2) {
                        System.out.println(component2 instanceof JPanel);
                        if (component2 instanceof JPanel) {
                            Component[] components3 = ((JPanel) component).getComponents();  //SubsectionQuestion
                            for (Component component3 : components3) {
                                if (component2 instanceof MCQPanel) {
                                    Question_DAO question_DAO = new Question_DAO();
                                    MCQ_DAO mcq_dao = new MCQ_DAO();

                                    question_DAO.update(subsection_ID, Integer.parseInt(((JPanel) component3).getName()));
                                    mcq_dao.update(subsection_ID, Integer.parseInt(((JPanel) component3).getName()));

                                }
                            }
                        }
                    }

                }

            }
            JOptionPane.showMessageDialog(sectionLabel, "Successfully saved!", "Success", JOptionPane.PLAIN_MESSAGE);
            return true;

        } else {
            JOptionPane.showMessageDialog(sectionLabel, "Cannot submit empty section...", "Attention", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public boolean updateSection() {
        MCQ_DAO mcq_DAO = new MCQ_DAO();
        Question_DAO question_DAO = new Question_DAO();
        Subsection_DAO subsection_DAO = new Subsection_DAO();
        Section_DAO section_DAO = new Section_DAO();

        int section_ID = section.getSection_ID();
        mcq_DAO.cancelRWithSubSec(section_ID);
        question_DAO.cancelRWithSubSec(section_ID);
        subsection_DAO.delete("Section_ID='" + section_ID + "'");
        section_DAO.delete("Section_ID='" + section_ID + "'");

        return submitSection();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == sectionButton1) {
            if (sectionContentFlag == sectionContentState.NOSUB && sectionContentPanel.getComponentCount() != 0) {
                JOptionPane.showMessageDialog(sectionLabel, "Delete all the questions first!", "Attention", JOptionPane.ERROR_MESSAGE);

            } else if (sectionContentPanel.getComponentCount() >= 2) {
                JOptionPane.showMessageDialog(sectionLabel, "Already have two subsections!", "Attention", JOptionPane.ERROR_MESSAGE);

            } else {
                SubsectionPanel subSectionPanel1 = new SubsectionPanel();

                subSectionPanel1.getGUI();
                sectionContentPanel.add(subSectionPanel1);
                sectionContentPanel.revalidate();
                sectionContentFlag = sectionContentState.TWOSUB;
            }

        } else if (e.getSource() == sectionButton2) {
            if (sectionContentFlag == sectionContentState.TWOSUB && sectionContentPanel.getComponentCount() != 0) {
                JOptionPane.showMessageDialog(sectionLabel, "Delete all the subsections first!", "Attention", JOptionPane.ERROR_MESSAGE);

            } else {
                sectionContentFlag = sectionContentState.NOSUB;
                ChooseQuestionsPanel chooseQuestionsPanel = new ChooseQuestionsPanel(this);
                chooseQuestionsPanel.setVisible(true);
            }

        }
    }

}
