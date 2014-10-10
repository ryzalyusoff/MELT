package melt;

public class getQuestion {
    
    private int id;
    private String question;
    private String answer;
    


    private char correctAnswer;
    

    public getQuestion(int id, String question) {
        super();
        this.id = id;
        this.question = question;
        //this.answer = answer;
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

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
    
}
