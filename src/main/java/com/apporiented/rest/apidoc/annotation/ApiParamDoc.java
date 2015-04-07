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
 * This annotations is to be used inside an annotations of type ApiParams
 *
 * @author Fabio Maffioletti
 * @author Lars Behnke
 * @see com.apporiented.rest.apidoc.model.ApiParamDocModel
 */
@Documented
@Target(value = ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiParamDoc {

    /**
     * A description of what the parameter is needed for
     *
     * @return
     */
    public String value() default "";

    /**
     * An array representing the allowed values this parameter can have. Default value is *
     *
     * @return
     */
    public String[] allowedValues() default {};

    /**
     * The format of the parameter (ex. yyyy-MM-dd HH:mm:ss, ...)
     *
     * @return
     */
    public String format() default "";

    /**
     * The data type of the parameter. Corresponds to lowercase java type.
     *
     * @return
     */
    public String dataType() default "";

}
