/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package melt.View;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableCellRenderer;
import melt.DAO.Question_DAO;

/**
 * the dialog enable for teacher to choose question for the Exam
 *
 * @author Aote Zhou
 */
public class ChooseQuestionsPanel extends JDialog implements ActionListener {

    JTable table1;
    JButton button1;
    int fatherPanelState;//0->SectionPanel 1->Subsectionpanel
    JPanel fatherPanel;

    public ChooseQuestionsPanel() {
        this.setLocationRelativeTo(null);  //make window in the center of desktop
        setSize(640, 500);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setContentPane(getGUI());
    }

    /**
     * initialize the ChooseQuestionPanel and make sure the fatherPanel
     *
     * @param fatherPanel the panel where the question will go to in the future
     */
    public ChooseQuestionsPanel(SectionPanel fatherPanel) {
        this.setLocationRelativeTo(null);  //make window in the center of desktop
        setSize(640, 500);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setContentPane(getGUI());
        this.fatherPanel = fatherPanel;
        fatherPanelState = 0;
    }

    /**
     * initialize the ChooseQuestionPanel and make sure the fatherPanel
     *
     * @param fatherPanel the panel where the question will go to in the future
     */
    public ChooseQuestionsPanel(SubsectionPanel fatherPanel) {
        this.setLocationRelativeTo(null);  //make window in the center of desktop
        setSize(640, 500);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setContentPane(getGUI());
        this.fatherPanel = fatherPanel;
        fatherPanelState = 1;
    }

    /**
     * get contentPane
     *
     * @return
     */
    public JPanel getGUI() {
        JPanel p1;

        button1 = new JButton("OK");
        button1.addActionListener(this);
        //columnNames for the table
        String[] columnNames = {
            "Q_ID",
            "Q_TEXT",
            "Choose"};
        //get data from database
        Object[][] data = getData();
        
        //create jtable
        table1 = new JTable(data, columnNames);
        
        //set selectionMode
        table1.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        
        //turn the last column from text to checkbox
        table1.getColumnModel().getColumn(2).setCellRenderer(new TableCellRenderer() {

            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {

                JCheckBox ck = new JCheckBox();
                ck.setSelected(isSelected);
                ck.setHorizontalAlignment(0);
                return ck;
            }
        });

        p1 = new JPanel();
        p1.setLayout(new BoxLayout(p1, BoxLayout.Y_AXIS));
        p1.add(new JScrollPane(table1)); //put table1 into a jscrollpane and add to p1
        p1.add(button1);

        return p1;

    }

    /**
     * get question data from database
     *
     * @return
     */
    public Object[][] getData() {
        try {
            Question_DAO question_DAO = new Question_DAO();
            //get questions
            ResultSet rs = question_DAO.getList("");
            ArrayList<Object[]> objectArraylist = new ArrayList<Object[]>();
            //store data into the arrayList
            while (rs.next()) {
                Object[] col = new Object[3];
                col[0] = rs.getInt(1);
                col[1] = rs.getString(4);
                col[2] = false;
                objectArraylist.add(col);

            }
            //trun arraylist<object[]> to object[][]
            Object[][] datas = new Object[objectArraylist.size()][3];
            for (int i = 0; i < objectArraylist.size(); i++) {
                datas[i] = objectArraylist.get(i);
            }
            return datas;
        } catch (SQLException ex) {
            Logger.getLogger(ChooseQuestionsPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static void main(String[] args) {
        ChooseQuestionsPanel chooseQuestionsPanel = new ChooseQuestionsPanel();
        chooseQuestionsPanel.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button1) { //add pressed
            int[] ints = table1.getSelectedRows();
            for (int rowNum : ints) {
                //check which the fatherPanel is and add  MCQPanel to it
                if (fatherPanelState == 0) {
                    ((SectionPanel) fatherPanel).addQ(new MCQPanel((int) table1.getValueAt(rowNum, 0)));
                    ((SectionPanel) fatherPanel).subQPanelRepaint();
                } else {
                    ((SubsectionPanel) fatherPanel).addQ(new MCQPanel((int) table1.getValueAt(rowNum, 0)));
                    ((SubsectionPanel) fatherPanel).subQPanelRepaint();
                }

            }
            this.dispose();

        }
    }

}
