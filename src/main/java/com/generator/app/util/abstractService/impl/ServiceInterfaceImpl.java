package com.generator.app.util.abstractService.impl;

import com.generator.app.util.AbstractEntity;
import com.generator.app.util.abstractService.ServiceInterface;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public abstract class ServiceInterfaceImpl
        <EntityClass extends AbstractEntity,
                repositoryClass extends JpaRepository<EntityClass, Long>>
        implements ServiceInterface<EntityClass> {

    protected final repositoryClass repository;

    public ServiceInterfaceImpl(repositoryClass repository) {
        this.repository = repository;
    }

    @Override
    public EntityClass getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public EntityClass add(EntityClass model) {
        return repository.saveAndFlush(model);
    }

    @Override
    public void delete(EntityClass model) {
        repository.delete(model);
    }

    @Override
    public EntityClass update(EntityClass model) {
        return repository.saveAndFlush(model);
    }

    @Override
    public List<EntityClass> getAll() {
        return repository.findAll();
    }
}
