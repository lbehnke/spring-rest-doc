package com.apporiented.rest.apidoc.model;

import com.apporiented.rest.apidoc.annotation.ApiModelDoc;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * Representation of the documentation of a model (domain) class.
 * @author Lars Behnke
 */
@ApiModelDoc("Documentation of a DTO (data transfer object).")
@XmlRootElement(name = "apiModelDocModel")
@XmlType(propOrder = {"name", "className", "description", "fields"})
public class ApiModelDocModel implements Comparable<ApiModelDocModel> {
    private String name;
    private String className;
    private String description;
    private List<ApiModelFieldDocModel> fields;

    public ApiModelDocModel() {
        this(null, null, null);
    }

    public ApiModelDocModel(String name, String description, List<ApiModelFieldDocModel> fields) {
        super();
        this.name = name;
        this.description = description;
        this.fields = fields;
    }

    @XmlAttribute
    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    @XmlAttribute
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @XmlElementWrapper(name = "fields", required = false)
    @XmlElement(name = "field")
    public List<ApiModelFieldDocModel> getFields() {
        return fields;
    }

    public void setFields(List<ApiModelFieldDocModel> fields) {
        this.fields = fields;
    }

    @Override
    public int compareTo(ApiModelDocModel o) {
        return name.compareTo(o.getName());
    }

}
