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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle;
import melt.DAO.SubsectionQuestion_DAO;
import melt.Model.*;

/**
 * panel for the subsection
 * @author Aote Zhou
 */
public class SubsectionPanel extends JPanel implements ActionListener{
    //components in p2
    JLabel subsectionLabel1;
    JButton subsectionButton1,subsectionButton2;
    JPanel subsectionQuestionPanel;
    JPanel p1,p2;
    int exam_ID;

    public SubsectionPanel(int exam_ID) {
        this.exam_ID=exam_ID;
    }
    
    
    /**
     *set the GUI of this panel
     */
    public void getGUI(){
        
        
        GroupLayout groupLayout;
        GroupLayout.ParallelGroup  horizontalGroup_P;
        GroupLayout.SequentialGroup  verticalGroup_S;
        
        //p2 = new JPanel();
        this.setName("subsec");
        subsectionQuestionPanel=new JPanel();
        subsectionQuestionPanel.setName("subsecQ");
        //subsectionQuestionPanel.setBackground(Color.white);
        subsectionQuestionPanel.setLayout(new BoxLayout(subsectionQuestionPanel, BoxLayout.Y_AXIS));
        
        
        subsectionLabel1 = new JLabel("Subsection 1");
        subsectionButton1 = new JButton("Add question");
        subsectionButton2 = new JButton("Delete");
        
        subsectionButton1.addActionListener(this);
        subsectionButton2.addActionListener(this);

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
                .addComponent(subsectionQuestionPanel));

        verticalGroup_S.addGroup(groupLayout.createParallelGroup()
                .addComponent(subsectionLabel1)
                .addComponent(subsectionButton1)
                .addComponent(subsectionButton2))
                .addComponent(subsectionQuestionPanel);
                

        groupLayout.setHorizontalGroup(horizontalGroup_P);
        groupLayout.setVerticalGroup(verticalGroup_S);
        
        this.setLayout(groupLayout);
        
        
//        p1=new JPanel();
//        p1.setLayout(new BoxLayout(p1, BoxLayout.Y_AXIS));
//        p1.add(p2);
//        subsectionQuestionPanel=new JPanel();
//        subsectionQuestionPanel.setLayout(new BoxLayout(subsectionQuestionPanel, BoxLayout.Y_AXIS));
//        //subsectionQuestionPanel.add(new MCQPanel().getGUI());
//        p1.add(subsectionQuestionPanel);

        //return p2;
    }

    /**
     *get the gui when provided with a subsection
     * @param subSection
     */
    public void getGUI(SubSection subSection){
        getGUI();
        SubsectionQuestion_DAO subsectionQuestion_DAO=new SubsectionQuestion_DAO();
        ResultSet rs=subsectionQuestion_DAO.getList("subsection_ID='"+subSection.getSubSection_ID()+"'");
        try {
            while (rs.next()) {
                MCQPanel mCQPanel=new MCQPanel(rs.getInt("Question_ID"));
                addQ(mCQPanel);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SubsectionPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //return p2;
    }

    /**
     * add MCQPanel to subsectionQuestionPanel
     * @param mCQPanel
     */
    public void addQ(MCQPanel mCQPanel){
        mCQPanel.getGUI();
        subsectionQuestionPanel.add(mCQPanel);
    }

    /**
     * reaint the subsectionpanel
     */
    public void subQPanelRepaint(){
        subsectionQuestionPanel.revalidate();
        subsectionQuestionPanel.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==subsectionButton1) {//add a question
            ChooseQuestionsPanel chooseQuestionsPanel=new ChooseQuestionsPanel((SubsectionPanel)this);
            chooseQuestionsPanel.setVisible(true);
        }if (e.getSource()==subsectionButton2) {//cancel
            //remove current panel form the parent panel
            this.setVisible(false);
            this.getParent().remove(this);
            this.invalidate();
        }
    }

}
