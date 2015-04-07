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

package com.apporiented.rest.apidoc.factory;


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
