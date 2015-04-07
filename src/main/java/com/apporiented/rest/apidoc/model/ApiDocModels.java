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
