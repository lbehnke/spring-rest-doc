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
import javax.xml.bind.annotation.XmlType;

/**
 * Model of an API error documentation.
 *
 * @author Lars Behnke
 */
@ApiModelDoc("Model of an API error documentation.")
@XmlRootElement(name = "apiErrorDocModel")
@XmlType(propOrder = {"code", "description"})
public class ApiErrorDocModel {
    private Integer code;
    private String description;

    public ApiErrorDocModel() {
        this(null, null);
    }

    public ApiErrorDocModel(Integer code, String description) {
        super();
        this.code = code;
        this.description = description;
    }

    @XmlAttribute
    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @XmlAttribute
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
