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
    JRadioButton[] choices;
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
        
        GroupLayout groupLayout;
        GroupLayout.ParallelGroup horizontalContentGroup_P;
        GroupLayout.SequentialGroup verticalGroup_S,horizontalGroup_S;
        this.setName(Q_ID+"");
        
        //getQuestions from database
        getQ(Q_ID);
        
        questionLabel = new JLabel("Question 1");
        questionDeleteButton = new JButton("Delete");
        questionContent = new JLabel(question.getQuestion_Text());
        choices=new JRadioButton[mCQOptions.length];
        
        
        for(int i=0;i<mCQOptions.length;i++){
            choices[i]=new JRadioButton(mCQOptions[i].getContent());
            
        }
   
        questionDeleteButton.addActionListener(this);
        //setlayouts
        groupLayout = new GroupLayout(this);
        groupLayout.setAutoCreateContainerGaps(true);
        groupLayout.setAutoCreateGaps(true);
        horizontalContentGroup_P=groupLayout.createParallelGroup();
        horizontalGroup_S = groupLayout.createSequentialGroup();
        verticalGroup_S = groupLayout.createSequentialGroup();
        //group for choices
        GroupLayout.ParallelGroup choiceGroup_P=groupLayout.createParallelGroup();
        GroupLayout.SequentialGroup choiceGroup_S=groupLayout.createSequentialGroup();
        
        for(JRadioButton choice:choices){
            choiceGroup_P.addComponent(choice);
            choiceGroup_S.addComponent(choice);
        }
        
        
        //group for questions
        horizontalContentGroup_P.addGroup(groupLayout.createSequentialGroup()
                .addComponent(questionLabel)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 200, Short.MAX_VALUE)
                .addComponent(questionDeleteButton));
        horizontalContentGroup_P.addGroup(groupLayout.createSequentialGroup()
                .addComponent(questionContent));
        horizontalContentGroup_P.addGroup(groupLayout.createSequentialGroup()
                .addGap(5)
                .addGroup(choiceGroup_P));
        //group for horizontal
        horizontalGroup_S.addGap(0).addGroup(horizontalContentGroup_P);

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
        
    }

    /**
     *get questions from database
     * @param questionID
     */
    public void getQ(int questionID){
        Question_DAO question_DAO=new Question_DAO();
        MCQOption_DAO mCQOption_DAO=new MCQOption_DAO();
        
        question=question_DAO.getModel(questionID);
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