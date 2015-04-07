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
