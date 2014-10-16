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
public class FIB extends Question{
    private int AnswerID;
    private String AnswerContentText;

    /**
     * @return the AnswerID
     */
    public int getAnswerID() {
        return AnswerID;
    }

    /**
     * @param AnswerID the AnswerID to set
     */
    public void setAnswerID(int AnswerID) {
        this.AnswerID = AnswerID;
    }

    /**
     * @return the AnswerContentText
     */
    public String getAnswerContentText() {
        return AnswerContentText;
    }

    /**
     * @param AnswerContentText the AnswerContentText to set
     */
    public void setAnswerContentText(String AnswerContentText) {
        this.AnswerContentText = AnswerContentText;
    }
}
