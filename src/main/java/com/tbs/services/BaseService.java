package com.tbs.services;

import lombok.Getter;
import com.tbs.entity.BaseClass;
import com.tbs.repository.BaseRepository;
import com.tbs.services.serviceInterfaces.IBaseService;

import java.util.List;
import java.util.Optional;

@Getter
public abstract class BaseService<E extends BaseClass> implements IBaseService<E> {
    protected final BaseRepository<E> repository;

    public BaseService(BaseRepository<E> repository) {
        this.repository = repository;
    }

    public E create(E entity) {
        return repository.save(entity);
    }

    public E update(E entity) {
        return repository.update(entity);
    }

    public void delete(Long id) {
        repository.delete(id);
    }

    public List<E> findAll() {
        return repository.findAll();
    }

    public E findById(Long id) {
        Optional<E> entity = repository.findById(id);
        if (entity.isEmpty()) {
            throw new IllegalArgumentException("entity not found with the provided ID" + id);
        }
        return entity.get();
    }
}
