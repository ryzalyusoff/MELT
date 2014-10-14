/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package melt.View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import melt.DAO.Exam_DAO;

/**
 * privode an overview of the exam(show all the exam in the system)
 * @author Aote Zhou
 */
public class ExamOverview extends JFrame implements ActionListener {

    public ArrayList<melt.Model.Exam> exams;
    public JLabel[] examLabels;
    JButton addExamButton,backButton;
    public JButton[] buttons, isPublicButtons;
    public JCheckBox[] checkbox;
    JPanel contentPanel;
    JPanel p1, p2, p3, p4, p5;
    JScrollPane jspane;
    public boolean checkboxSelected;

    public ExamOverview() {
        //this.setLocationRelativeTo(null);  //make window in the center of desktop
        setTitle("MELTSystem--Exam");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();
        setSize((int)width, (int)height);
        setContentPane(getGUI());
        JMenuBar jMenuBar1 = new JMenuBar();
        JMenu jMenu1 = new JMenu("Exam");
        jMenuBar1.add(jMenu1);
        getRootPane().setMenuBar(jMenuBar1);
    }

    public JPanel getGUI() {

        //create p2
        p2 = new JPanel();
        setP2();
        //Create p3
        p3 = new JPanel();

        JButton EditButton = new JButton("Edit");
        EditButton.addActionListener(this);

        JButton isActivatedButton = new JButton("Activate");
        isActivatedButton.addActionListener(this);

        addExamButton = new JButton("Add a Exam");
        addExamButton.addActionListener(this);
        
        backButton=new JButton("Back");
        backButton.addActionListener(this);
        
        //Create p4
        p4 = new JPanel();
        p4.setMaximumSize(new Dimension(100,1000));
        p4.setLayout(new BoxLayout(p4, BoxLayout.Y_AXIS));
        
        jspane=new JScrollPane(p2);
        jspane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        p4.add(backButton);
        p4.add(jspane);
        
        JPanel pTempOuter = new JPanel(new BorderLayout());
        pTempOuter.setBackground(new Color(153, 153, 153));
        //p3.add(new JLabel("Overall   6.0/30.0"));
        JPanel pTemp = new JPanel(new GridLayout(3, 1));
        pTempOuter.setMaximumSize(new Dimension(100,200));
        pTemp.setMaximumSize(new Dimension(100,200));
        pTemp.setBackground(new Color(153, 153, 153));
        
        pTemp.add(EditButton);
        pTemp.add(isActivatedButton);
        pTemp.add(addExamButton);
        
        pTempOuter.setAlignmentX( Component.LEFT_ALIGNMENT );
        pTemp.setAlignmentX( Component.LEFT_ALIGNMENT );
        p4.setAlignmentX( Component.LEFT_ALIGNMENT );
        
       // pTempOuter.add(pTemp);
        p4.add(pTemp);
        

        //create p5
        p5 = new JPanel();
        p5.setLayout(new BoxLayout(p5, BoxLayout.X_AXIS));
        p5.add(p4);
        contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());
        p5.add(contentPanel);
        
        //set the color of the left part
        p2.setBackground(new Color(153, 153, 153));
        p3.setBackground(new Color(153, 153, 153));
        p4.setBackground(new Color(153, 153, 153));

