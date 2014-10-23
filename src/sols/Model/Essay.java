/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sols.Model;

/**
 *
 * @author Ghader
 */
public class Essay {
    
    private int noofwords;
    private String instructions;
    private int QType_ID;
    private int Question_ID;
    
    
    /**
     * @return the noofwords
     */
    public int getNoofwords() {
        return noofwords;
    }

/**
     * @param noofwords the noofwords to set
     */
    public void setNoofwords(int noofwords) {
        this.noofwords = noofwords;
    }
    
    
    /**
     * @return the instructions
     */
    public String getInstructions() {
        return instructions;
    }

/**
     * @param instructions the instructions to set
     */
    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }
    
    
     public int getQType_ID() {
        return QType_ID;
    }
    
      public void setQType_ID(int  QType_ID) {
        this.QType_ID = QType_ID;
    }
      
      
       public int getQuestion_ID() {
        return Question_ID;
    }
       
        public void setQuestion_ID(int  Question_ID) {
        this.Question_ID = Question_ID;
    }
}
    
    




 


