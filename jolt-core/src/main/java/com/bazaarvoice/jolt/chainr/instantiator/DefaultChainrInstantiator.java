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

import com.bazaarvoice.jolt.JoltTransform;
import com.bazaarvoice.jolt.chainr.spec.ChainrEntry;
import com.bazaarvoice.jolt.exception.SpecException;
import com.bazaarvoice.jolt.modifier.function.Function;

import java.util.HashMap;
import java.util.Map;

/**
 * Loads classes via Java Reflection APIs.
 */
public class DefaultChainrInstantiator implements ChainrInstantiator {

    @Override
    public JoltTransform hydrateTransform( ChainrEntry entry ) {

        Object spec = entry.getSpec();
        Class<? extends JoltTransform> transformClass = entry.getJoltTransformClass();

        try {
            if(entry.hasFunctions()) {
                Map<String, Class<? extends Function>> functionClassMap = entry.getFunctionClassMap();
                Map<String, Function> functionMap = new HashMap<>(  );
                for(Map.Entry<String, Class<? extends Function>> functionEntry: functionClassMap.entrySet()) {
                    Function function = ObjectBuilder.ofClass( functionEntry.getValue() ).build();
                    functionMap.put( functionEntry.getKey(), function );
                }
                return ObjectBuilder.ofClass( transformClass ).withParam( Object.class, spec ).withParam( Map.class, functionMap ).build();
            }
            // If the transform class is a SpecTransform, we try to construct it with the provided spec.
            else if ( entry.isSpecDriven() ) {
                return ObjectBuilder.ofClass( transformClass ).withParam( Object.class, spec ).build();
            }
            else {
                return ObjectBuilder.ofClass( transformClass ).build();
            }
        } catch ( Exception e ) {
            // FYI 3 exceptions are known to be thrown here
            // IllegalAccessException, InvocationTargetException, InstantiationException
            throw new SpecException( "JOLT Chainr encountered an exception constructing Transform className:"
                    + transformClass.getCanonicalName() + entry.getErrorMessageIndexSuffix(), e );
        }
    }
}
