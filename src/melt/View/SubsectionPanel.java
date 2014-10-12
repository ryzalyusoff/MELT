/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package melt.View;

import java.awt.Component;
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
import javax.swing.JSeparator;
import javax.swing.LayoutStyle;
import javax.swing.SwingConstants;
import melt.DAO.Question_DAO;
import melt.Model.*;

/**
 *
 * @author Aote Zhou
 */
public class SubsectionPanel extends JPanel implements ActionListener{
    //components in p2
    JLabel subsectionLabel1;
    JButton subsectionButton1,subsectionButton2;
    JPanel subsectionQuestionPanel;
    JPanel p1,p2;
    
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
        
        
        subsectionLabel1 = new JLabel("Subsection");
        subsectionButton1 = new JButton("Add question");
        subsectionButton2 = new JButton("Delete");
        
        subsectionButton1.addActionListener(this);
        subsectionButton2.addActionListener(this);
        
        groupLayout = new GroupLayout(this);
        groupLayout.setAutoCreateContainerGaps(true);
        groupLayout.setAutoCreateGaps(true);
        verticalGroup_S = groupLayout.createSequentialGroup();
        horizontalGroup_P = groupLayout.createParallelGroup();
        JSeparator sep = new JSeparator(SwingConstants.HORIZONTAL);

        horizontalGroup_P.addGroup(groupLayout.createSequentialGroup()
                .addComponent(subsectionLabel1)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 200, Short.MAX_VALUE)
                .addComponent(subsectionButton1)
                .addComponent(subsectionButton2))
            .addGroup(groupLayout.createSequentialGroup()
                .addGap(20)
                .addComponent(subsectionQuestionPanel))
                .addComponent(sep); 

        verticalGroup_S.addGroup(groupLayout.createParallelGroup()
                .addComponent(subsectionLabel1)
                .addComponent(subsectionButton1)
                .addComponent(subsectionButton2))
                .addComponent(subsectionQuestionPanel)
                .addComponent(sep);                
                
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
    public void getGUI(SubSection subSection){
        getGUI();
        Question_DAO question_DAO=new Question_DAO();
        ResultSet rs=question_DAO.getList("subsection_ID='"+subSection.getSubSection_ID()+"'");
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
    public void addQ(MCQPanel mCQPanel){
        mCQPanel.getGUI();
        subsectionQuestionPanel.add(mCQPanel);
    }
    public void subQPanelRepaint(){
        subsectionQuestionPanel.revalidate();
        subsectionQuestionPanel.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==subsectionButton1) {
            ChooseQuestionsPanel chooseQuestionsPanel=new ChooseQuestionsPanel(this);
            chooseQuestionsPanel.setVisible(true);
        }if (e.getSource()==subsectionButton2) {
//            Component lastSeparator = null;
//            for (Component comp : this.getComponents()) 
//            {
//                if(comp instanceof JSeparator)
//                {
//                    lastSeparator = comp;
//                }
//            }
//            for(int i = 0; i<this.getComponents().length;i++)
//            {
//               if(this.getComponent(i) == lastSeparator)
//               {
//                   this.remove(i);
//               }
//            }
            
            this.setVisible(false);
            this.getParent().remove(this);   
            this.invalidate();
        }
    }

}
