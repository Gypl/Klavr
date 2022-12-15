package com.company.klavr.logic;

public class ExerciseHandler {

    private int currentLength = 0;
    private int maxLength;

    private int currentMistakes = 0;
    private int maxMistakes;
    private int averageSpeed = 0;
    public ExerciseHandler(int maxLength, int maxMistakes) {
        this.maxLength = maxLength;
        this.maxMistakes = maxMistakes;
    }

    public void mistake() {
        currentMistakes++;
    }

    public boolean isMistakesFull() {
        return currentMistakes >= maxMistakes;
    }

    public void incLength() {
        currentLength++;
    }

    public int getAverageSpeed() {
        return averageSpeed;
    }

    public void setAverageSpeed(int averageSpeed) {
        this.averageSpeed = averageSpeed;
    }

    public int getCurrentLength() {
        return currentLength;
    }

    public void setCurrentLength(int currentLength) {
        this.currentLength = currentLength;
    }

    public int getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    public int getCurrentMistakes() {
        return currentMistakes;
    }

    public void setCurrentMistakes(int currentMistakes) {
        this.currentMistakes = currentMistakes;
    }

    public int getMaxMistakes() {
        return maxMistakes;
    }

    public void setMaxMistakes(int maxMistakes) {
        this.maxMistakes = maxMistakes;
    }
}
