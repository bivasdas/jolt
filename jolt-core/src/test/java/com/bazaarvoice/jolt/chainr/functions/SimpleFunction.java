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
package com.bazaarvoice.jolt.chainr.functions;

import com.bazaarvoice.jolt.common.Optional;
import com.bazaarvoice.jolt.modifier.function.Function;

@SuppressWarnings( "unused" )
public class SimpleFunction implements Function {
    @Override
    public Optional<Object> apply( final Object... args ) {
        return Optional.<Object>of( args.length );
    }

    public static class InnerClassFunction implements Function {
        @Override
        public Optional<Object> apply( final Object... args ) {
            return Optional.<Object>of( args.length + 1 );
        }
    }
}
