package com.bazaarvoice.jolt.List;

import java.util.ArrayList;

/**
 *
 */
public class ArrayUnorderedList<E> extends ArrayList<E> implements UnorderedList<E> {

    @Override
    public boolean equalsIgnoreOrder(final Object o) {
        return UnOrderedListUtils.equalsIgnoreOrder(this, o);
    }
}
