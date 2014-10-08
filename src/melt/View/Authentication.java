package melt.View;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import melt.DAO.Student_DAO;
import melt.DAO.Teacher_DAO;

public class Authentication extends JDialog implements ActionListener {

    private JButton b1, b2;
    private JLabel l1, l2,l3;
    private JTextField textField1;
    private JPasswordField passwordField;
    private JFrame parentJFrame;
    private int flag;

    private static final int STUDENT = 0;
    private static final int TEACHER = 1;

    /**
     *
     * @param character Student/Teacher
     */
    Authentication(String character) {
        this.setLocationRelativeTo(null);
        setSize(300, 200);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setContentPane(getGUI(character));
        if (character.equals("Student")) {
            flag = STUDENT;
        } else {
            flag = TEACHER;
        }
    }

    public JPanel getGUI(String character) {
        JPanel p1,p2,p3;

        // Create p1;
        p1 = new JPanel();

        l1 = new JLabel(character + " ID");
        l2 = new JLabel("PassCode");
        l3 = new JLabel("");

        textField1 = new JTextField();
        textField1.setPreferredSize(new Dimension(200, 20));
        passwordField = new JPasswordField();

        passwordField.setPreferredSize(new Dimension(200, 20));

        b1 = new JButton("Cancel");
        b2 = new JButton("OK");

        b1.addActionListener(this);
        b2.addActionListener(this);

        p1.setLayout(new FlowLayout());
        p1.add(l1);
        p1.add(textField1);
        p1.add(l2);
        p1.add(passwordField);
        p1.add(b1);
        p1.add(b2);
        
        p2=new JPanel();
        p2.add(l3);
        
        p3=new JPanel();
        p3.setLayout(new BoxLayout(p3, BoxLayout.Y_AXIS));
        p3.add(p1);
        p3.add(p2);
        
        
        return p3;

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        String ID = textField1.getText();
        String passcode = new String(passwordField.getPassword());
        if (e.getSource() == b2) {
            if (flag == STUDENT) {
                Student_DAO student_DAO = new Student_DAO();

                boolean isExist = student_DAO.Exist(ID, passcode);
                if (isExist) {
                    l3.setText("success");
                } else {
                    l3.setText("fail");
                }
            } else {
                Teacher_DAO teacher_DAO = new Teacher_DAO();

                boolean isExist = teacher_DAO.Exist(ID, passcode);

                if (isExist) {
                    l3.setText("success");
                } else {
                    l3.setText("fail");
                }
            }

        } else if (e.getSource() == b1) {
            System.out.println("ddddd");
            this.setModal(false);
            this.dispose();

//			this.setModal(false);
//			Exam test=new Exam();
//			this.dispose();
//			parentJFrame.dispose();
//			test.setVisible(true);
        }

    }

    public void setParentJFrame(JFrame parentJFrame) {
        this.parentJFrame = parentJFrame;

    }

    public static void main(String[] args) {
        Authentication lI = new Authentication("Student");
        lI.setVisible(true);

    }

}