        p4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(""), "Exam Overview"));

        return p5;
    }

    private void setP2() {

        getExams();

        buttons = new JButton[exams.size()];
        isPublicButtons = new JButton[exams.size()];
        checkbox = new JCheckBox[exams.size()];

        examLabels = new JLabel[exams.size()];

        for (int i = 1; i < exams.size() + 1; i++) {
            melt.Model.Exam exam = exams.get(i - 1);

            examLabels[i - 1] = new JLabel("Exam" + i);
            //Checkboxes
            checkbox[i - 1] = new JCheckBox();
            checkbox[i - 1].setName(exam.getExam_ID() + "");
            checkbox[i - 1].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    JCheckBox cbLog = (JCheckBox) e.getSource();

                    if (cbLog.isSelected()) {
                        for (int i = 0; i < checkbox.length; i++) {
                            if (!(checkbox[i] == e.getSource())) {
                                checkbox[i].setEnabled(false);
                            }
                        }
                    } else {
                        for (int i = 0; i < checkbox.length; i++) {
                            checkbox[i].setEnabled(true);
                        }
                    }
                }
            });
            //isPublicButton
            if (exam.getIsPublic()) {
                isPublicButtons[i - 1] = new JButton("Activated");
                isPublicButtons[i - 1].setEnabled(false);
                Dimension s = isPublicButtons[i - 1].getSize();
                System.out.println(s);
            } else {
                isPublicButtons[i - 1] = new JButton("Activate");
                isPublicButtons[i - 1].setVisible(false);
            }
            isPublicButtons[i - 1].setName(exam.getExam_ID() + "");
            isPublicButtons[i - 1].addActionListener(this);

        }
        
        /**
         * 
         * setlayout
        */
        //p2.setLayout(new GridLayout(sections.size(),3));
        GroupLayout groupLayout;
        GroupLayout.ParallelGroup horizontalGroup_P;
        GroupLayout.SequentialGroup verticalGroup_S;

        groupLayout = new GroupLayout(p2);

        groupLayout.setAutoCreateContainerGaps(
                true);
        groupLayout.setAutoCreateGaps(
                true);

        horizontalGroup_P = groupLayout.createParallelGroup();
        verticalGroup_S = groupLayout.createSequentialGroup();

        for (int i = 0;
                i < exams.size();
                i++) {
//            p2.add(sectionLabels[i]);
//            p2.add(buttons[i]);
//            p2.add(deleteButtons[i]);
            horizontalGroup_P.addGroup(groupLayout.createSequentialGroup()
                    .addComponent(checkbox[i])
                    .addComponent(examLabels[i])
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                    .addComponent(isPublicButtons[i]));

            verticalGroup_S.addGroup(groupLayout.createParallelGroup()
                    .addComponent(checkbox[i])
                    .addComponent(examLabels[i])
                    .addComponent(isPublicButtons[i])
                    .addGap(40));
        }

        groupLayout.setHorizontalGroup(horizontalGroup_P);

        groupLayout.setVerticalGroup(verticalGroup_S);

        p2.setLayout(groupLayout);

        p2.revalidate();
        //p2.repaint();

    }
    /**
     * getExams from database
     */
    private void getExams() {
        try {
            Exam_DAO exam_DAO = new Exam_DAO();
            ResultSet rs = exam_DAO.getList("");

            exams = new ArrayList<melt.Model.Exam>();
            while (rs.next()) {
                melt.Model.Exam examTemp = new melt.Model.Exam();
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
        
        if (((JButton) e.getSource()).getText().equals("Edit")) {  //Edit Button
            if(checkbox.length>0)
            {
            
            int exam_ID = 0;
            for (int i = 0; i < checkbox.length; i++) {
                if (checkbox[i].isSelected()) {
                    //this.dispose();
                    exam_ID = Integer.parseInt((checkbox[i].getName()));
                    Exam exam = new Exam(exam_ID);
                    JFrame fatherFrame=(JFrame)p2.getRootPane().getParent();
                    fatherFrame.setContentPane(exam.getGUI());
                    fatherFrame.revalidate();
                    fatherFrame.repaint();
            //exam.setVisible(true);
                }
            }
        }
            
        

        }else if(e.getSource()==backButton){
            this.dispose();
            melt.startupPanel startupPanel=new melt.startupPanel();
            startupPanel.setVisible(true);
        }else if (e.getSource() == addExamButton) {
            AddExam addExam = new AddExam();
            //settingSection.setVisible(true);
            contentPanel.removeAll();
            JPanel temp = addExam.getGUI();

            contentPanel.setLayout(new BorderLayout());
            
            
            contentPanel.add(temp, BorderLayout.NORTH);
            contentPanel.revalidate();
        } else if (((JButton) e.getSource()).getText().equals("Activate")) {
            //isPublicButtons
            if(checkbox.length>0)
            {
            Exam_DAO exam_DAO = new Exam_DAO();
            int exam_ID = 0;
            for (int i = 0; i < checkbox.length; i++) {
                if (checkbox[i].isSelected()) {
                    exam_ID = Integer.parseInt((checkbox[i].getName()));
                    exam_DAO.makeItPublic(exam_ID);
            //update the panel
            p2.removeAll();
            setP2();
                }
            }
            
            }
        }

    }

//    @Override
//    public void actionPerformed(ActionEvent e) {
//       if(((JButton)e.getSource()).getText().equals("Edit")){  //Edit Button
//            this.dispose();
//            int exam_ID=Integer.parseInt(((JButton)e.getSource()).getName());
//            Exam exam=new Exam(exam_ID);
//            exam.setVisible(true);
//            
//        }else if(e.getSource()==addExamButton){
//            AddExam addExam=new AddExam();
//            //settingSection.setVisible(true);
//            contentPanel.removeAll();
//            JPanel temp=addExam.getGUI();
//            
//            contentPanel.setLayout(new BorderLayout());
//            contentPanel.add(temp);
//            contentPanel.revalidate();
//        }else if(((JButton)e.getSource()).getText().equals("Activate")){
//            //isPublicButtons
//            Exam_DAO exam_DAO=new Exam_DAO();
//            int exam_ID=Integer.parseInt(((JButton)e.getSource()).getName());
//            exam_DAO.makeItPublic(exam_ID);
//            p2.removeAll();
//            setP2();
//        }
//    }
    public static void main(String[] args) {
        ExamOverview test = new ExamOverview();

        test.setVisible(true);

    }

}
