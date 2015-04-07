package com.apporiented.rest.apidoc.annotation;

import java.lang.annotation.*;

/**
 * This annotations is to be used inside an annotations of type ApiErrorsDoc
 *
 * @author Fabio Maffioletti
 * @author Lars Behnke
 * @see ApiErrorsDoc
 */
@Documented
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiErrorDoc {

    public int code();

    public String description();

}
