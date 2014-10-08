/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package melt.Model;

/**
 *
 * @author eddychou
 */
public class Exam {
    private int exam_ID;
    private String Instructions;

    /**
     * @return the exam_ID
     */
    public int getExam_ID() {
        return exam_ID;
    }

    /**
     * @param exam_ID the exam_ID to set
     */
    public void setExam_ID(int exam_ID) {
        this.exam_ID = exam_ID;
    }

    /**
     * @return the Instructions
     */
    public String getInstructions() {
        return Instructions;
    }

    /**
     * @param Instructions the Instructions to set
     */
    public void setInstructions(String Instructions) {
        this.Instructions = Instructions;
    }
}
