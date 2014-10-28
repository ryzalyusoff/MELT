/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package melt.View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.LayoutStyle;
import melt.DAO.SectionQuestion_DAO;
import melt.Model.*;

/**
 * panel for the subsection
 *
 * @author Aote Zhou
 */
public class SubsectionPanel extends JPanel implements ActionListener {

    //components in p2

    JLabel subsectionLabel1;
    JButton subsectionButton1, subsectionButton2;
    JPanel subsectionQuestionPanel;
    JPanel p1, p2;
    int exam_ID;
    int qType;

    public SubsectionPanel(int exam_ID,int sectionID,int qtype) {
        this.exam_ID = exam_ID;
        this.qType=qtype;
        getGUI(sectionID);
    }

    /**
     * set the GUI of this panel
     */
    private void getGUI(int sectionID) {

        GroupLayout groupLayout;
        GroupLayout.ParallelGroup horizontalGroup_P;
        GroupLayout.SequentialGroup verticalGroup_S;

        //p2 = new JPanel();
        this.setName("subsec");
        subsectionQuestionPanel = new JPanel();
        subsectionQuestionPanel.setName("subsecQ");
        //subsectionQuestionPanel.setBackground(Color.white);
        subsectionQuestionPanel.setLayout(new BoxLayout(subsectionQuestionPanel, BoxLayout.Y_AXIS));

        subsectionLabel1 = new JLabel("Subsection");
        subsectionButton1 = new JButton("Add question");
        subsectionButton2 = new JButton("Delete");

        subsectionButton1.addActionListener(this);
        subsectionButton2.addActionListener(this);

        JSeparator jSeparator = new JSeparator();

        groupLayout = new GroupLayout(this);
        groupLayout.setAutoCreateContainerGaps(true);
        groupLayout.setAutoCreateGaps(true);
        verticalGroup_S = groupLayout.createSequentialGroup();
        horizontalGroup_P = groupLayout.createParallelGroup();

        horizontalGroup_P.addGroup(groupLayout.createSequentialGroup()
                .addComponent(subsectionLabel1)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 200, Short.MAX_VALUE)
                .addComponent(subsectionButton1)
                .addComponent(subsectionButton2))
                .addGroup(groupLayout.createSequentialGroup()
                        .addGap(20)
                        .addComponent(subsectionQuestionPanel))
                .addComponent(jSeparator);

        verticalGroup_S.addGroup(groupLayout.createParallelGroup()
                .addComponent(subsectionLabel1)
                .addComponent(subsectionButton1)
                .addComponent(subsectionButton2))
                .addComponent(subsectionQuestionPanel)
                .addComponent(jSeparator);

        groupLayout.setHorizontalGroup(horizontalGroup_P);
        groupLayout.setVerticalGroup(verticalGroup_S);

        this.setLayout(groupLayout);

        SectionQuestion_DAO sectionQuestion_DAO = new SectionQuestion_DAO();
        ArrayList<int[]> results = sectionQuestion_DAO.getList("section_ID='" + sectionID + "' and QType_ID='"+qType+"'");

        for (int i = 0; i < results.size(); i++) {
            int[] ints = results.get(i);
            QuestionPanel questionPanel = new QuestionPanel(ints[0], ints[2]);
            addQ(questionPanel);
        }
        //return p2;
    }

    

    /**
     * add QuestionPanel to subsectionQuestionPanel
     *
     * @param questionPanel
     */
    public void addQ(QuestionPanel questionPanel) {
        questionPanel.getGUI();
        subsectionQuestionPanel.add(questionPanel);
    }

    /**
     * reaint the subsectionpanel
     */
    public void subQPanelRepaint() {
        subsectionQuestionPanel.revalidate();
        subsectionQuestionPanel.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == subsectionButton1) {//add a question
            ChooseQuestionsPanel chooseQuestionsPanel = new ChooseQuestionsPanel((SubsectionPanel) this);
            chooseQuestionsPanel.setVisible(true);
        }
        if (e.getSource() == subsectionButton2) {//cancel
            //remove current panel form the parent panel
            this.setVisible(false);
            this.getParent().remove(this);
            this.invalidate();
        }
    }

}
