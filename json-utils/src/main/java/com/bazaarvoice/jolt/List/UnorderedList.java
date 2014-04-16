package com.bazaarvoice.jolt.List;

import java.util.List;

/**
 *
 */
public interface UnorderedList<E> extends List<E> {
    boolean equalsIgnoreOrder(Object o);
}
