package com.company.klavr.entity;

import io.jmix.core.metamodel.annotation.JmixEntity;

@JmixEntity
public class UserProgress {
    private String exercise;

    private Integer timer;

    public UserProgress(String name, int time_) {
        exercise = name;
        timer = time_;
    }

    public Integer getTimer() {
        return timer;
    }

    public void setTimer(Integer time) {
        this.timer = time;
    }

    public String getExercise() {
        return exercise;
    }

    public void setExercise(String exercise) {
        this.exercise = exercise;
    }
}