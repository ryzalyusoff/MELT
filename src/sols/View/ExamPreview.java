/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sols.View;


import java.util.ArrayList;
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
    private ArrayList<Section>  sections;
    
    public ExamPreview(int exam_ID){
        this.exam_ID=exam_ID;
        getGUI();
    }
    private void getExam(){
        
        sections=new ArrayList<Section>();
        Section_DAO section_DAO=new Section_DAO();
        sections=section_DAO.getList("exam_ID='"+exam_ID+"'");
            
        
    }
    
    public JScrollPane getGUI(){
        JPanel p1=new JPanel();
        
        getExam();
        
        GroupLayout groupLayout=new GroupLayout(p1);
        groupLayout.setAutoCreateContainerGaps(true);
        groupLayout.setAutoCreateGaps(true);
        GroupLayout.ParallelGroup horizontalGroup_P = groupLayout.createParallelGroup(GroupLayout.Alignment.CENTER);
        GroupLayout.SequentialGroup verticalGroup_S = groupLayout.createSequentialGroup();
        
        for (int i = 0; i < sections.size(); i++) {
            SectionPanel_Preview sectionPanel=new SectionPanel_Preview(exam_ID);
            sectionPanel.getGUI(((Section)sections.get(i)).getSection_ID());
            
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
