package com.generator.app.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.generator.app.util.AuditModel;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "subjects")
public class Subject extends AuditModel {

    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonIgnore
    private final List<Theme> themes = new LinkedList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Theme> getThemes() {
        return themes;
    }

    public void addTheme(Theme theme) {
        this.themes.add(theme);
        theme.setSubject(this);
    }

    public void removeTheme(Theme theme) {
        this.themes.remove(theme);
        theme.setSubject(null);
    }

    @Override
    public String toString() {
        return "Subject{" +
                "name='" + name + '\'' +
                ", themes=" + themes +
                ", id=" + id +
                '}';
    }
}
