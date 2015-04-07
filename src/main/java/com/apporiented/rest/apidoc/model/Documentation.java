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

import com.apporiented.rest.apidoc.annotation.ApiFieldDoc;
import com.apporiented.rest.apidoc.annotation.ApiModelDoc;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * Model of the documentation of the complete REST API including used data transfer objects.
 *
 * @author Lars Behnke
 */
@ApiModelDoc("Documentation model of the complete REST API including used data transfer objects.")
@XmlRootElement(name = "documentation")
@XmlType(propOrder = {"version", "basePath", "apis", "dtos"})
public class Documentation {

    private String version;

    private String basePath;

    private List<ApiDocModel> apis;

    private List<ApiModelDocModel> dtos;

    public Documentation() {
        this(null, null);
    }

    public Documentation(String version, String basePath) {
        super();
        this.version = version;
        this.basePath = basePath;
    }


    @ApiFieldDoc("API version")
    @XmlAttribute(name = "version", required = false)
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @XmlElementWrapper(name = "apis")
    @XmlElement(name = "api")
    public List<ApiDocModel> getApis() {
        return apis;
    }

    public void setApis(List<ApiDocModel> apis) {
        this.apis = apis;
    }

    @ApiFieldDoc("Data transfer dtos.")
    @XmlElementWrapper(name = "dtos")
    @XmlElement(name = "dto")
    public List<ApiModelDocModel> getDtos() {
        return dtos;
    }

    public void setDtos(List<ApiModelDocModel> dtos) {
        this.dtos = dtos;
    }

    @ApiFieldDoc("Base path")
    @XmlAttribute(name = "basePath")
    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

}
