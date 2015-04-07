package com.apporiented.rest.apidoc.model;

import com.apporiented.rest.apidoc.annotation.ApiModelDoc;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Model of an API request header documentation.
 *
 * @author Lars Behnke
 */
@ApiModelDoc("Model of an API request header documentation.")
@XmlRootElement(name = "apiHeaderDocModel")
@XmlType(propOrder = {"name", "description"})
public class ApiHeaderDocModel {
    private String name;
    private String description;

    public ApiHeaderDocModel() {
        this(null, null);
    }

    public ApiHeaderDocModel(String name, String description) {
        super();
        this.name = name;
        this.description = description;
    }

    @XmlAttribute(name = "name", required = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement(name = "description", required = false)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
