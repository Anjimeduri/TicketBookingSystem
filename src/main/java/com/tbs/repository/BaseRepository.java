package com.tbs.repository;

import lombok.extern.slf4j.Slf4j;
import com.tbs.entity.BaseClass;
import com.tbs.exceptions.NotFoundException;
import com.tbs.utils.IdGenerator;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public abstract class BaseRepository<T extends BaseClass> {
    // Here we considered Id will always be long so directly defined inside this
    // if we are unsure about the id can be of diff types we need to get in params as generic
    protected final Map<Long, T> storage = new ConcurrentHashMap<>();

    public void saveAll(List<T> entityList) {
        synchronized (storage) {
            entityList.forEach(this::save);
        }
    }

    public T save(T entity) {
        synchronized (storage) {
            log.info("Creating an entity {}", entity.getClass().getSimpleName());
            if (entity.getId() == null) {
                entity.setId(IdGenerator.generateSequence(entity.getClass()));
            }
            storage.put(entity.getId(), entity);
            log.info("Created an entity {} With id {}", entity.getClass().getSimpleName(), entity.getId());
            return entity;
        }
    }

    public void delete(Long id) {
        synchronized (storage) {
            if (!storage.containsKey(id)) {
                throw new NotFoundException("Entity with ID " + id + " not found");
            }
            storage.remove(id);
        }
    }

    public T update(T entity) {
        Objects.requireNonNull(entity, "Entity can't be null");
        synchronized (storage) {
            log.info("updating an entity {} with id {}", entity.getClass().getName(), entity.getId());

            Long id = entity.getId();
            if (id == null || !storage.containsKey(id)) {
                throw new NotFoundException("Entity with ID " + id + " not found");
            }
            storage.put(id, entity);
            log.info("updated an entity {} with id {} successfully", entity.getClass().getName(), entity.getId());
            return entity;
        }
    }

    public Optional<T> findById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    public List<T> findAll() {
        return storage.values().stream().toList();
    }
}
