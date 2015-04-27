package com.apporiented.rest.apidoc.factory;

import com.apporiented.rest.apidoc.annotation.*;
import com.apporiented.rest.apidoc.model.*;
import com.apporiented.rest.apidoc.utils.ApiDocConstants;
import com.apporiented.rest.apidoc.utils.ApiParamType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


/**
 * Implementation of a factory that creates documentation for REST resources (controllers) based on internal annotations
 * and Spring MVC annotations.
 *
 * @author Lars Behnke
 */
public class SpringControllerDocumentationModel implements ControllerDocumentationFactory {

    private ModelDocumentationFactory modelDocumentationFactory;

    public SpringControllerDocumentationModel(ModelDocumentationFactory modelDocumentationFactory) {
        this.modelDocumentationFactory = modelDocumentationFactory;
    }

    public ApiDocModel createApiDocModel(Class<?> ctrlClass) {
        ApiDoc doc = ctrlClass.getAnnotation(ApiDoc.class);
        if (doc == null) {
            return null;
        }

        ApiDocModel apiDoc = new ApiDocModel();
        apiDoc.setDescription(doc.description());
        apiDoc.setName(doc.name());
        apiDoc.setClassName(ctrlClass.getSimpleName());
        List<ApiMethodDocModel> methods = new ArrayList<>(createApiMethodDocs(ctrlClass));
        Collections.sort(methods);
        apiDoc.setMethods(methods);

        for (ApiMethodDocModel apiMethodDocModel : apiDoc.getMethods()) {
            if (apiMethodDocModel.getResponse() != null) {
                apiDoc.getModelClasses().add(apiMethodDocModel.getResponse().getModelClass());
            }
            if (apiMethodDocModel.getBodyObject() != null) {
                apiDoc.getModelClasses().add(apiMethodDocModel.getBodyObject().getModelClass());
            }
        }
        return apiDoc;
    }

