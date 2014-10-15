/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package melt.View;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 *
 * @author Aote Zhou
 */
public class StartupPanel extends JFrame implements ActionListener {

    JButton b1 = new JButton("Student");
    JButton b2 = new JButton("Teacher");

    JLabel titleLabel;

    StartupPanel() {
        
//        Toolkit toolkit=Toolkit.getDefaultToolkit();
//        Dimension screenSize =toolkit.getScreenSize();
//        int screenWidth=screenSize.width;
//        int screenHeight=screenSize.height;
//        int frameWidth=this.getWidth();
//        int frameHeight=this.getHeight();
//        this.setLocation(screenWidth/2,screenHeight/2);
        this.setLocationRelativeTo(null);  //locate window in the center
        this.setTitle("MELTSystem");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(300, 200);
        this.setContentPane(getGUI());
        this.setVisible(true);
    }

    public JPanel getGUI() {
        JPanel p1;

        //Create p1;
        p1 = new JPanel();
        p1.setLayout(new FlowLayout(FlowLayout.CENTER));
        titleLabel = new JLabel("MELT System", JLabel.CENTER);
        titleLabel.setPreferredSize(new Dimension(200, 70));

        b1.setPreferredSize(new Dimension(200, 20));
        b1.addActionListener(this);
        b2.setPreferredSize(new Dimension(200, 20));
        b2.addActionListener(this);

        p1.add(titleLabel);
        p1.add(b1);
        p1.add(b2);
        

        return p1;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        if (e.getSource() == b1) {
            Authentication lI = new Authentication("Student");
            lI.setParentJFrame(this);
            lI.setModal(true);
            lI.setVisible(true);

        }else if(e.getSource()==b2){
            Authentication lI = new Authentication("Teacher");
            lI.setParentJFrame(this);
            lI.setModal(true);
            lI.setVisible(true);
        }

    }

    public static void main(String[] args) {
        StartupPanel startupPanel = new StartupPanel();
    }
}
