/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package melt.View;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import javax.swing.GroupLayout;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import melt.DAO.*;
import melt.Model.Section;

/**
 *
 * @author eddychou
 */
public class ExamPreview extends JDialog implements WindowListener{
    private int exam_ID;
    private SectionPanel sectionPanel;
    private JPanel fatherJPanel;
    private ArrayList<Section>  sections;
    
    public ExamPreview(int exam_ID){
        this.exam_ID=exam_ID;
        getGUI();
    }
    /**
     *
     * @param exam_ID
     * @param section_ID the section_ID cannot same as db
     */
    public ExamPreview(int exam_ID,melt.View.SectionPanel sectionPanel){
        this.exam_ID=exam_ID;
        this.sectionPanel=sectionPanel;
        fatherJPanel=(JPanel)sectionPanel.getParent();
        setTitle("MELTSystem");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();
        setSize((int) width, (int) height);
        setContentPane(getGUI(sectionPanel));
        addWindowListener(this);
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
    public JPanel getGUI(melt.View.SectionPanel sectionPanel){
        JPanel p1=new JPanel();
        JPanel p2=new JPanel();
        p2.setLayout(new BorderLayout());
        
        getExam();
        
        GroupLayout groupLayout=new GroupLayout(p1);
        groupLayout.setAutoCreateContainerGaps(true);
        groupLayout.setAutoCreateGaps(true);
        GroupLayout.ParallelGroup horizontalGroup_P = groupLayout.createParallelGroup(GroupLayout.Alignment.CENTER);
        GroupLayout.SequentialGroup verticalGroup_S = groupLayout.createSequentialGroup();
        
        boolean isNewSec=true;//not a new sec->replace is a new sec->append
        for (int i = 0; i < sections.size(); i++) {
            melt.View.SectionPanel_Preview sectionPanel_Preview=new melt.View.SectionPanel_Preview(exam_ID);
            int sectionID=((melt.Model.Section)sections.get(i)).getSection_ID();
            if(sectionID==sectionPanel.section.getSection_ID()){
                horizontalGroup_P.addComponent(sectionPanel);
                verticalGroup_S.addComponent(sectionPanel);
                isNewSec=false;
            }else{
                sectionPanel_Preview.getGUI(sectionID);
                horizontalGroup_P.addComponent(sectionPanel_Preview);
                verticalGroup_S.addComponent(sectionPanel_Preview);
            }
            
            
        }
        if (isNewSec) {
            horizontalGroup_P.addComponent(sectionPanel);
            verticalGroup_S.addComponent(sectionPanel);
        }
     
        
        
        
                

        groupLayout.setHorizontalGroup(horizontalGroup_P);
        groupLayout.setVerticalGroup(verticalGroup_S);
        p1.setLayout(groupLayout);
        JScrollPane scrollPane=new JScrollPane(p1);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        
        p2.add(scrollPane);
        return p2;

    }

    @Override
    public void windowOpened(WindowEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowClosing(WindowEvent e) {
        fatherJPanel.add(sectionPanel);
        fatherJPanel.revalidate();
        fatherJPanel.repaint();
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
