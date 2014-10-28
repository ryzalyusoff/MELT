/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package melt.View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import melt.DAO.Exam_DAO;
import melt.DAO.MCQ_DAO;
import melt.DAO.Question_DAO;
import melt.DAO.Section_DAO;
import melt.DAO.SectionQuestion_DAO;
import melt.Model.*;

/**
 * Exam frame shows the detailed info of it(Sections and subsec and Questions)
 *
 * @author Aote Zhou
 */
public class Exam extends JFrame implements ActionListener, WindowListener {

    public JLabel welcomeLabel, timeLabel;
    public JButton addSectionButton, deleteButton, editButton, backButton;
    private JButton previewButton,activatedButton,addExamButton;
    public ArrayList<Section> sections;
    public ArrayList<melt.Model.Exam> exams;
    public JPanel contentPanel;
    public JPanel mainPanel, sectionButtonsPanel, examButtonsPanel, leftPanel, rightPanel;
    public JScrollPane scrollPane;
    public JTree examTree;

    private Section selectedSection;
    private melt.Model.Exam selectedExam;

    public Exam(boolean showWindowsFirstOrNot) {
        
 
        //this.setLocationRelativeTo(null);  //make window in the center of desktop
        setTitle("MELTSystem");
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();
        setSize((int) width, (int) height);
        if (showWindowsFirstOrNot) {
            
            setVisible(true);
        }
        setContentPane(getGUI());
        //MenuBar
        JMenuBar jMenuBar1 = new JMenuBar();
        JMenu jMenu1 = new JMenu("Set the exam, by adding Sections, Subsections and Questions");
        jMenuBar1.add(jMenu1);
        getRootPane().setMenuBar(jMenuBar1);

        addWindowListener(this);
    }
    
/**
     * getExams from database
     */
    private void getExams() {
        try {
            Exam_DAO exam_DAO = new Exam_DAO();
            ResultSet rs = exam_DAO.getList("");

            exams = new ArrayList<melt.Model.Exam>();
            while (rs.next()) {
                melt.Model.Exam examTemp = new melt.Model.Exam();
                examTemp.setExam_ID(rs.getInt("Exam_ID"));
                examTemp.setInstructions(rs.getString("Instructions"));
                examTemp.setIsPublic(rs.getBoolean("isPublic"));
                exams.add(examTemp);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ExamOverview.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    private void getTree() {
        getExams();
        DefaultMutableTreeNode top = new DefaultMutableTreeNode("Exam");

        for(melt.Model.Exam exam:exams){
            DefaultMutableTreeNode top2 = new DefaultMutableTreeNode(exam);
            top.add(top2);
            getSections(exam.getExam_ID());
            DefaultMutableTreeNode[] sectionNodes = new DefaultMutableTreeNode[sections.size()];
            for (int i = 0; i < sections.size(); i++) {
                //getsections according to examId
                sectionNodes[i] = new DefaultMutableTreeNode(sections.get(i));
                top2.add(sectionNodes[i]);
            }
        }
        examTree = new JTree(top);
        examTree.setRootVisible(false);
        examTree.setBackground(new Color(153, 153, 153));
        examTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        MouseListener mouseListener = new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                TreePath path = examTree.getPathForLocation(e.getX(), e.getY());
                try {
                    
                    DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
                    if (node.getLevel() == 1) {
                        sectionButtonsPanel.setVisible(false);
                        examButtonsPanel.setVisible(true);
                        selectedExam=(melt.Model.Exam)node.getUserObject();
                        selectedSection=null;
                    } else if (node.getLevel() == 2) {
                        sectionButtonsPanel.setVisible(true);
                        examButtonsPanel.setVisible(false);
                        selectedSection = (Section) node.getUserObject();
                        selectedExam=null;
                    }
                } catch (Exception exception) {
                }

                
            }
        };
        examTree.addMouseListener(mouseListener);

    }

    private DefaultMutableTreeNode getTreeNode_Exam(melt.Model.Exam exam){
        DefaultMutableTreeNode top = new DefaultMutableTreeNode(exam);
        DefaultMutableTreeNode[] sectionNodes = new DefaultMutableTreeNode[sections.size()];
        for (int i = 0; i < sections.size(); i++) {

            sectionNodes[i] = new DefaultMutableTreeNode(sections.get(i));
            top.add(sectionNodes[i]);
        }

       
        return top;
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
        backButton = new JButton("<<<Back to Home");
        backButton.setMinimumSize(new Dimension(350, 20));
        backButton.addActionListener(this);


        setMainPanel();
        //Create sectionButtonsPanel
        sectionButtonsPanel = new JPanel();

        editButton = new JButton("Edit");
        editButton.addActionListener(this);

        deleteButton = new JButton("Delete");
        deleteButton.addActionListener(this);

        addSectionButton = new JButton("Add a Section");
        addSectionButton.addActionListener(this);

        sectionButtonsPanel.setLayout(new BorderLayout());
        //p3.add(new JLabel("Overall   6.0/30.0"));
        JPanel pTemp = new JPanel(new BorderLayout());

        pTemp.setBackground(new Color(153, 153, 153));

        //pTemp.add(backButton,BorderLayout.NORTH);
        pTemp.add(editButton, BorderLayout.NORTH);
        pTemp.add(deleteButton, BorderLayout.CENTER);
        //pTemp.add(addSectionButton, BorderLayout.SOUTH);

        sectionButtonsPanel.add(pTemp, BorderLayout.SOUTH);
        //examButtonsPanel
        examButtonsPanel = new JPanel();
        examButtonsPanel.setBackground(new Color(153, 153, 153));
        examButtonsPanel.setLayout(new BorderLayout());
  
        previewButton = new JButton("Preview");
        previewButton.addActionListener(this);

        activatedButton = new JButton("Activate");
        activatedButton.addActionListener(this);

        addExamButton = new JButton("Add a Exam");
        addExamButton.addActionListener(this);
        
        
        JPanel pTemp1 = new JPanel(new BorderLayout());
        pTemp1.setBackground(new Color(153, 153, 153));
        
        pTemp1.add(previewButton,BorderLayout.NORTH);
        pTemp1.add(activatedButton, BorderLayout.CENTER);
        pTemp1.add(addSectionButton, BorderLayout.SOUTH);
        
      
        
        examButtonsPanel.add(pTemp1,BorderLayout.SOUTH);

        //Create leftPanel
        leftPanel = new JPanel();
        leftPanel.setMaximumSize(new Dimension(100, 1000));
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        //add mainPanel to a scrollpane and set scrollbarpolicy
        scrollPane = new JScrollPane(mainPanel);
        scrollPane.setMinimumSize(new Dimension(100, 500));
        scrollPane.setPreferredSize(new Dimension(100, 500));
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        leftPanel.add(backButton);
        leftPanel.add(addExamButton);
        leftPanel.add(scrollPane);
        leftPanel.add(sectionButtonsPanel);
        leftPanel.add(examButtonsPanel);
        sectionButtonsPanel.setVisible(false);
        
        

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
        sectionButtonsPanel.setBackground(new Color(153, 153, 153));
        leftPanel.setBackground(new Color(153, 153, 153));

        String examName = "ExamOverView";

        leftPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(""), examName));