    private List<ApiMethodDocModel> createApiMethodDocs(Class<?> controllerClass) {

        boolean restController = controllerClass.getAnnotation(RestController.class) != null;

        List<ApiMethodDocModel> apiMethodDocModels = new ArrayList<>();
        Method[] methods = controllerClass.getMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(ApiMethodDoc.class)) {
                Class<?> returnType = method.getReturnType();

                ApiMethodDoc apiAnnotation = method.getAnnotation(ApiMethodDoc.class);
                RequestMapping classMapping = controllerClass.getAnnotation(RequestMapping.class);
                RequestMapping methodMapping = method.getAnnotation(RequestMapping.class);
                ResponseBody responseBody = method.getAnnotation(ResponseBody.class);
                ApiHeadersDoc headers = method.getAnnotation(ApiHeadersDoc.class);
                ApiErrorsDoc errors = method.getAnnotation(ApiErrorsDoc.class);
                Deprecated depr = method.getAnnotation(Deprecated.class);

                StringBuffer path = new StringBuffer();
                appendMappedPath(classMapping, path);
                appendMappedPath(methodMapping, path);

                ApiMethodDocModel apiMethodDocModel = new ApiMethodDocModel();
                apiMethodDocModel.setPath(path.toString());
                apiMethodDocModel.setAction(StringUtils.arrayToCommaDelimitedString(methodMapping.method()));
                apiMethodDocModel.setDescription(apiAnnotation.value());
                apiMethodDocModel.setHeaders(createHeaderDocModels(headers));
                apiMethodDocModel.setProduces(getProducesList(classMapping, methodMapping));
                apiMethodDocModel.setConsumes(getConsumesList(classMapping, methodMapping));
                apiMethodDocModel.setParameters(createApiParamDocs(method));
                apiMethodDocModel.setBodyObject(createBodyObjectModel(method));
                apiMethodDocModel.setHint(createHint(depr));
                apiMethodDocModel.setMethodName(method.getName());

                if (restController
                        || responseBody != null
                        || ResponseEntity.class.isAssignableFrom(returnType)
                        || DeferredResult.class.isAssignableFrom(returnType)) {
                    apiMethodDocModel.setResponse(getReturnObject(method));

                    if (DeferredResult.class.isAssignableFrom(returnType)) {
                        apiMethodDocModel.setAsync(true);
                    }
                }

                apiMethodDocModel.setApiErrors(createErrorDoc(errors));

                apiMethodDocModels.add(apiMethodDocModel);
            }

        }
        return apiMethodDocModels;
    }

    private String createHint(Deprecated deprecated) {
        StringBuilder sb = new StringBuilder();
        if (deprecated != null) {
            sb.append(ApiDocConstants.DEPRECATED);
        }
        return sb.length() > 0 ? sb.toString() : null;
    }


    private List<ApiHeaderDocModel> createHeaderDocModels(ApiHeadersDoc annotation) {
        List<ApiHeaderDocModel> headers = new ArrayList<>();
        if (annotation != null && annotation.value().length > 0) {
            headers = new ArrayList<>();
            for (ApiHeaderDoc apiHeaderDoc : annotation.value()) {
                headers.add(new ApiHeaderDocModel(apiHeaderDoc.name(), apiHeaderDoc.description()));
            }
        }
        return headers.size() > 0 ? headers : null;
    }

    private List<ApiErrorDocModel> createErrorDoc(ApiErrorsDoc annotation) {
        List<ApiErrorDocModel> errors = null;
        if (annotation != null) {
            errors = new ArrayList<>();
            for (ApiErrorDoc apiErrorDoc : annotation.value()) {
                errors.add(new ApiErrorDocModel(apiErrorDoc.code(), apiErrorDoc.description()));
            }

        }
        return errors;
    }


    private List<String> getProducesList(RequestMapping classMapping, RequestMapping methodMapping) {
        List<String> produces;
        if (methodMapping.produces().length > 0) {
            produces = Arrays.asList(methodMapping.produces());
        } else {
            produces = Arrays.asList(classMapping.produces());
        }
        return produces.size() > 0 ? produces : null;
    }


    private List<String> getConsumesList(RequestMapping classMapping, RequestMapping methodMapping) {
        List<String> consumes;
        if (methodMapping.produces().length > 0) {
            consumes = Arrays.asList(methodMapping.consumes());
        } else {
            consumes = Arrays.asList(classMapping.consumes());
        }
        return consumes.size() > 0 ? consumes : null;
    }

    private void appendMappedPath(RequestMapping mapping, StringBuffer path) {
        if (mapping != null && mapping.value().length > 0) {
            if (!mapping.value()[0].startsWith("/")) {
                path.append("/");
            }
            path.append(mapping.value()[0]);
        }
    }

    private ApiDocModelRef createBodyObjectModel(Method method) {
        Integer index = getApiBodyObjectIndex(method);
        return createBodyObject(method, index);
    }

    private Integer getApiBodyObjectIndex(Method method) {
        Annotation[][] parametersAnnotations = method.getParameterAnnotations();
        for (int i = 0; i < parametersAnnotations.length; i++) {
            for (int j = 0; j < parametersAnnotations[i].length; j++) {
                if (parametersAnnotations[i][j] instanceof RequestBody) {
                    return i;
                }
            }
        }
        return null;
    }

    private ApiDocModelRef getReturnObject(Method method) {
        Class<?> returnType = method.getReturnType();
        Type genericReturnType = method.getGenericReturnType();
        return modelDocumentationFactory.createModelRef(returnType, genericReturnType);
    }

    private ApiDocModelRef createBodyObject(Method method, Integer index) {
        ApiDocModelRef modelRef = null;
        if (index != null && index >= 0) {
            Class<?> parameter = method.getParameterTypes()[index];
            Type generic = method.getGenericParameterTypes()[index];
            modelRef = modelDocumentationFactory.createModelRef(parameter, generic);
        }
        return modelRef;
    }

    public List<ApiParamDocModel> createApiParamDocs(Method method) {
        List<ApiParamDocModel> docs = new ArrayList<>();
        Annotation[][] parametersAnnotations = method.getParameterAnnotations();
        int i = 0;
        for (Annotation[] parametersAnnotation : parametersAnnotations) {
            Class<?> javaDataType = method.getParameterTypes()[i++];

            ApiParamDocModel apiParamDocModel;
            String apiDataType = null;
            ApiParamDoc apiParamDocAnn = null;
            RequestParam requestParamAnn = null;
            PathVariable pathVariableAnn = null;
            for (Annotation aParametersAnnotation : parametersAnnotation) {
                if (aParametersAnnotation instanceof ApiParamDoc) {
                    apiParamDocAnn = (ApiParamDoc) aParametersAnnotation;
                } else if (aParametersAnnotation instanceof RequestParam) {
                    requestParamAnn = (RequestParam) aParametersAnnotation;
                } else if (aParametersAnnotation instanceof PathVariable) {
                    pathVariableAnn = (PathVariable) aParametersAnnotation;
                }
            }

            if (apiParamDocAnn != null || requestParamAnn != null || pathVariableAnn != null) {
                apiParamDocModel = new ApiParamDocModel();
                if (apiParamDocAnn != null) {
                    apiParamDocModel.setDescription(nullIfEmpty(apiParamDocAnn.value()));
                    apiParamDocModel.setAllowedValues(nullIfEmpty(apiParamDocAnn.allowedValues()));
                    apiParamDocModel.setFormat(nullIfEmpty(apiParamDocAnn.format()));
                    apiDataType = apiParamDocAnn.dataType();
                }

                if (pathVariableAnn != null) {
                    apiParamDocModel.setParamType(ApiParamType.path);
                    apiParamDocModel.setRequired(true);
                    apiParamDocModel.setName(pathVariableAnn.value());
                } else if (requestParamAnn != null) {
                    apiParamDocModel.setParamType(ApiParamType.query);
                    apiParamDocModel.setRequired(requestParamAnn.required());
                    apiParamDocModel.setName(requestParamAnn.value());
                }

                /* Data type not explicitly overridden, derive it from java type */
                if (apiDataType == null || apiDataType.trim().length() == 0) {
                    apiDataType = convertDataType(javaDataType);
                }
                apiParamDocModel.setDataType(apiDataType);
                docs.add(apiParamDocModel);

            }
        }

        return docs.size() > 0 ? docs : null;
    }

    private String convertDataType(Class<?> javaDataType) {
        return javaDataType == null ? "?" : javaDataType.getSimpleName().toLowerCase();
    }

    private String nullIfEmpty(String s) {
        if (s != null && s.isEmpty()) {
            s = null;
        }
        return s;
    }

    private String[] nullIfEmpty(String[] s) {
        if (s != null && s.length == 0) {
            s = null;
        }
        return s;
    }
}
