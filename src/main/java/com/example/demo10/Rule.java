package com.example.demo10;

public class Rule {
    private int id;
    private String symptoms;
    private String conditions;
    private int confidence;
    private String recommendation;

    public Rule(int id, String symptoms, String conditions, int confidence, String recommendation) {
        this.id = id;
        this.symptoms = symptoms;
        this.conditions = conditions;
        this.confidence = confidence;
        this.recommendation = recommendation;
    }

    public int getId() { return id; }
    public String getSymptoms() { return symptoms; }
    public String getCondition() { return conditions; }
    public int getConfidence() { return confidence; }
    public String getRecommendation() { return recommendation; }
}
