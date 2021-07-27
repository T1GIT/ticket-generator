package com.generator.app.controller;

import com.generator.app.entity.Practice;
import com.generator.app.entity.Subject;
import com.generator.app.entity.Theme;
import com.generator.app.entity.Theory;
import com.generator.app.service.SubjectService;
import com.generator.app.service.ThemeService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/subject")
public class SubjectController {

    private final SubjectService subjectService;
    private final ThemeService themeService;

    public SubjectController(SubjectService subjectService, ThemeService themeService) {
        this.subjectService = subjectService;
        this.themeService = themeService;
    }

    @GetMapping()
    public List<Subject> getAll() {
        List<Subject> subjects = subjectService.getAll();
        subjects.sort((s1, s2) -> String.CASE_INSENSITIVE_ORDER.compare(s1.getName(), s2.getName()));
        return subjects;
    }

    @PostMapping()
    @Transactional
    public void putExcel(@RequestParam MultipartFile file) throws IOException {
        String name = file.getOriginalFilename().replace(".xlsx", "").replace(".xls", "");
        Subject subject = subjectService.getByName(name);
        if (subject == null) {
            subject = new Subject();
            subject.setName(file.getOriginalFilename().replace(".xlsx", "").replace(".xls", ""));
        } else {
            themeService.deleteBySubject(subject);
        }
        XSSFWorkbook book = new XSSFWorkbook(file.getInputStream());
        for (Sheet sheet : book) {
            Theme theme = new Theme();
            theme.setName(sheet.getSheetName());
            subject.addTheme(theme);
            sheet.forEach(row -> {
                Cell theoryCell = row.getCell(0);
                Cell practiceCell = row.getCell(1);
                if (theoryCell != null) {
                    Theory theory = new Theory();
                    theory.setText(theoryCell.getStringCellValue());
                    theme.addTheory(theory);
                }
                if (practiceCell != null) {
                    Practice practice = new Practice();
                    practice.setText(practiceCell.getStringCellValue());
                    theme.addPractice(practice);
                }
            });
        }
        subjectService.add(subject);
    }
}
