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

package com.apporiented.rest.apidoc.factory;


import com.apporiented.rest.apidoc.model.ApiDocModelRef;
import com.apporiented.rest.apidoc.model.ApiModelDocModel;

import java.lang.reflect.Type;

/**
 * Contract for documentation factory that create a documentation for model classes used
 * by the API.
 *
 * @author Lars Behnke
 */

public interface ModelDocumentationFactory {
    public ApiModelDocModel createModelDocModel(Class<?> clazz);

    public ApiDocModelRef createModelRef(Class<?> objectType, Type genericObjectType);
}
