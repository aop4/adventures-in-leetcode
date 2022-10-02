package com.andrewpuglionesi.datastructures;

import java.util.Collection;
import java.util.HashMap;
import java.util.Objects;

/**
 * A general-purpose counter data structure. Associates keys with a frequency
 * (stored as a {@link Long}).
 * @param <K> data type of keys in the counter.
 */
@SuppressWarnings("PMD.BeanMembersShouldSerialize")
public final class Counter<K> {
    private final HashMap<K, Long> counterMap;

    public Counter() {
        this.counterMap = new HashMap<>();
    }

    public Counter(Collection<K> collection) {
        this();
        if (collection != null) {
            for (K key : collection) {
                this.increment(key);
            }
        }
    }

    /**
     * Increments the value mapped to {@code key}. If {@code key} is not present,
     * adds it and sets its value to 1.
     * @param key the key whose value to increment.
     */
    public void increment(K key) {
        this.counterMap.put(key, this.counterMap.getOrDefault(key, 0L) + 1);
    }

    /**
     * Decrements the value mapped to {@code key} in {@code counter}. If {@code key} has
     * a value less than 2, removes it from the counter.
     * @param key the character whose value to decrement.
     */
    public void decrement(K key) {
        if (this.counterMap.get(key) == null) {
            return;
        }
        this.counterMap.put(key, this.counterMap.get(key) - 1);
        if (this.counterMap.get(key) <= 0) {
            this.counterMap.remove(key);
        }
    }

    /**
     * Gets the value mapped to {@code key}.
     * @param key K a potential key in the map.
     * @return the value for the key, or else null if it isn't present.
     */
    public Long get(K key) {
        return this.counterMap.get(key);
    }

    /**
     * @return the number of keys in the map.
     */
    public int size() {
        return this.counterMap.size();
    }

    /**
     * @param other another Counter.
     * @return true if the same characters are mapped to the same frequencies
     * in both Counters.
     */
    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (!(other instanceof Counter)) {
            return false;
        }
        HashMap otherMap = ((Counter) other).counterMap;
        return Objects.equals(this.counterMap, otherMap);
    }

    @Override
    public int hashCode() {
        return this.counterMap.hashCode();
    }
}
