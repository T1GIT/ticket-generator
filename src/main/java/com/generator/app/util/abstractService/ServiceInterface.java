package com.generator.app.util.abstractService;

import com.generator.app.util.AbstractEntity;

import java.util.List;


public interface ServiceInterface<EntityClass extends AbstractEntity> {

    EntityClass getById(Long id);

    EntityClass add(EntityClass model);

    void delete(EntityClass model);

    EntityClass update(EntityClass model);

    List<EntityClass> getAll();
}
