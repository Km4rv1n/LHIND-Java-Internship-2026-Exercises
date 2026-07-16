package Ex1;

import java.util.*;
import java.util.stream.Collectors;

public class SurveyManager {
    private List<SurveyResult> surveyResults;

    public SurveyManager(List<SurveyResult> surveyResults) {
        this.surveyResults = surveyResults;
    }

    public void addSurveyResult(SurveyResult result){
        surveyResults.add(result);
    }

    public List<SurveyResult> getSurveyResults() {
        return surveyResults;
    }

    public Answer findMostGivenAnswer(Survey survey){
        Map<Answer,Integer> frequencies = new EnumMap<>(Answer.class);

        for(SurveyResult result : surveyResults){

            if(!result.getSurvey().equals(survey))
                continue;

            for(Answer answer : result.getCandidateAnswers().values()){
                frequencies.merge(answer,1,Integer::sum);
            }
        }

        return frequencies.entrySet().stream().max(Map.Entry.comparingByValue()).map(Map.Entry::getKey).orElse(null);
    }

    public void printSurveyResult(Survey survey){
        int questionNo = 1;
        for(Question question : survey.getQuestions()){
            System.out.println("\nQuestion "+questionNo+" "+question.getQuestion());
            questionNo++;

            Map<Answer,Integer> counts = new EnumMap<>(Answer.class);

            for(Answer a : Answer.values()){
                counts.put(a,0);
            }

            for(SurveyResult result : surveyResults){

                if(result.getSurvey().equals(survey)){
                    continue;
                }

                Answer answer = result.getCandidateAnswers().get(question);

                if (answer != null){
                    counts.put(answer, counts.get(answer)+1);
                }
            }

            counts.forEach((k,v) -> System.out.println(k + ": " + v));
        }
    }

    public Map<Question, Answer> findAnswersByCandidate(Candidate candidate, Survey survey){

        for(SurveyResult result : surveyResults){

            if(result.getCandidate().equals(candidate) && result.getSurvey().equals(survey)){
                return result.getCandidateAnswers();
            }
        }

        return Collections.emptyMap();
    }

    public Candidate findCandidateWithMostSurveys(){

        Map<Candidate, Long> candidateSurveyCounts = surveyResults.stream().collect(Collectors.groupingBy(SurveyResult::getCandidate, Collectors.counting()));

        return candidateSurveyCounts.entrySet().stream().max(Map.Entry.comparingByValue()).map(Map.Entry::getKey).orElse(null);
    }

    public void removeLowResponseQuestions(Survey survey){

        int totalCandidates = (int) surveyResults.stream().filter(r -> r.getSurvey().equals(survey)).count();

        List<Question> questionsToRemove = new ArrayList<>();

        for (Question question : survey.getQuestions()){

            int answered = 0;

            for (SurveyResult result : surveyResults){

                if(!result.getSurvey().equals(survey))
                    continue;

                if(result.getCandidateAnswers().containsKey(question)){
                    answered++;
                }
            }

            double percentage;

            if (totalCandidates == 0) {
                percentage = 0;
            } else {
                percentage = answered * 100.0 / totalCandidates;
            }

            if(percentage < 50.0){
                questionsToRemove.add(question);
            }
        }

        survey.getQuestions().removeAll(questionsToRemove);
    }
}
