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
 * This annotations is to be used inside an annotations of type ApiHeadersDoc
 *
 * @author Fabio Maffioletti
 * @author Lars Behnke
 * @see ApiHeadersDoc
 */
@Documented
@Target(value = ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiHeaderDoc {

    /**
     * The name of the header parameter
     *
     * @return
     */
    public String name();

    /**
     * A description of what the parameter is needed for
     *
     * @return
     */
    public String description();

}
