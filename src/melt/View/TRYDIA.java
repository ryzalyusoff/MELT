/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package melt.View;

import javax.swing.JDialog;

/**
 *
 * @author eddychou
 */
public class TRYDIA extends JDialog{
    TRYDIA(){
        this.setLocationRelativeTo(null);  //make window in the center of desktop
        setSize(500, 150);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
    }
    public static void main(String[] args) {
        TRYDIA trydia=new TRYDIA();
        trydia.setContentPane(new AddSection(1).getGUI());
        trydia.setVisible(true);
    }
    
}
