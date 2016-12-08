package com.apporiented.rest.apidoc.factory.impl;

import com.apporiented.rest.apidoc.factory.ModelDocumentationFactory;
import com.apporiented.rest.apidoc.factory.TestApiDocClass;
import com.apporiented.rest.apidoc.factory.TestDTO;
import com.apporiented.rest.apidoc.model.ApiDocModel;
import com.apporiented.rest.apidoc.model.ApiDocModelRef;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.*;

/**
 * @author Lars Behnke <lars.behnke@bruker.com>
 */
@RunWith(MockitoJUnitRunner.class)
public class SpringControllerDocumentationFactoryTest {

    private SpringControllerDocumentationFactory factory;

    @Mock
    private ModelDocumentationFactory modelDocFactory;

    @Before
    public void setUp() throws Exception {
        factory = new SpringControllerDocumentationFactory(modelDocFactory);
    }

    @Test
    public void createApiDocModel() throws Exception {
        ApiDocModelRef errorRef = new ApiDocModelRef();
        errorRef.setModelRef("error");
        errorRef.setMultiple(false);
        errorRef.setModelClass(String.class);
        ApiDocModelRef responseRef = new ApiDocModelRef();
        responseRef.setModelRef("response");
        responseRef.setMultiple(false);
        responseRef.setModelClass(TestDTO.class);

        when(modelDocFactory.createModelRef(String.class, null)).thenReturn(errorRef);
        when(modelDocFactory.createModelRef(any(), anyObject())).thenReturn(responseRef);
        ApiDocModel model = factory.createApiDocModel(TestApiDocClass.class);
        assertNotNull(model);
        assertEquals("test", model.getName());
        assertEquals("TestApiDocClass", model.getClassName());
        assertEquals("test description", model.getDescription());
        assertEquals(1, model.getErrorResponses().size());
        assertEquals(2, model.getMethods().size());
        assertEquals(2, model.getModelClasses().size());

        /* Provoke exception */
        try {
            factory.createApiDocModel(Object.class);
            fail("Configuration exception expected");
        }
        catch (Exception e) {
            // expected
        }

    }




}