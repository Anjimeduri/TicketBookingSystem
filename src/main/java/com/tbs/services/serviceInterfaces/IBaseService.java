package com.tbs.services.serviceInterfaces;

import com.tbs.entity.BaseClass;

import java.util.List;

public interface IBaseService<E extends BaseClass> {
    E create(E entity);

    E update(E entity);

    void delete(Long id);

    E findById(Long id);

    List<E> findAll();
}
