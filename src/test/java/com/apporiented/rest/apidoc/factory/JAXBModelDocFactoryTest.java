package com.apporiented.rest.apidoc.factory;

import com.apporiented.rest.apidoc.annotation.ApiAdaptedTypeDoc;
import com.apporiented.rest.apidoc.annotation.ApiFieldDoc;
import com.apporiented.rest.apidoc.annotation.ApiModelDoc;
import com.apporiented.rest.apidoc.model.ApiModelDocModel;
import org.junit.Before;
import org.junit.Test;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

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

    //@Test
    public void testCreateModelRef() throws Exception {

    }

    //@Test
    public void testCreateModelReferenceId() throws Exception {

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