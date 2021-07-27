package com.generator.app.repository;

import com.generator.app.entity.Theory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TheoryRepository extends JpaRepository<Theory, Long> {

    List<Theory> findAllByThemeId(long themeId);
}
