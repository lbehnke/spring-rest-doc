package com.apporiented.rest.apidoc.factory.impl;


import com.apporiented.rest.apidoc.ConfigurationException;
import com.apporiented.rest.apidoc.annotation.ApiDoc;
import com.apporiented.rest.apidoc.annotation.ApiModelDoc;
import com.apporiented.rest.apidoc.factory.ControllerDocumentationFactory;
import com.apporiented.rest.apidoc.factory.DocumentationFactory;
import com.apporiented.rest.apidoc.factory.ModelDocumentationFactory;
import com.apporiented.rest.apidoc.model.ApiDocModel;
import com.apporiented.rest.apidoc.model.ApiDocModels;
import com.apporiented.rest.apidoc.model.ApiModelDocModel;
import com.apporiented.rest.apidoc.model.Documentation;
import com.google.common.base.Strings;
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
        this.ctrlDocFactory = new SpringControllerDocumentationFactory(modelDocFactory);
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
            } catch (ConfigurationException e) {
                throw e;
            } catch (Exception e) {
                throw new ConfigurationException("Could not create API documentation for controller class " + ctrlClass);
            }
        }
        return apiDocModels;
    }

    private Set<ApiModelDocModel> createApiObjectDocs(Set<Class<?>> dtoClasses) {
        Set<ApiModelDocModel> dtoClassDocs = new TreeSet<>();
        Set<String> dtoNames = new HashSet<>();
        for (Class<?> dtoClass : dtoClasses) {
            if (dtoClass != null && !dtoClass.equals(void.class)) {
                try {
                    ApiModelDocModel m = modelDocFactory.createModelDocModel(dtoClass);
                    if (m != null) {
                        if (Strings.isNullOrEmpty(m.getName())) {
                            throw new ConfigurationException("Missing DTO name, class " + m.getClassName());
                        }
                        if (dtoNames.contains(m.getName().toUpperCase())) {
                            throw new ConfigurationException("Duplicate DTO name: " + m.getName() + ", class " + m.getClassName());
                        }
                        dtoNames.add(m.getName().toUpperCase());
                        dtoClassDocs.add(m);
                    }
                } catch (ConfigurationException e) {
                    throw e;
                } catch (Exception e) {
                    throw new ConfigurationException("Could not create API documentation for DTO class " + dtoClass, e);
                }
            } else {
                log.warn("DTO list contains NULL value.");
            }
        }
        return dtoClassDocs;
    }


}
