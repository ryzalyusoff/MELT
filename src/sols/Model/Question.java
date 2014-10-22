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
public class Question {
    protected int question_ID;  
    protected int qtype_ID;

    /**
     * @return the Question_ID
     */
    public int getQuestion_ID() {
        return question_ID;
    }

    /**
     * @param Question_ID the Question_ID to set
     */
    public void setQuestion_ID(int Question_ID) {
        this.question_ID = Question_ID;
    }

    /**
     * @return the Qtype_ID
     */
    public int getQtype_ID() {
        return qtype_ID;
    }

    /**
     * @param Qtype_ID the Qtype_ID to set
     */
    public void setQtype_ID(int Qtype_ID) {
        this.qtype_ID = Qtype_ID;
    }

    
}
