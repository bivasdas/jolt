package com.bazaarvoice.jolt.List;

import java.util.List;

/**
 *
 */
public final class UnOrderedListUtils {

    public static final boolean equalsIgnoreOrder(final Object a, final Object b) {
        if (a == b)
            return true;

        if (!(a instanceof List) || !(b instanceof List))
            return false;

        final List thisList = (List) a;
        final List thatList = (List) b;

        if(thisList.size() != thatList.size())
            return false;

        for(Object o: thisList) {
            if(!thatList.contains(o))
                return false;
        }
        return true;
    }
}
