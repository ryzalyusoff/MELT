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
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
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
public class ExamOverview extends JFrame implements ActionListener,WindowListener {

    public ArrayList<melt.Model.Exam> exams;
    public JLabel[] examLabels;
    JButton addExamButton,backButton;
    public JButton[] isPublicButtons;
    public JCheckBox[] examSelected;
    JPanel contentPanel;
    JPanel ExamListPanel, buttonsPanel, lowerLeftPanel;
    JScrollPane scrollPane;

    public ExamOverview() {
        //this.setLocationRelativeTo(null);  //make window in the center of desktop
        setTitle("MELTSystem--Exam");
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();
        setSize((int)width, (int)height);
        setContentPane(GetGUI());
        JMenuBar jMenuBar1 = new JMenuBar();
        JMenu jMenu1 = new JMenu("Exam");
        jMenuBar1.add(jMenu1);
        getRootPane().setMenuBar(jMenuBar1);
        addWindowListener(this);
    }

    public JPanel GetGUI() {

        //create ExamListPanel
        ExamListPanel = new JPanel();
        SetExamListPanel();

        JButton EditButton = new JButton("Edit");
        EditButton.addActionListener(this);

        JButton isActivatedButton = new JButton("Activate");
        isActivatedButton.addActionListener(this);

        addExamButton = new JButton("Add a Exam");
        addExamButton.addActionListener(this);
        
        backButton=new JButton("<<<Back to Main menu");
        backButton.addActionListener(this);
        
        //Create buttonsPanel
        buttonsPanel = new JPanel();
        buttonsPanel.setMaximumSize(new Dimension(100,1000));
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));
        
        scrollPane=new JScrollPane(ExamListPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        buttonsPanel.add(backButton);
        buttonsPanel.add(scrollPane);
        
        JPanel pTempOuter = new JPanel(new BorderLayout());
        pTempOuter.setBackground(new Color(153, 153, 153));
        JPanel pTemp = new JPanel(new GridLayout(3, 1));
        pTempOuter.setMaximumSize(new Dimension(100,200));
        pTemp.setMaximumSize(new Dimension(100,200));
        pTemp.setBackground(new Color(153, 153, 153));
        
        pTemp.add(EditButton);
        pTemp.add(isActivatedButton);
        pTemp.add(addExamButton);
        
        pTempOuter.setAlignmentX( Component.LEFT_ALIGNMENT );
        pTemp.setAlignmentX( Component.LEFT_ALIGNMENT );
        buttonsPanel.setAlignmentX( Component.LEFT_ALIGNMENT );
        
       // pTempOuter.add(pTemp);
        buttonsPanel.add(pTemp);
        

        //create lowerLeftPanel
        lowerLeftPanel = new JPanel();
        lowerLeftPanel.setLayout(new BoxLayout(lowerLeftPanel, BoxLayout.X_AXIS));
        lowerLeftPanel.add(buttonsPanel);
        contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());
        lowerLeftPanel.add(contentPanel);
        
        //set the color of the left part
        ExamListPanel.setBackground(new Color(153, 153, 153));
        buttonsPanel.setBackground(new Color(153, 153, 153));

        buttonsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(""), "Exam Overview"));

        return lowerLeftPanel;
    }

    private void SetExamListPanel() {

        GetExams();

        isPublicButtons = new JButton[exams.size()];
        examSelected = new JCheckBox[exams.size()];

        examLabels = new JLabel[exams.size()];

        for (int i = 1; i < exams.size() + 1; i++) {
            melt.Model.Exam exam = exams.get(i - 1);

            examLabels[i - 1] = new JLabel("Exam" + i);
            //Checkboxes
            examSelected[i - 1] = new JCheckBox();
            examSelected[i - 1].setName(exam.getExam_ID() + "");
            examSelected[i - 1].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    JCheckBox cbLog = (JCheckBox) e.getSource();

                    if (cbLog.isSelected()) {
                        for (int i = 0; i < examSelected.length; i++) {
                            if (!(examSelected[i] == e.getSource())) {
                                examSelected[i].setEnabled(false);
                            }
                        }
                    } else {
                        for (int i = 0; i < examSelected.length; i++) {
                            examSelected[i].setEnabled(true);
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

        groupLayout = new GroupLayout(ExamListPanel);

        groupLayout.setAutoCreateContainerGaps(
                true);
        groupLayout.setAutoCreateGaps(
                true);

        horizontalGroup_P = groupLayout.createParallelGroup();
        verticalGroup_S = groupLayout.createSequentialGroup();

        for (int i = 0;
                i < exams.size();
                i++) {
//            ExamListPanel.add(sectionLabels[i]);
//            ExamListPanel.add(buttons[i]);
//            ExamListPanel.add(deleteButtons[i]);
            horizontalGroup_P.addGroup(groupLayout.createSequentialGroup()
                    .addComponent(examSelected[i])
                    .addComponent(examLabels[i])
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                    .addComponent(isPublicButtons[i]));

            verticalGroup_S.addGroup(groupLayout.createParallelGroup()
                    .addComponent(examSelected[i])
                    .addComponent(examLabels[i])
                    .addComponent(isPublicButtons[i])
                    .addGap(40));
        }

        groupLayout.setHorizontalGroup(horizontalGroup_P);

        groupLayout.setVerticalGroup(verticalGroup_S);

        ExamListPanel.setLayout(groupLayout);

        ExamListPanel.revalidate();
        //p2.repaint();

    }
    /**
     * GetExams from database
     */
    private void GetExams() {
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
            if(examSelected.length>0)
            {
            
            int exam_ID = 0;
            for (int i = 0; i < examSelected.length; i++) {
                if (examSelected[i].isSelected()) {
                    //this.dispose();
                    exam_ID = Integer.parseInt((examSelected[i].getName()));
                    Exam exam = new Exam(exam_ID);
                    JFrame fatherFrame=(JFrame)ExamListPanel.getRootPane().getParent();
                    fatherFrame.setContentPane(exam.GetGUI());
                    fatherFrame.revalidate();
                    fatherFrame.repaint();
            //exam.setVisible(true);
                }
            }
        }
            
        

        }else if(e.getSource()==backButton){
            this.dispose();
            melt.View.startupPanel startupPanel=new melt.View.startupPanel();
            startupPanel.setVisible(true);
        }else if (e.getSource() == addExamButton) {
            AddExam addExam = new AddExam();
            //settingSection.setVisible(true);
            contentPanel.removeAll();
            JPanel temp = addExam.GetGUI();

            contentPanel.setLayout(new BorderLayout());
            
            
            contentPanel.add(temp, BorderLayout.NORTH);
            contentPanel.revalidate();
        } else if (((JButton) e.getSource()).getText().equals("Activate")) {
            //isPublicButtons
            if(examSelected.length>0)
            {
            Exam_DAO exam_DAO = new Exam_DAO();
            int exam_ID = 0;
            for (int i = 0; i < examSelected.length; i++) {
                if (examSelected[i].isSelected()) {
                    exam_ID = Integer.parseInt((examSelected[i].getName()));
                    exam_DAO.makeItPublic(exam_ID);
            //update the panel
            ExamListPanel.removeAll();
            SetExamListPanel();
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
//            JPanel temp=addExam.GetGUI();
//            
//            contentPanel.setLayout(new BorderLayout());
//            contentPanel.add(temp);
//            contentPanel.revalidate();
//        }else if(((JButton)e.getSource()).getText().equals("Activate")){
//            //isPublicButtons
//            Exam_DAO exam_DAO=new Exam_DAO();
//            int exam_ID=Integer.parseInt(((JButton)e.getSource()).getName());
//            exam_DAO.makeItPublic(exam_ID);
//            ExamListPanel.removeAll();
//            SetExamListPanel();
//        }
//    }
    public static void main(String[] args) {
        ExamOverview test = new ExamOverview();

        test.setVisible(true);

    }

    @Override
    public void windowOpened(WindowEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowClosing(WindowEvent e) {
        dispose();
        new melt.View.startupPanel().setVisible(true);
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowClosed(WindowEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowIconified(WindowEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowActivated(WindowEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
