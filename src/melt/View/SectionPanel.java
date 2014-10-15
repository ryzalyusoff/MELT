/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package melt.View;

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
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.SwingConstants;
import melt.DAO.*;
import melt.Model.*;

/**
 * panels for sections
 * @author Aote Zhou
 */
public class SectionPanel extends JPanel implements ActionListener {

    //components 
    public JLabel sectionLabel, l0,l1, l2;
    public JTextField sectionTimeField_h, sectionTimeField_m, sectionTimeField_s;
    public JButton sectionButton1, sectionButton2, sectionButton3;
    public JButton sectionSubmitButton;
    public JPanel p1;
    public JPanel sectionContentPanel;
    //state for sectionContents:have no subsec or two subsec
    public enum sectionContentState {

        NOSUB, TWOSUB
    };
    public sectionContentState sectionContentFlag;//the flag to store Content State

    public Section section;

    public int numOfSub;//number of subsections
    int exam_ID;

    public SectionPanel(int exam_ID) {
        this.exam_ID=exam_ID;
    }

    public SectionPanel(int numOfSub,int exam_ID) {
        this.numOfSub = numOfSub;
        this.exam_ID=exam_ID;
    }

    /**
     *get the content panel(when adding a new section)
     * @param sectionName 
     * @param timelimit_h
     * @param timelimit_m
     * @param timelimit_s
     */
    public void getGUI(String sectionName, int timelimit_h, int timelimit_m, int timelimit_s) {

        GroupLayout groupLayout;
        GroupLayout.ParallelGroup horizontalGroup_P;
        GroupLayout.SequentialGroup verticalGroup_S;

        //if the section instance is null then create new ()
        if (section == null) {
            section = new Section();
            section.setSection_Name(sectionName);
            
            try {
                Date timeLimit = new SimpleDateFormat("HH:mm:ss").parse(timelimit_h + ":" + timelimit_m + ":" + timelimit_s);
                section.setTimeLimit(timeLimit);
            } catch (ParseException ex) {
                Logger.getLogger(SectionPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
            section.setExam_ID(section.getExam_ID());
        }

        //create p1
        //p1 = new JPanel();
        //p1.setLayout(new BoxLayout(p1, BoxLayout.X_AXIS));
        sectionContentPanel = new JPanel();
        //sectionContentPanel.setBackground(Color.red);
        sectionContentPanel.setLayout(new BoxLayout(sectionContentPanel, BoxLayout.Y_AXIS));
        for (int i = 0; i < numOfSub; i++) {
            SubsectionPanel subSectionPanel1 = new SubsectionPanel(exam_ID);

            subSectionPanel1.getGUI();
            sectionContentPanel.add(subSectionPanel1);       
            sectionContentFlag = sectionContentState.TWOSUB;
        }

        sectionLabel = new JLabel("Section " + sectionName);
        l0 = new JLabel("Hours");
        l1 = new JLabel("Minutes");
        l2 = new JLabel("Seconds");
        String timelimit_hString = Integer.toString(timelimit_h);
        String timelimit_mString = Integer.toString(timelimit_m);
        String timelimit_sString = Integer.toString(timelimit_s);
        if(timelimit_h == 0)
        {
            timelimit_hString = "00";
        }
        if(timelimit_m == 0)
        {
            timelimit_mString = "00";
        }
        if(timelimit_s == 0)
        {
            timelimit_sString = "00";
        }
        sectionTimeField_h = new JTextField(timelimit_hString + "");
        sectionTimeField_h.setMaximumSize(new Dimension(30, 4));
        sectionTimeField_m = new JTextField(timelimit_mString + "");
        sectionTimeField_m.setMaximumSize(new Dimension(30, 4));
        sectionTimeField_s = new JTextField(timelimit_sString + "");
        sectionTimeField_s.setMaximumSize(new Dimension(30, 4));
        sectionButton1 = new JButton("Add a Subsection");
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
                .addComponent(l0)
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
        verticalGroup_S.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(sectionLabel)
                .addComponent(l0)
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

    /**
     *get the content panel of section(a section which is now exited in the database)
     * @param section_ID
     */
    public void getGUI(int section_ID) {
        //create section_DAO
        Section_DAO section_DAO = new Section_DAO();
        //get section
        section = section_DAO.getModel(section_ID);
        //get section's timelimit
        Date sectionTimeLimit = section.getTimeLimit();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(sectionTimeLimit);
        
        //set content panel
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
        
        //the condition that have only one subsection in the database now
        if (subSections.size() == 1) {
            SubSection subSection = subSections.get(0);
            if (subSection.getSubSection_Name().equals("0")) {
                //on the condition no subsection            
                //if the subsectionname equals 0 means this section will have no subsection and have only one type of question

                //getquestions
                SubsectionQuestion_DAO subsectionQuestion_DAO=new SubsectionQuestion_DAO();
                ResultSet rs1 = subsectionQuestion_DAO.getList("subsection_ID='" + subSection.getSubSection_ID() + "'");
                try {
                    while (rs1.next()) {
                        MCQPanel mCQPanel = new MCQPanel(rs1.getInt("Question_ID"));
                        addQ(mCQPanel);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(SubsectionPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
                sectionContentFlag = sectionContentState.NOSUB;

            } else {
                //on the condition has two subsection but only add a subsection
                SubsectionPanel subSectionPanel1 = new SubsectionPanel(exam_ID);

                subSectionPanel1.getGUI(subSections.get(0));
                //add subSectionPanel1 and set the flag of section
                sectionContentPanel.add(subSectionPanel1);
                sectionContentFlag = sectionContentState.TWOSUB;
            }

        } else if (subSections.size() == 2) {
            //create two panels
            SubsectionPanel subSectionPanel1 = new SubsectionPanel(exam_ID);
            SubsectionPanel subSectionPanel2 = new SubsectionPanel(exam_ID);

            subSectionPanel1.getGUI(subSections.get(0));
            subSectionPanel2.getGUI(subSections.get(1));
            //add subseationpanel to the contentpanel and set the contentstate
            sectionContentPanel.add(subSectionPanel1);
            sectionContentPanel.add(subSectionPanel2);
            sectionContentFlag = sectionContentState.TWOSUB;
        }

        //return p1;
    }

    /**
     *get GUI of mcqPanel and set it as content panel
     * @param mCQPanel
     */
    public void addQ(MCQPanel mCQPanel) {
        mCQPanel.getGUI();
        sectionContentPanel.add(mCQPanel);
    }

    /**
     *repaint the subQuestionPanel
     */
    public void subQPanelRepaint() {
        sectionContentPanel.revalidate();
        sectionContentPanel.repaint();
    }

    /**
     *deal with the data when submit the section
     * @return
     */
    public boolean submitSection() {
        if (sectionContentFlag == sectionContentState.NOSUB) {    
            //on the condition that section has no subsection
            Section_DAO section_DAO = new Section_DAO();
            Subsection_DAO subsection_DAO = new Subsection_DAO();
//            Question_DAO question_DAO = new Question_DAO();
//            MCQ_DAO mcq_dao = new MCQ_DAO();
            SubsectionQuestion_DAO subsectionQuestion_DAO=new SubsectionQuestion_DAO();

            // get timelimit
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

            //add section and get section_ID
            int section_ID = section_DAO.add(section);
            
            //construct the subsection and add to the database
            SubSection subSection = new SubSection();
            subSection.setSection_ID(section_ID);
            subSection.setSubSection_Name("0");
            int subsection_ID = subsection_DAO.add(subSection);
            Component[] components = sectionContentPanel.getComponents();
            for (Component component : components) {
                if (component instanceof MCQPanel) {
                    //System.out.println(((JPanel)component).getName());

//                    question_DAO.update(subsection_ID, Integer.parseInt(((JPanel) component).getName()));
//                    mcq_dao.update(subsection_ID, Integer.parseInt(((JPanel) component).getName()));
                    subsectionQuestion_DAO.add(subsection_ID, Integer.parseInt(((JPanel) component).getName()));
                }

            }
            
            JOptionPane.showMessageDialog(sectionLabel, "Successfully saved!", "Success", JOptionPane.PLAIN_MESSAGE);
            return true;
        } else if (sectionContentFlag == sectionContentState.TWOSUB) { //the condition that section has two subsection

            //add section to the database
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
            section.setExam_ID(exam_ID);

            int section_ID = section_DAO.add(section);
            //get subsection and add to the database
            Component[] components = sectionContentPanel.getComponents();//Subsection
            for (Component component : components) {
                if (component instanceof SubsectionPanel) {
                    Subsection_DAO subsection_DAO = new Subsection_DAO();
                    SubSection subSection = new SubSection();
                    subSection.setSection_ID(section_ID);
                    subSection.setSubSection_Name("");
                    
                    int subsection_ID = subsection_DAO.add(subSection);
                    //get subsection question and save to the database
                    Component[] components2 = ((JPanel) component).getComponents();  //SubsectionQuestion
                    for (Component component2 : components2) {
                        System.out.println(component2 instanceof JPanel);
                        if (component2 instanceof JPanel) {
                            Component[] components3 = ((JPanel) component2).getComponents();  //SubsectionQuestion
                            for (Component component3 : components3) {
                                if (component3 instanceof MCQPanel) {
//                                    Question_DAO question_DAO = new Question_DAO();
//                                    MCQ_DAO mcq_dao = new MCQ_DAO();
//
//                                    question_DAO.update(subsection_ID, Integer.parseInt(((JPanel) component3).getName()));
//                                    mcq_dao.update(subsection_ID, Integer.parseInt(((JPanel) component3).getName()));
                                    SubsectionQuestion_DAO subsectionQuestion_DAO=new SubsectionQuestion_DAO();
                                    subsectionQuestion_DAO.add(subsection_ID, Integer.parseInt(((JPanel) component3).getName()));
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

    /**
     *operations for update the section(delete the related info first and then add again)
     * @return
     */
    public boolean updateSection() {
//        MCQ_DAO mcq_DAO = new MCQ_DAO();
//        Question_DAO question_DAO = new Question_DAO();
        SubsectionQuestion_DAO subsectionQuestion_DAO=new SubsectionQuestion_DAO();
        Subsection_DAO subsection_DAO = new Subsection_DAO();
        Section_DAO section_DAO = new Section_DAO();

        int section_ID = section.getSection_ID();
//        mcq_DAO.cancelRWithSubSec(section_ID);
//        question_DAO.cancelRWithSubSec(section_ID);
        subsectionQuestion_DAO.cancelRWithSubSec(section_ID);
        
        subsection_DAO.delete("Section_ID='" + section_ID + "'");
        section_DAO.delete("Section_ID='" + section_ID + "'");

        boolean result = submitSection();
        return result;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == sectionButton1) { //add a subsection
            if (sectionContentFlag == sectionContentState.NOSUB && sectionContentPanel.getComponentCount() != 0) {
                //the condition when there are still some questions in the section
                JOptionPane.showMessageDialog(sectionLabel, "Delete all the questions first!", "Attention", JOptionPane.ERROR_MESSAGE);

            } else if (sectionContentPanel.getComponentCount() >= 2) {
                //the condition that already have two subsecions
                JOptionPane.showMessageDialog(sectionLabel, "Already have two subsections!", "Attention", JOptionPane.ERROR_MESSAGE);

            } else {
                //add the subsections to the contentpanel
                SubsectionPanel subSectionPanel1 = new SubsectionPanel(exam_ID);

                subSectionPanel1.getGUI();
                sectionContentPanel.add(subSectionPanel1);
                sectionContentPanel.revalidate();
                sectionContentFlag = sectionContentState.TWOSUB;
            }

        } else if (e.getSource() == sectionButton2) {//add a question
            if (sectionContentFlag == sectionContentState.TWOSUB && sectionContentPanel.getComponentCount() != 0) {
                //the condtion that still some subsection exist in the section
                JOptionPane.showMessageDialog(sectionLabel, "Delete all the subsections first!", "Attention", JOptionPane.ERROR_MESSAGE);

            } else {
                //set the contentflag and open the choose question panel
                sectionContentFlag = sectionContentState.NOSUB;
                ChooseQuestionsPanel chooseQuestionsPanel = new ChooseQuestionsPanel((SectionPanel)this);
                chooseQuestionsPanel.setVisible(true);
            }

        }
    }

}
