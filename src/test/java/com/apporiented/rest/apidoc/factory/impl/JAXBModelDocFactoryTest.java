package com.apporiented.rest.apidoc.factory.impl;

import com.apporiented.rest.apidoc.annotation.ApiAdaptedTypeDoc;
import com.apporiented.rest.apidoc.annotation.ApiFieldDoc;
import com.apporiented.rest.apidoc.annotation.ApiModelDoc;
import com.apporiented.rest.apidoc.factory.TestDTO;
import com.apporiented.rest.apidoc.model.ApiDocModelRef;
import com.apporiented.rest.apidoc.model.ApiModelDocModel;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.async.DeferredResult;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class JAXBModelDocFactoryTest {

    private JAXBModelDocFactory factory;

    @Before
    public void setup() {
        factory = new JAXBModelDocFactory();
    }

    @Test
    public void testCreateModelDocModel() throws Exception {
        ApiModelDocModel model = factory.createModelDocModel(TestClass.class);
        assertEquals("Description TestClass", model.getDescription());
        assertEquals(2, model.getFields().size());
        assertEquals("TestField", model.getFields().get(0).getDescription());
        assertEquals("entries", model.getFields().get(0).getName());
        assertEquals("substitute", model.getFields().get(0).getType());
        assertEquals("localDate", model.getFields().get(1).getType());
        assertEquals("yyyy-MM-dd", model.getFields().get(1).getFormat());

    }

    @Test
    public void testCreateModelRef() throws Exception {
        ApiDocModelRef ref;
        ParameterizedType pt = new ParameterizedType() {
            @Override
            public Type[] getActualTypeArguments() {
                return new Type[]{String.class, String.class};
            }

            @Override
            public Type getRawType() {
                return null;
            }

            @Override
            public Type getOwnerType() {
                return null;
            }
        };
        WildcardType wt = new WildcardType() {
            @Override
            public Type[] getUpperBounds() {
                return new Type[0];
            }

            @Override
            public Type[] getLowerBounds() {
                return new Type[]{String.class};
            }
        };

        /* Map */
        ref = factory.createModelRef(Map.class, null);
        assertTrue(ref.getMultiple().booleanValue());
        ref = factory.createModelRef(Map.class, pt);
        assertTrue(ref.getMultiple().booleanValue());
        assertEquals("string", ref.getMapKeyObject());
        assertEquals("string", ref.getMapValueObject());

        /* List */
        ref = factory.createModelRef(List.class, String.class);
        assertTrue(ref.getMultiple().booleanValue());
        assertTrue(ref.getMultiple().booleanValue());
        assertEquals("string", ref.getModelRef());

        ref = factory.createModelRef(List.class, pt);
        assertTrue(ref.getMultiple().booleanValue());
        ref = factory.createModelRef(List.class, wt);
        assertTrue(ref.getMultiple().booleanValue());

        /* Array */
        ref = factory.createModelRef(new Object[0].getClass(), String.class);
        assertTrue(ref.getMultiple().booleanValue());
        assertEquals(ref.getModelRef(), "object");

        /* Enum */
        ref = factory.createModelRef(TestEnum.class, null);
        assertFalse(ref.getMultiple().booleanValue());
        assertEquals(ref.getModelRef(), "string");

        /* ResponseEntity */
        ResponseEntity<TestDTO> re = new ResponseEntity<>(HttpStatus.OK);
        ref = factory.createModelRef(re.getClass(), TestDTO.class);
        assertFalse(ref.getMultiple().booleanValue());
        assertEquals(ref.getModelRef(), "testModel");

        /* DeferredResult */
        DeferredResult<TestDTO> dr = new DeferredResult<>(1L);
        ref = factory.createModelRef(dr.getClass(), TestDTO.class);
        assertEquals(ref.getModelRef(), "testModel");

        /* Other */
        ref = factory.createModelRef(Long.class, null);
        assertEquals(ref.getModelRef(), "long");
    }

    @Test
    public void testCreateAllowedValues() throws Exception {
        String[] values = factory.createAllowedValues(TestEnum.class);
        assertEquals("A", values[0]);
        assertEquals("B", values[1]);

    }

    //@Test
    public void testCreateModelReferenceId() throws Exception {

    }

    enum TestEnum {
        A, B
    }

    @ApiModelDoc("Description TestClass")
    @XmlRootElement
    private static class TestClass {

        private List<ClassB> list;
        private LocalDate ldt;

        @ApiFieldDoc("TestField")
        @XmlElement(name = "entries")
        @XmlJavaTypeAdapter(TestXmlAdapter.class)
        public List<ClassB> getList() {
            return list;
        }
        public void setList(List<ClassB> list) {
            this.list = list;
        }


        @ApiFieldDoc("DateField")
        @XmlAttribute
        @XmlJavaTypeAdapter(ZdtXmlAdapter.class)
        public LocalDate getLdt() {
            return ldt;
        }

        public void setLdt(LocalDate ldt) {
            this.ldt = ldt;
        }
    }

    @ApiModelDoc("Description ClassA")
    @XmlRootElement(name = "substitute")
    private static class ClassA {

        @XmlElementWrapper(name="entries")
        @XmlElement(name="entry")
        private List<String> list;
    }

    @XmlRootElement(name="entry")
    private static class ClassB {

        @XmlElement
        private String entry;
    }

    private static class TestXmlAdapter extends XmlAdapter<ClassA, List<ClassB>> {
        public List<ClassB> unmarshal(ClassA v) throws Exception {
            return null;
        }
        public ClassA marshal(List<ClassB> v) throws Exception {
            return null;
        }
    }

    @ApiAdaptedTypeDoc(value = LocalDate.class, format = "yyyy-MM-dd")
    private static class ZdtXmlAdapter extends XmlAdapter<LocalDate, String> {
        public String unmarshal(LocalDate v) throws Exception {
            return null;
        }
        public LocalDate marshal(String v) throws Exception {
            return null;
        }
    }

}