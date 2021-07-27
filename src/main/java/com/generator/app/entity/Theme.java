package com.generator.app.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.generator.app.util.AuditModel;

import javax.persistence.*;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "themes")
public class Theme extends AuditModel {

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id", nullable = false)
    @JsonIgnore
    private Subject subject;

    @JsonIgnore
    @OneToMany(mappedBy = "theme", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private final Set<Theory> theories = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "theme", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private final Set<Practice> practices = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "theme", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private final List<Ticket> tickets = new LinkedList<>();
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Set<Theory> getTheories() {
        return theories;
    }

    public Set<Practice> getPractices() {
        return practices;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void addTheory(Theory theory) {
        this.theories.add(theory);
        theory.setTheme(this);
    }

    public void removeTheory(Theory theory) {
        this.theories.remove(theory);
        theory.setTheme(null);
    }

    public void addPractice(Practice practice) {
        this.practices.add(practice);
        practice.setTheme(this);
    }

    public void removePractice(Practice practice) {
        this.practices.remove(practice);
        practice.setTheme(null);
    }

    public void addTicket(Ticket ticket) {
        this.tickets.add(ticket);
        ticket.setTheme(this);
    }

    public void removeTicket(Ticket ticket) {
        this.tickets.remove(ticket);
        ticket.setTheme(null);
    }

    @Override
    public String toString() {
        return "Theme{" +
                "name='" + name + '\'' +
                ", theories=" + theories +
                ", practices=" + practices +
                ", tickets=" + tickets +
                ", id=" + id +
                '}';
    }
}
