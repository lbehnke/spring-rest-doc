package com.apporiented.rest.apidoc.factory;

import com.apporiented.rest.apidoc.annotation.ApiFieldDoc;
import com.apporiented.rest.apidoc.annotation.ApiAdaptedTypeDoc;
import com.apporiented.rest.apidoc.annotation.ApiModelDoc;
import com.apporiented.rest.apidoc.model.ApiDocModelRef;
import com.apporiented.rest.apidoc.model.ApiModelDocModel;
import com.apporiented.rest.apidoc.model.ApiModelFieldDocModel;
import com.apporiented.rest.apidoc.utils.ApiDocConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.async.DeferredResult;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.beans.*;
import java.lang.reflect.*;
import java.util.*;

/**
 * Documentation factory that creates a documentation model based on internal API annoations
 * and JAXB annotations.
 *
 * @author Lars Behnke
 */
public class JAXBModelDocFactory implements ModelDocumentationFactory {

    public static final String XML_NODE_ATTR = "A";
    public static final String XML_NODE_ELEM = "E";
    public static final String XML_NODE_ELEM_WRAPPER = "EW";

    public static final String DEFAULT_NAME = "##default";
    private static Logger log = LoggerFactory.getLogger(JAXBModelDocFactory.class);

    public ApiModelDocModel createModelDocModel(Class clazz) {

        /* Checks */
        ApiModelDoc objDoc = (ApiModelDoc) clazz.getAnnotation(ApiModelDoc.class);
        if (objDoc == null) {
            return null;
        }
        BeanInfo beanInfo;
        try {
            beanInfo = Introspector.getBeanInfo(clazz);
        } catch (IntrospectionException e) {
            log.warn("Could not introspect bean class " + clazz.getName() + ". No API documentation will be available for this class.");
            return null;
        }

        ApiModelDocModel objectModel = new ApiModelDocModel();

        /* Handle class data */
        objectModel.setName(createModelReferenceId(clazz));
        objectModel.setDescription(objDoc.value());
        objectModel.setClassName(clazz.getSimpleName());

        /* Handle fields */
        Set<ApiModelFieldDocModel> fieldModels = new HashSet<>();
        PropertyDescriptor[] propDesc = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor propertyDescriptor : propDesc) {

            /* We do not want the class */
            if ("class".equals(propertyDescriptor.getName())) {
                continue;
            }
            Field field;
            try {
                field = clazz.getDeclaredField(propertyDescriptor.getName());
            } catch (NoSuchFieldException e) {
                field = null;
            }

            ApiModelFieldDocModel fieldModel = new ApiModelFieldDocModel();
            Method getter = propertyDescriptor.getReadMethod();

            XmlTransient trans = null;
            XmlElement elem = null;
            XmlElementWrapper elems = null;
            XmlAttribute attr = null;
            XmlJavaTypeAdapter adapter = null;
            ApiFieldDoc doc = null;

            Type genericResultType = null;
            Class<?> resultType = propertyDescriptor.getPropertyType();

            if (getter != null) {
                trans = getter.getAnnotation(XmlTransient.class);
                elem = getter.getAnnotation(XmlElement.class);
                elems = getter.getAnnotation(XmlElementWrapper.class);
                attr = getter.getAnnotation(XmlAttribute.class);
                doc = getter.getAnnotation(ApiFieldDoc.class);
                adapter = getter.getAnnotation(XmlJavaTypeAdapter.class);
                genericResultType = getter.getGenericReturnType();
            }
            if (field != null) {
                elem = elem == null ? field.getAnnotation(XmlElement.class) : elem;
                elems = elems == null ? field.getAnnotation(XmlElementWrapper.class) : elems;
                attr = attr == null ? field.getAnnotation(XmlAttribute.class) : attr;
                doc = doc == null ? field.getAnnotation(ApiFieldDoc.class) : doc;
                genericResultType = genericResultType == null ? field.getGenericType() : genericResultType;
                trans = trans == null ? field.getAnnotation(XmlTransient.class) : trans;
                adapter = adapter == null ? field.getAnnotation(XmlJavaTypeAdapter.class) : adapter;
            }

            if (trans != null) {
                continue;
            }
            String format = null;
            String[] allowedValues = null;
            if (doc != null) {
                if (doc.format().length() > 0) {
                    format = doc.format();
                }
                if (doc.allowedValues().length > 0) {
                    allowedValues = doc.allowedValues();
                }
            }

            String xmlNodeType = XML_NODE_ELEM;
            String xmlName = null;
            boolean required = false;
            if (elems != null) {
                xmlName = elems.name();
                required = elems.required();
                xmlNodeType = XML_NODE_ELEM_WRAPPER;
            } else if (elem != null) {
                xmlName = elem.name();
                required = elem.required();

            } else if (attr != null) {
                xmlName = attr.name();
                required = attr.required();
                xmlNodeType = XML_NODE_ATTR;
            }

            /* Adapters */
            if (adapter != null) {
                Class<? extends XmlAdapter> adapterClass = adapter.value();
                try {
                    ApiAdaptedTypeDoc td = adapterClass.getAnnotation(ApiAdaptedTypeDoc.class);
                    if (td != null) {
                        resultType = td.value();

                        /* Set allowed values if not already defined (field config has precedence */
                        if (allowedValues == null && td.allowedValues() != null && td.allowedValues().length > 0) {
                            allowedValues = td.allowedValues();
                        }

                        /* Set format if not already defined (field config has precedence */
                        if (format == null && td.format() != null && td.format().length() > 0) {
                            format = td.format();
                        }
                    }
                    else {
                        Method marshalMethod = ReflectionUtils.findMethod(adapterClass, "marshal", resultType);
                        genericResultType = marshalMethod.getGenericReturnType();
                        resultType = marshalMethod.getReturnType();
                    }
                } catch (Exception e) {
                    log.warn("Could not introspect bean class " + clazz.getName() + ". No API documentation will be available for this class.");
                    continue;
                }
            }

            fieldModel.setName(createFieldName(propertyDescriptor, xmlName));
            fieldModel.setRequired(required);
            fieldModel.setXmlNodeType(xmlNodeType);
            if (doc != null) {
                fieldModel.setDescription(doc.value());
            }

            /* Derive allowed values from type if possible (enums!) */
            if (allowedValues == null) {
                allowedValues = createAllowedValues(resultType);
            }
            fieldModel.setAllowedValues(allowedValues);
            fieldModel.setFormat(format);

            ApiDocModelRef modelRef = createModelRef(resultType, genericResultType);
            if (modelRef != null) {
                fieldModel.setMultiple(modelRef.getMultiple());
                fieldModel.setType(modelRef.getModelRef());
                fieldModel.setMapKeyObject(modelRef.getMapKeyObject());
                fieldModel.setMapValueObject(modelRef.getMapValueObject());
                fieldModel.setMap(modelRef.getMap());
            }

            fieldModels.add(fieldModel);
        }

