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


import com.apporiented.rest.apidoc.annotation.ApiDoc;
import com.apporiented.rest.apidoc.annotation.ApiModelDoc;
import com.apporiented.rest.apidoc.model.ApiDocModel;
import com.apporiented.rest.apidoc.model.ApiDocModels;
import com.apporiented.rest.apidoc.model.ApiModelDocModel;
import com.apporiented.rest.apidoc.model.Documentation;
import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.*;

/**
 * Factory implementation for creating the API documentation.
 * <br>Usage:
 * <pre>
 *      String[] pkgNames = new String[]{
 * "com.apporiented.example.web.rest",
 * "com.apporiented.example.web.rest.error"
 * };
 *     DocumentationFactory df = new DefaultDocumentationFactory();
 *     Documentation d = df.createDocumentation("1.0", "http://www.host.com/api", pkgNames);
 * </pre>
 *
 * @author Lars Behnke
 */
public class DefaultDocumentationFactory implements DocumentationFactory {

    private static Logger log = LoggerFactory.getLogger(DefaultDocumentationFactory.class);

    private ControllerDocumentationFactory ctrlDocFactory;
    private ModelDocumentationFactory modelDocFactory;

    public DefaultDocumentationFactory() {
        this.modelDocFactory = new JAXBModelDocFactory();
        this.ctrlDocFactory = new SpringControllerDocumentationModel(modelDocFactory);
    }

    public DefaultDocumentationFactory(ControllerDocumentationFactory ctrlDocFactory, ModelDocumentationFactory modelDocFactory) {
        this.ctrlDocFactory = ctrlDocFactory;
        this.modelDocFactory = modelDocFactory;
    }


    public Documentation createDocumentation(String version, String basePath, ClassLoader... classloaders) {
        Set<URL> urls = new HashSet<>(ClasspathHelper.forClassLoader(classloaders));
        return createDocumentForUrls(version, basePath, urls);
    }

    public Documentation createDocumentation(String version, String basePath, Class<?>... classes) {
        Set<URL> urls = new HashSet<>();
        for (Class<?> clazz : classes) {
            urls.add(ClasspathHelper.forClass(clazz, clazz.getClassLoader()));
        }
        return createDocumentForUrls(version, basePath, urls);
    }

    public Documentation createDocumentation(String version, String basePath, String... packageNames) {
        Set<URL> urls = new HashSet<>();
        for (String pkgName : packageNames) {
            urls.addAll(ClasspathHelper.forPackage(pkgName, (ClassLoader[]) null));
        }
        return createDocumentForUrls(version, basePath, urls);
    }

    private Documentation createDocumentForUrls(String version, String basePath, Set<URL> urls) {
        Reflections reflections = new Reflections(new ConfigurationBuilder().setUrls(urls));
        Documentation documentation = new Documentation(version, basePath);
        Set<Class<?>> classes = new HashSet<>();
        classes.addAll(reflections.getTypesAnnotatedWith(ApiDoc.class));
        ApiDocModels apiDocModels = createApiDocModels(classes);

        List<ApiDocModel> apis = new ArrayList<>(apiDocModels.getApis());
        Collections.sort(apis);
        documentation.setApis(apis);

        classes = new HashSet<>();
        classes.addAll(reflections.getTypesAnnotatedWith(ApiModelDoc.class));
        classes.addAll(apiDocModels.getModelClasses());

        List<ApiModelDocModel> dtos = new ArrayList<>(createApiObjectDocs(classes));
        Collections.sort(dtos);
        documentation.setDtos(dtos);
        return documentation;
    }

    private ApiDocModels createApiDocModels(Set<Class<?>> ctrlClasses) {
        ApiDocModels apiDocModels = new ApiDocModels();
        for (Class<?> ctrlClass : ctrlClasses) {
            try {
                ApiDocModel api = ctrlDocFactory.createApiDocModel(ctrlClass);
                apiDocModels.addApiModel(api);
            } catch (Exception e) {
                log.error("Could not create API documentation for controller class " + ctrlClass, e);
            }
        }
        return apiDocModels;
    }

    private Set<ApiModelDocModel> createApiObjectDocs(Set<Class<?>> dtoClasses) {
        Set<ApiModelDocModel> dtoClassDocs = new TreeSet<>();
        for (Class<?> dtoClass : dtoClasses) {
            if (dtoClass != null) {
                try {
                    ApiModelDocModel m = modelDocFactory.createModelDocModel(dtoClass);
                    if (m != null) {
                        dtoClassDocs.add(m);
                    }
                } catch (Exception e) {
                    log.error("Could not create API documentation for DTO class " + dtoClass, e);
                }
            } else {
                log.warn("DTO list contains NULL value.");
            }
        }
        return dtoClassDocs;
    }


}
