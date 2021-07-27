package com.generator.app.service;


import com.generator.app.entity.Subject;
import com.generator.app.util.abstractService.ServiceInterface;
import org.springframework.stereotype.Service;

@Service
public interface SubjectService extends ServiceInterface<Subject> {

    Subject getByName(String name);
}
