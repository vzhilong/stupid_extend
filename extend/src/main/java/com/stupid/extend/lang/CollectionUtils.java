package com.stupid.extend.lang;

import java.util.Collection;

/**
 * Created by vincent on 16/6/4.
 */
public class CollectionUtils {

    public static <T> boolean isEmpty(Collection<T> collection) {
        return collection == null || collection.isEmpty();
    }

    public static <T> boolean isNotEmpty(Collection<T> collection) {
        return !isEmpty(collection);
    }

    public static <T> int size(Collection<T> collection) {
        return collection == null ? 0 : collection.size();
    }
}
