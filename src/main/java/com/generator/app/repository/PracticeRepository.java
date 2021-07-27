package com.generator.app.repository;

import com.generator.app.entity.Practice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PracticeRepository extends JpaRepository<Practice, Long> {

    List<Practice> findAllByThemeId(long themeId);
}
