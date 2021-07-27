package com.generator.app.service;


import com.generator.app.entity.*;
import com.generator.app.util.exception.NotEnoughTaskException;
import com.generator.app.util.abstractService.ServiceInterface;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PracticeService extends ServiceInterface<Practice> {

    List<Practice> getRandom(Theme theme, int amount) throws NotEnoughTaskException;
}
