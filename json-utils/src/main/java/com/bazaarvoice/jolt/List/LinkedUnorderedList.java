package com.bazaarvoice.jolt.List;

import java.util.LinkedList;

/**
 *
 */
public class LinkedUnorderedList<E> extends LinkedList<E> implements UnorderedList<E> {

    @Override
    public boolean equalsIgnoreOrder(final Object o) {
        return UnOrderedListUtils.equalsIgnoreOrder(this, o);
    }

}
