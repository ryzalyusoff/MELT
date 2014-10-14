/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package melt.View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.LayoutStyle;
import melt.DAO.*;
import melt.Model.*;

/**
 * the panel of MCQ
 * @author Aote Zhou
 */
public class MCQPanel extends JPanel implements ActionListener{
    JPanel p3;
    //components in p3
    JLabel questionLabel, questionContent;
    JButton questionDeleteButton;
    JLabel[] choices;
    int Q_ID;
    Question question;
    MCQOption[] mCQOptions;
    public MCQPanel(int Q_ID){
        super();
        this.Q_ID=Q_ID;
    }
    /**
     * set the contentpanel
     */
    public void getGUI(){
        //JPanel p3;
        
        GroupLayout groupLayout;
        GroupLayout.ParallelGroup horizontalContentGroup_P;
        GroupLayout.SequentialGroup verticalGroup_S,horizontalGroup_S;
        //p3 = new JPanel();
        this.setName(Q_ID+"");
        //p3.setLayout(new BoxLayout(p3, BoxLayout.Y_AXIS));
//        p3.setBackground(Color.cyan);

        getQ(Q_ID);
        questionLabel = new JLabel("Question 1");
        questionDeleteButton = new JButton("Delete");
        questionContent = new JLabel(question.getQuestion_Text());
        choices=new JLabel[mCQOptions.length];
        
        
        for(int i=0;i<mCQOptions.length;i++){
            if(i == 0) choices[i]=new JLabel("A.  " + mCQOptions[i].getContent());            
            if(i == 1) choices[i]=new JLabel("B.  " + mCQOptions[i].getContent());            
            if(i == 2) choices[i]=new JLabel("C.  " + mCQOptions[i].getContent());
            if(i == 3) choices[i]=new JLabel("D.  " + mCQOptions[i].getContent());
            if(i == 4) choices[i]=new JLabel("E.  " + mCQOptions[i].getContent());
            if(i == 5) choices[i]=new JLabel("F.  " + mCQOptions[i].getContent());
            
        }
   
        questionDeleteButton.addActionListener(this);

        groupLayout = new GroupLayout(this);
        groupLayout.setAutoCreateContainerGaps(true);
        groupLayout.setAutoCreateGaps(true);
        horizontalContentGroup_P=groupLayout.createParallelGroup();
        horizontalGroup_S = groupLayout.createSequentialGroup();
        verticalGroup_S = groupLayout.createSequentialGroup();
        
        GroupLayout.ParallelGroup choiceGroup_P=groupLayout.createParallelGroup();
        GroupLayout.SequentialGroup choiceGroup_S=groupLayout.createSequentialGroup();
        
        for(JLabel choice:choices){
            choiceGroup_P.addComponent(choice);
            choiceGroup_S.addComponent(choice);
        }
        
        
        //group for horizontal
        horizontalContentGroup_P.addGroup(groupLayout.createSequentialGroup()
                .addComponent(questionLabel)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 200, Short.MAX_VALUE)
                .addComponent(questionDeleteButton));
        horizontalContentGroup_P.addGroup(groupLayout.createSequentialGroup()
                .addComponent(questionContent));
        horizontalContentGroup_P.addGroup(groupLayout.createSequentialGroup()
                .addGap(5)
                .addGroup(choiceGroup_P));
        horizontalGroup_S.addGap(0).addGroup(horizontalContentGroup_P);
        //group for vertical
        verticalGroup_S.addGroup(groupLayout.createParallelGroup()
                .addComponent(questionDeleteButton)
                .addComponent(questionLabel));
        verticalGroup_S.addGroup(groupLayout.createParallelGroup()
                .addComponent(questionContent));
        verticalGroup_S.addGroup(groupLayout.createParallelGroup()
                .addGroup(choiceGroup_S
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)));

        
        groupLayout.setHorizontalGroup(horizontalGroup_S);
        groupLayout.setVerticalGroup(verticalGroup_S);

        this.setLayout(groupLayout);
        
        //return p3;
    }
    /**
     *get questions from database
     * @param questionID
     */
    public void getQ(int questionID){
        //Question_DAO question_DAO=new Question_DAO();
        MCQ_DAO mcq_dao=new MCQ_DAO();
        MCQOption_DAO mCQOption_DAO=new MCQOption_DAO();
        
        question=mcq_dao.getModel(questionID);
        mCQOptions=mCQOption_DAO.getModel(questionID);    
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==questionDeleteButton) {
            this.setVisible(false);
            this.getParent().remove(this);
            this.invalidate();
        }
    }
}

