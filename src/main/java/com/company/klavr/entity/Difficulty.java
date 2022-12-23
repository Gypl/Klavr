package com.company.klavr.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import org.checkerframework.common.aliasing.qual.Unique;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@JmixEntity
@Table(name = "DIFFICULTY")
@Entity
public class Difficulty {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @InstanceName
    @Column(name = "NAME", nullable = false, length = 30)
    @NotNull
    @Unique
    private String name;

    @Column(name = "MISTAKES_COUNT", nullable = false)
    @NotNull
    private Integer mistakesCount;

    @Column(name = "MIN_LENGTH", nullable = false)
    @NotNull
    private Integer minLength;

    @Column(name = "MAX_LENGTH", nullable = false)
    @NotNull
    private Integer maxLength;

    @Column(name = "PRESS_TIME", nullable = false)
    @NotNull
    private Integer pressTime;

    @JoinTable(name = "DIFFICULTY_ZONE_LINK",
            joinColumns = @JoinColumn(name = "DIFFICULTY_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "ZONE_ID", referencedColumnName = "ID"))
    @ManyToMany
    private List<Zone> difficult_to_zone;

    public List<Zone> getDifficult_to_zone() {
        return difficult_to_zone;
    }

    public void setDifficult_to_zone(List<Zone> difficult_to_zone) {
        this.difficult_to_zone = difficult_to_zone;
    }

    public Integer getPressTime() {
        return pressTime;
    }

    public void setPressTime(Integer pressTime) {
        this.pressTime = pressTime;
    }

    public Integer getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(Integer maxLength) {
        this.maxLength = maxLength;
    }

    public Integer getMinLength() {
        return minLength;
    }

    public void setMinLength(Integer minLength) {
        this.minLength = minLength;
    }

    public Integer getMistakesCount() {
        return mistakesCount;
    }

    public void setMistakesCount(Integer mistakesCount) {
        this.mistakesCount = mistakesCount;
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