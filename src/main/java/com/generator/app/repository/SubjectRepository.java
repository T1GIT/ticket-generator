package com.generator.app.repository;

import com.generator.app.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<Subject, Long> {

    Subject findByName(String name);
}
