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
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import melt.DAO.Exam_DAO;
import melt.DAO.MCQ_DAO;
import melt.DAO.Question_DAO;
import melt.DAO.Section_DAO;
import melt.DAO.Subsection_DAO;
import melt.Model.*;

/**
 *
 * @author Aote Zhou
 */

public class ExamOverview extends JFrame implements ActionListener{

   
    public ArrayList<melt.Model.Exam> exams;
    public JLabel[] examLabels;
    JButton addExamButton;
    public JButton[] buttons,isPublicButtons;
    JPanel contentPanel;
    JPanel p1, p2, p3, p4,p5;
    JScrollPane jspane;

    public ExamOverview() {
        this.setLocationRelativeTo(null);  //make window in the center of desktop
        setTitle("MELTSystem--Exam");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setContentPane(getGUI());
        JMenuBar jMenuBar1=new JMenuBar();
        JMenu jMenu1=new JMenu("Exam");
        jMenuBar1.add(jMenu1);
        getRootPane().setMenuBar(jMenuBar1);
    }

    public JPanel getGUI() {
        

        

        //create p2
        p2 = new JPanel();
        setP2();
        //Create p3
        p3 = new JPanel();
        
        addExamButton = new JButton("Add a Exam");
        addExamButton.addActionListener(this);
        
        
        p3.setLayout(new BorderLayout());
        //p3.add(new JLabel("Overall   6.0/30.0"));
        //p3.add(addExamButton,BorderLayout.SOUTH);

        //Create p4
        p4 = new JPanel();
        p4.setPreferredSize(new Dimension(300,1000));
        p4.setLayout(new BoxLayout(p4, BoxLayout.Y_AXIS));
        //p4.add(p1);
        jspane=new JScrollPane(p2);
        jspane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        p4.add(jspane);
        p4.add(addExamButton);
        
        
        //create p5
        p5=new JPanel();
        p5.setLayout(new BoxLayout(p5, BoxLayout.X_AXIS));
        p5.add(p4);
        contentPanel=new JPanel();
        contentPanel.setLayout(new BorderLayout());
        p5.add(contentPanel);
        
        
        p2.setBackground(new Color(153, 153, 153));
        p3.setBackground(new Color(153, 153, 153));
        p4.setBackground(new Color(153, 153, 153));
        
        
        p4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(""), "Exam Overview"));

        return p5;
    }

    private void setP2(){
        
        
        
        getExams();
        
        buttons = new JButton[exams.size()];
        isPublicButtons=new JButton[exams.size()];
        
        
        

        examLabels=new JLabel[exams.size()];

        for (int i = 1; i < exams.size() + 1; i++) {
            melt.Model.Exam exam=exams.get(i-1);
           
            
            examLabels[i - 1] = new JLabel("Exam" + i);
            //Edit Button
            buttons[i - 1] = new JButton("Edit");
            buttons[i-1].setName(exam.getExam_ID()+"");
            buttons[i-1].addActionListener(this);
            //isPublicButton
            if (exam.getIsPublic()) {
                isPublicButtons[i-1]=new JButton("public now");
                isPublicButtons[i-1].setEnabled(false);
            }else{
                isPublicButtons[i-1]=new JButton("Make it public");
            }
            isPublicButtons[i-1].setName(exam.getExam_ID()+"");
            isPublicButtons[i-1].addActionListener(this);
            
            
            
            

        }
        
        //p2.setLayout(new GridLayout(sections.size(),3));
        GroupLayout groupLayout;
        GroupLayout.ParallelGroup horizontalGroup_P;
        GroupLayout.SequentialGroup verticalGroup_S;
        
        groupLayout=new GroupLayout(p2);
        groupLayout.setAutoCreateContainerGaps(true);
        groupLayout.setAutoCreateGaps(true);
        
        horizontalGroup_P=groupLayout.createParallelGroup();
        verticalGroup_S=groupLayout.createSequentialGroup();
        
        
        
        for (int i = 0; i < exams.size(); i++) {
//            p2.add(sectionLabels[i]);
//            p2.add(buttons[i]);
//            p2.add(deleteButtons[i]);
            horizontalGroup_P.addGroup(groupLayout.createSequentialGroup()
                    .addComponent(examLabels[i])
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                    .addComponent(buttons[i])
                    .addComponent(isPublicButtons[i]));
            verticalGroup_S.addGroup(groupLayout.createParallelGroup()
                    .addComponent(examLabels[i])
                    .addComponent(buttons[i])
                    .addComponent(isPublicButtons[i]));
        }
        groupLayout.setHorizontalGroup(horizontalGroup_P);
        groupLayout.setVerticalGroup(verticalGroup_S);
        
        p2.setLayout(groupLayout);
        p2.revalidate();
        //p2.repaint();

    }
    private void getExams() {
        try {
            Exam_DAO exam_DAO=new Exam_DAO();
            ResultSet rs=exam_DAO.getList("");

            exams = new ArrayList<melt.Model.Exam>();
            while (rs.next()) {
                melt.Model.Exam examTemp=new melt.Model.Exam();
                examTemp.setExam_ID(rs.getInt("Exam_ID"));
                examTemp.setInstructions(rs.getString("Instructions"));
                examTemp.setIsPublic(rs.getBoolean("isPublic"));
                exams.add(examTemp);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ExamOverview.class.getName()).log(Level.SEVERE, null, ex);
        } 

    }

    

    @Override
    public void actionPerformed(ActionEvent e) {
       if(((JButton)e.getSource()).getText().equals("Edit")){  //Edit Button
            this.dispose();
            int exam_ID=Integer.parseInt(((JButton)e.getSource()).getName());
            Exam exam=new Exam(exam_ID);
            exam.setVisible(true);
            
        }else if(e.getSource()==addExamButton){
            AddExam addExam=new AddExam();
            //settingSection.setVisible(true);
            contentPanel.removeAll();
            JPanel temp=addExam.getGUI();
            
            contentPanel.setLayout(new BorderLayout());
            contentPanel.add(temp);
            contentPanel.revalidate();
        }else if(((JButton)e.getSource()).getText().equals("Make it public")){
            //isPublicButtons
            Exam_DAO exam_DAO=new Exam_DAO();
            int exam_ID=Integer.parseInt(((JButton)e.getSource()).getName());
            exam_DAO.makeItPublic(exam_ID);
            p2.removeAll();
            setP2();
        }
    }
    public static void main(String[] args) {
        ExamOverview test = new ExamOverview();
        
        test.setVisible(true);

    }

}
