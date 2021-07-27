package com.generator.app.service.impl;

import com.generator.app.entity.Subject;
import com.generator.app.entity.Theme;
import com.generator.app.repository.ThemeRepository;
import com.generator.app.service.ThemeService;
import com.generator.app.util.abstractService.impl.ServiceInterfaceImpl;
import org.springframework.stereotype.Service;

@Service
public class ThemeServiceImpl extends ServiceInterfaceImpl<Theme, ThemeRepository> implements ThemeService {
    public ThemeServiceImpl(ThemeRepository repository) {
        super(repository);
    }

    @Override
    public void deleteBySubject(Subject subject) {
        repository.deleteAllBySubjectId(subject.getId());
    }
}
