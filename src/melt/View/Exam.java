/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package melt.View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import melt.DAO.MCQ_DAO;
import melt.DAO.Question_DAO;
import melt.DAO.Section_DAO;
import melt.DAO.Subsection_DAO;
import melt.Model.*;

/**
 * Exam frame shows the detailed info of it(Sections and subsec and Questions)
 * @author Aote Zhou
 */

public class Exam extends JFrame implements ActionListener,WindowListener{

    public JLabel welcomeLabel, timeLabel;
    public JButton addSectionButton, deleteButton, editButton;
    public ArrayList<Section> sections;
    public JLabel[] sectionLabels;
    public JButton[] buttons,deleteButtons;
    public JCheckBox[] checkbox;
    public JPanel contentPanel;
    public JPanel p1, p2, p3, p4,p5;
    public JScrollPane jspane;
    public int exam_ID;

    
    public Exam(int exam_ID) {
        this.exam_ID=exam_ID;
        //this.setLocationRelativeTo(null);  //make window in the center of desktop
        setTitle("MELTSystem--Test");
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();
        setSize((int)width, (int)height);
        setContentPane(getGUI());
        //MenuBar
        JMenuBar jMenuBar1=new JMenuBar();
        JMenu jMenu1=new JMenu("ddd");
        jMenuBar1.add(jMenu1);
        getRootPane().setMenuBar(jMenuBar1);
        
        addWindowListener(this);
    }

