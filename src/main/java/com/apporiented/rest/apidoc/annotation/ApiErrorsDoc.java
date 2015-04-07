package com.apporiented.rest.apidoc.annotation;

import java.lang.annotation.*;

/**
 * This annotations is to be used on your method and represents the possible errors returned by the method
 *
 * @author Fabio Maffioletti
 * @author Lars Behnke
 * @see ApiErrorDoc
 */
@Documented
@Target(value = ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiErrorsDoc {

    /**
     * An array of ApiErrorDoc annotations
     *
     * @return
     * @see ApiErrorDoc
     */
    ApiErrorDoc[] value() default {};

}


