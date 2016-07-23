/*
 * Copyright 2013 Bazaarvoice, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.bazaarvoice.jolt.chainr.instantiator;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

public final class ObjectBuilder<T> {

    private final Class<T> objectClass;
    private final Map<Class<?>, Object> constructorArgs;

    public static <T> ObjectBuilder<T> ofClass(final Class<T> objectClass) {
        assert objectClass != null;
        return new ObjectBuilder<>( objectClass );
    }

    private ObjectBuilder( final Class<T> objectClass ) {
        this.objectClass = objectClass;
        constructorArgs = new LinkedHashMap<>(  );
    }

    public ObjectBuilder<T> withParam( final Class<?> argType, final Object argValue ) {
        assert argType != null;
        assert argValue != null;
        constructorArgs.put( argType, argValue );
        return this;
    }

    public T build() {
        Class<?>[] constructorArgClasses = constructorArgs.keySet().toArray(new Class<?>[constructorArgs.size()]);
        Object[] constructorArgValues = constructorArgs.values().toArray(new Object[constructorArgs.size()]);
        try {
            Constructor<T> constructor = objectClass.getConstructor( constructorArgClasses );
            return constructor.newInstance( constructorArgValues );
        }
        catch(NoSuchMethodException gex) {
            throw new RuntimeException( "Object builder encountered an exception constructing class:" + objectClass.getCanonicalName() +
                            ".  Specifically, no constructor found with argument Classes" + Arrays.toString( constructorArgClasses ), gex );
        }
        catch(InvocationTargetException|InstantiationException|IllegalAccessException nex) {
            throw new RuntimeException( "Object builder encountered an exception constructing class:" + objectClass.getCanonicalName() +
                            ".  Specifically, cannot construct with constructor argument Values" + Arrays.toString( constructorArgValues ), nex );
        }
    }
}
