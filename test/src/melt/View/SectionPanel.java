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
import javax.swing.Box;
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
 *
 * @author Aote Zhou
 */
public class SectionPanel extends JPanel implements ActionListener {

    //components 
    public JLabel sectionLabel, l0, l1, l2;
    public JTextField sectionTimeField_h, sectionTimeField_m, sectionTimeField_s;
    public JButton sectionButton2, sectionButton3;
    public JButton sectionSubmitButton;
    public JPanel p1;
    public JPanel sectionContentPanel;
    public Section section;

    public int numOfSub;//number of subsections
    int exam_ID;

    public SectionPanel(int exam_ID) {
        this.exam_ID = exam_ID;
    }

    public SectionPanel(int numOfSub, int exam_ID) {
        this.numOfSub = 0;
        this.exam_ID = exam_ID;
    }

    /**
     * get the content panel(when adding a new section)
     *
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
        

        sectionLabel = new JLabel("Section " + sectionName);
        l0 = new JLabel("Hours");
        l1 = new JLabel("Minutes");
        l2 = new JLabel("Seconds");
        String timelimit_hString = Integer.toString(timelimit_h);
        String timelimit_mString = Integer.toString(timelimit_m);
        String timelimit_sString = Integer.toString(timelimit_s);
        if (timelimit_h == 0) {
            timelimit_hString = "00";
        }
        if (timelimit_m == 0) {
            timelimit_mString = "00";
        }
        if (timelimit_s == 0) {
            timelimit_sString = "00";
        }
        sectionTimeField_h = new JTextField(timelimit_hString + "");
        sectionTimeField_h.setMaximumSize(new Dimension(30, 4));
        sectionTimeField_m = new JTextField(timelimit_mString + "");
        sectionTimeField_m.setMaximumSize(new Dimension(30, 4));
        sectionTimeField_s = new JTextField(timelimit_sString + "");
        sectionTimeField_s.setMaximumSize(new Dimension(30, 4));
        sectionButton2 = new JButton("Add a Question");
//        sectionButton3 = new JButton("Delete");

        sectionSubmitButton = new JButton("Submit");

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
                .addComponent(sectionButton2))
                .addComponent(sectionContentPanel);
        groupLayout.setHorizontalGroup(horizontalGroup_P);
        groupLayout.setVerticalGroup(verticalGroup_S);

        this.setLayout(groupLayout);

        //return p1;
    }

    /**
     * get the content panel of section(a section which is now exited in the
     * database)
     *
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

        //getnum of questiontypes
        SectionQuestion_DAO sectionQuestion_DAO = new SectionQuestion_DAO();
        ArrayList<Integer> qTypeLists= sectionQuestion_DAO.getQTypes("section_ID='" + section_ID + "'");
        int numOfQTypes=qTypeLists.size();
        if (numOfQTypes == 1) {
            //getquestions
            ArrayList<int[]> results = sectionQuestion_DAO.getList("section_ID='" + section_ID + "'");

            for (int i = 0; i < results.size(); i++) {
                int[] ints = results.get(i);
                QuestionPanel questionPanel = new QuestionPanel(ints[0], ints[2]);
                addQ(questionPanel);
            }

        } else if (numOfQTypes == 2) {
            for(int qType:qTypeLists){
                SubsectionPanel subsectionPanel=new SubsectionPanel(exam_ID,section_ID,qType);
                sectionContentPanel.add(subsectionPanel);
            }

        } else if (numOfQTypes == 0) {
            

        }else {
            sectionContentPanel.add(new JLabel("fail to load exam try again"));
        }

        //return p1;
    }

    /**
     * get GUI of mcqPanel and set it as content panel
     *
     * @param questionPanel
     */
    public void addQ(QuestionPanel questionPanel) {
        questionPanel.getGUI();
        sectionContentPanel.add(questionPanel);
//        sectionContentPanel.add(Box.createRigidArea(new Dimension(0, 10)));

    }

    /**
     * repaint the subQuestionPanel
     */
    public void subQPanelRepaint() {
        sectionContentPanel.revalidate();
        sectionContentPanel.repaint();
    }