        return rightPanel;
    }

    private void setMainPanel() {
        

 

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
        
        getTree();
        
        horizontalGroup_P.addComponent(examTree);
        verticalGroup_S.addComponent(examTree);
        groupLayout.setHorizontalGroup(horizontalGroup_P);
        groupLayout.setVerticalGroup(verticalGroup_S);
        mainPanel.setLayout(groupLayout);
        mainPanel.revalidate();
        mainPanel.setMinimumSize(new Dimension(100, 500));
        //p2.repaint();

    }

    /**
     * get sections according to exam
     *
     * @param examId
     */
    private void getSections(int examId) {

        Section_DAO section_DAO = new Section_DAO();
        sections = section_DAO.getList("Exam_ID='" + examId + "'");

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addSectionButton) {
            //add settingSection panel to the right panel
            if (selectedExam!=null) {
                
                AddSection settingSection = new AddSection(selectedExam.getExam_ID());
                contentPanel.removeAll();
                JPanel temp = settingSection.getGUI();

                contentPanel.setLayout(new FlowLayout());
                contentPanel.add(temp);
                contentPanel.revalidate();
            }

            //contentPanel.repaint();
        } else if (e.getSource() == backButton) {
            melt.View.StartupPanel startupPanel=new melt.View.StartupPanel();
            startupPanel.setVisible(true);
            this.dispose();

        }  else if (((JButton) e.getSource()).getText().equals("Delete")) {
            //set section_ID

            if (selectedSection!= null) {
                       // section_ID = Integer.parseInt((sectionChosen[i].getName()));
                //get settingExamPanel and add to right panel
                //create and intialize the DAOs
                //MCQ_DAO mcq_DAO=new MCQ_DAO();
                //Question_DAO question_DAO=new Question_DAO();
                SectionQuestion_DAO subSectionQuestion_DAO = new SectionQuestion_DAO();

                Section_DAO section_DAO = new Section_DAO();

                        //update the info of question and MCQ then delete the relative data in section and subsection 
                //mcq_DAO.cancelRWithSubSec(section_ID);
                //question_DAO.cancelRWithSubSec(section_ID);
                subSectionQuestion_DAO.cancelRWithSec(selectedSection.getSection_ID());
                section_DAO.delete("Section_ID='" + selectedSection.getSection_ID() + "'");

                mainPanel.removeAll();

                setMainPanel();
            }

        } else if ((JButton) e.getSource()==editButton) {  //Edit Button
            // this.dispose();

            if (selectedSection != null) {
                        //section_ID = Integer.parseInt((sectionChosen[i].getName()));

                //get settingExamPanel and add to right panel
                SettingExam settingExam = new SettingExam(selectedSection.getSection_ID());
                contentPanel.removeAll();
                contentPanel.setLayout(new BorderLayout());
                contentPanel.add(settingExam.getGUI());
                contentPanel.revalidate();
                        //contentPanel.repaint();
                //settingExam.setVisible(true);
            }
        }else if ( e.getSource()==previewButton) {  //Preview Button
           
                ExamPreview examPreview = new ExamPreview(selectedExam.getExam_ID());

            //examOverview.setVisible(true);
            contentPanel.removeAll();
            contentPanel.setLayout(new BorderLayout());
            contentPanel.add(examPreview.getGUI());
            contentPanel.revalidate();
         
        

        }else if (e.getSource() == addExamButton) {
            AddExam addExam = new AddExam();
            //settingSection.setVisible(true);
            contentPanel.removeAll();
            JPanel temp = addExam.getGUI();

            contentPanel.setLayout(new BorderLayout());
            
            
            contentPanel.add(temp, BorderLayout.NORTH);
            contentPanel.revalidate();
        } else if (e.getSource()==activatedButton) {
            //isPublicButtons
           
                if (selectedExam!=null) {
                    Exam_DAO exam_DAO=new Exam_DAO();
                    exam_DAO.makeItPublic(selectedExam.getExam_ID());
            //update the panel
                 mainPanel.removeAll();

                setMainPanel();
                }
           
        }

    }

    public static void main(String[] args) {
        Exam test = new Exam(false);

        test.setVisible(true);

    }

    @Override
    public void windowOpened(WindowEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowClosing(WindowEvent e) {
    new melt.View.StartupPanel().setVisible(true);
        dispose();
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
