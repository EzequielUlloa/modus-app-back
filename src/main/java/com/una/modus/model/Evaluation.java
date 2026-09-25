package com.una.modus.model;

import jakarta.persistence.*;

@Entity
@Table(name = "evaluations")
public class Evaluation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "submission_id", nullable = false)
    private Submission submission;

    @Column(name = "teaching_profile_id", nullable = false)
    private Long teachingProfileId;

    @Column(name = "result_correct", nullable = false)
    private Boolean resultCorrect;

    @Column(name = "method_score", nullable = false)
    private Double methodScore;

    @Column(name = "is_current", nullable = false)
    private Boolean current;

    public Evaluation() {
    }

    public Evaluation(Submission submission,
                      Long teachingProfileId,
                      Boolean resultCorrect,
                      Double methodScore,
                      Boolean current) {
        this.submission = submission;
        this.teachingProfileId = teachingProfileId;
        this.resultCorrect = resultCorrect;
        this.methodScore = methodScore;
        this.current = current;
    }

    public Long getId() {
        return id;
    }

    public Submission getSubmission() {
        return submission;
    }

    public void setSubmission(Submission submission) {
        this.submission = submission;
    }

    public Long getTeachingProfileId() {
        return teachingProfileId;
    }

    public void setTeachingProfileId(Long teachingProfileId) {
        this.teachingProfileId = teachingProfileId;
    }

    public Boolean getResultCorrect() {
        return resultCorrect;
    }

    public void setResultCorrect(Boolean resultCorrect) {
        this.resultCorrect = resultCorrect;
    }

    public Double getMethodScore() {
        return methodScore;
    }

    public void setMethodScore(Double methodScore) {
        this.methodScore = methodScore;
    }

    public Boolean getCurrent() {
        return current;
    }

    public void setCurrent(Boolean current) {
        this.current = current;
    }
}