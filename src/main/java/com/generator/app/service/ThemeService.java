package com.generator.app.service;


import com.generator.app.entity.Subject;
import com.generator.app.entity.Theme;
import com.generator.app.util.abstractService.ServiceInterface;
import org.springframework.stereotype.Service;

@Service
public interface ThemeService extends ServiceInterface<Theme> {

    void deleteBySubject(Subject subject);
}
