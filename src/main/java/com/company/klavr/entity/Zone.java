package com.company.klavr.entity;

import io.jmix.core.entity.annotation.CaseConversion;
import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@JmixEntity
@Table(name = "ZONE_")
@Entity(name = "Zone_")
public class Zone {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @InstanceName
    @CaseConversion
    @Column(name = "SYMBOLS", length = 60)
    private String symbols;

    @JoinTable(name = "DIFFICULTY_ZONE_LINK",
            joinColumns = @JoinColumn(name = "ZONE_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "DIFFICULTY_ID", referencedColumnName = "ID"))
    @ManyToMany
    private List<Difficulty> difficulties;

    public List<Difficulty> getDifficulties() {
        return difficulties;
    }

    public String getSymbols() {
        return symbols;
    }

    public void setSymbols(String symbols) {
        this.symbols = symbols;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}