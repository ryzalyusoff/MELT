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
public class Essay extends Question{
    private String noOfWords;
    private String Instructions;

    /**
     * @return the noOfWords
     */
    public String getNoOfWords() {
        return noOfWords;
    }

    /**
     * @param noOfWords the noOfWords to set
     */
    public void setNoOfWords(String noOfWords) {
        this.noOfWords = noOfWords;
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
