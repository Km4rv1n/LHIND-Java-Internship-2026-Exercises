package Ex1;

import java.time.LocalDate;

public class Question {

    private String question;
    private LocalDate submissionDate;
    private LocalDate deletionDate;

    public Question(String question) {
        this.question = question;
        this.submissionDate = LocalDate.now();
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public LocalDate getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(LocalDate submissionDate) {
        this.submissionDate = submissionDate;
    }

    public LocalDate getDeletionDate() {
        return deletionDate;
    }

    public void setDeletionDate(LocalDate deletionDate) {
        this.deletionDate = deletionDate;
    }

}
