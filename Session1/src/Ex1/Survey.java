package Ex1;

import java.util.Collections;
import java.util.HashSet;
import java.util.stream.Collectors;

public class Survey {

    private String title;
    private String topic;
    private String description;
    private HashSet<Question> questions;

    public Survey(String title, String topic, String description) {
        this.title = title;
        this.topic = topic;
        this.description = description;
        this.questions = new HashSet<>();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public HashSet<Question> getQuestions() {
        return questions;
    }

    public boolean addQuestion(Question question){

        if(questions.size() == 40){
            throw new RuntimeException("Survey cannot contain more than 40 questions.");
        }

        return questions.add(question);
    }

    public boolean removeQuestion(Question question){
        return questions.remove(question);
    }

    public boolean validateSurvey(){
        if(questions.size() < 10 || questions.size() > 40)
            return false;

        return true;
    }

    @Override
    public String toString() {
        String listOfQuestions = "";
        for(Question question : questions){
            listOfQuestions += question.getQuestion() + "\n";
        }

        return "Survey title: " + title +"\n"
                +"Topic: "+topic + "\n"
                +"Description: "+description + "\n"
                +listOfQuestions;

    }
}
