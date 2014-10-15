package melt.View;

public class SettingQuestion {
    
    private int id;
    private String question;
    private int counter;
    


    private char correctAnswer;
    

    public SettingQuestion(int counter, int id, String question) {
        super();
        this.id = id;
        this.question = question;
        this.counter = counter;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getCounter() {
        return counter;
    }
    
    public void setCounter(int counter) {
        this.counter = counter;
    }

    
}
