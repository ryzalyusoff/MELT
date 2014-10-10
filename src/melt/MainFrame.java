/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package melt;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import melt.View.Exam;

/**
 *
 * @author eddychou
 */
public class MainFrame extends JFrame{
    public JPanel contentPanel=new JPanel();
    public JPanel contentPanel2=new JPanel();
    public MainFrame(String frameName) {
        setTitle(frameName);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setContentPane(contentPanel);
        getGUI();
        JMenuBar jMenuBar1=new JMenuBar();
        JMenu jMenu1=new JMenu("ddd");
        jMenuBar1.add(jMenu1);
        getRootPane().setMenuBar(jMenuBar1);
    }
    
    public void getGUI(){
        JMenuBar jMenuBar1=new JMenuBar();
        JMenu jMenu1=new JMenu("ddd");
        jMenu1.setPreferredSize(new Dimension(10, 10));
        jMenuBar1.add(jMenu1);
        jMenuBar1.setPreferredSize(new Dimension(10, 10));
        //JPanel contentPanel=new JPanel();
//        Exam exam=new Exam();
//        contentPanel.set
//        JPanel leftPanel=exam.getGUI();
//        contentPanel.add(leftPanel);
        contentPanel.setLayout(new BorderLayout());
        Exam exam=new Exam(1);
        JPanel leftPanel=exam.getGUI();
        leftPanel.setPreferredSize(new Dimension(100, 100));
        contentPanel.add(exam.getGUI(),BorderLayout.WEST);
        contentPanel2.setBackground(Color.red);
        contentPanel.add(contentPanel2,BorderLayout.EAST);
        
//        GroupLayout groupLayout=new GroupLayout(getContentPane());
//        GroupLayout.ParallelGroup verticalGroup_P,horizontalGroup_P;
//        
//        horizontalGroup_P=groupLayout.createParallelGroup();
//        verticalGroup_P=groupLayout.createParallelGroup();
//        Exam exam=new Exam();
//        
//        horizontalGroup_P.addGroup(groupLayout.createSequentialGroup()
//                .addComponent(exam.getGUI(), javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
//                
//                );
//                
//        
//        verticalGroup_P.addComponent(exam.getGUI(), javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE);
//        
//        groupLayout.setHorizontalGroup(horizontalGroup_P);
//        groupLayout.setVerticalGroup(verticalGroup_P);
//        
//        this.setLayout(groupLayout);
        //return contentPanel;
    
    }
    public static void main(String[] args) {
        MainFrame mainFrame=new MainFrame("MainFrame");
        mainFrame.setVisible(true);
    }
    
}
