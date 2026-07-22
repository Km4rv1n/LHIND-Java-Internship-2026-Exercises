package Ex1;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class SurveyResult {
    private Survey survey;
    private Candidate candidate;
    private LocalDate dateTaken;
    private Map<Question,Answer> candidateAnswers;

    public SurveyResult(Survey survey, Candidate candidate) {
        this.survey = survey;
        this.candidate = candidate;
        this.dateTaken = LocalDate.now();
        candidateAnswers = new HashMap<>();
    }

    public void answerQuestion(Question question, Answer answer){
        candidateAnswers.put(question,answer);
    }

    public Survey getSurvey() {
        return survey;
    }

    public Candidate getCandidate() {
        return candidate;
    }

    public LocalDate getDateTaken() {
        return dateTaken;
    }

    public Map<Question, Answer> getCandidateAnswers() {
        return candidateAnswers;
    }
}
