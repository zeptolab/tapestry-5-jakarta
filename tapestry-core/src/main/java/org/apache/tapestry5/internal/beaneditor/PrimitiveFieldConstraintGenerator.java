// Copyright 2007 The Apache Software Foundation
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package org.apache.tapestry5.internal.beaneditor;

import org.apache.tapestry5.ioc.AnnotationProvider;
import org.apache.tapestry5.services.ValidationConstraintGenerator;

import java.util.Arrays;
import java.util.List;

/**
 * Adds a "required" constraint for any property of whose type is a primitive (not a wrapper or reference) type.
 */
public class PrimitiveFieldConstraintGenerator implements ValidationConstraintGenerator
{
    private final List<String> REQUIRED = Arrays.asList("required");

    @Override
    public List<String> buildConstraints(Class<?> propertyType, AnnotationProvider annotationProvider)
    {
        return propertyType.isPrimitive() && !"boolean".equals(propertyType.getName()) ? REQUIRED : null;
    }

}
