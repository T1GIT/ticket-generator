package com.generator.app.service;


import com.generator.app.entity.Theme;
import com.generator.app.entity.Theory;
import com.generator.app.util.exception.NotEnoughTaskException;
import com.generator.app.util.abstractService.ServiceInterface;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TheoryService extends ServiceInterface<Theory> {

    List<Theory> getRandom(Theme theme, int amount) throws NotEnoughTaskException;
}
