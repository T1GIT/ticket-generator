package com.generator.app.service.impl;

import com.generator.app.entity.Subject;
import com.generator.app.repository.SubjectRepository;
import com.generator.app.service.SubjectService;
import com.generator.app.util.abstractService.impl.ServiceInterfaceImpl;
import org.springframework.stereotype.Service;

@Service
public class SubjectServiceImpl extends ServiceInterfaceImpl<Subject, SubjectRepository> implements SubjectService {
    public SubjectServiceImpl(SubjectRepository repository) {
        super(repository);
    }

    @Override
    public Subject getByName(String name) {
        return repository.findByName(name);
    }
}
