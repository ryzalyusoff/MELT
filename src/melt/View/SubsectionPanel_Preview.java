/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package melt.View;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.LayoutStyle;
import melt.DAO.SectionQuestion_DAO;
import melt.Model.*;

/**
 * panel for the subsection
 *
 * @author Aote Zhou
 */
public class SubsectionPanel_Preview extends JPanel implements ActionListener {

    //components in p2

    JLabel subsectionLabel1;
    JPanel subsectionQuestionPanel;
    JPanel p1, p2;
    int exam_ID;
    int qType;

    public SubsectionPanel_Preview(int exam_ID,int sectionID,int qtype) {
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
        

        JSeparator jSeparator = new JSeparator();

        groupLayout = new GroupLayout(this);
        groupLayout.setAutoCreateContainerGaps(true);
        groupLayout.setAutoCreateGaps(true);
        verticalGroup_S = groupLayout.createSequentialGroup();
        horizontalGroup_P = groupLayout.createParallelGroup();

        horizontalGroup_P.addGroup(groupLayout.createSequentialGroup()
                .addComponent(subsectionLabel1)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 200, Short.MAX_VALUE))
                .addGroup(groupLayout.createSequentialGroup()
                        .addGap(20)
                        .addComponent(subsectionQuestionPanel))
                .addComponent(jSeparator);

        verticalGroup_S.addGroup(groupLayout.createParallelGroup()
                .addComponent(subsectionLabel1))
                .addComponent(subsectionQuestionPanel)
                .addComponent(jSeparator);

        groupLayout.setHorizontalGroup(horizontalGroup_P);
        groupLayout.setVerticalGroup(verticalGroup_S);

        this.setLayout(groupLayout);

        SectionQuestion_DAO sectionQuestion_DAO = new SectionQuestion_DAO();
        ArrayList<int[]> results = sectionQuestion_DAO.getList("section_ID='" + sectionID + "' and QType_ID='"+qType+"'");

        for (int i = 0; i < results.size(); i++) {
            int[] ints = results.get(i);
            QuestionPanel_Preview questionPanel_Preview=new QuestionPanel_Preview(ints[0], ints[2]);
            addQ(questionPanel_Preview);
        }
        //return p2;
    }

    

    /**
     * add QuestionPanel to subsectionQuestionPanel
     *
     * @param questionPanel
     */
    public void addQ(QuestionPanel_Preview questionPanel) {
        questionPanel.getGUI();
        subsectionQuestionPanel.add(questionPanel);
//        subsectionQuestionPanel.add(Box.createRigidArea(new Dimension(0, 10)));
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
        
    }

}
