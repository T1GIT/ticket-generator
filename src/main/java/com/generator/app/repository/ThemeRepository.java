package com.generator.app.repository;

import com.generator.app.entity.Theme;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ThemeRepository extends JpaRepository<Theme, Long> {

    void deleteAllBySubjectId(long subjectId);
}
