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

/**
 * Model of the documentation of a DTO field.
 *
 * @author Lars Behnke
 */
@ApiModelDoc("Model of the documentation of a DTO field.")
@XmlRootElement(name = "apiModelFieldDocModel")
@XmlType(propOrder = {"name", "type", "multiple", "format", "description", "allowedValues", "mapKeyObject", "mapValueObject", "map", "xmlNodeType"})
public class ApiModelFieldDocModel implements Comparable<ApiModelFieldDocModel> {
    private String name;
    private String type;
    private Boolean multiple;
    private String description;
    private String format;
    private String[] allowedValues;
    private String mapKeyObject;
    private String mapValueObject;
    private Boolean map;
    private Boolean required;
    private String xmlNodeType;

    public ApiModelFieldDocModel() {
        super();
    }

    @XmlAttribute
    public Boolean isRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    public String getMapKeyObject() {
        return mapKeyObject;
    }

    public void setMapKeyObject(String mapKeyObject) {
        this.mapKeyObject = mapKeyObject;
    }

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

    @XmlElementWrapper(name = "allowedValues", required = false)
    @XmlElement(name = "value")
    public String[] getAllowedValues() {
        return allowedValues;
    }


    public void setAllowedValues(String[] allowedValues) {
        this.allowedValues = allowedValues;
    }

    @XmlAttribute
    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    @XmlAttribute
    public Boolean getMultiple() {
        return multiple;
    }

    public void setMultiple(Boolean multiple) {
        this.multiple = multiple;
    }

    @XmlAttribute
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @XmlAttribute
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @XmlAttribute(required = false)
    public String getXmlNodeType() {
        return xmlNodeType;
    }

    public void setXmlNodeType(String xmlNodeType) {
        this.xmlNodeType = xmlNodeType;
    }

    public int compareTo(ApiModelFieldDocModel o) {
        return getName().compareTo(o.getName());
    }

}
