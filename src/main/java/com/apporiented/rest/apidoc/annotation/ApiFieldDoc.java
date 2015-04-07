/*
 * Copyright 2002-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.apporiented.rest.apidoc.annotation;

import java.lang.annotation.*;

/**
 * This annotations is to be used on your objects' fields and represents a field of an object
 *
 * @author Fabio Maffioletti
 * @author Lars Behnke
 */
@Documented
@Target(value = {ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiFieldDoc {

    /**
     * A description of what the field is
     *
     * @return
     */
    public String value() default "";

    /**
     * The format pattern for this field
     *
     * @return
     */
    public String format() default "";

    /**
     * The allowed values for this field
     *
     * @return
     */
    public String[] allowedValues() default {};

}
