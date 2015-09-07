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
@XmlType(propOrder = {"name", "className", "description", "errorResponseClass", "methods"})
public class ApiDocModel implements Comparable<ApiDocModel> {
    private String name;
    private String className;
    private String description;
    private List<ApiDocModelRef> errorResponses;
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

    @XmlElement(name = "errorResponses", required = false)
    public List<ApiDocModelRef> getErrorResponses() {
        return errorResponses;
    }

    public void setErrorResponses(List<ApiDocModelRef> errorResponses) {
        this.errorResponses = errorResponses;
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
