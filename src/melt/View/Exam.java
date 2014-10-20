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
import melt.DAO.SubsectionQuestion_DAO;
import melt.DAO.Subsection_DAO;
import melt.Model.*;

/**
 * Exam frame shows the detailed info of it(Sections and subsec and Questions)
 *
 * @author Aote Zhou
 */
public class Exam extends JFrame implements ActionListener, WindowListener {

    public JLabel welcomeLabel, timeLabel;
    public JButton addSectionButton, deleteButton, editButton, backButton,previewButton;
    public ArrayList<Section> sections;
    public JLabel[] sectionLabels;
    public JCheckBox[] sectionChosen;
    public JPanel contentPanel;
    public JPanel mainPanel, buttonsPanel, leftPanel, rightPanel;
    public JScrollPane scrollPane;
    public int examId;

    public Exam(int examId) {
        this.examId = examId;
        //this.setLocationRelativeTo(null);  //make window in the center of desktop
        setTitle("MELTSystem");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();
        setSize((int) width, (int) height);
        setContentPane(getGUI());
        //MenuBar
        JMenuBar jMenuBar1 = new JMenuBar();
        JMenu jMenu1 = new JMenu("Set the exam, by adding Sections, Subsections and Questions");
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
        //create mainPanel(mainPanel is the main part to show the sections and its subsec and questions)
        mainPanel = new JPanel();
        backButton = new JButton("<<<Back to Exams");
        backButton.setMinimumSize(new Dimension(350, 20));
        backButton.addActionListener(this);
        
        previewButton=new JButton("Preview");
        previewButton.addActionListener(this);
        
        setMainPanel();
        //Create buttonsPanel
        buttonsPanel = new JPanel();

        editButton = new JButton("Edit");
        editButton.addActionListener(this);

        deleteButton = new JButton("Delete");
        deleteButton.addActionListener(this);

        addSectionButton = new JButton("Add a Section");
        addSectionButton.addActionListener(this);

        buttonsPanel.setLayout(new BorderLayout());
        //p3.add(new JLabel("Overall   6.0/30.0"));
        JPanel pTemp = new JPanel(new BorderLayout());

        pTemp.setBackground(new Color(153, 153, 153));

        //pTemp.add(backButton,BorderLayout.NORTH);
        pTemp.add(editButton, BorderLayout.NORTH);
        pTemp.add(deleteButton, BorderLayout.CENTER);
        pTemp.add(addSectionButton, BorderLayout.SOUTH);

        buttonsPanel.add(pTemp, BorderLayout.SOUTH);

        //Create leftPanel
        leftPanel = new JPanel();
        leftPanel.setMaximumSize(new Dimension(100, 1000));
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        //add mainPanel to a scrollpane and set scrollbarpolicy
        scrollPane = new JScrollPane(mainPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        leftPanel.add(backButton);
        leftPanel.add(previewButton);
        leftPanel.add(scrollPane);
        leftPanel.add(buttonsPanel);

        //create rightPanel (right part)
        rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.X_AXIS));
        rightPanel.add(leftPanel);
        contentPanel = new JPanel();
        //contentPanel.setBackground(Color.red);
        contentPanel.setLayout(new FlowLayout());
        rightPanel.add(contentPanel);

        //set the background color of the leftpart
        mainPanel.setBackground(new Color(153, 153, 153));
        buttonsPanel.setBackground(new Color(153, 153, 153));
        leftPanel.setBackground(new Color(153, 153, 153));

        String examName = "Exam " + examId;

        leftPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(""), examName));

        return rightPanel;
    }

    private void setMainPanel() {
        //getsections according to examId
        getSections(examId);

        sectionChosen = new JCheckBox[sections.size()];

        sectionLabels = new JLabel[sections.size()];

        //set editbuttons, editbuttons and sectionlabels
        for (int i = 1; i < sections.size() + 1; i++) {
            Section section = sections.get(i - 1);
            String sectionTimeLimit = new SimpleDateFormat("HH:mm:ss").format(section.getTimeLimit());

            sectionLabels[i - 1] = new JLabel("Section " + i + "  " + section.getSection_Name());
            //Edit Button

            //Checkboxes
            sectionChosen[i - 1] = new JCheckBox();
            sectionChosen[i - 1].setName(section.getSection_ID() + "");
            sectionChosen[i - 1].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    JCheckBox cbLog = (JCheckBox) e.getSource();

                    if (cbLog.isSelected()) {
                        for (int i = 0; i < sectionChosen.length; i++) {
                            if (!(sectionChosen[i] == e.getSource())) {
                                sectionChosen[i].setEnabled(false);
                            }
                        }
                    } else {
                        for (int i = 0; i < sectionChosen.length; i++) {
                            sectionChosen[i].setEnabled(true);
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

        groupLayout = new GroupLayout(mainPanel);
        groupLayout.setAutoCreateContainerGaps(true);
        groupLayout.setAutoCreateGaps(true);

        horizontalGroup_P = groupLayout.createParallelGroup();
        verticalGroup_S = groupLayout.createSequentialGroup();

        for (int i = 0; i < sections.size(); i++) {
//            mainPanel.add(sectionLabels[i]);
//            mainPanel.add(buttons[i]);
//            mainPanel.add(deleteButtons[i]);
            horizontalGroup_P.addGroup(groupLayout.createSequentialGroup()
                    .addComponent(sectionChosen[i])
                    .addComponent(sectionLabels[i])
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE));

            verticalGroup_S.addGroup(groupLayout.createParallelGroup()
                    .addComponent(sectionChosen[i])
                    .addComponent(sectionLabels[i]));

        }
        groupLayout.setHorizontalGroup(horizontalGroup_P);
        groupLayout.setVerticalGroup(verticalGroup_S);
        mainPanel.setLayout(groupLayout);
        mainPanel.revalidate();
        //p2.repaint();

    }

    /**
     * get sections according to exam
     *
     * @param examId
     */
    private void getSections(int examId) {
        try {
            Section_DAO section_DAO = new Section_DAO();
            ResultSet rs = section_DAO.getList("Exam_ID='" + examId + "'");

            sections = new ArrayList<Section>();
            while (rs.next()) {
                Section section = new Section();
                section.setExam_ID(examId);
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
        if (e.getSource() == addSectionButton) {
            //add settingSection panel to the right panel
            AddSection settingSection = new AddSection(examId);
            contentPanel.removeAll();
            JPanel temp = settingSection.getGUI();

            contentPanel.setLayout(new FlowLayout());
            contentPanel.add(temp);
            contentPanel.revalidate();
            for (int i = 0; i < sectionChosen.length; i++) {
                sectionChosen[i].setSelected(false);
                sectionChosen[i].setEnabled(true);
            }
            //contentPanel.repaint();

        }else if (e.getSource() == backButton) {
            //this.dispose();
            ExamOverview examOverview = new ExamOverview();
            //examOverview.setVisible(true);
            JFrame fatherFrame = (JFrame) mainPanel.getRootPane().getParent();
            fatherFrame.setContentPane(examOverview.getGUI());
            fatherFrame.revalidate();
            fatherFrame.repaint();

        } else if (e.getSource() == previewButton) {
            //this.dispose();
            ExamPreview examPreview=new ExamPreview(examId);
            
            //examOverview.setVisible(true);
            contentPanel.removeAll();
            contentPanel.setLayout(new BorderLayout());
            contentPanel.add(examPreview.getGUI());
            contentPanel.revalidate();

        } else if (((JButton) e.getSource()).getText().equals("Delete")) {
            //set section_ID
            if (sectionChosen.length > 0) {
                int section_ID = 0;
                for (int i = 0; i < sectionChosen.length; i++) {
                    if (sectionChosen[i].isSelected()) {
                        section_ID = Integer.parseInt((sectionChosen[i].getName()));
                        //get settingExamPanel and add to right panel
                        //create and intialize the DAOs
                        //MCQ_DAO mcq_DAO=new MCQ_DAO();
                        //Question_DAO question_DAO=new Question_DAO();
                        SubsectionQuestion_DAO subSectionQuestion_DAO = new SubsectionQuestion_DAO();
                        Subsection_DAO subsection_DAO = new Subsection_DAO();
                        Section_DAO section_DAO = new Section_DAO();

                        //update the info of question and MCQ then delete the relative data in section and subsection 
                        //mcq_DAO.cancelRWithSubSec(section_ID);
                        //question_DAO.cancelRWithSubSec(section_ID);
                        subSectionQuestion_DAO.cancelRWithSubSec(section_ID);
                        subsection_DAO.delete("Section_ID='" + section_ID + "'");
                        section_DAO.delete("Section_ID='" + section_ID + "'");

                        mainPanel.removeAll();

                        setMainPanel();
                    }
                }
            }

        } else if (((JButton) e.getSource()).getText().equals("Edit")) {  //Edit Button
            // this.dispose();
            if (sectionChosen.length > 0) {
                int section_ID = 0;
                for (int i = 0; i < sectionChosen.length; i++) {
                    if (sectionChosen[i].isSelected()) {
                        section_ID = Integer.parseInt((sectionChosen[i].getName()));

                        //get settingExamPanel and add to right panel
                        SettingExam settingExam = new SettingExam(section_ID);
                        contentPanel.removeAll();
                        contentPanel.setLayout(new BorderLayout());
                        contentPanel.add(settingExam.getGUI());
                        contentPanel.revalidate();
                        //contentPanel.repaint();
                        //settingExam.setVisible(true);
                    }
                }

            }

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
