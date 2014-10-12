package melt;

import java.util.List;
import javax.swing.table.AbstractTableModel;

public class QuestionTableModel extends AbstractTableModel {

    private static final int NO_COL = 0;
    private static final int QUESTION_COL = 1;
    private static final int ANSWER_COL = 2;
    
    private String[] columnNames = { "No", "Question"};
    private List<getQuestion> questions;
    
    public QuestionTableModel(List<getQuestion> theQuestions) {
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

		getQuestion tempQuestion = questions.get(row);

		switch (col) {
                case NO_COL:
			return tempQuestion.getId();    
		case QUESTION_COL:
			return tempQuestion.getQuestion();

		//case ANSWER_COL:
		//	return tempQuestion.getAnswer();

		default:
			return tempQuestion.getQuestion();
		}
	}

	@Override
	public Class getColumnClass(int c) {
		return getValueAt(0, c).getClass();
	}
    
}
