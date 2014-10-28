/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package melt.View;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle;
import melt.DAO.Exam_DAO;

/**
 * JFrame class for adding a exam can also use getGUI to return its jpanel
 * @author eddychou
 */
public class AddExam extends JFrame implements ActionListener,WindowListener{
    
    public JLabel instructions;
    public JTextArea instructionsText;
    public JComboBox examChoice;
    public JButton cancelButton,okButton;
    public JPanel examPanel;
    

    public AddExam() {
        this.setLocationRelativeTo(null);  //make window in the center of desktop
        setSize(400, 150);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(this);
        setContentPane(getGUI());
    }
    
    /**
     *create the contentPane
     * @return
     */
    public JPanel getGUI(){
        
        
        //Create p1
        examPanel=new JPanel();
        
        instructions=new JLabel("Instructions");
        instructionsText=new JTextArea();
        JScrollPane sp = new JScrollPane(instructionsText);
        instructionsText.setMinimumSize(new Dimension(500, 100));
        sp.setMinimumSize(new Dimension(500, 500)); 
        cancelButton=new JButton("Cancel");
        okButton=new JButton("   OK   ");
        
        cancelButton.addActionListener(this);
        okButton.addActionListener(this);
        

        
        GroupLayout groupLayout;
        GroupLayout.ParallelGroup horizontalGroup_P;
        GroupLayout.SequentialGroup verticalGroup_S; 
        
        groupLayout=new GroupLayout(examPanel);
        //set suto gaps in the grouplayout
        groupLayout.setAutoCreateContainerGaps(true);
        groupLayout.setAutoCreateGaps(true);
        
        //create horizontalGruop and verticalGroup
        horizontalGroup_P=groupLayout.createParallelGroup();
        verticalGroup_S=groupLayout.createSequentialGroup();
        
        //Set horizontalGroup and verticalGroup
        horizontalGroup_P.addGroup(groupLayout.createSequentialGroup()
                            .addComponent(instructions)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED,10,Short.MAX_VALUE))
                        .addComponent(sp)
                        .addGroup(groupLayout.createSequentialGroup()
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                            .addComponent(cancelButton)
                            .addComponent(okButton));
        verticalGroup_S.addGroup(groupLayout.createParallelGroup()
                            .addComponent(instructions))
                .addComponent(sp)
                .addGroup(groupLayout.createParallelGroup()
                            .addComponent(cancelButton)
                            .addComponent(okButton));
                                                
                           
        //set grouplayout
        groupLayout.setHorizontalGroup(horizontalGroup_P);
        groupLayout.setVerticalGroup(verticalGroup_S);
        //set p1's layout
        examPanel.setLayout(groupLayout);
        
        return examPanel;
        
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==cancelButton) {//cancel button
            //Exam exam=new Exam();
            //exam.setVisible(true);
            //this.dispose();
            examPanel.setVisible(false);
            examPanel.getParent().remove(examPanel);
            examPanel.invalidate();
            
        }else if (e.getSource()==okButton) {//submit button
            
            //parentJpanel.repaint();
            melt.Model.Exam exam =new melt.Model.Exam();
            exam.setInstructions(instructionsText.getText());
            exam.setIsPublic(false);
            
            //initialize Exam_DAO and add exam into database
            Exam_DAO exam_DAO=new Exam_DAO();
            exam_DAO.add(exam);
            //close the parentFrame and reopen it
            JFrame fatherFrame=(JFrame)examPanel.getRootPane().getParent();
            //fatherFrame.dispose();
            Exam examPanel=new Exam();
            fatherFrame.setContentPane(examPanel.getGUI());
            fatherFrame.revalidate();
            fatherFrame.repaint();
            //examOverview.setVisible(true);
        }
    }

    @Override
    public void windowOpened(WindowEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowClosing(WindowEvent e) {
        this.dispose();
//        Exam exam=new Exam();
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
    
    
    public static void main(String[] args) {
        AddExam addExam=new AddExam();
        addExam.setVisible(true);
    }
}
