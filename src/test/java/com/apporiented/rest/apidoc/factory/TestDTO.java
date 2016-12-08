package com.apporiented.rest.apidoc.factory;

import com.apporiented.rest.apidoc.annotation.ApiModelDoc;

import javax.xml.bind.annotation.XmlRootElement;

@ApiModelDoc("test model")
@XmlRootElement(name="testModel")
public class TestDTO {

    private String stringField;

    public String getStringField() {
        return stringField;
    }

    public void setStringField(String stringField) {
        this.stringField = stringField;
    }
}
