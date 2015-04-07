package com.apporiented.rest.apidoc.annotation;

import java.lang.annotation.*;

/**
 * This annotations is to be used inside an annotations of type ApiHeadersDoc
 *
 * @author Fabio Maffioletti
 * @author Lars Behnke
 * @see ApiHeadersDoc
 */
@Documented
@Target(value = ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiHeaderDoc {

    public String name();

    public String description();

}