        /* Sort fields alphabetically */
        List<ApiModelFieldDocModel> sortedFields = new ArrayList<>();
        sortedFields.addAll(fieldModels);
        Collections.sort(sortedFields);
        objectModel.setFields(sortedFields);

        return objectModel;
    }

    private String[] createAllowedValues(Class<?> resultType) {
        String[] allowedValues = null;
        if (resultType.isEnum()) {
            List<String> consts = new ArrayList<>();
            for (Object o : resultType.getEnumConstants()) {
                if (!consts.contains(o.toString())) {
                    consts.add(o.toString());
                }
            }
            allowedValues = consts.toArray(new String[consts.size()]);
        }
        return nullIfEmpty(allowedValues);
    }

    private String[] nullIfEmpty(String[] strings) {
        if (strings != null && strings.length == 0) {
            strings = null;
        }
        return strings;
    }


    @Override
    public ApiDocModelRef createModelRef(Class<?> objectType, Type genericObjectType) {
        ApiDocModelRef modelRef;

        /* Handle Maps */
        if (Map.class.isAssignableFrom(objectType)) {
            if (genericObjectType instanceof ParameterizedType) {
                ParameterizedType parameterizedType = (ParameterizedType) genericObjectType;
                ClassInfo mapKeyType = resolveClassInfo(parameterizedType.getActualTypeArguments()[0]);
                ClassInfo mapValueType = resolveClassInfo(parameterizedType.getActualTypeArguments()[1]);
                modelRef = new ApiDocModelRef(mapValueType.getClazz(), ApiDocConstants.UNDEFINED, mapKeyType.getRefKey(), mapValueType.getRefKey(), true, true);
            } else {
                modelRef = new ApiDocModelRef(null, ApiDocConstants.UNDEFINED, ApiDocConstants.UNDEFINED, ApiDocConstants.UNDEFINED, true, true);

            }
        }

        /* Handle collections */
        else if (Collection.class.isAssignableFrom(objectType)) {
            ClassInfo classInfo = resolveClassInfo(genericObjectType);
            modelRef = new ApiDocModelRef(classInfo.getClazz(), classInfo.getRefKey(), null, null, true, null);
        }

        /* Handle arrays */
        else if (objectType.isArray()) {
            Class<?> itemClass = objectType.getComponentType();
            modelRef = new ApiDocModelRef(itemClass, createModelReferenceId(itemClass), null, null, true, null);
        }

        /* Handle enums */
        else if (objectType.isEnum()) {
            modelRef = new ApiDocModelRef(null, createModelReferenceId(String.class), null, null, false, null);
        }

        /* Handle Response entity wrapper */
        else if (ResponseEntity.class.isAssignableFrom(objectType)) {
            ClassInfo classInfo = resolveClassInfo(genericObjectType);
            modelRef = new ApiDocModelRef(classInfo.getClazz(), classInfo.getRefKey(), null, null, false, null);
        }

        /* Handle Deferred result wrapper */
        else if (DeferredResult.class.isAssignableFrom(objectType)) {
            ClassInfo classInfo = resolveClassInfo(genericObjectType);
            modelRef = new ApiDocModelRef(classInfo.getClazz(), classInfo.getRefKey(), null, null, false, null);
        }

        /* Handle single objects */
        else {
            modelRef = new ApiDocModelRef(objectType, createModelReferenceId(objectType), null, null, false, null);
        }
        return modelRef;
    }

    private ClassInfo resolveClassInfo(Type type) {
        return resolveClassInfo(type, null);
    }

    private ClassInfo resolveClassInfo(Type type, Integer paramTypeIdx) {
        ClassInfo result = null;
        if (type != null) {
            if (type instanceof ParameterizedType) {
                if (paramTypeIdx == null) {
                    paramTypeIdx = 0;
                }
                ParameterizedType parameterizedType = (ParameterizedType) type;
                result = resolveClassInfo(parameterizedType.getActualTypeArguments()[paramTypeIdx], 0);
            } else if (type instanceof WildcardType) {
                result = new ClassInfo(Object.class, ApiDocConstants.WILDCARD);
            } else {
                Class<?> clazz = (Class<?>) type;
                result = new ClassInfo(clazz, createModelReferenceId(clazz));
            }
        }
        return result;
    }

    private String createFieldName(PropertyDescriptor desc, String xmlName) {
        String name;
        if (xmlName != null) {
            if (xmlName.isEmpty() || DEFAULT_NAME.equals(xmlName)) {
                xmlName = null;
            }
        }
        if (xmlName == null) {
            name = desc.getName();
        } else {
            name = xmlName;
        }
        return name;
    }

    protected String createModelReferenceId(Class<?> clazz) {
        String name = null;
        if (clazz != null) {
            XmlRootElement root = clazz.getAnnotation(XmlRootElement.class);
            if (root != null) {
                name = root.name();
            }
            if (name == null || name.length() == 0 || DEFAULT_NAME.equals(name)) {
                BeanInfo beanInfo = null;
                try {
                    beanInfo = Introspector.getBeanInfo(clazz);
                } catch (IntrospectionException e) {
                    log.warn("Could not introspect class " + clazz.getName() + ". No reference ID can be created for this class.");
                }
                if (beanInfo != null && beanInfo.getBeanDescriptor() != null) {
                    name = beanInfo.getBeanDescriptor().getName();
                }
            }
        }
        if (name != null) {
            name = StringUtils.uncapitalize(name);
        }

        return name;
    }


    public static class ClassInfo {
        private Class<?> clazz;
        private String refKey;

        public ClassInfo(Class<?> clazz, String refKey) {
            this.clazz = clazz;
            this.refKey = refKey;
        }

        public Class<?> getClazz() {
            return clazz;
        }

        public String getRefKey() {
            return refKey;
        }
    }
}
