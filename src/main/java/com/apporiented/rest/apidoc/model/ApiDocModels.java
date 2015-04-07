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

package com.apporiented.rest.apidoc.model;

import com.apporiented.rest.apidoc.annotation.ApiModelDoc;

import java.util.HashSet;
import java.util.Set;

/**
 * Model of a documentation of all REST resources.
 *
 * @author Lars Behnke
 */

@ApiModelDoc("Model of the documentation of all REST resources")
public class ApiDocModels {

    private Set<ApiDocModel> apis;
    private Set<Class<?>> modelClasses;

    public ApiDocModels() {
        this.apis = new HashSet<>();
        this.modelClasses = new HashSet<>();
    }

    public Set<ApiDocModel> getApis() {
        return apis;
    }

    public Set<Class<?>> getModelClasses() {
        return modelClasses;
    }

    public void addApiModel(ApiDocModel m) {
        if (m != null) {
            apis.add(m);
            if (m.getModelClasses() != null) {
                modelClasses.addAll(m.getModelClasses());
            }
        }
    }
}
