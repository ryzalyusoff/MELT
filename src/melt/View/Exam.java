/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package melt.View;

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
import melt.DAO.Section_DAO;
import melt.Model.*;

/**
 *
 * @author Aote Zhou
 */

public class Exam extends JFrame implements ActionListener{

    public JLabel welcomeLabel, timeLabel;
    public JButton addSectionButton;
    public ArrayList<Section> sections;
    public JLabel[] sectionLabels;
    public JButton[] buttons,deleteButtons;

    public Exam() {
        setTitle("MELTSystem--Test");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        setContentPane(getGUI());
    }

    public JPanel getGUI() {
        JPanel p1, p2, p3, p4;

        welcomeLabel = new JLabel("....,Welcome!");
        timeLabel = new JLabel("dd-mm-yyyy hh:mm");

        getSections(1);
        
        buttons = new JButton[sections.size()];
        deleteButtons = new JButton[sections.size()];
        
        addSectionButton = new JButton("Add Section");
        addSectionButton.addActionListener(this);
        

        sectionLabels=new JLabel[sections.size()];
        for (int i = 1; i < sections.size() + 1; i++) {
            Section section=sections.get(i-1);
            String sectionTimeLimit=new SimpleDateFormat("HH:mm:ss").format(section.getTimeLimit());
            
            sectionLabels[i - 1] = new JLabel("Section" + i + "  " + section.getSection_Name()+"  time:"+sectionTimeLimit);
            //Edit Button
            buttons[i - 1] = new JButton("Edit");
            buttons[i-1].setName(section.getSection_ID()+"");
            buttons[i-1].addActionListener(this);
            //Delete Button
            deleteButtons[i - 1] = new JButton("Delete");
            deleteButtons[i-1].setName(section.getSection_ID()+"");
            deleteButtons[i-1].addActionListener(this);
            
            

        }
        // create p1
        p1 = new JPanel();
        p1.setLayout(new GridLayout(1, 3));
        p1.add(welcomeLabel);
        p1.add(new JPanel());
        p1.add(timeLabel);

        //create p2
        p2 = new JPanel();
        //p2.setLayout(new GridLayout(sections.size(),3));
        GroupLayout groupLayout;
        GroupLayout.ParallelGroup horizontalGroup_P;
        GroupLayout.SequentialGroup verticalGroup_S;
        
        groupLayout=new GroupLayout(p2);
        groupLayout.setAutoCreateContainerGaps(true);
        groupLayout.setAutoCreateGaps(true);
        
        horizontalGroup_P=groupLayout.createParallelGroup();
        verticalGroup_S=groupLayout.createSequentialGroup();
        
        
        
        for (int i = 0; i < sections.size(); i++) {
//            p2.add(sectionLabels[i]);
//            p2.add(buttons[i]);
//            p2.add(deleteButtons[i]);
            horizontalGroup_P.addGroup(groupLayout.createSequentialGroup()
                    .addComponent(sectionLabels[i])
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                    .addComponent(buttons[i])
                    .addComponent(deleteButtons[i]));
            verticalGroup_S.addGroup(groupLayout.createParallelGroup()
                    .addComponent(sectionLabels[i])
                    .addComponent(buttons[i])
                    .addComponent(deleteButtons[i]));
        }
        groupLayout.setHorizontalGroup(horizontalGroup_P);
        groupLayout.setVerticalGroup(verticalGroup_S);
        
        p2.setLayout(groupLayout);

        //Create p3
        p3 = new JPanel();
        //p3.add(new JLabel("Overall   6.0/30.0"));
        p3.add(addSectionButton);

        //Create p4
        p4 = new JPanel();
        p4.setLayout(new BoxLayout(p4, BoxLayout.Y_AXIS));
        //p4.add(p1);
        JScrollPane jspane=new JScrollPane(p2);
        jspane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        p4.add(jspane);
        p4.add(p3);

        return p4;
    }

    private void getSections(int exam_ID) {
        try {
            Section_DAO section_DAO = new Section_DAO();
            ResultSet rs = section_DAO.getList("Exam_ID='" + exam_ID + "'");

            sections = new ArrayList<Section>();
            while (rs.next()) {
                Section section = new Section();
                section.setExam_ID(exam_ID);
                section.setSection_ID(rs.getInt("Section_ID"));
                section.setSection_Name(rs.getString("Section_Name"));
                section.setTimeLimit(new SimpleDateFormat("HH:mm:ss").parse(rs.getString("TimeLimit")));
                sections.add(section);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Exam.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(Exam.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==addSectionButton) {
            this.dispose();
            SettingSection settingSection=new SettingSection();
            settingSection.setVisible(true);
            
        }else if (((JButton)e.getSource()).getText().equals("Delete")) {
            // wait for complete
            
        }else{  //Edit Button
            this.dispose();
            int section_ID=Integer.parseInt(((JButton)e.getSource()).getName());
            SettingExam settingExam=new SettingExam(section_ID);
            settingExam.setVisible(true);
            
        }
    }
    public static void main(String[] args) {
        Exam test = new Exam();
        test.setTitle("MELTSystem--Test");
        test.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        test.setSize(400, 400);
        test.setContentPane(test.getGUI());
        test.setVisible(true);

    }

}
