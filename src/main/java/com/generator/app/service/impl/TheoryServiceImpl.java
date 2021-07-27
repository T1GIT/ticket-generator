package com.generator.app.service.impl;

import com.generator.app.entity.*;
import com.generator.app.repository.TheoryRepository;
import com.generator.app.service.TheoryService;
import com.generator.app.util.exception.NotEnoughTaskException;
import com.generator.app.util.abstractService.impl.ServiceInterfaceImpl;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class TheoryServiceImpl extends ServiceInterfaceImpl<Theory, TheoryRepository> implements TheoryService {

    public TheoryServiceImpl(TheoryRepository repository) {
        super(repository);
    }

    @Override
    public List<Theory> getRandom(Theme theme, int amount) throws NotEnoughTaskException {
        List<Theory> theories = repository.findAllByThemeId(theme.getId());
        Collections.shuffle(theories);
        if (theories.size() < amount)
            throw new NotEnoughTaskException();
        return theories.subList(0, amount);
    }
}
