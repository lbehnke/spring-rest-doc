package com.apporiented.rest.apidoc.factory.impl;


import com.apporiented.rest.apidoc.factory.ControllerDocumentationFactory;
import com.apporiented.rest.apidoc.factory.ModelDocumentationFactory;
import com.apporiented.rest.apidoc.factory.TestApiDocClass;
import com.apporiented.rest.apidoc.factory.TestDTO;
import com.apporiented.rest.apidoc.model.ApiDocModel;
import com.apporiented.rest.apidoc.model.Documentation;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * @author Lars Behnke <lars.behnke@bruker.com>
 */
@RunWith(MockitoJUnitRunner.class)
public class DefaultDocumentationFactoryTest {

    @Mock
    private ControllerDocumentationFactory ctrlDocFactory;

    @Mock
    private ModelDocumentationFactory modelDocFactory;

    private DefaultDocumentationFactory factory;

    @Before
    public void setUp() throws Exception {
        factory = new DefaultDocumentationFactory(null, ctrlDocFactory, modelDocFactory);
    }

    @Test
    public void testDefaultConstructor() throws Exception {
        new DefaultDocumentationFactory();
    }

    @Test
    public void createDocumentationWithPackage() throws Exception {
        when(ctrlDocFactory.createApiDocModel(TestApiDocClass.class)).thenReturn(new ApiDocModel());
        Documentation doc = factory.createDocumentation("1.0", "/test", TestApiDocClass.class.getPackage().getName());
        assertEquals(1, doc.getApis().size());
    }

    @Test
    public void createDocumentationWithClasses() throws Exception {
        when(ctrlDocFactory.createApiDocModel(TestApiDocClass.class)).thenReturn(new ApiDocModel());
        Documentation doc = factory.createDocumentation("1.0", "/test", TestApiDocClass.class, TestDTO.class);
        assertEquals(1, doc.getApis().size());
    }

}