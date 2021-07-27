package com.generator.app.entity;

import com.generator.app.util.AuditModel;

import javax.persistence.*;

@Entity
@Table(name = "tasks")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Task extends AuditModel {

    @Column(nullable = false, columnDefinition = "text")
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String name) {
        this.text = name;
    }

    @Override
    public String toString() {
        return "Task{" +
                "text='" + text + '\'' +
                ", id=" + id +
                '}';
    }
}
