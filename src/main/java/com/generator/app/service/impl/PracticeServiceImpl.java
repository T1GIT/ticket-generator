package com.generator.app.service.impl;

import com.generator.app.entity.*;
import com.generator.app.repository.PracticeRepository;
import com.generator.app.service.PracticeService;
import com.generator.app.util.exception.NotEnoughTaskException;
import com.generator.app.util.abstractService.impl.ServiceInterfaceImpl;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class PracticeServiceImpl extends ServiceInterfaceImpl<Practice, PracticeRepository> implements PracticeService {

    public PracticeServiceImpl(PracticeRepository repository) {
        super(repository);
    }

    @Override
    public List<Practice> getRandom(Theme theme, int amount) throws NotEnoughTaskException {
        List<Practice> practices = repository.findAllByThemeId(theme.getId());
        Collections.shuffle(practices);
        if (practices.size() < amount)
            throw new NotEnoughTaskException();
        return practices.subList(0, amount);
    }
}
