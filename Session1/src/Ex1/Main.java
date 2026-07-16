package Ex1;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        Survey survey = new Survey("Survey title", "Survey Topic", "Survey Description");

        for(int i=1; i<=10; i++){
            survey.addQuestion(new Question("Question " + i));
        }

        System.out.println("Survey validity check: " + survey.validateSurvey());

        System.out.println(survey.toString());

        Candidate c1 = new Candidate("firstName1","lastName1","email1@gmail.com","+12345678");
        Candidate c2 = new Candidate("firstName2","lastName2","email2@gmail.com","+87654321");

        SurveyResult sr1 = new SurveyResult(survey,c1);
        SurveyResult sr2 = new SurveyResult(survey,c2);

        for(Question q : survey.getQuestions()){
            sr1.answerQuestion(q, Answer.SLIGHTLY_AGREE);
            sr2.answerQuestion(q, Answer.DISAGREE);
        }

        SurveyManager surveyManager = new SurveyManager(List.of(sr1,sr2));

        System.out.println("Most common answer: "+ surveyManager.findMostGivenAnswer(survey));

        int cnt = 1;

        for(Question q : survey.getQuestions()){
            if(cnt > 3) break;

            sr1.answerQuestion(q, null);
            cnt++;
        }

        System.out.println("Candidate with the most completed surveys: "+surveyManager.findCandidateWithMostSurveys().getFirstName());
        
    }
}
