/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package melt.View;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * JFrame class for adding a section
 * @author eddychou
 */
public class AddSection extends JFrame implements ActionListener,WindowListener{
    
    public JLabel l1,l2,l3,l4,l5;
    public JTextField tf1,tf3_h,tf3_m,tf3_s;
    public JComboBox cb1;
    public JButton button1,button2;
    public JPanel p1;
    public int exam_ID;
    
    public AddSection(int exam_ID) {
        this.exam_ID=exam_ID;
        this.setLocationRelativeTo(null);  //make window in the center of desktop
        setSize(500, 150);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(this);
        setContentPane(getGUI());
        
    }
    
    /**
     *get the contentPane
     * @return
     */
    public JPanel getGUI(){
        
        
        //Create p1
        p1=new JPanel();
        
        l1=new JLabel("Section Name");
        l2=new JLabel("Time Limit (hh:mm:ss)");
        l3=new JLabel("The Number of Subsections");
        l4=new JLabel(":");
        l5=new JLabel(":"); 
        tf1=new JTextField();
        tf3_h=new JTextField("00");
        tf3_m=new JTextField("00");
        tf3_s=new JTextField("00");
        cb1=new JComboBox();
        cb1.addItem("0");
        cb1.addItem("1");
        cb1.addItem("2");
        button1=new JButton("Cancel");
        button2=new JButton("   OK   ");
        
        button1.addActionListener(this);
        button2.addActionListener(this);
        

        //Create grouplayout and horizontalGroup and VerticalGroup
        GroupLayout groupLayout;
        GroupLayout.SequentialGroup horizontalGroup_S,verticalGroup_S; 
        
        groupLayout=new GroupLayout(p1);
        //set auto Gaps
        groupLayout.setAutoCreateContainerGaps(true);
        groupLayout.setAutoCreateGaps(true);
        
        horizontalGroup_S=groupLayout.createSequentialGroup();
        verticalGroup_S=groupLayout.createSequentialGroup();
        //set horizontalGroup and VerticalGroup
        horizontalGroup_S.addGroup(groupLayout.createParallelGroup()
                            .addComponent(l1)
                            .addComponent(l2)
                            .addComponent(l3))
                        .addGroup(groupLayout.createParallelGroup()
                            .addComponent(tf1)
                            .addGroup(groupLayout.createSequentialGroup()
                                .addComponent(tf3_h)
                                .addComponent(l4)
                                .addComponent(tf3_m)
                                .addComponent(l5)
                                .addComponent(tf3_s))
                            .addComponent(cb1))
                        .addGroup(groupLayout.createParallelGroup()
                            .addComponent(button2)
                            .addComponent(button1));
        verticalGroup_S.addGroup(groupLayout.createParallelGroup()
                            .addComponent(l1)
                            .addComponent(tf1)
                            .addComponent(button2))
                        .addGroup(groupLayout.createParallelGroup()
                            .addComponent(l2)
                            .addGroup(groupLayout.createParallelGroup()
                                .addComponent(tf3_h)
                                .addComponent(l4)
                                .addComponent(tf3_m)
                                .addComponent(l5)
                                .addComponent(tf3_s))
                            .addComponent(button1))
                        .addGroup(groupLayout.createParallelGroup()
                            .addComponent(l3)
                            .addComponent(cb1));
                                                
                           
        //set grouplayout
        groupLayout.setHorizontalGroup(horizontalGroup_S);
        groupLayout.setVerticalGroup(verticalGroup_S);
        //set p1's layout
        p1.setLayout(groupLayout);
        
        return p1;
        
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==button1) {//cancel button
            //Exam exam=new Exam();
            //exam.setVisible(true);
            //this.dispose();
            p1.setVisible(false);
            p1.getParent().remove(p1);
            p1.invalidate();
            
        }else if (e.getSource()==button2) {//ok button
            //get every attribute needed for the exam
            int timeLimit_h=0;
            int timeLimit_m=0;
            int timeLimit_s=0;
            String sectionName=tf1.getText();
            if(sectionName.trim().isEmpty())
            {JOptionPane.showMessageDialog(p1, "Please enter a Section title", "Incorrect Section title", JOptionPane.ERROR_MESSAGE);
            return ;}
            try{
            timeLimit_h=Integer.parseInt(tf3_h.getText());
            timeLimit_m=Integer.parseInt(tf3_m.getText());
            timeLimit_s=Integer.parseInt(tf3_s.getText());
            }catch(Exception exception){JOptionPane.showMessageDialog(p1, "Please enter a correct time format (ex. 00:30:00)", "Incorrect time value", JOptionPane.ERROR_MESSAGE);
            return ;}
            if(timeLimit_h >24 || timeLimit_h <00 || timeLimit_m > 60 || timeLimit_m < 0 || timeLimit_s > 60 || timeLimit_s <0 || (timeLimit_h == 0 && timeLimit_m == 0 && timeLimit_s == 0))
            {JOptionPane.showMessageDialog(p1, "Please enter a correct time format (ex. 00:30:00)", "Incorrect time value", JOptionPane.ERROR_MESSAGE);
            return;}
            int numOfSub=cb1.getSelectedIndex();
            
            //get settingExamPanel and put it into right part of the frame
            SettingExam settingExamFrame=new SettingExam(sectionName, timeLimit_h, timeLimit_m, timeLimit_s, numOfSub,exam_ID);
            //settingExamFrame.setVisible(true);
            JPanel parentJpanel=(JPanel)p1.getParent();
            parentJpanel.remove(p1);
            parentJpanel.setLayout(new BorderLayout());
            parentJpanel.add(settingExamFrame.GetGUI());
            parentJpanel.revalidate();
            //parentJpanel.repaint();
            
            this.dispose();
        }
    }

    @Override
    public void windowOpened(WindowEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowClosing(WindowEvent e) {
        this.dispose();
//        Exam exam=new Exam(1);
//        exam.setVisible(true);
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
