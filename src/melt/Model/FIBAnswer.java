/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package melt.Model;

/**
 *
 * @author Aote Zhou
 */
public class FIBAnswer {
    private int FIBAnswer_ID;
    private int Question_ID;
    private String Answer;

    /**
     * @return the FIBAnswer_ID
     */
    public int getFIBAnswer_ID() {
        return FIBAnswer_ID;
    }

    /**
     * @param FIBAnswer_ID the FIBAnswer_ID to set
     */
    public void setFIBAnswer_ID(int FIBAnswer_ID) {
        this.FIBAnswer_ID = FIBAnswer_ID;
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
     * @return the Answer
     */
    public String getAnswer() {
        return Answer;
    }

    /**
     * @param Answer the Answer to set
     */
    public void setAnswer(String Answer) {
        this.Answer = Answer;
    }

    
}