    public JPanel getGUI() {
        

//        welcomeLabel = new JLabel("....,Welcome!");
//        timeLabel = new JLabel("dd-mm-yyyy hh:mm");
//
//        
//        // create p1
//        p1 = new JPanel();
//        p1.setLayout(new GridLayout(1, 3));
//        p1.add(welcomeLabel);
//        p1.add(new JPanel());
//        p1.add(timeLabel);

        //create p2(p2 is the main part to show the sections and its subsec and questions)
        p2 = new JPanel();
        setP2();
        //Create p3
        p3 = new JPanel();
        
        editButton = new JButton("Edit");
        editButton.addActionListener(this);
        
        deleteButton = new JButton("Delete");
        deleteButton.addActionListener(this);
        
        addSectionButton = new JButton("Add a Section");
        addSectionButton.addActionListener(this);
        
        p3.setLayout(new BorderLayout());
        //p3.add(new JLabel("Overall   6.0/30.0"));
        JPanel pTemp = new JPanel(new BorderLayout());

        pTemp.setBackground(new Color(153, 153, 153));
        
        pTemp.add(editButton,BorderLayout.NORTH);
        pTemp.add(deleteButton,BorderLayout.CENTER);
        pTemp.add(addSectionButton,BorderLayout.SOUTH);

        p3.add(pTemp,BorderLayout.SOUTH);
        
        //Create p4
        p4 = new JPanel();
        p4.setMaximumSize(new Dimension(100,1000));
        p4.setLayout(new BoxLayout(p4, BoxLayout.Y_AXIS));
            //add p2 to a scrollpane and set scrollbarpolicy
        jspane=new JScrollPane(p2);
        jspane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        p4.add(jspane);
        p4.add(p3);
        
        //create p5 (right part)
        p5=new JPanel();
        p5.setLayout(new BoxLayout(p5, BoxLayout.X_AXIS));
        p5.add(p4);
        contentPanel=new JPanel();
        //contentPanel.setBackground(Color.red);
        contentPanel.setLayout(new BorderLayout());
        p5.add(contentPanel);
        
        //set the background color of the leftpart
        p2.setBackground(new Color(153, 153, 153));
        p3.setBackground(new Color(153, 153, 153));
        p4.setBackground(new Color(153, 153, 153));
        
        p4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(""), "Exam 1"));

        return p5;
    }

    private void setP2(){
        //getsections according to exam_ID
        getSections(exam_ID);
        
        checkbox = new JCheckBox[sections.size()];        
        

        sectionLabels=new JLabel[sections.size()];

        
        //set editbuttons, editbuttons and sectionlabels
        for (int i = 1; i < sections.size() + 1; i++) {
            Section section=sections.get(i-1);
            String sectionTimeLimit=new SimpleDateFormat("HH:mm:ss").format(section.getTimeLimit());
            
            sectionLabels[i - 1] = new JLabel("Section" + i + "  " + section.getSection_Name());
            //Edit Button
            
            //Checkboxes
            checkbox[i - 1] = new JCheckBox();
            checkbox[i - 1].setName(section.getSection_ID() + "");
            checkbox[i - 1].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    JCheckBox cbLog = (JCheckBox) e.getSource();

                    if (cbLog.isSelected()) {
                        for (int i = 0; i < checkbox.length; i++) {
                            if (!(checkbox[i] == e.getSource())) {
                                checkbox[i].setEnabled(false);
                            }
                        }
                    } else {
                        for (int i = 0; i < checkbox.length; i++) {
                            checkbox[i].setEnabled(true);
                        }
                    }
                }
            });          

        }
        
        //p2.setLayout(new GridLayout(sections.size(),3));
        
        //setlayout
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
                    .addComponent(checkbox[i])
                    .addComponent(sectionLabels[i])
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE));
                    
            
            verticalGroup_S.addGroup(groupLayout.createParallelGroup()
                    .addComponent(checkbox[i])
                    .addComponent(sectionLabels[i]));
            
        }
        groupLayout.setHorizontalGroup(horizontalGroup_P);
        groupLayout.setVerticalGroup(verticalGroup_S);
        p2.setLayout(groupLayout);
        p2.revalidate();
        //p2.repaint();

    }
    /**
     * get sections according to exam
     * @param exam_ID 
     */
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
            //add settingSection panel to the right panel
            AddSection settingSection=new AddSection(exam_ID);
            contentPanel.removeAll();
            JPanel temp=settingSection.getGUI();
            
            contentPanel.setLayout(new FlowLayout());
            contentPanel.add(temp);
            contentPanel.revalidate();
            //contentPanel.repaint();
            
        }else if (((JButton)e.getSource()).getText().equals("Delete")) {
            //set section_ID
            // wait for complete
            int section_ID = 0;
            for (int i = 0; i < checkbox.length; i++) {
                if (checkbox[i].isSelected()) {
                    section_ID = Integer.parseInt((checkbox[i].getName()));
                }
            }
            
            //create and intialize the DAOs
            MCQ_DAO mcq_DAO=new MCQ_DAO();
            Question_DAO question_DAO=new Question_DAO();
            Subsection_DAO subsection_DAO=new Subsection_DAO();
            Section_DAO section_DAO=new Section_DAO();
        
            //update the info of question and MCQ then delete the relative data in section and subsection 
            mcq_DAO.cancelRWithSubSec(section_ID);
            question_DAO.cancelRWithSubSec(section_ID);
            subsection_DAO.delete("Section_ID='"+section_ID+"'");
            section_DAO.delete("Section_ID='"+section_ID+"'");
            
            p2.removeAll();
            
            setP2();
            
            
            
        }else if(((JButton)e.getSource()).getText().equals("Edit")){  //Edit Button
           // this.dispose();
            int section_ID = 0;
            for (int i = 0; i < checkbox.length; i++) {
                if (checkbox[i].isSelected()) {
                    section_ID = Integer.parseInt((checkbox[i].getName()));
                }
            }
           
            //get settingExamPanel and add to right panel
            SettingExam settingExam=new SettingExam(section_ID);
            contentPanel.removeAll();
            contentPanel.setLayout(new BorderLayout());
            contentPanel.add(settingExam.getGUI());
            contentPanel.revalidate();
            //contentPanel.repaint();
            //settingExam.setVisible(true);
            
        }
    }
    public static void main(String[] args) {
        Exam test = new Exam(1);
        
        test.setVisible(true);

    }

    @Override
    public void windowOpened(WindowEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowClosing(WindowEvent e) {
        this.dispose();
        ExamOverview examOverview=new ExamOverview();
        examOverview.setVisible(true);
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
