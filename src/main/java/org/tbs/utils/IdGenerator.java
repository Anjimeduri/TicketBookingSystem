package org.tbs.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class IdGenerator {
    private static final Map<Class<?>, AtomicLong> classSequenceMap = new HashMap<>();

    public static Long generateSequence(Class<?> entityClass) {
        AtomicLong currentSeqId = classSequenceMap.computeIfAbsent(entityClass, k -> new AtomicLong(1));

        return currentSeqId.getAndIncrement();
    }
}
