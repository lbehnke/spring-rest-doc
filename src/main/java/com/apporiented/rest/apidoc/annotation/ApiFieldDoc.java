package com.apporiented.rest.apidoc.annotation;

import java.lang.annotation.*;

/**
 * This annotations is to be used on your objects' fields and represents a field of an object
 *
 * @author Fabio Maffioletti
 * @author Lars Behnke
 */
@Documented
@Target(value = {ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiFieldDoc {

    /**
     * A description of what the field is
     *
     * @return
     */
    public String value() default "";

    /**
     * The format pattern for this field
     *
     * @return
     */
    public String format() default "";

    /**
     * The allowed values for this field
     *
     * @return
     */
    public String[] allowedValues() default {};

}
