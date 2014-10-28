/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package melt.View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import melt.DAO.*;
import melt.Model.*;

/**
 * the panel of MCQ
 *
 * @author Aote Zhou
 */
public class QuestionPanel extends JPanel implements ActionListener {

    JPanel p3;
    //components in p3
    JLabel questionLabel, questionContent;
    JButton questionDeleteButton;
    JLabel[] choices;
    int Q_ID, Q_Type;
    Question question;
    MCQOption[] mCQOptions;

    public QuestionPanel(int Q_ID, int Q_Type) {
        super();
        this.Q_ID = Q_ID;
        this.Q_Type = Q_Type;
    }

    /**
     * set the contentpanel
     */
    public void getGUI() {

        JPanel p1=new JPanel();
        GroupLayout groupLayout;
        GroupLayout.ParallelGroup horizontalContentGroup_P;
        GroupLayout.SequentialGroup verticalGroup_S, horizontalGroup_S;
        //p3 = new JPanel();
        this.setName(Q_ID + "");
        //p3.setLayout(new BoxLayout(p3, BoxLayout.Y_AXIS));
//        p3.setBackground(Color.cyan);

        getQ(Q_ID);
        if (Q_Type == 1) {
            questionLabel = new JLabel("MCQ");
            questionDeleteButton = new JButton("Delete");
            questionContent = new JLabel(((MCQ) question).getQuestion_Text());
            choices = new JLabel[mCQOptions.length];

            for (int i = 0; i < mCQOptions.length; i++) {
//            if(mCQOptions[i].getContent().trim().isEmpty()){
//                break;
//            }
                if (i == 0) {
                    choices[i] = new JLabel("A.  " + mCQOptions[i].getContent());
                }
                if (i == 1) {
                    choices[i] = new JLabel("B.  " + mCQOptions[i].getContent());
                }
                if (i == 2) {
                    choices[i] = new JLabel("C.  " + mCQOptions[i].getContent());
                }
                if (i == 3) {
                    choices[i] = new JLabel("D.  " + mCQOptions[i].getContent());
                }
                if (i == 4) {
                    choices[i] = new JLabel("E.  " + mCQOptions[i].getContent());
                }
                if (i == 5) {
                    choices[i] = new JLabel("F.  " + mCQOptions[i].getContent());
                }

            }

            questionDeleteButton.addActionListener(this);

            groupLayout = new GroupLayout(p1);
            groupLayout.setAutoCreateContainerGaps(true);
            groupLayout.setAutoCreateGaps(true);
            horizontalContentGroup_P = groupLayout.createParallelGroup();
            horizontalGroup_S = groupLayout.createSequentialGroup();
            verticalGroup_S = groupLayout.createSequentialGroup();

            GroupLayout.ParallelGroup choiceGroup_P = groupLayout.createParallelGroup();
            GroupLayout.SequentialGroup choiceGroup_S = groupLayout.createSequentialGroup();

            for (JLabel choice : choices) {
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
                    .addComponent(questionLabel)
            );
            verticalGroup_S.addGroup(groupLayout.createParallelGroup()
                    .addComponent(questionContent));
            verticalGroup_S.addGroup(groupLayout.createParallelGroup()
                    .addGroup(choiceGroup_S
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)));

            groupLayout.setHorizontalGroup(horizontalGroup_S);
            groupLayout.setVerticalGroup(verticalGroup_S);

            p1.setLayout(groupLayout);
        }
        if (Q_Type == 2) {
            questionLabel = new JLabel(((melt.Model.FIB) question).getQuestionInstructions());
            questionDeleteButton = new JButton("Delete");
            String rawQuestion = ((melt.Model.FIB) question).getQuestionText();
            JPanel fibPanel = new JPanel();
            fibPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));

            while (rawQuestion.contains("[")) {
                int startIndex = rawQuestion.indexOf("[");
                fibPanel.add(new JLabel(rawQuestion.substring(0, startIndex)));
                JTextField blank = new JTextField();
                blank.setPreferredSize(new Dimension(80, 25));
                fibPanel.add(blank);
                rawQuestion = rawQuestion.substring(rawQuestion.indexOf("]") + 1);
            }
            if (!(rawQuestion.isEmpty())) {
                fibPanel.add(new JLabel(rawQuestion));
            }

            questionDeleteButton.addActionListener(this);

            groupLayout = new GroupLayout(p1);
            groupLayout.setAutoCreateContainerGaps(true);
            groupLayout.setAutoCreateGaps(true);
            horizontalContentGroup_P = groupLayout.createParallelGroup();
            horizontalGroup_S = groupLayout.createSequentialGroup();
            verticalGroup_S = groupLayout.createSequentialGroup();

            //group for horizontal
            horizontalContentGroup_P.addGroup(groupLayout.createSequentialGroup()
                    .addComponent(questionLabel)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 200, Short.MAX_VALUE)
                    .addComponent(questionDeleteButton));
            horizontalContentGroup_P.addGroup(groupLayout.createSequentialGroup()
                    .addComponent(fibPanel));
            horizontalGroup_S.addGap(0).addGroup(horizontalContentGroup_P);
            //group for vertical
            verticalGroup_S.addGroup(groupLayout.createParallelGroup()
                    .addComponent(questionDeleteButton)
                    .addComponent(questionLabel));
            verticalGroup_S.addGroup(groupLayout.createParallelGroup()
                    .addComponent(fibPanel));

            groupLayout.setHorizontalGroup(horizontalGroup_S);
            groupLayout.setVerticalGroup(verticalGroup_S);

            p1.setLayout(groupLayout);
        }
            p1.setBorder(BorderFactory.createLineBorder(Color.lightGray));
            this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            this.add(p1);
            this.add(Box.createRigidArea(new Dimension(0, 10)));

        //return p3;
    }

    /**
     * get questions from database
     *
     * @param questionID
     */
    public void getQ(int questionID) {
        //Question_DAO question_DAO=new Question_DAO();
        if (Q_Type == 1) {
            MCQ_DAO mcq_dao = new MCQ_DAO();
            MCQOption_DAO mCQOption_DAO = new MCQOption_DAO();

            question = mcq_dao.getModel(questionID);
            mCQOptions = mCQOption_DAO.getModel(questionID);
        }
        if (Q_Type == 2) {

            FIB_DAO fib_dao = new FIB_DAO();
            FIBAnswer_DAO fIBAnswer_DAO = new FIBAnswer_DAO();

            question = fib_dao.getModel(questionID);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == questionDeleteButton) {
            this.setVisible(false);
            this.getParent().remove(this);
            this.invalidate();
        }
    }
}
