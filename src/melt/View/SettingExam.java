/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package melt.View;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import melt.DAO.Section_DAO;
import melt.Model.*;

/**
 * Frame for setting the Exam
 * @author Aote Zhou
 */
public class SettingExam extends JFrame implements ActionListener,WindowListener {

    public String sectionName;
    public int timeLimit_h,timeLimit_m,timeLimit_s,numOfSub;
    public JButton submitButton,updateButton,cancelButton,previewButton;
    public SectionPanel sectionPanel;
    public boolean isUpdate; 
    JScrollPane scrollPane;
    int examId;

    /**
     * when a new Exam is going to be set
     * @param sectionName
     * @param timeLimit_h
     * @param timeLimit_m
     * @param timeLimit_s
     * @param numOfSub
     * @param examId
     */
    public SettingExam(String sectionName,int timeLimit_h,int timeLimit_m, int timeLimit_s,int numOfSub,int examId)  {
        
        this.examId=examId;
        isUpdate=false;
        this.setLocationRelativeTo(null);  //make window in the center of desktop
        setSize(640, 500);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(this);
        
        this.sectionName=sectionName;
        this.timeLimit_h=timeLimit_h;
        this.timeLimit_m=timeLimit_m;
        this.timeLimit_s=timeLimit_s;
        this.numOfSub=numOfSub;
        
        sectionPanel=new SectionPanel(numOfSub,examId);
        sectionPanel.getGUI(sectionName,timeLimit_h,timeLimit_m,timeLimit_s);
        
        setContentPane(getGUI());
        
        
    }

    /**
     *when an old exam is going to be edited
     * @param sectionId
     */
    public SettingExam(int sectionId)  {
        isUpdate=true;//Edit or Update Mode
        this.setLocationRelativeTo(null);  //make window in the center of desktop
        setSize(640, 500);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(this);

        Section_DAO section_DAO=new Section_DAO();
        Section section=section_DAO.getModel(sectionId);
        this.examId=section.getExam_ID();
        
        sectionPanel=new SectionPanel(examId);
        sectionPanel.getGUI(sectionId);
        
        setContentPane(getGUI());
        
    }
    
    /**
     *get the contentpanel
     * @return
     */
    public JScrollPane getGUI() {
        JPanel p999;
        
        GroupLayout groupLayout;
        GroupLayout.ParallelGroup horizontalGroup_P;
        GroupLayout.SequentialGroup verticalGroup_S;

        
        submitButton=new JButton("Create Section");
        submitButton.addActionListener(this);
        updateButton=new JButton("Update Section");
        updateButton.addActionListener(this);
        cancelButton=new JButton("Cancel");
        cancelButton.addActionListener(this);
        previewButton=new JButton("Preview");
        previewButton.addActionListener(this);

        
        p999 = new JPanel();
         
        groupLayout = new GroupLayout(p999);
        groupLayout.setAutoCreateContainerGaps(true);
        groupLayout.setAutoCreateGaps(true);
        horizontalGroup_P = groupLayout.createParallelGroup(GroupLayout.Alignment.CENTER);
        verticalGroup_S = groupLayout.createSequentialGroup();
     
        horizontalGroup_P.addComponent(sectionPanel);
                
        
                

        verticalGroup_S.addComponent(sectionPanel);
        
        //when update shows the update button and when add shows the submit button
        if (isUpdate) {
            horizontalGroup_P.addGroup(groupLayout.createSequentialGroup()
                    .addComponent(previewButton)
                    .addComponent(updateButton)
                    .addComponent(cancelButton));
            verticalGroup_S.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                    .addComponent(previewButton)
                    .addComponent(updateButton)
                    .addComponent(cancelButton));
        }else{
            horizontalGroup_P.addGroup(groupLayout.createSequentialGroup()
                    .addComponent(previewButton)
                    .addComponent(submitButton)
                    .addComponent(cancelButton));
            verticalGroup_S.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                    .addComponent(previewButton)
                    .addComponent(submitButton)
                    .addComponent(cancelButton));
        }
                

        groupLayout.setHorizontalGroup(horizontalGroup_P);
        groupLayout.setVerticalGroup(verticalGroup_S);
        p999.setLayout(groupLayout);

        /*add p999 to a jscrollpane so that there will 
        **have a scroll bar when we cannot see all thw content
        */
        scrollPane=new JScrollPane(p999);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        
        
        
        return scrollPane;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==submitButton) {
            if (sectionPanel.submitSection()) {//if submit succeed
                JFrame rootFrame=(JFrame)scrollPane.getRootPane().getParent();
                //rootFrame.dispose();
                //this.dispose();
                Exam exam=new Exam(examId);
                rootFrame.setContentPane(exam.getGUI());
                rootFrame.revalidate();
                rootFrame.repaint();
                //exam.setVisible(true);
            }

            
        }else if (e.getSource()==updateButton) {
            if (sectionPanel.updateSection()) {//if update succeed
              
                JFrame rootFrame=(JFrame)scrollPane.getRootPane().getParent();
                //rootFrame.dispose();
                //this.dispose();
                Exam exam=new Exam(examId);
                rootFrame.setContentPane(exam.getGUI());
                rootFrame.revalidate();
                rootFrame.repaint();
                //exam.setVisible(true);
            }
        }else if (e.getSource()==cancelButton) {
            //remove current panel when calbutton
            scrollPane.setVisible(false);
            scrollPane.getParent().remove(scrollPane);
            scrollPane.invalidate();
                
              
        }else if (e.getSource()==previewButton) {
            //remove current panel when calbutton
            ExamPreview examPreview=new ExamPreview(examId,sectionPanel);
            examPreview.setVisible(true);
                
              
        }
    }

    public static void main(String[] args) {
        SettingExam p = new SettingExam("hhhh", 30, 0, 0, 0,1);
        
        
        p.setVisible(true);
    }

    @Override
    public void windowOpened(WindowEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowClosing(WindowEvent e) {
        this.dispose();
        Exam exam=new Exam(1);
        exam.setVisible(true);
        System.out.println("Closing!");
    }

    @Override
    public void windowClosed(WindowEvent e) {
        System.out.println("Closed!");
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








