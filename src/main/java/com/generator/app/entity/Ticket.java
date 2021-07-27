package com.generator.app.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.generator.app.util.AuditModel;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "tickets")
public class Ticket extends AuditModel {

    @Column(nullable = false)
    private int num;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "theme_id", nullable = false)
    private Theme theme;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "ticket_theory",
            joinColumns = @JoinColumn(name = "ticket_id"),
            inverseJoinColumns = @JoinColumn(name = "theory_id"))
    List<Theory> theories = new LinkedList<>();

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "ticket_practice",
            joinColumns = @JoinColumn(name = "ticket_id"),
            inverseJoinColumns = @JoinColumn(name = "practice_id"))
    List<Practice> practices = new LinkedList<>();

    public int getNum() {
        return num;
    }

    public Theme getTheme() {
        return theme;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }

    public List<Practice> getPractices() {
        return practices;
    }

    public List<Theory> getTheories() {
        return theories;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void addTheory(Theory theory) {
        this.theories.add(theory);
        theory.addTicket(this);
    }

    public void removeTheory(Theory theory) {
        this.theories.add(theory);
        theory.addTicket(this);
    }

    public void addPractice(Practice practice) {
        this.practices.add(practice);
        practice.addTicket(this);
    }

    public void removePractice(Practice practice) {
        this.practices.add(practice);
        practice.addTicket(this);
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "num=" + num +
                ", theme=" + theme +
                ", theories=" + theories +
                ", practices=" + practices +
                ", id=" + id +
                '}';
    }
}
