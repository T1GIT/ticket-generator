package com.generator.app.controller;

import com.generator.app.entity.Theme;
import com.generator.app.service.SubjectService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/theme")
public class ThemeController {

    private final SubjectService subjectService;

    public ThemeController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @GetMapping("/subject")
    public List<Theme> getAllBySubject(@RequestParam long subjectId) {
        List<Theme> themes = subjectService.getById(subjectId).getThemes();
        themes.sort((t1, t2) -> String.CASE_INSENSITIVE_ORDER.compare(t1.getName(), t2.getName()));
        return themes;
    }
}
