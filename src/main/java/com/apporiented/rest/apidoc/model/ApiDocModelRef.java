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

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

/**
 * Representation of a model reference.
 *
 * @author Lars Behnke
 * @apiviz.owns java.lang.Class - modelClasses
 */
@ApiModelDoc("Representation of a model reference.")
@XmlRootElement(name = "apiBodyObjectDocModel")
@XmlType(propOrder = {"modelRef", "multiple", "mapKeyObject", "mapValueObject", "map"})
public class ApiDocModelRef {
    private String modelRef;
    private Boolean multiple;
    private String mapKeyObject;
    private String mapValueObject;
    private Boolean map;
    private Class<?> modelClass;


    public ApiDocModelRef() {
        this(null, null, null, null, null, null);
    }

    public ApiDocModelRef(Class<?> modelClass, String modelRef, String mapKeyObject, String mapValueObject, Boolean multiple, Boolean map) {
        super();
        this.modelRef = modelRef;
        this.multiple = multiple;
        this.mapKeyObject = mapKeyObject;
        this.mapValueObject = mapValueObject;
        this.map = map;
        this.modelClass = modelClass;
    }

    @XmlAttribute
    public String getModelRef() {
        return modelRef;
    }

    public void setModelRef(String ref) {
        this.modelRef = ref;
    }

    @XmlAttribute
    public Boolean getMultiple() {
        return multiple;
    }

    public void setMultiple(Boolean multiple) {
        this.multiple = multiple;
    }

    @XmlAttribute
    public String getMapKeyObject() {
        return mapKeyObject;
    }

    public void setMapKeyObject(String mapKeyObject) {
        this.mapKeyObject = mapKeyObject;
    }

    @XmlAttribute
    public String getMapValueObject() {
        return mapValueObject;
    }

    public void setMapValueObject(String mapValueObject) {
        this.mapValueObject = mapValueObject;
    }

    @XmlAttribute
    public Boolean getMap() {
        return map;
    }

    public void setMap(Boolean map) {
        this.map = map;
    }

    @XmlTransient
    public Class<?> getModelClass() {
        return modelClass;
    }

    public void setModelClass(Class<?> modelClass) {
        this.modelClass = modelClass;
    }


}