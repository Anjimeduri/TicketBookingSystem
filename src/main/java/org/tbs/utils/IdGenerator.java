package org.tbs.utils;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

// To prevent inheritance. Should not be instantiated
public final class IdGenerator {
    private IdGenerator() {
        throw new UnsupportedOperationException();
    }

    // For thread safety updated to concurrent hashmap
    private static final ConcurrentMap<Class<?>, AtomicLong> CLASS_SEQUENCE_MAP = new ConcurrentHashMap<>();

    public static Long generateSequence(Class<?> entityClass) {
        return CLASS_SEQUENCE_MAP
                .computeIfAbsent(entityClass, k -> new AtomicLong(1))
                .getAndIncrement();
    }
}
