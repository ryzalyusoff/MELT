/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sols.Model;

/**
 *
 * @author Aote Zhou
 */
public class MCQOption {
    private int MCQOption_ID;
    private int Question_ID;
    private String Content;
    private boolean isAnswerOrNot;

    /**
     * @return the MCQOption_ID
     */
    public int getMCQOption_ID() {
        return MCQOption_ID;
    }

    /**
     * @param MCQOption_ID the MCQOption_ID to set
     */
    public void setMCQOption_ID(int MCQOption_ID) {
        this.MCQOption_ID = MCQOption_ID;
    }

    /**
     * @return the Question_ID
     */
    public int getQuestion_ID() {
        return Question_ID;
    }

    /**
     * @param Question_ID the Question_ID to set
     */
    public void setQuestion_ID(int Question_ID) {
        this.Question_ID = Question_ID;
    }

    /**
     * @return the Content
     */
    public String getContent() {
        return Content;
    }

    /**
     * @param Content the Content to set
     */
    public void setContent(String Content) {
        this.Content = Content;
    }

    /**
     * @return the isAnswerOrNot
     */
    public boolean isIsAnswerOrNot() {
        return isAnswerOrNot;
    }

    /**
     * @param isAnswerOrNot the isAnswerOrNot to set
     */
    public void setIsAnswerOrNot(boolean isAnswerOrNot) {
        this.isAnswerOrNot = isAnswerOrNot;
    }
}
