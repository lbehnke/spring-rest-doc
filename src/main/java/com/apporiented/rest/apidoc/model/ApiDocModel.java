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

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Representation of an REST resource documentation.
 *
 * @author Lars Behnke
 */

@ApiModelDoc("REST resource documentation.")
@XmlRootElement(name = "apiDoc")
@XmlType(propOrder = {"name", "className", "description", "methods"})
public class ApiDocModel implements Comparable<ApiDocModel> {
    private String name;
    private String className;
    private String description;
    private List<ApiMethodDocModel> methods;
    private Set<Class<?>> modelClasses;

    public ApiDocModel() {
        this.methods = new ArrayList<>();
        this.modelClasses = new HashSet<>();

    }

    @XmlAttribute(name = "name", required = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlAttribute(name = "className", required = true)
    public String getClassName() {
        return className;
    }

    public void setClassName(String name) {
        this.className = name;
    }

    @XmlElement(name = "description", required = false)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @XmlElementWrapper(name = "methods")
    @XmlElement(name = "method", required = false)
    public List<ApiMethodDocModel> getMethods() {
        return methods;
    }

    public void setMethods(List<ApiMethodDocModel> methods) {
        this.methods = methods;
    }


    @XmlTransient
    public Set<Class<?>> getModelClasses() {
        return modelClasses;
    }

    public void setModelClasses(Set<Class<?>> modelClasses) {
        this.modelClasses = modelClasses;
    }

    @Override
    public int compareTo(ApiDocModel o) {
        return name.compareTo(o.getName());
    }


}
