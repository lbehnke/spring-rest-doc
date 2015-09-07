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

    public String description();

    public String name();

    public Class<?>[] errorResponseClasses() default {};

}
