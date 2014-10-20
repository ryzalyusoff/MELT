/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package melt.View;

import java.awt.BorderLayout;
import java.awt.ScrollPane;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.GroupLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import melt.DAO.*;
import melt.Model.Section;

/**
 *
 * @author eddychou
 */
public class ExamPreview {
    private int exam_ID;
    private ArrayList<Integer> section_IDList;
    
    public ExamPreview(int exam_ID){
        this.exam_ID=exam_ID;
        getGUI();
    }
    private void getExam(){
        try {
            section_IDList=new ArrayList<Integer>();
            Section_DAO section_DAO=new Section_DAO();
            ResultSet rs=section_DAO.getList("exam_ID='"+exam_ID+"'");
            while (rs.next()) {
                section_IDList.add(rs.getInt("Section_ID"));
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(ExamPreview.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public JScrollPane getGUI(){
        JPanel p1=new JPanel();
        
        getExam();
        
        GroupLayout groupLayout=new GroupLayout(p1);
        groupLayout.setAutoCreateContainerGaps(true);
        groupLayout.setAutoCreateGaps(true);
        GroupLayout.ParallelGroup horizontalGroup_P = groupLayout.createParallelGroup(GroupLayout.Alignment.CENTER);
        GroupLayout.SequentialGroup verticalGroup_S = groupLayout.createSequentialGroup();
        
        for (int i = 0; i < section_IDList.size(); i++) {
            SectionPanel sectionPanel=new SectionPanel(exam_ID);
            sectionPanel.getGUI(section_IDList.get(i));
            
            horizontalGroup_P.addComponent(sectionPanel);
            verticalGroup_S.addComponent(sectionPanel);
            
        }
     
        
        
        
                

        groupLayout.setHorizontalGroup(horizontalGroup_P);
        groupLayout.setVerticalGroup(verticalGroup_S);
        p1.setLayout(groupLayout);
        JScrollPane scrollPane=new JScrollPane(p1);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        
        return scrollPane;

    }
}
