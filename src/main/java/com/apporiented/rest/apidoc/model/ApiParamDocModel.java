package com.apporiented.rest.apidoc.model;

import com.apporiented.rest.apidoc.annotation.ApiModelDoc;
import com.apporiented.rest.apidoc.ApiParamType;

import javax.xml.bind.annotation.*;

@ApiModelDoc("Documentation of a parameter of a REST resource method.")
@XmlRootElement(name = "apiParamDocModel")
@XmlType(propOrder = {"name", "paramType", "modelRef", "dataType", "format", "required", "description", "allowedValues"})
public class ApiParamDocModel {
    private String name;
    private ApiParamType paramType;
    private String modelRef;
    private String format;
    private boolean required;
    private String description;
    private String[] allowedValues;
    private String dataType;

    public ApiParamDocModel() {
        super();
    }


    public ApiParamDocModel(String name, String description, ApiParamType paramType, boolean required, String dataType, String[] allowedValues,
                            String format) {
        super();
        this.name = name;
        this.description = description;
        this.paramType = paramType;
        this.required = required;
        this.dataType = dataType;
        this.allowedValues = allowedValues;
        this.format = format;
    }

    @XmlAttribute(required = true)
    public ApiParamType getParamType() {
        return paramType;
    }

    public void setParamType(ApiParamType paramType) {
        this.paramType = paramType;
    }

    @XmlAttribute(required = false)
    public String getModelRef() {
        return modelRef;
    }

    public void setModelRef(String modelRef) {
        this.modelRef = modelRef;
    }

    @XmlAttribute(required = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement(required = false)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @XmlAttribute(required = true)
    public boolean getRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    @XmlElementWrapper(name = "allowedValues", required = false)
    @XmlElement(name = "value")
    public String[] getAllowedValues() {
        return allowedValues;
    }

    public void setAllowedValues(String[] allowedValues) {
        this.allowedValues = allowedValues;
    }

    @XmlAttribute
    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }
}
