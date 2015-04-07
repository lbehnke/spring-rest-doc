package com.apporiented.rest.apidoc.model;

import com.apporiented.rest.apidoc.annotation.ApiModelDoc;

import javax.xml.bind.annotation.*;
import java.util.Arrays;
import java.util.List;

/**
 * Model of a REST resource method documentation.
 *
 * @author Lars Behnke
 */
@ApiModelDoc("Model of a REST resource method documentation.")
@XmlRootElement(name = "apiMethodDocModel")
@XmlType(propOrder = {"action", "path", "methodName", "hint", "description", "headers", "consumes", "produces", "parameters", "bodyObject", "response", "apiErrors"})
public class ApiMethodDocModel implements Comparable<ApiMethodDocModel> {
    private static final String[] ACTIONS = new String[]{"GET", "POST", "PUT", "PATCH", "DELETE", "HEAD", "OPTIONS"};
    private String action;
    private String path;
    private String methodName;
    private String description;
    private List<ApiHeaderDocModel> headers;
    private List<String> consumes;
    private List<String> produces;
    private List<ApiParamDocModel> parameters;
    private ApiDocModelRef bodyObject;
    private ApiDocModelRef response;
    private List<ApiErrorDocModel> apiErrors;

    private String hint;

    public ApiMethodDocModel() {
        super();
    }


    @XmlAttribute(required = false)
    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    @XmlElementWrapper(name = "headers")
    @XmlElement(name = "header", required = false)
    public List<ApiHeaderDocModel> getHeaders() {
        return headers;
    }

    public void setHeaders(List<ApiHeaderDocModel> headers) {
        this.headers = headers;
    }

    @XmlElementWrapper(name = "producesList", required = false)
    @XmlElement(name = "produces", required = false)
    public List<String> getProduces() {
        return produces;
    }

    public void setProduces(List<String> produces) {
        this.produces = produces;
    }

    @XmlElementWrapper(name = "consumesList", required = false)
    @XmlElement(name = "consumes", required = false)
    public List<String> getConsumes() {
        return consumes;
    }

    public void setConsumes(List<String> consumes) {
        this.consumes = consumes;
    }

    @XmlAttribute(name = "action", required = true)
    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    @XmlAttribute(name = "path")
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @XmlAttribute(name = "methodName", required = true)
    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String name) {
        this.methodName = name;
    }

    @XmlElement(name = "description", required = false)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @XmlElementWrapper(name = "params", required = false)
    @XmlElement(name = "param", required = false)
    public List<ApiParamDocModel> getParameters() {
        return parameters;
    }

    public void setParameters(List<ApiParamDocModel> parameters) {
        this.parameters = parameters;
    }

    @XmlElement(name = "response", required = false)
    public ApiDocModelRef getResponse() {
        return response;
    }

    public void setResponse(ApiDocModelRef response) {
        this.response = response;
    }

    @XmlElement(name = "bodyObject", required = false)
    public ApiDocModelRef getBodyObject() {
        return bodyObject;
    }

    public void setBodyObject(ApiDocModelRef bodyObject) {
        this.bodyObject = bodyObject;
    }

    @XmlElementWrapper(name = "apiErrors", required = false)
    @XmlElement(name = "apiError", required = false)
    public List<ApiErrorDocModel> getApiErrors() {
        return apiErrors;
    }

    public void setApiErrors(List<ApiErrorDocModel> apiErrors) {
        this.apiErrors = apiErrors;
    }

    @Override
    public int compareTo(ApiMethodDocModel o) {
        String p1 = getPath();
        String p2 = o.getPath();
        int result = p1.compareTo(p2);
        if (result == 0) {
            Integer a1 = Arrays.binarySearch(ACTIONS, getAction().toUpperCase());
            Integer a2 = Arrays.binarySearch(ACTIONS, o.getAction().toUpperCase());
            result = -a1.compareTo(a2);
        }
        return result;
    }
}
