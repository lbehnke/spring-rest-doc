package com.apporiented.rest.apidoc.annotation;

import java.lang.annotation.*;

/**
 * This annotations is to be used on your exposed methods.
 *
 * @author Fabio Maffioletti
 * @author Lars Behnke
 */
@Documented
@Target(value = ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiMethodDoc {

    /**
     * A description of what the method does
     *
     * @return
     */
    public String value() default "";

}
