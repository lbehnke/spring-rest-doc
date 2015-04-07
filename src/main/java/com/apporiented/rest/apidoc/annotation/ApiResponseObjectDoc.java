package com.apporiented.rest.apidoc.annotation;

import java.lang.annotation.*;

/**
 * This annotations is to be used on your method and represents the returned value
 *
 * @author Fabio Maffioletti
 * @author Lars Behnke
 * @see ApiModelDoc
 */
@Documented
@Target(value = ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiResponseObjectDoc {

}
