package melt.Model;

import melt.View.SettingQuestion;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class QuestionTableModel extends AbstractTableModel {

    private static final int NO_COL = 0;
    private static final int ID_COL = 1;
    private static final int QUESTION_COL = 2;
    
    private String[] columnNames = { "No", "Question ID" ,"Question"};
    private List<SettingQuestion> questions;
    
    public QuestionTableModel(List<SettingQuestion> theQuestions) {
		questions = theQuestions;
    }
    
    @Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		return questions.size();
	}

	@Override
	public String getColumnName(int col) {
		return columnNames[col];
	}

	@Override
	public Object getValueAt(int row, int col) {

		SettingQuestion tempQuestion = questions.get(row);

		switch (col) {
                case NO_COL:
			return tempQuestion.getCounter();
                
		case ID_COL:
                        return tempQuestion.getId();
    
		case QUESTION_COL:
			return tempQuestion.getQuestion();

		default:
			return tempQuestion.getQuestion();
		}
	}

	@Override
	public Class getColumnClass(int c) {
		return getValueAt(0, c).getClass();
	}
    
}
