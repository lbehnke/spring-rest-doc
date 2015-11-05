package com.apporiented.rest.apidoc.factory;


import com.apporiented.rest.apidoc.ConfigurationException;
import com.apporiented.rest.apidoc.model.Documentation;

/**
 * Contract for factory implementations that provide a controller (REST resource) documentation.
 *
 * @author Lars Behnke
 */
public interface DocumentationFactory {

    public Documentation createDocumentation(String version, String basePath, ClassLoader... classloaders);

    public Documentation createDocumentation(String version, String basePath, Class<?>... classes);

    public Documentation createDocumentation(String version, String basePath, String... packageNames);
}
