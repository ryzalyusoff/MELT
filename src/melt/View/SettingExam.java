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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import melt.DAO.Section_DAO;
import melt.Model.*;

/**
 *
 * @author Aote Zhou
 */
public class SettingExam extends JFrame implements ActionListener,WindowListener {

    public String sectionName;
    public int timeLimit_h,timeLimit_m,timeLimit_s,numOfSub;
    public JButton submitButton,updateButton,cancelButton;
    public SectionPanel sectionPanel1;
    public boolean isUpdate; 
    JScrollPane p1000;
    int exam_ID;

    

    public SettingExam(String sectionName,int timeLimit_h,int timeLimit_m, int timeLimit_s,int numOfSub,int exam_ID)  {
        
        this.exam_ID=exam_ID;
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
        
        sectionPanel1=new SectionPanel(numOfSub);
        sectionPanel1.getGUI(sectionName,timeLimit_h,timeLimit_m,timeLimit_s);
        
        setContentPane(getGUI());
        
        
    }
    public SettingExam(int section_ID)  {
        isUpdate=true;//Edit or Update Mode
        this.setLocationRelativeTo(null);  //make window in the center of desktop
        setSize(640, 500);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(this);

        sectionPanel1=new SectionPanel();
        sectionPanel1.getGUI(section_ID);
        
        Section_DAO section_DAO=new Section_DAO();
        Section section=section_DAO.getModel(section_ID);
        this.exam_ID=section.getExam_ID();
        
        setContentPane(getGUI());
        
    }
    

    public JScrollPane getGUI() {
        JPanel p999;
        
        GroupLayout groupLayout;
        GroupLayout.ParallelGroup horizontalGroup_P;
        GroupLayout.SequentialGroup verticalGroup_S;

        
        submitButton=new JButton("submit");
        submitButton.addActionListener(this);
        updateButton=new JButton("update");
        updateButton.addActionListener(this);
        cancelButton=new JButton("cancel");
        cancelButton.addActionListener(this);

        
        p999 = new JPanel();
         
        groupLayout = new GroupLayout(p999);
        groupLayout.setAutoCreateContainerGaps(true);
        groupLayout.setAutoCreateGaps(true);
        horizontalGroup_P = groupLayout.createParallelGroup(GroupLayout.Alignment.CENTER);
        verticalGroup_S = groupLayout.createSequentialGroup();
     
        horizontalGroup_P.addComponent(sectionPanel1);
                
        
                

        verticalGroup_S.addComponent(sectionPanel1);
        if (isUpdate) {
            horizontalGroup_P.addGroup(groupLayout.createSequentialGroup()
                    .addComponent(updateButton)
                    .addComponent(cancelButton));
            verticalGroup_S.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                    .addComponent(updateButton)
                    .addComponent(cancelButton));
        }else{
            horizontalGroup_P.addGroup(groupLayout.createSequentialGroup()
                    .addComponent(submitButton)
                    .addComponent(cancelButton));
            verticalGroup_S.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                    .addComponent(submitButton)
                    .addComponent(cancelButton));
        }
                

        groupLayout.setHorizontalGroup(horizontalGroup_P);
        groupLayout.setVerticalGroup(verticalGroup_S);
        p999.setLayout(groupLayout);

        /*add p999 to a jscrollpane so that there will 
        **have a scroll bar when we cannot see all thw content
        */
        p1000=new JScrollPane(p999);
        p1000.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        p1000.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        
        
        
        return p1000;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==submitButton) {
            if (sectionPanel1.submitSection()) {
                JFrame rootFrame=(JFrame)p1000.getRootPane().getParent();
                rootFrame.dispose();
                //this.dispose();
                Exam exam=new Exam(exam_ID);
                exam.setVisible(true);
            }

            
        }else if (e.getSource()==updateButton) {
            if (sectionPanel1.updateSection()) {
              
                JFrame rootFrame=(JFrame)p1000.getRootPane().getParent();
                rootFrame.dispose();
                //this.dispose();
                Exam exam=new Exam(exam_ID);
                exam.setVisible(true);
            }
        }else if (e.getSource()==cancelButton) {
            p1000.setVisible(false);
            p1000.getParent().remove(p1000);
            p1000.invalidate();
                
              
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








