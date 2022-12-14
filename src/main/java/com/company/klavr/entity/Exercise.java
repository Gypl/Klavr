package com.company.klavr.entity;

import io.jmix.core.entity.annotation.CaseConversion;
import io.jmix.core.entity.annotation.ConversionType;
import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@JmixEntity
@Table(name = "EXERCISE", indexes = {
        @Index(name = "IDX_EXERCISE_EXERCISETODIFFICU", columnList = "EXERCISE_TO_DIFFICULTY_ID")
})
@Entity
public class Exercise {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @InstanceName
    @CaseConversion
    @Column(name = "NAME", nullable = false, length = 30)
    @NotNull
    private String name;

    @CaseConversion(type = ConversionType.LOWER)
    @Column(name = "TEXT", nullable = false)
    @NotNull
    private String text;

    @Column(name = "LENGTH", nullable = false)
    @NotNull
    private Integer length;

    @JoinColumn(name = "EXERCISE_TO_DIFFICULTY_ID", nullable = false)
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Difficulty exercise_to_difficulty;

    public void setLength(Integer length) {
        this.length = length;
    }

    public Difficulty getExercise_to_difficulty() {
        return exercise_to_difficulty;
    }

    public void setExercise_to_difficulty(Difficulty exercise_to_difficulty) {
        this.exercise_to_difficulty = exercise_to_difficulty;
    }

    public Integer getLength() {
        return length;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}