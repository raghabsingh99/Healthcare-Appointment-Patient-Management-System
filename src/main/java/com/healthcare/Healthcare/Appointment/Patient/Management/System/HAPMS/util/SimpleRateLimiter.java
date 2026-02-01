package com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.util;

import org.springframework.stereotype.Component;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SimpleRateLimiter {

    private static final Map<String, Deque<Long>> hits = new ConcurrentHashMap<>();
    private static final long windows = 60_000;
    private static final int maxHits = 30;

    public static boolean allow(String key){
        long now = System.currentTimeMillis();
        hits.putIfAbsent(key,new ArrayDeque<>());
        Deque<Long> longDeque = hits.get(key);

        synchronized (longDeque){
            while (!longDeque.isEmpty() && now - longDeque.peekFirst() > windows) longDeque.peekFirst();
            if(longDeque.size() >= maxHits) return false;
            longDeque.add(now);
            return true;

        }
    }
}
