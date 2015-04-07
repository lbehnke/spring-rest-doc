package com.apporiented.rest.apidoc.annotation;

import java.lang.annotation.*;

/**
 * This annotations is to be used on your "service" class, for example controller classes in Spring MVC.
 *
 * @author Fabio Maffioletti
 * @author Lars Behnke
 */
@Documented
@Target(value = ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiDoc {

    /**
     * A description of what the API does
     *
     * @return
     */
    public String description();

    /**
     * The name of the API
     *
     * @return
     */
    public String name();

}
