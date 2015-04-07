package com.apporiented.rest.apidoc.annotation;

import java.lang.annotation.*;

/**
 * This annotations is to be used on your object classes and represents an object used for communication between clients and server
 *
 * @author Fabio Maffioletti
 * @author Lars Behnke
 */
@Documented
@Target(value = ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiModelDoc {

    /**
     * A description of what the object contains or represents
     *
     * @return
     */
    public String value() default "";


}
