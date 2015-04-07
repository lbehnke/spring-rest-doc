package com.apporiented.rest.apidoc.factory;


import com.apporiented.rest.apidoc.model.ApiDocModel;

/**
 * Contract for factory implementations that provide a controller (REST resource) documentation.
 *
 * @author Lars Behnke
 */
public interface ControllerDocumentationFactory {

    public ApiDocModel createApiDocModel(Class<?> ctrlClass);

}
