package org.tbs.services.serviceInterfaces;

import org.tbs.entity.BaseClass;

import java.util.List;

public interface IBaseService<E extends BaseClass> {
    E create(E entity);

    E update(E entity);

    void delete(Long id);

    E findById(Long id);

    List<E> findAll();
}