    /**
     * deal with the data when submit the section
     *
     * @return
     */
    public boolean submitSection() {
        if (sectionContentPanel.getComponentCount() != 0) {
            //add section to the database
            Section_DAO section_DAO = new Section_DAO();

            String final_timelimit_h = sectionTimeField_h.getText();
            String final_timelimit_m = sectionTimeField_m.getText();
            String final_timelimit_s = sectionTimeField_s.getText();
            int timeLimit_h = 0;
            int timeLimit_m = 0;
            int timeLimit_s = 0;
            try {
                timeLimit_h = Integer.parseInt(final_timelimit_h);
                timeLimit_m = Integer.parseInt(final_timelimit_m);
                timeLimit_s = Integer.parseInt(final_timelimit_s);
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(p1, "Please enter a correct time format (ex. 00:30:00)", "Incorrect time value", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            if (timeLimit_h > 24 || timeLimit_h < 00 || timeLimit_m > 60 || timeLimit_m < 0 || timeLimit_s > 60 || timeLimit_s < 0 || (timeLimit_h == 0 && timeLimit_m == 0 && timeLimit_s == 0)) {
                JOptionPane.showMessageDialog(p1, "Please enter a correct time format (ex. 00:30:00)", "Incorrect time value", JOptionPane.ERROR_MESSAGE);
                return false;
            }
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
                    //get subsection question and save to the database
                    Component[] components2 = ((JPanel) component).getComponents();  //SubsectionQuestion
                    for (Component component2 : components2) {
                        System.out.println(component2 instanceof JPanel);
                        if (component2 instanceof JPanel) {
                            Component[] components3 = ((JPanel) component2).getComponents();  //SubsectionQuestion
                            for (Component component3 : components3) {
                                if (component3 instanceof QuestionPanel) {
//                                    Question_DAO question_DAO = new Question_DAO();
//                                    MCQ_DAO mcq_dao = new MCQ_DAO();
//
//                                    question_DAO.update(subsection_ID, Integer.parseInt(((JPanel) component3).getName()));
//                                    mcq_dao.update(subsection_ID, Integer.parseInt(((JPanel) component3).getName()));
                                    SectionQuestion_DAO sectionQuestion_DAO = new SectionQuestion_DAO();
                                    sectionQuestion_DAO.add(section_ID, Integer.parseInt(((JPanel) component3).getName()));
                                }
                            }
                        }
                    }

                } else if (component instanceof QuestionPanel) {
                    //System.out.println(((JPanel)component).getName());

//                    question_DAO.update(subsection_ID, Integer.parseInt(((JPanel) component).getName()));
//                    mcq_dao.update(subsection_ID, Integer.parseInt(((JPanel) component).getName()));
                    SectionQuestion_DAO sectionQuestion_DAO = new SectionQuestion_DAO();

                    sectionQuestion_DAO.add(section_ID, Integer.parseInt(((JPanel) component).getName()));
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
     * operations for update the section(delete the related info first and then
     * add again)
     *
     * @return
     */
    public boolean updateSection() {
//        MCQ_DAO mcq_DAO = new MCQ_DAO();
//        Question_DAO question_DAO = new Question_DAO();
        SectionQuestion_DAO subsectionQuestion_DAO = new SectionQuestion_DAO();
        Section_DAO section_DAO = new Section_DAO();

        int section_ID = section.getSection_ID();
//        mcq_DAO.cancelRWithSubSec(section_ID);
//        question_DAO.cancelRWithSubSec(section_ID);
        subsectionQuestion_DAO.cancelRWithSec(section_ID);

        section_DAO.delete("Section_ID='" + section_ID + "'");

        boolean result = submitSection();
        return result;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == sectionButton2) {//add a question
            if (sectionContentPanel.getComponentCount()==0||sectionContentPanel.getComponent(0) instanceof QuestionPanel) {
                ChooseQuestionsPanel chooseQuestionsPanel = new ChooseQuestionsPanel((SectionPanel) this);
                chooseQuestionsPanel.setVisible(true);

            }else{
               JOptionPane.showMessageDialog(sectionLabel, "please click the <add question> button of subsection!", "Attention", JOptionPane.ERROR_MESSAGE);
            }

        }
    }

}
