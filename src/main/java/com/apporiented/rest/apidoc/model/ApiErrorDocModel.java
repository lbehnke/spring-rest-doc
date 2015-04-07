package com.apporiented.rest.apidoc.model;

import com.apporiented.rest.apidoc.annotation.ApiModelDoc;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Model of an API error documentation.
 *
 * @author Lars Behnke
 */
@ApiModelDoc("Model of an API error documentation.")
@XmlRootElement(name = "apiErrorDocModel")
@XmlType(propOrder = {"code", "description"})
public class ApiErrorDocModel {
    private Integer code;
    private String description;

    public ApiErrorDocModel() {
        this(null, null);
    }

    public ApiErrorDocModel(Integer code, String description) {
        super();
        this.code = code;
        this.description = description;
    }

    @XmlAttribute
    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @XmlAttribute
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
