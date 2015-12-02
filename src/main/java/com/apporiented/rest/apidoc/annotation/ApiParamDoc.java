package com.apporiented.rest.apidoc.annotation;

import java.lang.annotation.*;

/**
 * Description of a REST resource parameter.
 *
 * @author Fabio Maffioletti
 * @author Lars Behnke
 * @see com.apporiented.rest.apidoc.model.ApiParamDocModel
 */
@Documented
@Target(value = ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiParamDoc {

    enum EmptyEnum {}

    String value() default "";

    String[] allowedValues() default {};

    /**
     * Intended for REST interfaces that accept string values as parameters,
     * which are mapped to enum types by the application.
     * @return The enum class
     */
    Class<? extends Enum> enumClass() default EmptyEnum.class;

    String format() default "";

    String dataType() default "";

}
