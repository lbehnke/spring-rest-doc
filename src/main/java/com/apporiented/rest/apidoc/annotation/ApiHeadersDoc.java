package com.apporiented.rest.apidoc.annotation;

import java.lang.annotation.*;

/**
 * This annotations is to be used on your method and contains an array of ApiHeader.
 *
 * @author Fabio Maffioletti
 * @author Lars Behnke
 * @see ApiHeaderDoc
 */
@Documented
@Target(value = ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiHeadersDoc {

    public ApiHeaderDoc[] value() default {};

}
